package ir.jahanmirbazh.bazh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelPayment;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.enums.EnumPaymentType;

public class ActivityShowPayment extends AppCompatActivity {


    @Bind(R.id.layImgBack)
    LinearLayout layImgBack;
    @Bind(R.id.layToolbarShowPaymentPage)
    LinearLayout layToolbarShowPaymentPage;
    @Bind(R.id.txtPaymentDes)
    TextView txtPaymentDes;
    @Bind(R.id.txtRegDate)
    TextView txtRegDate;
    @Bind(R.id.txtPersonName)
    TextView txtPersonName;
    @Bind(R.id.txtType)
    TextView txtType;
    @Bind(R.id.txtPrice)
    TextView txtPrice;
    @Bind(R.id.txtBank)
    TextView txtBank;

    @Bind(R.id.txtPayer)
    TextView txtPayer;

    @Bind(R.id.txtDescription)
    TextView txtDescription;


    ModelPayment modelPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        modelPayment = new ModelPayment();
        Intent intent = getIntent();
        modelPayment.setPaymentId(intent.getIntExtra("PaymentId", 0));
        modelPayment.setAmount(intent.getIntExtra("Amount", 0));
        modelPayment.setBank(intent.getStringExtra("Bank"));
        modelPayment.setPayer(intent.getStringExtra("Payer"));
        modelPayment.setPaymentType(intent.getIntExtra("PaymentType", 0));
        modelPayment.setRegisterDateTime(intent.getStringExtra("RegisterDateTime"));
        modelPayment.setDescription(intent.getStringExtra("Description"));

        switch (modelPayment.getPaymentType()) {
            case EnumPaymentType.Cash:
                txtType.setText("پرداخت نقدی");
                break;
            case EnumPaymentType.Online:
                txtType.setText("آنلاین");
                break;
            case EnumPaymentType.CardReader:
                txtType.setText("پرداخت از طریق کارت خوان");
                break;
        }

        txtPrice.setText(String.valueOf(modelPayment.getAmount()));
        txtPersonName.setText(modelPayment.getPayer());
        txtRegDate.setText(modelPayment.getRegisterDateTime());
        txtBank.setText(modelPayment.getBank());
        txtPayer.setText(modelPayment.getPayer());
        txtDescription.setText(modelPayment.getDescription());

        layImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_from_up_to_bottom);

    }
}
