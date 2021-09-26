package com.choudhary.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.neocrux.suziloader.SuziLoader;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList;

    SuziLoader loader = new SuziLoader();

    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      String filepath = modelArrayList.get(position).getSongName();

      String fileName = filepath.substring(filepath.lastIndexOf("/")+1);


      holder.name.setText(fileName);
      holder.name.setSelected(true);

         //Create it for once
        loader.with(context).load(filepath).into(holder.thumnail).type("mini").show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent in = new Intent(context,PlayerActivity.class);
             in.putExtra("VIDEO_URL", filepath);
             context.startActivity(in);
         }
     });
        holder.size.setText(modelArrayList.get(position).getFilesize());
        holder.duration.setText(modelArrayList.get(position).getDuration());

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name ,size, duration;
        ImageView thumnail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            thumnail = itemView.findViewById(R.id.imageView2);
            size = itemView.findViewById(R.id.size);
            duration = itemView.findViewById(R.id.Duration);
        }
    }
}
