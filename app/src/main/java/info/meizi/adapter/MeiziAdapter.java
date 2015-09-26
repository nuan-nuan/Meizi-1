package info.meizi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import info.meizi.R;
import info.meizi.bean.TestContent;
import info.meizi.widget.RadioImageView;

/**
 * Created by Mr_Wrong on 15/9/14.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MyViewHolder> {
    private List<TestContent> mDatas;
    private LayoutInflater mInflater;
    private Context mContetx;

    /**
     * 点击事件接口
     */
    public interface OnitemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int pisition);
    }

    private OnitemClickListener mOnitemClickListener;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.mOnitemClickListener = onitemClickListener;
    }

    public MeiziAdapter(Context context, List<TestContent> mDatas) {
        this.mDatas = mDatas;
        this.mContetx = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.meizi_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
        TestContent content = mDatas.get(i);
        //使用Picasso来进行图片的加载
        Picasso.with(mContetx).load(content.getUrl()).tag("1").config(Bitmap.Config.RGB_565).
                transform(new CopyOnWriteArrayList<Transformation>()).
                into(holder.imageView);

//        Picasso.with(mContetx).load(content.getUrl()).tag("1").
//                memoryPolicy(NOCACHE, NO_STORE).
//                transform(new CopyOnWriteArrayList<Transformation>()).
//                into(holder.imageView);

        holder.imageView.setOriginalSize(content.getImagewidth(), content.getImageheight());


        //如果设置回调，设置点击事件
        if (mOnitemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    //删除数据
    public void removeData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    //添加数据
    public void addData(int position, TestContent content) {
        mDatas.add(position, content);
        notifyItemInserted(position);
    }


    public void addAll(List<TestContent> lists) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.remove(i);
        }
        mDatas.addAll(lists);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RadioImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (RadioImageView) view.findViewById(R.id.iv_item);
        }
    }
}

