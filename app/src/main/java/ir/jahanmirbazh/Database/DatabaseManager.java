package ir.jahanmirbazh.Database;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import ir.jahanmirbazh.bazh.V;

/**
 * Created by FCC on 7/30/2017.
 */

public class DatabaseManager {

    Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public DatabaseManager() {
    }

    public void configDb() {
        Configuration dbConfiguration = new Configuration.Builder(context).setDatabaseName("Database.db").create();
        ActiveAndroid.initialize(dbConfiguration);

        if (new Select().from(ModelSetting.class).execute().isEmpty()) {
            ModelSetting modelSetting = new ModelSetting();
            modelSetting.save();
        }

//        configT_Setting();
//        configT_UserInfo();
    }

    public void insertUserInfo(ModelUserInfo userInfo) {
        userInfo.save();
    }

    public void insertEstatest(ModelEstate modelEstate) {
        modelEstate.save();
    }

    public void insertBill(ModelBill modelBill) {
        modelBill.save();
    }

    public void insertPayment(ModelPayment payment) {
        payment.save();
    }

    public void insertNews(ModelNews news) {
        news.save();
    }

    public void insertCost(ModelCost modelCost) {
        modelCost.save();
    }

    public void insertTicket(ModelTicket modelTicket) {
        modelTicket.save();
    }

    public void insertTicketDetail(ModelTicketDetail detail) {
        detail.save();
    }

    public void insertNewsDetail(ModelNewsDetail detail) {
        detail.save();
    }

    private void configT_Setting() {
//        ActiveAndroid.beginTransaction();
        ModelSetting setting = new ModelSetting();
        setting.setShowWizard(true);
        setting.save();
//        ActiveAndroid.endTransaction();

    }

    public ModelUserInfo readUserInfo() {
        return new Select().from(ModelUserInfo.class).executeSingle();
    }

    public ModelSetting readSetting() {
        return new Select().from(ModelSetting.class).executeSingle();
    }

    public List<ModelEstate> selectEstates() {
        return new Select().from(ModelEstate.class).execute();
    }

    public ModelEstate selectEstatesByEstateId(int EstateId) {
        return new Select().from(ModelEstate.class).where("EstateId = ?", EstateId).executeSingle();
    }

    public List<ModelBill> selectAllBills() {
        try {
            List<ModelBill> modelBills = new Select().from(ModelBill.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
            if (modelBills != null) {
                return modelBills;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<ModelPayment> selectAllpayments() {
        try {
            List<ModelPayment> paymentList = new Select().from(ModelPayment.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
            if (paymentList != null) {
                return paymentList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<ModelNews> selectAllNews() {
        try {
            List<ModelNews> newsList = new Select().from(ModelNews.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
            if (newsList != null) {
                return newsList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<ModelCost> selectAllCosts() {
        try {
            List<ModelCost> newsList = new Select().from(ModelCost.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
            if (newsList != null) {
                return newsList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<ModelTicket> selectAllTicket() {
        try {
            List<ModelTicket> modelTicketList = new Select().from(ModelTicket.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
            if (modelTicketList != null) {
                return modelTicketList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<ModelTicketDetail> selectTicketDetail(long parentId) {
        try {
            List<ModelTicketDetail> details = new Select().from(ModelTicketDetail.class).where("NewsId = ?", parentId).execute();
            if (details != null) {
                return details;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ModelNewsDetail selectNewsDetailByNewsId(int NewsId) {
        ModelNewsDetail modelNewsDetail =
                new Select().from(ModelNewsDetail.class).where("NewsId = ?", NewsId).executeSingle();
        if (modelNewsDetail != null) {
            return modelNewsDetail;
        } else {
            return null;
        }
    }

    public ModelCost selectCostByCostId(int CostId) {
        try {
            ModelCost modelCost = new Select().from(ModelCost.class).where("CostId = ?", CostId).executeSingle();
            if (modelCost != null) {
                return modelCost;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void clearT_UserInfo() {
        try {
            new Delete().from(ModelUserInfo.class).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_Estate() {
        try {
            new Delete().from(ModelEstate.class).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_News() {
        try {
            new Delete().from(ModelNews.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_Bill() {
        try {
            new Delete().from(ModelBill.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_Payment() {
        try {
            new Delete().from(ModelPayment.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_Cost() {
        try {
            new Delete().from(ModelCost.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearT_Ticket() {
        try {
            new Delete().from(ModelTicket.class).where("EstateId = ?", V.currentEstate.getEstateId()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteDb(Context context) {

    }

    public void deleteTicketDetail(int DetailId) {
        new Delete().from(ModelTicketDetail.class).where("DetailId = ?", DetailId).execute();
    }

    public void deleteNewsDetailByNewsId(int NewsId) {
        new Delete().from(ModelNewsDetail.class).where("NewsId = ?", NewsId).execute();
    }
}
