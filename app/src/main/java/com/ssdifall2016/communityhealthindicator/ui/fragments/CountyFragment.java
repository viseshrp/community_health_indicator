package com.ssdifall2016.communityhealthindicator.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.ssdifall2016.communityhealthindicator.CHIApp;
import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.adapters.DiseaseSelectorAdapter;
import com.ssdifall2016.communityhealthindicator.models.DiseaseName;
import com.ssdifall2016.communityhealthindicator.models.DiseaseNameList;
import com.ssdifall2016.communityhealthindicator.ui.activity.InfoActivity;
import com.ssdifall2016.communityhealthindicator.ui.activity.MainActivity;
import com.ssdifall2016.communityhealthindicator.utils.AppConstants;
import com.ssdifall2016.communityhealthindicator.utils.MsgUtils;
import com.ssdifall2016.communityhealthindicator.utils.NetworkUtil;
import com.ssdifall2016.communityhealthindicator.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountyFragment extends Fragment {

    @BindView(R.id.disease_list)
    RecyclerView mDiseaseListRV;

    @BindView(R.id.emptyListLayout)
    RelativeLayout mEmptyListLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout mNoInternetLayout;

    private String mappedCountyId;

    private DiseaseSelectorAdapter diseaseSelectorAdapter;

    public CountyFragment() {
        // Required empty public constructor
    }

    public static CountyFragment newInstance() {
        CountyFragment fragment = new CountyFragment();
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
        mappedCountyId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY_ID, ""); //// TODO: 11/24/16 change to null later

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
        View rootView = inflater.inflate(R.layout.fragment_county, container, false);
        ButterKnife.bind(this, rootView);
        setupListView();
        downLoadDiseaseList();
        return rootView;
    }

    private void setupListView() {
        mDiseaseListRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        mDiseaseListRV.setLayoutManager(mLayoutManger);
        diseaseSelectorAdapter = new DiseaseSelectorAdapter(getActivity(), new DiseaseSelectorAdapter.DiseaseTapListener() {
            @Override
            public void onTap(DiseaseName diseaseName) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(AppConstants.SELECTED_DISEASE_ID, diseaseName.getDiseaseId());
                startActivity(intent);
            }
        });
        mDiseaseListRV.setAdapter(diseaseSelectorAdapter);
    }

    private void downLoadDiseaseList() {
        if (NetworkUtil.getConnectivityStatusString(getActivity())) {
            enableNoInternetView(false);
            ((MainActivity) getActivity()).showProgressDialog(getString(R.string.progress_dialog_loading_text), true);
            CHIApp.get().getmChiApi().getDiseaseNameListApi(mappedCountyId, new Response.Listener<DiseaseNameList>() {
                @Override
                public void onResponse(DiseaseNameList response) {
                    Log.e("downloaddisease", "Success");
                    if (((getActivity()) != null))
                        ((MainActivity) getActivity()).dismissProgressDialog();
                    if (response != null && response.getDiseaseNameList() != null && !response.getDiseaseNameList().isEmpty()) {
                        showEmptyList(false);
                        setDataset(response.getDiseaseNameList());
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
        if (mEmptyListLayout != null && mDiseaseListRV != null) {
            if (value) {
                //Show empty list layout
                mEmptyListLayout.setVisibility(View.VISIBLE);
                mDiseaseListRV.setVisibility(View.GONE);
            } else {
                //Show list view;
                mEmptyListLayout.setVisibility(View.GONE);
                mDiseaseListRV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void enableNoInternetView(boolean value) {
        if (mNoInternetLayout != null && mDiseaseListRV != null) {
            if (value) {
                mNoInternetLayout.setVisibility(View.VISIBLE);
                mDiseaseListRV.setVisibility(View.GONE);
            } else {
                mNoInternetLayout.setVisibility(View.GONE);
                mDiseaseListRV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setDataset(ArrayList<DiseaseName> diseaseNameList) {
        diseaseSelectorAdapter.setDataset(diseaseNameList);
        diseaseSelectorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downLoadDiseaseList();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mappedCountyId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY_ID, ""); //// TODO: 11/24/16 change to null later
        downLoadDiseaseList();
    }
}