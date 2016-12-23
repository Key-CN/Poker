package net.qjkj.poker.home.ui;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import net.qjkj.poker.BaseActivity;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.home.DaggerIHomeComponent;
import net.qjkj.poker.home.HomePresenter;
import net.qjkj.poker.home.HomePresenterModule;
import net.qjkj.poker.home.IHomeContract;
import net.qjkj.poker.util.ActivityUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by Key on 2016/11/25 01:17
 * email: MrKey.K@gmail.com
 * description:
 */

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, AddDialogFragment.OnAddPlayerListener {

    @BindView(R.id.drawerLayout_home_act)
    DrawerLayout drawerLayout_home_act;//act的最外层布局ID
    @BindView(R.id.navigationView_home_act)
    NavigationView navigationView_home_act;//侧拉
    @BindView(R.id.toolbar_home_act)
    Toolbar toolbar_home_act;//顶上

    @Inject
    HomePresenter mHomePresenter;

    /**
     * fragment的集合
     */
    private ArrayList<Fragment> mFragmentList;

    /**
     * @return 返回布局文件ID
     */
    @Override
    protected int getContentViewId() {
        return R.layout.home_act;
    }

    /**
     * TODO 初始化布局文件
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLayout(Bundle savedInstanceState) {

        // 初始化选择人物的列表
        PokerApplication.checkedPlayerList = new RealmList<>();

        // Set up the toolbar.
        setSupportActionBar(toolbar_home_act);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        drawerLayout_home_act.setStatusBarBackground(R.color.colorPrimaryDark);
        if (navigationView_home_act != null) {
            setupDrawerContent(navigationView_home_act);
        }

        //初始化fragment集合
        if (mFragmentList == null || mFragmentList.size() != 1) {
            mFragmentList = new ArrayList<>();
            //创建fragment
            HomeFragment homeFragment = HomeFragment.newInstance("创建时传进fragment的参数1", "参数2");
            mFragmentList.add(homeFragment);
            //放到contentFrame_first这个容器中
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(0), R.id.contentFrame_home_act);
        }

        // dagger2
        DaggerIHomeComponent.builder()
                .iPokerRepositoryComponent(((PokerApplication) getApplication()).getIPokerRepositoryComponent())
                .homePresenterModule(new HomePresenterModule(((IHomeContract.View) (mFragmentList.get(0)))))
                .build().inject(this);
    }

    /**
     * 侧拉菜单内的点击事件
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                Toast.makeText(HomeActivity.this, "1111", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.statistics_navigation_menu_item:
                                Toast.makeText(HomeActivity.this, "222222", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        drawerLayout_home_act.closeDrawers();
                        return true;
                    }
                });
    }

    /**
     * 三条横线的侧拉按钮点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                drawerLayout_home_act.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 本activity实现了fragment中的内部接口--OnFragmentInteractionListener接口，所以算是他的子类
     * fragment中又声明了OnFragmentInteractionListener接口为他的成员变量
     * 所以fragment可以调用OnFragmentInteractionListener中的方法，而他的子类已经重写
     *
     * @param string
     */
    @Override
    public void onFragmentInteraction(String string) {
        Logger.d(string + "----fragment互动接口，回调，fragment调用此方法将会执行子类（也就是本activity）自己重写的方法，意思就是将fragment的参数传回activity，用来更新UI");
    }

    /**
     * 已经在dialog里面检查好姓名了。正确的话直接输入数据库
     * @param playerName
     */
    @Override
    public void onAddPlayer(String playerName) {
        mHomePresenter.addPlayer(playerName);
    }
}
