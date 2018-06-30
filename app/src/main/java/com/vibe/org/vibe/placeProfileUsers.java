package com.vibe.org.vibe;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */
public class placeProfileUsers extends Fragment {
    Context myContext;
    private RecyclerView RecyclerView;
    private RequestQueue sendRate, sendComment;
    private StringRequest stringRequest;
    private float prevRate;
    private float rate;
    String wholeComment;
    private String urlForRate = "http://192.168.43.14:3000/place/rate/";
    private String urlForComment = "http://192.168.43.14:3000/place/comment/";
    private String urlForProPic = "http://192.168.43.14:3000/profilePic/place/";
    private RatingBar ratingBar,ratingBar2;
    private Button rateBtn;
    public  static  JSONArray sharedComments;
    String TAG = "SUGGESTION-PROFILE";
    private String token;
    private JSONObject user;
    private JSONObject place;

    public placeProfileUsers() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_profile_users, container, false);
        myContext = this.getActivity();

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar2 = (RatingBar) view.findViewById(R.id.ratingBar2);
        final LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);


        rateBtn = (Button) view.findViewById(R.id.sendRateBtn);
        LayerDrawable starsAction = (LayerDrawable) ratingBar.getProgressDrawable();
        starsAction.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        starsAction.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        starsAction.getDrawable(1).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        TextView viewMap = (TextView) view.findViewById(R.id.viewMap);
        final Button commentBtn = (Button) view.findViewById(R.id.comment);


        JSONArray suggestedPlaces ;
        try {
            SharedPreferences shared = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
            user  = new JSONObject(shared.getString("user",""));
            place = getSharedPreference();
            token = shared.getString("token","");
            //sharedComments = place.getJSONArray("comments");
        } catch (JSONException e) {
            Log.d(TAG, "onCreateView: Error JSON PARSE IN LINE 149");
        }

        //recycler view for the comments
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        final ListAdapterPlaceProfile ListAdapter = new ListAdapterPlaceProfile(getActivity());
        recyclerView.setAdapter(ListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //parse the json array and the json object/user object
        // using user and place object set the profile page for the user to see
        try {

            final TextView placeName = (TextView) getActivity().findViewById(R.id.placeName) ;
            final TextView placeVibe = (TextView) getActivity().findViewById(R.id.vibe);
            placeName.setText(place.getString("userName"));
            placeVibe.setText(place.getString("vibe"));
            ratingBar.setRating(place.getInt("rating"));
            //get the profile Pic
            getProfilePic();

        } catch (JSONException e) {
            Log.d(TAG, "onCreateView: Error JSON PARSE IN LINE 171");
        }



        //
//        final JSONObject finalPlace = place;//copy place object to another final object
//        float rating = ratingBar.getRating();



        rateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: RateBtn");
                handleRate();
            }
        });
        //add comment
        commentBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                handleComment();
            }
        });


        viewMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                handelViewOnMap();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleComment() {
        Log.d(TAG, "onClick: CLIcked");
        final EditText editText = (EditText) getActivity().findViewById(R.id.editText2);
        String commentStr = editText.getText().toString();
        final Button commentBtn = (Button) getActivity().findViewById(R.id.comment);
        try {
            wholeComment = "['"+user.getString("userName")+"','" + commentStr +"']";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = null;
        try {

            place = getSharedPreference();
            url = urlForComment + place.getString("_id")+"/?token="+token;
            Log.d(TAG, "onClick: URL for place : "+url );
        } catch (JSONException e) {
            Log.d(TAG, "onClick: JSONEXCEPTIOn 229");
        }
        HashMap<String,String> args = new HashMap<String,String>();
        JSONArray jcomment = null;
        try {
            jcomment = new JSONArray(wholeComment);
            args.put("userName", jcomment.getString(0));
            args.put("comment",jcomment.getString(1));
        } catch (JSONException e) {
            Log.d(TAG, "getParams: JSONEXECption 259");
            e.printStackTrace();
        }
        ServerHelper.sendPost(getActivity(), url, new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                try {
                    JSONArray arr = new JSONArray(wholeComment);
                    sharedComments.put(new String[]{arr.getString(0), arr.getString(1)});
                    editText.setText("");
                    editText.setActivated(false);
                    commentBtn.setEnabled(true);
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    placeProfileUsers viewPlaceProfileFragment = new placeProfileUsers();
                    fragmentTransaction.replace(android.R.id.content,viewPlaceProfileFragment);
                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    Log.d(TAG, "onClick: JSONEXECPTION ");
                    e.printStackTrace();
                }
                return null;
            }
        },args);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleRate() {

        final double prevRating;
        try {

            JSONObject placeObj = getSharedPreference();
            prevRating = placeObj.getDouble("rating");
            Log.d(TAG, "onClick: PlaceObject : "+ placeObj.toString());
            Log.d(TAG ,placeObj.getString("_id"));
            String url = urlForRate+placeObj.getString("_id")+"/?token="+token;
            HashMap<String,String> map = new HashMap<>();
            float newRating = ((float) prevRating + ratingBar2.getRating() ) /2;
            map.put("rating", String.valueOf(newRating));
            // ServerHelper.sendPost(getActivity(), LOGIN_URL, new Function<String, Void>() {
            ServerHelper.sendPost(getActivity(), url, new Function<String, Void>() {
                @Override
                public Void apply(String s) {
                    Toast.makeText(getActivity(), "Successfully rated place",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), HomeController.class);
                    startActivity(intent);
                    getActivity().finish();
                    return null;
                }
            },map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handelViewOnMap() {

        Intent intent = new Intent(myContext, MapsActivity.class);
        Bundle bundle = new Bundle();
        int latitude = 0;
        int longitude = 0;
        JSONObject splace = null;
        try {
            splace = getSharedPreference();
            JSONArray slocation = splace.getJSONArray("location");
            latitude = slocation.getInt(0);
            longitude = slocation.getInt(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.putInt("latitude", latitude);
        bundle.putInt("latitude", longitude);
        try {
            bundle.putString("placeName", splace.getString("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtras(bundle);
        myContext.startActivity(intent);



    }
    public JSONObject getSharedPreference() throws JSONException {
        JSONArray suggestedPlaces;
        JSONObject place;
        SharedPreferences shared = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        int selected = shared.getInt("selected", 0);
        suggestedPlaces = new JSONArray(shared.getString("suggestedPlaces", ""));
        place = suggestedPlaces.getJSONObject(selected);
        return place;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getProfilePic() {
        //profile pic for place
        final ImageView proPic = (ImageView) getActivity().findViewById(R.id.ProPic);
        try {
            //get the profile Pic
            String requestUrl;
            requestUrl = urlForProPic+place.getString("_id")  + "/?token=" + token;
            Log.d(TAG, "onCreateView: URl for pic "+ requestUrl);
            Log.d("   ",requestUrl );
            ServerHelper.getImage(getActivity(), requestUrl, new Function<Bitmap, Void>() {
                @Override
                public Void apply(Bitmap bitmap) {
                    proPic.setImageBitmap(bitmap);
                    return null;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}








