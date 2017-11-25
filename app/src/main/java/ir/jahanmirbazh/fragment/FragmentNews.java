package ir.jahanmirbazh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelNews;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterNewsRecycler;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetNewsRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetNews;
import ir.jahanmirbazh.events.EventOnUpdateCounts;


public class FragmentNews extends Fragment {


    AdapterNewsRecycler adapterNewsRecycler;
    //    LinearLayoutManager linearLayoutManager;
//    List<News> newses;
//    int offset = 0;
//    // on scroll
//    private int loadLimit = 30;
//    boolean visibleToUser = false;
//
    @Bind(R.id.recyclerViewNews)
    RecyclerView recyclerViewNews;
    @Bind(R.id.layShowMessage)
    LinearLayout layShowMessage;

    DatabaseManager databaseManager;
    DialogClass dialogClass;
    private boolean showDialog = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d("FragmentNews", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        databaseManager = new DatabaseManager();
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        dialogClass = new DialogClass(getContext());
        initView();
        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(layoutManager);

        List<ModelNews> newsList = databaseManager.selectAllNews();
        if (newsList != null && newsList.size() != 0) {
            adapterNewsRecycler = new AdapterNewsRecycler(newsList);
            recyclerViewNews.setAdapter(adapterNewsRecycler);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetNews news) {
        adapterNewsRecycler = new AdapterNewsRecycler(news.getModelNewses());
        recyclerViewNews.setAdapter(adapterNewsRecycler);
        layShowMessage.setVisibility(View.GONE);
        dialogClass.DialogWaitingClose();
        int i = 0;
        for (ModelNews news1 : news.getModelNewses()) {
            if (!news1.isSeen()) {
                i++;
            }
        }
        if (i > 0) {
            V.currentEstate.setNewsCount(i);
        } else {
            V.currentEstate.setNewsCount(0);
        }
        V.currentEstate.save();
        EventBus.getDefault().post(new EventOnUpdateCounts());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetNewsRequest request) {
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
        dialogClass.DialogWaitingClose();
//        Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();

//        TastyToast.makeText(getContext(), getString(R.string.offline_mode), TastyToast.LENGTH_SHORT, TastyToast.ERROR);

    }


}
