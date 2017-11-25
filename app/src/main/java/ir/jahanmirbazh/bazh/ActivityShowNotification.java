package ir.jahanmirbazh.bazh;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterNotifiesRecycler;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnSuccessGetNotification;
import ir.jahanmirbazh.events.EventOnUpdateCounts;

public class ActivityShowNotification extends AppCompatActivity {

    @Bind(R.id.layClose)
    LinearLayout layClose;
    @Bind(R.id.layToolbarShowNotificationPage)
    LinearLayout layToolbarShowNotificationPage;
    @Bind(R.id.lstNotification)
    RecyclerView lstNotification;
    @Bind(R.id.layMain)
    LinearLayout layMain;
    @Bind(R.id.layShowMessage)
    LinearLayout layShowMessage;

    AdapterNotifiesRecycler adapterNotifiesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        WebService.sendGetNotificationRequest(ActivityShowNotification.this);
        V.currentEstate.setNotificationsCount(0);
        V.currentEstate.save();
        EventBus.getDefault().post(new EventOnUpdateCounts());
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getSystemService(ns);
        nMgr.cancelAll();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {

        lstNotification.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityShowNotification.this);
        lstNotification.setLayoutManager(layoutManager);
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetNotification notification) {
        adapterNotifiesRecycler = new AdapterNotifiesRecycler(notification.getModelNotifications());
        lstNotification.setAdapter(adapterNotifiesRecycler);
        layShowMessage.setVisibility(View.GONE);
    }
}
