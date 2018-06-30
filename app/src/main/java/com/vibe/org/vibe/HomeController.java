package com.vibe.org.vibe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class HomeController extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_controller);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Users", Context.MODE_PRIVATE);
        try {
            String sharedUser = sharedPreferences.getString("user","");
            JSONObject User = new JSONObject(sharedUser);
            Boolean isPlace = User.getBoolean("isPlace");
            Log.d("LoggedIn", isPlace.toString());
            if (isPlace) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PlaceFragment placeFragment = new PlaceFragment();
                fragmentTransaction.replace(android.R.id.content, placeFragment);
                fragmentTransaction.commit();
            } else {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                IndividualFragment individualFragment = new IndividualFragment();
                fragmentTransaction.replace(android.R.id.content, individualFragment);
                fragmentTransaction.commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



};









