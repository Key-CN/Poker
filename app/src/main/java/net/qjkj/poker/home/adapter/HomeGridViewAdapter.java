package net.qjkj.poker.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.RealmPlayerInfo;

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
    private List<RealmPlayerInfo> realmPlayerInfoList;

    public HomeGridViewAdapter(Context mContext, List<RealmPlayerInfo> realmPlayerInfoList) {
        this.mContext = mContext;
        this.realmPlayerInfoList = realmPlayerInfoList;
    }

    public void refresh(List<RealmPlayerInfo> realmPlayerInfoList) {
        this.realmPlayerInfoList = realmPlayerInfoList;
        // 刷新时清空下集合，其实不清也没bug，因为监听里写了移除，但是还是要防止意外，毕竟还没写记住CheckBox状态
//        PokerApplication.checkedPlayers.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return realmPlayerInfoList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.home_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String playerName = realmPlayerInfoList.get(position).getPlayerName();

        viewHolder.cb_home_gridview_item.setText(playerName);
        //复选框读取
//        viewHolder.cb_home_gridview_item.setChecked(realmPlayerInfoList.get(position).isChecked());

        viewHolder.cb_home_gridview_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                //复选框记忆

                if (isChecked) {
                    PokerApplication.checkedPlayers.add(playerName);
                    PokerApplication.roundInfo.addPlayerInfo(realmPlayerInfoList.get(position));
                } else {
                    PokerApplication.checkedPlayers.remove(playerName);
                    PokerApplication.roundInfo.removePlayerInfo(realmPlayerInfoList.get(position));
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
