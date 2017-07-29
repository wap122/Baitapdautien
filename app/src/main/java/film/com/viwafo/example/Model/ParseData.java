package film.com.viwafo.example.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Model.Entity.ListCurrentFilm;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Fetcher.AsyncApi;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {
    private MainActivity mainActivity;

    public ParseData(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getDataFormApi(String urlApi) {
        AsyncApi asyncApi = new AsyncApi(this);
        asyncApi.execute(urlApi);
    }

    public void createDatabase(String jsonStr) {
        try {
            JSONObject jsonRootObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonRootObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.optString("title");
                String posterUrl = jsonObject.optString("poster_path");
                String realeaseDate = jsonObject.optString("release_date");
                String voteAverage = jsonObject.optString("vote_average");
                String overview = jsonObject.optString("overview");
                String isAdult = jsonObject.optString("adult");
                MovieSqlite.getInstance(null).addMovie(new Movie(title,posterUrl,
                        realeaseDate,voteAverage,overview,isAdult));
            }
            ListCurrentFilm.getInstance().addAll(0, MovieSqlite.getInstance(null).getAllMovies());
            mainActivity.changeFilmFragment();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
