package net.qjkj.poker.gandengyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.gandengyan.Decoration.StickyHeaderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static net.qjkj.poker.PokerApplication.realmGameInfo;

/**
 * Created by Key on 2016/12/20 12:02
 * email: MrKey.K@gmail.com
 * description:
 */

public class ScoreAdapterX extends RecyclerView.Adapter<ScoreAdapterX.MyViewHolder> implements StickyHeaderAdapter<ScoreAdapterX.MyViewHolder> {

    private Context mContext;
    int playerListSize = PokerApplication.checkedPlayerList.size();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_WINNER = 2;

    public ScoreAdapterX(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gandengyan_name_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // 当前行 position / 人数   5/4 = 1      8/4 = 2
        // 当前列 position % 人数   5%4 = 1      8%4 = 0
        // holder.setText(R.id.tv_name_item_gandengyan, PokerApplication.realmGameInfo.getRealmRoundInfoList().get(position / PokerApplication.realmRoundInfo.getPlayerScoreList().size()).getPlayerScoreList().get(position % PokerApplication.realmRoundInfo.getPlayerScoreList().size()).getScore());
        int newPosition = position + playerListSize;
        holder.tv_name_item_gandengyan.setText(realmGameInfo.getRealmRoundInfoList()
                .get(position / newPosition).getPlayerScoreList()
                .get(position % newPosition).getScore());
    }

    @Override
    public int getItemCount() {
        return realmGameInfo.getTotal() - PokerApplication.checkedPlayerList.size();
    }

    /**
     * Returns the header id for the item at the given position.
     *
     * @param position the item position
     * @return the header id
     */
    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    /**
     * Creates a new header ViewHolder.
     *
     * @param parent the header's view parent
     * @return a view holder for the created view
     */
    @Override
    public MyViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gandengyan_score_winner_item, null));
    }

    /**
     * Updates the header view to reflect the header data for the given position
     *
     * @param viewHolder the header view holder
     * @param position   the header's item position
     */
    @Override
    public void onBindHeaderViewHolder(MyViewHolder viewHolder, int position) {
        int playerListSize = PokerApplication.realmRoundInfo.getPlayerScoreList().size();
        viewHolder.tv_name_item_gandengyan.setText(realmGameInfo.getRealmRoundInfoList()
                .get(position / playerListSize).getPlayerScoreList()
                .get(position % playerListSize).getScore());
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_item_gandengyan)
        TextView tv_name_item_gandengyan;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

