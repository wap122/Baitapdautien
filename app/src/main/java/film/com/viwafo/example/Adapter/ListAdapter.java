package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import film.com.viwafo.example.Fragment.BookmarkFimlFragment;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 26/07/2017.
 */

public class ListAdapter extends BaseAdapter implements Filterable {

    private List<Movie> listMovie;
    private ArrayList<Movie> arrayListFilter;
    private Context context;
    private ValueFilter valueFilter;
    private List<Movie> listFavorite;
    private OnFavoriteClick listenner;
    private List<Drawable> listPosterImage;

    public ListAdapter(Context context, OnFavoriteClick listenner) {
        super();
        this.context = context;
        listMovie = new ArrayList<>();
        arrayListFilter = new ArrayList<>();
        if (listenner instanceof BookmarkFimlFragment) {
            BookmarkFimlFragment bookmarkFimlFragment = (BookmarkFimlFragment) listenner;
            listFavorite = bookmarkFimlFragment.getListFavorite();
            listPosterImage = bookmarkFimlFragment.getListPosterImange();
        }
        this.listenner = listenner;
    }

    public List getListMovie() {
        return listMovie;
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return listMovie.get(position);
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
            viewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.img_favorite);
            viewHolder.imgIsAdult = (ImageView) convertView.findViewById(R.id.img_isadult);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = listMovie.get(position);
        viewHolder.tvTitle.setText(movie.getTitle());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterUrl())
                .placeholder(R.drawable.ic_holder).into(viewHolder.imgPoster);
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
        viewHolder.tvOverview.setText(movie.getOverview());
        if (takePosition(movie) != -1) {
            viewHolder.imgFavorite.setImageResource(R.drawable.ic_start_selected);
        } else {
            viewHolder.imgFavorite.setImageResource(R.drawable.ic_star_border_black);
        }
        if (Boolean.parseBoolean(movie.getIsAdult())) {
            viewHolder.imgIsAdult.setVisibility(View.INVISIBLE);
        }

        viewHolder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.imgFavorite.getDrawable().getConstantState().equals
                        (context.getResources().getDrawable(
                                R.drawable.ic_star_border_black).getConstantState())) {
                    viewHolder.imgFavorite.setImageResource(R.drawable.ic_start_selected);
                    listPosterImage.add(viewHolder.imgPoster.getDrawable());
                    listFavorite.add(movie);
                    listenner.onFavoriteClick();
                } else {
                    listPosterImage.remove(viewHolder.imgPoster.getDrawable());
                    listFavorite.remove(takePosition(movie));
                    listenner.onFavoriteClick();
                    viewHolder.imgFavorite.setImageResource(R.drawable.ic_star_border_black);
                }
            }
        });

        return convertView;
    }

    private int takePosition(Movie movie) {
        for (Movie m : listFavorite) {
            if (m.getTitle().contentEquals(movie.getTitle())) {
                return listFavorite.indexOf(m);
            }
        }
        return -1;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public void addAll(List list) {
        listMovie.clear();
        listMovie.addAll(list);
        arrayListFilter = (ArrayList<Movie>) listMovie;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView tvTitle, tvReleaseDate, tvVoteAverage, tvOverview;
        private ImageView imgPoster, imgIsAdult, imgFavorite;
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
