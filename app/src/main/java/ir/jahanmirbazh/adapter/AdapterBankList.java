package ir.jahanmirbazh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Gson.ModelBank;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.classes.WebService;
import ir.jahanmirbazh.components.PersianTextViewNormal;

import static com.activeandroid.Cache.getContext;

/**
 * Created by FCC on 9/18/2017.
 */

public class AdapterBankList extends RecyclerView.Adapter<AdapterBankList.AdapterBankListHolder> {

    List<ModelBank> bankList;
    int billId;
    Context context;
    int amount;
    String Description;

    public AdapterBankList(List<ModelBank> bankList, int billId, Context context, int amount,String Description) {
        this.bankList = bankList;
        this.billId = billId;
        this.context = context;
        this.amount = amount;
        this.Description = Description;
    }

    @Override
    public AdapterBankListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_item_bank, parent, false);
        return new AdapterBankListHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterBankListHolder holder, int position) {
        final ModelBank modelBank = bankList.get(position);

        holder.txtBankName.setText(modelBank.getName());
        holder.layMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount != 0) {
                    WebService.sendPayWhitAmountRequest(getContext(), amount, modelBank.getBankId(),Description);
                } else {
                    WebService.sendPayWhitBillIdRequest(context,billId,modelBank.getBankId());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bankList != null) {
            return bankList.size();
        } else {
            return 0;
        }
    }

    public class AdapterBankListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtBankName)
        PersianTextViewNormal txtBankName;

        @Bind(R.id.imgBankImage)
        ImageView imgBankImage;

        @Bind(R.id.layMain)
        LinearLayout layMain;

        public AdapterBankListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
