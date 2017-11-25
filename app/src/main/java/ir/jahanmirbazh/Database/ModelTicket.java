package ir.jahanmirbazh.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 8/23/2017.
 */

@Table(name = "T_Ticket")
public class ModelTicket extends Model {

    @Expose
    @Column(name = "TicketId")
    int TicketId;

    @Expose
    @Column(name = "Title")
    String Title;

    @Expose
    @Column(name = "RegisterDateTime")
    String RegisterDateTime;

    @Expose
    @Column(name = "CloseDateTime")
    String CloseDateTime;

    @Expose
    @Column(name = "TicketStatus")
    int TicketStatus;

    @Expose
    @Column(name = "TicketsCount")
    int TicketsCount;

    @Column(name = "EstateId")
    int EstateId = V.currentEstate.getEstateId();

    @Column(name = "FileServerUrl")
    String FileServerUrl;

    public String getFileServerUrl() {
        return FileServerUrl;
    }

    public void setFileServerUrl(String fileServerUrl) {
        FileServerUrl = fileServerUrl;
    }

    public int getEstateId() {

        return EstateId;
    }

    public void setEstateId(int estateId) {
        EstateId = estateId;
    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public int getTicketStatus() {
        return TicketStatus;
    }

    public void setTicketStatus(int ticketStatus) {
        TicketStatus = ticketStatus;
    }

    public int getTicketsCount() {
        return TicketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        TicketsCount = ticketsCount;
    }
}
