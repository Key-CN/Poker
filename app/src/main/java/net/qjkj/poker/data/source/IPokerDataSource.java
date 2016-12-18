package net.qjkj.poker.data.source;

import android.support.annotation.NonNull;

import net.qjkj.poker.data.RealmPlayerInfo;

import java.util.List;

/**
 * Created by Key on 2016/11/28 22:34
 * email: MrKey.K@gmail.com
 * description:
 */

public interface IPokerDataSource {

    /**
     * 加载游戏人员的回调
     */
    interface LoadPlayersCallback {
        /**
         * 回调游戏人员名单集合
         * @param copyRealmPlayerInfos 已经从Realm对象copyFrom为普通对象
         */
        void onPlayersLoaded(List<RealmPlayerInfo> copyRealmPlayerInfos);

    }

    /**
     * 获取游戏人员名单
     * @param callback 用户刷新界面的回调数据
     */
    void getPlayers(@NonNull LoadPlayersCallback callback);

    /**
     * 增加选手
     * @param callback 用户刷新界面的回调数据
     * @param playerName 增加的选手名字
     */
    void addPlayer(String playerName, @NonNull LoadPlayersCallback callback);

    /**
     * 删除选中的选手，用的数据是 {@link net.qjkj.poker.PokerApplication 中的 public static List<String> checkedPlayers;}
     * @param callback 用户刷新界面的回调数据
     */
    void deletePlayers(@NonNull LoadPlayersCallback callback);
}
