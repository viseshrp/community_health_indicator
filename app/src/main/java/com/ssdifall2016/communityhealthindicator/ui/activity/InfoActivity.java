package com.ssdifall2016.communityhealthindicator.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.models.Disease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    ArrayList<Disease> diseaseArrayList;

    String countyOrDisease;

    BarChart barChart;

    GoogleMap googleMap;

    Spinner selectionSpinner;

    TextView txtViewheading;

    ArrayList<Disease> selectedArrayList;
    com.google.android.gms.maps.MapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        countyOrDisease = "count";

        txtViewheading = (TextView) findViewById(R.id.textViewHeading);
        selectionSpinner = (Spinner) findViewById(R.id.spinner);
        barChart = (BarChart) findViewById(R.id.barChartUI);


//        if(googleMap!=null)
//        {
//            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
//
//// adding marker
//            googleMap.addMarker(marker);
//
//
//
//
//
//            Log.d("Marker","Added marker");
//        }


        if (countyOrDisease.equalsIgnoreCase("county")) {

            String url = "http://172.73.154.11:8080/countyname/Albany/";


            new CountyDetailsDownload().execute(url);

        } else {
            String url = "http://172.73.154.11:8080/disease/1/";


            new CountyDetailsDownload().execute(url);
        }


    }

    class CountyDetailsDownload extends AsyncTask<String, Void, ArrayList<Disease>> {

        @Override
        protected ArrayList<Disease> doInBackground(String... params) {
            ArrayList<Disease> diseasesForCounty = new ArrayList<>();
            HttpURLConnection connection;
            URL url = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = reader.readLine();
                    StringBuilder sb = new StringBuilder();
                    while (line != null) {
                        sb.append(line);
                        line = reader.readLine();

                    }

                    return parseJson(sb.toString());


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {


            }


            return diseasesForCounty;
        }

        @Override
        protected void onPostExecute(ArrayList<Disease> diseases) {
            super.onPostExecute(diseases);


            Log.d("DiseasesForCounty", diseases.toString());

            diseaseArrayList = diseases;

            if (countyOrDisease.equalsIgnoreCase("county")) {
                selectedArrayList = new ArrayList<>();

                for (int i = 0; i < diseaseArrayList.size(); i++) {

                    if (selectedArrayList.size() >= 5) {
                        break;
                    } else {
                        selectedArrayList.add(diseaseArrayList.get(i));

                    }

                }

                setBarChartData(selectedArrayList);
                try {
                    // Loading map
                    initilizeMap();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                selectedArrayList = new ArrayList<>();

                for (int i = 0; i < diseaseArrayList.size(); i++) {

                    if (selectedArrayList.size() >= 5) {
                        break;
                    } else {
                        selectedArrayList.add(diseaseArrayList.get(i));

                    }

                }

                setBarChartData(selectedArrayList);
                try {
                    // Loading map
                    initilizeMap();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public ArrayList<Disease> parseJson(String ln) throws JSONException {
        ArrayList<Disease> diseaseArrayList = new ArrayList<>();


        JSONObject root = new JSONObject(ln);

        //JSONObject resultObject=root.getJSONObject("result");

        JSONArray resultArray = root.getJSONArray("result");

        for (int i = 0; i < resultArray.length(); i++) {
            if (diseaseArrayList.size() == 10) {
                return diseaseArrayList;
            }
            Disease disease = new Disease();
            JSONObject listObject = resultArray.getJSONObject(i);
            disease.setLocation(listObject.getString("location"));
            disease.setMappingDist(listObject.getString("mappingDist"));
            disease.setDiseaseDescription(listObject.getString("diseaseDescription").trim());
            disease.setPercent(listObject.getString("percent"));
            disease.setCountyName(listObject.getString("countyName"));
            disease.setMeasure(listObject.getString("measure"));
            disease.setAvgNumDen(listObject.getString("avgNumDen"));
            disease.setEventCount(listObject.getString("eventCount"));
            disease.setIndDescription(listObject.getString("indDescription"));

            if (!diseaseArrayList.contains(disease))
                diseaseArrayList.add(disease);


        }


        return diseaseArrayList;
    }

    private void initilizeMap() {
        if (googleMap == null) {
            mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(
                    R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap1) {
                    googleMap = googleMap1;
                    if (countyOrDisease.equalsIgnoreCase("county")) {
                        Disease loc = selectedArrayList.get(0);
                        double latitude;
                        double longitude;
                        String[] locationSplit = loc.getLocation().split(":");
                        String latitudeStr = locationSplit[0].substring(1);
                        String longitudeStr = locationSplit[1].substring(0, locationSplit[1].length() - 2);
                        Log.d("DemoLocation", latitudeStr + " " + longitudeStr);


                        latitude = Double.valueOf(latitudeStr);
                        longitude = Double.valueOf(longitudeStr);

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");

                        //adding marker

                        if (Integer.parseInt(loc.getMappingDist()) == 1) {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                        } else if (Integer.parseInt(loc.getMappingDist()) == 2) {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                        } else {
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                        }


                        googleMap.addMarker(marker);

                        //ROSE color icon

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 5.0f));

                        Log.d("Marker", "Added marker");
                    } else {
                        for (Disease loc : selectedArrayList) {

                            double latitude;
                            double longitude;
                            String[] locationSplit = loc.getLocation().split(":");
                            String latitudeStr = locationSplit[0].substring(1);
                            String longitudeStr = locationSplit[1].substring(0, locationSplit[1].length() - 2);
                            Log.d("DemoLocation", latitudeStr + " " + longitudeStr);


                            latitude = Double.valueOf(latitudeStr);
                            longitude = Double.valueOf(longitudeStr);

                            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");

                            //adding marker

                            if (Integer.parseInt(loc.getMappingDist()) == 1) {
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                            } else if (Integer.parseInt(loc.getMappingDist()) == 2) {
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                            } else {
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                            }


                            googleMap.addMarker(marker);

                            //ROSE color icon

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 5.0f));

                            Log.d("Marker", "Added marker");
                        }


                    }


                }
            });


            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void setBarChartData(ArrayList<Disease> selectedDiseases) {

        if (selectedDiseases.size() == 5) {
            ArrayList<BarEntry> barEntries = new ArrayList<>();

            for (int i = 0; i < selectedDiseases.size(); i++) {

                barEntries.add(new BarEntry(Float.parseFloat(selectedDiseases.get(i).getMappingDist()), i));
            }


            BarDataSet dataSet = new BarDataSet(barEntries, "BarEntries");
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);
            colors.add(Color.GREEN);
            colors.add(Color.CYAN);
            colors.add(Color.GRAY);


            dataSet.setColors(colors);

            dataSet.setBarSpacePercent(10f);


            ArrayList<String> counties = new ArrayList<>();
            if (countyOrDisease.equals("county")) {
                for (int i = 0; i < selectedDiseases.size(); i++) {

                    String disease = selectedDiseases.get(i).getDiseaseDescription().split(" ")[0];


                    counties.add(disease);
                }


            } else {

                counties.add(selectedDiseases.get(0).getCountyName());
                counties.add(selectedDiseases.get(1).getCountyName());
                counties.add(selectedDiseases.get(2).getCountyName());
                counties.add(selectedDiseases.get(3).getCountyName());
                counties.add(selectedDiseases.get(4).getCountyName());
            }


            BarData data = new BarData(counties, dataSet);
            data.setGroupSpace(1.0f);

            // BarData data1=new BarData()

//        data.set

            //BarData data = new BarData(dataSet);

            barChart.setData(data);

            barChart.getXAxis().setDrawLabels(false);

            barChart.setHorizontalScrollBarEnabled(true);

            Legend l = barChart.getLegend();
//        l.setComputedLabels(counties);
            l.setCustom(colors, counties);
//        l.setDirection(Legend.LegendDirection.);
            l.setTextSize(5.0f);


            barChart.setDoubleTapToZoomEnabled(false);


        }

    }

}
