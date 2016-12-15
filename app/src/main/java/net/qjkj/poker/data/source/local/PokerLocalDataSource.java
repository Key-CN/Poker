package net.qjkj.poker.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.data.PlayerInfo;
import net.qjkj.poker.data.source.IPokerDataSource;
import net.qjkj.poker.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Key on 2016/11/28 22:37
 * email: MrKey.K@gmail.com
 * description:
 */
@Singleton
public class PokerLocalDataSource implements IPokerDataSource {

    private Realm mRealm;
    private Context mContext;

    public PokerLocalDataSource(@NonNull Context context) {
        mContext = checkNotNull(context);
    }

    /**
     * 获取游戏人员名单
     *
     * @param callback
     */
    @Override
    public void getPlayers(@NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);

        // 查数据库获取数据
        mRealm = Realm.getDefaultInstance();
        RealmResults<PlayerInfo> playerInfos = mRealm.where(PlayerInfo.class).findAll();

        // 回传
        if (playerInfos.isEmpty()) {
            // 如果为空就创建四个默认选手
            List<PlayerInfo> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                list.add(new PlayerInfo(i, "选手" + i));
            }
            try {
                mRealm.beginTransaction();
                mRealm.insert(list);
                mRealm.commitTransaction();
                Logger.d("创建默认数据成功");
            } catch (Exception e) {
                e.printStackTrace();
                mRealm.cancelTransaction();
                Logger.d("创建默认数据失败");
            }
            mRealm.close();
            callback.onPlayersLoaded(list);
        } else {
            // 如果不为空，回调
            callback.onPlayersLoaded(playerInfos);
            Logger.d(playerInfos.toString());
        }
    }

    /**
     * 增加选手
     *
     * @param playerName 增加的选手名字
     * @param callback   用户刷新界面的回调数据
     */
    @Override
    public void addPlayer(String playerName, @NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);

        // 添加到数据库
        mRealm = Realm.getDefaultInstance();
        try {
            // 开始事务
            mRealm.beginTransaction();
            // 执行Realm方法
            mRealm.insert(new PlayerInfo(PokerApplication.playerInfoPrimaryKeyValue.incrementAndGet(), playerName));
            // 提交事务
            mRealm.commitTransaction();
            ToastUtils.getToast(mContext, playerName + "  已添加");
        } catch (Exception e) {
            e.printStackTrace();
            // 失败，则取消事务
            mRealm.cancelTransaction();
            ToastUtils.getToast(mContext, "添加失败");
        }
        // 关闭Realm
        mRealm.close();
        // 把全部选手的list放进回调
        getPlayers(callback);
    }

    /**
     * 删除选中的选手，用的数据是 {@link PokerApplication 中的 public static List<String> checkedPlayers;}
     *
     * @param callback 用户刷新界面的回调数据
     */
    @Override
    public void deletePlayers(@NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);

        // 从数据库中删除,先拿实例
        mRealm = Realm.getDefaultInstance();

        try {
            mRealm.beginTransaction();
            String[] playerNames = PokerApplication.checkedPlayers.toArray(new String[PokerApplication.checkedPlayers.size()]);
            Logger.d(playerNames);
            RealmResults<PlayerInfo> players = mRealm.where(PlayerInfo.class).in("playerName", playerNames).findAll();
            Logger.d(players.toArray());
            boolean b = players.deleteAllFromRealm();
            mRealm.commitTransaction();
            if (b) {
                ToastUtils.getToast(mContext, PokerApplication.checkedPlayers.toString() + "  已删除");
            } else {
                ToastUtils.getToast(mContext, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            ToastUtils.getToast(mContext, "删除失败");
        }
        mRealm.close();
        // 把全部选手的list放进回调
        getPlayers(callback);
    }

}
