package com.vibe.org.vibe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import static com.vibe.org.vibe.R.*;

/**
 * Created by Hena on 6/10/2018.
 */

public class SplashFragment extends Fragment {
    LinearLayout linear1,linear2;
    Button subscribe;
    Animation animate,anim2;
    private static int SPLASH_TIME=2000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutFrag = inflater.inflate(R.layout.fragment_splash, container, false);
        linear1 = (LinearLayout) layoutFrag.findViewById(id.linear1);
        linear2 = (LinearLayout) layoutFrag.findViewById(id.linear2);
        animate = AnimationUtils.loadAnimation(getActivity(), anim.animate);
        anim2 = AnimationUtils.loadAnimation(getActivity(), anim.anim2);
        linear1.setAnimation(animate);
        linear2.setAnimation(anim2);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run () {
                try {
                    // sleep(3000);
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        },SPLASH_TIME);

        return layoutFrag;

    }
    public void onActivityCreated(Bundle savedStateInstance){
        super.onActivityCreated(savedStateInstance);
    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

    }



}
