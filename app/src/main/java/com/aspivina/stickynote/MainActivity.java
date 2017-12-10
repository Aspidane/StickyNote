package com.aspivina.stickynote;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    /* A constant to save and restore the current note that is being displayed*/
    private static final String CURRENT_NOTE_EXTRA = "current note";
    private TextView current_note_title;
    private TextView current_note_body;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //edit_note_fragment edit_note=new edit_note_fragment();
        //note_list_fragment note_list=new note_list_fragment();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop()
    { super.onStop();
        save_note_function();
        Log.d(TAG, "onStop: it stops");
        read_note_function();
    }

    @Override
    protected void onResume()
    { super.onResume();
        Log.d(TAG, "onStop: it resumes");
    }

    /********************************SAVE NOTE FUNCTION***********************************/
    public void save_note_function(){
        String string = "Hello world! TESTING";
        String filename = "current_note.txt";
        FileOutputStream outputStream;
        try {
            Log.d(TAG, "onWriting: it writes");

            outputStream = openFileOutput(filename, this.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/********************************SAVING DATA FUNCTION*********************************/
    public void saving_data_function(){
        String string = "New text - testing";
        Bundle current_note_bundle = new Bundle();
        current_note_bundle.putString(CURRENT_NOTE_EXTRA, string.toString());
    }

/********************************READ NOTE FUNCTION***********************************/
    public void read_note_function() {
        current_note_title = (TextView) findViewById(R.id.tv_current_note);
        //reading text from file
        FileInputStream fileIn;
        try {
            fileIn = openFileInput("current_note.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[100];
            String s="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            current_note_title.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        current_note_body = (TextView) findViewById(R.id.tv_current_note);
        int id = item.getItemId();
        if (id == R.id.action_edit_note) {
            Intent start_edit_note = new Intent(this, edit_note.class);
            start_edit_note.putExtra(Intent.EXTRA_TEXT,current_note_body.getText().toString());
            startActivity(start_edit_note);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}//Main Activity
