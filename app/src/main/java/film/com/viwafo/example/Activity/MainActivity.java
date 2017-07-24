package film.com.viwafo.example.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import film.com.viwafo.example.Fragment.Fragment1;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Util.PagerAdapter;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        createViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
//        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        createTabIcons();
        viewPager.setOffscreenPageLimit(3);
    }

    private void createTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("ONE");
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Tab 2");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_adult, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }

    private void createViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);
        pagerAdapter.addFrag(new Fragment1(),"Tab1");
        pagerAdapter.addFrag(new Fragment1(),"Tab2");
        viewPager.setAdapter(pagerAdapter);
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
}
