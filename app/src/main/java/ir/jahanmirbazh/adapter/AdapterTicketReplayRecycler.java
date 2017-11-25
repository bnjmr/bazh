package ir.jahanmirbazh.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelTicketDetail;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.enums.EnumTicketSendType;
import ir.jahanmirbazh.enums.EnumTicketStatus;
import ir.jahanmirbazh.events.EventOnOpenAttachmentFile;
import ir.jahanmirbazh.events.EventOnStartDownloadAttach;

import static ir.jahanmirbazh.R.id.web;

public class AdapterTicketReplayRecycler extends RecyclerView.Adapter<AdapterTicketReplayRecycler.TicketReplayViewHolder> {

    private static final int DOWNLOAD_THREAD_POOL_SIZE = 2;
    List<Integer> downloadIds = new ArrayList<>();
    List<ModelTicketDetail> ticketDetails;
    DatabaseManager databaseManager;

    public AdapterTicketReplayRecycler(List<ModelTicketDetail> ticketDetails) {
        this.ticketDetails = ticketDetails;
        databaseManager = new DatabaseManager();
    }

    @Override
    public TicketReplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EnumTicketSendType.PersonToUser:
                View viewPersonToUser = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_ticket_replay_send, parent, false);
                return new TicketReplayViewHolder(viewPersonToUser);
            case EnumTicketSendType.UserToPerson:
                View viewUserToPerson = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_ticket_replay_received, parent, false);
                return new TicketReplayViewHolder(viewUserToPerson);
            default:
                View viewUserde = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_ticket_replay_received, parent, false);
                return new TicketReplayViewHolder(viewUserde);
        }
    }

    @Override
    public void onBindViewHolder(final TicketReplayViewHolder holder, final int position) {
        final ModelTicketDetail detail = ticketDetails.get(position);

        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                detail.getText() +
                "</body></html>";
        holder.txtTicketDes.loadData(s, "Text/html; charset=utf-8", "UTF-8");
        holder.txtTicketDes.setBackgroundColor(0x00000000);

        holder.layViewAttachment.setVisibility(View.GONE);
        holder.txtSender.setText(detail.getSender());
        holder.txtDate.setText(detail.getRegisterDateTime());
        switch (detail.getTicketStatus()) {
            case EnumTicketStatus.Acting:
                holder.txtTicketStatus.setText("درحال اقدام");
                break;
            case EnumTicketStatus.Reviewing:
                holder.txtTicketStatus.setText("درحال بررسی");

                break;
            case EnumTicketStatus.WaitingForPersonAnswer:
                holder.txtTicketStatus.setText("در انتظار پاسخ شخص");

                break;
            case EnumTicketStatus.WaitingForReview:
                holder.txtTicketStatus.setText("در انتظاربررسی");
                break;
        }


        if (detail.getTicketStatus() == EnumTicketSendType.UserToPerson) {
            if (detail.isSeen()) {
                holder.layViewAttachment.setVisibility(View.VISIBLE);
            } else {
                holder.layViewAttachment.setVisibility(View.GONE);
            }
        }

        if (detail.getAttachmentFile() == null || detail.getAttachmentFile().equals("")) {
            //http://192.168.1.44:10965//Files/LetterAttachmentFiles/663c373a-97dc-4ba6-b4b7-3e470d2dbdf3.jpeg
            holder.layDownloadAttachment.setVisibility(View.GONE);
        } else {
            holder.layDownloadAttachment.setVisibility(View.VISIBLE);
            holder.layDownloadAttachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] fileName = detail.getAttachmentFile().split("/");
                    final String fileUri = Environment
                            .getExternalStorageDirectory().toString()
                            + "/Download/" + fileName[fileName.length - 1];

                    File file = new File(fileUri);

                    if (file.exists()) {

                        EventBus.getDefault().post(new EventOnOpenAttachmentFile(fileUri));

                    } else {
                        EventBus.getDefault().post(new EventOnStartDownloadAttach(detail.getAttachmentFile(), detail.getDetailId()));
                    }
                }
            });


        }


    }


    @Override
    public int getItemCount() {
        return ticketDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ticketDetails.get(position).getSendType();
    }

    public static class TicketReplayViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtTicketDes)
        WebView txtTicketDes;
        @Bind(R.id.layTicketDes)
        LinearLayout layTicketDes;
        @Bind(R.id.layDownloadAttachment)
        LinearLayout layDownloadAttachment;
        @Bind(R.id.layViewAttachment)
        LinearLayout layViewAttachment;
        @Bind(R.id.layAttachment)
        LinearLayout layAttachment;
        @Bind(R.id.layBackTicketReplay)
        LinearLayout layBackTicketReplay;
        @Bind(R.id.txtSender)
        TextView txtSender;
        @Bind(R.id.txtDate)
        TextView txtDate;
        @Bind(R.id.txtTicketStatus)
        TextView txtTicketStatus;

        public TicketReplayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelTicketDetail> data) {
        ticketDetails = data;
        notifyDataSetChanged();
    }

    public boolean isFileExist(String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return true;
        return false;
    }

    String stripHTMLtagsExceptIMG(String htmlString) {
        String subbed = htmlString.replaceAll("< *[iI][mM][gG]", "_iimmgg");
        String stripped = android.text.Html.fromHtml(subbed).toString();
        String unsubbed = stripped.replaceAll("_iimmgg", "<img");
        return unsubbed;
    }
}
