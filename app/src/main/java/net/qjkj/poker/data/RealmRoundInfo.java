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
    private long roundId;


    private RealmList<RealmPlayerInfo> realmPlayerInfoList = new RealmList<>();

    public RealmRoundInfo() {
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public List<RealmPlayerInfo> getRealmPlayerInfoList() {
        return realmPlayerInfoList;
    }

    public void setRealmPlayerInfoList(RealmList<RealmPlayerInfo> realmPlayerInfoList) {
        this.realmPlayerInfoList = realmPlayerInfoList;
    }

    public void addPlayerInfo(RealmPlayerInfo realmPlayerInfo) {
        realmPlayerInfoList.add(realmPlayerInfo);
    }

    public void removePlayerInfo(RealmPlayerInfo realmPlayerInfo) {
        realmPlayerInfoList.remove(realmPlayerInfo);
    }

    public void removeAll() {
        realmPlayerInfoList.clear();
    }

    public List<String> getPlayerNameList() {
        List<String> names = new ArrayList<>();
        for (RealmPlayerInfo realmPlayerInfo : realmPlayerInfoList) {
            names.add(realmPlayerInfo.getPlayerName());
        }
        return names;
    }

    public String[] getPlayerNameArray() {
        int size = realmPlayerInfoList.size();
        String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = realmPlayerInfoList.get(i).getPlayerName();
        }
        return names;
    }
}
