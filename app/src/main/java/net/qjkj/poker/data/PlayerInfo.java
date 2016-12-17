package net.qjkj.poker.data;

/**
 * Created by Key on 2016/11/28 22:26
 * email: MrKey.K@gmail.com
 * description:
 */

public class PlayerInfo {

    public PlayerInfo() {
    }

    public PlayerInfo(long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public PlayerInfo(RealmPlayerInfo realmPlayerInfo) {
        this.playerId = realmPlayerInfo.getPlayerId();
        this.playerName = realmPlayerInfo.getPlayerName();
    }

    private long playerId;
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
