package film.com.viwafo.example.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import film.com.viwafo.example.R;

/**
 * Created by minhl on 06/08/2017.
 */

public class ActorViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPoster;
    public TextView tvName;

    public ActorViewHolder(View itemView) {
        super(itemView);
        imgPoster = (ImageView) itemView.findViewById(R.id.img_poster);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
    }
}
