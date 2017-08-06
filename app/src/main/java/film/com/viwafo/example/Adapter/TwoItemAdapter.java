package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Listener.OnItemListview;
import film.com.viwafo.example.Model.Entity.Movie;
import film.com.viwafo.example.R;

/**
 * Created by minhl on 05/08/2017.
 */

public class TwoItemAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> listMovie;
    private OnItemListview listenner;

    public TwoItemAdapter(Context context, OnItemListview listenner) {
        super();
        listMovie = new ArrayList<>();
        this.context = context;
        this.listenner = listenner;

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
        return listMovie.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_row_2item, null);
            viewHolder = new ViewHolder();
            viewHolder.imgPoster1 = (ImageView) convertView.findViewById(R.id.img_poster1);
            viewHolder.imgPoster2 = (ImageView) convertView.findViewById(R.id.img_poster2);
            viewHolder.tvTitle1 = (TextView) convertView.findViewById(R.id.tv_title1);
            viewHolder.tvTitle2 = (TextView) convertView.findViewById(R.id.tv_title2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Movie movie1 = (Movie) getItem(position * 2);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie1.getPosterUrl())
                .placeholder(R.drawable.ic_holder).into(viewHolder.imgPoster1);
        viewHolder.imgPoster1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onItemListviewClick(movie1, viewHolder.imgPoster1.getDrawable());
            }
        });
        viewHolder.tvTitle1.setText(movie1.getTitle());


        final Movie movie2 = (Movie) getItem(position * 2 + 1);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie2.getPosterUrl())
                .placeholder(R.drawable.ic_holder).into(viewHolder.imgPoster2);
        viewHolder.tvTitle2.setText(movie2.getTitle());

        viewHolder.imgPoster2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onItemListviewClick(movie2, viewHolder.imgPoster2.getDrawable());
            }
        });
        return convertView;
    }

    public void addAll(List list) {
        listMovie.clear();
        listMovie.addAll(list);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView imgPoster1, imgPoster2;
        TextView tvTitle1, tvTitle2;
    }
}
