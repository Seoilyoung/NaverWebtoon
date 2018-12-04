package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import gabriel.com.nwebtoon_android.Activity.ShowActivity;
import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.LocalData_CurrentWebtoon;
import gabriel.com.nwebtoon_android.model.webtoonListDetail;

public class webtoonDetailAdapter extends RecyclerView.Adapter<webtoonDetailAdapter.ViewHolder> {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일");

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<webtoonListDetail> webtoonListDetails;
        private LocalData_CurrentWebtoon localdata_currentWebtoon;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public webtoonListDetail mBoundwebtoonListDetail;

            public final View View;
            public final ImageView img_detail_thumbnail ;
            public final TextView txt_detail_name, txt_detail_rate, txt_detail_update_dt ;
            public ViewHolder(View view) {
                super(view);
                View = view;
                img_detail_thumbnail = (ImageView) view.findViewById(R.id.img_detail_thumbnail);
                txt_detail_name = (TextView) view.findViewById(R.id.txt_detail_name);
                txt_detail_rate = (TextView) view.findViewById(R.id.txt_detail_rate);
                txt_detail_update_dt = (TextView) view.findViewById(R.id.txt_detail_update_dt);
            }
        }

        public webtoonListDetail getValueAt(int position) {
            return webtoonListDetails.get(position);
        }

        public webtoonDetailAdapter(Context context, List<webtoonListDetail> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            webtoonListDetails = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_webtoon_detail, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundwebtoonListDetail = webtoonListDetails.get(position);
            holder.txt_detail_name.setText(holder.mBoundwebtoonListDetail.DT_NAME);
            holder.txt_detail_rate.setText(String.valueOf(holder.mBoundwebtoonListDetail.DT_RATE));
            Date date = new Date(holder.mBoundwebtoonListDetail.UPDATE_DT);
            holder.txt_detail_update_dt.setText(dateformat.format(date));

            holder.View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<LocalData_CurrentWebtoon> CurrentWebtoons = LocalData_CurrentWebtoon.find(LocalData_CurrentWebtoon.class, "TOONID=?", holder.mBoundwebtoonListDetail.TOON_ID);
                    if (CurrentWebtoons.size() == 0) {
                        localdata_currentWebtoon = new LocalData_CurrentWebtoon(holder.mBoundwebtoonListDetail.TOON_ID, holder.mBoundwebtoonListDetail.DT_ID,
                                holder.mBoundwebtoonListDetail.TOON_TP, holder.mBoundwebtoonListDetail.TOON_NAME, holder.mBoundwebtoonListDetail.TOON_WTR, holder.mBoundwebtoonListDetail.TOON_STAT,
                                holder.mBoundwebtoonListDetail.TOON_THM, holder.mBoundwebtoonListDetail.UPDATE_DT);
                    } else {
                        localdata_currentWebtoon = CurrentWebtoons.get(0);
                        localdata_currentWebtoon.setDT_ID(holder.mBoundwebtoonListDetail.DT_ID);
                    }
                    localdata_currentWebtoon.save();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShowActivity.class);
                    intent.putExtra("TOON_ID", holder.mBoundwebtoonListDetail.TOON_ID);
                    intent.putExtra("DT_ID", holder.mBoundwebtoonListDetail.DT_ID);
                    intent.putExtra("TOON_NAME", holder.mBoundwebtoonListDetail.TOON_NAME);
                    intent.putExtra("size", getItemCount());
                    context.startActivity(intent);
                }
            });

            Glide.with(holder.img_detail_thumbnail.getContext()).load(holder.mBoundwebtoonListDetail.DT_THM).thumbnail(0.1f).into(holder.img_detail_thumbnail);
        }

        @Override
        public int getItemCount() {
            return webtoonListDetails.size();
        }
}
