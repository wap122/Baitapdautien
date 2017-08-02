package film.com.viwafo.example.Fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapterBookMark;
import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.ListFavorite;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class BookmarkFimlFragment extends BaseFragment implements OnFavotiteClick {

    private ListView lvBookmark;
    private CustomAdapterBookMark customAdapter;
    private OnFavotiteClick listenner;
    private DetailFragment detailFragment;

    public BookmarkFimlFragment() {
        super();
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_bookmark_film;
    }

    @Override
    protected void mapView(View view) {
        lvBookmark = (ListView) view.findViewById(R.id.lv_bookmark);
    }

    @Override
    protected void mapData() {
        changeAdapter();
        lvBookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = ListFavorite.getInstance().get(position);
                detailFragment = new DetailFragment(movie, listenner);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.fl_containerbookmark, detailFragment).addToBackStack(null).commit();
                lvBookmark.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void changeAdapter() {
        customAdapter = new CustomAdapterBookMark(getContext(), listenner);
        lvBookmark.setAdapter(customAdapter);
    }

    @Override
    public void onEvent() {
        customAdapter.notifyDataSetChanged();
        MainActivity.tvFavoriteNum.setText(String.valueOf(ListFavorite.getInstance().size()));
    }
    public void setListenner(OnFavotiteClick listenner) {
        this.listenner = listenner;
    }

    public boolean allowBackPressed() {
        if (detailFragment == null) {
            return true;
        }
        getChildFragmentManager().beginTransaction().remove(detailFragment).commit();
        detailFragment = null;
        customAdapter.notifyDataSetChanged();
        lvBookmark.setVisibility(View.VISIBLE);
        return false;
    }
}
