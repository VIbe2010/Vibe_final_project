package com.vibe.org.vibe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SuggestionController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_suggestion_controller);
            ListAdapterSuggestion.activity = this;

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SuggestedPlaces suggestedPlaces = new SuggestedPlaces();
            fragmentTransaction.replace(R.id.placeholder, suggestedPlaces);
            fragmentTransaction.commit();

        }
    }


