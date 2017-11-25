package ir.jahanmirbazh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelPayment;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterPaymentRecycler;
import ir.jahanmirbazh.bazh.ActivityBankList;
import ir.jahanmirbazh.bazh.ActivityBrowser;
import ir.jahanmirbazh.bazh.U;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetPaymentRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetBanks;
import ir.jahanmirbazh.events.EventOnSuccessGetPayUrl;
import ir.jahanmirbazh.events.EventOnSuccessGetPayments;


public class FragmentPayment extends Fragment {


    AdapterPaymentRecycler adapterPaymentRecycler;
    //    List<Peyment> payments = new ArrayList<>();
    @Bind(R.id.recyclerViewPeyment)
    RecyclerView recyclerViewPeyment;
    //        @Bind(R.id.actionButton)
//        FloatingActionButton actionButton;
    @Bind(R.id.txtMessage)
    TextView txtMessage;
    @Bind(R.id.txtNewPaymentOpenClose)
    TextView txtNewPaymentOpenClose;
    @Bind(R.id.txtNewPayment)
    TextView txtNewPayment;
    @Bind(R.id.edtPayment)
    EditText edtPayment;
    @Bind(R.id.edtPaymentDes)
    EditText edtPaymentDes;
    @Bind(R.id.layShowMessage)
    LinearLayout layShowMessage;
    @Bind(R.id.layPay)
    LinearLayout layPay;
    @Bind(R.id.layExpandPayment)
    LinearLayout layExpandPayment;
    @Bind(R.id.layAddNewPayment)
    LinearLayout layAddNewPayment;


    DialogClass dialogClass;

//
//    boolean visibleToUser = false;
//    private String current = "";
//
//
    /**
     * baraye inke bedunim range dokme bayad chejuri bashad
     **/
    boolean isCloseButtonShow = false;

    DatabaseManager databaseManager;
    private boolean showDialog = false;
    int amount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FragmentPayment Life Cycle", "onCreate");

//        EventBus.getDefault().register(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d("FragmentPayment Life Cycle", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_peyment, container, false);
        dialogClass = new DialogClass(getContext());
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        databaseManager = new DatabaseManager();
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        Logger.d("FragmentPayment Life Cycle", "onResume");

        if (!isCloseButtonShow) {
            txtNewPayment.setText("ثبت پرداخت جدید");
            txtNewPaymentOpenClose.setText("+");
        } else {
            txtNewPayment.setText("بستن پنجره");
            txtNewPaymentOpenClose.setText("-");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d("FragmentPayment Life Cycle", "onAttach");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("FragmentPayment Life Cycle", "onActivityCreated");

        initView();
    }

    private void initView() {
        if (V.OnlinePayment) {
//        if (true) {
            layAddNewPayment.setVisibility(View.VISIBLE);
        } else {
            layAddNewPayment.setVisibility(View.GONE);
        }

        layAddNewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!layExpandPayment.isShown()) {
                    U.expand(layExpandPayment);
                    isCloseButtonShow = true;
                    txtNewPayment.setText("بستن پنجره");
                    txtNewPaymentOpenClose.setText("-");

                } else {
                    U.collapse(layExpandPayment);
                    isCloseButtonShow = false;

                    txtNewPayment.setText("ثبت پرداخت جدید");
                    txtNewPaymentOpenClose.setText("+");
                }
            }
        });
        layPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPayment.getText().toString().equals("")) {
                    dialogClass.DialogError("وارد کردن مبلغ الزامی است");
                }
                if (Integer.parseInt(edtPayment.getText().toString()) < 100) {
                    Toast.makeText(getContext(), "حداقل مبلغ باید 100 تومان باشد", Toast.LENGTH_SHORT).show();
                } else {
                    dialogClass.DialogWaiting();
                    WebService.sendGetBankListRequest(getContext());
                    try {
                        amount = Integer.parseInt(edtPayment.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPeyment.setHasFixedSize(true);
        recyclerViewPeyment.setLayoutManager(layoutManager);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetPayments payments) {
        initView();
        adapterPaymentRecycler = new AdapterPaymentRecycler(payments.getModelPayments());
        recyclerViewPeyment.setAdapter(adapterPaymentRecycler);
        layShowMessage.setVisibility(View.GONE);
        dialogClass.DialogWaitingClose();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetPaymentRequest request) {
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }

        List<ModelPayment> paymentList = databaseManager.selectAllpayments();
        adapterPaymentRecycler = new AdapterPaymentRecycler(paymentList);
        recyclerViewPeyment.setAdapter(adapterPaymentRecycler);

        if (paymentList != null && paymentList.size() != 0)
            layShowMessage.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
        dialogClass.DialogWaitingClose();
//        Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();

//        TastyToast.makeText(getContext(), getString(R.string.offline_mode), TastyToast.LENGTH_SHORT, TastyToast.ERROR);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetBanks banks) {
        String Description = edtPaymentDes.getText().toString();
        if (banks.getModelBanks().size() == 1) {
            WebService.sendPayWhitAmountRequest(getContext(), amount, banks.getModelBanks().get(0).getBankId(),Description);
        } else {
            Intent intent = new Intent(getContext(), ActivityBankList.class);
            Gson gson = new Gson();
            String s = gson.toJson(banks.getModelBanks());
            intent.putExtra("BankList",s);
            intent.putExtra("Amount",amount);
            intent.putExtra("Description",Description);
            startActivity(intent);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetPayUrl url) {
        dialogClass.DialogWaitingClose();
        Intent intent = new Intent(getContext(), ActivityBrowser.class);
        intent.putExtra("Url", url.getUrl());
        startActivity(intent);

    }
}
