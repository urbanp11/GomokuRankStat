package cz.kreatur.urban.gomokurankstat.ladder.presenter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import cz.kreatur.urban.gomokurankstat.ladder.view.LadderView;

/**
 * Created by Petr Urban on 07.03.16.
 */

// Presenter interface
public interface LadderPresenter extends MvpPresenter<LadderView> {
    void loadPlayers(final boolean pullToRefresh);
}
