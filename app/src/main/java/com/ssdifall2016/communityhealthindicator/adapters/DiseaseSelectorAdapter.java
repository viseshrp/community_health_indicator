package com.ssdifall2016.communityhealthindicator.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.models.DiseaseName;

import java.util.ArrayList;

/**
 * Created by viseshprasad on 11/18/16.
 */

public class DiseaseSelectorAdapter extends RecyclerView.Adapter<DiseaseSelectorAdapter.ViewHolder> {

    private final DiseaseTapListener mDiseaseTapListener;
    private ArrayList<DiseaseName> mDiseaseNameList;
    private Context mContext;

    public DiseaseSelectorAdapter(Context mContext, DiseaseTapListener listener) {
        mDiseaseNameList = new ArrayList<>();
        mDiseaseTapListener = listener;
        this.mContext = mContext;
    }

    public void setDataset(ArrayList<DiseaseName> diseaseNameList) {
        this.mDiseaseNameList = diseaseNameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DiseaseName diseaseName = mDiseaseNameList.get(position);

        holder.titleText.setText(diseaseName.getDiseaseName());

        holder.wrapLayout.setId(position);
        holder.wrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDiseaseTapListener != null) {
                    mDiseaseTapListener.onTap(diseaseName);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDiseaseNameList.size();
    }

    public interface DiseaseTapListener {
        void onTap(DiseaseName diseaseName);
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