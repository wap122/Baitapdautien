package film.com.viwafo.example.Listener;

import android.graphics.drawable.Drawable;

import film.com.viwafo.example.Model.Entity.Movie;

/**
 * Created by minhl on 05/08/2017.
 */

public interface OnItemListview {
    void onItemListviewClick(Movie movie, Drawable poster);
}
