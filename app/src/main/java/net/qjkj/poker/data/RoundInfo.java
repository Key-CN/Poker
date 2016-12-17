package net.qjkj.poker.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Key on 2016/12/14 15:47
 * email: MrKey.K@gmail.com
 * description:
 */

public class RoundInfo  extends RealmObject {

    @PrimaryKey
    private long roundId;


    private RealmList<RealmPlayerInfo> realmPlayerInfoList = new RealmList<>();

    public RoundInfo() {
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
}
