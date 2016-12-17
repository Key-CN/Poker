package net.qjkj.poker.data.source;

import android.support.annotation.NonNull;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.data.RealmPlayerInfo;

import java.util.List;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Key on 2016/11/28 22:36
 * email: MrKey.K@gmail.com
 * description:
 */

public class PokerRepository implements IPokerDataSource {

    private final IPokerDataSource mPokerLocalDataSource;

    @Inject
    public PokerRepository(@Local IPokerDataSource mPokerLocalDataSource) {
        this.mPokerLocalDataSource = mPokerLocalDataSource;
    }

    /**
     * 获取游戏人员名单
     * 如果缓存可用就从缓存中拿，如果缓存不可用就从数据库中拿，* 如果数据库不可用就从网络中拿，（* 此版本中无网络数据，也没做缓存）
     * 所以直接就把callback往上传
     * @param callback
     */
    @Override
    public void getPlayers(@NonNull final LoadPlayersCallback callback) {
        checkNotNull(callback);
        mPokerLocalDataSource.getPlayers(new LoadPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<RealmPlayerInfo> realmPlayerInfoList) {
                callback.onPlayersLoaded(realmPlayerInfoList);
            }
        });
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
        // 区别于getPlayers的写法，因为参数都是LoadPlayersCallback，所以直接往下传，就不重新new一个callback了
        mPokerLocalDataSource.addPlayer(playerName, callback);
    }

    /**
     * 删除选中的选手，用的数据是 {@link PokerApplication 中的 public static List<String> checkedPlayers;}
     *
     * @param callback 用户刷新界面的回调数据
     */
    @Override
    public void deletePlayers(@NonNull LoadPlayersCallback callback) {
        checkNotNull(callback);
        mPokerLocalDataSource.deletePlayers(callback);
    }
}
