package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import gabriel.com.nwebtoon_android.Activity.DetailActivity;
import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.webtoonListMyFavorite;

/**
 * Created by seoil on 2016-02-16.
 */
public class webtoonMyFavoriteAdapter extends RecyclerView.Adapter<webtoonMyFavoriteAdapter.ViewHolder> {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일");

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<webtoonListMyFavorite> webtoonListFavorites;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public webtoonListMyFavorite mBoundwebtoonListFavorite;

            public final View View;
            public final ImageView img_thumbnail, img_notification, img_stat, img_type ;
            public final TextView txt_list_name, txt_list_wtr, txt_list_update_dt;
            public final RelativeLayout layout_notification ;

            public ViewHolder(View view) {
                super(view);
                View = view;
                layout_notification = (RelativeLayout) view.findViewById(R.id.layout_notification);
                img_thumbnail = (ImageView) view.findViewById(R.id.img_thumbnail);
                img_notification = (ImageView)view.findViewById(R.id.img_notification);
                img_stat = (ImageView)view.findViewById(R.id.img_stat);
                img_type = (ImageView)view.findViewById(R.id.img_type);
                txt_list_name = (TextView) view.findViewById(R.id.txt_list_name);
                txt_list_wtr = (TextView) view.findViewById(R.id.txt_list_wtr);
                txt_list_update_dt = (TextView) view.findViewById(R.id.txt_list_update_dt);
            }
        }

        public webtoonListMyFavorite getValueAt(int position) {
            return webtoonListFavorites.get(position);
        }

        public webtoonMyFavoriteAdapter(Context context, List<webtoonListMyFavorite> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            webtoonListFavorites = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_webtoon_my_favorite, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundwebtoonListFavorite = webtoonListFavorites.get(position);
            holder.txt_list_name.setText(holder.mBoundwebtoonListFavorite.TOON_NAME);
            holder.txt_list_wtr.setText(holder.mBoundwebtoonListFavorite.TOON_WTR);
            Date date = new Date(holder.mBoundwebtoonListFavorite.UPDATE_DT);
            holder.txt_list_update_dt.setText(dateformat.format(date));

            if(holder.mBoundwebtoonListFavorite.TOON_STAT==0)
                Glide.with(holder.img_stat.getContext()).load(R.drawable.ico_end).thumbnail(0.1f).into(holder.img_stat);
            else if(holder.mBoundwebtoonListFavorite.TOON_STAT==2)
                Glide.with(holder.img_stat.getContext()).load(R.drawable.ico_rest).thumbnail(0.1f).into(holder.img_stat);

            if(holder.mBoundwebtoonListFavorite.TOON_TP.equals("smart"))
                Glide.with(holder.img_type.getContext()).load(R.drawable.ico_smart).thumbnail(0.1f).into(holder.img_type);
            else if(holder.mBoundwebtoonListFavorite.TOON_TP.equals("cut"))
                Glide.with(holder.img_type.getContext()).load(R.drawable.ico_cut).thumbnail(0.1f).into(holder.img_type);

            if(holder.mBoundwebtoonListFavorite.PUSH_OK==false)
                Glide.with(holder.img_notification.getContext()).load(R.drawable.ic_notifications_none_black_24dp).thumbnail(0.1f).into(holder.img_notification);
            else if(holder.mBoundwebtoonListFavorite.PUSH_OK==true)
                Glide.with(holder.img_notification.getContext()).load(R.drawable.ic_notifications_clicked).thumbnail(0.1f).into(holder.img_notification);

            holder.View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("TOON_ID", holder.mBoundwebtoonListFavorite.TOON_ID);
                    intent.putExtra("TOON_NAME", holder.mBoundwebtoonListFavorite.TOON_NAME);
                    context.startActivity(intent);
                }
            });

            holder.layout_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    if(holder.mBoundwebtoonListFavorite.PUSH_OK==false) {
                        Toast.makeText(context, holder.mBoundwebtoonListFavorite.TOON_ID + " + " + "해당 알림 설정", Toast.LENGTH_SHORT).show();
                        //POST 쏴주기
                    }
                    else {
                        Toast.makeText(context, holder.mBoundwebtoonListFavorite.TOON_ID + " + " + "해당 알림 해제", Toast.LENGTH_SHORT).show();
                        //POST 쏴주기
                    }
                }
            });

            Glide.with(holder.img_thumbnail.getContext()).load(holder.mBoundwebtoonListFavorite.TOON_THM).thumbnail(0.1f).into(holder.img_thumbnail);
        }

        @Override
        public int getItemCount() {
            return webtoonListFavorites.size();
        }
}
