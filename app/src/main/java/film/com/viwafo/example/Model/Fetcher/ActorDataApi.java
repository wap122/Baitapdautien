package film.com.viwafo.example.Model.Fetcher;

import film.com.viwafo.example.Model.Entity.ListActor;
import film.com.viwafo.example.Model.Entity.ListMovie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



/**
 * Created by minh on 8/16/17.
 */

public interface ActorDataApi {
    @GET("{id}/credits?api_key=01d6eaad3bb353d05c20716701c51937&page=1")
    Call<ListActor> getActors(@Path("id")  int id);
}
