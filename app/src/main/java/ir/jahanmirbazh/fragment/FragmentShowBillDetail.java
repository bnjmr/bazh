package ir.jahanmirbazh.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityBankList;
import ir.jahanmirbazh.bazh.ActivityBrowser;
import ir.jahanmirbazh.bazh.U;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.components.PersianTextViewNormal;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendPayBillRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetBanks;
import ir.jahanmirbazh.events.EventOnSuccessGetPayUrl;

public class FragmentShowBillDetail extends Fragment {

    @Bind(R.id.txtBillDes)
    TextView txtBillDes;
    @Bind(R.id.txtRegDate)
    TextView txtRegDate;
    @Bind(R.id.txtStartDate)
    TextView txtStartDate;
    @Bind(R.id.txtEndDate)
    TextView txtEndDate;
    @Bind(R.id.txtExpDate)
    TextView txtExpDate;
    @Bind(R.id.txtPayDate)
    TextView txtPayDate;
    @Bind(R.id.txtPrice)
    TextView txtPrice;
    @Bind(R.id.txtPay)
    TextView txtPay;
    @Bind(R.id.txtTax)
    TextView txtTax;
    @Bind(R.id.txtFine)
    TextView txtFine;
    @Bind(R.id.txtDiscount)
    TextView txtDiscount;
    @Bind(R.id.txtAmount)
    TextView txtAmount;
    @Bind(R.id.layPay)
    LinearLayout layPay;
    @Bind(R.id.layPayDate)
    LinearLayout layPayDate;

    @Bind(R.id.txtPaymentType)
    TextView txtPaymentType;

    @Bind(R.id.txtDescription)
    PersianTextViewNormal txtDescription;

    @Bind(R.id.txtPayer)
    TextView txtPayer;

    long billId;
    String Charge;
    String Description;
    String RegisterDateTime;
    String StartDate;
    String EndDate;
    String CloseDate;
    String PaymentDateTime;
    String Payer;
    String PaymentType;
    int Amount;
    int Discount;
    int TotalAmount;
    int Status;
    boolean OnlinePayment;


    boolean canPay = false;
    boolean BillPayment = false;
    String url = "";


    DialogClass dialogClass;


    public static FragmentShowBillDetail newInstance(long billId,
                                                     String Charge,
                                                     String RegisterDateTime,
                                                     String StartDate,
                                                     String EndDate,
                                                     String CloseDate,
                                                     String PaymentDateTime,
                                                     int Amount,
                                                     int Discount,
                                                     int TotalAmount,
                                                     int Status,
                                                     boolean OnlinePayment,
                                                     String Payer,
                                                     String PaymentType,
                                                     boolean BillPayment,
                                                     String Description

    ) {
        Bundle args = new Bundle();
        args.putLong("BILL_ID", billId);
        args.putString("Charge", Charge);
        args.putString("RegisterDateTime", RegisterDateTime);
        args.putString("StartDate", StartDate);
        args.putString("EndDate", EndDate);
        args.putString("CloseDate", CloseDate);
        args.putString("PaymentType", PaymentType);
        args.putString("Payer", Payer);
        args.putString("PaymentDateTime", PaymentDateTime);
        args.putString("Description", Description);
        args.putInt("Amount", Amount);
        args.putInt("Discount", Discount);
        args.putInt("TotalAmount", TotalAmount);
        args.putInt("Status", Status);
        args.putBoolean("OnlinePayment", OnlinePayment);
        args.putBoolean("BillPayment", BillPayment);
        FragmentShowBillDetail fragment = new FragmentShowBillDetail();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.gc();
        System.runFinalization();
        View view = inflater.inflate(R.layout.fragment_show_bill, container, false);
        dialogClass = new DialogClass(getContext());
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
//        View view = getView();
        initialize();

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void initialize() {
//
        layPay.setVisibility(View.GONE);


        billId = getArguments().getLong("BILL_ID");
        Charge = getArguments().getString("Charge");
        if (getArguments().getString("Description") == null || getArguments().getString("Description").equals("")) {
            Description = "توضیحات ندارد";

        } else {
            Description = getArguments().getString("Description");

        }
        RegisterDateTime = getArguments().getString("RegisterDateTime");
        StartDate = getArguments().getString("StartDate");
        EndDate = getArguments().getString("EndDate");
        CloseDate = getArguments().getString("CloseDate");
        PaymentDateTime = getArguments().getString("PaymentDateTime");
        Payer = getArguments().getString("Payer");
        PaymentType = getArguments().getString("PaymentType");
        BillPayment = getArguments().getBoolean("BillPayment");

        if (PaymentDateTime != null && PaymentDateTime.equals(""))
            PaymentDateTime = U.ConvertStringSqlDateTimeToPersianStringDate(PaymentDateTime);

        if (PaymentType != null) {
            switch (PaymentType) {

                case "1":
                    txtPaymentType.setText("پرداخت از اعتبار");
                    break;
                case "2":
                    txtPaymentType.setText("پرداخت آنلاین");
                    break;

            }
        } else {
            txtPaymentType.setText("پرداخت نشده");
        }

        Amount = getArguments().getInt("Amount");
        Discount = getArguments().getInt("Discount");
        TotalAmount = getArguments().getInt("TotalAmount");
        Status = getArguments().getInt("Status");
        OnlinePayment = getArguments().getBoolean("OnlinePayment");


        txtBillDes.setText((Charge.trim().length() > 0) ? Charge : "نامشخص");
        txtRegDate.setText(RegisterDateTime);
        txtStartDate.setText(StartDate);
        txtEndDate.setText(EndDate);
        txtExpDate.setText(CloseDate);
        txtPayDate.setText(PaymentDateTime);
        txtPayer.setText(Payer);
        txtDescription.setText(Html.fromHtml(Description));
        NumberFormat formatter = new DecimalFormat("#,###,###,###");
        txtPrice.setText("" + formatter.format(Amount));
        txtDiscount.setText("" + formatter.format(Discount));
        txtAmount.setText("" + formatter.format(TotalAmount));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CardView layBtnPayOnline;
                CardView layBtnCredit;
                net.kianoni.fontloader.TextView txtLowCredit;
                net.kianoni.fontloader.TextView txtOnlineStatus;

                final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                        .customView(R.layout.dialog_pay_kind, false)
                        .cancelable(true)
                        .build();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                LinearLayout layCancel = (LinearLayout) dialog.findViewById(R.id.layCancel);
                TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
                layBtnPayOnline = (CardView) dialog.findViewById(R.id.layBtnPayOnline);
                layBtnCredit = (CardView) dialog.findViewById(R.id.layBtnCredit);
                txtLowCredit = (net.kianoni.fontloader.TextView) dialog.findViewById(R.id.txtLowCredit);
                txtOnlineStatus = (net.kianoni.fontloader.TextView) dialog.findViewById(R.id.txtOnlineStatus);

                if (!OnlinePayment) {
                    layBtnPayOnline.setPressed(true);
//                    Toast.makeText(getContext(), "پرداخت آنلاین برای شما غیر فعال است", Toast.LENGTH_SHORT).show();
                    layBtnPayOnline.setBackgroundColor(Color.GRAY);
                }

                layBtnCredit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebService.sendPayBillRequest(getContext(), billId);
                        EventBus.getDefault().post(new EventOnSendPayBillRequest());
                        dialog.dismiss();
                    }
                });

                if (TotalAmount > V.currentEstate.getCredit()) {
                    layBtnCredit.setPressed(true);
                    layBtnCredit.setBackgroundColor(Color.GRAY);
                    layBtnCredit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "اعتبار شما کافی نیست", Toast.LENGTH_SHORT).show();

//                            TastyToast.makeText(getContext(), "اعتبار شما کافی نیست", TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                            Toast.makeText(getContext(),"اعتبار شما کاغی نیست",Toast.LENGTH_LONG).show();

                        }
                    });
//                    txtLowCredit.setVisibility(View.VISIBLE);
                }

                layBtnPayOnline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!OnlinePayment) {
                            layBtnPayOnline.setPressed(true);
                            Toast.makeText(getContext(), "پرداخت آنلاین برای شما غیر فعال است", Toast.LENGTH_SHORT).show();

                        } else if (TotalAmount < 100) {
                            layBtnPayOnline.setPressed(true);
                            layBtnPayOnline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(), "حداقل مبلغ باید 100 تومان باشد", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            dialogClass.DialogWaiting();
                            dialog.dismiss();
                            WebService.sendGetBankListRequest(getContext());
                        }
                    }
                });


                layCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        };

        switch (Status) {
            case 1://New
                if (BillPayment) {
                    layPay.setVisibility(View.VISIBLE);
                    txtPay.setText("پرداخت");
                    layPay.setOnClickListener(onClickListener);
                    layPay.setBackgroundResource(R.drawable.bg_rounded_bill_status_not_paid);
                    canPay = true;
                } else {
                    layPay.setVisibility(View.VISIBLE);
                    txtPay.setText("قبض جدید");
//                    layPay.setOnClickListener(onClickListener);
                    layPay.setBackgroundResource(R.drawable.bg_rounded_bill_status_paid);
                    canPay = true;
                }

                break;
            case 2://Paid
                layPay.setVisibility(View.VISIBLE);
                txtPay.setText("پرداخت شده");
                layPay.setOnClickListener(null);
                layPay.setBackgroundResource(R.drawable.bg_rounded_bill_status_paid);
                canPay = false;
                break;
            case 3://Closed
                layPay.setVisibility(View.VISIBLE);
                txtPay.setText("بسته شده");
                layPay.setBackgroundResource(R.drawable.bg_rounded_bill_status_closed);
                layPay.setOnClickListener(null);
                canPay = false;
                break;
            case 4://Removed
                layPay.setVisibility(View.VISIBLE);
                txtPay.setText("حذف شده");
                layPay.setBackgroundResource(R.drawable.bg_rounded_bill_status_expire);
                layPay.setOnClickListener(null);
                canPay = false;
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetBanks banks) {
        if (banks.getModelBanks().size() == 1) {
            WebService.sendPayWhitBillIdRequest(getContext(), (int) billId, banks.getModelBanks().get(0).getBankId());
        } else {
            Intent intent = new Intent(getContext(), ActivityBankList.class);
            Gson gson = new Gson();
            String s = gson.toJson(banks.getModelBanks());
            intent.putExtra("BankList", s);
            intent.putExtra("billId", (int) billId);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError requestError) {
        dialogClass.DialogWaitingClose();
    }

}
