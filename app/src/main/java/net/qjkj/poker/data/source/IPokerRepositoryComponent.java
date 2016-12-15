package net.qjkj.poker.data.source;

import net.qjkj.poker.PokerApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Key on 2016/11/28 22:47
 * email: MrKey.K@gmail.com
 * description:
 */
@Singleton
@Component(modules = {PokerRepositoryModule.class, PokerApplicationModule.class})
public interface IPokerRepositoryComponent {

    PokerRepository getPokerRepository();

}
