package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Gson.ModelBank;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterBankList;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetPayUrl;

public class ActivityBankList extends AppCompatActivity {

    AdapterBankList adapterBankList;

    @Bind(R.id.rcyBanks)
    RecyclerView rcyBanks;

    @Bind(R.id.layImgBack)
    LinearLayout layImgBack;

    int billId;
    int Amount;
    DialogClass dialogClass;

    ModelBank[] modelBanks;
    String Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        ButterKnife.bind(this);
        dialogClass = new DialogClass(this);
        dialogClass.DialogWaiting();
        Intent intent = getIntent();
        if (intent != null) {
            billId = intent.getIntExtra("billId", 0);
            Amount = intent.getIntExtra("Amount", 0);
            Description = intent.getStringExtra("Description");
            String s = intent.getStringExtra("BankList");
            modelBanks = new GsonBuilder().create().fromJson(s, ModelBank[].class);
        }
//        WebService.sendGetBankListRequest(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcyBanks.setHasFixedSize(true);
        rcyBanks.setLayoutManager(layoutManager);
        dialogClass.DialogWaitingClose();
        adapterBankList = new AdapterBankList(Arrays.asList(modelBanks), billId, this, Amount, Description);
        rcyBanks.setAdapter(adapterBankList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetPayUrl url) {
        Intent intent = new Intent(this, ActivityBrowser.class);
        intent.putExtra("Url", url.getUrl());
        startActivity(intent);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnShowDialog event) {
        dialogClass.DialogWaitingClose();
        switch (event.getDialogKind()) {
            case success:
                dialogClass.DialogOkMessage(event.getMessage());
                break;
            case error:
                dialogClass.DialogError(event.getMessage());
                break;
        }
        dialogClass.DialogWaitingClose();


    }


}
