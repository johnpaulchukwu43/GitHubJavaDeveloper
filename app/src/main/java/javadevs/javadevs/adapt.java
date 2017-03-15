package javadevs.javadevs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by CHUKWU JOHNPAUL on 09/03/17.
 In this Class we create our own Custom Recycler View Adapter
 */

public class adapt extends RecyclerView.Adapter<adapt.ViewHolder> {
    private ArrayList<developer> dev;// generic list that only accepts type of developer class
    private Context ctx;//Context 

    public adapt( ArrayList<developer>dev,Context ctx) {
        this.ctx = ctx;
        this.dev = dev;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.developer_item,parent,false);
        return new adapt.ViewHolder(v,ctx,dev);
        
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final developer myDev = dev.get(position);
        //Here we bind the values from the developer class model to their respective views
        holder.usernameTxt.setText(myDev.getUsername());
        //we make use of Picasso Library to easily load our images
        Picasso.with(ctx)
                .load(myDev.getImageUrl())

                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dev.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView usernameTxt;
        public ImageView imageView;
        ArrayList<developer> myDevelopers = new ArrayList<developer>();
        Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<developer>myDevelopers) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.myDevelopers = myDevelopers;
            this.ctx = ctx;
            usernameTxt = (TextView)itemView.findViewById(R.id.devUsername);
            imageView = (ImageView)itemView.findViewById(R.id.devImage);

        }

        @Override
        public void onClick(View v) {
            //On click of any of the items this method is called 
            int position = getAdapterPosition();
            developer theD = this.myDevelopers.get(position);//get the postion of the item clicked

            Intent intent = new Intent(this.ctx, MoreInfo.class);//Create an explict Intent to our MoreInfoClass
            //Pass the username,image and profile Url to the Intent
           intent.putExtra("profile_url",theD.getProfileUrl());
            intent.putExtra("image",theD.getImageUrl());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.ctx.startActivity(intent);//start the Intent

        }
    }

}
