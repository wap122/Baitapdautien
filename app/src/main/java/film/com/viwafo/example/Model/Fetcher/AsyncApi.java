package film.com.viwafo.example.Model.Fetcher;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.Util.IOUtil;


/**
 * Created by macintoshhd on 7/23/17.
 */
public class AsyncApi extends AsyncTask<String, Void, List> {
    private OnDatabaseCreated listenner;

    public AsyncApi(OnDatabaseCreated listenner) {
        this.listenner = listenner;
    }

    @Override
    protected List doInBackground(String... params) {
        String jsonStr = getStringFromURL(params[0]);
        return createDatabase(jsonStr);
    }

    private String getStringFromURL(String urlApi) {
        InputStream in = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlApi);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int resCode = httpURLConnection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpURLConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuiety(in);
            IOUtil.closeQuiety(br);
        }
        return null;
    }

    private List createDatabase(String jsonStr) {
        try {
            List<Movie> list = new ArrayList<>();
            JSONObject jsonRootObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonRootObject.optJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(jsonObject.getInt("id"));
                movie.setTitle(jsonObject.optString("title"));
                movie.setPosterUrl(jsonObject.optString("poster_path"));
                movie.setReleaseDate(jsonObject.optString("release_date"));
                movie.setVoteAverage(jsonObject.optString("vote_average"));
                movie.setOverview(jsonObject.optString("overview"));
                movie.setIsAdult(jsonObject.optString("adult"));
                list.add(movie);
                MovieSqlite.getInstance(null).addMovie(movie);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        listenner.OnCreated(list);
//        parseData.mainActivity.changeFilmFragment();
    }

}
