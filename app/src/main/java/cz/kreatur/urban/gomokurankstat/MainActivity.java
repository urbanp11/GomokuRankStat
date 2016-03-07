package cz.kreatur.urban.gomokurankstat;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kreatur.urban.gomokurankstat.adapter.PlayersAdapter;
import cz.kreatur.urban.gomokurankstat.ladder.model.Player;
import cz.kreatur.urban.gomokurankstat.ladder.presenter.LadderPresenter;
import cz.kreatur.urban.gomokurankstat.ladder.presenter.SimpleLadderPresenter;
import cz.kreatur.urban.gomokurankstat.ladder.view.LadderView;

/**
 * Created by Petr Urban on 07.03.16.
 */
public class MainActivity extends MvpLceViewStateActivity<SwipeRefreshLayout, List<Player>, LadderView, LadderPresenter>
        implements LadderView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private PlayersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        contentView.setOnRefreshListener(this);

        // setup recycler view
        adapter = new PlayersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setRetainInstance(true);
    }

    // called by Mosby
    @NonNull
    @Override
    public LadderPresenter createPresenter() {
        return new SimpleLadderPresenter();
    }


    @Override public LceViewState<List<Player>, LadderView> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Player> getData() {
        return adapter == null ? null : adapter.getPlayers();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        // TODO: handle errors
        return null;
    }

    @Override
    public void showContent() {
        super.showContent();

        contentView.setRefreshing(false);
    }

    @Override public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        if (pullToRefresh && !contentView.isRefreshing()) {
            // bug: https://code.google.com/p/android/issues/detail?id=77712
            contentView.post(new Runnable() {
                @Override public void run() {
                    contentView.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void setData(List<Player> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadPlayers(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
