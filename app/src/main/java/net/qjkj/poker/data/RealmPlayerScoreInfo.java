package net.qjkj.poker.data;

import io.realm.RealmObject;

/**
 * Created by Key on 2016/11/28 22:26
 * email: MrKey.K@gmail.com
 * description:
 */

public class RealmPlayerScoreInfo extends RealmObject {

    public RealmPlayerScoreInfo() {
    }

    public RealmPlayerScoreInfo(String playerName) {
        this.playerName = playerName;
    }

    private String playerName;
    private String remain = "手牌数";
    private String score = "0";


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 清除选手的手牌数和得分
     */
    public void clearSoreAndRemain() {
        remain = "手牌数";
        score = "0";
    }

    @Override
    public String toString() {
        return "RealmPlayerInfo{" +
                "姓名：'" + playerName + '\'' +
                ", 手牌数：'" + remain + '\'' +
                ", 得分：'" + score + '\'' +
                '}';
    }
}
