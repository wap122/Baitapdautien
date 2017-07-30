package film.com.viwafo.example.Fragment;

import android.support.v7.widget.SearchView;
import android.text.TextUtils;
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
    private SearchView searchView;

    public ListFilmFragment(SearchView searchView) {
        super();
        this.searchView = searchView;
    }

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

    public void changeListview() {
        customAdapter = new CustomAdapter(getActivity());
        lvMovies.setAdapter(customAdapter);
//        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)) {
                    customAdapter.getFilter().filter("");
                    lvMovies.clearTextFilter();
                } else {
                    customAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
}
