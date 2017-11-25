package ir.jahanmirbazh.bazh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ctrlplusz.anytextview.AnyEditTextView;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.enums.EnumDialogKind;
import ir.jahanmirbazh.events.EventOnSendGetTicketRequest;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessRegisterTicket;
import ir.jahanmirbazh.events.EventOnSuccessUpload;
import ir.jahanmirbazh.events.EventOnUploadProgress;

public class ActivitySendTicket extends AppCompatActivity {


    @Bind(R.id.layClose)
    LinearLayout layClose;
    @Bind(R.id.layToolbarSendTicketPage)
    LinearLayout layToolbarSendTicketPage;
    @Bind(R.id.layUploadFile)
    LinearLayout layUploadFile;
    @Bind(R.id.layUpload)
    LinearLayout layUpload;
    @Bind(R.id.laySend)
    LinearLayout laySend;
    @Bind(R.id.edtTicketTitle)
    AnyEditTextView edtTicketTitle;
    @Bind(R.id.edtTicketMessage)
    AnyEditTextView edtTicketMessage;
    @Bind(R.id.layProgress)
    FrameLayout layProgress;
    @Bind(R.id.layWaiting)
    LinearLayout layWaiting;
    @Bind(R.id.layDeleteAttachment)
    LinearLayout layDeleteAttachment;
    @Bind(R.id.layWriteTicket)
    LinearLayout layWriteTicket;

    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;

    @Bind((R.id.layUploading))
    LinearLayout layUploading;


    String txtTitle = "";
    String txtMessage = "";
    String txtFileUrl = "";
    MaterialDialog alertDialog;
    String txtFileName = "";
    String fullFilePath = "";
    String filename = "";

    boolean uploaded = true;
    boolean isRunningToUpload = false;
    boolean isSending = false;


    DialogClass dialogNoEstate;
    private final int Read_Storage = 100;

    DialogClass dialogClass;

    File fileToUpload;
    private int b;

    //Because i want to cancel it if user cancel it from onBackPress
//    Call<TicketDelivery> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_ticket);
        ButterKnife.bind(this);
        dialogClass = new DialogClass(ActivitySendTicket.this);
//        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        b = 1;
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });

        laySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle = edtTicketTitle.getText().toString();
                txtMessage = edtTicketMessage.getText().toString();
                if (txtTitle.equals("")) {
                    dialogClass.DialogError("وارد کردن 'عنوان تیکت' الزامی است");
                } else if (txtMessage.equals("")) {
                    dialogClass.DialogError("وارد کردن 'متن تیکت ' الزامی است");
                } else {
                    try {
//                        dialogClass.DialogWaiting();
                        if (fileToUpload != null) {
                            layUploading.setVisibility(View.VISIBLE);
                            layWaiting.setVisibility(View.VISIBLE);
                            WebService.upload(ActivitySendTicket.this, V.setting.getFileServerUrl(), fileToUpload, dialogClass);
                        } else {
                            dialogClass.DialogWaiting();
                            WebService.sendTicketReqisterRequest(ActivitySendTicket.this, txtMessage, filename, txtTitle);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        layUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Read_Storage);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant

                        return;
                    } else {
                        //                if (G.filesToUpload.isEmpty()) {
                        if (true) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/* video/*");
                            startActivityForResult(intent, 0);
                        } else {

                            dialogClass.DialogError("یک فایل در حال آپلود است، امکان آپلود فایل وجود ندارد.");
                        }
                    }
                } else {
                    //                if (G.filesToUpload.isEmpty()) {
                    if (true) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/* video/*");
                        startActivityForResult(intent, 0);
                    } else {

                        dialogClass.DialogError("یک فایل در حال آپلود است، امکان آپلود فایل وجود ندارد.");
                    }
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Read_Storage: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (true) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/* video/*");
                        startActivityForResult(intent, 0);
                    } else {
                        DialogClass dialogError = new DialogClass(ActivitySendTicket.this);
                        dialogError.DialogError("یک فایل در حال آپلود است، امکان آپلود فایل وجود ندارد.");
                    }
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
        WebService.sendGetTicketRequest(ActivitySendTicket.this);
        EventBus.getDefault().post(new EventOnSendGetTicketRequest());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            fullFilePath = U.getPath(ActivitySendTicket.this, targetUri);
            isRunningToUpload = true;
            uploaded = false;
            layUploadFile.setVisibility(View.GONE);
            layDeleteAttachment.setVisibility(View.VISIBLE);
//            layProgress.setVisibility(View.VISIBLE);
            fileToUpload = new File(fullFilePath);
            String[] format = fullFilePath.split("\\.");
            if (!U.isValidFormat(format[format.length - 1])) {
                fullFilePath = "";
                layUploadFile.setVisibility(View.VISIBLE);
                layDeleteAttachment.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventOnShowDialog("فرمت فایل انتخاب شده نامعتبر است", ActivitySendTicket.this, EnumDialogKind.error));
                return;
            }
            int file_size = Integer.parseInt(String.valueOf(fileToUpload.length() / 1024));
            if (file_size > 5120) {
                layUploadFile.setVisibility(View.VISIBLE);
                layDeleteAttachment.setVisibility(View.GONE);
                fullFilePath = "";
                EventBus.getDefault().post(new EventOnShowDialog("حداکثر حجم فابل انتخابی باید 5 مگابایت باشد", ActivitySendTicket.this, EnumDialogKind.error));
                return;
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessUpload upload) {
        filename = upload.getFilename();
        WebService.sendTicketReqisterRequest(ActivitySendTicket.this, txtMessage, filename, txtTitle);
        layUploadFile.setVisibility(View.GONE);
        layDeleteAttachment.setVisibility(View.VISIBLE);
        layProgress.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOnShowDialog event) {

        layUploadFile.setVisibility(View.VISIBLE);
        layDeleteAttachment.setVisibility(View.GONE);
        layProgress.setVisibility(View.GONE);
        dialogClass.DialogWaitingClose();
//        layLoading.setVisibility(View.GONE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessRegisterTicket ticket) {
        dialogClass.DialogWaitingClose();
        layUploading.setVisibility(View.GONE);
        layWaiting.setVisibility(View.GONE);
        if (b == 1) {
            dialogClass.DialogOkMessage(ticket.getMessage());
            b = 2;
        }


        layUploadFile.setVisibility(View.VISIBLE);
        layDeleteAttachment.setVisibility(View.GONE);
        edtTicketMessage.setText("");
        edtTicketTitle.setText("");
        fileToUpload = null;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnUploadProgress progress) {
        layUploading.setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.VISIBLE);
        progressWheel.setProgress(progress.getProgress());
        if (progress.getProgress() == 100) {
            progressWheel.setVisibility(View.GONE);
            layUploading.setVisibility(View.GONE);
        }
    }

}
