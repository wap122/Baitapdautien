package film.com.viwafo.example.Fragment;

import android.view.View;
import android.widget.ListView;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.CustomAdapterBookMark;
import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class BookmarkFimlFragment extends BaseFragment implements OnFavotiteClick {

    private ListView lvBookmark;
    private CustomAdapterBookMark customAdapter;
    private OnFavotiteClick listenner;

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
    }

    public void changeAdapter() {
        customAdapter = new CustomAdapterBookMark(getContext(), listenner);
        lvBookmark.setAdapter(customAdapter);
    }

    @Override
    public void onEvent() {
        customAdapter.notifyDataSetChanged();
        MainActivity.tvFavoriteNum.setText(String.valueOf(FavoriteList.getInstance().size()));
    }

    public void setListenner(OnFavotiteClick listenner) {
        this.listenner = listenner;
    }
}
