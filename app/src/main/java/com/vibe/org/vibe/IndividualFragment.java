package com.vibe.org.vibe;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vibe.org.vibe.Important.EditProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

//import android.support.v4.app.Fragment;


public class IndividualFragment extends Fragment {

    private final static String suggestionUrl = "http://192.168.43.14:3000/place/suggest/";
    private final static String URL_PROFILE_PLACE = "http://192.168.43.14:3000/profilePic/place/";
    private final static String URL_PROFILE_USER = "http://192.168.43.14:3000/profilePic/user/";
    int i;
    private ImageView propic_user, propic_place;
    private Button go_button, editprofile;
    private String mMood;
    private  static String TAG = "LoggedIn";
    private TextView userName;
    private RequestQueue sendMood;
    private String token;
    private JSONObject user;
    private String id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /// Recycler view
        View LayoutFragment = inflater.inflate(R.layout.fragment_individual, container, false);
        RecyclerView recyclerView = (RecyclerView) LayoutFragment.findViewById(R.id.RecyclerView);
        ListAdapter ListAdapter = new ListAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ListAdapter);
        //declaration
        userName = (TextView) LayoutFragment.findViewById(R.id.username);
        propic_user = (ImageView) LayoutFragment.findViewById(R.id.ProPic);
        propic_place = (ImageView) LayoutFragment.findViewById(R.id.ProPic);
        editprofile = (Button) LayoutFragment.findViewById(R.id.editProfile);
        go_button = (Button) LayoutFragment.findViewById(R.id.go_button);
        Spinner spin = (Spinner) LayoutFragment.findViewById(R.id.spinner);
        final ArrayList<String> mood = new ArrayList<>();
        mood.add("happy");
        mood.add("sad");
        mood.add("romantic");
        mood.add("sick");
        mood.add("bored");
        mood.add("hungry");
        //Performing action onItemSelected and onNothing selected
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = mood.get(position);
                mMood = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mood);
        spin.setAdapter(adapter);

        //sharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Context.MODE_PRIVATE);
         token = sharedPreferences.getString("token", "");
        try {
            user = new JSONObject(sharedPreferences.getString("user", ""));
            id = user.getString("userId");
            userName.setText(user.getString("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getProfilePic();
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_Profile = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(edit_Profile);
            }
        });
        // when go button is clicked
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSuggestion();
            }
        });

        return LayoutFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getSuggestion() {
        //suggestion
        sendMood = Volley.newRequestQueue(getActivity());
        String requestUrl = suggestionUrl + "?token="+token;
        HashMap<String,String> map = new HashMap<>();
        Log.d(TAG, "getParams: "+mMood);
        map.put("mood",mMood);
        ServerHelper.sendPost(getActivity(), requestUrl, new Function<String, Void>() {
            @Override
            public Void apply(String response) {
                onSuggestion(response);
                return null;
            }
        },map);
    }

    private void onSuggestion(String response) {
        SharedPreferences users = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = users.edit();
        editor.putString("suggestedPlaces", response);
        Log.d("LoggedIn", response);
        editor.commit();
        ServerHelper.placeUserNames = new ArrayList<>();
        ServerHelper.bitmapArrayList = new ArrayList<>();
        Intent SuggestionController = new Intent(getActivity(), com.vibe.org.vibe.SuggestionController.class);
        startActivity(SuggestionController);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProfilePic() {
        //profile pic
        String requestUrl = URL_PROFILE_USER + id + "/?token=" + token;
        Log.d("LoggedIn",requestUrl );
        ServerHelper.getImage(getActivity(), requestUrl, new Function<Bitmap, Void>() {
            @Override
            public Void apply(Bitmap bitmap) {
                propic_user.setImageBitmap(bitmap);
                return null;
            }
        });
    }
}


