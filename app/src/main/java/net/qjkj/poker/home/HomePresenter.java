package net.qjkj.poker.home;

import net.qjkj.poker.data.RealmPlayerInfo;
import net.qjkj.poker.data.source.IPokerDataSource;
import net.qjkj.poker.data.source.PokerRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Key on 2016/11/25 01:15
 * email: MrKey.K@gmail.com
 * description:
 */

public final class HomePresenter implements IHomeContract.Presenter {

    private final PokerRepository mPokerRepository;

    private final IHomeContract.View mHomeView;

    @Inject
    public HomePresenter(PokerRepository mPokerRepository, IHomeContract.View mHomeView) {
        this.mPokerRepository = mPokerRepository;
        this.mHomeView = mHomeView;
    }

    @Inject
    void setupListeners() {
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    /**
     * 界面加载时获取选手，设置adapter
     */
    @Override
    public void getPlayerInfoList() {
        mPokerRepository.getPlayers(new IPokerDataSource.LoadPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<RealmPlayerInfo> copyRealmPlayerInfos) {
                mHomeView.updatePlayers(copyRealmPlayerInfos);
            }
        });
    }

    /**
     * 增加选手，刷新界面
     * @param playerName
     */
    @Override
    public void addPlayer(String playerName) {
        mPokerRepository.addPlayer(playerName, new IPokerDataSource.LoadPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<RealmPlayerInfo> copyRealmPlayerInfos) {
                mHomeView.updateAdapter(copyRealmPlayerInfos);
            }
        });
    }

    /**
     * 删除选中选手，刷新界面
     */
    @Override
    public void deletePlayers() {
        mPokerRepository.deletePlayers(new IPokerDataSource.LoadPlayersCallback() {
            @Override
            public void onPlayersLoaded(List<RealmPlayerInfo> copyRealmPlayerInfos) {
                mHomeView.updateAdapter(copyRealmPlayerInfos);
            }
        });
    }


}
