package ir.jahanmirbazh.bazh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelBillDetail;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnSendPayBillRequest;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetBillDetail;
import ir.jahanmirbazh.events.EventOnSuccessPayBill;
import ir.jahanmirbazh.events.EventOnUpdateCounts;
import ir.jahanmirbazh.fragment.FragmentShowBillDetail;

public class ActivityShowBill extends AppCompatActivity {

    MaterialDialog alertDialog;
    @Bind(R.id.layImgBack)
    LinearLayout layImgBack;
    @Bind(R.id.layToolbarShowBill)
    LinearLayout layToolbarShowBill;
    @Bind(R.id.layFragment)
    FrameLayout layFragment;

    boolean updateCount = false;


    long billId;
    private DialogClass dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        initializeView();
        dialogClass = new DialogClass(this);
        billId = getIntent().getExtras().getLong("BILL_ID");
        int newCount = V.currentEstate.getBillsCount() - 1;

    }

    private void initializeView() {

        //layToolbarShowBill.setBackgroundColor(Color.parseColor("#" + G.setting.appColor));
//        String billDes = getIntent().getExtras().getString("BILL_DES");
//        String billRegDate = getIntent().getExtras().getString("BILL_REGDATE");
//        String billStartDate = getIntent().getExtras().getString("BILL_STARTDATE");
//        String billEndDate = getIntent().getExtras().getString("BILL_ENDDATE");
//        String billExpDate = getIntent().getExtras().getString("BILL_EXPDATE");
//        String billPayDate = getIntent().getExtras().getString("BILL_PAYDATE");
//        double billPrice = getIntent().getExtras().getDouble("BILL_PRICE");
//        double billTax = getIntent().getExtras().getDouble("BILL_TAX");
//        double billDiscount = getIntent().getExtras().getDouble("BILL_DISCOUNT");
//        double billAmount = getIntent().getExtras().getDouble("BILL_AMOUNT");
//        boolean billClosed = getIntent().getExtras().getBoolean("BILL_CLOSED");

        /*Back Button*/
        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        WebService.sendGetBillDetailRequest(ActivityShowBill.this, billId);
        dialogClass.DialogWaiting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetBillDetail billDetail) {
        dialogClass.DialogWaitingClose();
        ModelBillDetail billDetail1 = billDetail.getBillDetail();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.layFragment,
                        FragmentShowBillDetail.newInstance(
                                billId,
                                billDetail1.getCharge(),
                                billDetail1.getRegisterDateTime(),
                                billDetail1.getStartDate(),
                                billDetail1.getEndDate(),
                                billDetail1.getCloseDate(),
                                billDetail1.getPaymentDateTime(),
                                billDetail1.getAmount(),
                                billDetail1.getDiscount(),
                                billDetail1.getTotalAmount(),
                                billDetail1.getStatus(),
                                billDetail1.isOnlinePayment(),
                                billDetail1.getPayer(),
                                billDetail1.getPaymentType(),
                                billDetail1.isBillPayment(),
                                billDetail1.getDescription()
                        ))
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOnShowDialog event) {
        dialogClass.DialogWaitingClose();
//        layLoading.setVisibility(View.GONE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendPayBillRequest request) {
        dialogClass.DialogWaiting();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessPayBill eventOnSuccessPayBill) {
        if (!updateCount) {
            updateCount = true;
            int newCount = V.currentEstate.getBillsCount() - 1;
            if (newCount > 0) {
                V.currentEstate.setBillsCount(V.currentEstate.getBillsCount() - 1);
            } else {
                V.currentEstate.setBillsCount(0);
            }
            V.currentEstate.save();
            EventBus.getDefault().post(new EventOnUpdateCounts());
        }
        WebService.sendGetBillDetailRequest(ActivityShowBill.this, billId);
        WebService.sendGetBillRequest(ActivityShowBill.this, V.currentEstate.getEstateId());


    }

}
