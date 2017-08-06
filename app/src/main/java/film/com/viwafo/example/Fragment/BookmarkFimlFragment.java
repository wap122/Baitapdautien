package film.com.viwafo.example.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.FavoriteAdapter;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Listener.OnItemListview;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class BookmarkFimlFragment extends BaseFragment implements OnFavoriteClick {

    private static final String SHARED_PREFERENCES_NAME = "Data";

    private ListView lvBookmark;
    private FavoriteAdapter customAdapter;
    private OnFavoriteClick listenner;
    private SharedPreferences sharedPreferences;
    private MainActivity main;

    public BookmarkFimlFragment(Context context, OnFavoriteClick listenner) {
        super();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.context = context;
        this.listenner = listenner;
        if (context instanceof MainActivity) {
            main = (MainActivity) context;
        }
        customAdapter = new FavoriteAdapter(context, listenner, sharedPreferences);
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void mapView(View view) {
        lvBookmark = (ListView) view.findViewById(R.id.lv_bookmark);
    }

    @Override
    protected void mapData() {
        main.changeFavoriteNumber(String.valueOf(customAdapter.getCount()));
        lvBookmark.setAdapter(customAdapter);
        lvBookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnItemListview l = (OnItemListview) getFragmentManager().getFragments().get(0);
                if (customAdapter.getListPosterImange().size() != customAdapter.getCount()) {
                    l.onItemListviewClick((Movie) parent.getItemAtPosition(position),
                            null);
                } else {
                    l.onItemListviewClick((Movie) parent.getItemAtPosition(position),
                            (Drawable) customAdapter.getListPosterImange().get(position));
                }
            }
        });
    }

    @Override
    public void onFavoriteClick() {
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(customAdapter.getListFavorite());
        editor.putString("ListFavorite", json);
        editor.apply();
    }

    public List getListFavorite() {
        return customAdapter.getListFavorite();
    }

    public List getListPosterImange() {
        return customAdapter.getListPosterImange();
    }
}
