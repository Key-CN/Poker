package net.qjkj.poker.data.source;

import android.content.Context;

import net.qjkj.poker.data.source.local.PokerLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Key on 2016/11/28 22:41
 * email: MrKey.K@gmail.com
 * description:
 */
@Module
public class PokerRepositoryModule {
    @Singleton
    @Provides
    @Local
    IPokerDataSource providePokerDataSource(Context context) {
        return new PokerLocalDataSource(context);
    }

}
