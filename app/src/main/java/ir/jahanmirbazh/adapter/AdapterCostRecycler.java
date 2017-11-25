package ir.jahanmirbazh.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ir.jahanmirbazh.Database.ModelCost;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityShowCost;

/**
 * Created by FCC on 8/22/2017.
 */

public class AdapterCostRecycler extends RecyclerView.Adapter<AdapterCostRecycler.AdapterCostRecyclerHolder> {

    List<ModelCost> modelCosts;

    public AdapterCostRecycler(List<ModelCost> modelCosts) {
        this.modelCosts = modelCosts;
    }

    @Override
    public AdapterCostRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_cost_item, parent, false);
        return new AdapterCostRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCostRecyclerHolder holder, int position) {
        final ModelCost modelCost = modelCosts.get(position);
        String pattern = "#,###";
        DecimalFormat format = new DecimalFormat(pattern);

        holder.txtCostAmount.setText(format.format(modelCost.getAmount()));
        holder.txtCostRegDate.setText(modelCost.getCostDate());
        holder.txtCostTitle.setText(modelCost.getTitle());

        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityShowCost.class);
                intent.putExtra("CostId",modelCost.getCostId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCosts.size();
    }

    class AdapterCostRecyclerHolder extends RecyclerView.ViewHolder {

        TextView txtCostTitle;
        TextView txtCostAmount;
        TextView txtCostRegDate;
        ImageView imgMoreDetail;

        public AdapterCostRecyclerHolder(View itemView) {
            super(itemView);
            txtCostRegDate = (TextView) itemView.findViewById(R.id.txtCostRegDate);
            txtCostAmount = (TextView) itemView.findViewById(R.id.txtCostAmount);
            txtCostTitle = (TextView) itemView.findViewById(R.id.txtCostTitle);
            imgMoreDetail = (ImageView)itemView.findViewById(R.id.imgMoreDetail);

        }
    }
}
