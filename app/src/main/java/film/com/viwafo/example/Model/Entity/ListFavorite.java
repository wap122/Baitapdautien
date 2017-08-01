package film.com.viwafo.example.Model.Entity;

import java.util.ArrayList;

/**
 * Created by minhl on 27/07/2017.
 */

public class ListFavorite extends ArrayList<Movie> {

    private static ListFavorite instance;
    public boolean[] favorite;

    private ListFavorite() {
        favorite = new boolean[20];
    }

    public static synchronized ListFavorite getInstance() {
        if (instance == null) {
            instance = new ListFavorite();
        }
        return instance;
    }

    @Override
    public boolean add(Movie movie) {
        favorite[movie.getId()] = true;
        return super.add(movie);

    }

    @Override
    public boolean remove(Object object) {
        Movie movie = (Movie) object;
        favorite[movie.getId()] = false;
        return super.remove(object);
    }

    public boolean isFavorite(int position) {
        return favorite[position];
    }
}
