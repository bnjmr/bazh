package ir.jahanmirbazh.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelPayment;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityShowPayment;
import ir.jahanmirbazh.enums.EnumPaymentType;


public class AdapterPaymentRecycler extends RecyclerView.Adapter<AdapterPaymentRecycler.PaymentViewHolder> {

    List<ModelPayment> payments = new ArrayList<>();


    public AdapterPaymentRecycler(List<ModelPayment> payments) {
        this.payments = payments;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PaymentViewHolder holder, int position) {
        final ModelPayment payment = payments.get(position);

        String pattern = "#,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.txtPaymentAmount.setText(""+decimalFormat.format(payment.getAmount()));

        switch (payment.getPaymentType()) {
            case EnumPaymentType.Cash:
                holder.txtPaymentTitle.setText("پرداخت نقدی");
                break;
            case EnumPaymentType.Online:
                holder.txtPaymentTitle.setText("آنلاین");
                break;
            case EnumPaymentType.CardReader:
                holder.txtPaymentTitle.setText("پرداخت از طریق کارت خوان");
                break;
        }

        holder.txtPaymentRegDate.setText(payment.getRegisterDateTime());

        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowPayment.class);
                intent.putExtra("Amount", payment.getAmount());
                intent.putExtra("Bank", payment.getBank());
                intent.putExtra("Payer", payment.getPayer());
                intent.putExtra("PaymentId", payment.getPaymentId());
                intent.putExtra("PaymentType", payment.getPaymentType());
                intent.putExtra("RegisterDateTime", payment.getRegisterDateTime());
                intent.putExtra("Description", payment.getDescription());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);

            }
        });


//        long billId = payment.billId;
//        final Bill bill = new Select().from(Bill.class).where("billId = ? ",billId).executeSingle();
//        if(bill != null )
//            holder.txtPaymentTitle.setText(bill.des);
//        else
//            holder.txtPaymentTitle.setText("نامشخص");
//        final String regDate = U.ConvertStringSqlDateTimeToPersianStringDate(payment.regDate);
//        holder.txtPaymentRegDate.setText(regDate);
//        NumberFormat formatter = new DecimalFormat("#,###,###,###");
//        holder.txtPaymentAmount.setText("" + formatter.format(payment.amount) + " تومان");
//        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Animation animFadein = AnimationUtils.loadAnimation(G.context.getApplicationContext(),R.anim.fade_in);
//                holder.imgMoreDetail.startAnimation(animFadein);
//
//                Intent intent =new Intent(G.context, ActivityShowPayment.class);
//                if(bill != null)
//                    intent.putExtra("PAYMENT_DES",bill.des);
//                else
//                    intent.putExtra("PAYMENT_DES", payment.des);
//                intent.putExtra("PAYMENT_AMOUNT",payment.amount);
//                intent.putExtra("PAYMENT_TYPE",payment.paymentType);
//                intent.putExtra("PAYMENT_REG_DATE",payment.regDate);
//                intent.putExtra("PAYMENT_PERSON_NAME", payment.personName);
//                G.currentActivity.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtPaymentTitle)
        TextView txtPaymentTitle;
        @Bind(R.id.txtPaymentAmount)
        TextView txtPaymentAmount;
        @Bind(R.id.txtPaymentRegDate)
        TextView txtPaymentRegDate;
        @Bind(R.id.layMoreDetail)
        LinearLayout layMoreDetail;
        @Bind(R.id.imgMoreDetail)
        ImageView imgMoreDetail;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelPayment> data) {
        payments = data;
        notifyDataSetChanged();
    }

}
