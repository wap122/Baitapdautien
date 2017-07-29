package film.com.viwafo.example.Model.Entity;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Model.Manager.MovieSqlite;

/**
 * Created by minhl on 27/07/2017.
 */

public class FavoriteList extends ArrayList<Movie> {

    private static FavoriteList instance;
    private boolean[] favorite;

    private FavoriteList() {
        favorite = new boolean[MovieSqlite.getInstance(null).getAllMovies().size()];
    }

    public static synchronized FavoriteList getInstance() {
        if (instance == null) {
            instance = new FavoriteList();
        }
        return instance;
    }

    public void setFavorite(int position, boolean isFavorite) {
        favorite[position] = isFavorite;
    }

    public boolean isFavorite(int position) {
        return favorite[position];
    }
}
