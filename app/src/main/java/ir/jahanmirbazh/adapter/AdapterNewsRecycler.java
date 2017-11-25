package ir.jahanmirbazh.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.jahanmirbazh.Database.ModelNews;
import ir.jahanmirbazh.R;
import ir.jahanmirbazh.bazh.ActivityShowNews;

public class AdapterNewsRecycler extends RecyclerView.Adapter<AdapterNewsRecycler.NewsViewHolder> {

    List<ModelNews> newses = new ArrayList<>();

    public AdapterNewsRecycler(List<ModelNews> newses) {
        this.newses = newses;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final ModelNews news = newses.get(position);
        holder.txtNewsTitle.setText(news.getTitle());
        if (!news.isSeen()) {
            holder.imgNew.setVisibility(View.VISIBLE);
        } else {
            holder.imgNew.setVisibility(View.GONE);
        }
        //holder.txtNewsContent.setText(Html.fromHtml(stripHTMLtagsExceptIMG(news.content), new EmptyImageGetter(), null));
        holder.txtNewsDate.setText(news.getRegisterDateTime());
        holder.imgMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadein = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
                holder.imgMoreDetail.startAnimation(animFadein);
                Intent intent = new Intent(view.getContext(), ActivityShowNews.class);
                intent.putExtra("NewsId", news.getNewsId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        //        @Bind(R.id.layBackNewsItem) LinearLayout layBackNewsItem;
        @Bind(R.id.layMoreDetail)
        FrameLayout layMoreDetail;
        @Bind(R.id.imgMoreDetail)
        ImageView imgMoreDetail;
        @Bind(R.id.txtNewsTitle)
        TextView txtNewsTitle;
        //        @Bind(R.id.txtNewsContent)TextView txtNewsContent;
        @Bind(R.id.txtNewsDate)
        TextView txtNewsDate;

        @Bind(R.id.imgNew)
        LinearLayout imgNew;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateList(List<ModelNews> data) {
        newses = data;
        notifyDataSetChanged();
    }

    String stripHTMLtagsExceptIMG(String htmlString) {
        String subbed = htmlString.replaceAll("< *[iI][mM][gG]", "_iimmgg");
        String stripped = android.text.Html.fromHtml(subbed).toString();
        String unsubbed = stripped.replaceAll("_iimmgg", "<img");
        return unsubbed;
    }
}
