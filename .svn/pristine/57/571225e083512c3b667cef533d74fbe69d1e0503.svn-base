package gabriel.com.nwebtoon_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.commentList;

public class commentListAdapter extends RecyclerView.Adapter<commentListAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<commentList> commentContent;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public commentList mBoundcomment;
        public String date_string;

        public final View View;
        public final TextView comment_content, comment_id, comment_date, txt_like, txt_bad;
        public ViewHolder(View view) {
            super(view);
            View = view;
            comment_content = (TextView) view.findViewById(R.id.comment_content);
            comment_id = (TextView) view.findViewById(R.id.comment_id);
            comment_date = (TextView) view.findViewById(R.id.comment_date);
            txt_like = (TextView) view.findViewById(R.id.txt_like);
            txt_bad = (TextView) view.findViewById(R.id.txt_bad);
            date_string = "";

        }
    }

    public commentList getValueAt(int position) {
        return commentContent.get(position);
    }

    public commentListAdapter(Context context, List<commentList> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        commentContent = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comments, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundcomment = commentContent.get(position);
        holder.comment_content.setText(holder.mBoundcomment.CMT_CONTENT);
        holder.comment_id.setText(holder.mBoundcomment.CMT_USER);
        if(holder.date_string.equals("")) {
            Date date = new Date(holder.mBoundcomment.CMT_DATE);
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy'년 'MM'월 'dd'일 'HH:mm");
            holder.date_string = df2.format(date);
        }
        holder.comment_date.setText(holder.date_string);
        holder.txt_like.setText("좋아요("+holder.mBoundcomment.CMT_UP+")");
        holder.txt_bad.setText("싫어요("+holder.mBoundcomment.CMT_DOWN+")");

        //Glide.with(holder.img_cut.getContext()).load(holder.mBoundcomment.IMG_PATH).thumbnail(1-(holder.mBoundcomment.CT_IDX/commentContent.size())*1f).into(holder.img_cut);
    }

    @Override
    public int getItemCount() {
        return commentContent.size();
    }
}
