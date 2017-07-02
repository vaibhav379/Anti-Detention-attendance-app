package com.darte.vaibhav.anti_detention;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class AddRemove extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper mydbar;
    EditText et1,et2,et3;
    Button b1,b2;
    Spinner sp1;
    public AdView mAdview;


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdview != null) {
            mAdview.resume();
        }
    }

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
        setContentView(R.layout.activity_add_remove);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydbar=new DatabaseHelper(this);
        et1= (EditText) findViewById(R.id.addsub);
        et2= (EditText) findViewById(R.id.present);
        et3= (EditText) findViewById(R.id.absent);
        b1= (Button) findViewById(R.id.btn_add);
        b2= (Button) findViewById(R.id.btn_remove);
        sp1= (Spinner) findViewById(R.id.spinner1);
        sp1.setOnItemSelectedListener(this);

        loadSpinnerData();
        AddData();
        removeSub();

        mAdview = (AdView) findViewById(R.id.adViewar);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("2F23865B0030F38FCF0E37F5E964B843")
                .build();
        mAdview.loadAd(adRequest);

    }

    public void removeSub()
    {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sp1.getSelectedItem()!=null)
                {
                    String delsub =sp1.getSelectedItem().toString();
                    mydbar.removeSubject(delsub);
                    mydbar.removealltest(delsub);
                    Toast.makeText(getApplicationContext(),"Removed: "+delsub,Toast.LENGTH_SHORT).show();
                    loadSpinnerData();
                }
                else {
                    Toast.makeText(getApplicationContext(),"No values available",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void AddData()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label1, label2, label3;
                label1 = et1.getText().toString();
                label2 = et2.getText().toString();
                label3 = et3.getText().toString();

                if (label1.trim().length() > 0 && label2.trim().length() > 0 && label3.trim().length() > 0) {
                    boolean isInserted = mydbar.insertData(label1, label2, label3);

                    if (isInserted == true) {
                        Toast.makeText(AddRemove.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        loadSpinnerData();
                    } else {
                        Toast.makeText(AddRemove.this, "Error Adding Data", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(AddRemove.this, "Please enter values", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void loadSpinnerData()
    {
        DatabaseHelper mydbar=new DatabaseHelper(getApplicationContext());

        List<String> allSubjects =mydbar.getallSubject();
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allSubjects);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
