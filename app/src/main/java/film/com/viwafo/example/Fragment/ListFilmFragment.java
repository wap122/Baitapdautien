package film.com.viwafo.example.Fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.ListAdapter;
import film.com.viwafo.example.Adapter.TwoItemAdapter;
import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Listener.OnItemListview;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment implements OnDatabaseCreated, OnFavoriteClick, OnItemListview {

    private ListView lvMovies;
    private ListAdapter customAdapter;
    private TwoItemAdapter twoItemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DetailFragment detailFragment;
    private OnFavoriteClick listenner;
    private MainActivity main;

    public ListFilmFragment() {
        super();
    }

    public ListView getLvMovies() {
        return lvMovies;
    }

    public ListAdapter getCustomAdapter() {
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
                main.getParseData().getListMovie(ListFilmFragment.this);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void createListview() {
        listenner = (OnFavoriteClick) getFragmentManager().getFragments().get(1);
        twoItemAdapter = new TwoItemAdapter(getContext(), this);
        customAdapter = new ListAdapter(getContext(), listenner);
        changeListViewtoList();
    }

    @Override
    public void onCreated(List list) {
        customAdapter.addAll(list);
        twoItemAdapter.addAll(list);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFavoriteClick() {
        customAdapter.notifyDataSetChanged();
        if (detailFragment != null) {
            detailFragment.onFavoriteClick();
        }
    }

    @Override
    public void onItemListviewClick(Movie movieData, Drawable poster) {
        lvMovies.setVisibility(View.INVISIBLE);
        if (getChildFragmentManager().getBackStackEntryCount() == 1) {
            if (detailFragment.getTitle().contentEquals(movieData.getTitle())) {
                return;
            }
        }
        detailFragment = new DetailFragment(movieData, poster);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_container, detailFragment).addToBackStack(null).commit();

    }

    public void changeListViewToGrid() {
        lvMovies.setAdapter(twoItemAdapter);
        lvMovies.setOnItemClickListener(null);
    }

    public void setDetailFragmentNull() {
        detailFragment = null;
    }

    public void changeListViewtoList() {
        lvMovies.setAdapter(customAdapter);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvMovies.setVisibility(View.INVISIBLE);
                Movie movie = (Movie) parent.getItemAtPosition(position);
                CardView cardView = (CardView) view;
                RelativeLayout rl = (RelativeLayout) cardView.getChildAt(0);
                ImageView img = (ImageView) rl.getChildAt(1);
                onItemListviewClick(movie, img.getDrawable());
            }
        });
    }
}
