package ir.jahanmirbazh.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import net.kianoni.fontloader.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelNotification;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.components.LayoutedTextView;


public class AdapterNotifiesRecycler extends RecyclerView.Adapter<AdapterNotifiesRecycler.NotifyViewHolder> {


    List<ModelNotification> notifies;

    public AdapterNotifiesRecycler(List<ModelNotification> notifies) {
        this.notifies = notifies;
    }
    @Override
    public NotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_notification_item, parent, false);
        return new NotifyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final NotifyViewHolder holder, final int position) {
        final ModelNotification notify = notifies.get(position);

        holder.txtNotifyDate.setText(notify.getRegisterDateTime());

        holder.txtNotifyDetail.setText(notify.getText());

        holder.layShowNotifyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final MaterialDialog dialogShowNotify = new MaterialDialog.Builder(view.getContext())
                        .customView(R.layout.dialog_show_message,false)
                        .build();
                dialogShowNotify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View v = dialogShowNotify.getCustomView();
                android.widget.TextView txtMessage = (android.widget.TextView) v.findViewById(R.id.txtMessage);
                LinearLayout layCancel = (LinearLayout) v.findViewById(R.id.layCancel);

                txtMessage.setText("" + notify.getText());

                layCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogShowNotify.dismiss();
                    }
                });
                dialogShowNotify.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifies.size();
    }

    public class NotifyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtNotifyDetail)
        LayoutedTextView txtNotifyDetail;
        @Bind(R.id.layNotifyDetail)
        LinearLayout layNotifyDetail;
        @Bind(R.id.txtNotifyDate) android.widget.TextView txtNotifyDate;
        @Bind(R.id.txtShowMoreDetail) TextView txtShowMoreDetail;
        @Bind(R.id.imgShowMoreDetail)
        ImageView imgShowMoreDetail;
        @Bind(R.id.layShowNotifyDetail)
        LinearLayout layShowNotifyDetail;

        public NotifyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelNotification> data) {
        notifies = data;
        notifyDataSetChanged();
    }
}
