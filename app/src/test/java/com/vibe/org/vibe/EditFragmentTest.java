package com.vibe.org.vibe.Important;

import com.vibe.org.vibe.Important.R;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aman on 27/06/2018.
 */
public class EditFragmentTest {
    EditFragment.EditInterface mEditInterface;

    EditChangeVibeFragment mEditChangeVibeFragment= new EditChangeVibeFragment();
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private ImageView displayImage;


    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void onAttach(Context context) throws Exception {
        mEditInterface= (EditFragment.EditInterface) context;
    }

    @Test
    public void onCreateView() throws Exception {
        mRecyclerView=mEditChangeVibeFragment.getActivity().findViewById(R.id.recycler_edit);
        mFloatingActionButton= mEditChangeVibeFragment.getActivity().findViewById(R.id.fab_edit);
        displayImage=mEditChangeVibeFragment.getActivity().findViewById(R.id.image_edit);

        assertNotNull(mRecyclerView);
        assertNotNull(mFloatingActionButton);
        assertNotNull(displayImage);
    }

    @Test
    public void loadData() throws Exception {

    }


}