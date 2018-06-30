package com.vibe.org.vibe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.function.Function;

public class LoginFragmentActivity extends Fragment implements MainContract.mvpView,LoginView{

    EditText userName1,passWord1;
    static  TextView error;
    Button login,userRegister,placeRegister;
    String userName,password;
    private  SharedPreferences sharedPref;
    private static SharedPreferences loggedUser = null;
    private SharedPreferences.Editor sharedPrefEditor;
    private static final String LOGIN_URL="http://192.168.43.14:3000/login";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutFrag = inflater.inflate(R.layout.activity_login_fragment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Users",Activity.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        if(!token.equals("")){
            Intent intent = new Intent(getActivity(),HomeController.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
        userName1 = (EditText) layoutFrag.findViewById(R.id.username);
        passWord1 = (EditText) layoutFrag.findViewById(R.id.password);
        login=(Button) layoutFrag.findViewById(R.id.login);
        userRegister=(Button) layoutFrag.findViewById(R.id.userRegister);
        placeRegister=(Button) layoutFrag.findViewById(R.id.placeRegister);
        error=(TextView)layoutFrag.findViewById(R.id.error);
        userRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launcherNoteDetail(vibe.Page.USER_REGISTER);
            }
        });
        sharedPref = getActivity().getSharedPreferences("Users",Activity.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
        placeRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launcherNoteDetail(vibe.Page.PLACE_REGISTER);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                loginHandler();
            }
        });

        return layoutFrag;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void loginHandler(){
        if(validate()) {
            Log.d("VALIDATED", "sending json object: ");
            userName = userName1.getText().toString().trim();
            password = passWord1.getText().toString().trim();
        }
        else{
            return;
        }
        HashMap params= new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);

        ServerHelper.sendPost(getActivity(), LOGIN_URL, new Function<String, Void>() {
                    @Override
                    public Void apply(String response) {
                        onLoginResponse(response);
                        return null;
                    }
                },params
        );
    }

    //Included THis to test loginHandler, because login handler is private
    @TargetApi(Build.VERSION_CODES.N)
    public void callLoginHandler(){
        loginHandler();
    }
    private void onLoginResponse(String response) {
        try {
            JSONObject jsonResponse= new JSONObject(response);
            String token = jsonResponse.getString("token");
            sharedPrefEditor.putString("token",token);
            Log.d("DataFromServer", "token: "+token);

            JSONObject user = jsonResponse.getJSONObject("user");
            Log.d("DataFromServer", "user "+user.toString());
            sharedPrefEditor.putString("user",user.toString());
            sharedPrefEditor.apply();
            Intent intent = new Intent(getActivity(),HomeController.class);
            startActivity(intent);
            getActivity().finish();

        } catch (JSONException e) {
            //Something went wrong sorry, try again
            String error="Sorry something went wrong, try again.";
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static void  showError(){
        error.setText("invalid username password combination");
    }
    public void onActivityCreated(Bundle savedStateInstance){
        super.onActivityCreated(savedStateInstance);

    }
    private void launcherNoteDetail(vibe.Page p){
        Intent intent=new Intent(getActivity(),RegistrationActivity.class);
        switch (p){
            case USER_REGISTER:
                intent.putExtra(vibe.PAGE_VIEWER,vibe.Page.USER_REGISTER);
                break;
            case PLACE_REGISTER:
                intent.putExtra(vibe.PAGE_VIEWER,vibe.Page.PLACE_REGISTER);
                break;
            case RESET_PASSWORD:
                intent.putExtra(vibe.PAGE_VIEWER,vibe.Page.RESET_PASSWORD);
                break;

        }
        startActivity(intent);
    }


    public  boolean validate(){
        boolean valid =true;
        if(userName1.getText().length()==0){
            userName1.setError("please Enter name");
            return false;
        }
        if(passWord1.getText().length() ==0 ){
            passWord1.setError("please Enter password");
            return false;
        }
        if(passWord1.getText().length() < 4){
            passWord1.setError("please Enter password more than 7 character");
            return false;

        }
        return valid;
    }
    @Override
    public void showUserSignUpScreen() {
        Toast.makeText(getActivity(), "USER SIGN UP PAGE SCREEN", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPlaceRegisterScreen() {
        Toast.makeText(getActivity(), "PLACE REGISTER PAGE SCREEN", Toast.LENGTH_LONG).show();

    }

    @Override
    public void resetPasswordScreen() {
        Toast.makeText(getActivity(), "RESET PASSWORD PAGE SCREEN", Toast.LENGTH_LONG).show();

    }


    @Override
    public String getValidToken(Context context) {
        UserPreferences(context);
        String token = loggedUser.getString("token",null);
        return token;
    }
    public void getValidConfirmation() {
        Toast.makeText(getActivity(), "VALID TOKEN HAVE SENT", Toast.LENGTH_LONG).show();

    }
    private static SharedPreferences UserPreferences(Context context)
    {
        if(loggedUser == null)
            loggedUser = context.getSharedPreferences("Users",0);
        return loggedUser;
    }

    @Override
    public String getUserName() {
        userName = userName1.getText().toString().trim();
        return userName;
    }
    public String getPassword() {
        password = passWord1.getText().toString().trim();
        return password;
    }


    @Override
    public void showUserNameError(int resId) {
        userName1.setError(getString(resId));
    }

    @Override
    public Boolean showUserNameSuccess(int resId) {
        userName1.setText(getString(resId));
        return true;
    }
    @Override
    public void showPasswordError(int resId) {
        passWord1.setError(getString(resId));
    }

    @Override
    public void startProfileActivity() {
        Intent intent=new Intent(getActivity(),UserRegisterFragment.class);
        new RegistrationActivity().startActivity(intent);
    }

    @Override
    public void showLoginError(int login_failed) {
        Toast.makeText(getActivity(), getString(login_failed), Toast.LENGTH_SHORT).show();

    }
    //Methods added for Testing validate method
    public void setFieldsForValidate(String userName, String password){
        userName1.setText(userName);
        passWord1.setText(password);
    }

}







