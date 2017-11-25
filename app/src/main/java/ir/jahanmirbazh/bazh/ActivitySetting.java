package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.BuildConfig;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnShowDialog;

public class ActivitySetting extends AppCompatActivity {


    @Bind(R.id.layClose)
    LinearLayout layClose;
    @Bind(R.id.layToolbarSettingPage)
    LinearLayout layToolbarSettingPage;
    @Bind(R.id.layUpdateProfile)
    LinearLayout layUpdateProfile;
    @Bind(R.id.edtOldPassword)
    EditText edtOldPassword;
    @Bind(R.id.edtNewPassword)
    EditText edtNewPassword;
    @Bind(R.id.edtRapidNewPassword)
    EditText edtRapidPassword;
    @Bind(R.id.edtMobile)
    EditText edtMobile;
    @Bind(R.id.laySendProfile)
    RippleView laySendProfile;
    @Bind(R.id.layExpandProfile)
    LinearLayout layExpandProfile;
    @Bind(R.id.layExpandAbout)
    LinearLayout layExpandAbout;
    @Bind(R.id.layDeleteAllData)
    LinearLayout layDeleteAllData;
    @Bind(R.id.layDeleteDatabase)
    RippleView layDeleteDatabase;
    @Bind(R.id.layExpandDatabase)
    LinearLayout layExpandDatabase;
    @Bind(R.id.layAboutText)
    LinearLayout layAboutText;
    @Bind(R.id.layDisableNotification)
    LinearLayout layDisableNotification;
    @Bind(R.id.chkActive)
    AppCompatCheckBox chkActive;
    @Bind(R.id.layExpandNotification)
    LinearLayout layExpandNotification;
    @Bind(R.id.layUpdateSoftware)
    LinearLayout layUpdateSoftware;
    @Bind(R.id.layAbout)
    LinearLayout layAbout;
    @Bind(R.id.imgExpandProfile)
    ImageView imgExpandProfile;
    @Bind(R.id.imgExpandDatabase)
    ImageView imgExpandDatabase;
    @Bind(R.id.imgExpandNotify)
    ImageView imgExpandNotify;
    @Bind(R.id.imgExpandAbout)
    ImageView imgExpandAbout;

    @Bind(R.id.txtBazhSite)
    TextView txtBazhSite;
    @Bind(R.id.txtJahanmirSite)
    TextView txtJahanmirSite;
    @Bind(R.id.txtJahanmirTel)
    TextView txtJahanmirTel;

    @Bind(R.id.txtVersion)
    TextView txtVersion;
    //    @Bind(R.id.imgExpandColorSetting) ImageView imgExpandColorSetting;
//    @Bind(R.id.layColorSetting) LinearLayout layColorSetting;
//    @Bind(R.id.layColorAmber) LinearLayout layColorAmber;
//    @Bind(R.id.layColorBrown) LinearLayout layColorBrown;
//    @Bind(R.id.layColorGrey) LinearLayout layColorGrey;
//    @Bind(R.id.layColorTeal) LinearLayout layColorTeal;
//    @Bind(R.id.layColorPurple) LinearLayout layColorPurple;
//    @Bind(R.id.layColorPink) LinearLayout layColorPink;
//    @Bind(R.id.layColorRed) LinearLayout layColorRed;
//    @Bind(R.id.layColorBlue) LinearLayout layColorBlue;
//    @Bind(R.id.layColorGreen) LinearLayout layColorGreen;
//    @Bind(R.id.layColorOrange) LinearLayout layColorOrange;
//    @Bind(R.id.layExpandColorSetting) LinearLayout layExpandColorSetting;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    /**
     * setting chatroom
     */

//    @Bind(R.id.layChatroomSetting) LinearLayout layChatroomSetting;
//    @Bind(R.id.layDisableChatSound) LinearLayout layDisableChatSound;
//    @Bind(R.id.layDisableChatNotify) LinearLayout layDisableChatNotify;
//    @Bind(R.id.imgExpandChatroomSetting) ImageView imgExpandChatroomSetting;
//    @Bind(R.id.chkActiveChatNotify) AppCompatCheckBox chkActiveChatNotify;
//    @Bind(R.id.chkActiveChatSound) AppCompatCheckBox chkActiveChatSound;
//    @Bind(R.id.layExpandChatroomSetting) LinearLayout layExpandChatroomSetting;

    /**
     * setting chatroom
     */


    boolean uploaded = true;
    boolean isRunningToUpload = false;
    boolean isSendingRequestChangePofile = false;
    boolean isSendingRequestChangeImage = false;

    String txtFileUrl = "";
    String txtFileName = "";
    String fullFilePath = "";
    String tMessage;

    DatabaseManager databaseManager;
    WebService webService;
    DialogClass dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        databaseManager = new DatabaseManager();
        webService = new WebService();
        dialogClass = new DialogClass(ActivitySetting.this);

//        layToolbarSettingPage.setBackgroundColor(Color.parseColor("#" + G.setting.appColor));

//        txtAbout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//        txtAbout.setLineSpacing(5);
//        txtAbout.setTextColor(R.color.color_light_gray);
//        txtAbout.setAlignment(Paint.Align.RIGHT);
//        txtAbout.setTypeFace(Typeface.createFromAsset(getAssets(), "fonts/biran.ttf"));


//        U.tintWidget(edtPassword,R.color.color_white);
        U.tintWidget(edtRapidPassword, R.color.color_white);
        U.tintWidget(edtMobile, R.color.color_white);

        txtVersion.setText(String.valueOf("نسخه : "+BuildConfig.VERSION_NAME));

//        edtName.setText("" + G.setting.personName);
//        edtUserName.setText("" + G.setting.username);
        edtMobile.setText(V.userInfo.getMobile());


        /** initialize setting by value in database*/

//        Bitmap bitmap = BitmapFactory.decodeFile(G.APP_FOLDER + "user/" + G.setting.personImage);
//        if (bitmap != null) {
//            bitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, false);
//            imgUser.setImageBitmap(bitmap);
//        }
//        getApplicationColorToSetInSetting();
        /** initialize setting by value in database*/


        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        chkActive.setChecked(G.LocalMemory.getBoolean("GET_NOTIFY", true));
//        chkActiveChatNotify.setChecked(G.LocalMemory.getBoolean("GET_CHAT_NOTIFY", true));
//        chkActiveChatSound.setChecked(G.LocalMemory.getBoolean("GET_CHAT_SOUND", true));

        /*Expand and Collapse View*/

        layAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!layExpandAbout.isShown()) {
                    U.expand(layExpandAbout);
                    imgExpandAbout.setRotation(180);
//                    txtAbout.setText("شرکت برنامه سازان جهانمیر در سال 1390 به منظور فعالیت در زمینه نرم افزار تاسیس گردید و راه پیشرفت در این عرصه را به منظور کسب تجربه وخدمت به پیشرفت نرم افزاری به همراه مدیران و متخصصین نرم افزار پیموده است.\n" +
//                            " \n" +
//                            "همواره اهداف مديران متعهد و همکاران متخصص ما بر شناخت نیازهاي مشتريان  و ارائه خدمات بهینه مبتنی بر پیشرفته ترين تکنولوژي هاي روز دنیا به سراسرکشور می باشد.\n" +
//                            "\n" +
//                            "هم اکنون اين مجموعه شامل 6 تیم جداگانه، طراحی و ساخت سیستم جامع مدیریت ارتباط با مشتری اسپا، نرم افزارهای تحت ویندوز، نرم افزارهای تحت مک، برنامه های موبایل، طراحی و توسعه وب سایت و طراحی گرافیک با گردآوري اين تیم ها تحت نامی يکسان سعی در ارائه کامل خدمات نرم افزاری به سازمانها و نهادهاي دولتی، شرکتهاي خصوصی و اشخاص دارد.");
                } else {
                    U.collapse(layExpandAbout);
                    imgExpandAbout.setRotation(0);
//                    txtAbout.setText("");
                }

//                scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        scrollView.post(new Runnable() {
//                            public void run() {
//                                scrollView.fullScroll(View.FOCUS_DOWN);
//                            }
//                        });
//                    }
//                });

            }
        });


        layUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!layExpandProfile.isShown()) {
                    U.expand(layExpandProfile);
                    imgExpandProfile.setRotation(180);
                } else {
                    U.collapse(layExpandProfile);
                    imgExpandProfile.setRotation(0);
                }
            }
        });
        layDeleteAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!layExpandDatabase.isShown()) {
                    U.expand(layExpandDatabase);
                    imgExpandDatabase.setRotation(180);
                } else {
                    U.collapse(layExpandDatabase);
                    imgExpandDatabase.setRotation(0);
                }
            }
        });
        layDisableNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!layExpandNotification.isShown()) {
                    U.expand(layExpandNotification);
                    imgExpandNotify.setRotation(180);
                } else {
                    U.collapse(layExpandNotification);
                    imgExpandNotify.setRotation(0);
                }
            }
        });

        txtJahanmirTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+987138015";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

//        layChatroomSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!layExpandChatroomSetting.isShown()) {
//                    U.expand(layExpandChatroomSetting);
//                    imgExpandChatroomSetting.setRotation(180);
//                } else {
//                    U.collapse(layExpandChatroomSetting);
//                    imgExpandChatroomSetting.setRotation(0);
//                }
//            }
//        });

//        layColorSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!layExpandColorSetting.isShown()) {
//                    U.expand(layExpandColorSetting);
//                    imgExpandColorSetting.setRotation(180);
//                } else {
//                    U.collapse(layExpandColorSetting);
//                    imgExpandColorSetting.setRotation(0);
//                }
//            }
//        });


        txtBazhSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bazh.ir"));
                startActivity(browserIntent);
            }
        });

        txtJahanmirSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jahanmirbazh.ir"));
                startActivity(browserIntent);
            }
        });

        /***********************************************************************************/
        /*setOnClickListener*/

        chkActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                    G.LocalMemory.edit().putBoolean("GET_NOTIFY", true).commit();
//                else
//                    G.LocalMemory.edit().putBoolean("GET_NOTIFY", false).commit();
            }
        });
        laySendProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txtMessageChangeProfile.setText("");
                String pass = "";
                String newPass = "";
                String mobileNumber = "";

                String txtNewPassword = edtNewPassword.getText().toString().trim();
                String txtOldPassword = edtOldPassword.getText().toString().trim();
                String txtRapidPassword = edtRapidPassword.getText().toString().trim();
                String txtMobile = edtMobile.getText().toString().trim();

                if (txtNewPassword.length() > 0 || txtRapidPassword.length() > 0 || txtOldPassword.length() > 0) {
                    mobileNumber = V.userInfo.getPhone().toString();
                    if (txtNewPassword.length() == 0 && txtRapidPassword.length() == 0) {
                        dialogClass.DialogError("برای تغییر رمز عبور لطفا مقادیر \" رمز عبور جدید \" و \" تکرار رمز عبور جدید \" را وارد کنید ");
                        return;
//                    } else if (txtNewPassword.length() != 0 || txtRapidPassword.length() != 0 && txtOldPassword.length() == 0) {
//                        dialogClass.DialogError("لطفا رمز قبلی خود را وارد کنید");
//                        return;
                    } else if (txtRapidPassword.length() == 0) {
//                        DialogClass dialogError = new DialogClass(ActivitySetting.this);
                        dialogClass.DialogError("لطفا رمز عبور را در قسمت تکرار پسورد وارد کنید");
                        return;
                    }
                    if (txtNewPassword.length() != 0 && txtRapidPassword.length() != 0 && txtOldPassword.length() == 0) {
                        dialogClass.DialogError("لطفا رمز قبلی خود را وارد کنید");
                        return;
                    } else if (!txtNewPassword.equals(txtRapidPassword)) {
//                        DialogClass dialogError = new DialogClass(ActivitySetting.this);
                        dialogClass.DialogError("رمز عبور وارد شده همخوانی ندارد");
                        return;
                    }
                    if (txtMobile.length() > 0) {
                        // show dialog waiting
                        mobileNumber = txtMobile;

                    }
                    dialogClass.DialogWaiting();
                    pass = txtOldPassword;
                    newPass = txtNewPassword;
                    webService.sendEditProfileRequest(ActivitySetting.this, pass, newPass, mobileNumber);
                }

                if (txtNewPassword.length() == 0 && txtRapidPassword.length() == 0 && txtMobile.length() > 0) {

                    isSendingRequestChangePofile = true;
                    mobileNumber = txtMobile;
                    // show dialog waiting

                    dialogClass.DialogWaiting();
                    webService.sendEditProfileRequest(ActivitySetting.this, pass, newPass, mobileNumber);

                }
                if (txtMobile.length() == 0 && txtNewPassword.length() == 0) {
                    DialogClass dialogError = new DialogClass(ActivitySetting.this);
                    dialogError.DialogError("تمامی فیلدها نمی تواند خالی باشد، حداقل یکی از فیلدها را صحیح وارد کنید.");

                }
            }
        });

        layDeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog alertDialogDeleteDatabase = new MaterialDialog.Builder(ActivitySetting.this)
                        .customView(R.layout.dialog_alert_red, false)
                        .cancelable(false)
                        .build();
                RippleView layOk = (RippleView) alertDialogDeleteDatabase.findViewById(R.id.layOk);
                RippleView layCancel = (RippleView) alertDialogDeleteDatabase.findViewById(R.id.layCancel);
                TextView txtMessage = (TextView) alertDialogDeleteDatabase.findViewById(R.id.txtMessage);
                alertDialogDeleteDatabase.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                txtMessage.setText("آیا مطمئن هستید میخواهید تمام اطلاعات دیتابیس خود را پاک کنید؟");
                layOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseManager.deleteDb(ActivitySetting.this);
                        Intent intent = new Intent(ActivitySetting.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                        V.setting = null;
                        /*stop service*/
                        alertDialogDeleteDatabase.dismiss();
                    }
                });
                layCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogDeleteDatabase.dismiss();
                    }
                });
                alertDialogDeleteDatabase.show();
            }
        });


    }


    /****************************************************************************************************/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
    }


    /************************************************ EVENT ***************************************************/
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOnShowDialog event) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError event) {
        dialogClass.DialogWaitingClose();
        dialogClass.DialogError("عدم ارتباط با سرور \n لطفا پس از اطمینان از متصل بودن به اینترنت مجددا تلاش کنید");
    }
}
