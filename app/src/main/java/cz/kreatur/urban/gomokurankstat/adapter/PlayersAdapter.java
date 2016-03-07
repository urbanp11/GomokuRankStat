package cz.kreatur.urban.gomokurankstat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cz.kreatur.urban.gomokurankstat.R;
import cz.kreatur.urban.gomokurankstat.ladder.model.Player;

/**
 * Created by Petr Urban on 07.03.16.
 */
public class PlayersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Player> players;

    public PlayersAdapter() {
    }

    public void setData(List<Player> players) {
        this.players = players;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_player, parent, false);

        PlayerViewHolder vh = new PlayerViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PlayerViewHolder) holder).bind(getPlayer(position), position);
    }

    @Override
    public int getItemCount() {
        return (players != null ? players.size() : 0);
    }

    private static class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final TextView rank;
        private final TextView name;
        private final TextView elo;
        private final ImageView flag;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            this.rank = (TextView) itemView.findViewById(R.id.tv_rank);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
            this.elo = (TextView) itemView.findViewById(R.id.tv_elo);
            this.flag = (ImageView) itemView.findViewById(R.id.iv_flag);
        }

        public void bind(Player player, int position) {
            Log.d("player debug", player.toString());
            Log.d("elo", player.getElo() + " ");

            rank.setText("#" + (position + 1));
            name.setText(player.getName());
            elo.setText(player.getElo() + " ");

            loadPicture(player);
        }

        private void loadPicture(Player player) {
            Context context = flag.getContext();
            String url = "http://congomikili.com/wp-content/plugins/world-flags/images/flags/48/" + player.getCountryCode().toLowerCase() + ".png";
            Picasso.with(context).load(url).into(flag);
        }
    }

    private Player getPlayer(int position) {
        return players.get(position);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
