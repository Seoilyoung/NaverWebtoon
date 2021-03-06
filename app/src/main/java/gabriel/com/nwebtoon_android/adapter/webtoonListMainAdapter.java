package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import gabriel.com.nwebtoon_android.Activity.DetailActivity;
import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.webtoonListDay;

public class webtoonListMainAdapter extends RecyclerView.Adapter<webtoonListMainAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<webtoonListDay> webtoonListMys;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public webtoonListDay mBoundwebtoonListDay;
        public webtoonListDay mBoundwebtoonListDay2;
        public webtoonListDay mBoundwebtoonListDay3;

        public final ImageView img_thumbnail1,img_thumbnail2,img_thumbnail3;
        public final TextView txt_list_name1,txt_list_name2,txt_list_name3;
        public final TextView txt_rating1, txt_rating2, txt_rating3;
        public final ImageView status_icon1, status_icon2, status_icon3;
        public final TextView txt_author1, txt_author2, txt_author3;
        public final RelativeLayout webtoon_item1,webtoon_item2,webtoon_item3;

        public ViewHolder(View view) {
            super(view);
            Log.e("ListMainAdapter", "ViewHolder Public constructor");
            img_thumbnail1 = (ImageView) view.findViewById(R.id.img_thumbnail1);
            img_thumbnail2 = (ImageView) view.findViewById(R.id.img_thumbnail2);
            img_thumbnail3 = (ImageView) view.findViewById(R.id.img_thumbnail3);
            txt_list_name1 = (TextView) view.findViewById(R.id.txt_list_name1);
            txt_list_name2 = (TextView) view.findViewById(R.id.txt_list_name2);
            txt_list_name3 = (TextView) view.findViewById(R.id.txt_list_name3);
            txt_rating1 = (TextView) view.findViewById(R.id.txt_rating1);
            txt_rating2 = (TextView) view.findViewById(R.id.txt_rating2);
            txt_rating3 = (TextView) view.findViewById(R.id.txt_rating3);
            status_icon1 = (ImageView) view.findViewById(R.id.status_icon1);
            status_icon2 = (ImageView) view.findViewById(R.id.status_icon2);
            status_icon3 = (ImageView) view.findViewById(R.id.status_icon3);
            txt_author1 = (TextView) view.findViewById(R.id.txt_author1);
            txt_author2 = (TextView) view.findViewById(R.id.txt_author2);
            txt_author3 = (TextView) view.findViewById(R.id.txt_author3);
            webtoon_item1 = (RelativeLayout) view.findViewById(R.id.webtoon_item1);
            webtoon_item2 = (RelativeLayout) view.findViewById(R.id.webtoon_item2);
            webtoon_item3 = (RelativeLayout) view.findViewById(R.id.webtoon_item3);
        }
    }

    public webtoonListDay getValueAt(int position) {
        Log.e("ListMainAdapter","getValueAt called" );
        return webtoonListMys.get(position);
    }

    public webtoonListMainAdapter(Context context, List<webtoonListDay> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        webtoonListMys = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_webtoon_main, parent, false);
        Log.e("ListMainAdapter","onCreateViewHoldeer called" );
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int numOfRow = (int)(position / 3);
        final int pos = position;
        if(position % 3 != 0){
            numOfRow++;
        }
        position *= 3;
        holder.mBoundwebtoonListDay = webtoonListMys.get(position);
        Glide.with(holder.img_thumbnail1.getContext()).load(holder.mBoundwebtoonListDay.TOON_THM).thumbnail(0.1f).into(holder.img_thumbnail1);
        holder.webtoon_item1.setVisibility(View.VISIBLE);
        holder.txt_list_name1.setText(holder.mBoundwebtoonListDay.TOON_NAME);
        holder.txt_rating1.setText(Float.toString(holder.mBoundwebtoonListDay.TOON_RATE));
        holder.txt_author1.setText(holder.mBoundwebtoonListDay.TOON_WTR);
        holder.webtoon_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OnClickListener","pos("+(pos*3)+") is clicked ("+holder.mBoundwebtoonListDay.TOON_NAME+")");
                Context context = null;
                context = v.getContext();
                if(context != null) {
                    //Toast.makeText(context, holder.mBoundwebtoonListDay.TOON_ID, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("TOON_ID", holder.mBoundwebtoonListDay.TOON_ID);
                    intent.putExtra("TOON_NAME", holder.mBoundwebtoonListDay.TOON_NAME);
                    context.startActivity(intent);
                }
            }
        });
        if((position+1) < webtoonListMys.size()) {
            holder.mBoundwebtoonListDay2 = webtoonListMys.get(position + 1);
            Glide.with(holder.img_thumbnail2.getContext()).load(holder.mBoundwebtoonListDay2.TOON_THM).thumbnail(0.1f).into(holder.img_thumbnail2);
            holder.webtoon_item2.setVisibility(View.VISIBLE);
            holder.txt_list_name2.setText(holder.mBoundwebtoonListDay2.TOON_NAME);
            holder.txt_rating2.setText(Float.toString(holder.mBoundwebtoonListDay2.TOON_RATE));
            holder.txt_author2.setText(holder.mBoundwebtoonListDay2.TOON_WTR);
            holder.webtoon_item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("OnClickListener", "pos(" + ((pos * 3) + 1) + ") is clicked (" + holder.mBoundwebtoonListDay2.TOON_NAME + ")");
                    Context context = null;
                    context = v.getContext();
                    if(context != null) {
                        //Toast.makeText(context, holder.mBoundwebtoonListDay2.TOON_ID, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("TOON_ID", holder.mBoundwebtoonListDay2.TOON_ID);
                        intent.putExtra("TOON_NAME", holder.mBoundwebtoonListDay2.TOON_NAME);
                        context.startActivity(intent);
                    }
                }
            });
        }
        if((position+2) < webtoonListMys.size()) {
            holder.mBoundwebtoonListDay3 = webtoonListMys.get(position + 2);
            Glide.with(holder.img_thumbnail3.getContext()).load(holder.mBoundwebtoonListDay3.TOON_THM).thumbnail(0.1f).into(holder.img_thumbnail3);
            holder.webtoon_item3.setVisibility(View.VISIBLE);
            holder.txt_list_name3.setText(holder.mBoundwebtoonListDay3.TOON_NAME);
            holder.txt_rating3.setText(Float.toString(holder.mBoundwebtoonListDay3.TOON_RATE));
            holder.txt_author3.setText(holder.mBoundwebtoonListDay3.TOON_WTR);
            holder.webtoon_item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("OnClickListener", "pos(" + (pos * 3 + 2) + ") is clicked (" + holder.mBoundwebtoonListDay3.TOON_NAME + ")");
                    Context context = null;
                    context = v.getContext();
                    if(context != null) {
                        //Toast.makeText(context, holder.mBoundwebtoonListDay3.TOON_ID, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("TOON_ID", holder.mBoundwebtoonListDay3.TOON_ID);
                        intent.putExtra("TOON_NAME", holder.mBoundwebtoonListDay3.TOON_NAME);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int numOfRow = (int)(webtoonListMys.size() / 3);
        if(webtoonListMys.size() % 3 != 0){
            numOfRow++;
        }
        return numOfRow;
    }
}
