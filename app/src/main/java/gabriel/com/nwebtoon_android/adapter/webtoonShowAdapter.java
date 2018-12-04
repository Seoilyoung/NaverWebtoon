package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.webtoonShow;

public class webtoonShowAdapter extends RecyclerView.Adapter<webtoonShowAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<webtoonShow> webtoonShows;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public webtoonShow mBoundwebtoonShow;

        public final View View;
        public final ImageView img_cut;
        public ViewHolder(View view) {
            super(view);
            View = view;
            img_cut = (ImageView) view.findViewById(R.id.img_cut);
        }
    }

    public webtoonShow getValueAt(int position) {
        return webtoonShows.get(position);
    }

    public webtoonShowAdapter(Context context, List<webtoonShow> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        webtoonShows = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_webtoon_show, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundwebtoonShow = webtoonShows.get(position);
        Glide.with(holder.img_cut.getContext()).load(holder.mBoundwebtoonShow.IMG_PATH).thumbnail(1-(holder.mBoundwebtoonShow.CT_IDX/webtoonShows.size())*1f).into(holder.img_cut);
    }

    @Override
    public int getItemCount() {
        return webtoonShows.size();
    }
}
