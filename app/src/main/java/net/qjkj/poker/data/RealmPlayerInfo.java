package net.qjkj.poker.data;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by Key on 2016/11/28 22:26
 * email: MrKey.K@gmail.com
 * description:
 */

public class RealmPlayerInfo extends RealmObject {

    public RealmPlayerInfo() {
    }

    public RealmPlayerInfo(String playerName) {
        this.playerName = playerName;
    }

    @Required
    private String playerName;

    @Ignore
    private boolean isChecked;

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

    @Override
    public String toString() {
        return "称呼: '" + playerName + '\'';
    }
}
