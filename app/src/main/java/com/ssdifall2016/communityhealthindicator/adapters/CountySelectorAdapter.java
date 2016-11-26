package com.ssdifall2016.communityhealthindicator.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.models.County;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class CountySelectorAdapter extends RecyclerView.Adapter<CountySelectorAdapter.ViewHolder> {

    private final CountySelectorAdapter.CountyTapListener mCountyTapListener;
    private ArrayList<County> mCountyList;
    private Context mContext;

    public CountySelectorAdapter(Context mContext, CountyTapListener listener) {
        mCountyList = new ArrayList<>();
        mCountyTapListener = listener;
        this.mContext = mContext;
    }

    public void setDataset(ArrayList<County> countyList) {
        this.mCountyList = countyList;
    }

    @Override
    public CountySelectorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        return new CountySelectorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CountySelectorAdapter.ViewHolder holder, final int position) {

        final County county = mCountyList.get(position);

        holder.titleText.setText(county.getCountyName());
        holder.wrapLayout.setId(position);
        holder.wrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCountyTapListener != null) {
                    mCountyTapListener.onTap(county);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCountyList.size();
    }

    public interface CountyTapListener {
        void onTap(County county);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private CardView wrapLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.info_text);
            wrapLayout = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
