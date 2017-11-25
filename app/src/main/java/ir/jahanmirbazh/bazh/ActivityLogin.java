package ir.jahanmirbazh.bazh;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetUserInfo;

import static ir.jahanmirbazh.enums.EnumDialogKind.success;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    int id_customer;
    String customerImage = "";
    String customerName = "";
    boolean firstLogin = true;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.layLogin)
    LinearLayout layLogin;
    @Bind(R.id.layGetPassword)
    LinearLayout layGetPassword;
    @Bind(R.id.layBtnForgetPassword)
    CardView layBtnForgetPassword;
    @Bind(R.id.txtBack)
    TextView txtBack;
    @Bind(R.id.layContent)
    FrameLayout layContent;
    @Bind(R.id.txtLoading)
    TextView txtLoading;
    //    @Bind(R.id.btnLogin) Button btnLogin;
    @Bind(R.id.layBtnLogin)
    CardView layBtnLogin;
    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPassword)
    EditText edtPassword;
    @Bind(R.id.edtForgetUsername)
    EditText edtForgetUsername;
    @Bind(R.id.edtForgetMobileNumber)
    EditText edtForgetMobileNumber;
    @Bind(R.id.txtErrorMessage)
    TextView txtErrorMessage;
    @Bind(R.id.txtErrorMessageForgetPassword)
    TextView txtErrorMessageForgetPassword;
    @Bind(R.id.txtForgetPassword)
    TextView txtForgetPassword;
    @Bind(R.id.txtSearchCustomer)
    TextView txtSearchCustomer;
    @Bind(R.id.txtCustomerName)
    TextView txtCustomerName;

    DialogClass dialogClass;
    private boolean isRemember = false;

    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        dialogClass = new DialogClass(ActivityLogin.this);

        Intent intent = getIntent();
        if (intent != null) {
            String s = intent.getStringExtra("showDialog");
            if (s != null) {
                switch (s) {
                    case "noEstate":
                        dialogClass.DialogOkMessage("ملکی برای شما ثبت نشده");
                        break;


                }
            }

        }

//                intent.putExtra("showDialog","noEstate");


    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getBooleanExtra("shouldShowDialog", false)) {
                String message = intent.getStringExtra("Message");
                if (message != null && !message.equals(""))
                    EventBus.getDefault().post(new EventOnShowDialog(message, ActivityLogin.this, success));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {

        layGetPassword.setVisibility(View.INVISIBLE);

        ir.jahanmirbazh.bazh.U.tintWidget(edtUsername, R.color.color_white);
        ir.jahanmirbazh.bazh.U.tintWidget(edtPassword, R.color.color_white);
        ir.jahanmirbazh.bazh.U.tintWidget(edtForgetMobileNumber, R.color.color_white);
        ir.jahanmirbazh.bazh.U.tintWidget(edtForgetUsername, R.color.color_white);

        /** draw line other textview */
        txtBack.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        txtErrorMessage.setVisibility(View.INVISIBLE);
        txtErrorMessageForgetPassword.setVisibility(View.INVISIBLE);
        layLoading.setVisibility(View.INVISIBLE);

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    dialogClass.DialogWaiting();
                    sendRequest(edtPassword);
                    return true;
                }
                return false;
            }
        });

        edtForgetMobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    sendForgetRequest();
                    return true;
                }
                return false;
            }
        });

        layBtnLogin.setOnClickListener(this);
        layBtnForgetPassword.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        layBtnForgetPassword.setOnClickListener(this);


    }

    public void sendForgetRequest() {
        String txtUserName = edtForgetUsername.getText().toString().trim();
        String txtMobileName = edtForgetMobileNumber.getText().toString().trim();
        if (txtUserName.length() > 0 && txtMobileName.length() == 10) {
            dialogClass.DialogWaiting();
            isRemember = true;
            userName = txtUserName;
            WebService.sendForgetPasswordRequest(ActivityLogin.this, txtUserName, txtMobileName);
        } else {

            if (txtUserName.length() == 0) {
                edtForgetUsername.setHint("نام کاربری خود را وارد کنید");
                edtForgetUsername.setHintTextColor(ContextCompat.getColor(ActivityLogin.this, R.color.color_red));
            }

            if (txtMobileName.length() != 10) {
                String message = " شماره موبایل خود را مانند نمونه وارد کنید"
                        + System.getProperty("line.separator")
                        + "نمونه : 9170000000";
                edtForgetMobileNumber.setText("");
                edtForgetMobileNumber.setHint(message);
                edtForgetMobileNumber.setHintTextColor(ContextCompat.getColor(ActivityLogin.this, R.color.color_red));
            }
        }
    }

    private void sendRequest(View view) {
        firstLogin = true;
            /*hide soft keyboard*/
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        layLoading.setVisibility(View.VISIBLE);
        String password = edtPassword.getText().toString();
        String username = edtUsername.getText().toString();
                /*check kardane inke username va password vared shode bashad.*/
        if (password.trim().length() == 0 || username.trim().length() == 0) {
            txtErrorMessage.setText("نام کاربری و رمز عبور را وارد کنید");
            txtErrorMessage.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.INVISIBLE);
        } else {
            txtErrorMessage.setVisibility(View.INVISIBLE);
                /* Darkhast login kardan az webservice*/
            WebService.sendLoginRequest(ActivityLogin.this, username, password);

//            layBtnLogin.setOnClickListener(null);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layBtnLogin:
                dialogClass.DialogWaiting();
                sendRequest(view);
                break;
            case R.id.layBtnForgetPassword:
                sendForgetRequest();
                break;
            case R.id.txtForgetPassword:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layLogin.setVisibility(View.INVISIBLE);
                        layGetPassword.setVisibility(View.VISIBLE);
                    }
                }, 500);

                ObjectAnimator rotateY0 = ObjectAnimator.ofFloat(layContent, "rotationY", 0.0f, 180f);
                ObjectAnimator scaleDownXToSmall0 = ObjectAnimator.ofFloat(layContent, "scaleX", 0.85f);
                ObjectAnimator scaleDownYToSmall0 = ObjectAnimator.ofFloat(layContent, "scaleY", 0.85f);
                ObjectAnimator scaleDownXToBig0 = ObjectAnimator.ofFloat(layContent, "scaleX", 1f);
                ObjectAnimator scaleDownYToBig0 = ObjectAnimator.ofFloat(layContent, "scaleY", 1f);
                rotateY0.setInterpolator(new AccelerateDecelerateInterpolator());
                scaleDownYToSmall0.setDuration(1000);
                AnimatorSet firstAnim0 = new AnimatorSet();
                firstAnim0.play(scaleDownXToSmall0).with(scaleDownYToSmall0).before(rotateY0);

                AnimatorSet secondAnim0 = new AnimatorSet();
                secondAnim0.play(scaleDownXToBig0).with(scaleDownYToBig0).after(firstAnim0);
                secondAnim0.start();
                break;
            case R.id.txtBack:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layLogin.setVisibility(View.VISIBLE);
                        layGetPassword.setVisibility(View.INVISIBLE);
                    }
                }, 500);

                ObjectAnimator rotateY = ObjectAnimator.ofFloat(layContent, "rotationY", 180f, 0.0f);
                ObjectAnimator scaleDownXToSmall = ObjectAnimator.ofFloat(layContent, "scaleX", 0.85f);
                ObjectAnimator scaleDownYToSmall = ObjectAnimator.ofFloat(layContent, "scaleY", 0.85f);
                ObjectAnimator scaleDownXToBig = ObjectAnimator.ofFloat(layContent, "scaleX", 1f);
                ObjectAnimator scaleDownYToBig = ObjectAnimator.ofFloat(layContent, "scaleY", 1f);
                rotateY.setInterpolator(new AccelerateDecelerateInterpolator());
                scaleDownYToSmall.setDuration(1000);


                AnimatorSet firstAnim = new AnimatorSet();
                firstAnim.play(scaleDownXToSmall).with(scaleDownYToSmall).before(rotateY);

                AnimatorSet secondAnim = new AnimatorSet();
                secondAnim.play(scaleDownXToBig).with(scaleDownYToBig).after(firstAnim);
                secondAnim.start();
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOnSuccessGetUserInfo event) {
        dialogClass.DialogWaitingClose();
        layLoading.setVisibility(View.GONE);
        startActivity(new Intent(ActivityLogin.this, ir.jahanmirbazh.bazh.ActivityFirstPage.class));
        finish();

        V.setting.setLogin(true);
        V.setting.save();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventOnShowDialog event) {
        dialogClass.DialogWaitingClose();
        layLoading.setVisibility(View.GONE);
        switch (event.getDialogKind()) {
            case success:
                if (isRemember) {
                    txtBack.callOnClick();
                    edtUsername.setText(userName);
                    isRemember = false;
                }
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
        layLoading.setVisibility(View.GONE);
        dialogClass.DialogError("عدم ارتباط با سرور \n لطفا پس از اطمینان از متصل بودن به اینترنت مجددا تلاش کنید");
    }

}
