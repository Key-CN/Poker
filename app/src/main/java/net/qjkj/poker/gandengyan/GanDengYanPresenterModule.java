package net.qjkj.poker.gandengyan;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Key on 2016/12/5 14:36
 * email: MrKey.K@gmail.com
 * description:
 */
@Module
public class GanDengYanPresenterModule {

    private final IGanDengYanContract.View mView;

    public GanDengYanPresenterModule(IGanDengYanContract.View mView) {
        this.mView = mView;
    }

    @Provides
    IGanDengYanContract.View provideIGanDengYanContractView() {
        return mView;
    }



}
