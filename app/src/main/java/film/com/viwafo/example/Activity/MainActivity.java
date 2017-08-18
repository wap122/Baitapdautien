package film.com.viwafo.example.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import film.com.viwafo.example.Adapter.PagerAdapter;
import film.com.viwafo.example.Fragment.AboutFragment;
import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Fragment.ListFilmFragment;
import film.com.viwafo.example.Fragment.SettingFragment;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.Model.ParseData;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Util.UtilPermissions;
import retrofit2.Retrofit;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WAKE_LOCK;

/**
 * Created by macintoshhd on 7/23/17.
 */

public class MainActivity extends BaseActivity {

    private static final int REQUEST_PERMISSIONS = 1;

    private TextView tvFavoriteNum;
    private ParseData parseData;
    private TabLayout tabLayout;
    private BookmarkFimlFragment bookmarkFimlFragment;
    private ListFilmFragment listFilmFragment;
    private SearchView searchView;
    private ViewPager viewPager;
    private ImageView imgGrid;

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
        setupView();

    }

    private void setupView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)) {
                    listFilmFragment.getCustomAdapter().getFilter().filter("");
                    listFilmFragment.getLvMovies().clearTextFilter();
                } else {
                    listFilmFragment.getCustomAdapter().getFilter().filter(newText);
                }
                return true;
            }
        });

        imgGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgGrid.getDrawable().getConstantState().equals
                        (getResources().getDrawable(
                                R.drawable.ic_grid_on_white).getConstantState())) {
                    imgGrid.setImageResource(R.drawable.ic_list_white);
                    listFilmFragment.changeListViewToGrid();
                } else {
                    imgGrid.setImageResource(R.drawable.ic_grid_on_white);
                    listFilmFragment.changeListViewtoList();
                }
            }
        });
    }


    private void createView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        MovieSqlite movieSqlite = MovieSqlite.getInstance(this);
        movieSqlite.onUpgrade(movieSqlite.getWritableDatabase(), 1, 2);
        listFilmFragment = new ListFilmFragment();
        bookmarkFimlFragment = new BookmarkFimlFragment(this, listFilmFragment);
        searchView = (SearchView) findViewById(R.id.search_view);
        imgGrid = (ImageView) findViewById(R.id.img_grid);
    }

    private void askForPermission() {
        String[] PERMISSION = {INTERNET, ACCESS_NETWORK_STATE, WAKE_LOCK};
        if (!UtilPermissions.hasPermissions(this, PERMISSION)) {
            ActivityCompat.requestPermissions(this, PERMISSION, REQUEST_PERMISSIONS);
        }
    }

    private void getDatatFromServer() {
        if (!checkConnection()) {
            return;
        }
        parseData = new ParseData();
        parseData.getListMovie(listFilmFragment);
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
//        Toast.makeTexyt(this, "Xài Wifi chùa dc rồi đó", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void customViewpagerTab2() {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab_2, null);
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
        pagerAdapter.addFrag(new SettingFragment(listFilmFragment), "Tab3");
        pagerAdapter.addFrag(new AboutFragment(), "Tab4");
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
        FragmentManager f = listFilmFragment.getChildFragmentManager();
        if (f.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }

        if (f.getBackStackEntryCount() == 1) {
            f.popBackStack();
            listFilmFragment.getCustomAdapter().notifyDataSetChanged();
            listFilmFragment.getLvMovies().setVisibility(View.VISIBLE);
            listFilmFragment.setDetailFragmentNull();
        } else {
            f.popBackStack();
        }

    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
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

    public void changeFavoriteNumber(String result) {
        tvFavoriteNum.setText(result);
    }

    public ParseData getParseData() {
        return parseData;
    }
}
