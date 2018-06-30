package com.vibe.org.vibe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ori on 2018-06-18.
 */

public class ListAdapterPlaceProfile extends RecyclerView.Adapter {
    Context activity;
    JSONArray comments;

    public  ListAdapterPlaceProfile(Context context){
        activity = context;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_for_places, parent,false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }


    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView TextView1;
        private TextView TextView2;

        public ListViewHolder(View itemView) {
            super(itemView);
            TextView1 = (TextView) itemView.findViewById(R.id.TextView1);
            TextView2 = (TextView) itemView.findViewById(R.id.TextView2);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            try {
                String username = placeProfileUsers.sharedComments.getJSONArray(position).getString(0);
                String content = placeProfileUsers.sharedComments.getJSONArray(position).getString(1);
                TextView1.setText(username);
                TextView2.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {

        }

    }
    @Override
    public int getItemCount(){
        JSONArray suggestedPlaces = new JSONArray();
        JSONObject place = new JSONObject();
        JSONArray comment = new JSONArray();
        SharedPreferences shared = activity.getSharedPreferences("Users", Activity.MODE_PRIVATE);
        int selected = shared.getInt("selected", 0);
        try {
            suggestedPlaces = new JSONArray(shared.getString("suggestedPlaces", ""));
            place = suggestedPlaces.getJSONObject(selected);
            comment = place.getJSONArray("comments");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comment.length();
    }
}


