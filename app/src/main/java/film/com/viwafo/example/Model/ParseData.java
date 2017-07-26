package film.com.viwafo.example.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Fetcher.AsyncApi;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {
    public static void getDataFormApi() {
        String urlApi = "http://api.themoviedb.org/3/movie/popular?api_key=01d6eaad3bb353d05c20716701c51937&page=";
        AsyncApi asyncApi = new AsyncApi();
        asyncApi.execute(urlApi);

    }

    public static void createJson(String jsonStr) {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
