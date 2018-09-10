package com.peep.peep.floatingview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.peep.peep.Map.LocationMap;
import com.peep.peep.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "SearchSuggestionsAdapter";

    private List<? extends LocationMap> mSearchSuggestions = new ArrayList<>();

    private Listener mListener;

    private Context mContext;

    private Drawable mRightIconDrawable;
    private boolean mShowRightMoveUpBtn = false;
    private int mBodyTextSizePx;
    private int mTextColor = -1;
    private int mRightIconColor = -1;

    public interface OnBindSuggestionCallback {

        void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, TextView textView2,
                              LocationMap item, int itemPosition);
    }

    private OnBindSuggestionCallback mOnBindSuggestionCallback;

    public interface Listener {

        void onItemSelected(LocationMap item);

        void onMoveItemToSearchClicked(LocationMap item);
    }

    public static class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {

        public TextView body,body_detail;
        public ImageView leftIcon;
        public ImageView rightIcon;

        private Listener mListener;

        public interface Listener {

            void onItemClicked(int adapterPosition);

            void onMoveItemToSearchClicked(int adapterPosition);
        }

        public SearchSuggestionViewHolder(View v, Listener listener) {
            super(v);

            mListener = listener;
            body = (TextView) v.findViewById(R.id.body);
            body_detail = (TextView) v.findViewById(R.id.body_detail);
            leftIcon = (ImageView) v.findViewById(R.id.left_icon);
            rightIcon = (ImageView) v.findViewById(R.id.right_icon);

            rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        mListener.onMoveItemToSearchClicked(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        mListener.onItemClicked(adapterPosition);
                    }
                }
            });
        }
    }

    public SearchCustomAdapter(Context context, int suggestionTextSize, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mBodyTextSizePx = suggestionTextSize;

        mRightIconDrawable = Util.getWrappedDrawable(mContext, R.drawable.ic_arrow_back_black_24dp);
        DrawableCompat.setTint(mRightIconDrawable, Util.getColor(mContext, R.color.gray_active_icon));
    }

    public void swapData(List<? extends LocationMap> searchSuggestions) {
        mSearchSuggestions = searchSuggestions;
        notifyDataSetChanged();
    }

    public List<? extends LocationMap> getDataSet() {
        return mSearchSuggestions;
    }

    public void setOnBindSuggestionCallback(OnBindSuggestionCallback callback) {
        this.mOnBindSuggestionCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_of_suggestions, viewGroup, false);
        SearchSuggestionViewHolder viewHolder = new SearchSuggestionViewHolder(view,
                new SearchSuggestionViewHolder.Listener() {

                    @Override
                    public void onItemClicked(int adapterPosition) {

                        if (mListener != null) {
                            mListener.onItemSelected(mSearchSuggestions.get(adapterPosition));
                        }
                    }

                    @Override
                    public void onMoveItemToSearchClicked(int adapterPosition) {

                        if (mListener != null) {
                            mListener.onMoveItemToSearchClicked(mSearchSuggestions
                                    .get(adapterPosition));
                        }
                    }

                });

        viewHolder.rightIcon.setImageDrawable(mRightIconDrawable);
        viewHolder.body.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBodyTextSizePx);
        viewHolder.body_detail.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBodyTextSizePx);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        SearchSuggestionViewHolder viewHolder = (SearchSuggestionViewHolder) vh;

        if (!mShowRightMoveUpBtn) {
            viewHolder.rightIcon.setEnabled(false);
            viewHolder.rightIcon.setVisibility(View.INVISIBLE);
            viewHolder.body_detail.setEnabled(true);
            viewHolder.body_detail.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rightIcon.setEnabled(true);
            viewHolder.rightIcon.setVisibility(View.VISIBLE);

        }

        LocationMap suggestionItem = mSearchSuggestions.get(position);
        viewHolder.body.setText(suggestionItem.getName());
        viewHolder.body_detail.setText(suggestionItem.getLocation());

        if(mTextColor != -1){
            viewHolder.body.setTextColor(mTextColor);
        }

        if(mRightIconColor != -1){
            Util.setIconColor(viewHolder.rightIcon, mRightIconColor);
        }

        if (mOnBindSuggestionCallback != null) {
            mOnBindSuggestionCallback.onBindSuggestion(viewHolder.itemView, viewHolder.leftIcon, viewHolder.body,viewHolder.body_detail,
                    suggestionItem, position);
        }
    }

    @Override
    public int getItemCount() {
        return mSearchSuggestions != null ? mSearchSuggestions.size() : 0;
    }

    public void setTextColor(int color) {

        boolean notify = false;
        if (this.mTextColor != color) {
            notify = true;
        }
        this.mTextColor = color;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setRightIconColor(int color) {

        boolean notify = false;
        if (this.mRightIconColor != color) {
            notify = true;
        }
        this.mRightIconColor = color;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setShowMoveUpIcon(boolean show) {

        boolean notify = false;
        if (this.mShowRightMoveUpBtn != show) {
            notify = true;
        }
        this.mShowRightMoveUpBtn = show;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void reverseList() {
        Collections.reverse(mSearchSuggestions);
        notifyDataSetChanged();
    }
}
