package net.qjkj.poker.gandengyan;

import net.qjkj.poker.data.source.PokerRepository;

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

}
