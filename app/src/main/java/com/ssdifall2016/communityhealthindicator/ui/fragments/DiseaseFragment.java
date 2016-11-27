package com.ssdifall2016.communityhealthindicator.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import com.ssdifall2016.communityhealthindicator.adapters.CountySelectorAdapter;
import com.ssdifall2016.communityhealthindicator.models.CountyName;
import com.ssdifall2016.communityhealthindicator.models.CountyNameList;
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

public class DiseaseFragment extends Fragment {

    @BindView(R.id.county_list)
    RecyclerView mCountyListRV;

    @BindView(R.id.emptyListLayout)
    RelativeLayout mEmptyListLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout mNoInternetLayout;

    @BindView(R.id.disease_progress)
    View mProgressView;

    private String mappedDiseaseId;

    private DownloadCountyTask downloadCountyTask = null;

    private boolean isSuccess;
    private int numberOfRetries = -1;

    private CountySelectorAdapter countySelectorAdapter;

    public DiseaseFragment() {
        // Required empty public constructor
    }

    public static DiseaseFragment newInstance() {
        DiseaseFragment fragment = new DiseaseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);
        ButterKnife.bind(this, rootView);
        setupListView();
        //downLoadCountyList();
        return rootView;
    }

    private void setupListView() {
        mCountyListRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        mCountyListRV.setLayoutManager(mLayoutManger);
        countySelectorAdapter = new CountySelectorAdapter(getActivity(), new CountySelectorAdapter.CountyTapListener() {
            @Override
            public void onTap(CountyName countyName) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(AppConstants.SELECTED_COUNTY_ID, countyName.getCountyId());
                startActivity(intent);
            }
        });
        mCountyListRV.setAdapter(countySelectorAdapter);
    }

    private void downLoadCountyList() {
        if (downloadCountyTask != null) {
            return;
        }

        Activity activity = getActivity();

        if (activity != null && isAdded()) {

            mappedDiseaseId = PreferencesUtils.getString(activity, AppConstants.MAPPED_DISEASE_ID, ""); //// TODO: 11/24/16 change later

            showProgress(true);

            downloadCountyTask = new DownloadCountyTask(getActivity(), mappedDiseaseId);
            downloadCountyTask.execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        Activity activity = getActivity();

        if (activity != null && isAdded()) {
            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
            // for very easy animations. If available, use these APIs to fade-in
            // the progress spinner.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mCountyListRV.setVisibility(show ? View.GONE : View.VISIBLE);
                mCountyListRV.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCountyListRV.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mCountyListRV.setVisibility(show ? View.GONE : View.VISIBLE);
            }
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

    /*
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
*/

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

    @Override
    public void onResume() {
        super.onResume();
        mappedDiseaseId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_DISEASE_ID, ""); //// TODO: 11/24/16 change later
        downLoadCountyList();
    }

    public class DownloadCountyTask extends AsyncTask<Void, Void, Boolean> {

        private final String mappedCountyId;
        private Context context;

        DownloadCountyTask(Context context, String mappedCountyId) {
            this.context = context;
            this.mappedCountyId = mappedCountyId;
        }

        private void setDataset(ArrayList<CountyName> countyNames) {
            countySelectorAdapter.setDataset(countyNames);
            countySelectorAdapter.notifyDataSetChanged();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            isSuccess = false;
            try {

                if (NetworkUtil.getConnectivityStatusString(context)) {
                    //enableNoInternetView(false);
                    CHIApp.get().getmChiApi().getCountyNameListApi(mappedDiseaseId, new Response.Listener<CountyNameList>() {
                        @Override
                        public void onResponse(CountyNameList response) {
                            if (response != null && response.getCountyNameList() != null && !response.getCountyNameList().isEmpty()) {
                                isSuccess = true;
                                setDataset(response.getCountyNameList());
                            } else {
                                isSuccess = false;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (numberOfRetries < 4) {
                                downLoadCountyList();
                                numberOfRetries += 1;
                            } else {
                                showProgress(false);
                                MsgUtils.displayToast(context, R.string.error_generic);
                                isSuccess = false;
                            }
                        }
                    });
                } else {
                    isSuccess = false;
                    MsgUtils.displayToast(context, R.string.error_internet_unavailable);
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return isSuccess;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            downloadCountyTask = null;
            showProgress(false);

            if (!success) {
                if (numberOfRetries < 4) {
                    downLoadCountyList();
                    numberOfRetries += 1;
                } else {
                    showEmptyList(true);
                    MsgUtils.displayToast(context, R.string.error_generic);
                }
            }
        }

        @Override
        protected void onCancelled() {
            downloadCountyTask = null;
            numberOfRetries = -1;
            showProgress(false);
        }
    }
}
