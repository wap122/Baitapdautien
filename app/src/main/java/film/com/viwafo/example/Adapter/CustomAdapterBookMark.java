package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 27/07/2017.
 */

public class CustomAdapterBookMark extends BaseAdapter {

    private Context context;
    private List<Movie> listMovie = new ArrayList<>();
    private BookmarkFimlFragment fragment;
    private MainActivity mainActivity;

    public CustomAdapterBookMark(MainActivity mainActivity, BookmarkFimlFragment fragment) {
        super();
        this.context = mainActivity.getApplicationContext();
        this.listMovie = FavoriteList.getInstance();
        this.fragment = fragment;
        this.mainActivity = mainActivity;
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_row_listview, null);
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

        Movie movie = listMovie.get(position);

        viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_star_border_black);

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.imgPoster.setImageDrawable(movie.getDrawable());
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
        viewHolder.tvOverview.setText(movie.getOverview());
        if (Boolean.parseBoolean(movie.getIsAdult())) {
            viewHolder.imgIsAdult.setVisibility(View.INVISIBLE);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.img_favorite);
        img.setImageResource(R.drawable.ic_start_selected);

        viewHolder.imgIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList.getInstance().remove(position);
                fragment.changeAdapter();
                mainActivity.changeFavoriteNum();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView imgPoster;
        TextView tvReleaseDate;
        TextView tvVoteAverage;
        TextView tvOverview;
        ImageView imgIsAdult;
        ImageView imgIsFavorite;
    }
}
