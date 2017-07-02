package com.darte.vaibhav.anti_detention;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutmanagaer;
    ArrayList<attendence> arrayList = new ArrayList<>();
    DatabaseHelper mydb;
    public AdView mAdview;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAdview != null) {
            mAdview.resume();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(), AddRemove.class);
                startActivity(intent);
            }
        });

        mAdview = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("2F23865B0030F38FCF0E37F5E964B843")
                .build();
        mAdview.loadAd(adRequest);


        mydb = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutmanagaer = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanagaer);
        recyclerView.setHasFixedSize(true);

        SQLiteDatabase sqLiteDatabase = mydb.getReadableDatabase();
        Cursor cursor = mydb.getattendence(sqLiteDatabase);

        if(cursor.getCount()>0)

        {
            refreshlist();

        }
        else {



            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setMessage("Do you want to add a Subject now?\nOr you can add by clicking Plus button later")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(),AddRemove.class));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.setTitle("No subject is Added");
            dialog.show();

        }



    /*    mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }





    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),Reminders.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    public void refreshlist()
    {

        SQLiteDatabase sqLiteDatabase = mydb.getReadableDatabase();
        Cursor cursor = mydb.getattendence(sqLiteDatabase);

        if (cursor.getCount() > 0) {


            cursor.moveToFirst();

            do {
                attendence attendence = new attendence(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
                arrayList.add(attendence);
            } while (cursor.moveToNext());


            adapter = new RecyclerAdapter(arrayList);
            recyclerView.setAdapter(adapter);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        refreshlist();
        if (mAdview != null) {
            mAdview.resume();
        }

    }


}
