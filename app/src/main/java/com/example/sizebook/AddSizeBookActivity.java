package com.example.sizebook;

/**
 * Created by zheng on 2017-02-04.
 */


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


/**
 *  This class is the add view class of the project.
 *  It can add a new record.
 *  When click sva button, it saves this new record and back to MainActivity.
 *
 *  @auther hanwen
 *
 *
 */


public class AddSizeBookActivity extends AppCompatActivity {

    /**
     * Instantiates a new record.
     */
    private ArrayAdapter<SizeBook> adapter;
    private EditText nameText;
    private EditText dateText;
    private EditText neckText;
    private EditText bustText;
    private EditText chestText;
    private EditText waistText;
    private EditText hipText;
    private EditText inseamText;
    private EditText commentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsizebook);
        nameText = (EditText) findViewById(R.id.name_field);
        dateText = (EditText) findViewById(R.id.date_field);
        neckText = (EditText) findViewById(R.id.neck_field);
        bustText = (EditText) findViewById(R.id.bust_field);
        chestText = (EditText) findViewById(R.id.chest_field);
        waistText = (EditText) findViewById(R.id.waist_field);
        hipText = (EditText) findViewById(R.id.hip_field);
        inseamText = (EditText) findViewById(R.id.inseam_field);
        commentText = (EditText) findViewById(R.id.comment_field);
        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
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


                    SizeBook person = new SizeBook(name);
                    person.setDate(date);
                    person.setNeck(neck);
                    person.setBust(bust);
                    person.setChest(chest);
                    person.setWaist(waist);
                    person.setHip(hip);
                    person.setInseam(inseam);
                    person.setComment(comment);

                    MainActivity.sizebooklist.add(person);
                    saveInFile();
                    finish();

                }



            }
        });
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
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

    /**
     * loads sizebooks from specified file.
     *
     * @exception FileNotFoundException if the file is not created first.
     */

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(MainActivity.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            MainActivity.sizebooklist = gson.fromJson(in, new TypeToken<ArrayList<SizeBook>>() {}.getType());

            fis.close();



        } catch (FileNotFoundException e) {
            MainActivity.sizebooklist = new ArrayList<SizeBook>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
