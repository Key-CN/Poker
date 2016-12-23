package net.qjkj.poker.gandengyan;

import net.qjkj.poker.IBasePresenter;
import net.qjkj.poker.IBaseView;

/**
 * Created by Key on 2016/12/5 14:31
 * email: MrKey.K@gmail.com
 * description:
 */

public interface IGanDengYanContract {

    interface View extends IBaseView<Presenter> {
        /**
         * 属性得分表数据
         */
        void updateScore();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 保存一局游戏
         */
        void saveRound();
    }
}
