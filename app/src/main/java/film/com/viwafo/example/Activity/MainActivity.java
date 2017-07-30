package film.com.viwafo.example.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
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
import film.com.viwafo.example.Util.UtilPermissions;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by macintoshhd on 7/23/17.
 */

public class MainActivity extends BaseActivity {

    private static final int REQUEST_PERMISSIONS = 1;

    private TabLayout tabLayout;
    private BookmarkFimlFragment bookmarkFimlFragment;
    private ListFilmFragment listFilmFragment;
    private TextView tvFavoriteNum;
    private SearchView searchView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermission();
        createView();
        createViewPager();
        customViewpagerTabs();
        customViewpagerTab2();
        getDatatFromServer();
    }

    public void changeBookmarkFragment(Movie movie) {
        if (movie != null) {
            FavoriteList.getInstance().remove(movie);
        }

        bookmarkFimlFragment.changeAdapter();
        changeFavoriteNum();
    }

    private void createView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        MovieSqlite movieSqlite = MovieSqlite.getInstance(this);
        movieSqlite.onUpgrade(movieSqlite.getWritableDatabase(), 1, 2);
        bookmarkFimlFragment = new BookmarkFimlFragment();
        listFilmFragment = new ListFilmFragment(searchView);
        searchView = (SearchView) findViewById(R.id.search_view);
    }

    private void askForPermission() {
        String[] PERMISSION = {INTERNET, ACCESS_NETWORK_STATE};
        if (!UtilPermissions.hasPermissions(this, PERMISSION)) {
            ActivityCompat.requestPermissions(this, PERMISSION, REQUEST_PERMISSIONS);
        }
    }

    private void getDatatFromServer() {
        if (!checkConnection()) {
            return;
        }
        String urlApi = "http://api.themoviedb.org/3/movie/popular?api_key=01d6eaad3bb353d05c20716701c51937&page=";
        ParseData parseData = new ParseData(this);
        parseData.getDataFormApi(urlApi);
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
//        Toast.makeText(this, "Xài Wifi chùa dc rồi đó", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void customViewpagerTab2() {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab_2,null);
        tvFavoriteNum = (TextView) view.findViewById(R.id.tv_number);
        tvFavoriteNum.bringToFront();
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

    private void createViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addFrag(listFilmFragment, "Tab1");
        pagerAdapter.addFrag(bookmarkFimlFragment, "Tab2");
        pagerAdapter.addFrag(new SettingFragment(), "Tab3");
        pagerAdapter.addFrag(new SettingFragment(), "Tab4");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(4);
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

    public void changeFilmFragment() {
        listFilmFragment.changeListview();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
            Toast.makeText(this, "Ko cấp quyền sao chạy dc :(", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void onPermissionsGranted(int requestCode) {
        Toast.makeText(this, "Đã dc cấp quyền", Toast.LENGTH_SHORT).show();
    }

}
