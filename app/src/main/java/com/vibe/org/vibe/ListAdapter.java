package com.vibe.org.vibe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Biya on 6/16/2018.
 */

public class ListAdapter extends RecyclerView.Adapter {


    //    List<Model> modelList;
//    ListAdapter(Context context, List<Model> modelList){
//        this.modelList=modelList;
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_indiv, parent,false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }


    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ImageView;
        private ImageView ImageView2;
        private TextView TextView;

        public ListViewHolder(View itemView){
            super(itemView);
            TextView =(TextView) itemView.findViewById(R.id.TextView);
            ImageView = (ImageView) itemView.findViewById(R.id.ImageView);
            ImageView2 = (ImageView) itemView.findViewById(R.id.ImageView2);
            itemView.setOnClickListener(this);
        }
        public void bindView(int position){

            //TextView.setText(modelList.get(position).getPlaceName());
//            TextView.setText(OurData.title.get(position));
//            ImageView.setImageBitmap(OurData.placePicture.get(position));
//            ImageView2.setImageBitmap(OurData.viewIcon);
        }
        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
