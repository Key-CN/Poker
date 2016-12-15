package net.qjkj.poker.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.PlayerInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Key on 2016/11/30 14:57
 * email: MrKey.K@gmail.com
 * description:
 */

public class HomeGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<PlayerInfo> playerInfoList;

    public HomeGridViewAdapter(Context mContext, List<PlayerInfo> playerInfoList) {
        this.mContext = mContext;
        this.playerInfoList = playerInfoList;
    }

    public void refresh(List<PlayerInfo> playerInfoList) {
        this.playerInfoList = playerInfoList;
        // 刷新时清空下集合，其实不清也没bug，因为监听里写了移除，但是还是要防止意外，毕竟还没写记住CheckBox状态
//        PokerApplication.checkedPlayers.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return playerInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.home_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String playerName = playerInfoList.get(position).getPlayerName();
        viewHolder.cb_home_gridview_item.setText(playerName);
        // TODO 还没解决拖动后的复用问题
        viewHolder.cb_home_gridview_item.setChecked(false);
        viewHolder.cb_home_gridview_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PokerApplication.checkedPlayers.add(playerName);
                } else {
                    PokerApplication.checkedPlayers.remove(playerName);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.cb_home_gridview_item)
        CheckBox cb_home_gridview_item;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
