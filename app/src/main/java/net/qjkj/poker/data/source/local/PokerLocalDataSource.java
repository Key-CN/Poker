package net.qjkj.poker.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.data.RealmPlayerInfo;
import net.qjkj.poker.data.source.IPokerDataSource;
import net.qjkj.poker.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    private Context mContext;

    /**
     * 选手缓存数据
     */
    private List<RealmPlayerInfo> copyRealmPlayerInfos = new ArrayList<>();

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
        // 每次加载界面的时候刷新数据
        copyRealmPlayerInfos.clear();
        // 查数据库获取数据
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmPlayerInfo> realmPlayerInfos = realm.where(RealmPlayerInfo.class).findAll();
                    if (realmPlayerInfos.isEmpty()) {
                        for (int i = 0; i < 4; i++) {
                            copyRealmPlayerInfos.add(new RealmPlayerInfo("选手" + i));
                        }
                        realm.insert(copyRealmPlayerInfos);
                    } else {
                        copyRealmPlayerInfos.addAll(realm.copyFromRealm(realmPlayerInfos));
                    }
                }
            });
        }
        Logger.d(copyRealmPlayerInfos.toString());
        callback.onPlayersLoaded(copyRealmPlayerInfos);
    }

    /**
     * 增加选手
     *
     * @param playerName 增加的选手名字
     * @param callback   用户刷新界面的回调数据
     */
    @Override
    public void addPlayer(final String playerName, @NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);

        // 添加到数据库
        // minSdkVersion >= 19 and Java >= 7 这样写 无需手动关闭Realm实例
        try (Realm mRealm = Realm.getDefaultInstance()) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmPlayerInfo realmPlayerInfo = new RealmPlayerInfo(playerName);
                    realm.insert(realmPlayerInfo);
                    copyRealmPlayerInfos.add(realmPlayerInfo);
                }
            });
        }
        ToastUtils.getToast(mContext, playerName + "  已添加");
        callback.onPlayersLoaded(copyRealmPlayerInfos);
/*        try {

        } catch (Exception e) {
            e.printStackTrace();
            // 失败，则取消事务
            mRealm.cancelTransaction();
            ToastUtils.getToast(mContext, "添加失败");
        }
        // 关闭Realm
        mRealm.close();*/

        // 把全部选手的list放进回调
//        getPlayers(callback);  //现在只需要把单个添加的放进去就好了
    }

    /**
     * 删除选中的选手，用的数据是 {@link PokerApplication 中的 public static List<String> checkedPlayers;}
     *
     * @param callback 用户刷新界面的回调数据
     */
    @Override
    public void deletePlayers(@NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);

        int size = PokerApplication.checkedPlayerList.size();
        final String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = PokerApplication.checkedPlayerList.get(i).getPlayerName();
        }

        // 从数据库中删除,先拿实例
        try (Realm mRealm = Realm.getDefaultInstance()) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    boolean b = realm.where(RealmPlayerInfo.class).in("playerName", names).findAll().deleteAllFromRealm();
                    ToastUtils.getToast(mContext, Arrays.toString(names) + (b ? "  已删除" : "  删除失败"));
                }
            });
        }
        copyRealmPlayerInfos.removeAll(PokerApplication.checkedPlayerList);
        PokerApplication.checkedPlayerList.clear();
        callback.onPlayersLoaded(copyRealmPlayerInfos);
/*        try {
            mRealm.beginTransaction();
            RealmResults<RealmPlayerInfo> players = mRealm.where(RealmPlayerInfo.class).in("playerName", PokerApplication.roundInfo.getPlayerNameArray()).findAll();
            Logger.d(players);
            boolean b = players.deleteAllFromRealm();
            mRealm.commitTransaction();
            if (b) {
                ToastUtils.getToast(mContext, Arrays.toString(PokerApplication.roundInfo.getPlayerNameArray()) + "  已删除");
            } else {
                ToastUtils.getToast(mContext, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            ToastUtils.getToast(mContext, "删除失败");
        } finally {
            mRealm.close();
        }*/
        // 也可以把每个对象单独从集合中移除。但是我希望重新加载一边。以保证准确性
//        playerInfoList.removeAll(PokerApplication.roundInfo.getPlayerInfoList());
//        callback.onPlayersLoaded(playerInfoList);
        // 把全部选手的list放进回调
//        playerInfoList.clear();
//        getPlayers(callback);
    }

    /**
     * 保存一局游戏,在整一盘游戏中
     */
    @Override
    public void saveRoundOnGame() {
        try (Realm realm = Realm.getDefaultInstance()) {
            if (PokerApplication.realmGameInfo.getGameId() == -1) {
                PokerApplication.realmGameInfo.setGameId(System.currentTimeMillis());
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(PokerApplication.realmGameInfo);
                    Logger.d(PokerApplication.realmGameInfo.toString());
                }
            });
        }
    }

}
