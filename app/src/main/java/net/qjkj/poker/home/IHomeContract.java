package net.qjkj.poker.home;

import net.qjkj.poker.IBasePresenter;
import net.qjkj.poker.IBaseView;
import net.qjkj.poker.data.RealmPlayerInfo;

import java.util.List;

/**
 * Created by Key on 2016/11/25 01:13
 * email: MrKey.K@gmail.com
 * description: 用来写View接口和Presenter接口的契约
 */

public interface IHomeContract {

    interface View extends IBaseView<Presenter> {

        /** 更新 GridView ,第一次 setAdapter */
        void updatePlayers(List<RealmPlayerInfo> realmPlayerInfoList);

        /** 更新 GridView， 后续，刷新adapter notify */
        void updateAdapter(List<RealmPlayerInfo> realmPlayerInfoList);

    }

    interface Presenter extends IBasePresenter {

        /** 获取选手列表，通过 updatePlayers(List<RealmPlayerInfo> playerInfoList) 来更新 */
        void getPlayerInfoList();

        /** 增加选手 */
        void addPlayer(String playerName);

        /** 删除选中选手 */
        void deletePlayers();
    }
}
