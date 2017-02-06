package com.example.sizebook;

/**
 * Created by zheng on 2017-02-05.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ShowActivity extends AppCompatActivity {

    /**
     * Show details for a record, can edit and save changes and can delete this record.
     */

    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        Intent intentshow = getIntent();
        String message = intentshow.getStringExtra(MainActivity.EXTRA_MESSAGE);
        position = Integer.parseInt(message);

        /**
         * Show details for a record.
         */
        TextView nameText = (TextView) findViewById(R.id.name_field);
        nameText.setText(MainActivity.sizebooklist.get(position).getName());

        TextView dateText = (TextView) findViewById(R.id.date_field);
        dateText.setText(MainActivity.sizebooklist.get(position).getDate());

        TextView neckText = (TextView) findViewById(R.id.neck_field);
        neckText.setText(MainActivity.sizebooklist.get(position).getNeck());

        TextView bustText = (TextView) findViewById(R.id.bust_field);
        bustText.setText(MainActivity.sizebooklist.get(position).getBust());

        TextView chestText = (TextView) findViewById(R.id.chest_field);
        chestText.setText(MainActivity.sizebooklist.get(position).getChest());

        TextView waistText = (TextView) findViewById(R.id.waist_field);
        waistText.setText(MainActivity.sizebooklist.get(position).getWaist());

        TextView hipText = (TextView) findViewById(R.id.hip_field);
        hipText.setText(MainActivity.sizebooklist.get(position).getHip());

        TextView inseamText = (TextView) findViewById(R.id.inseam_field);
        inseamText.setText(MainActivity.sizebooklist.get(position).getInseam());

        TextView commentText = (TextView) findViewById(R.id.comment_field);
        commentText.setText(MainActivity.sizebooklist.get(position).getComment());

        /**
         * save changes.
         */
        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nameText = (EditText) findViewById(R.id.name_field);
                EditText dateText = (EditText) findViewById(R.id.date_field);
                EditText neckText = (EditText) findViewById(R.id.neck_field);
                EditText bustText = (EditText) findViewById(R.id.bust_field);
                EditText chestText = (EditText) findViewById(R.id.chest_field);
                EditText waistText = (EditText) findViewById(R.id.waist_field);
                EditText hipText = (EditText) findViewById(R.id.hip_field);
                EditText inseamText = (EditText) findViewById(R.id.inseam_field);
                EditText commentText = (EditText) findViewById(R.id.comment_field);

                String name = nameText.getText().toString();



                if (name.matches("")) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "The name is empty!!!", Toast.LENGTH_SHORT).show();

                }
                else{
                    setResult(RESULT_OK);

                    String date = dateText.getText().toString();
                    String neck = neckText.getText().toString();
                    String bust = bustText.getText().toString();
                    String chest = chestText.getText().toString();
                    String waist = waistText.getText().toString();
                    String hip = hipText.getText().toString();
                    String inseam = inseamText.getText().toString();
                    String comment = commentText.getText().toString();

                    SizeBook person = MainActivity.sizebooklist.get(position);
                    person.setDate(date);
                    person.setNeck(neck);
                    person.setBust(bust);
                    person.setChest(chest);
                    person.setWaist(waist);
                    person.setHip(hip);
                    person.setInseam(inseam);
                    person.setComment(comment);

                    saveInFile();
                    finish();

                }
            }
        });
    }

    /**
     * delete this record.
     */
    public void deleteRecord(View view){
        MainActivity.sizebooklist.remove(position);
        saveInFile();
        finish();
    }
    /**
     * Saves tweets to a specified file in JSON format.
     * @throws FileNotFoundException if file folder doesn't exist.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            gson.toJson(MainActivity.sizebooklist, out);

            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
