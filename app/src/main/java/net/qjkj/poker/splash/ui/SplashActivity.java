package net.qjkj.poker.splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import net.qjkj.poker.BaseActivity;
import net.qjkj.poker.R;
import net.qjkj.poker.home.ui.HomeActivity;

public class SplashActivity extends BaseActivity {

    /**
     * @return 返回布局文件ID
     */
    @Override
    protected int getContentViewId() {
        return R.layout.splash_act;
    }

    /**
     * TODO 初始化布局文件
     *
     * @param savedInstanceState
     */
    @Override
    protected void initLayout(Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(888);
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }).start();

    }
}
