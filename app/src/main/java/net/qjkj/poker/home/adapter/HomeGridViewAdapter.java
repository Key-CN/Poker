package net.qjkj.poker.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.orhanobut.logger.Logger;

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
    private List<RealmPlayerInfo> copyRealmPlayerInfos;

    public HomeGridViewAdapter(Context mContext, List<RealmPlayerInfo> copyRealmPlayerInfos) {
        this.mContext = mContext;
        this.copyRealmPlayerInfos = copyRealmPlayerInfos;
    }

    public void refresh(List<RealmPlayerInfo> copyRealmPlayerInfos) {
        this.copyRealmPlayerInfos = copyRealmPlayerInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return copyRealmPlayerInfos.size();
    }

    @Override
    public RealmPlayerInfo getItem(int position) {
        return copyRealmPlayerInfos.get(position);
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
        final RealmPlayerInfo realmPlayerInfo = getItem(position);

        viewHolder.cb_home_gridview_item.setText(realmPlayerInfo.getPlayerName());
        //复选框读取
        viewHolder.cb_home_gridview_item.setChecked(realmPlayerInfo.isChecked());

        viewHolder.cb_home_gridview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复选框记忆
                realmPlayerInfo.setChecked(!realmPlayerInfo.isChecked());
                Logger.d("checked:" + copyRealmPlayerInfos.get(position).isChecked() + "----position:"+position);
                if (realmPlayerInfo.isChecked()) {
                    PokerApplication.checkedPlayerList.add(realmPlayerInfo);
                } else {
                    PokerApplication.checkedPlayerList.remove(realmPlayerInfo);
                }
            }
        });

        // 复用时会出错，滑动时会自动更改其他position的isChecked
/*        viewHolder.cb_home_gridview_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //复选框记忆
                copyRealmPlayerInfos.get(position).setChecked(isChecked);
                Logger.d("checked:" + copyRealmPlayerInfos.get(position).isChecked() + "----position:"+position);
                if (isChecked) {
                    PokerApplication.realmRoundInfo.addPlayerInfo(realmPlayerInfo);
                } else {
                    PokerApplication.realmRoundInfo.removePlayerInfo(realmPlayerInfo);

                }
            }
        });*/

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
