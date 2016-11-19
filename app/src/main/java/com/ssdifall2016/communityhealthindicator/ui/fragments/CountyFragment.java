package com.ssdifall2016.communityhealthindicator.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.ssdifall2016.communityhealthindicator.adapters.DiseaseSelectorAdapter;
import com.ssdifall2016.communityhealthindicator.models.Disease;
import com.ssdifall2016.communityhealthindicator.ui.activity.InfoActivity;
import com.ssdifall2016.communityhealthindicator.ui.activity.MainActivity;
import com.ssdifall2016.communityhealthindicator.utils.AppConstants;
import com.ssdifall2016.communityhealthindicator.utils.MsgUtils;
import com.ssdifall2016.communityhealthindicator.utils.NetworkUtil;
import com.ssdifall2016.communityhealthindicator.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CountyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CountyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.disease_list)
    RecyclerView mDiseaseListRV;

    @BindView(R.id.emptyListLayout)
    RelativeLayout mEmptyListLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout mNoInternetLayout;

    private String userEmail;
    private String mappedCounty;

    private DiseaseSelectorAdapter diseaseSelectorAdapter;

    public CountyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountyFragment newInstance(String param1, String param2) {
        CountyFragment fragment = new CountyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEmail = PreferencesUtils.getString(getActivity(), AppConstants.EMAIL, null);
        mappedCounty = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY, null);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            public void onTap(String disease) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(AppConstants.SELECTED_DISEASE, disease);
                startActivity(intent);
                getActivity().finish();
            }
        });
        mDiseaseListRV.setAdapter(diseaseSelectorAdapter);
    }

    private void downLoadDiseaseList() {
        if (NetworkUtil.getConnectivityStatusString(getActivity())) {
            enableNoInternetView(false);
            ((MainActivity) getActivity()).showProgressDialog(getString(R.string.progress_dialog_loading_text));
            CHIApp.get().getmChiApi().getDiseaseListApi(userEmail, mappedCounty, new Response.Listener<Disease>() {
                @Override
                public void onResponse(Disease response) {
                    ((MainActivity) getActivity()).dismissProgressDialog();
                    if (response != null && response.getDiseaseList() != null && !response.getDiseaseList().isEmpty()) {
                        showEmptyList(false);
                        setDataset(response.getDiseaseList());
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

    private void setDataset(ArrayList<String> diseaseList) {
        diseaseSelectorAdapter.setDataset(diseaseList);
        diseaseSelectorAdapter.notifyDataSetChanged();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        downLoadDiseaseList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
