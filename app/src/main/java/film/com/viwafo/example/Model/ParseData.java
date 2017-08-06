package film.com.viwafo.example.Model;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Fetcher.AsyncGetListMovieApi;
import film.com.viwafo.example.Model.Fetcher.AsyncGetNameActorApi;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {

    public static final String urlApiActor = "http://api.themoviedb.org/3/movie/credits?api_key=";
    private static final String urlApiMovie = "http://api.themoviedb.org/3/movie/popular?api_key=01d6eaad3bb353d05c20716701c51937&page=";
    private static int page;

    public ParseData() {
        page = 1;
    }

    public void getListMovie(OnDatabaseCreated listenner) {
        AsyncGetListMovieApi asyncApi = new AsyncGetListMovieApi(listenner);
        asyncApi.execute(urlApiMovie + page);
        page++;
    }

    public void getNameActorMovie(int id, OnDatabaseCreated listenner) {
        AsyncGetNameActorApi actorApi = new AsyncGetNameActorApi(listenner);
        actorApi.execute("http://api.themoviedb.org/3/movie/" + id + "/credits?api_key=01d6eaad3bb353d05c20716701c51937&page=1");
    }


}
