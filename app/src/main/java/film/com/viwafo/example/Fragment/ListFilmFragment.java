package film.com.viwafo.example.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapterList;
import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.ListCurrentFilm;
import film.com.viwafo.example.Model.Entity.ListFavorite;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment implements OnDatabaseCreated, OnFavotiteClick {

    private ListView lvMovies;
    private CustomAdapterList customAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnFavotiteClick listenner;
    private DetailFragment detailFragment;

    public ListFilmFragment(OnFavotiteClick listenner) {
        super();
        this.listenner = listenner;
    }

    public ListView getLvMovies() {
        return lvMovies;
    }

    public CustomAdapterList getCustomAdapter() {
        return customAdapter;
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_list_film;
    }

    @Override
    protected void mapView(View view) {
        lvMovies = (ListView) view.findViewById(R.id.lv_movies);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ListFavorite.getInstance().favorite = new boolean[20];
                MainActivity.parseData.getDataFormApi();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void mapData() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = ListCurrentFilm.getInstance().get(position);
                detailFragment = new DetailFragment(movie, listenner);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, detailFragment).addToBackStack(null).commit();
                lvMovies.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void changeListview() {
        customAdapter = new CustomAdapterList(getContext(), listenner);
        lvMovies.setAdapter(customAdapter);
    }

    @Override
    public void OnCreated() {
        changeListview();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEvent() {
        MainActivity.tvFavoriteNum.setText(String.valueOf(ListFavorite.getInstance().size()));
        customAdapter.notifyDataSetChanged();
        if (detailFragment != null) {
            detailFragment.onFavoriteClick();
        }
    }

    public boolean allowBackPressed() {
        if (detailFragment == null) {
            return true;
        }
        getChildFragmentManager().beginTransaction().remove(detailFragment).commit();
        detailFragment = null;
        customAdapter.notifyDataSetChanged();
        lvMovies.setVisibility(View.VISIBLE);
        return false;
    }
}
