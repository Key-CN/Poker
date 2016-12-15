package net.qjkj.poker.gandengyan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.qjkj.poker.BaseActivity;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.gandengyan.DaggerIGanDengYanComponent;
import net.qjkj.poker.gandengyan.GanDengYanPresenterModule;
import net.qjkj.poker.gandengyan.IGanDengYanContract;
import net.qjkj.poker.util.ActivityUtils;

import java.util.ArrayList;

public class GanDengYanActivity extends BaseActivity {

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
