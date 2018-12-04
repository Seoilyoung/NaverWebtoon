package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import gabriel.com.nwebtoon_android.Activity.DetailActivity;
import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.webtoonListSearch;

/**
 * Created by seoil on 2016-03-17.
 */
public class webtoonSearchAdapter extends RecyclerView.Adapter<webtoonSearchAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<webtoonListSearch> webtoonListSearches;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public webtoonListSearch mBoundwebtoonListSearch;

            public final View View;
            public final ImageView img_search_thumbnail ;
            public final TextView txt_search_name, txt_search_rate, txt_search_wtr ;
            public ViewHolder(View view) {
                super(view);
                View = view;
                img_search_thumbnail = (ImageView) view.findViewById(R.id.img_search_thumbnail);
                txt_search_name = (TextView) view.findViewById(R.id.txt_search_name);
                txt_search_rate = (TextView) view.findViewById(R.id.txt_search_rate);
                txt_search_wtr = (TextView) view.findViewById(R.id.txt_search_wtr);
            }
        }

        public webtoonListSearch getValueAt(int position) {
            return webtoonListSearches.get(position);
        }

        public webtoonSearchAdapter(Context context, List<webtoonListSearch> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            webtoonListSearches = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_webtoon_search, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundwebtoonListSearch = webtoonListSearches.get(position);
            holder.txt_search_name.setText(holder.mBoundwebtoonListSearch.TOON_NAME);
            holder.txt_search_rate.setText(String.valueOf(holder.mBoundwebtoonListSearch.TOON_RATE));
            holder.txt_search_wtr.setText(holder.mBoundwebtoonListSearch.TOON_WTR);

            holder.View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("TOON_ID", holder.mBoundwebtoonListSearch.TOON_ID);
                    intent.putExtra("TOON_NAME", holder.mBoundwebtoonListSearch.TOON_NAME);
                    context.startActivity(intent);
                }
            });

            Glide.with(holder.img_search_thumbnail.getContext()).load(holder.mBoundwebtoonListSearch.TOON_THM).thumbnail(0.1f).into(holder.img_search_thumbnail);
        }

        @Override
        public int getItemCount() {
            return webtoonListSearches.size();
        }


}
