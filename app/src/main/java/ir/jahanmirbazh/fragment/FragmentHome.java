package ir.jahanmirbazh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.kianoni.fontloader.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.enums.EnumCurrentPage;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSelectEstate;
import ir.jahanmirbazh.events.EventOnSendGetDashboardRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetDashboard;

public class FragmentHome extends Fragment {


    @Bind(R.id.txtAds)
    TextView txtAds;

    @Bind(R.id.txtDebt)
    TextView txtDebt;

    @Bind(R.id.txtCredit)
    TextView txtCredit;

    DialogClass dialogClass;
    private boolean showDialog = false;
//    @Bind(R.id.layBackDateOfToday) LinearLayout layBackDateOfToday;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        initializeView();
        dialogClass = new DialogClass(getContext());
        return v;
    }

    private void initializeView() {
        if (V.currentEstate != null) {
            txtDebt.setVisibility(View.VISIBLE);
            txtCredit.setVisibility(View.VISIBLE);
            String pattern = "#,###";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            txtDebt.setText(" " + myFormatter.format(V.currentEstate.getDebt()) + "");
            txtCredit.setText(" " + myFormatter.format(V.currentEstate.getCredit()) + "");
        } else {
            txtDebt.setVisibility(View.GONE);
            txtCredit.setVisibility(View.GONE);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            Logger.d("setUserVisibleHint", "FragmentHome : setUserVisibleHint");
            V.currentPage = EnumCurrentPage.PAGE_HOME;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initializeView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSelectEstate event) {
        initializeView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetDashboardRequest request) {
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetDashboard dashboard){
        dialogClass.DialogWaitingClose();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error){
        dialogClass.DialogWaitingClose();
        Toast.makeText(getContext(),getString(R.string.offline_mode),Toast.LENGTH_SHORT).show();
 }


}
