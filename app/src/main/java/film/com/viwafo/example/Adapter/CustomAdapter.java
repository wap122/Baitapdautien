package film.com.viwafo.example.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 26/07/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> listMovie;
    private MainActivity mainActivity;

    public CustomAdapter(MainActivity activity,List<Movie> list) {
        super();
        this.context= activity.getApplicationContext();
        mainActivity = activity;
        listMovie = list;
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_row_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.imgPoster = (ImageView) convertView.findViewById(R.id.img_poster);
            viewHolder.tvReleaseDate = (TextView) convertView.findViewById(R.id.tv_edit_releaseday);
            viewHolder.tvVoteAverage = (TextView) convertView.findViewById(R.id.tv_edit_vote);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tv_overview);
            viewHolder.imgIsFavorite = (ImageView) convertView.findViewById(R.id.img_favorite);
            viewHolder.imgIsAdult = (ImageView) convertView.findViewById(R.id.img_isadult);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = listMovie.get(position);
//        viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_star_border_black);
        viewHolder.tvTitle.setText(movie.getTitle());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterUrl())
                .placeholder(R.drawable.ic_holder)
                .into(viewHolder.imgPoster);
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
        viewHolder.tvOverview.setText(movie.getOverview());
        if (Boolean.parseBoolean(movie.getIsAdult())) {
            viewHolder.imgIsAdult.setVisibility(View.INVISIBLE);
        }
        viewHolder.imgIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.imgIsFavorite.getDrawable().getConstantState().equals
                        (context.getResources().getDrawable(
                                R.drawable.ic_star_border_black).getConstantState())) {
                    viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_start_selected);
                    movie.setDrawable(viewHolder.imgPoster.getDrawable());
                    mainActivity.addFavorite(movie);
                    mainActivity.changeFavoriteNum();
                } else {
                    viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_star_border_black);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private TextView tvTitle;
        private ImageView imgPoster;
        private TextView tvReleaseDate;
        private TextView tvVoteAverage;
        private TextView tvOverview;
        private ImageView imgIsAdult;
        private ImageView imgIsFavorite;
    }
}
