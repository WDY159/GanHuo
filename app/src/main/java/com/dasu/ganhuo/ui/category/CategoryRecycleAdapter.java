package com.dasu.ganhuo.ui.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.ui.view.ScaleImageView;
import com.dasu.ganhuo.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2017/4/20.
 *
 * Fragment中的RecyclerView的适配器，用于显示各干货数据
 */

class CategoryRecycleAdapter extends RecyclerView.Adapter<CategoryRecycleAdapter.ViewHolder> {

    private List<GanHuoEntity> mDataList;
    private Context mContext;
    private OnItemClickListener<GanHuoEntity> mClickListener;

    CategoryRecycleAdapter(List<GanHuoEntity> data) {
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GanHuoEntity data = mDataList.get(position);
        holder.data = data;
        setDemoImage(holder.mDemoIv, data.getImages());
        holder.mTitleTv.setText(data.getDesc());
        holder.mAuthorTv.setText("© " + data.getWho());
        setDate(data.getPublishedAt(), holder.mDateTv);
    }

    private void setDemoImage(final ScaleImageView imageView, List<String> images) {
        //todo images 和 占位符需要修改
        Glide.with(mContext)
                .load(images != null ? images.get(0) : "http://www.baidu.com.jpg")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_placeholder_blank)
                .error(R.drawable.bg_placeholder_blank)
                .into(imageView);
    }

    private void setDate(Date date, TextView tv) {
        String time = TimeUtils.howLongAgo(TimeUtils.adjustDate(date));
        tv.setText(time);
    }

    public void setOnItemClickListener(OnItemClickListener<GanHuoEntity> listener) {
        mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ScaleImageView mDemoIv;
        TextView mTitleTv;
        TextView mAuthorTv;
        TextView mDateTv;
        ViewGroup mInfoLayout;
        GanHuoEntity data;

        ViewHolder(View itemView) {
            super(itemView);
            mDemoIv = (ScaleImageView) itemView.findViewById(R.id.iv_category_demo);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_category_title);
            mAuthorTv = (TextView) itemView.findViewById(R.id.tv_category_author);
            mDateTv = (TextView) itemView.findViewById(R.id.tv_category_date);
            mInfoLayout = (ViewGroup) itemView.findViewById(R.id.layout_category_info);
            mDemoIv.setOnClickListener(this);
            mInfoLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                if (v == mDemoIv) {
                    mClickListener.onImageClick(data.getImages());
                } else {
                    mClickListener.onItemClick(data);
                }
            }
        }
    }

}