package film.com.viwafo.example.Model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhl on 27/07/2017.
 */

public class FavoriteList extends ArrayList<Movie> {

    private static FavoriteList instance;

    public static synchronized FavoriteList getInstance() {
        if (instance == null) {
            instance = new FavoriteList();
        }
        return instance;
    }

    private FavoriteList() {

    }

}
