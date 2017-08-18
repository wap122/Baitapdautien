package film.com.viwafo.example.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Model.Entity.Actor;
import film.com.viwafo.example.R;
import film.com.viwafo.example.View.ActorViewHolder;

/**
 * Created by minhl on 06/08/2017.
 */

public class ActorAdapter extends RecyclerView.Adapter<ActorViewHolder> {

    private List<Actor> listActor;
    private Context context;

    public ActorAdapter(Context context) {
        this.context = context;
        this.listActor = new ArrayList<>();
    }

    @Override
    public ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_actor, parent, false);
        ActorViewHolder actorViewHolder = new ActorViewHolder(view);
        return actorViewHolder;
    }

    @Override
    public void onBindViewHolder(ActorViewHolder holder, int position) {
        Actor actor = listActor.get(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + actor.getProfilePath().toString())
                .placeholder(R.drawable.ic_holder).into(holder.imgPoster);
        holder.tvName.setText(actor.getName());
    }

    @Override
    public int getItemCount() {
        return listActor.size();
    }

    public void add(List list) {
        this.listActor = list;
        notifyDataSetChanged();
    }
}
