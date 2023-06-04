package com.example.projects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public Adapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.card1,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        User user = userArrayList.get(position);

        holder.ID.setText(user.ID);
        holder.ChickenCount.setText(user.ChickenCount);
        holder.EggCount.setText(user.EggCount);
        holder.Week.setText(user.Week);
        holder.Time.setText(user.Time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adapt = new Intent(context,HomePage.class);

                adapt.putExtra("Uid",userArrayList.get(position).getUid());
                adapt.putExtra("id",userArrayList.get(position).getID());
                adapt.putExtra("chickenCount",userArrayList.get(position).getChickenCount());
                adapt.putExtra("eggCount",userArrayList.get(position).getEggCount());
                adapt.putExtra("week",userArrayList.get(position).getWeek());

                context.startActivity(adapt);
            }


        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ID, ChickenCount,EggCount,Week,Time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ID=itemView.findViewById(R.id.id);
            ChickenCount=itemView.findViewById(R.id.ch1);
            EggCount=itemView.findViewById(R.id.eg1);
            Week=itemView.findViewById(R.id.week);
            Time=itemView.findViewById(R.id.time);
        }
    }
}
