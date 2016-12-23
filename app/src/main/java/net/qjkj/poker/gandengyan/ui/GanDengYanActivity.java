package net.qjkj.poker.gandengyan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.qjkj.poker.BaseActivity;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.RealmGameInfo;
import net.qjkj.poker.data.RealmRoundInfo;
import net.qjkj.poker.gandengyan.DaggerIGanDengYanComponent;
import net.qjkj.poker.gandengyan.GanDengYanPresenter;
import net.qjkj.poker.gandengyan.GanDengYanPresenterModule;
import net.qjkj.poker.gandengyan.IGanDengYanContract;
import net.qjkj.poker.util.ActivityUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class GanDengYanActivity extends BaseActivity {

    @Inject
    GanDengYanPresenter mGanDengYanPresenter;

    /**
     * fragment的集合
     */
    private ArrayList<Fragment> mFragmentList;

    /**
     * @return 返回布局文件ID
     */
    @Override
    protected int getContentViewId() {
        return R.layout.gandengyan_act;
    }

    /**
     * TODO 初始化布局文件
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLayout(Bundle savedInstanceState) {

        // 创建一局游戏，并且把创建时间作为主键存入，这样也可以知道什么时候开始游戏的。并且同时创建第一盘游戏，作用储存总分的作用
        PokerApplication.realmGameInfo = new RealmGameInfo(new RealmRoundInfo(PokerApplication.checkedPlayerList, System.currentTimeMillis()));

        // 创建一盘游戏，实际数据库中是第二盘，因为第一盘是总分
        PokerApplication.realmRoundInfo = new RealmRoundInfo(PokerApplication.checkedPlayerList);

        //初始化fragment集合
        if (mFragmentList == null || mFragmentList.size() != 1) {
            mFragmentList = new ArrayList<>();
            //创建fragment
            mFragmentList.add(GanDengYanFragment.newInstance());
            // 加载fragment到activity
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(0), R.id.contentFrame_gandengyan_act);
        }
        //dagger2 注入
        DaggerIGanDengYanComponent.builder()
                .iPokerRepositoryComponent(((PokerApplication) getApplication()).getIPokerRepositoryComponent())
                .ganDengYanPresenterModule(new GanDengYanPresenterModule((IGanDengYanContract.View) mFragmentList.get(0)))
                .build().inject(this);
    }
}
