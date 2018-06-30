package com.vibe.org.vibe.Important;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vibe.org.vibe.LoginActivity;
import com.vibe.org.vibe.R;


public class EditProfileActivity extends AppCompatActivity implements EditFragment.EditInterface{


    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_mood_edit_profile);

        mtoolbar= (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Edit Profile");


        EditFragment editFragment = new EditFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_edit, editFragment).commit();
    }


    @Override
    public void ImplementationChangePassword() {
        EditChangePasswordFragment editChangePasswordFragment = new EditChangePasswordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_edit, editChangePasswordFragment).commit();
    }

    @Override
    public void ImplementationChangeVibe() {
        EditChangeVibeFragment editChangeVibeFragment = new EditChangeVibeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_edit, editChangeVibeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.logout, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout){
            logout(this);
        }
        return super.onOptionsItemSelected(item);
    }
    public static void logout(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("Users", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(activity, "Logout", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

}
