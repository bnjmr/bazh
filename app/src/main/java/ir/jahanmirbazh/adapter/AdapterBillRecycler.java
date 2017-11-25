package ir.jahanmirbazh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelBill;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityShowBill;
import ir.jahanmirbazh.enums.EnumBillStatus;


public class AdapterBillRecycler extends RecyclerView.Adapter<AdapterBillRecycler.BillViewHolder> {

    List<ModelBill> bills;
    Context context;

    public AdapterBillRecycler(List<ModelBill> bills, Context context) {
        this.bills = bills;
        this.context = context;
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_bill_item, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BillViewHolder holder, int position) {
        final ModelBill bill = bills.get(position);
        try {
            if (bill.getCharge() == null || bill.getCharge().trim().length() == 0)
                holder.txtBillTitle.setText("نامشخص");
            else
                holder.txtBillTitle.setText(bill.getCharge());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bill.getStatus() == EnumBillStatus.New) {
            holder.imgNew.setVisibility(View.VISIBLE);
        } else {
            holder.imgNew.setVisibility(View.GONE);
        }
        holder.txtBillRegDate.setText(bill.getRegisterDateTime());
        holder.txtBillEndDate.setText(bill.getDeadlineDate());
        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation animFadein = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fade_in);
                holder.imgMoreDetail.startAnimation(animFadein);
                animFadein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

//                        final String startDate = U.ConvertStringSqlDateTimeToPersianStringDateWithoutTime(bill.getRegisterDateTime());
//                        final String endDate = U.ConvertStringSqlDateTimeToPersianStringDateWithoutTime(bill.getDeadlineDate());

                        Intent intent = new Intent(context, ActivityShowBill.class);
                        intent.putExtra("BILL_ID", bill.getBillId());
//                        intent.putExtra("BILL_DES",bill.des);
//                        intent.putExtra("BILL_REGDATE",regDate);
//                        intent.putExtra("BILL_STARTDATE",startDate);
//                        intent.putExtra("BILL_ENDDATE",endDate);
//                        intent.putExtra("BILL_EXPDATE",expDate);
//                        Logger.d("onAnimationEnd","AdapterBillRecycler : bill payDate is = " + bill.payDate);
//
//                        intent.putExtra("BILL_PAYDATE",bill.payDate);
//                        intent.putExtra("BILL_PRICE",bill.price);
//                        intent.putExtra("BILL_TAX",bill.tax);
//                        intent.putExtra("BILL_DISCOUNT",bill.discount);
//                        intent.putExtra("BILL_AMOUNT",bill.amount);
//                        intent.putExtra("BILL_CLOSED",bill.closed);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.layMoreDetail) LinearLayout layMoreDetail;
        @Bind(R.id.imgMoreDetail)
        ImageView imgMoreDetail;
        //        @Bind(R.id.layStatus) LinearLayout layStatus;
        @Bind(R.id.txtBillTitle)
        TextView txtBillTitle;
        @Bind(R.id.txtBillRegDate)
        TextView txtBillRegDate;
        @Bind(R.id.txtBillEndDate)
        TextView txtBillEndDate;
        @Bind(R.id.imgNew)
        LinearLayout imgNew;

        public BillViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelBill> data) {
        bills = data;
        notifyDataSetChanged();
    }
}
