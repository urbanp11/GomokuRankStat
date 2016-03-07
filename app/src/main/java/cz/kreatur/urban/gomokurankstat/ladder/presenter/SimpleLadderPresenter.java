package cz.kreatur.urban.gomokurankstat.ladder.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import cz.kreatur.urban.gomokurankstat.ladder.view.LadderView;
import cz.kreatur.urban.gomokurankstat.network.GomokuLoader;
import cz.kreatur.urban.gomokurankstat.ladder.model.Player;

/**
 * Created by Petr Urban on 07.03.16.
 */

// Presenter that coordinates LadderView and business logic
public class SimpleLadderPresenter extends MvpBasePresenter<LadderView> implements LadderPresenter {

    @Override
    public void loadPlayers(boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);

        GomokuLoader.getInstance().downloadData(new GomokuLoader.GomokuDownloaderCallback() {
            @Override
            public void onStart() {
                Log.d("progress", "GomokuLoader onStart");
            }

            @Override
            public void onSuccess(List<Player> countries) {
                Log.d("progress", "GomokuLoader onSuccess");
                if(isViewAttached()) {
                    getView().setData(countries);
                    getView().showContent();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d("progress", "GomokuLoader onError");
            }
        });
    }

    // cancel running bacground tasks when activity gets destroyed
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance) {
            GomokuLoader.getInstance().cancel(true);
        }
    }

    @Override
    public void attachView(LadderView view) {
        super.attachView(view);
    }
}
