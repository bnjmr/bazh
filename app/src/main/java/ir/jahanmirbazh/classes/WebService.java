package ir.jahanmirbazh.classes;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelTicketDetail;
import ir.jahanmirbazh.bazh.U;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.enums.EnumDialogKind;
import ir.jahanmirbazh.enums.EnumMessageKind;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetTicketDetail;
import ir.jahanmirbazh.events.EventOnSuccessUpload;
import ir.jahanmirbazh.events.EventOnUploadProgress;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by FCC on 7/31/2017.
 */

public class WebService {
    private static long timeOut = 8;
    private static JsonParser parser = new JsonParser();

    public static void sendLoginRequest(final Context context, final String username, String pass) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Username", username)
                .add("Password", pass)
                .add("Device", String.valueOf(2))
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(V.URL + "Auth/Signin")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
                Logger.d("WebService sendLoginRequest onFailure ", e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendLoginRequest onResponse ", s);

                parser.pars(context, response.code(), s, EnumMessageKind.Signin);

            }
        });


    }

    public static void sendGetProfileRequest(final Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Profile")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context, EnumMessageKind.GetProfile));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetProfileRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.GetProfile);

            }
        });


    }

    public static void sendForgetPasswordRequest(final Context context, String Username, String Mobile) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("Username", Username)
                .add("Mobile", Mobile)
                .build();

        Request request = new Request.Builder()
//                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .post(body)
                .url(V.URL + "Auth/Remember")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendForgetPasswordRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.ForgetPassword);

            }
        });


    }

    public static void sendGetEstateRequest(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context, EnumMessageKind.Estate));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetEstateRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Estate);

            }
        });

    }

    public static void sendEditProfileRequest(final Context context, String pass, String newPss, String mobilenum) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Password", pass)
                .add("NewPassword", newPss)
                .add("Mobile", mobilenum)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Profile")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendEditProfileRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.EditProfile);

            }
        });

    }

    public static void sendGetBillRequest(final Context context, int EstateId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + EstateId + "/Bill")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetBillRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Bill);

            }
        });

    }

    public static void sendGetBillDetailRequest(final Context context, long billId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Bill/" + billId + "/Detail")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetBillDetailRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.BillDetail);

            }
        });

    }

    //pardakht ghabz az etebar
    public static void sendPayBillRequest(final Context context, long billId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Bill/" + billId + "/pay")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendPayBillRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.BillPay);

            }
        });

    }

    public static void sendGetPaymentRequest(final Context context, int EstateId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + EstateId + "/Payment")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetPaymentRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Payment);

            }
        });

    }

    public static void sendGetNewsRequest(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/News")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetNewsRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.News);

            }
        });

    }

    public static void sendGetNewsDetailRequest(final Context context, final int newsId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/News/" + newsId + "/Detail")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context, EnumMessageKind.NewsDetail));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetNewsDetailRequest onResponse ", s);
                V.NewsId = newsId;
                parser.pars(context, response.code(), s, EnumMessageKind.NewsDetail);

            }
        });

    }

    public static void sendGetCostRequest(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Cost/"+V.StartDate+"/"+V.EndDate)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetCostRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.cost);

            }
        });

    }

    public static void sendGetTicketRequest(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Ticket")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetTicketRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Ticket);

            }
        });

    }

    public static void sendGetTicketDetailRequest(final Context context, final int ticketId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Ticket/" + ticketId + "/Detail")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetTicketRequest onResponse ", s);


                try {
                    JSONObject content = new JSONObject(s);
                    JSONObject jsonObject = content.getJSONObject("Content");
                    JSONArray jsonArray = jsonObject.getJSONArray("Details");
                    String ss = jsonArray.toString();
                    ModelTicketDetail[] modelTicketDetail = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(ss, ModelTicketDetail[].class);
                    for (ModelTicketDetail modelTicketDetail1 : modelTicketDetail) {
                        modelTicketDetail1.setCloseDateTime(jsonObject.getString("CloseDateTime"));
                        modelTicketDetail1.setParent(ticketId);
                    }
                    EventBus.getDefault().post(new EventOnSuccessGetTicketDetail(Arrays.asList(modelTicketDetail)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parser.pars(context, response.code(), s, EnumMessageKind.TicketDetail);

            }
        });

    }

    public static void sendGetNotificationRequest(final Context context) {
        DatabaseManager db;
        db = new DatabaseManager(context);
//        configDatabase();
        db.configDb();
//        ActiveAndroid.initialize(this);
        V.currentEstate = U.getCurrentEstate(context);

        V.userInfo = db.readUserInfo();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Notification")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetNotificationRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Notification);

            }
        });

    }

    public static void sendTicketReplayRequest(final Context context, int ticketId, String Text, String Filename) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Text", Text)
                .add("Filename", Filename)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Ticket/" + ticketId + "/Reply")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetTicketRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.TicketReply);

            }
        });

    }

    public static void upload(final Context context, String url, File file, final DialogClass dialogClass) throws IOException {

        V.isUploadingFile = true;


        OkHttpClient client = new OkHttpClient();
        MultipartBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("text/plain"), file))
                .addFormDataPart("other_field", "other_field_value")
                .build();

        //wrap your original request body with progress
        RequestBody requestBody = ProgressHelper.withProgress(formBody, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);
                Log.e("TAG", "onUIProgressStart:" + totalBytes);
            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                Log.e("TAG", "=============start===============");
                Log.e("TAG", "numBytes:" + numBytes);
                Log.e("TAG", "totalBytes:" + totalBytes);
                Log.e("TAG", "percent:" + percent);
                Log.e("TAG", "speed:" + speed);
                Log.e("TAG", "============= end ===============");

                int pog = (int) (100 * percent);
                Log.e("pog", "pog:" + pog);

                EventBus.getDefault().post(new EventOnUploadProgress(pog));
//                uploadProgress.setProgress((int) (100 * percent));
//                uploadInfo.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
            }

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();
                Log.e("TAG", "onUIProgressFinish:");
            }

        });
        ProgressHelper.withProgress(formBody, new ProgressUIListener() {
            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                Logger.d("upload progressBar", "numBytes" + numBytes + "totalBytes" + totalBytes + "percent" + percent + "speed" + speed);
                int d = (int) (numBytes / totalBytes) * 100;
                EventBus.getDefault().post(new EventOnUploadProgress(d));
            }
        });

        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                V.isUploadingFile = false;
                Logger.d("upload", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response onResponse) throws IOException {
                V.isUploadingFile = false;
                Logger.d("upload", "onResponse");
                String s = onResponse.body().string();
                Logger.d("upload", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean result = jsonObject.getBoolean("result");
                    String message = jsonObject.getString("message");
                    String filename = jsonObject.getString("filename");
                    if (result) {
                        EventBus.getDefault().post(new EventOnSuccessUpload(filename));
                    } else {
                        EventBus.getDefault().post(new EventOnShowDialog(message, context, EnumDialogKind.error));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                parser.pars(context, onResponse.code(), s, EnumMessageKind.upload);
            }
        });


//        Response response = this.client.newCall(request).execute();
    }

    public static void sendTicketReqisterRequest(final Context context, String text, String fileName, String title) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("Text", text)
                .add("Filename", fileName)
                .add("Title", title)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Ticket/Register")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendTicketReqisterRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.registerTicket);
            }
        });


    }

    public static void sendGetDashboardRequest(final Context context) {

        if (V.userInfo == null || V.userInfo.getToken().equals(""))
            return;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Dashboard")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetDashboardRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.Dashboard);

            }
        });


    }

    /**
     * 1 add  2 remove
     **/
    public static void sendFirebaseToken(Context context, final int addOrRemove) {
        U.updateFirebaseToken(context);
        if (V.userInfo == null) {
            Logger.d("WebService sendFirebaseToken (V.userInfo == null) ", "return");
            return;
        }

        if (addOrRemove == 1 && V.userInfo.getNewToken() == null) {
            Logger.d("WebService sendFirebaseToken V.userInfo.getNewToken() == null ", "return");
            return;
        }
        if (addOrRemove == 2 && V.userInfo.getOldToken() == null) {
            Logger.d("WebService sendFirebaseToken V.userInfo.getOldToken() == null", "return");
            return;
        }

        Logger.d("WebService sendFirebaseToken == ", V.userInfo.getOldToken());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();
        RequestBody body;
        if (addOrRemove == 1) {
            body = new FormBody.Builder()
                    .add("NewToken", V.userInfo.getNewToken())
                    .add("Device", String.valueOf(2))
                    .build();
        } else {
            String oldToken;

            if (V.userInfo.getOldToken().equals("")) {
                oldToken = V.userInfo.getNewToken();
            } else {
                oldToken = V.userInfo.getOldToken();
            }
            body = new FormBody.Builder()
                    .add("OldToken", oldToken)
                    .add("Device", String.valueOf(2))
                    .build();
        }


        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Profile/Token/Firebase")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendFirebaseToken onResponse ", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean Result = jsonObject.getBoolean("Result");
                    if (addOrRemove == 1 && Result) {
                        V.setting.setTokenSend(true);
                        V.setting.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                parser.pars(context, response.code(), s, EnumMessageKind.TicketReply);

            }
        });

    }

    public static void sendGetBankListRequest(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Bank")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendGetBankListRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.BankList);

            }
        });

    }

    public static void sendPayWhitAmountRequest(final Context context, int amount, int bankId, String Description) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(V.currentEstate.getEstateId()))
                .add("bankId", String.valueOf(bankId))
                .add("amount", String.valueOf(amount))
                .add("Description", Description)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/pay/Online/" + amount + "/" + bankId)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendPayWhitAmountRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.PayResponse);

            }
        });

    }

    public static void sendPayWhitBillIdRequest(final Context context, int billId, int bankId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(V.currentEstate.getEstateId()))
                .add("bankId", String.valueOf(bankId))
                .add("billId", String.valueOf(billId))
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "Estate/" + V.currentEstate.getEstateId() + "/Bill/" + billId + "/pay/Online/" + bankId)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendPayWhitAmountRequest onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.PayResponse);
            }
        });

    }

    public static void sendCheckUpdate(final Context context ) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();


        Request request = new Request.Builder()
                .get()
                .addHeader("X-My-Token", V.userInfo.getToken())
                .url(V.URL + "/Update/2")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnRequestError(context));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Logger.d("WebService sendCheckUpdate onResponse ", s);
                parser.pars(context, response.code(), s, EnumMessageKind.update);
            }
        });

    }

}
