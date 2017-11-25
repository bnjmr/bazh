package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by FCC on 8/23/2017.
 */

@Table(name = "T_TicketDetail")
public class ModelTicketDetail extends Model {

    @Expose
    @Column(name = "NewsId")
    int Parent;

    @Expose
    @Column(name = "CloseDateTime")
    String CloseDateTime;

    @Expose
    @Column(name = "RegisterDateTime")
    String RegisterDateTime;

    @Expose
    @Column(name = "TicketStatus")
    int TicketStatus;

    @Expose
    @Column(name = "Sender")
    String Sender;

    @Expose
    @Column(name = "Text")
    String Text;

    @Expose
    @Column(name = "AttachmentFile")
    String AttachmentFile;

    @Expose
    @Column(name = "Type")
    String Type;

    @Expose
    @Column(name = "IsSeen")
    boolean IsSeen;

    @Expose
    @Column(name = "SendType")
    int SendType;

    @Expose
    @Column(name = "DetailId")
    int DetailId;


    public int getDetailId() {
        return DetailId;
    }

    public void setDetailId(int detailId) {
        DetailId = detailId;
    }

    public int getParent() {
        return Parent;
    }

    public void setParent(int parent) {
        this.Parent = parent;
    }


    public int getTicketStatus() {
        return TicketStatus;
    }

    public void setTicketStatus(int ticketStatus) {
        TicketStatus = ticketStatus;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getAttachmentFile() {
        return AttachmentFile;
    }

    public void setAttachmentFile(String attachmentFile) {
        AttachmentFile = attachmentFile;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }

    public int getSendType() {
        return SendType;
    }

    public void setSendType(int sendType) {
        SendType = sendType;
    }

    public String getRegisterDateTime() {
        return RegisterDateTime;
    }

    public void setRegisterDateTime(String registerDateTime) {
        RegisterDateTime = registerDateTime;
    }

    public String getCloseDateTime() {
        return CloseDateTime;
    }

    public void setCloseDateTime(String closeDateTime) {
        CloseDateTime = closeDateTime;
    }
}
