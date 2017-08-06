package film.com.viwafo.example.Fragment;


import android.app.Dialog;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    private TextView tvCategory;
    private OnDatabaseCreated listenner;
    private Dialog dlCategory;

    public SettingFragment(OnDatabaseCreated listenner) {
        this.listenner = listenner;
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void mapView(View view) {
        tvCategory = (TextView) view.findViewById(R.id.tv_category);
        dlCategory = new Dialog(getContext());
        dlCategory.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlCategory.setContentView(R.layout.dialog_category);
        RadioGroup radioGroup = (RadioGroup) dlCategory.findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                String rank = null;
                switch (checkedId) {
                    case 1: {
                        rank = MovieSqlite.COLUMN_MOVIE_VOTE_AVERAGE;
                        break;
                    }
                    case 2: {
                        rank = MovieSqlite.COLUMN_MOVIE_TITLE;
                        break;
                    }
                    case 3: {
                        Toast.makeText(getContext(), "Ahihi đồ ngốc", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                listenner.onCreated(MovieSqlite.getInstance(null).sortWithRank(rank));
                dlCategory.dismiss();
            }
        });
    }

    @Override
    protected void mapData() {
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlCategory.show();
            }
        });
    }
}
