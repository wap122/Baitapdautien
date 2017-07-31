package film.com.viwafo.example.Model;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Fetcher.AsyncApi;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {

    private static final String urlApi = "http://api.themoviedb.org/3/movie/popular?api_key=01d6eaad3bb353d05c20716701c51937&page=";
    private static int page = 1;
    private OnDatabaseCreated listenner;

    public ParseData(OnDatabaseCreated listenner) {
        this.listenner = listenner;
    }

    public void getDataFormApi() {
        AsyncApi asyncApi = new AsyncApi(listenner);
        asyncApi.execute(urlApi + page);
        page++;
    }

}
