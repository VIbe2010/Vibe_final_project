package com.vibe.org.vibe;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceRegisterFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private EditText placeName1, password1, confirmPassword1, place_email1, locationGet;
    private Button place_register, location;
    private Boolean isPlace;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;
    private TextView error;
    View layoutFrag;
    Spinner spinner;
    private double[] slocation = new double[2];
    private JSONArray result;
    private ArrayList<String> vibesArray;
    private static final String REGISTER_URL="http://192.168.43.14:3000/place/register";


    private static final String VIBE_URL = "http://localhost:8080/place/vibe";

    public PlaceRegisterFragment() {
        // Required empty public constructor
    }

    String vibe= "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutFrag = inflater.inflate(R.layout.fragment_place_register, container, false);
        placeName1 = (EditText) layoutFrag.findViewById(R.id.place_name);
        password1 = (EditText) layoutFrag.findViewById(R.id.password);
        place_email1 = (EditText) layoutFrag.findViewById(R.id.place_email);
        confirmPassword1 = (EditText) layoutFrag.findViewById(R.id.confirmPassword);
        locationGet = (EditText) layoutFrag.findViewById(R.id.locationGet);
        place_register = (Button) layoutFrag.findViewById(R.id.place_register);
        error = (TextView) layoutFrag.findViewById(R.id.error);
        location = (Button) layoutFrag.findViewById(R.id.location);

        //generateData();
        spinner = (Spinner) layoutFrag.findViewById(R.id.spinner);
        vibesArray = new ArrayList<String>();
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> mood = new ArrayList<>();
        mood.add("happy");
        mood.add("sad");
        mood.add("romantic");
        mood.add("sick");
        mood.add("bored");
        mood.add("hungry");
        locationGet.setEnabled(false);
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mood));
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                }
        });
        place_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
        return layoutFrag;
    }

    private void handleRegister() {
        if(!validate()){
            Toast.makeText(getActivity(),"register Failed",Toast.LENGTH_SHORT).show();
            return;
        }
        String placeName = placeName1.getText().toString().trim();
        String placeEmail = place_email1.getText().toString().trim();
        String password = password1.getText().toString().trim();
        final HashMap <String , String> params= new HashMap<>();
        params.put("userName",placeName);
        params.put("email",placeEmail);
        params.put("vibe",vibe);
        JSONArray loc = null;
        try {
            loc = new JSONArray("["+slocation[0]+","+slocation[1]+"]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("location", loc.toString() );
        params.put("password",password);

        ServerHelper.sendPost(getActivity(), REGISTER_URL, new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                //After this entering to the login page
                Intent intent=new Intent(getActivity(),LoginActivity.class );
                startActivity(intent);
                return null;
            }},params);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//                getLocation();
        Log.i("error", "button clicked");
        locationGet.setHint("Getting location...");
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d(" Incoming Message :  ", "Latitude : Longitude "
                        + latitude + " : " + longitude);
//                        Toast.makeText(getActivity(), "latitude" + latitude +":" + "longitude" + longitude, Toast.LENGTH_SHORT).show();
                locationGet.setHint(latitude + ":" + longitude);
                slocation[0] = latitude;
                slocation[1] = longitude;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }
    public boolean validate() {
        boolean valid = true;
        if (placeName1.getText().length() == 0 || placeName1.getText().length() >= 20) {
            placeName1.setError("please Enter valid name");
            valid = false;
        }
        if (place_email1.getText().length() == 0) {
            place_email1.setError("please Enter valid Email Address");
            valid = false;
        }
        if(vibe.length() ==0){
            valid = false;
        }
        if(((String)locationGet.getHint()).equalsIgnoreCase("Getting location...")){
            locationGet.setError("please Enter your location");
            valid = false;
        }

        if (password1.getText().length() == 0 && password1.getText().length() < 4) {
            password1.setError("please Enter valid password");
            valid = false;
        }
        if (confirmPassword1.getText().length() == 0) {
            password1.setError("please Enter valid  password");
            valid = false;
        }
        if (!(confirmPassword1.getText().toString().trim().equals(password1.getText().toString().trim()))) {
            confirmPassword1.setError("please Enter the confirm password correctly");
            valid = false;

        }


        return valid;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vibe =parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
