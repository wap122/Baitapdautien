package film.com.viwafo.example.Fragment;

import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.zip.Inflater;

import film.com.viwafo.example.R;

/**
 * Created by macintoshhd on 7/23/17.
 */
public class ListFilmFragment extends BaseFragment {

    private Button btn;
    private List<String> list;

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_list_film;
    }

    @Override
    protected void mapView(View view) {
        btn = (Button) view.findViewById(R.id.btn);
        btn.setText("Óc chó");
    }

    @Override
    protected void mapData() {

    }
}
