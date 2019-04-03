package com.example;

/**
 * Created by wurood on 10/21/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rp.listview.R;

import java.util.List;

/**
 * Created by Vipul on 18/11/16.
 */

public class  ElementsAdapter extends RecyclerView.Adapter {
    int flag =0;
    private Context context;
    private OnItemClicked onClick;
    interface Callbacks {
        public void onClickLoadMore();
    }
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    private Callbacks mCallbacks;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean mWithHeader = false;
    private boolean mWithFooter = false;
    private List<Elements> mFeedList;

    public ElementsAdapter(Context context,List<Elements> feedList) {
        this.mFeedList = feedList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        if (viewType == TYPE_FOOTER) {
         flag=1;
            itemView = View.inflate(parent.getContext(), R.layout.row_loadmore, null);
            return new LoadMoreViewHolder(itemView);

        } else {

            itemView = View.inflate(parent.getContext(), R.layout.row_element, null);
            return new ElementsViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof LoadMoreViewHolder) {

            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;

            loadMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallbacks!=null)
                        mCallbacks.onClickLoadMore();
                }
            });

        } else {
            ElementsViewHolder elementsViewHolder = (ElementsViewHolder) holder;

            Elements elements = mFeedList.get(position);
            elementsViewHolder.icon.setImageResource(elements.getIcon());
            elementsViewHolder.name.setText(elements.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mFeedList.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0 && mWithHeader;
    }

    public boolean isPositionFooter(int position) {
        return position == getItemCount() - 1 && mWithFooter;
    }

    public void setWithHeader(boolean value){
        mWithHeader = value;
    }

    public void setWithFooter(boolean value){
        mWithFooter = value;
    }

    public void setCallback(Callbacks callbacks){
        mCallbacks = callbacks;
    }

    public class ElementsViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name;

        public ElementsViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon_elem);
            name = (TextView) itemView.findViewById(R.id.name_elem);
        }
    }

    public class LoadMoreViewHolder  extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}