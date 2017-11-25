package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelNewsDetail;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.events.EventOnRequestError;
import ir.jahanmirbazh.events.EventOnSuccessGetNewsDetail;
import ir.jahanmirbazh.events.EventOnUpdateCounts;

public class ActivityShowNews extends AppCompatActivity {

//    AdapterCommentRecyclerView adapterCommentRecyclerView;
//    List<NewsComment> comments;

    @Bind(R.id.layImgBack)
    LinearLayout layImgBack;
    //    @Bind(R.id.actionButtonSendComment) FloatingActionButton actionButtonSendComment;
    @Bind(R.id.txtNewsTitle)
    TextView txtNewsTitle;
    @Bind(R.id.txtNewsContent)
    WebView txtNewsContent;
    @Bind(R.id.txtNewsDate)
    TextView txtNewsDate;
    MaterialDialog dialog;
    MaterialDialog alertDialog;
    long newsId = -1;
    boolean isExpanded = false;

    int NewsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });

    }

    private void initView() {
        DatabaseManager databaseManager = new DatabaseManager();
        ModelNewsDetail detail = databaseManager.selectNewsDetailByNewsId(NewsId);
        if (detail != null) {
            txtNewsDate.setText(detail.getRegisterDateTime());
            txtNewsTitle.setText(detail.getTitle());
            String s = "<html>" +
                    "<head>" +
                    "<style>body{direction:ltr;}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    detail.getText() +
                    "</body></html>";
            txtNewsContent.loadDataWithBaseURL("", s, "text/html", "utf-8", "");

            txtNewsContent.setBackgroundColor(0x00000000);

//            txtNewsContent.loadData("<html>" +
//                    "<body style=\"background-color:#00A8BD;text-align:right;direction:rtl\">"
//                    + detail.getText() +
//                    "</body>" +
//                    "</html>" +
//                    "", "text/html; charset=UTF-8", null);
//        txtNewsContent.setText(Html.fromHtml(detail.getModelNewsDetail().getText()));
            if (!detail.isSeen()) {
//                V.currentEstate.setNewsCount(V.currentEstate.getNewsCount() - 1);
//                V.currentEstate.save();
//                detail.setSeen(true);
//                detail.save();
//                EventBus.getDefault().post(new EventOnUpdateCounts());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        NewsId = intent.getIntExtra("NewsId", 0);
        WebService.sendGetNewsDetailRequest(this, NewsId);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccexxEvent(EventOnSuccessGetNewsDetail detail) {

        txtNewsDate.setText(detail.getModelNewsDetail().getRegisterDateTime());
        txtNewsTitle.setText(detail.getModelNewsDetail().getTitle());
        txtNewsContent.loadData("<html>" +
                "<body style=\"background-color:#00A8BD;text-align:right\">"
                + detail.getModelNewsDetail().getText() +
                "</body>" +
                "</html>" +
                "", "text/html; charset=UTF-8", null);
//        txtNewsContent.setText(Html.fromHtml(detail.getModelNewsDetail().getText()));
        if (!detail.getModelNewsDetail().isSeen()) {
            int a = V.currentEstate.getNewsCount();
            if (a > 0) {
                V.currentEstate.setNewsCount(V.currentEstate.getNewsCount() - 1);
            } else {
                V.currentEstate.setNewsCount(0);
            }
            V.currentEstate.save();
            EventBus.getDefault().post(new EventOnUpdateCounts());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(EventOnRequestError error) {
        initView();
    }
}
