package ir.jahanmirbazh.bazh;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelEstate;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.adapter.AdapterExpandableEstate;
import ir.jahanmirbazh.adapter.AdapterViewPager;
import ir.jahanmirbazh.classes.DialogClass;
import ir.jahanmirbazh.classes.Logger;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.components.SizeAdjustingTextView;
import ir.jahanmirbazh.components.SquareLinearLayout;
import ir.jahanmirbazh.events.EventOnGetUpdateResponse;
import ir.jahanmirbazh.events.EventOnPageChanged;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSelectEstate;
import ir.jahanmirbazh.events.EventOnSendGetBillRequest;
import ir.jahanmirbazh.events.EventOnSendGetCost;
import ir.jahanmirbazh.events.EventOnSendGetDashboardRequest;
import ir.jahanmirbazh.events.EventOnSendGetNewsRequest;
import ir.jahanmirbazh.events.EventOnSendGetPaymentRequest;
import ir.jahanmirbazh.events.EventOnSendGetTicketRequest;
import ir.jahanmirbazh.events.EventOnShowDialog;
import ir.jahanmirbazh.events.EventOnShowUpdateDialog;
import ir.jahanmirbazh.events.EventOnSuccessGetDashboard;
import ir.jahanmirbazh.events.EventOnSuccessGetEstates;
import ir.jahanmirbazh.events.EventOnSuccessGetEstatestEmpty;
import ir.jahanmirbazh.events.EventOnUpdateCounts;

import static ir.jahanmirbazh.bazh.V.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION;
import static ir.jahanmirbazh.bazh.V.setting;
import static ir.jahanmirbazh.bazh.V.userInfo;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_BILL;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_COST;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_HOME;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_NEWS;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_PAYMENT;
import static ir.jahanmirbazh.enums.EnumCurrentPage.PAGE_TICKET;

public class ActivityFirstPage extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    @Bind(R.id.txtCurrentDateInfo)
    TextView txtCurrentDateInfo;
    @Bind(R.id.layStatusBar)
    LinearLayout layStatusBar;
    @Bind(R.id.txtEstateCredit)
    TextView txtEstateCredit;
    @Bind(R.id.txtUserName)
    TextView txtUserName;
    @Bind(R.id.imgUserAvatar)
    CircleImageView imgUserAvatar;
    @Bind(R.id.expEstateList)
    ExpandableListView expEstateList;
    @Bind(R.id.imgSetting)
    CircleImageView imgSetting;
    @Bind(R.id.imgSignOut)
    CircleImageView imgSignOut;
    @Bind(R.id.layMenu)
    LinearLayout layMenu;
    @Bind(R.id.layCountNewNotify)
    FrameLayout layCountNewNotify;
    @Bind(R.id.txtCountNewNotify)
    SizeAdjustingTextView txtCountNewNotify;
    @Bind(R.id.layTxtCountNewNotify)
    LinearLayout layTxtCountNewNotify;
    @Bind(R.id.laySlidingLayout)
    SlidingUpPanelLayout laySlidingLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.imgChatPager)
    ImageView imgChatPager;
    @Bind(R.id.imgNewsPager)
    ImageView imgNewsPager;
    @Bind(R.id.imgTicketPager)
    ImageView imgTicketPager;
    @Bind(R.id.imgPaymentPager)
    ImageView imgPaymentPager;
    @Bind(R.id.imgBillPager)
    ImageView imgBillPager;
    @Bind(R.id.imgHomePager)
    ImageView imgHomePager;
    @Bind(R.id.layCountNewBill)
    LinearLayout layCountNewBill;
    @Bind(R.id.layCountNewTicket)
    LinearLayout layCountNewTicketDetail;
    @Bind(R.id.layCountNewNews)
    LinearLayout layCountNewNews;
    @Bind(R.id.layCountNewPayment)
    LinearLayout layCountNewPayment;
    @Bind(R.id.layCountNewChat)
    LinearLayout layCountNewChat;
    @Bind(R.id.txtCountNewBill)
    TextView txtCountNewBill;
    @Bind(R.id.txtCountNewTicketDetail)
    TextView txtCountNewTicketDetail;
    @Bind(R.id.txtCountNewNews)
    TextView txtCountNewNews;
    @Bind(R.id.txtCountNewPayment)
    TextView txtCountNewPayment;
    @Bind(R.id.txtCountNewChat)
    TextView txtCountNewChat;
    @Bind(R.id.layCostTab)
    SquareLinearLayout layCostTab;

    AdapterViewPager adapter;
    List<ModelEstate> estateList;
    DatabaseManager databaseManager;
    MaterialDialog alertDialog;
    AdapterExpandableEstate adapterExpandableEstate;
    DialogClass dialogClass;
    int pagerCount = 5;

    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        databaseManager = new DatabaseManager();
        ButterKnife.bind(this);
//        V.currentEstate = getCurrentEstate();
        dialogClass = new DialogClass(this);
        dialogClass.DialogWaiting();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        WebService.sendGetEstateRequest(ActivityFirstPage.this);
        WebService.sendGetProfileRequest(ActivityFirstPage.this);
        if (V.setting != null && userInfo != null && !V.setting.isTokenSend()) {
            WebService.sendFirebaseToken(this, 1);
        }


//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);

//        String token = ir.jahanmirbazh.bazh.V.setting.getFirebaseToken();
//        Logger.d("firebase Token", token);

    }

    @Override
    protected void onResume() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        if (currentPage == PAGE_NEWS) {
            WebService.sendGetNewsRequest(ActivityFirstPage.this);
            EventBus.getDefault().post(new EventOnSendGetNewsRequest());
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
//
    public void initCounts() {
        if (V.currentEstate.getBillsCount() != 0) {
            layCountNewBill.setVisibility(View.VISIBLE);
            txtCountNewBill.setText(String.valueOf(ir.jahanmirbazh.bazh.V.currentEstate.getBillsCount()));
        } else {
            layCountNewBill.setVisibility(View.GONE);
        }

        if (V.currentEstate.getNotificationsCount() != 0) {
            txtCountNewNotify.setVisibility(View.VISIBLE);
            txtCountNewNotify.setText(String.valueOf(V.currentEstate.getNotificationsCount()+""));
            layTxtCountNewNotify.setVisibility(View.VISIBLE);
        } else {
            txtCountNewNotify.setVisibility(View.GONE);
            layTxtCountNewNotify.setVisibility(View.GONE);
        }

        if (V.currentEstate.getNewsCount() != 0) {
            layCountNewNews.setVisibility(View.VISIBLE);
            txtCountNewNews.setText(String.valueOf(ir.jahanmirbazh.bazh.V.currentEstate.getNewsCount()));
        } else {
            layCountNewNews.setVisibility(View.GONE);
        }

        if (V.currentEstate.getTicketsCount() != 0) {
            layCountNewTicketDetail.setVisibility(View.VISIBLE);
            txtCountNewTicketDetail.setText(String.valueOf(ir.jahanmirbazh.bazh.V.currentEstate.getTicketsCount()));
        } else {
            layCountNewTicketDetail.setVisibility(View.GONE);
        }

    }

    private void initView() {
        if (databaseManager.selectEstates() != null && databaseManager.selectEstates().size() != 0) {
            V.currentEstate = U.getCurrentEstate(this);
            txtUserName.setText(userInfo.getName());
            txtCurrentDateInfo.setText(ir.jahanmirbazh.bazh.U.convertDateToPersianDateShowInMainPage(new Date()));
            /** Set Adapter to ViewPager*/


            if (ir.jahanmirbazh.bazh.V.ShowCosts) {
//            if (true) {
                pagerCount = 6;
                layCostTab.setVisibility(View.VISIBLE);
            } else {
                layCostTab.setVisibility(View.GONE);
                pagerCount = 5;
            }
            adapter = new AdapterViewPager(pagerCount, getSupportFragmentManager());

            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.addOnPageChangeListener(this);
            setMenuBarItemsImage(0);

            /** set kardane tanzimate marbut be slidingUpPanelLayout*/
            initializeSlidingUpPanelLayout();
            initializeEstateListView();


            imgHomePager.setOnClickListener(this);
            imgBillPager.setOnClickListener(this);
            imgPaymentPager.setOnClickListener(this);
            imgTicketPager.setOnClickListener(this);
            imgNewsPager.setOnClickListener(this);
            imgChatPager.setOnClickListener(this);
            layCountNewNotify.setOnClickListener(this);

            WebService.sendCheckUpdate(this);
        }
    }

    private void initializeEstateListView() {
        estateList = databaseManager.selectEstates();

        // agar tedade EstateHa 0 bashad yani baraye karbae Estate sabt nashode
        if (estateList.size() != 0) {


            //check mikonim bebinim dar jadvale setting currentEstate sabt shode ya na
            //agar sabt shode check mikonim bebinim in Estate dar hale hazer vojod darad ya na
//            for (ModelEstate estate : estateList) {
//                if (setting != null && setting.getCurrentEstateId() == estate.getEstateId()) {
//                    V.currentEstate = estate;
//                    break;
//                } else {
//                    V.currentEstate = null;
//                }
//            }
//
//            //inja ya currentEstate set nashode ya Estate select shode
//            // dar hale hazer motabar nist
//            // dar in halat avalin Estate ra select mikonim
//            if (V.currentEstate == null) {
//                V.currentEstate = estateList.get(0);
//            }
            adapterExpandableEstate = new AdapterExpandableEstate(ActivityFirstPage.this, estateList);
            expEstateList.setAdapter(adapterExpandableEstate);
            expEstateList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                    return false;

                }
            });
        } else {
            /**dar surati ke baraye shakhs hich melki sabt nashode bashad*/
            Logger.d("ActivityFirsPage ", "Show dialog no estate register");
            alertDialog = new MaterialDialog.Builder(this)
                    .customView(R.layout.dialog_no_estate, false)
                    .cancelable(false)
                    .build();

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RippleView layChangeUser = (RippleView) alertDialog.findViewById(R.id.layChangeUser);
            RippleView layExit = (RippleView) alertDialog.findViewById(R.id.layExit);
            layChangeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setting.setLogin(false);
                    ir.jahanmirbazh.bazh.V.setting.save();
                    Intent intent = new Intent(ActivityFirstPage.this, ir.jahanmirbazh.bazh.ActivityLogin.class);
                    closeAllActivities();
                    startActivity(intent);
                    alertDialog.dismiss();
                    finish();
                }
            });
            layExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    alertDialog.dismiss();
                }
            });

            if (alertDialog != null)
                alertDialog.show();
        }
    }

    private void setMenuBarItemsImage(int position) {
        switch (position) {
            case 0:
//                currentPage = PAGE_HOME;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_HOME));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_selected);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_normal);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_normal);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_normal);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_normal);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_normal);
                break;
            case 1:
//                currentPage = PAGE_BILL;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_BILL));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_normal);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_selected);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_normal);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_normal);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_normal);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_normal);
                break;
            case 2:
//                currentPage = PAGE_PAYMENT;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_PAYMENT));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_normal);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_normal);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_selected);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_normal);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_normal);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_normal);
                break;
            case 3:
//                currentPage = PAGE_TICKET;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_TICKET));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_normal);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_normal);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_normal);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_selected);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_normal);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_normal);
                break;
            case 4:
//                currentPage = PAGE_NEWS;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_NEWS));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_normal);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_normal);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_normal);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_normal);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_selected);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_normal);
                break;
            case 5:
//                currentPage = PAGE_COST;
                EventBus.getDefault().post(new EventOnPageChanged(PAGE_COST));
                imgHomePager.setImageResource(R.drawable.ic_home_menu_bar_normal);
                imgBillPager.setImageResource(R.drawable.ic_check_menu_bar_normal);
                imgPaymentPager.setImageResource(R.drawable.ic_payment_menu_bar_normal);
                imgTicketPager.setImageResource(R.drawable.ic_ticket_menu_bar_normal);
                imgNewsPager.setImageResource(R.drawable.ic_news_menu_bar_normal);
                imgChatPager.setImageResource(R.drawable.ic_chat_menu_bar_selected);
                break;
        }
    }

    private void initializeSlidingUpPanelLayout() {

        /** add sliding up panel layout open and close listener*/
        laySlidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                U.log("ActivityFirstPage : slideOffset is " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (laySlidingLayout != null &&
                        (laySlidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ||
                                laySlidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    laySlidingLayout.setTouchEnabled(true);
                }
            }
        });

        /** open and close when clicked on layStatus */
        layStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (laySlidingLayout != null &&
                        (laySlidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || laySlidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    laySlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    laySlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });

        /** when sliding up panel open the background of main content not change and fade*/
        laySlidingLayout.setCoveredFadeColor(ContextCompat.getColor(getApplicationContext(), R.color.color_trans));

        /** ertefahe menu'ye balaei ra bar asase inke dastgah
         *  goshi hast ya tablet moshakhas mikonad.*/
        setSlidingUpPanelHeight();

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*** bastane slidingLayout */
                laySlidingLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        laySlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }
                });
                final MaterialDialog alertExit = new MaterialDialog.Builder(ActivityFirstPage.this)
                        .customView(R.layout.dialog_alert_red, false)
                        .cancelable(false)
                        .build();
                RippleView layOk = (RippleView) alertExit.findViewById(R.id.layOk);
                RippleView layCancel = (RippleView) alertExit.findViewById(R.id.layCancel);
                TextView txtMessage = (TextView) alertExit.findViewById(R.id.txtMessage);
                alertExit.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                txtMessage.setText("آیا مطمئن هستید میخواهید از برنامه خارج شوید؟");
                layOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        V.setting = databaseManager.readSetting();
                        V.setting.setLogin(false);
                        V.setting.save();
                        V.currentEstate = null;
                        WebService.sendFirebaseToken(ActivityFirstPage.this, 2);


                        Intent intent = new Intent(ActivityFirstPage.this, ir.jahanmirbazh.bazh.ActivityLogin.class);
                        startActivity(intent);
                        finish();

                        /** remove notification */
                        ir.jahanmirbazh.bazh.U.cancelNotification(1369, ActivityFirstPage.this);
                        ir.jahanmirbazh.bazh.U.cancelNotification(1367, ActivityFirstPage.this);
                    }
                });
                layCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertExit.dismiss();
                    }
                });
                alertExit.show();
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*** bastane slidingLayout */
                laySlidingLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        laySlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }
                });
                Intent intent = new Intent(getBaseContext(), ir.jahanmirbazh.bazh.ActivitySetting.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setSlidingUpPanelHeight() {
        boolean isTablet = ir.jahanmirbazh.bazh.U.isTablet(ActivityFirstPage.this);
        if (isTablet) {
            SlidingUpPanelLayout.LayoutParams layMenuLayoutParams = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ir.jahanmirbazh.bazh.U.getDeviceHeight(ActivityFirstPage.this) * 2 / 3);
            layMenu.setLayoutParams(layMenuLayoutParams);
        } else {
            SlidingUpPanelLayout.LayoutParams layMenuLayoutParams = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ir.jahanmirbazh.bazh.U.getDeviceHeight(ActivityFirstPage.this) * 3 / 4);
            layMenu.setLayoutParams(layMenuLayoutParams);
        }
    }

    protected void closeAllActivities() {
        sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        System.gc();
        System.runFinalization();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
        }
        setMenuBarItemsImage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.imgHomePager:
                if (currentPage == PAGE_HOME)
                    return;

                setMenuBarItemsImage(0);
                pager.setCurrentItem(0);
                break;
            case R.id.imgBillPager:
                if (currentPage == PAGE_BILL)
                    return;

                setMenuBarItemsImage(1);
                pager.setCurrentItem(1);
                break;
            case R.id.imgPaymentPager:
                if (currentPage == PAGE_PAYMENT)
                    return;

                setMenuBarItemsImage(2);
                pager.setCurrentItem(2);
                break;
            case R.id.imgTicketPager:
                if (currentPage == PAGE_TICKET)
                    return;

                setMenuBarItemsImage(3);
                pager.setCurrentItem(3);
                break;
            case R.id.imgNewsPager:
                if (currentPage == PAGE_NEWS)
                    return;

                setMenuBarItemsImage(4);
                pager.setCurrentItem(4);
                break;
            case R.id.imgChatPager:
                if (currentPage == PAGE_COST)
                    return;

                setMenuBarItemsImage(5);
                pager.setCurrentItem(5);
                break;

            case R.id.layCountNewNotify:
                Intent intent = new Intent(ActivityFirstPage.this, ir.jahanmirbazh.bazh.ActivityShowNotification.class);
                startActivity(intent);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetEstates event) {
        dialogClass.DialogWaitingClose();
        try {
            V.currentEstate = U.getCurrentEstate(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetEstatestEmpty empty) {
        V.setting.setLogin(false);
        V.setting.save();
        Intent intent = new Intent(ActivityFirstPage.this, ActivityLogin.class);
        intent.putExtra("showDialog", "noEstate");
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError event) {

        switch (event.getMessageKind()) {
            case Estate:
                try {
                    dialogClass.DialogWaitingClose();
                    dialogClass.DialogError("عدم ارتباط با سرور \n لطفا پس از اطمینان از متصل بودن به اینترنت مجددا تلاش کنید");
                    initView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(final EventOnSelectEstate event) {
        if (ir.jahanmirbazh.bazh.V.currentEstate != null && ir.jahanmirbazh.bazh.V.currentEstate != event.getModelEstate()) {
            ir.jahanmirbazh.bazh.V.currentEstate = event.getModelEstate();

            ir.jahanmirbazh.bazh.V.setting = databaseManager.readSetting();
            ir.jahanmirbazh.bazh.V.setting.setCurrentEstateId(ir.jahanmirbazh.bazh.V.currentEstate.getEstateId());
            ir.jahanmirbazh.bazh.V.setting.save();
            if (ir.jahanmirbazh.bazh.V.ShowCosts) {
                pagerCount = 6;
                layCostTab.setVisibility(View.VISIBLE);
            } else {
                layCostTab.setVisibility(View.GONE);
                pagerCount = 5;
            }
            adapter = new AdapterViewPager(pagerCount, getSupportFragmentManager());

            setMenuBarItemsImage(0);
            adapterExpandableEstate.updateList(databaseManager.selectEstates());
            pager.setAdapter(adapter);
            pager.setCurrentItem(0);

        } else {
            ir.jahanmirbazh.bazh.V.currentEstate = event.getModelEstate();
            if (V.ShowCosts) {
                pagerCount = 6;
                layCostTab.setVisibility(View.VISIBLE);
            } else {
                layCostTab.setVisibility(View.GONE);
                pagerCount = 5;
            }
            adapter = new AdapterViewPager(pagerCount, getSupportFragmentManager());

            adapterExpandableEstate.updateList(databaseManager.selectEstates());
            pager.setAdapter(adapter);


            laySlidingLayout.post(new Runnable() {
                @Override
                public void run() {
                    laySlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            });
        }
        txtUserName.setText(userInfo.getName());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnPageChanged pageChanged) {
        EventBus.getDefault().post(new EventOnUpdateCounts());
        currentPage = pageChanged.getEnumCurrentPage();
        switch (pageChanged.getEnumCurrentPage()) {
            case PAGE_HOME:
                if (currentPage == PAGE_HOME) {
                    EventBus.getDefault().post(new EventOnSendGetDashboardRequest());
                    WebService.sendGetDashboardRequest(ActivityFirstPage.this);
                }
                break;

            case PAGE_BILL:
                if (currentPage == PAGE_BILL) {
                    EventBus.getDefault().post(new EventOnSendGetBillRequest());
                    WebService.sendGetBillRequest(ActivityFirstPage.this, V.currentEstate.getEstateId());
                }

                break;

            case PAGE_PAYMENT:
                if (currentPage == PAGE_PAYMENT) {
                    WebService.sendGetPaymentRequest(ActivityFirstPage.this, ir.jahanmirbazh.bazh.V.currentEstate.getEstateId());
                    EventBus.getDefault().post(new EventOnSendGetPaymentRequest());
                }
                break;

            case PAGE_TICKET:
                if (currentPage == PAGE_TICKET) {
                    WebService.sendGetTicketRequest(ActivityFirstPage.this);
                    EventBus.getDefault().post(new EventOnSendGetTicketRequest());
                }
                break;
            case PAGE_NEWS:
                if (currentPage == PAGE_NEWS) {
                    WebService.sendGetNewsRequest(ActivityFirstPage.this);
                    EventBus.getDefault().post(new EventOnSendGetNewsRequest());
                }

                break;

            case PAGE_COST:
                if (currentPage == PAGE_COST) {
                    WebService.sendGetCostRequest(ActivityFirstPage.this);
                    EventBus.getDefault().post(new EventOnSendGetCost());
                }
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnUpdateCounts counts) {
        initCounts();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnSuccessGetDashboard getDashboard) {
        EventBus.getDefault().post(new EventOnUpdateCounts());
    }

    /* Update EventBus Method */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventOnGetUpdateResponse event) {
        try {
            float ver = Float.parseFloat(V.versionName);
            if (ver < event.getUpdateResponse().getVersion()) {
                EventBus.getDefault().post(new EventOnShowUpdateDialog(event.getUpdateResponse()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventOnShowUpdateDialog dialog0) {
        DialogClass dialog = new DialogClass(this);
        dialog.showUpdateDialog(dialog0.getUpdate(),this);
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
}