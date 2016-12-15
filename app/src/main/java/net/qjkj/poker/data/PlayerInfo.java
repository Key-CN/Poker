package net.qjkj.poker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Key on 2016/11/28 22:26
 * email: MrKey.K@gmail.com
 * description:
 */

public class PlayerInfo extends RealmObject {

    public PlayerInfo() {
    }

    public PlayerInfo(long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    @PrimaryKey
    private long playerId;

    @Required
    private String playerName;

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

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
