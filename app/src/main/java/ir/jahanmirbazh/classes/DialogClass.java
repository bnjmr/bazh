package ir.jahanmirbazh.classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.jahanmirbazh.Gson.getUpdate;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.components.PersianTextViewNormal;


public class DialogClass {

    Dialog dlgWaiting;
    MaterialDialog dlgNoEstate;
    Context context;

    public DialogClass(Context context) {
        this.context = context;
    }

//    public static void DialogExitForce(final Context context, final String message) {
//
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                if(context != null){
//                    final MaterialDialog dialog = new MaterialDialog.Builder(context)
//                            .customView(R.layout.dialog_no_estate, false)
//                            .cancelable(false)
//                            .build();
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                    RippleView layChangeUser = (RippleView) dialog.findViewById(R.id.layChangeUser);
//                    RippleView layExit = (RippleView) dialog.findViewById(R.id.layExit);
//                    TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
//                    txtMessage.setText(message);
//                    layChangeUser.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                    /*stop service*/
//                            /**stop kardan Service va taghir halete login shakhs be false*/
//                            G.currentActivity.stopService(new Intent(G.currentActivity, SocketService.class));
//                            G.socket.stop();
//                            G.setting.isLogin = false;
//                            G.setting.save();
//                            Intent intent = new Intent(G.context, ActivityLogin.class);
//                            intent.putExtra("CUSTOMER_ID", G.setting.customerId);
//                            intent.putExtra("CUSTOMER_IMAGE", G.setting.customerImage);
//                            intent.putExtra("CUSTOMER_NAME", G.setting.customerName);
//                            G.setting = null;
//                            new BaseActivity() {
//                                @Override
//                                protected void closeAllActivities() {
//                                    super.closeAllActivities();
//                                }
//                            };
//                            G.currentActivity.startActivity(intent);
//
//                            /** remove notification */
//                            U.cancelNotification(1369);
//                            U.cancelNotification(1367);
//                            dialog.dismiss();
//
//                        }
//                    });
//                    layExit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            EventBus.getDefault().post(new EventCloseActivity());
//                            /** remove notification */
//                            U.cancelNotification(1369);
//                            U.cancelNotification(1367);
//                            dialog.dismiss();
//                            new BaseActivity() {
//                                @Override
//                                protected void closeAllActivities() {
//                                    super.closeAllActivities();
//                                }
//                            };
//                        }
//                    });
//                    dialog.show();
//                }
//            }
//        });
//    }
    public void DialogWaiting() {
        dlgWaiting = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);


        dlgWaiting.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dlgWaiting.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlgWaiting.setContentView(R.layout.dialog_waiting);
        dlgWaiting.setCancelable(false);


        ImageView imgLoading = (ImageView) dlgWaiting.findViewById(R.id.imgLoading);
        AnimationDrawable frameAnimation = (AnimationDrawable) imgLoading.getBackground();
        frameAnimation.start();


        dlgWaiting.show();
    }

    public void DialogWaitingClose() {
        try {
            if (dlgWaiting != null)
                dlgWaiting.dismiss();
        } catch (Exception e) {
        }
    }

    public void DialogError(String message) {
        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_error_message, false)
                .cancelable(true)
                .build();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        LinearLayout layCancel = (LinearLayout) dialog.findViewById(R.id.layCancel);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        layCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void DialogOkMessage(String message) {
        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_show_message, false)
                .cancelable(true)
                .build();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        LinearLayout layCancel = (LinearLayout) dialog.findViewById(R.id.layCancel);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        layCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showUpdateDialog(final getUpdate getUpdate, final Context context) {
        if (getUpdate.getVersion() == 0.0f)
            return;

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        PersianTextViewNormal txtUpdate = (PersianTextViewNormal) dialog.findViewById(R.id.txtUpdate);
        PersianTextViewNormal txtClose = (PersianTextViewNormal) dialog.findViewById(R.id.txtClose);
        PersianTextViewNormal txtVer = (PersianTextViewNormal) dialog.findViewById(R.id.txtVer);
        WebView web = (WebView) dialog.findViewById(R.id.web);

        String s = "<html>" +
                "<head>" +
                "<style>body{direction:rtl;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                getUpdate.getDescription() +
                "</body></html>";
        web.loadData(s, "Text/html; charset=utf-8", "UTF-8");
        web.setBackgroundColor(0x00000000);

        String Ver = " نسخه فعلی : " + V.versionName + "\n نسخه جدید : " + getUpdate.getVersion() + "\n";
        txtVer.setText(Ver);

        if (getUpdate.isRequired()) {
            txtClose.setVisibility(View.GONE);
            dialog.setCanceledOnTouchOutside(false);
        }
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getUpdate.getUrl()));
                    context.startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    public void DialogNoEstate(){
//        dlgNoEstate = new MaterialDialog.Builder(context)
//                .customView(R.layout.dialog_no_estate, false)
//                .cancelable(false)
//                .build();
//        RippleView layChangeUser = (RippleView) dlgNoEstate.findViewById(R.id.layChangeUser);
//        RippleView layExit = (RippleView) dlgNoEstate.findViewById(R.id.layExit);
//        dlgNoEstate.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        layChangeUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    /*stop service*/
//                /**stop kardan Service va taghir halete login shakhs be false*/
//                G.currentActivity.stopService(new Intent(context, SocketService.class));
//                G.socket.stop();
//                G.setting.isLogin = false;
//                G.setting.save();
//                Intent intent = new Intent(G.context, ActivityLogin.class);
//                intent.putExtra("CUSTOMER_ID", G.setting.customerId);
//                intent.putExtra("CUSTOMER_IMAGE", G.setting.customerImage);
//                intent.putExtra("CUSTOMER_NAME", G.setting.customerName);
//                G.setting = null;
//                new BaseActivity() {
//                    @Override
//                    protected void closeAllActivities() {
//                        super.closeAllActivities();
//                    }
//                };
//                G.currentActivity.startActivity(intent);
//                dlgNoEstate.dismiss();
//                /** remove notification */
//                U.cancelNotification(1369);
//                U.cancelNotification(1367);
//            }
//        });
//        layExit.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new EventCloseActivity());
//                new BaseActivity() {
//                    @Override
//                    protected void closeAllActivities() {
//                        super.closeAllActivities();
//                    }
//                };
//                dlgNoEstate.dismiss();
//                /** remove notification */
//                U.cancelNotification(1369);
//                U.cancelNotification(1367);
//            }
//        });
//        dlgNoEstate.show();
//    }
//    public void DialogNoEstateClose() {
//        if(dlgNoEstate != null)
//            dlgNoEstate.dismiss();
//    }
//    public void DialogShowGreenAlertDialog(String message){
//        final MaterialDialog greenAlert = new MaterialDialog.Builder(G.currentActivity)
//                .customView(R.layout.dialog_alert_green, false)
//                .cancelable(false)
//                .build();
//
//        greenAlert.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        RippleView layOk = (RippleView) greenAlert.findViewById(R.id.layOk);
//        RippleView layCancel = (RippleView) greenAlert.findViewById(R.id.layCancel);
//        TextView txtMessage = (TextView) greenAlert.findViewById(R.id.txtMessage);
//        txtMessage.setText(message);
//
//
//        layOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new EventOnClickDialogOkButton());
//                greenAlert.dismiss();
//            }
//        });
//
//        layCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                greenAlert.dismiss();
//            }
//        });
//
//        greenAlert.show();
//
//    }
}
