package com.vibe.org.vibe.Important;


import com.vibe.org.vibe.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vibe.org.vibe.Common.EditConnectionChecker;
import com.vibe.org.vibe.Common.EditMySingleton;
import com.vibe.org.vibe.Common.EditSharedPreferenceData;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditChangeVibeFragment extends Fragment {

    //URL_VIBE URL to send the selected vibe
    private static final String URL_VIBE= "http://192.168.43.14:3000/place/changevibe/";


    private Spinner mSpinner;
    private Button changeVibe;

    public EditChangeVibeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.edit_fragment_change_vibe, container, false);

        mSpinner= (Spinner) view.findViewById(R.id.spinner_edit);
        changeVibe= (Button) view.findViewById(R.id.ChangeVibe_changevibe_edit);

        loadData();

        changeVibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (EditConnectionChecker.isConnected(getContext())) {
                try{
                    selectedVibe(URL_VIBE, mSpinner.getSelectedItem().toString());
                }catch (Exception e){
                    Log.e("error", e.toString());
                }
            }
            }
        });
        return view;

    }

    private void loadData(){
        String[] moodArray= {"happy", "sad", "romantic", "sick", "bored", "hungry"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, moodArray);
        mSpinner.setAdapter(adapter);
    }

    private void selectedVibe(String URL, final String selectedVibe){
        StringRequest stringRequest= new StringRequest(URL, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Intent intent= new Intent(getContext(), EditProfileActivity.class);
                    startActivity(intent);
                }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap= new HashMap<>();
                hashMap.put("vibe", selectedVibe);
                return hashMap;
            }
        };
        EditMySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}