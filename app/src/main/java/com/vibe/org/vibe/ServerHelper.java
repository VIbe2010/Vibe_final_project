package com.vibe.org.vibe;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vibe.org.vibe.Important.EditProfileActivity;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ServerHelper {
    public static ArrayList<String> placeUserNames = new ArrayList<>();
    public static ArrayList<Double> ratings = new ArrayList<>();
    public static ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();

    public static void sendGet (final Activity activity, String url,
                                final Function<String, Void> onResponse){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse (String response) {
                onResponse.apply(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(activity, error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }
    public static void sendPost(final Activity activity, String url,
                                final Function<String , Void> onResponse, final HashMap args){
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse (String response) {
                onResponse.apply(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(activity, error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                return args;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }
    public static void getImage (final Activity activity, String url,
                                 final Function<Bitmap, Void> onResponse) {
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                onResponse.apply(bitmap);
            }
        }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        handleError(activity, error);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }
    private static  void handleError(Activity activity, VolleyError error){
        NetworkResponse response = error.networkResponse;
        if(response!=null && (response.statusCode == 401 || response.statusCode == 401) ){
            if(activity.getComponentName().toString().contains("Login")){
                LoginFragmentActivity.showError();
                return;
            }
            Toast.makeText(activity , "Must Be Logged In",Toast.LENGTH_LONG).show();
            EditProfileActivity.logout(activity);
            return;
        }
        else
            Toast.makeText(activity, "Sorry Something went wrong ",Toast.LENGTH_LONG).show();
        }
    }