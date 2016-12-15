package net.qjkj.poker.home;

import net.qjkj.poker.data.source.IPokerRepositoryComponent;
import net.qjkj.poker.home.ui.HomeActivity;
import net.qjkj.poker.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Key on 2016/12/3 16:31
 * email: MrKey.K@gmail.com
 * description:
 */
@FragmentScoped
@Component(dependencies = IPokerRepositoryComponent.class, modules = HomePresenterModule.class)
public interface IHomeComponent {

    void inject(HomeActivity homeActivity);

}
