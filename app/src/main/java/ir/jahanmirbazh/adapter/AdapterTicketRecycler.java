package ir.jahanmirbazh.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelTicket;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityShowTicket;
import ir.jahanmirbazh.enums.EnumTicketStatus;


public class AdapterTicketRecycler extends RecyclerView.Adapter<AdapterTicketRecycler.TicketViewHolder> {

    List<ModelTicket> modelTickets;

    public AdapterTicketRecycler(List<ModelTicket> modelTickets) {
        this.modelTickets = modelTickets;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TicketViewHolder holder, int position) {
        final ModelTicket modelTicket = modelTickets.get(position);
        holder.txtTicketTitle.setText(modelTicket.getTitle());
        holder.txtTicketDate.setText(modelTicket.getRegisterDateTime());
        switch (modelTicket.getTicketStatus()) {
            case EnumTicketStatus.WaitingForReview:
                holder.txtTicketStatus.setText("منتظر بررسی");
                break;
            case EnumTicketStatus.Reviewing:
                holder.txtTicketStatus.setText("در حال بررسی");
                break;
            case EnumTicketStatus.Acting:
                holder.txtTicketStatus.setText("در حال اقدام");
                break;
            case EnumTicketStatus.WaitingForPersonAnswer:
                holder.txtTicketStatus.setText("منتظر پاسخ مشترک");
                break;
        }
        if (modelTicket.getCloseDateTime() != null && !modelTicket.equals("")) {
            holder.txtTicketStatus.setText("بسته شده");
        }

        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadein = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
                holder.imgMoreDetail.startAnimation(animFadein);
//                WebService.sendGetTicketDetailRequest(view.getContext(),modelTicket.getTicketId());
                Intent intent = new Intent(view.getContext(), ActivityShowTicket.class);
                intent.putExtra("TICKET_ID", modelTicket.getTicketId());
                intent.putExtra("TICKET_COUNT", modelTicket.getTicketsCount());
                intent.putExtra("TICKET_STATUS", modelTicket.getTicketStatus());
                intent.putExtra("TICKET_REGISTER_DATE", modelTicket.getRegisterDateTime());
                intent.putExtra("TICKET_CLOSE_DATE_TIME", modelTicket.getCloseDateTime());
                intent.putExtra("TICKET_TITLE", modelTicket.getTitle());
                view.getContext().startActivity(intent);
            }
        });

        if (modelTicket.getTicketsCount() == 0) {
            holder.layCountUnSeenTicket.setVisibility(View.GONE);
        } else {
            holder.txtCountUnSeenTicket.setText(String.valueOf(modelTicket.getTicketsCount()));
            holder.layCountUnSeenTicket.setVisibility(View.VISIBLE);
        }


//        if(position == tickets.size() - 1 && tickets.size() >= G.pageSize) {
//            EventBus.getDefault().post(new EventOnEndOfTicketList());
//        }
    }

    @Override
    public int getItemCount() {
        return modelTickets.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.layMoreDetail)
        LinearLayout layMoreDetail;
        @Bind(R.id.imgMoreDetail)
        ImageView imgMoreDetail;
        @Bind(R.id.txtTicketTitle)
        TextView txtTicketTitle;
        @Bind(R.id.txtTicketDate)
        TextView txtTicketDate;
        @Bind(R.id.txtTicketStatus)
        TextView txtTicketStatus;
        @Bind(R.id.layCountUnSeenTicket)
        LinearLayout layCountUnSeenTicket;
        @Bind(R.id.txtCountUnSeenTicket)
        TextView txtCountUnSeenTicket;

        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelTicket> data) {
        modelTickets = data;
        notifyDataSetChanged();
    }
}
