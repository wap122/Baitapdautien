package film.com.viwafo.example.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapterList;
import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment implements OnDatabaseCreated, OnFavotiteClick {

    private ListView lvMovies;
    private CustomAdapterList customAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnFavotiteClick listenner;

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
    }

    private void fetchTimelineAsync(int page) {


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
        MainActivity.tvFavoriteNum.setText(String.valueOf(FavoriteList.getInstance().size()));
        customAdapter.notifyDataSetChanged();
    }
}
