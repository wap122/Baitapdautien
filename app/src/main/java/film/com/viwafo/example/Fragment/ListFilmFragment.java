package film.com.viwafo.example.Fragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.zip.Inflater;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapter;
import film.com.viwafo.example.Listener.Listenner;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment {
    private ListView lvMovies;
    private CustomAdapter customAdapter;

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_list_film;
    }

    @Override
    protected void mapView(View view) {
        lvMovies = (ListView) view.findViewById(R.id.lv_movies);
    }

    @Override
    protected void mapData() {
    }

    public void changeListview(List<Movie> list) {
        customAdapter = new CustomAdapter(getActivity(), list);
        lvMovies.setAdapter(customAdapter);
    }

    public CustomAdapter getCustomAdapter() {
        return customAdapter;
    }

    public ListView getListview() {
        return lvMovies;
    }
}
