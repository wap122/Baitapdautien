package film.com.viwafo.example.Fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapterList;
import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Listener.OnItemListview;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.ParseData;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment implements OnDatabaseCreated, OnFavoriteClick, OnItemListview {

    private ListView lvMovies;
    private CustomAdapterList customAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnFavoriteClick listenner;
    private DetailFragment detailFragment;
    private MainActivity main;

    public ListFilmFragment() {
        super();
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

    }

    @Override
    protected void mapData() {
        if (getContext() instanceof MainActivity) {
            main = (MainActivity) getContext();
        }
        createListview();
        setupSwipeRefresh();
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                main.getParseData().getDataFormApi(ParseData.urlApiMovie);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void createListview() {
        listenner = (OnFavoriteClick) getFragmentManager().getFragments().get(1);
        customAdapter = new CustomAdapterList(getContext(), listenner);
        lvMovies.setAdapter(customAdapter);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                RelativeLayout rl = (RelativeLayout) view;
                ImageView img = (ImageView) rl.getChildAt(1);
                detailFragment = new DetailFragment(movie, img.getDrawable());
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, detailFragment).addToBackStack(null).commit();
                lvMovies.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void OnCreated(List list) {
        customAdapter.addAll(list);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void OnFavoriteClick() {
        customAdapter.notifyDataSetChanged();
        if (detailFragment != null) {
            detailFragment.onFavoriteClick();
        }
    }

    @Override
    public void OnItemListviewClick(Movie movieData, Drawable poster) {
        lvMovies.setVisibility(View.INVISIBLE);
        detailFragment.onItemListviewClick(movieData, poster);

    }
}
