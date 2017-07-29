package film.com.viwafo.example.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Model.Entity.FavoriteList;
import film.com.viwafo.example.Model.Entity.ListCurrentFilm;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.Model.Manager.MovieSqlite;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 26/07/2017.
 */

public class CustomAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Movie> listMovie;
    private ArrayList<Movie> arrayListFilter;
    private MainActivity mainActivity;
    private ValueFilter valueFilter;
    private FavoriteList favoriteList;

    public CustomAdapter(Activity activity) {
        super();
        this.context = activity;
        mainActivity = (MainActivity) activity;
        listMovie = ListCurrentFilm.getInstance();
        arrayListFilter = (ArrayList<Movie>) listMovie;
        favoriteList = FavoriteList.getInstance();
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
        if (favoriteList.isFavorite(position)) {
            viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_start_selected);
        } else {
            viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_star_border_black);
        }

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
                    favoriteList.setFavorite(position, true);
                    movie.setId(position);
                    mainActivity.addFavorite(movie);
                    mainActivity.changeFavoriteNum();
                } else {
                    favoriteList.setFavorite(position, false);
                    mainActivity.changeBookmarkFragment(movie);
                    viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_star_border_black);
                }
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private static class ViewHolder {
        private TextView tvTitle, tvReleaseDate, tvVoteAverage, tvOverview;
        private ImageView imgPoster, imgIsAdult, imgIsFavorite;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Movie> filterList = new ArrayList<>();
                for (int i = 0; i < arrayListFilter.size(); i++) {
                    if ((arrayListFilter.get(i).getTitle().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Movie movie;
                        movie = arrayListFilter.get(i);
                        filterList.add(movie);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = arrayListFilter.size();
                results.values = arrayListFilter;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listMovie = (List<Movie>) results.values;
            notifyDataSetChanged();
        }
    }
}
