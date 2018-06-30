package com.vibe.org.vibe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class SuggestedPlaces extends android.app.Fragment {


    private final static String URL_PROFILE_PLACE = "http://192.168.43.14:3000/profilePic/place/";
    private RecyclerView recyclerView;

    public SuggestedPlaces() {
        // Required empty public constructor
    }

    private  String TAG = "LoggedIn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_places, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        String suggestedPlaces = sharedPreferences.getString("suggestedPlaces","");
        final String token = sharedPreferences.getString("token","");

        parseJSONArray(suggestedPlaces);

        return view;
    }

    public void parseJSONArray(String suggestedPlaces){
        try {
            final JSONArray jsonArray = new JSONArray(suggestedPlaces);
            JSONObject place;
            String id;
            for (int i = 0; i <jsonArray.length() ; i++) {
                place =  jsonArray.getJSONObject(i);
                id = place.getString("_id");
                ServerHelper.placeUserNames.add(place.getString("userName"));
                ServerHelper.ratings.add(place.getDouble("rating"));
                getProfilePic(id, jsonArray);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getProfilePic(String id, final JSONArray jsonArray) {
        //profile pic for place
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token","");
        String url = URL_PROFILE_PLACE + id+"/?token="+ token;

        Log.d(TAG, "onCreateView: URl for pic "+ url);
        Log.d("   ",url );
        ServerHelper.getImage(getActivity(), url, new Function<Bitmap, Void>() {
            @Override
            public Void apply(Bitmap bitmap) {

                gotPlace(bitmap, jsonArray);
                return null;
            }
        });
    }

    public void gotPlace(Bitmap response, JSONArray jsonArray) {
        ServerHelper.bitmapArrayList.add(response);
        if(ServerHelper.bitmapArrayList.size() == jsonArray.length()){

            //done loading
            ListAdapterSuggestion ListAdapter = new ListAdapterSuggestion(getActivity());
            recyclerView.setAdapter(ListAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        }
    }

    private void handleError(JSONArray places) {

        Log.d(TAG, "onErrorResponse: Here");
        ServerHelper.bitmapArrayList.add(BitmapFactory.decodeResource(null, R.drawable.hilton));
        if(ServerHelper.bitmapArrayList.size() == places.length()){
            //done loading
            ListAdapterSuggestion ListAdapter = new ListAdapterSuggestion(getActivity());
            recyclerView.setAdapter(ListAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        }
        return;
    }

}
