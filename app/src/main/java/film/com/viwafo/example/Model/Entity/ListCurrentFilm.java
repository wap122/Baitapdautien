package film.com.viwafo.example.Model.Entity;

import java.util.ArrayList;

/**
 * Created by minhl on 29/07/2017.
 */

public class ListCurrentFilm extends ArrayList<Movie> {
    private static ListCurrentFilm instance;

    public static synchronized ListCurrentFilm getInstance() {
        if (instance == null) {
            instance = new ListCurrentFilm();
        }
        return instance;
    }
}
