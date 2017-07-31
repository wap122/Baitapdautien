package film.com.viwafo.example.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import film.com.viwafo.example.Activity.BaseActivity;

/**
 * Created by macintoshhd on 7/23/17.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    BaseActivity activity;

    protected abstract int getResIdLayout();

    protected abstract void mapView(View view);

    protected abstract void mapData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getResIdLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
