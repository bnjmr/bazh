package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.DatabaseManager;
import ir.jahanmirbazh.Database.ModelCost;
import ir.jahanmirbazh.R;

public class ActivityShowCost extends AppCompatActivity {

    @Bind(R.id.txtCostTitle)
    TextView txtCostTitle;

    @Bind(R.id.txtCostDate)
    TextView txtCostDate;

    @Bind(R.id.txtCostDescription)
    TextView txtCostDescription;

    @Bind(R.id.txtCostAmount)
    TextView txtCostAmount;

    @Bind(R.id.layImgBack)
    LinearLayout layImgBack;

    ModelCost modelCost;
    DatabaseManager databaseManager;

    int CostId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cost);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
        databaseManager = new DatabaseManager();

        Intent intent = getIntent();
        if (intent != null) {
            CostId = intent.getIntExtra("CostId", 0);

            modelCost = databaseManager.selectCostByCostId(CostId);

            initView();

        }


    }

    private void initView() {
        txtCostTitle.setText(modelCost.getTitle());
        txtCostDate.setText(modelCost.getCostDate());
//        txtCostDescription.setText(modelCost.getDescription());
        String format = "#,###";
        DecimalFormat decimalFormat = new DecimalFormat(format);

        txtCostAmount.setText(decimalFormat.format(modelCost.getAmount()));

        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, R.anim.exit_from_up_to_bottom);
            }
        });

    }
}
