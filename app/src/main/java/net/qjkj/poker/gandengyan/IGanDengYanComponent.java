package net.qjkj.poker.gandengyan;

import net.qjkj.poker.data.source.IPokerRepositoryComponent;
import net.qjkj.poker.gandengyan.ui.GanDengYanActivity;
import net.qjkj.poker.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Key on 2016/12/5 14:36
 * email: MrKey.K@gmail.com
 * description:
 */
@FragmentScoped
@Component(dependencies = IPokerRepositoryComponent.class, modules = GanDengYanPresenterModule.class)
public interface IGanDengYanComponent {

    void inject(GanDengYanActivity ganDengYanActivity);

}
