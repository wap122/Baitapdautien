package film.com.viwafo.example.Model;

import java.util.List;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Entity.ListActor;
import film.com.viwafo.example.Model.Entity.ListMovie;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Fetcher.ActorDataApi;
import film.com.viwafo.example.Model.Fetcher.MovieDataApi;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ParseData {
    private Retrofit retrofit = null;
    private int page = 1;
    private OnDatabaseCreated listenner;

    public void getListMovie(final OnDatabaseCreated listenner) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/").addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDataApi movieAPI = retrofit.create(MovieDataApi.class);
        Call<ListMovie> call = movieAPI.getMovies(String.valueOf(page));
        call.enqueue(new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                List listMovie = response.body().getMovies();
                for (Object movie : listMovie) {
                    MovieSqlite.getInstance(null).addMovie((Movie) movie);
                }
                listenner.onCreated(listMovie);
                page++;
            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {

            }
        });
    }

    public void getNameActorMovie(int id, final OnDatabaseCreated listenner) {
//        AsyncGetNameActorApi actorApi = new AsyncGetNameActorApi(listenner);
//        actorApi.execute("http://api.themoviedb.org/3/movie/" + id + "/credits?api_key=01d6eaad3bb353d05c20716701c51937&page=1")
        ActorDataApi actorAPI = retrofit.create(ActorDataApi.class);
        Call<ListActor> call = actorAPI.getActors(id);
        call.enqueue(new Callback<ListActor>() {
            @Override
            public void onResponse(Call<ListActor> call, Response<ListActor> response) {
                listenner.onCreated(response.body().getActors());
            }

            @Override
            public void onFailure(Call<ListActor> call, Throwable t) {

            }
        });

    }



}
