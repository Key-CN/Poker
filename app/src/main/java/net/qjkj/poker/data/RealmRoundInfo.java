package net.qjkj.poker.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Key on 2016/12/14 15:47
 * email: MrKey.K@gmail.com
 * description:
 */

public class RealmRoundInfo extends RealmObject {

    @PrimaryKey
    private long roundId; // 用time做主键

    private RealmList<RealmPlayerScoreInfo> playerScoreList = new RealmList<>();

    public RealmRoundInfo() {
    }

    public RealmRoundInfo(RealmList<RealmPlayerInfo> playerInfos) {
        for (RealmPlayerInfo realmPlayerInfo : playerInfos) {
            playerScoreList.add(new RealmPlayerScoreInfo(realmPlayerInfo.getPlayerName()));
        }
    }

    public RealmRoundInfo(RealmList<RealmPlayerInfo> playerInfos, long roundId) {
        for (RealmPlayerInfo realmPlayerInfo : playerInfos) {
            playerScoreList.add(new RealmPlayerScoreInfo(realmPlayerInfo.getPlayerName()));
        }
        this.roundId = roundId;
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public List<RealmPlayerScoreInfo> getPlayerScoreList() {
        return playerScoreList;
    }

    public List<String> getPlayerNameList() {
        List<String> names = new ArrayList<>();
        for (RealmPlayerScoreInfo realmPlayerInfo : playerScoreList) {
            names.add(realmPlayerInfo.getPlayerName());
        }
        return names;
    }

    public String[] getPlayerNameArray() {
        int size = playerScoreList.size();
        String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = playerScoreList.get(i).getPlayerName();
        }
        return names;
    }

    @Override
    public String toString() {
        return "RealmRoundInfo{" +
                "roundId=" + roundId +
                ", 每个选手：" + playerScoreList.toString() +
                '}';
    }
}
