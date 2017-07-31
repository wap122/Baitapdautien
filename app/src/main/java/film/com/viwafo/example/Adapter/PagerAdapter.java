package film.com.viwafo.example.Adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhl on 10/07/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listFragmentTitle = new ArrayList<>();

    private Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFrag(Fragment fragment, String title) {
        listFragment.add(fragment);
        listFragmentTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentTitle.get(position);
    }

}
