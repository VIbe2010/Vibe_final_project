package com.vibe.org.vibe;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegisterFragment extends Fragment  {
    private EditText username1, password1, confirmPassword1, email1;
    private  String userName,email,password,confirmPassword;
    private Button register;
    private TextView error;
    private static final String URL_USER_REGISTER="http://192.168.43.14:3000/user/register";
    Boolean isPlace;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedPrefEditor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutFrag = inflater.inflate(R.layout.fragment_user_register, container, false);
        username1 = (EditText) layoutFrag.findViewById(R.id.username);
        password1 = (EditText) layoutFrag.findViewById(R.id.password);
        email1 = (EditText) layoutFrag.findViewById(R.id.email);
        confirmPassword1 = (EditText) layoutFrag.findViewById(R.id.confirmPassword);
        error = (TextView) layoutFrag.findViewById(R.id.error);
        register = (Button) layoutFrag.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleUserRegister();
                }
        });
        return layoutFrag;

        }

    private void handleUserRegister() {
        if(!validate()){
            Toast.makeText(getActivity(),"register Failed",Toast.LENGTH_SHORT).show();
            return;
        }
        userName = username1.getText().toString().trim();
        email = email1.getText().toString().trim();
        password = password1.getText().toString().trim();
        confirmPassword = confirmPassword1.getText().toString().trim();

        isPlace=false;
        sharedPref=getActivity().getSharedPreferences("Users", Activity.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
        HashMap<String, String >params= new HashMap<>();
        params.put("userName",userName);
        params.put("email",email);
        params.put("password",password);

        ServerHelper.sendPost(getActivity(), URL_USER_REGISTER, new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Intent intent=new Intent(getActivity(),LoginActivity.class );
                startActivity(intent);
                getActivity().finish();
                return null;
            }
        },params);
    }

    public boolean validate(){
        boolean valid =true;
        if(username1.getText().length()==0 ||username1.getText().length()>=20 ){
            username1.setError("please Enter valid name");
            valid=false;
        }
        if(email1.getText().length()==0){
            email1.setError("please Enter valid Email Address");
            valid=false;
        }

        if(password1.getText().length() ==0 && password1.getText().length() < 4){
            password1.setError("please Enter valid password");
            valid=false;
        }
        if(confirmPassword1.getText().length() ==0){
            password1.setError("please Enter valid  password");
            valid=false;
        }
        if(!(confirmPassword1.getText().toString().trim().equals(password1.getText().toString().trim()))){
            confirmPassword1.setError("Passwords Don't Match");
            valid=false;

        }


        return valid;
    }
}