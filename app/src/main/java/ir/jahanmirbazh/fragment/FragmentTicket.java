package ir.jahanmirbazh.fragment;

import android.content.Intent;
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
import ir.jahanmirbazh.Database.ModelTicket;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterTicketRecycler;
import ir.jahanmirbazh.bazh.ActivitySendTicket;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetTicketRequest;
import ir.jahanmirbazh.events.EventOnSuccessGetTicket;
import ir.jahanmirbazh.events.EventOnUpdateCounts;

public class FragmentTicket extends Fragment {

    //    List<Ticket> tickets = new ArrayList<>();
//    int offset = 0;
//    private int loadLimit = 30;
    LinearLayoutManager linearLayoutManager;
    AdapterTicketRecycler adapterTicketRecycler;
    //    @Bind(R.id.actionButton)
//    FloatingActionButton actionButton;
    @Bind(R.id.recyclerViewTicket)
    RecyclerView recyclerViewTicket;
    @Bind(R.id.layShowMessage)
    LinearLayout layShowMessage;
    @Bind(R.id.layAddNewTicket)
    LinearLayout layAddNewTicket;
    DatabaseManager databaseManager;
    View v;
    DialogClass dialogClass;
    private boolean showDialog = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.d("FragmentTicket", "onCreateView");
        v = inflater.inflate(R.layout.fragment_ticket, container, false);
        ButterKnife.bind(this, v);
        EventBus.getDefault().register(this);
        databaseManager = new DatabaseManager();
        initView();
        dialogClass = new DialogClass(getContext());
        return v;
    }

    private void initView() {

        layAddNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivitySendTicket.class));
            }
        });

        recyclerViewTicket.setHasFixedSize(true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetTicketRequest request) {
        if (!showDialog) {
            showDialog = true;
            dialogClass.DialogWaiting();
        }

        List<ModelTicket> modelTicketList = databaseManager.selectAllTicket();
        if (modelTicketList != null && modelTicketList.size() != 0) {
            adapterTicketRecycler = new AdapterTicketRecycler(modelTicketList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewTicket.setLayoutManager(layoutManager);
            recyclerViewTicket.setAdapter(adapterTicketRecycler);
            layShowMessage.setVisibility(View.GONE);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetTicket ticket) {
        dialogClass.DialogWaitingClose();

        if (ticket.getModelTickets() != null && ticket.getModelTickets().size() != 0) {
            adapterTicketRecycler = new AdapterTicketRecycler(ticket.getModelTickets());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewTicket.setLayoutManager(layoutManager);
            recyclerViewTicket.setAdapter(adapterTicketRecycler);
            layShowMessage.setVisibility(View.GONE);
            int h = 0;
            for (ModelTicket ticket1 : ticket.getModelTickets()) {
                if (ticket1.getTicketsCount() > 0) {
                    h += ticket1.getTicketsCount();
                }
            }
            V.currentEstate.setTicketsCount(h);
            V.currentEstate.save();
            EventBus.getDefault().post(new EventOnUpdateCounts());

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
//        Toast.makeText(getContext(), getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();

//        TastyToast.makeText(getContext(), getString(R.string.offline_mode), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        dialogClass.DialogWaitingClose();
    }

}
