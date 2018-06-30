package com.vibe.org.vibe.Important;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vibe.org.vibe.HomeController;
import com.vibe.org.vibe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.vibe.org.vibe.Common.EditProfileModel;
import com.vibe.org.vibe.Common.EditProfileRecycler;
import com.vibe.org.vibe.Common.EditRecyclerItemClickListener;
import com.vibe.org.vibe.Common.EditSharedPreferenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment {

    //URL UPLOAD IMAGE -- URL to upload image
    //URL DISPLAY IMAGE -- URL to display image
    private static String URL_UPLOAD_IMAGE= "http://192.168.43.14:3000/user/changeProfilePic/";
    private static String URL_GET_IMAGE = "http://192.168.43.14:3000/profilePic/user/";
    private  String TAG = "PROFILE_PIC";
    public interface EditInterface {
        void ImplementationChangePassword();
        void ImplementationChangeVibe();
    }


    EditInterface mEditInterface;
    public EditFragment() {
        // Required empty public constructor
    }

    private int IMG_REQUEST=6;
    private Bitmap mBitmap;

    RecyclerView mRecyclerView;
    FloatingActionButton mFloatingActionButton;
    private ImageView displayImage;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEditInterface = (EditInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.edit_fragment_mood_edit, container, false);


        displayImage= (ImageView) view.findViewById(R.id.image_edit);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_edit);
        mFloatingActionButton= (FloatingActionButton) view.findViewById(R.id.fab_edit);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);



        mRecyclerView.addOnItemTouchListener(new EditRecyclerItemClickListener(getContext(), mRecyclerView,
                new EditRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView key= (TextView) view.findViewById(R.id.key_recycler_edit);

                if (key.getText().toString().contains("Change Password")){
                    mEditInterface.ImplementationChangePassword();
                }else if (key.getText().toString().contains("Change Vibe")){
                    mEditInterface.ImplementationChangeVibe();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }) );

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            selectImage();
            }
        });
        loadImage();
        loadData(getContext());
        Log.d("fragment", "edit");
        return view;
    }

    public void loadData(Context context){
        List<EditProfileModel> editProfileModels= new ArrayList<>();
        editProfileModels.add(new EditProfileModel("Change Password", "******"));
        EditProfileRecycler editProfileRecycler = new EditProfileRecycler(editProfileModels,context);
        mRecyclerView.setAdapter(editProfileRecycler);
    }


    public void addImage(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        String user = sharedPreferences.getString("user","");
        String id= "";
        try {
            JSONObject oUser = new JSONObject(user);
            id = oUser.getString("userId");
            Log.d("PROFILE_PIC","JSON SUccessfule id :"+ id);
        } catch (JSONException e) {
            Log.d("PROFILE_PIC","JSON FAILED");
        }

        String url = URL_UPLOAD_IMAGE + id;
        Log.d(TAG, "addImage: url "+ url);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        Toast.makeText(getActivity(),"Successfully Changed Profile Picture",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), HomeController.class);
                        startActivity(intent);
                        getActivity().finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(),"Sorry, Something Went Wrong",Toast.LENGTH_LONG).show();
                    Log.d("network error", error.toString());}
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("pid", String.valueOf(EditSharedPreferenceData.getUserId()));
                    //Optional
                    hashMap.put("name", "imageName");
                    hashMap.put("image", imageToString(mBitmap));
                    return hashMap;
                }
            };
            //send the request
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(stringRequest);
        }catch (Exception e){

        }
    }

    private void selectImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes=  byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    private void loadImage(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        String user = sharedPreferences.getString("user","");
        String id= "";
        try {
            JSONObject oUser = new JSONObject(user);
            id = oUser.getString("userId");
            Log.d("PROFILE_PIC","JSON SUccessfule id :"+ id);
        } catch (JSONException e) {
            Log.d("PROFILE_PIC","JSON FAILED");
        }
        String url = URL_GET_IMAGE + id;
        ImageRequest imageRequest= new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                displayImage.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(imageRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path= data.getData();
            try {
                mBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                Log.d("PROFILE_PIC","IMAGE lOAD success");
            }catch (IOException e) {
                Log.d("PROFILE_PIC","IMAGE lOAD Failed");
            }
            addImage();
        }
    }


}
