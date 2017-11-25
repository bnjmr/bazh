package ir.jahanmirbazh.bazh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelTicketDetail;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterTicketReplayRecycler;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.components.PersianTextViewNormal;
import ir.jahanmirbazh.enums.EnumDialogKind;
import ir.jahanmirbazh.enums.EnumTicketStatus;
import ir.jahanmirbazh.events.EventOnOpenAttachmentFile;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSendGetTicketRequest;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnStartDownloadAttach;
import ir.jahanmirbazh.events.EventOnSuccessDownloadAttach;
import ir.jahanmirbazh.events.EventOnSuccessGetTicketDetail;
import ir.jahanmirbazh.events.EventOnSuccessGetTicketReply;
import ir.jahanmirbazh.events.EventOnSuccessUpload;
import ir.jahanmirbazh.events.EventOnUpdateCounts;
import ir.jahanmirbazh.events.EventOnUploadProgress;


public class ActivityShowTicket extends AppCompatActivity implements View.OnClickListener {

    long ticketId;
    List<ModelTicketDetail> ticketDetails;
    AdapterTicketReplayRecycler adapterTicketReplayRecycler;
    @Bind(R.id.lstTicketReplay)
    RecyclerView lstTicketReplay;
    @Bind(R.id.layCreateReplay)
    LinearLayout layCreateReplay;
    //    @Bind(R.id.layCloseTicket)
    LinearLayout layCloseTicket;
    @Bind(R.id.layMain)
    LinearLayout layMain;
    @Bind(R.id.layClose)
    LinearLayout layClose;
    @Bind(R.id.layToolbarShowTicketPage)
    LinearLayout layToolbarShowTicketPage;

    @Bind(R.id.txtStatus)
    TextView txtStatus;

    @Bind(R.id.txtRegDate)
    TextView txtRegDate;
    @Bind(R.id.txtCloseDateTime)
    TextView txtCloseDateTime;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;

    @Bind(R.id.progressWheelUploading)
    ProgressWheel progressWheelUploading;

    @Bind(R.id.layDownloading)
    LinearLayout layDownloading;

    @Bind(R.id.txtProgressTitle)
    PersianTextViewNormal txtProgressTitle;

    @Bind(R.id.layUploading)
    LinearLayout layUploading;

    @Bind(R.id.layCloseDateTime)
    LinearLayout layCloseDateTime;


    //    @Bind(R.id.imgCloseOrOpenTicket)
    ImageView imgCloseOrOpenTicket;


    FrameLayout layProgress;
    LinearLayout layDeleteAttachment;
    RelativeLayout layUpload;
    RelativeLayout laySend;

    boolean uploaded = true;
    boolean isRunningToUpload = false;
    boolean isSending = false;

    String txtFileUrl = "";
    String txtFileName = "";
    String fullFilePath = "";
    String tMessage;

//    MaterialDialog alertDialog;
//    MaterialDialog dialog;

    DatabaseManager databaseManager;
    int TICKET_ID;
    int TICKET_COUNT;
    int DetailId;

    File fileToUpload;
    DialogClass dialogClass;
    private String filename = "";
    private ProgressDialog pDialog;
    private String fileUri;
    private int TICKET_STATUS;
    private String TICKET_REGISTER_DATE;
    private String TICKET_CLOSE_DATE_TIME;
    private String TICKET_TITLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        dialogClass = new DialogClass(ActivityShowTicket.this);
        databaseManager = new DatabaseManager();


        Intent intent = getIntent();
        TICKET_ID = intent.getIntExtra("TICKET_ID", 0);
        TICKET_COUNT = intent.getIntExtra("TICKET_COUNT", 0);

        TICKET_STATUS = intent.getIntExtra("TICKET_STATUS", 0);
        TICKET_REGISTER_DATE = intent.getStringExtra("TICKET_REGISTER_DATE");
        TICKET_CLOSE_DATE_TIME = intent.getStringExtra("TICKET_CLOSE_DATE_TIME");
        TICKET_TITLE = intent.getStringExtra("TICKET_TITLE");


        WebService.sendGetTicketDetailRequest(this, TICKET_ID);
        initView();
    }

    private void initView() {
        List<ModelTicketDetail> modelTicketDetails = databaseManager.selectTicketDetail(TICKET_ID);
        if (modelTicketDetails != null && modelTicketDetails.size() != 0) {
            lstTicketReplay.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            lstTicketReplay.setLayoutManager(layoutManager);
            adapterTicketReplayRecycler = new AdapterTicketReplayRecycler(modelTicketDetails);
            lstTicketReplay.setAdapter(adapterTicketReplayRecycler);
        }

        layCreateReplay.setVisibility(View.GONE);

        switch (TICKET_STATUS) {
            case EnumTicketStatus.Acting:
                txtStatus.setText("درحال اقدام");
                layCreateReplay.setVisibility(View.VISIBLE);

                break;
            case EnumTicketStatus.Reviewing:
                txtStatus.setText("درحال بررسی");
                layCreateReplay.setVisibility(View.VISIBLE);

                break;
            case EnumTicketStatus.WaitingForPersonAnswer:
                txtStatus.setText("در انتظار پاسخ شخص");
                layCreateReplay.setVisibility(View.VISIBLE);

                break;
            case EnumTicketStatus.WaitingForReview:
                txtStatus.setText("در انتظاربررسی");
                layCreateReplay.setVisibility(View.VISIBLE);

                break;
        }
        txtRegDate.setText(TICKET_REGISTER_DATE);
        if (TICKET_CLOSE_DATE_TIME != null && !TICKET_CLOSE_DATE_TIME.equals("")) {
            txtCloseDateTime.setText(TICKET_CLOSE_DATE_TIME);
            layCreateReplay.setVisibility(View.GONE);

        } else {
            layCloseDateTime.setVisibility(View.GONE);
        }
        txtTitle.setText(TICKET_TITLE);


        layClose.setOnClickListener(this);


        layCreateReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog dialog = new MaterialDialog.Builder(ActivityShowTicket.this)
                        .cancelable(true)
                        .customView(R.layout.dialog_send_ticket, false)
                        .build();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                laySend = (RelativeLayout) dialog.findViewById(R.id.laySend);
                layUpload = (RelativeLayout) dialog.findViewById(R.id.layUpload);
                layDeleteAttachment = (LinearLayout) dialog.findViewById(R.id.layDeleteAttachment);
                layClose = (LinearLayout) dialog.findViewById(R.id.layClose);
                layProgress = (FrameLayout) dialog.findViewById(R.id.layProgress);
                LinearLayout layClose = (LinearLayout) dialog.findViewById(R.id.layClose);
                progressWheel = (ProgressWheel) dialog.findViewById(R.id.progressWheel);
                final EditText edtTicketMessage = (EditText) dialog.findViewById(R.id.edtTicketMessage);
                LinearLayout layTitle = (LinearLayout) dialog.findViewById(R.id.layTitle);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                layClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                layUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!V.isUploadingFile) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/* video/*");
                            startActivityForResult(intent, 0);
                        } else {
//                            DialogClass dialogError = new DialogClass(ActivityShowTicket.this);
                            dialogClass.DialogError("یک فایل در حال آپلود است، امکان آپلود فایل وجود ندارد.");
                        }
                    }
                });


                laySend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tMessage = edtTicketMessage.getText().toString();
                        if (tMessage.trim().length() == 0 && txtFileUrl.length() != 0) {
                            tMessage = "فایل پیوست";
                        }
                        if (!tMessage.equals("")) {
                            isSending = true;
                                /*hide soft keyboard*/
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            if (!fullFilePath.equals("")) {


                                try {
//                                    dialogClass.DialogWaiting();
                                    dialog.dismiss();
                                    layUploading.setVisibility(View.VISIBLE);
                                    WebService.upload(ActivityShowTicket.this, V.setting.getFileServerUrl(), fileToUpload, dialogClass);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
//                                dialogClass.DialogWaiting();
                                dialog.dismiss();
                                WebService.sendTicketReplayRequest(ActivityShowTicket.this, TICKET_ID, tMessage, filename);
                            }


                        } else {
                            edtTicketMessage.setHint("متن تیکت نباید خالی باشد");
                            edtTicketMessage.setHintTextColor(ContextCompat.getColor(getBaseContext(), R.color.color_red));
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            fullFilePath = U.getPath(ActivityShowTicket.this, targetUri);
            isRunningToUpload = true;
            uploaded = false;

            layUpload.setVisibility(View.GONE);
            layDeleteAttachment.setVisibility(View.GONE);
            layProgress.setVisibility(View.VISIBLE);
            fileToUpload = new File(fullFilePath);
            String[] format = fullFilePath.split("\\.");
            if (!U.isValidFormat(format[format.length - 1])) {
                fullFilePath = "";
                layUpload.setVisibility(View.VISIBLE);
                layDeleteAttachment.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventOnShowDialog("فرمت فایل انتخاب شده نامعتبر است", ActivityShowTicket.this, EnumDialogKind.error));
                return;
            }
            int file_size = Integer.parseInt(String.valueOf(fileToUpload.length() / 1024));
            if (file_size > 5000) {
                layUpload.setVisibility(View.VISIBLE);
                layDeleteAttachment.setVisibility(View.GONE);
                fullFilePath = "";
                EventBus.getDefault().post(new EventOnShowDialog("حداکثر حجم فابل انتخابی باید 5 مگابایت باشد", ActivityShowTicket.this, EnumDialogKind.error));
                return;
            }

//            U.uploadMultipart(this, fullFilePath, "uploadTicketFile", "tickets", true, V.setting.getFileServerUrl());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSendGetTicketRequest request) {
//        lstTicketReplay.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        List<ModelTicket> modelTicketList = databaseManager.selectAllTicket();
//        adapterTicketReplayRecycler = new AdapterTicketReplayRecycler(modelTicketList)
//    }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetTicketDetail event) {
        //ticket count ra dasti kam mikonim va dar db save mikonim
        int c = V.currentEstate.getTicketsCount() - TICKET_COUNT;
        if (c > 0) {
            V.currentEstate.setTicketsCount(V.currentEstate.getTicketsCount() - TICKET_COUNT);
        } else {
            V.currentEstate.setTicketsCount(0);
        }
        V.currentEstate.save();
        EventBus.getDefault().post(new EventOnUpdateCounts());
        lstTicketReplay.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstTicketReplay.setLayoutManager(layoutManager);
//        List<ModelTicket> modelTicketList = databaseManager.selectAllTicket();
        adapterTicketReplayRecycler = new AdapterTicketReplayRecycler(event.getModelTicketDetails());
        lstTicketReplay.setAdapter(adapterTicketReplayRecycler);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessUpload upload) {
        layUploading.setVisibility(View.GONE);
        filename = upload.getFilename();
        WebService.sendTicketReplayRequest(ActivityShowTicket.this, TICKET_ID, tMessage, filename);
        layUpload.setVisibility(View.GONE);
        layDeleteAttachment.setVisibility(View.VISIBLE);
        progressWheelUploading.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(final EventOnSuccessGetTicketReply reply) {
        layUploading.setVisibility(View.GONE);

        dialogClass.DialogWaitingClose();
        WebService.sendGetTicketDetailRequest(this, TICKET_ID);


        dialogClass.DialogOkMessage(reply.getMessage());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnStartDownloadAttach attach) {
        new ActivityShowTicket.DownloadFileFromURL().execute(attach.getUrl());
        DetailId = attach.getDetailId();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(final EventOnSuccessDownloadAttach attach) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layDownloading.setVisibility(View.GONE);
                File file = new File(attach.getFileUri());
                try {

                    openFile(ActivityShowTicket.this, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                final MaterialDialog dialog = new MaterialDialog.Builder(ActivityShowTicket.this)
//                        .customView(R.layout.dialog_success_download_file, false)
//                        .cancelable(true)
//                        .build();
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                LinearLayout layCancel = (LinearLayout) dialog.findViewById(R.id.layCancel);
//                TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
//                CardView layBtnOpenFile = (CardView) dialog.findViewById(R.id.layBtnOpenFile);
//
//                txtMessage.setText("فایل مورد نظر با موفقیت دانلود شد");
//                layCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                layBtnOpenFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        File file = new File(attach.getFileUri());
//                        try {
//
//                            openFile(v.getContext(), file);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//                dialog.show();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnOpenAttachmentFile file) {
        File file1 = new File(file.getFileUri());
        try {
            openFile(ActivityShowTicket.this, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnUploadProgress progress) {
        txtProgressTitle.setText("در حال بارگزاری . . .");
        progressWheelUploading.setVisibility(View.VISIBLE);
//        layDownloading.setVisibility(View.VISIBLE);
        progressWheelUploading.setProgress(progress.getProgress());
        if (progress.getProgress() == 100) {
            progressWheel.setVisibility(View.GONE);
            layDownloading.setVisibility(View.GONE);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError event) {
        layUploading.setVisibility(View.GONE);
        dialogClass.DialogWaitingClose();
        dialogClass.DialogError("عدم ارتباط با سرور \n لطفا پس از اطمینان از متصل بودن به اینترنت مجددا تلاش کنید");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnShowDialog event) {
        dialogClass.DialogWaitingClose();
        switch (event.getDialogKind()) {
            case success:
                dialogClass.DialogOkMessage(event.getMessage());
                break;
            case error:
                dialogClass.DialogError(event.getMessage());
                break;
        }
        dialogClass.DialogWaitingClose();


    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onSuccessEvent(EventOnUploadProgress progress) {
//        layUploading.setVisibility(View.VISIBLE);
//        progressWheel.setVisibility(View.VISIBLE);
//        progressWheel.setProgress(progress.getProgress());
//        if (progress.getProgress() == 100) {
//            progressWheel.setVisibility(View.GONE);
//            layUploading.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layClose:
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
                break;


        }
    }

    public static void openFile(Context context, File url) throws IOException {
        // Create URI
        File file = url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/zip");
        } else if (url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(0);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();
//            http://192.168.1.44:10965//Files/TicketAttachmentFiles/e82d6fd0-63bd-4592-9ba6-e282d69d74af.png

                String url0 = conection.getURL().toString();
                String[] name = url0.split("/");


                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/Download/" + name[name.length - 1]);

                fileUri = Environment
                        .getExternalStorageDirectory().toString()
                        + "/Download/" + name[name.length - 1];
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    EventBus.getDefault().post(new EventOnUploadProgress((int) ((total * 100) / lenghtOfFile)));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
//            Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(final String file_url) {
            // dismiss the dialog after the file was downloaded
//            dismissDialog(0);
            if (fileUri != null && !fileUri.equals("")) {
                EventBus.getDefault().post(new EventOnSuccessDownloadAttach(fileUri));
                ///storage/emulated/0/Download/663c373a-97dc-4ba6-b4b7-3e470d2dbdf3.jpeg
//                String[] s = fileUri.split("/");
//                String fileName = s[s.length-1];

            }


        }

    }

}