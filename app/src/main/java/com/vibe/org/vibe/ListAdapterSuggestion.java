package com.vibe.org.vibe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.PendingIntent.getActivity;
/**
 * Created by ori on 2018-06-18.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class ListAdapterSuggestion extends RecyclerView.Adapter {
        public static AppCompatActivity activity;
        private OnItemClickListener mListener;
        private Activity mContext;
        String TAG = "Suggestion";
    public ListAdapterSuggestion(Activity context){
        mContext=context;
    }

    @NonNull
        @Override


        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).BindView(position);
        }

        @Override
        public int getItemCount() {
            return ServerHelper.placeUserNames.size();
        }


        public interface OnItemClickListener {
            void onItemClick(int position);
        }
        public void setOnItemClickListener(OnItemClickListener listener){
            mListener = listener;
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private ImageView profilePic;
            private TextView placeName;
            private RatingBar rating;
            private ImageButton viewProfileImg;
            private  TextView viewProfile;
            private ImageButton viewMapImg;
            private TextView viewMap;

            public ListViewHolder(View itemView){
                super(itemView);

                profilePic = (ImageView)itemView.findViewById(R.id.profile_pic);
                placeName = (TextView)itemView.findViewById(R.id.place_name);
                rating = (RatingBar)itemView.findViewById(R.id.rating);
                viewProfileImg = (ImageButton)itemView.findViewById(R.id.viewProfile_img);
                viewMapImg = (ImageButton)itemView.findViewById(R.id.viewMap_img);
                viewMap = (TextView)itemView.findViewById(R.id.viewMap);
                itemView.setOnClickListener(this);

            }

            public void BindView(final int position){
                final int selected = position;
                profilePic.setImageBitmap(ServerHelper.bitmapArrayList.get(position));
                placeName.setText(ServerHelper.placeUserNames.get(position));
                rating.setRating(ServerHelper.ratings.get(position).floatValue());
                Log.d(TAG, "BindView: Position: "+ position);
                viewProfileImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToprofile(selected);
                    }
                });
                viewMapImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToMap(selected);
                    }
                });
            }

            private void goToMap(int selected) {
                SharedPreferences shared = activity.getSharedPreferences("Users", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putInt("selected",selected);
                Intent intent = new Intent(mContext, MapsActivity.class);
                Bundle bundle = new Bundle();
                JSONArray suggestedPlaces;
                int latitude = 0;
                int longitude = 0;
                JSONObject splace = null;
                try {
                    suggestedPlaces = new JSONArray(shared.getString("suggestedPlaces", ""));
                    Log.d(TAG, "onClick: SUggested Places : " + suggestedPlaces.toString());
                    splace = suggestedPlaces.getJSONObject(selected);
                    JSONArray slocation = splace.getJSONArray("location");
                    Log.d(TAG, "onClick: slocation:"+slocation.toString());

                    latitude = slocation.getInt(0);
                    longitude = slocation.getInt(1);
                    Log.d(TAG, "onClick: lat : long - "+latitude+","+longitude);
                } catch (JSONException e) {
                    Log.d(TAG, "onClick: JSON PARSE ERROR");
                }
                bundle.putInt("latitude", latitude);
                bundle.putInt("latitude", longitude);
                try {
                    bundle.putString("placeName", splace.getString("userName"));
                } catch (JSONException e) {
                    Log.d(TAG, "onClick: JSON PARSE ERROR placename");
                }

                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }

            private void goToprofile(int selected) {
                SharedPreferences shared = activity.getSharedPreferences("Users", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putInt("selected",selected);
                editor.apply();
                android.app.FragmentManager fragmentManager = mContext.getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                placeProfileUsers viewPlaceProfileFragment = new placeProfileUsers();
                fragmentTransaction.replace(android.R.id.content,viewPlaceProfileFragment);
                fragmentTransaction.commit();
            }


            @Override
            public void onClick(View view) {
                //this.getP
            }




        }
    }




