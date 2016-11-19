package com.ssdifall2016.communityhealthindicator.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssdifall2016.communityhealthindicator.R;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */
    public class DiseaseSelectorAdapter extends RecyclerView.Adapter<DiseaseSelectorAdapter.ViewHolder> {

    private final DiseaseTapListener mDiseaseTapListener;
    private ArrayList<String> mDiseaseList = new ArrayList<>();
    private Context mContext;

    public DiseaseSelectorAdapter(Context mContext, DiseaseTapListener listener) {
        mDiseaseTapListener = listener;
        this.mContext = mContext;
    }

    public void setDataset(ArrayList<String> diseaseList) {
        this.mDiseaseList = diseaseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String disease = mDiseaseList.get(position);

        holder.titleText.setText(disease);
        holder.wrapLayout.setId(position);
        holder.wrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDiseaseTapListener != null) {
                    mDiseaseTapListener.onTap(disease);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDiseaseList.size();
    }

    public interface DiseaseTapListener {
        void onTap(String disease);
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