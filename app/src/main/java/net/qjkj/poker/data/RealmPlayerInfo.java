package net.qjkj.poker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Key on 2016/11/28 22:26
 * email: MrKey.K@gmail.com
 * description:
 */

public class RealmPlayerInfo extends RealmObject {

    public RealmPlayerInfo() {
    }

    public RealmPlayerInfo(long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    @PrimaryKey
    private long playerId;

    @Required
    private String playerName;

    private int remain;
    private int score;
    private boolean isChecked;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RealmPlayerInfo{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
