package net.qjkj.poker;

import android.app.Application;
import android.content.Context;

import net.qjkj.poker.data.PlayerInfo;
import net.qjkj.poker.data.source.DaggerIPokerRepositoryComponent;
import net.qjkj.poker.data.source.IPokerRepositoryComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Key on 2016/11/30 15:49
 * email: MrKey.K@gmail.com
 * description:
 */

public class PokerApplication extends Application {
    
    private IPokerRepositoryComponent mIPokerRepositoryComponent;
    private Context mContext;
    private String mRealmName = "qjpoker.realm";
    // player表主键
    public static AtomicLong playerInfoPrimaryKeyValue;

    public static List<String> checkedPlayers;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        checkedPlayers = new ArrayList<>();

        // Realm 初始化
        // Call `Realm.init(Context)` before creating a RealmConfiguration
        Realm.init(mContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(mRealmName).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // 不能自动增长的主键，所以要记录下
        Realm realm = Realm.getDefaultInstance();
        playerInfoPrimaryKeyValue = new AtomicLong(realm.where(PlayerInfo.class).max("playerId").longValue());
        realm.close(); //用完一定要关闭

        // dagger2
        mIPokerRepositoryComponent = DaggerIPokerRepositoryComponent.builder()
                .pokerApplicationModule(new PokerApplicationModule(mContext))
                .build();
    }

    public IPokerRepositoryComponent getIPokerRepositoryComponent() {
        return mIPokerRepositoryComponent;
    }

}