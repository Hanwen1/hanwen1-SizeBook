package com.example.sizebook;
/**
 * Created by zheng on 2017-02-04.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 *  This class is the main view class of the assignment.
 *  All files are in the form of "json" files that are stored in Emulator's accessiable from Andriod Device moni
 *  This file name is indicated in the &nbsp &nbsp &nbsp FILENAME constant.
 *  It can shows the lists of records and the number of records.
 *  When click add button, it calls AddSizeBookActivity.
 *
 *  @auther hanwen
 *  @version 1.0
 *  @since 0.5
 *
 *
 */

public class MainActivity extends AppCompatActivity {
    /**
     * The file that all the sizebooks are saved there. The format of the file is JSON.
     * @see #loadFromFile()
     */

    public final static String EXTRA_MESSAGE = "com.example.SizeBook.MESSAGE";
    public static final String FILENAME = "file.sav";
    private ListView oldRecordsList;
    private TextView counts;

    public static ArrayList<SizeBook> sizebooklist;

    public static ArrayAdapter<SizeBook> adapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldRecordsList = (ListView) findViewById(R.id.oldRecordsList);

    }

    /** Called when the add button clicked. */
    public void addRecord(View view){
        Intent intent = new Intent(this, AddSizeBookActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        //tweetList = new ArrayList<Tweet>();
        adapter = new ArrayAdapter<SizeBook>(this,
                R.layout.list_item, sizebooklist);
        oldRecordsList.setAdapter(adapter);


        String countnum = String.valueOf(oldRecordsList.getAdapter().getCount());
        countnum = countnum +" records";
        counts = (TextView)findViewById(R.id.numRecords);
        counts.setText(countnum);


        oldRecordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Intent intentshow = new Intent(getApplicationContext(), ShowActivity.class);

                String message = String.valueOf(position);
                intentshow.putExtra(EXTRA_MESSAGE, message);
                startActivity(intentshow);
            }
        });

    }


    /**
     * loads sizebooks from specified file.
     *
     * @exception FileNotFoundException if the file is not created first.
     */

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            sizebooklist = gson.fromJson(in, new TypeToken<ArrayList<SizeBook>>() {}.getType());


            fis.close();



        } catch (FileNotFoundException e) {
            sizebooklist = new ArrayList<SizeBook>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
