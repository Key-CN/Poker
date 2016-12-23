package net.qjkj.poker.gandengyan;

import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.data.RealmPlayerScoreInfo;
import net.qjkj.poker.data.RealmRoundInfo;
import net.qjkj.poker.data.source.PokerRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Key on 2016/12/5 14:32
 * email: MrKey.K@gmail.com
 * description:
 */

public class GanDengYanPresenter implements IGanDengYanContract.Presenter {

    private final IGanDengYanContract.View mView;
    private final PokerRepository mPokerRepository;

    @Inject
    public GanDengYanPresenter(IGanDengYanContract.View mView, PokerRepository mPokerRepository) {
        this.mView = mView;
        this.mPokerRepository = mPokerRepository;
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    /**
     * 保存一局游戏
     */
    @Override
    public void saveRound() {
        PokerApplication.realmRoundInfo.setRoundId(System.currentTimeMillis());
        PokerApplication.realmGameInfo.addRound(PokerApplication.realmRoundInfo);

        // 总分Round
        List<RealmPlayerScoreInfo> totalScore = PokerApplication.realmGameInfo.getRealmRoundInfoList().get(0).getPlayerScoreList();
        // 当前Round
        List<RealmPlayerScoreInfo> currentScore = PokerApplication.realmGameInfo.getRealmRoundInfoList().get(1).getPlayerScoreList();

        int size = PokerApplication.checkedPlayerList.size();

        for (int i = 0; i < size; i++) {
            totalScore.get(i).setScore(Integer.parseInt(totalScore.get(i).getScore()) + Integer.parseInt(currentScore.get(i).getScore()) + "");
        }

        mPokerRepository.saveRoundOnGame();
        // 开始下一局
        PokerApplication.realmRoundInfo = new RealmRoundInfo(PokerApplication.checkedPlayerList);
        mView.updateScore();
    }
}
