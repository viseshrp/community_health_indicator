package com.ssdifall2016.communityhealthindicator.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.ssdifall2016.communityhealthindicator.CHIApp;
import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.adapters.CountySelectorAdapter;
import com.ssdifall2016.communityhealthindicator.models.County;
import com.ssdifall2016.communityhealthindicator.models.CountyList;
import com.ssdifall2016.communityhealthindicator.ui.activity.InfoActivity;
import com.ssdifall2016.communityhealthindicator.ui.activity.MainActivity;
import com.ssdifall2016.communityhealthindicator.utils.AppConstants;
import com.ssdifall2016.communityhealthindicator.utils.MsgUtils;
import com.ssdifall2016.communityhealthindicator.utils.NetworkUtil;
import com.ssdifall2016.communityhealthindicator.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiseaseFragment extends Fragment {

    @BindView(R.id.county_list)
    RecyclerView mCountyListRV;

    @BindView(R.id.emptyListLayout)
    RelativeLayout mEmptyListLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout mNoInternetLayout;

    private int mappedDiseaseId;

    private CountySelectorAdapter countySelectorAdapter;

    public DiseaseFragment() {
        // Required empty public constructor
    }

    public static DiseaseFragment newInstance() {
        DiseaseFragment fragment = new DiseaseFragment();
/*
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mappedDiseaseId = PreferencesUtils.getInt(getActivity(), AppConstants.MAPPED_DISEASE, 1); //// TODO: 11/24/16 change later

/*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);
        ButterKnife.bind(this, rootView);
        setupListView();
        downLoadCountyList();
        return rootView;
    }

    private void setupListView() {
        mCountyListRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        mCountyListRV.setLayoutManager(mLayoutManger);
        countySelectorAdapter = new CountySelectorAdapter(getActivity(), new CountySelectorAdapter.CountyTapListener() {
            @Override
            public void onTap(County county) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(AppConstants.SELECTED_COUNTY, county.getCountyName());
                startActivity(intent);
            }
        });
        mCountyListRV.setAdapter(countySelectorAdapter);
    }

    private void downLoadCountyList() {
        if (NetworkUtil.getConnectivityStatusString(getActivity())) {
            enableNoInternetView(false);
            ((MainActivity) getActivity()).showProgressDialog(getString(R.string.progress_dialog_loading_text));
            CHIApp.get().getmChiApi().getCountyListApi(mappedDiseaseId, new Response.Listener<CountyList>() {
                @Override
                public void onResponse(CountyList response) {
                    ((MainActivity) getActivity()).dismissProgressDialog();
                    if (response != null && response.getCountyList() != null && !response.getCountyList().isEmpty()) {
                        showEmptyList(false);
                        setDataset(response.getCountyList());
                    } else {
                        showEmptyList(true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ((MainActivity) getActivity()).dismissProgressDialog();
                    showEmptyList(true);
                }
            });
        } else {
            enableNoInternetView(true);
            MsgUtils.displayToast(getActivity(), R.string.error_internet_unavailable);
        }
    }

    private void showEmptyList(boolean value) {
        if (mEmptyListLayout != null && mCountyListRV != null) {
            if (value) {
                //Show empty list layout
                mEmptyListLayout.setVisibility(View.VISIBLE);
                mCountyListRV.setVisibility(View.GONE);
            } else {
                //Show list view;
                mEmptyListLayout.setVisibility(View.GONE);
                mCountyListRV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void enableNoInternetView(boolean value) {
        if (mNoInternetLayout != null && mCountyListRV != null) {
            if (value) {
                mNoInternetLayout.setVisibility(View.VISIBLE);
                mCountyListRV.setVisibility(View.GONE);
            } else {
                mNoInternetLayout.setVisibility(View.GONE);
                mCountyListRV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setDataset(ArrayList<County> countyList) {
        countySelectorAdapter.setDataset(countyList);
        countySelectorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downLoadCountyList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
