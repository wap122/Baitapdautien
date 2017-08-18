package film.com.viwafo.example.Fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Adapter.ActorAdapter;
import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Receiver.AlarmReceiver;
import film.com.viwafo.example.Util.ObjectUtil;

public class DetailFragment extends BaseFragment implements OnDatabaseCreated {

    private Movie movieData;
    private ImageView imgFavorite, imgPoster;
    private TextView tvRelease, tvRating, tvOverview;
    private Button btnReminder;
    private OnFavoriteClick listenner;
    private List<Movie> listFavorite;
    private List<Drawable> listPosterImage;
    private Drawable poster;
    private RecyclerView rvActor;
    private ActorAdapter adapterActor;

    public DetailFragment(Movie movie, Drawable poster) {
        super();
        this.movieData = movie;
        this.poster = poster;
    }

    @Override
    protected int getResIdLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void mapView(View view) {
        imgFavorite = (ImageView) view.findViewById(R.id.img_favorite);
        imgPoster = (ImageView) view.findViewById(R.id.img_poster);
        tvRelease = (TextView) view.findViewById(R.id.tv_releaseday);
        tvRating = (TextView) view.findViewById(R.id.tv_rating);
        tvOverview = (TextView) view.findViewById(R.id.tv_overview);
        btnReminder = (Button) view.findViewById(R.id.btn_reminder);
        rvActor = (RecyclerView) view.findViewById(R.id.rv_actor_name);

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
                calendar.set(year, month, dayOfMonth);
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
        getActivity().setTitle("DetailFragment");
        setupRecycleView();
        BookmarkFimlFragment b = (BookmarkFimlFragment) getParentFragment()
                .getFragmentManager().getFragments().get(1);
        this.listenner = b;
        listFavorite = b.getListFavorite();
        listPosterImage = b.getListPosterImange();

        if (!ObjectUtil.isNull(poster)) {
            imgPoster.setImageDrawable(poster);
        } else {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w500" + movieData.getPosterPath())
                    .placeholder(R.drawable.ic_holder).into(imgPoster);
        }

        tvRelease.setText(movieData.getReleaseDate());
        tvRating.setText(movieData.getVoteAverage() + "/10");
        tvOverview.setText(movieData.getOverview());
        if (takePosition(movieData) != -1) {
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
                    listPosterImage.add(imgPoster.getDrawable());
                    listFavorite.add(movieData);
                    listenner.onFavoriteClick();
                } else {
                    listPosterImage.remove(imgPoster.getDrawable());
                    listFavorite.remove(takePosition(movieData));
                    listenner.onFavoriteClick();
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

    private void setupRecycleView() {
        rvActor.setHasFixedSize(true);
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getContext());
        rvLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvActor.setLayoutManager(rvLayoutManager);
        adapterActor = new ActorAdapter(getContext());
        rvActor.setAdapter(adapterActor);
        MainActivity main = (MainActivity) getActivity();
        main.getParseData().getNameActorMovie(movieData.getId(), this);
    }

    private int takePosition(Movie movie) {
        for (Movie m : listFavorite) {
            if (m.getTitle().contentEquals(movie.getTitle())) {
                return listFavorite.indexOf(m);
            }
        }
        return -1;
    }

    public String getTitle() {
        return movieData.getTitle();
    }

    @Override
    public void onCreated(List list) {
        adapterActor.add(list);
    }
}
