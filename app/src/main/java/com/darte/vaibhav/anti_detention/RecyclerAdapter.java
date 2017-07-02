package com.darte.vaibhav.anti_detention;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VAIBHAV on 14-08-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {


    ArrayList<attendence> arrayList= new ArrayList<>();
    RecyclerAdapter(ArrayList<attendence> arrayList)
    {
        this.arrayList = arrayList;

    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final attendence attendence = arrayList.get(position);
        holder.name.setText(attendence.getSubject());
        final int[] atta = {attendence.getAbsent()};
        final int[] attp = {attendence.getPresent()};
        final float[][] total = {{attp[0] + atta[0]}};
        final float[] perc = {(attp[0] / total[0][0]) * 100};


        if(total[0][0] ==0){

            holder.percenttext.setText("0%");
        }
        else{
            holder.percenttext.setText(String.format("%.2f", perc[0])+"%");

        }

        holder.currentpresent.setText("Present: " + Integer.toString(attp[0]));
        holder.currentabsent.setText("Absent: " + Integer.toString(atta[0]));





        holder.c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "you clicked " + attendence.getSubject(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(),marks.class);
                intent.putExtra("subis",attendence.getSubject());
                v.getContext().startActivity(intent);


            }
        });


        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rview", "clicked P+ :" + attendence.getSubject());
                DatabaseHelper mydb=new DatabaseHelper(v.getContext());
                mydb.increasepres(attendence.getSubject());
                holder.currentpresent.setText("Present: " + Integer.toString(++attp[0]));
                float[] total = {attp[0] + atta[0]};
                perc[0] =(attp[0] / total[0])*100;
                holder.percenttext.setText(String.format("%.2f", perc[0])+"%");



            }
        });
        holder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rview", "clicked P- :" + attendence.getSubject());
                DatabaseHelper mydb=new DatabaseHelper(v.getContext());
                mydb.decreasepres(attendence.getSubject());
                holder.currentpresent.setText("Present: " + Integer.toString(--attp[0]));
                float[] total = {attp[0] + atta[0]};
                perc[0] =(attp[0] / total[0])*100;
                holder.percenttext.setText(String.format("%.2f", perc[0])+"%");

            }
        });
        holder.b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rview","clicked A+ :"+attendence.getSubject());
                DatabaseHelper mydb=new DatabaseHelper(v.getContext());
                mydb.increaseabs(attendence.getSubject());
                holder.currentabsent.setText("Absent: " + Integer.toString(++atta[0]));
                float[] total = {attp[0] + atta[0]};
                perc[0] =(attp[0] / total[0])*100;
                holder.percenttext.setText(String.format("%.2f", perc[0])+"%");

            }
        });
        holder.b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("rview","clicked A- :"+attendence.getSubject());
                DatabaseHelper mydb=new DatabaseHelper(v.getContext());
                mydb.decreaseabs(attendence.getSubject());
                holder.currentabsent.setText("Absent: " + Integer.toString(--atta[0]));
                float[] total = {attp[0] + atta[0]};
                perc[0] =(attp[0] / total[0])*100;
                holder.percenttext.setText(String.format("%.2f", perc[0])+"%");

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,percenttext,currentpresent,currentabsent;

        Button b1,b2,b3,b4;
        CardView c1;
        RecyclerViewHolder(View view)
        {
            super(view);
            name=(TextView)view.findViewById(R.id.subname);
            percenttext=(TextView)view.findViewById(R.id.percent);
            currentpresent=(TextView)view.findViewById(R.id.currentpres);
            currentabsent=(TextView)view.findViewById(R.id.currentabs);
            b1=(Button)view.findViewById(R.id.presentplus);
            b2=(Button)view.findViewById(R.id.presentminus);
            b3=(Button)view.findViewById(R.id.absentplus);
            b4=(Button)view.findViewById(R.id.absentminus);
            c1= (CardView) view.findViewById(R.id.attendencecard);




        }


    }





}
