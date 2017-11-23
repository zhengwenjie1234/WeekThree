package com.baway.weekthree.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.weekthree.R;
import com.baway.weekthree.model.bean.JavaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 郑文杰 on 2017/11/18.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<JavaBean.DataBean> list;

    private OnClickItem onClickItem;
    public interface OnClickItem{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnClickItem(OnClickItem onClickItem){
        this.onClickItem=onClickItem;
    }
    public MyAdapter(Context context, List<JavaBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        JavaBean.DataBean dataBean = list.get(position);
        myViewHolder.tvTitle.setText(dataBean.title);
        myViewHolder.tvContent.setText(dataBean.content);
        myViewHolder.sdv.setImageURI(dataBean.image_url);
        myViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position);

            }
        });
        myViewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickItem.onLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;
        private final TextView tvTitle;
        private final SimpleDraweeView sdv;
        private final LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            sdv = itemView.findViewById(R.id.sdv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
