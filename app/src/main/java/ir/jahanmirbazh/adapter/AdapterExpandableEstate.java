package ir.jahanmirbazh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ir.jahanmirbazh.Database.ModelEstate;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.V;
import ir.jahanmirbazh.events.EventOnSelectEstate;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class AdapterExpandableEstate extends BaseExpandableListAdapter implements View.OnClickListener {

    private Activity context;
    private List<ModelEstate> estateList;

    public AdapterExpandableEstate(Activity context, List<ModelEstate> estateList) {
        this.context = context;
        this.estateList = estateList;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return (estateList.get(groupPosition));
    }

    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.l_estate_child_item, parent, false);
        }

//        EstateDetail estateDetail = (EstateDetail) getChild(groupPosition, childPosition);
//
//        ImageView imgKhabar = (ImageView) convertView.findViewById(R.id.imgKhabar);
//        ImageView imgGhabz = (ImageView) convertView.findViewById(R.id.imgGhabz);
//        ImageView imgPardakht = (ImageView) convertView.findViewById(R.id.imgPardakht);
//        ImageView imgTicket = (ImageView) convertView.findViewById(R.id.imgTicket);
//        LinearLayout layCountNewNews = (LinearLayout) convertView.findViewById(R.id.layCountNewNews);
//        LinearLayout layCountNewBill = (LinearLayout) convertView.findViewById(R.id.layCountNewBill);
//        LinearLayout layCountNewPayment = (LinearLayout) convertView.findViewById(R.id.layCountNewPayment);
//        LinearLayout layCountNewTicket = (LinearLayout) convertView.findViewById(R.id.layCountNewTicket);
//        TextView txtCountNewNews = (TextView) convertView.findViewById(R.id.txtCountNewNews);
//        TextView txtCountNewBill = (TextView) convertView.findViewById(R.id.txtCountNewBill);
//        TextView txtCountNewPayment = (TextView) convertView.findViewById(R.id.txtCountNewPayment);
//        TextView txtCountNewTicketDetail = (TextView) convertView.findViewById(R.id.txtCountNewTicketDetail);
//
//        if(estateDetail.countNewBill == 0){
//            layCountNewBill.setVisibility(View.GONE);
//        }else{
//            layCountNewBill.setVisibility(View.VISIBLE);
//            txtCountNewBill.setText("" + estateDetail.countNewBill);
//        }
//        if(estateDetail.countNewPayment == 0){
//            layCountNewPayment.setVisibility(View.GONE);
//        }else{
//            layCountNewPayment.setVisibility(View.VISIBLE);
//            txtCountNewPayment.setText("" + estateDetail.countNewPayment);
//        }
//        if(estateDetail.countNewNews == 0){
//            layCountNewNews.setVisibility(View.GONE);
//        }else{
//            layCountNewNews.setVisibility(View.VISIBLE);
//            txtCountNewNews.setText("" + estateDetail.countNewNews);
//        }
//        if(estateDetail.countNewTicketDetail == 0){
//            layCountNewTicket.setVisibility(View.GONE);
//        }else{
//            layCountNewTicket.setVisibility(View.VISIBLE);
//            txtCountNewTicketDetail.setText("" + estateDetail.countNewTicketDetail);
//        }
//
//
//
//
//
//
//
//        imgTicket.setTag(estateDetail.estate);
//        imgGhabz.setTag(estateDetail.estate);
//        imgKhabar.setTag(estateDetail.estate);
//        imgPardakht.setTag(estateDetail.estate);
//        imgTicket.setOnClickListener(this);
//        imgGhabz.setOnClickListener(this);
//        imgKhabar.setOnClickListener(this);
//        imgPardakht.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgGhabz:
//                EventBus.getDefault().post(new EventOnClickedEstateItemMenu((Estate) view.getTag(), EnumEstateMenuItem.BILL));
                break;
            case R.id.imgKhabar:
//                EventBus.getDefault().post(new EventOnClickedEstateItemMenu((Estate) view.getTag(), EnumEstateMenuItem.NEWS));
                break;
            case R.id.imgTicket:
//                EventBus.getDefault().post(new EventOnClickedEstateItemMenu((Estate) view.getTag(), EnumEstateMenuItem.TICKET));
                break;
            case R.id.imgPardakht:
//                EventBus.getDefault().post(new EventOnClickedEstateItemMenu((Estate) view.getTag(), EnumEstateMenuItem.PAYMENT));
                break;
        }
    }

    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    public Object getGroup(int groupPosition) {
        return estateList.get(groupPosition);
    }

    public int getGroupCount() {
        return estateList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ModelEstate estate = (ModelEstate) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.l_estate_parent_item, parent, false);
        }
        LinearLayout layBackEstateName = (LinearLayout) convertView.findViewById(R.id.layBackEstateName);
        TextView txtEstateName = (TextView) convertView.findViewById(R.id.txtEstateName);
        ImageView imgExpandStatus = (ImageView) convertView.findViewById(R.id.imgExpandStatus);
        FrameLayout layEstateHeader = (FrameLayout)convertView.findViewById(R.id.layEstateHeader);

        txtEstateName.setText("" + estate.getName() + " (" + estate.getOwnerType() + ")");
        layBackEstateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnSelectEstate(estate));
            }
        });

        layEstateHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnSelectEstate(estate));

            }
        });

        if (isExpanded)
            imgExpandStatus.setImageResource(R.drawable.ic_collapse_estate_menu);
        else
            imgExpandStatus.setImageResource(R.drawable.ic_expand_estate_menu);


        /** dar surati ke melke konuni current estate bashad va tedad melk haye shaksh bishtar az ye mored bashad.*/
        if (V.currentEstate == estate) {
//            if (estateList.size() > 1)
                layBackEstateName.setBackgroundResource(R.drawable.back_estate_name_selected);
        } else {
            layBackEstateName.setBackgroundResource(R.drawable.back_estate_name_normal);
        }

        return convertView;
    }


    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateList(List<ModelEstate> data) {
        estateList = data;
        notifyDataSetChanged();
    }
}
