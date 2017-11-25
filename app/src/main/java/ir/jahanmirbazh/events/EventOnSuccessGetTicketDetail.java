package ir.jahanmirbazh.events;

import com.activeandroid.query.Select;

import java.util.List;

import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelTicketDetail;

/**
 * Created by FCC on 8/24/2017.
 */

public class EventOnSuccessGetTicketDetail {

    List<ModelTicketDetail> modelTicketDetails;



    public EventOnSuccessGetTicketDetail(List<ModelTicketDetail> modelTicketDetails) {
        this.modelTicketDetails = modelTicketDetails;

        DatabaseManager databaseManager = new DatabaseManager();
        for (ModelTicketDetail ticketDetail : modelTicketDetails) {

            ModelTicketDetail detail = new Select().from(ModelTicketDetail.class).where("DetailId=?", ticketDetail.getDetailId()).executeSingle();
            if (detail == null) {
                databaseManager.insertTicketDetail(ticketDetail);
            } else {
                detail.setCloseDateTime(ticketDetail.getCloseDateTime());
                detail.setSeen(ticketDetail.isSeen());
                detail.setTicketStatus(ticketDetail.getTicketStatus());
                databaseManager.deleteTicketDetail(detail.getDetailId());
                databaseManager.insertTicketDetail(detail);
//                new Update(ModelTicketDetail.class)
//                        .set("CloseDateTime = ?",ticketDetail.getCloseDateTime())
//                        .set("TicketStatus = ?",ticketDetail.getTicketStatus())
//                        .set("CloseDateTime = ?",ticketDetail.getCloseDateTime())
//                        .set("CloseDateTime = ?",ticketDetail.getCloseDateTime())
//                        .where("parentId = ?", parentId)
//                        .execute();
            }
        }
    }

    public List<ModelTicketDetail> getModelTicketDetails() {
        return modelTicketDetails;
    }

    public void setModelTicketDetails(List<ModelTicketDetail> modelTicketDetails) {
        this.modelTicketDetails = modelTicketDetails;
    }
}
