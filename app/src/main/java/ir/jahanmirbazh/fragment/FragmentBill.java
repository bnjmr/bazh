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
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelBill;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterBillRecycler;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetBillRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetBill;


public class FragmentBill extends Fragment {

    AdapterBillRecycler adapterBillRecycler;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewBill;
    LinearLayout layShowMassage;
    Spinner spinner;
    List<ModelBill> bills = new ArrayList<>();
    int offset = 0;
    int iCurrentSelection = 0;
    // on scroll
    private int loadLimit = 30;
    boolean visibleToUser = false;
    DatabaseManager databaseManager;
    DialogClass dialogClass;
    boolean showDialog = false;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.d("FragmentBill", "onCreateView");
        View v = inflater.inflate(R.layout.fragment_bill, container, false);
        databaseManager = new DatabaseManager();
        dialogClass = new DialogClass(getContext());
        initializeView(v);
        return v;
    }

    private void initializeView(View view) {

        layShowMassage = (LinearLayout) view.findViewById(R.id.layShowMessage);

        recyclerViewBill = (RecyclerView) view.findViewById(R.id.recyclerViewBill);
        spinner = (Spinner) view.findViewById(R.id.spinner);


    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("onPause", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("FragmentBill", "onResume");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d("onViewCreated", "onViewCreated");

    }

    /*******************************************EVENT***************************************************/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetBill getBill) {
//        List<ModelBill> modelBills = databaseManager.selectAllBills();
        dialogClass.DialogWaitingClose();

        layShowMassage.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewBill.setHasFixedSize(true);
        recyclerViewBill.setLayoutManager(linearLayoutManager);
        adapterBillRecycler = new AdapterBillRecycler(getBill.getModelBills(), getContext());
        recyclerViewBill.setAdapter(adapterBillRecycler);
//        dialogClass.DialogWaitingClose();
//        loading.setVisibility(View.GONE);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetBillRequest request) {
//        loading.setVisibility(View.VISIBLE);
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }
        adapterBillRecycler = new AdapterBillRecycler(null, getContext());
        recyclerViewBill.setAdapter(adapterBillRecycler);

        /*initialize RecyclerView*/
        List<ModelBill> modelBills = databaseManager.selectAllBills();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewBill.setHasFixedSize(true);
        recyclerViewBill.setLayoutManager(linearLayoutManager);
        adapterBillRecycler = new AdapterBillRecycler(modelBills, getContext());
        recyclerViewBill.setAdapter(adapterBillRecycler);
        if (modelBills != null && modelBills.size() != 0) {
            layShowMassage.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
        try {
            dialogClass.DialogWaitingClose();
//            loading.setVisibility(View.GONE);
            Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();
//            TastyToast.makeText(getContext(), getString(R.string.offline_mode), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
