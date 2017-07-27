package film.com.viwafo.example.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Fragment.ListFilmFragment;
import film.com.viwafo.example.Fragment.SettingFragment;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.Model.ParseData;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Adapter.PagerAdapter;

/**
 * Created by macintoshhd on 7/23/17.
 */

public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;
    private BookmarkFimlFragment bookmarkFimlFragment;
    private ListFilmFragment listFilmFragment;
    private TextView tvFavoriteNum;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        MovieSqlite movieSqlite = MovieSqlite.getInstance(this);
        bookmarkFimlFragment = new BookmarkFimlFragment();
        listFilmFragment = new ListFilmFragment();
        searchView = (SearchView) findViewById(R.id.search_view);

        setupSearchView();
        createViewPager(viewPager);
        customViewpagerTabs();
        customViewpagerTab2();
        getDatatFromServer();
    }

    private void setupSearchView() {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    private void getDatatFromServer() {
        if (!checkConnection()) {
            return;
        }
        ParseData.getDataFormApi();

    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "Mạng chưa bật", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Mạng chưa đc kết nốt", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Mạng sida rồi", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Xài Wifi chùa dc rồi đó", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void customViewpagerTab2() {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab_2,null);
        TextView tv = (TextView) view.findViewById(R.id.tv_number);
        tv.bringToFront();
        tvFavoriteNum = tv;
        tabLayout.getTabAt(1).setCustomView(view);

    }

    private void customViewpagerTabs() {
        createTabIcons(0, R.drawable.ic_home, "Movies");
        createTabIcons(2, R.drawable.ic_settings, "Setting");
        createTabIcons(3, R.drawable.ic_info, "About");

    }

    private void createTabIcons(int position, int idDrawable, String label) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        img.setImageResource(idDrawable);
        tv.setText(label);
        tabLayout.getTabAt(position).setCustomView(view);
    }

    private void createViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFrag(listFilmFragment, "Tab1");
        pagerAdapter.addFrag(bookmarkFimlFragment, "Tab2");
        pagerAdapter.addFrag(new SettingFragment(), "Tab3");
        pagerAdapter.addFrag(new ListFilmFragment(), "Tab4");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Handle button back
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }
    public void addFavorite(Movie movie) {
        FavoriteList.getInstance().add(movie);
        bookmarkFimlFragment.changeAdapter();
    }

    public void changeFavoriteNum() {
        tvFavoriteNum.setText(String.valueOf(FavoriteList.getInstance().size()));
    }
    public void changeFilmFragment(List<Movie> list) {
        listFilmFragment.changeListviewWithSort(list);
    }

}
