package film.com.viwafo.example.Fragment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Adapter.CustomAdapterBookMark;
import film.com.viwafo.example.Listener.Listenner;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class BookmarkFimlFragment extends BaseFragment implements Listenner{
    private ListView lvBookmark;
    private List<Movie> listMovie = new ArrayList<>();

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_bookmark_film;
    }

    @Override
    protected void mapView(View view) {
        lvBookmark= (ListView) view.findViewById(R.id.lv_bookmark);
    }

    @Override
    protected void mapData() {
    }

    @Override
    public void setAdapter(Movie movie) {
        listMovie.add(movie);
        CustomAdapterBookMark customAdapter = new CustomAdapterBookMark(getContext(),listMovie);
        lvBookmark.setAdapter(customAdapter);
    }
}
