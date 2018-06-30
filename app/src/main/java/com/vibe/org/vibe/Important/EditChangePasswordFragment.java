package com.vibe.org.vibe.Important;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vibe.org.vibe.HomeController;
import com.vibe.org.vibe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vibe.org.vibe.Common.EditConnectionChecker;
import com.vibe.org.vibe.Common.EditMySingleton;
import com.vibe.org.vibe.Common.EditSharedPreferenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditChangePasswordFragment extends Fragment {


    //response "incorrect" if the password doesn't match, otherwise it considers at correct declared at line 82
    private EditText oldPassword, newPassword;
    private Button changePassword;
    private String URL= "http://192.168.43.14:3000/user/changePassword/";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.edit_fragment_mood_change, container, false);

        oldPassword= (EditText) view.findViewById(R.id.oldpassword_edit);
        newPassword= (EditText) view.findViewById(R.id.newpassword_edit);
        changePassword=(Button) view.findViewById(R.id.changepassword_edit);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (verify(oldPassword) && verify(newPassword)){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
                    String user =  sharedPreferences.getString("user","");
                    JSONObject suser;
                    String id = "";
                    try {
                         suser = new JSONObject(user);
                         id  = suser.getString("userId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url  = URL+ id ;
                    ChangePassword(getContext(), url);
            }
        }});

        return view;
    }


    public boolean verify(EditText editText){
        if (editText.getText().toString().equals("")){
            editText.setError("Please insert password");
            return false;
        }else if (editText.getText().toString().length()<4){
            editText.setError("Password must be above 4 characters");
            return false;
        }
        return true;
    }


    public void ChangePassword(final Context context, String url){

        StringRequest stringRequest= new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(getContext(), HomeController.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Log.d("LoggedIn", "getParams: In getParams");
                HashMap<String, String> hashMap= new HashMap<>();
                hashMap.put("oldPwd", oldPassword.getText().toString());
                hashMap.put("newPwd", newPassword.getText().toString());
                return hashMap;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

}
