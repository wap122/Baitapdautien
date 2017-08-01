package film.com.viwafo.example.Fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.ListFavorite;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Receiver.AlarmReceiver;

public class DetailFragment extends BaseFragment {

    private Movie movieData;
    private ImageView imgFavorite, imgPoster;
    private TextView tvRelease, tvRating, tvOverview;
    private Button btnReminder;
    private ListFavorite listFavorite;
    private OnFavotiteClick listenner;

    public DetailFragment(Movie movie, OnFavotiteClick listenner) {
        super();
        this.movieData = movie;
        this.listenner = listenner;
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void mapView(View view) {
        listFavorite = ListFavorite.getInstance();
        imgFavorite = (ImageView) view.findViewById(R.id.img_favorite);
        imgPoster = (ImageView) view.findViewById(R.id.img_poster);
        tvRelease = (TextView) view.findViewById(R.id.tv_releaseday);
        tvRating = (TextView) view.findViewById(R.id.tv_rating);
        tvOverview = (TextView) view.findViewById(R.id.tv_overview);
        btnReminder = (Button) view.findViewById(R.id.btn_reminder);

        if (listFavorite.isFavorite(movieData.getId())) {
            imgFavorite.setImageResource(R.drawable.ic_start_selected);
        } else {
            imgFavorite.setImageResource(R.drawable.ic_star_border_black);
        }
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFavorite.getDrawable().getConstantState().equals
                        (getContext().getResources().getDrawable(
                                R.drawable.ic_star_border_black).getConstantState())) {

                    imgFavorite.setImageResource(R.drawable.ic_start_selected);
                    listFavorite.add(movieData);
                    listenner.onEvent();
                } else {
                    listFavorite.remove(movieData);
                    listenner.onEvent();
                    imgFavorite.setImageResource(R.drawable.ic_star_border_black);
                }
            }
        });
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(year, month, dayOfMonth, 12, 0);
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 0, alarmIntent);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        dialog.show();
    }

    public void onFavoriteClick() {
        imgFavorite.setImageResource(R.drawable.ic_star_border_black);
    }

    @Override
    protected void mapData() {
        imgPoster.setImageDrawable(movieData.getDrawable());
        tvRelease.setText(movieData.getReleaseDate());
        tvRating.setText(movieData.getVoteAverage() + "/10");
        tvOverview.setText(movieData.getOverview());

    }
}
