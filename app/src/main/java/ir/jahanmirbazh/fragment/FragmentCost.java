package ir.jahanmirbazh.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelCost;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterCostRecycler;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.components.PersianTextViewNormal;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetCost;
import ir.jahanmirbazh.events.EventOnSuccessGetCost;
import saman.zamani.persiandate.PersianDate;

import static ir.jahanmirbazh.bazh.G.getDate;


public class FragmentCost extends Fragment {

    View view;
    AdapterCostRecycler adapterCostRecycler;
    RecyclerView recyclerViewCost;
    LinearLayout layShowMessage;
    LinearLayout layEndDate;
    LinearLayout layStartDate;
    RecyclerView.LayoutManager linearLayoutManager;
    DatabaseManager databaseManager;
    DialogClass dialogClass;
    private boolean showDialog = false;
    PersianTextViewNormal btnFilter;
    PersianTextViewNormal txtStartDate;
    PersianTextViewNormal txtEndDate;

    private PersianDatePickerDialog StartPicker;
    private PersianDatePickerDialog EndPicker;
    int StartDate3[] = {0, 0, 0};
    int EndDate3[] = {0, 0, 0};
    PersianCalendar StartinitDate;
    PersianCalendar EndinitDate;

    String StartDate;
    String EndDate;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        view = inflater.inflate(R.layout.fragment_cost, container, false);
        databaseManager = new DatabaseManager();
        initView();
        StartinitDate = new PersianCalendar();
        EndinitDate = new PersianCalendar();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        int day0 = calendar.get(Calendar.DAY_OF_MONTH);
        int m0 = calendar.get(Calendar.MONTH) + 1;
        int y0 = calendar.get(Calendar.YEAR);

        StartDate = df.format(getDate(y0, m0, day0));
        EndDate = df.format(getDate(y0, m0, day0));


        V.StartDate = df.format(getDate(y0, m0 - 1, day0));
        V.EndDate = V.StartDate;


        PersianDate pdate0 = new PersianDate();
        int date[] = pdate0.toJalali(y0, m0, day0);
        String d0 = date[0] + " / " + date[1] + " / " + date[2];

        txtStartDate.setText(d0);
        txtEndDate.setText(d0);

        layEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndPicker();
            }
        });

        layStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartPicker(StartDate3);
            }
        });
//        V.StartDate = U.getDate(-5);
//        V.EndDate = U.getCurrentDate();
        dialogClass = new DialogClass(getContext());

        return view;
    }

    private void showStartPicker(int[] date) {

        StartPicker = new PersianDatePickerDialog(getContext())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(StartinitDate)
                .setMaxYear(1398)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        PersianDate pdate = new PersianDate();
                        StartinitDate = persianCalendar;
                        EndDate3 = pdate.toGregorian(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                        V.StartDate = df.format(getDate(EndDate3[0], EndDate3[1] - 1, EndDate3[2]));
                        String d = persianCalendar.getPersianYear() + " / " + persianCalendar.getPersianMonth() + " / " + persianCalendar.getPersianDay();
                        txtStartDate.setText(d);
                    }

                    @Override
                    public void onDisimised() {

                    }
                });

        StartPicker.show();

    }

    private void showEndPicker() {

        EndPicker = new PersianDatePickerDialog(getContext())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMaxYear(1398)
                .setMinYear(1300)
                .setInitDate(EndinitDate)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
//                        Toast.makeText(context, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        PersianDate pdate = new PersianDate();
                        EndinitDate = persianCalendar;
                        StartDate3 = pdate.toGregorian(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                        V.EndDate = df.format(getDate(StartDate3[0], StartDate3[1] - 1, StartDate3[2] + 1));
                        String d = persianCalendar.getPersianYear() + " / " + persianCalendar.getPersianMonth() + " / " + persianCalendar.getPersianDay();
                        txtEndDate.setText(d);
                    }

                    @Override
                    public void onDisimised() {

                    }
                });
        EndPicker.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("FragmentCost", "onResume");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        recyclerViewCost = (RecyclerView) view.findViewById(R.id.recyclerViewCost);
        layShowMessage = (LinearLayout) view.findViewById(R.id.layShowMessage);
        layEndDate = (LinearLayout) view.findViewById(R.id.layEndDate);
        layStartDate = (LinearLayout) view.findViewById(R.id.layStartDate);
        btnFilter = (PersianTextViewNormal) view.findViewById(R.id.btnFilter);
        txtStartDate = (PersianTextViewNormal) view.findViewById(R.id.txtStartDate);
        txtEndDate = (PersianTextViewNormal) view.findViewById(R.id.txtEndDate);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebService.sendGetCostRequest(getContext());
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetCost cost) {
        layShowMessage.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCost.setHasFixedSize(true);
        recyclerViewCost.setLayoutManager(linearLayoutManager);
        adapterCostRecycler = new AdapterCostRecycler(cost.getModelCosts());
        recyclerViewCost.setAdapter(adapterCostRecycler);
        dialogClass.DialogWaitingClose();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetCost request) {
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }


        List<ModelCost> modelCosts = databaseManager.selectAllCosts();
        if (modelCosts != null && modelCosts.size() != 0) {

            layShowMessage.setVisibility(View.GONE);
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewCost.setHasFixedSize(true);
            recyclerViewCost.setLayoutManager(linearLayoutManager);
            adapterCostRecycler = new AdapterCostRecycler(modelCosts);
            recyclerViewCost.setAdapter(adapterCostRecycler);
//            dialogClass.DialogWaitingClose();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
        dialogClass.DialogWaitingClose();
//        Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent0(EventOnRequestError onRequestError) {
        dialogClass.DialogWaitingClose();
//        Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();
    }


}
