package com.darte.vaibhav.anti_detention;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class marks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView tv1,tv2;

    Button b1,b2;
    Spinner sp1;
DatabaseHelper dbhelper=new DatabaseHelper(this);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv1= (TextView) findViewById(R.id.subnamemarks);
        tv2= (TextView) findViewById(R.id.showmarks);
        tv2.setMovementMethod(new ScrollingMovementMethod());
        sp1= (Spinner) findViewById(R.id.removemarksspinner);





        b1= (Button) findViewById(R.id.addmarksbutton);
        b2= (Button) findViewById(R.id.removemarksbutton);


        Intent intent=getIntent();
        final String subname=intent.getStringExtra("subis");

        tv1.setText(subname);

        settextview(subname);

        loadexamname(subname);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp1.getSelectedItem()!=null) {

                    String examname = sp1.getSelectedItem().toString();
                    dbhelper.removeexam(subname, examname);
                    settextview(subname);
                    loadexamname(subname);
                }
                else{
                    Toast.makeText(getApplicationContext(),"nothing to delete",Toast.LENGTH_SHORT).show();
                }
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final EditText text1= new EditText(marks.this);
                final EditText text2= new EditText(marks.this);
                final EditText text3= new EditText(marks.this);


                text1.setHint("Test name");
                text2.setHint("Marks obtained");
                text2.setInputType(InputType.TYPE_CLASS_NUMBER);
                text3.setHint("Total exam marks");
                text3.setInputType(InputType.TYPE_CLASS_NUMBER);




                final LinearLayout linearLayout=new LinearLayout(getApplicationContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(text1);
                linearLayout.addView(text2);
                linearLayout.addView(text3);


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setCancelable(false)

                        .setTitle("Add marks")
                        .setView(linearLayout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String testname = text1.getText().toString();
                                if(text1.getText().length()>0) {
                                    Toast.makeText(getApplicationContext(), testname + " : marks added", Toast.LENGTH_SHORT).show();
                                    int obtained = Integer.parseInt(text2.getText().toString());
                                    int total = Integer.parseInt(text3.getText().toString());
                                    dbhelper.addmarks(subname, obtained, total, testname);
                                    settextview(subname);
                                    loadexamname(subname);
                                    dialog.dismiss();
                                    
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Please Type Some value",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settextview(subname);
                                dialog.dismiss();
                                dialog.cancel();

                            }
                        });

                AlertDialog dialog=builder.create();
                dialog.show();



            }
        });








    }



    public void settextview(String sub)
    {

        Cursor showmarks=dbhelper.getmarks(sub);
        StringBuffer buffer=new StringBuffer();
        if(showmarks.getCount()==0){
            tv2.setText("No test marks added");

        }
        else {
            while (showmarks.moveToNext()) {
                buffer.append("Test name: " + showmarks.getString(2) + "\n");
                buffer.append("Marks obtained: " + showmarks.getString(0) + "\n");
                buffer.append("Total marks: " + showmarks.getString(1) + "\n\n");
            }

            tv2.setText(buffer.toString());

        }
    }



    private void loadexamname(String subject)
    {


        List<String> allSubjects =dbhelper.getexamname(subject);
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
