package com.vibe.org.vibe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        createAndFragment();
    }
     private void createAndFragment(){
        Intent intent=getIntent();
        vibe.Page page=(vibe.Page) intent.getSerializableExtra(vibe.PAGE_VIEWER);

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        switch(page){

            case USER_REGISTER:
                UserRegisterFragment userRegistrationFragment=new UserRegisterFragment();
                transaction.add(R.id.registration,userRegistrationFragment,"USER_REGISTRATION_FRAGMENT");
                transaction.commit();
                break;

            case  PLACE_REGISTER:
                PlaceRegisterFragment placeRegistrationFragment=new PlaceRegisterFragment();
                transaction.add(R.id.registration,placeRegistrationFragment,"VIEW_DETAIL_FRAGMENT");
                transaction.commit();
                break;

            case  RESET_PASSWORD:
                ResetPasswordFragment resetPasswordFragment=new ResetPasswordFragment();
                transaction.add(R.id.registration,resetPasswordFragment,"ADD_NOTE_FRAGMENT");
                transaction.commit();
                break;

}
        }
}
