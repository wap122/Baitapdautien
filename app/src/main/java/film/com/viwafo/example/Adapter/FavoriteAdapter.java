package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Activity.MainActivity;
import film.com.viwafo.example.Listener.OnFavoriteClick;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;
import film.com.viwafo.example.Util.ObjectUtil;

/**
 * Created by minhl on 27/07/2017.
 */

public class FavoriteAdapter extends BaseAdapter {

    private List<Movie> listFavorite;
    private Context context;
    private OnFavoriteClick listenner;
    private List<Drawable> listPosterImange;
    private MainActivity main;

    public FavoriteAdapter(Context context, OnFavoriteClick listenner, SharedPreferences sharedPreferences) {
        super();
        listFavorite = createListFavorte(sharedPreferences);
        listPosterImange = new ArrayList<>();
        this.context = context;
        this.listenner = listenner;
        if (context instanceof MainActivity) {
            main = (MainActivity) context;
        }
    }

    private List<Movie> createListFavorte(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ListFavorite", null);
        if (ObjectUtil.isNull(json)) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<Movie>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public List getListFavorite() {
        return listFavorite;
    }

    @Override
    public int getCount() {
        return listFavorite.size();
    }

    @Override
    public Object getItem(int position) {
        return listFavorite.get(position);
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
        Movie movie = listFavorite.get(position);
        viewHolder.tvTitle.setText(movie.getTitle());
        if ((listFavorite.size() == listPosterImange.size()) && (!listPosterImange.isEmpty())) {
            viewHolder.imgPoster.setImageDrawable(listPosterImange.get(position));
        } else {
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .placeholder(R.drawable.ic_holder).into(viewHolder.imgPoster);
        }
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvVoteAverage.setText(movie.getVoteAverage() + "/10");
        viewHolder.tvOverview.setText(movie.getOverview());
        if (movie.getAdult()) {
            viewHolder.imgIsAdult.setVisibility(View.INVISIBLE);
        }
        viewHolder.imgIsFavorite.setImageResource(R.drawable.ic_start_selected);

        viewHolder.imgIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFavorite.remove(position);
                if (!listPosterImange.isEmpty()) {
                    listPosterImange.remove(position);
                }
                listenner.onFavoriteClick();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        main.changeFavoriteNumber((String.valueOf(getCount())));
    }

    public List getListPosterImange() {
        return listPosterImange;
    }

    private class ViewHolder {
        TextView tvTitle, tvReleaseDate, tvVoteAverage, tvOverview;
        ImageView imgPoster, imgIsAdult, imgIsFavorite;
    }
}
