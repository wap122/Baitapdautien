package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Listener.OnFavotiteClick;
import film.com.viwafo.example.Model.Entity.ListCurrentFilm;
import film.com.viwafo.example.Model.Entity.ListFavorite;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 26/07/2017.
 */

public class CustomAdapterList extends BaseAdapter implements Filterable {

    private List<Movie> listMovie;
    private ArrayList<Movie> arrayListFilter;
    private Context context;
    private ValueFilter valueFilter;
    private ListFavorite favoriteList;
    private OnFavotiteClick listenner;

    public CustomAdapterList(Context context, OnFavotiteClick listenner) {
        super();
        this.context = context;
        listMovie = ListCurrentFilm.getInstance();
        arrayListFilter = ListCurrentFilm.getInstance();
        favoriteList = ListFavorite.getInstance();
        this.listenner = listenner;
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
        viewHolder.tvTitle.setText(movie.getTitle());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterUrl())
                .placeholder(R.drawable.ic_holder)
                .into(viewHolder.imgPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        movie.setDrawable(viewHolder.imgPoster.getDrawable());
                    }

                    @Override
                    public void onError() {
                    }
                });

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
        movie.setId(position);

        viewHolder.imgIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.imgIsFavorite.getDrawable().getConstantState().equals
                        (context.getResources().getDrawable(
                                R.drawable.ic_star_border_black).getConstantState())) {

                    viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_start_selected);
                    favoriteList.add(movie);
                    listenner.onEvent();

                } else {
                    favoriteList.remove(movie);
                    listenner.onEvent();
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
                List<Movie> filterList = new ArrayList<>();
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
