package net.qjkj.poker.gandengyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.RealmPlayerScoreInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Key on 2016/12/20 12:02
 * email: MrKey.K@gmail.com
 * description:
 */

public class ScoreAdapterN extends RecyclerView.Adapter<ScoreAdapterN.MyViewHolder> {

    private Context mContext;
    int playerListSize = PokerApplication.checkedPlayerList.size();

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_WINNER = 2;

    public ScoreAdapterN(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return getPlayer(position).getRemain().equals("赢") ? TYPE_WINNER : TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_WINNER) return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gandengyan_score_winner_item, null));
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gandengyan_score_normal_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_score_item_gandengyan.setText(getPlayer(position).getScore());
    }

    /**
     * 根据position + 选手人数，获得一个新的position，然后根据这个position获取应该输出的选手
     * @param position 当前的position
     * @return 当前应该输出的选手
     */
    public RealmPlayerScoreInfo getPlayer(int position) {
        // 当前行 position / 人数   0+4 / 4 = 1  第一行拿第二个Round，因为第一个Round是总分
        // 当前列 position % 人数   0+4 % 4 = 0  第一列拿第一个Player
        int newPosition = position + playerListSize;
        return PokerApplication.realmGameInfo.getRealmRoundInfoList()
                .get(newPosition / playerListSize).getPlayerScoreList()
                .get(newPosition % playerListSize);
    }

    @Override
    public int getItemCount() {
        return PokerApplication.realmGameInfo.getTotal() - playerListSize;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_score_item_gandengyan)
        TextView tv_score_item_gandengyan;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

