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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.ssdifall2016.communityhealthindicator.ui.activity.LoginActivity;
import com.ssdifall2016.communityhealthindicator.ui.activity.MainActivity;
import com.ssdifall2016.communityhealthindicator.ui.views.MyRecyclerView;
import com.ssdifall2016.communityhealthindicator.utils.AppConstants;
import com.ssdifall2016.communityhealthindicator.utils.MsgUtils;
import com.ssdifall2016.communityhealthindicator.utils.NetworkUtil;
import com.ssdifall2016.communityhealthindicator.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountyFragment extends Fragment {

    @BindView(R.id.disease_list)
    MyRecyclerView mDiseaseListRV;

    @BindView(R.id.emptyListLayout)
    RelativeLayout mEmptyListLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout mNoInternetLayout;

    @BindView(R.id.county_progress)
    View mProgressView;

    private String mappedCountyId;
    private DownloadDiseaseTask downloadDiseaseTask = null;

    private boolean isSuccess;
    private int numberOfRetries = -1;

    private DiseaseSelectorAdapter diseaseSelectorAdapter;

    public CountyFragment() {
        // Required empty public constructor
    }

    public static CountyFragment newInstance() {
        CountyFragment fragment = new CountyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_county, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Activity activity = getActivity();

            if (activity != null && isAdded()) {
                mappedCountyId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY_ID, ""); //// TODO: 11/24/16 change to null later
                downLoadDiseaseList();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_county, container, false);
        ButterKnife.bind(this, rootView);
        setupListView();
        //downLoadDiseaseList();
        return rootView;
    }

    private void setupListView() {
        Activity activity = getActivity();

        if (activity != null && isAdded()) {

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
    }

    private void downLoadDiseaseList() {
        if (downloadDiseaseTask != null) {
            return;
        }

        Activity activity = getActivity();

        if (activity != null && isAdded()) {

            mappedCountyId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY_ID, ""); //// TODO: 11/24/16 change to null later
            showProgress(true);
            downloadDiseaseTask = new DownloadDiseaseTask(getActivity(), mappedCountyId);
            downloadDiseaseTask.execute((Void) null);
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

                mDiseaseListRV.setVisibility(show ? View.GONE : View.VISIBLE);
                mDiseaseListRV.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDiseaseListRV.setVisibility(show ? View.GONE : View.VISIBLE);
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
                mDiseaseListRV.setVisibility(show ? View.GONE : View.VISIBLE);
            }
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

    /*

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
*/

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
        Activity activity = getActivity();

        if (activity != null && isAdded()) {
            mappedCountyId = PreferencesUtils.getString(getActivity(), AppConstants.MAPPED_COUNTY_ID, ""); //// TODO: 11/24/16 change to null later
            downLoadDiseaseList();
        }
    }

    public class DownloadDiseaseTask extends AsyncTask<Void, Void, Boolean> {

        private final String mappedCountyId;
        private Context context;

        DownloadDiseaseTask(Context context, String mappedCountyId) {
            this.context = context;
            this.mappedCountyId = mappedCountyId;
        }

        private void setDataset(ArrayList<DiseaseName> diseaseNameList) {
            diseaseSelectorAdapter.setDataset(diseaseNameList);
            diseaseSelectorAdapter.notifyDataSetChanged();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            isSuccess = false;
            try {
                if (NetworkUtil.getConnectivityStatusString(context)) {
                    //enableNoInternetView(false);
                    CHIApp.get().getmChiApi().getDiseaseNameListApi(mappedCountyId, new Response.Listener<DiseaseNameList>() {
                        @Override
                        public void onResponse(DiseaseNameList response) {
                            if (response != null && response.getDiseaseNameList() != null && !response.getDiseaseNameList().isEmpty()) {
                                Log.e("downloaddisease", "Success");
                                isSuccess = true;
                                setDataset(response.getDiseaseNameList());
                            } else {
                                isSuccess = false;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (numberOfRetries < 4) {
                                downLoadDiseaseList();
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
            downloadDiseaseTask = null;
            showProgress(false);

            if (!success) {
                if (numberOfRetries < 4) {
                    downLoadDiseaseList();
                    numberOfRetries += 1;
                } else {
                    showEmptyList(true);
                    MsgUtils.displayToast(context, R.string.error_generic);
                }
            }
        }

        @Override
        protected void onCancelled() {
            downloadDiseaseTask = null;
            numberOfRetries = -1;
            showProgress(false);
        }
    }
}