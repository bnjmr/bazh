package ir.jahanmirbazh.classes;

import android.content.Context;
import android.content.Intent;

import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ir.jahanmirbazh.Database.ModelBill;
import ir.jahanmirbazh.Database.ModelBillDetail;
import ir.jahanmirbazh.Database.ModelCost;
import ir.jahanmirbazh.Database.ModelEstate;
import ir.jahanmirbazh.Database.ModelNews;
import ir.jahanmirbazh.Database.ModelNewsDetail;
import ir.jahanmirbazh.Database.ModelNotification;
import ir.jahanmirbazh.Database.ModelPayment;
import ir.jahanmirbazh.Database.ModelTicket;
import ir.jahanmirbazh.Database.ModelUserInfo;
import ir.jahanmirbazh.Gson.ModelBank;
import ir.jahanmirbazh.Gson.getUpdate;
import ir.jahanmirbazh.bazh.ActivityLogin;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.enums.EnumDialogKind;
import ir.jahanmirbazh.enums.EnumMessageKind;
import ir.jahanmirbazh.events.EventOnGetUpdateResponse;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetBanks;
import ir.jahanmirbazh.events.EventOnSuccessGetBill;
import ir.jahanmirbazh.events.EventOnSuccessGetBillDetail;
import ir.jahanmirbazh.events.EventOnSuccessGetCost;
import ir.jahanmirbazh.events.EventOnSuccessGetDashboard;
import ir.jahanmirbazh.events.EventOnSuccessGetEstates;
import ir.jahanmirbazh.events.EventOnSuccessGetEstatestEmpty;
import ir.jahanmirbazh.events.EventOnSuccessGetNews;
import ir.jahanmirbazh.events.EventOnSuccessGetNewsDetail;
import ir.jahanmirbazh.events.EventOnSuccessGetNotification;
import ir.jahanmirbazh.events.EventOnSuccessGetPayUrl;
import ir.jahanmirbazh.events.EventOnSuccessGetPayments;
import ir.jahanmirbazh.events.EventOnSuccessGetTicket;
import ir.jahanmirbazh.events.EventOnSuccessGetTicketReply;
import ir.jahanmirbazh.events.EventOnSuccessGetUserInfo;
import ir.jahanmirbazh.events.EventOnSuccessPayBill;
import ir.jahanmirbazh.events.EventOnSuccessRegisterTicket;
import ir.jahanmirbazh.events.EventOnSuccessUpload;

import static ir.jahanmirbazh.bazh.G.gson;

/**
 * Created by FCC on 7/30/2017.
 */

public class JsonParser {

    public void pars(Context context, int code, String json, EnumMessageKind messageKind) {


        Logger.d("pars", json);
        boolean result = false;
        String message = "";
        JSONObject content = null;
        try {
            content = new JSONObject(json);
            result = content.getBoolean("Result");
            message = content.getString("Message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (code == 401) {
            Intent intent = new Intent(context, ActivityLogin.class);
            intent.putExtra("shouldShowDialog", true);
            intent.putExtra("Message", "");
            context.startActivity(intent);
            V.setting.setLogin(false);
            V.setting.save();
            return;
        }

        if (!result) {
            if (!message.equals(""))
                EventBus.getDefault().post(new EventOnShowDialog(message, context, EnumDialogKind.error));
            EventBus.getDefault().post(new EventOnRequestError());
        } else {
            switch (messageKind) {
                case Signin:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        ModelUserInfo userInfo = gson.fromJson(object.toString(), ModelUserInfo.class);
                        EventBus.getDefault().post(new EventOnSuccessGetUserInfo(userInfo));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case ForgetPassword:
                    EventBus.getDefault().post(new EventOnShowDialog(message, context, EnumDialogKind.success));
                    break;
                case Estate:
                    try {

                        JSONObject object = content.getJSONObject("Content");
                        V.ShowCosts = object.getBoolean("ShowCosts");
                        V.userInfo.setName(object.getString("Name"));
                        JSONArray jsonArray = object.getJSONArray("List");

                        String s = jsonArray.toString();
                        ModelEstate[] modelEstates = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelEstate[].class);
                        if (modelEstates.length > 0) {
                            EventBus.getDefault().post(new EventOnSuccessGetEstates(context, modelEstates));
                        } else {
                            EventBus.getDefault().post(new EventOnSuccessGetEstatestEmpty());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case EditProfile:
                    EventBus.getDefault().post(new EventOnShowDialog(message, context, EnumDialogKind.success));
                    break;

                case Bill:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        JSONArray jsonArray = object.getJSONArray("List");
                        V.currentEstate.setBillsCount(object.getInt("BillsCount"));
                        V.currentEstate.save();
                        String s = jsonArray.toString();
                        ModelBill[] modelBills = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelBill[].class);
                        EventBus.getDefault().post(new EventOnSuccessGetBill(context, Arrays.asList(modelBills)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case BillDetail:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        String s = object.toString();
                        ModelBillDetail billDetail = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelBillDetail.class);
                        EventBus.getDefault().post(new EventOnSuccessGetBillDetail(billDetail));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case BillPay:
                    EventBus.getDefault().post(new EventOnShowDialog(message, context, EnumDialogKind.success));
                    EventBus.getDefault().post(new EventOnSuccessPayBill());
                    break;

                case Payment:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        V.OnlinePayment = object.getBoolean("c");
                        JSONArray jsonArray = object.getJSONArray("List");
                        String s = jsonArray.toString();
                        ModelPayment[] modelPayment = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelPayment[].class);
                        EventBus.getDefault().post(new EventOnSuccessGetPayments(Arrays.asList(modelPayment)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case News:
                    try {
                        JSONArray object = content.getJSONArray("Content");
                        //{"Result":true,"Message":null,"Content":[{"NewsId":1012,"Title":",nm,.nm.bnm.bnm.","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1010,"Title":"ccccccccccccc","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1008,"Title":"asfasf","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1007,"Title":"asfasf","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1006,"Title":"new","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1005,"Title":"asf","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1004,"Title":"Test","StartDate":"1396/06/16","IsSeen":true},{"NewsId":1003,"Title":"titkleee","StartDate":"1396/06/15","IsSeen":true},{"NewsId":1002,"Title":"تست پیامک خبر","StartDate":"1396/06/15","IsSeen":true},{"NewsId":2,"Title":"تغییر هزینه آب بلوک های شرقی فصل تابستان","StartDate":"1396/05/21","IsSeen":true}]}
                        String s = object.toString();

                        ModelNews[] newses = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelNews[].class);
                        for (ModelNews news : newses) {
                            news.setEstateId(V.currentEstate.getEstateId());
                        }
                        EventBus.getDefault().post(new EventOnSuccessGetNews(Arrays.asList(newses)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case NewsDetail:
                    try {
                        JSONObject jsonObject = content.getJSONObject("Content");
                        String s = jsonObject.toString();
                        ModelNewsDetail modelNewsDetail = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelNewsDetail.class);
                        modelNewsDetail.setNewsId(V.NewsId);
                        EventBus.getDefault().post(new EventOnSuccessGetNewsDetail(modelNewsDetail));
                        V.NewsId = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case cost:
                    try {
                        //{"Result":true,"Message":null,"Content":{"ShowCosts":true,"List":[{"CostId":1,"Amount":150000,"CostDate":"1396/05/01","Title":"اولین هزینه"},{"CostId":2,"Amount":500000,"CostDate":"1396/04/01","Title":"عوارض شهرداری"},{"CostId":3,"Amount":2500000,"CostDate":"1396/05/02","Title":"هزینه فاضلاب"},{"CostId":4,"Amount":70000,"CostDate":"1396/04/13","Title":"نظافت تیر ماه"},{"CostId":5,"Amount":67000,"CostDate":"1396/05/15","Title":"هزینه خرید لامپ های پارکینگ"},{"CostId":6,"Amount":8000,"CostDate":"1396/05/19","Title":"تست ویرایش"}]}}
                        JSONObject object = content.getJSONObject("Content");
                        boolean b = object.getBoolean("ShowCosts");
                        if (V.ShowCosts != b) {
                            V.ShowCosts = b;
                            EventBus.getDefault().post(new EventOnSuccessGetEstates());
                        }

                        JSONArray jsonArray = object.getJSONArray("List");
                        String s = jsonArray.toString();
                        ModelCost[] modelCosts = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelCost[].class);
                        EventBus.getDefault().post(new EventOnSuccessGetCost(Arrays.asList(modelCosts)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Ticket:
                    try {
                        JSONObject jsonObject = content.getJSONObject("Content");
                        JSONArray List = jsonObject.getJSONArray("List");
                        String s = List.toString();
                        String FileServerUrl = jsonObject.getString("FileServerUrl");
                        V.setting.setFileServerUrl(FileServerUrl);
                        V.setting.save();
                        ModelTicket[] modelTickets = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelTicket[].class);
                        EventBus.getDefault().post(new EventOnSuccessGetTicket(Arrays.asList(modelTickets)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case TicketDetail:
//                    try {
//                        JSONObject jsonObject = content.getJSONObject("Content");
//                        JSONArray jsonArray = jsonObject.getJSONArray("Details");
//                        String s = jsonArray.toString();
//                        ModelTicketDetail[] modelTicketDetail = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelTicketDetail[].class);
//                        for (ModelTicketDetail modelTicketDetail1 : modelTicketDetail) {
//                            modelTicketDetail1.setCloseDateTime(jsonObject.getString("CloseDateTime"));
//                        }
//                        EventBus.getDefault().post(new EventOnSuccessGetTicketDetail(Arrays.asList(modelTicketDetail)));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    break;

                case Notification:
                    try {
                        JSONArray jsonArray = content.getJSONArray("Content");
                        String s = jsonArray.toString();
                        ModelNotification[] modelNotification = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(s, ModelNotification[].class);
                        EventBus.getDefault().post(new EventOnSuccessGetNotification(Arrays.asList(modelNotification)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case upload:
                    try {
                        String fileName = content.getString("filename");
                        EventBus.getDefault().post(new EventOnSuccessUpload(fileName));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case registerTicket:
                    EventBus.getDefault().post(new EventOnSuccessRegisterTicket(message));
                    break;
                case TicketReply:

                    EventBus.getDefault().post(new EventOnSuccessGetTicketReply(message));
                    break;

                case Dashboard:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        int Debt = object.getInt("Debt");
                        int Credit = object.getInt("Credit");
                        int NotificationsCount = object.getInt("NotificationsCount");
                        V.currentEstate.setCredit(Credit);
                        V.currentEstate.setDebt(Debt);
                        V.currentEstate.setNotificationsCount(NotificationsCount);
                        V.currentEstate.save();
                        EventBus.getDefault().post(new EventOnSuccessGetDashboard());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case GetProfile:
                    try {
                        JSONObject object = content.getJSONObject("Content");
                        String Name = object.getString("Name");
                        String Mobile = object.getString("Mobile");

                        V.userInfo.setName(Name);
                        V.userInfo.setMobile(Mobile);
                        V.userInfo.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case BankList:
                    try {
                        JSONArray jsonArray = content.getJSONArray("Content");
                        String s = jsonArray.toString();
                        ModelBank[] modelBank = new GsonBuilder().create().fromJson(s, ModelBank[].class);

                        EventBus.getDefault().post(new EventOnSuccessGetBanks(Arrays.asList(modelBank)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case PayResponse:
                    //{"Result":true,"Message":null,"Content":{"Url":"http://pay.bazh.ir//Pay/2000003101"}}
                    try {
                        JSONObject jsonObject = content.getJSONObject("Content");
                        String Url = jsonObject.getString("Url");
                        EventBus.getDefault().post(new EventOnSuccessGetPayUrl(Url));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case update:
                    try {
                        JSONObject jsonObject = content.getJSONObject("Content");
                        String s = jsonObject.toString();
                        getUpdate response = gson.fromJson(s, getUpdate.class);
                        if (response.getVersion() == 0.0)
                            return;

                        EventBus.getDefault().post(new EventOnGetUpdateResponse(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }


    }
}
