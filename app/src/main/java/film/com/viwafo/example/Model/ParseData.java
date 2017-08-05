package film.com.viwafo.example.Model;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Fetcher.AsyncApi;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {

    public static final String urlApiMovie = "http://api.themoviedb.org/3/movie/popular?api_key=01d6eaad3bb353d05c20716701c51937&page=";
    public static final String urlApiActor = "http://api.themoviedb.org/3/movie/credits?api_key=";
    private static int page;
    private OnDatabaseCreated listenner;

    public ParseData(OnDatabaseCreated listenner) {
        this.listenner = listenner;
        page = 1;
    }

    public void getDataFormApi(String urlApi) {
        AsyncApi asyncApi = new AsyncApi(listenner);
        asyncApi.execute(urlApi + page);
        if (urlApi.contentEquals(urlApiMovie)) {
            page++;
        }
    }

}
