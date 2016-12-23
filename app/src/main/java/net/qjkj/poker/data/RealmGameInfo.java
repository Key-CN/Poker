package net.qjkj.poker.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Key on 2016/12/14 15:46
 * email: MrKey.K@gmail.com
 * description:
 */

public class RealmGameInfo extends RealmObject {

    @PrimaryKey
    private long gameId = -1; // 用选手加time做主键

    private RealmList<RealmRoundInfo> realmRoundInfoList = new RealmList<>();

    public RealmGameInfo() {
    }

    public RealmGameInfo(RealmRoundInfo realmRoundInfo) {
        realmRoundInfoList.add(0, realmRoundInfo);
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public RealmList<RealmRoundInfo> getRealmRoundInfoList() {
        return realmRoundInfoList;
    }

    public void setRealmRoundInfoList(RealmList<RealmRoundInfo> realmRoundInfoList) {
        this.realmRoundInfoList = realmRoundInfoList;
    }

    public void addRound(RealmRoundInfo realmRoundInfo) {
        realmRoundInfoList.add(1, realmRoundInfo);
    }

    public void removeRound(RealmRoundInfo realmRoundInfo) {
        realmRoundInfoList.remove(realmRoundInfo);
    }

    public void removeAllRounds() {
        realmRoundInfoList.clear();
    }

    public int getTotal() {
        int size = 0;
        // 用普通for循环可以不用判断，是否为空，不然增强for循环size为0，会空指针
        for (int i = 0; i < realmRoundInfoList.size(); i++) {
            size += realmRoundInfoList.get(i).getPlayerScoreList().size();
        }
        return size;
    }

    @Override
    public String toString() {
        return "RealmGameInfo{" +
                "gameId=" + gameId +
                ", 每：单盘游戏：" + realmRoundInfoList.toString() +
                '}';
    }
}
