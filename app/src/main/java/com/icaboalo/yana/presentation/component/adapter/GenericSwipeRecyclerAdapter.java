package com.icaboalo.yana.presentation.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemBase;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by icaboalo on 23/01/17.
 */

public abstract class GenericSwipeRecyclerAdapter<M extends GenericSwipeRecyclerAdapter.SwipeableViewHolder> extends RecyclerSwipeAdapter<M> {

    protected Context mContext;
    public final LayoutInflater mLayoutInflater;
    private List<ItemInfo> mDataList;
    private GenericSwipeRecyclerAdapter.OnItemClickListener mOnItemClickListener;
    private boolean areItemsClickable = true, areItemsExpandable = false, hasHeader = false, hasFooter = false,
            isLoadingFooterAdded = false;

    public GenericSwipeRecyclerAdapter(Context context, List<ItemInfo> dataList) {
        mContext = context;
        mDataList = dataList;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public abstract M onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(M holder, int position) {
        ItemInfo itemInfo = mDataList.get(position);
        holder.bindData(itemInfo.getData(), position, itemInfo.isEnabled());
        if (areItemsClickable && !(hasHeader() && position == 0 || hasFooter() && position == mDataList.size() - 1)) {
            if (mOnItemClickListener != null)
                holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClicked(holder.getAdapterPosition(), itemInfo, holder));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeader)
            return ItemInfo.HEADER;
        else if (position == mDataList.size() - 1 && isLoadingFooterAdded)
            return ItemInfo.LOADING;
        else if (position == mDataList.size() - 1 && !isLoadingFooterAdded && hasFooter)
            return ItemInfo.FOOTER;
        else
            return mDataList != null ? mDataList.get(position).getLayoutId() : 0;
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public ItemInfo getItem(int index) {
        return mDataList.get(index);
    }

    public int getItemIndexById(long itemId) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getId() == itemId)
                return i;
        }
        return -1;
    }

    public boolean hasItemWithId(long id) {
        for (ItemInfo item : mDataList)
            if (item.getId() == id)
                return true;
        return false;
    }

    public ItemInfo getItemById(long itemId) throws Exception {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getId() == itemId)
                return mDataList.get(i);
        }
        throw new Exception("Item with id " + itemId + " does not exist!");
    }

    public void disableViewHolder(int index) {
        mDataList.get(index).setEnabled(false);
    }

    public void enableViewHolder(int index) {
        mDataList.get(index).setEnabled(true);
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader, String label) {
        if (!this.hasHeader && hasHeader) {
            this.hasHeader = hasHeader;
            if (hasHeader) {
                mDataList.add(0, new ItemInfo<>(label, ItemInfo.HEADER).setId(ItemInfo.HEADER));
                notifyItemInserted(0);
            } else if (mDataList.size() > 0)
                if (mDataList.get(0).getId() == ItemInfo.HEADER) {
                    mDataList.remove(0);
                    notifyItemRemoved(0);
                }
        }
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter, String label) {
        if (!this.hasFooter && hasFooter) {
            this.hasFooter = hasFooter;
            int position;
            if (hasFooter) {
                position = mDataList.size();
                mDataList.add(position, new ItemInfo<>(label, ItemInfo.FOOTER).setId(ItemInfo.FOOTER));
                notifyItemInserted(position);
            } else if (!mDataList.isEmpty()) {
                position = mDataList.size() - 1;
                if (mDataList.get(position).getId() == ItemInfo.FOOTER) {
                    mDataList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void addLoading() {
        isLoadingFooterAdded = true;
        if (mDataList.size() > 0) {
            mDataList.add(mDataList.size() - 1, new ItemInfo<Void>(null, ItemInfo.LOADING).setId(ItemInfo.LOADING));
            notifyItemInserted(mDataList.size() - 1);
        }
    }

    public void removeLoading() {
        isLoadingFooterAdded = false;
        int position = mDataList.size() - 1;
        if (position > 0) {
            ItemInfo itemInfo;
            for (int i = 0; i < mDataList.size(); i++) {
                itemInfo = mDataList.get(i);
                if (itemInfo.getId() == ItemInfo.LOADING) {
                    mDataList.remove(i);
                    notifyItemRemoved(i);
                }
            }
        }
    }

    public void setDataList(List<ItemInfo> dataSet) {
        validateList(dataSet);
        mDataList = dataSet;
        notifyDataSetChanged();
    }

    public void appendList(List<ItemInfo> dataSet) {
        validateList(dataSet);
        mDataList.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void appendList(int position, List<ItemInfo> dataSet) {
        validateList(dataSet);
        mDataList.addAll(position, dataSet);
        notifyDataSetChanged();
    }

    public boolean isSectionHeader(int index) {
        return mDataList.get(index).getId() == ItemInfo.HEADER;
    }

    public void addSectionHeader(int index, String title) {
        addItem(index, new ItemInfo<>(title, ItemInfo.SECTION_HEADER).setId(ItemInfo.SECTION_HEADER));
    }

    public void addCardSectionHeader(int index, String title) {
        addItem(index, new ItemInfo<>(title, ItemInfo.CARD_SECTION_HEADER).setId(ItemInfo.CARD_SECTION_HEADER));
    }

    public void removeSectionHeader(int index) throws Exception {
        if (mDataList.get(index).getData() instanceof String)
            mDataList.remove(index);
        else
            throw new Exception("The item at given index is not a section header.");
    }

    public List<ItemInfo> getDataList() {
        return mDataList;
    }

    public List<ItemInfo> getPureDataList() {
        List<ItemInfo> pureList = new ArrayList<>();
        pureList.addAll(mDataList);
        if (hasHeader)
            pureList.remove(0);
        if (hasFooter)
            pureList.remove(pureList.size() - 1);
        if (isLoadingFooterAdded)
            pureList.remove(pureList.size() - 1);
        ItemInfo item;
        for (int i = 0; i < pureList.size(); i++) {
            item = pureList.get(i);
            if (item.getData() instanceof String)
                pureList.remove(i);
        }
        return pureList;
    }

    public List<ItemInfo> getPureDataListWithSectionHeaders() {
        List<ItemInfo> pureList = new ArrayList<>();
        pureList.addAll(mDataList);
        if (hasHeader)
            pureList.remove(0);
        if (hasFooter)
            pureList.remove(pureList.size() - 1);
        if (isLoadingFooterAdded)
            pureList.remove(pureList.size() - 1);
        return pureList;
    }

    public void clearPureDataList() {
        int startIndex = 0, endIndex = 0;
        if (hasHeader)
            startIndex = 1;
        if (hasFooter)
            endIndex++;
        if (isLoadingFooterAdded)
            endIndex++;
        for (int i = startIndex; i < mDataList.size() - endIndex; i++)
            mDataList.remove(i);
        notifyDataSetChanged();
    }

    public void clearItemList() {
        mDataList.clear();
    }

    public void setAreItemsClickable(boolean areItemsClickable) {
        this.areItemsClickable = areItemsClickable;
    }

    public void setAreItemsExpandable(boolean areItemsExpandable) {
        this.areItemsExpandable = areItemsExpandable;
    }

    public boolean areItemsClickable() {
        return areItemsClickable;
    }

    public boolean areItemsExpandable() {
        return areItemsExpandable;
    }

    private void validateList(List<ItemInfo> dataList) {
        if (dataList == null)
            throw new IllegalArgumentException("The list cannot be null");
    }

    public void addItem(int index, ItemInfo model) {
        mDataList.add(index, model);
        notifyItemInserted(index);
        notifyItemChanged(index, mDataList.size());
    }

    public void setOnItemClickListener(GenericSwipeRecyclerAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, ItemInfo viewModel, SwipeableViewHolder holder);
    }

    public static class SwipeableViewHolder extends RecyclerView.ViewHolder implements ItemBase {

        public SwipeableViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Object data, int position, boolean isEnabled) {
        }
    }

}
