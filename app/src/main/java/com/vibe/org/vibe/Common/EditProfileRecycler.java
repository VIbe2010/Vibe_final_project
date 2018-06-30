package com.vibe.org.vibe.Common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vibe.org.vibe.R;



import java.util.List;

/**
 * Created by Aman on 10/06/2018.
 */

public class EditProfileRecycler extends RecyclerView.Adapter<EditProfileRecycler.PersonViewHolder> {

    private Context context;
    private List<EditProfileModel> mEditProfileModels;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView key, value;

        PersonViewHolder(View itemView) {
            super(itemView);
            key = (TextView)itemView.findViewById(R.id.key_recycler_edit);
            value = (TextView) itemView.findViewById(R.id.value_recycler_edit);
        }
    }


    public EditProfileRecycler(List<EditProfileModel> editProfileModels, Context context) {
        super();
        this.mEditProfileModels = editProfileModels;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.edit_recycler_mood_edit_profile, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.key.setText(mEditProfileModels.get(i).getKey());
        personViewHolder.value.setText(mEditProfileModels.get(i).getValue());
    }

    @Override
    public int getItemCount() {
        return mEditProfileModels.size();
    }

}