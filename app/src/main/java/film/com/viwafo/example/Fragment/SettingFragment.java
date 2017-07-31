package film.com.viwafo.example.Fragment;


import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Model.Entity.ListCurrentFilm;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {
    private Button btnCategory;

    public SettingFragment() {
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void mapView(View view) {
        btnCategory = (Button) view.findViewById(R.id.btn_category);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryMenu();
            }
        });
    }

    @Override
    protected void mapData() {

    }

    private void showCategoryMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), btnCategory);
        popupMenu.getMenuInflater().inflate(R.menu.menu_category, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity mainActivity = (MainActivity) getActivity();
                switch (item.getOrder()) {
                    case 0: {
                        String rank = MovieSqlite.COLUMN_MOVIE_VOTE_AVERAGE;
                        ListCurrentFilm.getInstance().clear();
                        ListCurrentFilm.getInstance().addAll(MovieSqlite.getInstance(null).sortWithRank(rank));
                        break;
                    }
                    case 1: {
                        ListCurrentFilm.getInstance().clear();
                        ListCurrentFilm.getInstance().addAll(MovieSqlite.getInstance(null).getAllMovies());
                    }
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
