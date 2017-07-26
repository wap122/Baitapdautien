package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 26/07/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> listMovie = MovieSqlite.getInstance(null).getAllMovies();

    public CustomAdapter(Context context) {
        super();
        this.context = context;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_row_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.imgPoster = (ImageView) convertView.findViewById(R.id.img_poster);
            viewHolder.tvReleaseDate = (TextView) convertView.findViewById(R.id.tv_edit_releaseday);
            viewHolder.tvVoteAverage = (TextView) convertView.findViewById(R.id.tv_edit_vote);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tv_edit_releaseday);
            viewHolder.imgIsFavorite = (ImageView) convertView.findViewById(R.id.img_favorite);
            viewHolder.imgIsAdult = (ImageView) convertView.findViewById(R.id.img_isadult);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Movie movie = listMovie.get(position);
        viewHolder.tvTitle.setText(movie.getTitle());
//        viewHolder.imgPoster.setImageBitmap();
//        viewHolder.tvReleaseDate = (TextView) convertView.findViewById(R.id.tv_edit_releaseday);
//        viewHolder.tvVoteAverage = (TextView) convertView.findViewById(R.id.tv_edit_vote);
//        viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tv_edit_releaseday);
//        viewHolder.imgIsFavorite = (ImageView) convertView.findViewById(R.id.img_favorite);
//        viewHolder.imgIsAdult = (ImageView) convertView.findViewById(R.id.img_isadult);

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
