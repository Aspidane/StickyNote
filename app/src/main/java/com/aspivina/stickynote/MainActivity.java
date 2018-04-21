package com.aspivina.stickynote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /* A constant to save and restore the current note that is being displayed*/
    private static final String CURRENT_NOTE_EXTRA = "current note";
    private TextView current_note_title;
    private TextView current_note_body;
    private EditText input_note;
    private Button my_save_button;
    private ImageView my_save_image;
    private ImageView my_edit_image;
    private ImageView my_delete_image;
    private Boolean my_toggle;
    private JSONArray all_notes;
    private sticky_note_db my_db;
    private JSONObject current_note_json;
    private TextView current_id;
    private static final String TAG = MainActivity.class.getSimpleName();

    /******************************** OnCreate Function ***********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: it creates");
        read_note_function();
        my_toggle = false;
        my_db = new sticky_note_db(this);
        all_notes = my_db.get_all_notes();
        Log.d(TAG, all_notes.toString());
    }
    /******************************** OnStop Function ***********************************/
    @Override
    protected void onStop()
    { super.onStop();
        save_note_function();
        Log.d(TAG, "onStop: it stops");
        read_note_function();
    }
    /******************************** OnDestroy Function ***********************************/
    @Override
    protected void onDestroy()
    { super.onDestroy();
        save_note_function();
        Log.d(TAG, "onDestroy: it destroys");
        my_db.close();
    }
    /******************************** OnResume Function ***********************************/
    @Override
    protected void onResume()
    { super.onResume();
        Log.d(TAG, "onResume: it resumes");
        read_note_function();
    }
    /******************************** OnStart Function ***********************************/
    @Override
    protected void onStart()
    { super.onStart();
        Log.d(TAG, "onStart: it starts");
        read_note_function();
    }
    /********************************SAVE NOTE FUNCTION***********************************/
    public void save_note_function(){
        current_note_body = (TextView) findViewById(R.id.tv_current_note);
        input_note = (EditText) findViewById(R.id.et_edit_note);
        String string = input_note.getText().toString();
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
/********************************READ NOTE FUNCTION***********************************/
    public void read_note_function() {
        current_note_body = (TextView) findViewById(R.id.tv_current_note);
        //reading text from file
        FileInputStream fileIn;
        try {
            Log.d(TAG, "onReading: it reads");
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
            current_note_body.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/********************************onCreateOptionsMenu***********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }
/********************************onOptionsItemSelected***********************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TOOLBAR
        if (id == R.id.action_about) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("About");
            dialog.setMessage("Hello! We are AspiDane. If you liked this or if you find any bugs, please feel free to email us at aspidane@gmail.com");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /******************************** edit_save_click_button ***********************************/
    public void edit_save_click_button (View v){
        current_note_body = (TextView) findViewById(R.id.tv_current_note);
        input_note = (EditText) findViewById(R.id.et_edit_note);
        my_delete_image = (ImageView) findViewById(R.id.im_edit_save);
        Context context = getApplicationContext();
        if (false == my_toggle) {
            Log.d(TAG,"edit_click_view: it clicks for editing");
            CharSequence text = "editing";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            current_note_body.setVisibility(View.INVISIBLE);
            input_note.setVisibility(View.VISIBLE);
            my_delete_image.setImageResource(R.drawable.save_t);
            input_note.setText(current_note_body.getText().toString());
            my_toggle = true;
        }else{
            Log.d(TAG,"edit_click_view: it clicks for saving");
            CharSequence text = "saved";
            db_save_note_function();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            current_note_body.setVisibility(View.VISIBLE);
            input_note.setVisibility(View.INVISIBLE);
            my_delete_image.setImageResource(R.drawable.edit_t);
            current_note_body.setText(input_note.getText().toString());
            my_toggle = false;
        }
    }
    /******************************** DB Related Functions ***********************************/
    /******************************** DB Save Note Function ***********************************/
    public void db_save_note_function(){
        current_note_body = (TextView) findViewById(R.id.tv_current_note);
        input_note = (EditText) findViewById(R.id.et_edit_note);
        JSONObject note = new JSONObject();
        JSONObject old_note = new JSONObject();

        my_db = new sticky_note_db(this);
        String note_body = input_note.getText().toString();
        current_id = (TextView) findViewById(R.id.tv_id_note);
        String t_id = current_id.getText().toString();

        Date date = Calendar.getInstance().getTime();
        DateFormat date_formatter = new SimpleDateFormat("MM/dd/yyyy");
        String today = date_formatter.format(date);

        if (t_id.equals("zero")){
            Log.d(TAG, "Making a new entry in the dB");
            Log.d(TAG, "ID: "+t_id);
            try {
                note.put("title", "");
                note.put("contents", note_body);
                note.put("creation_time", today);
                note.put("last_modified", today);
                my_db.add_note(note);
                old_note = note;
                note = my_db.get_last_note();
                t_id = note.has("id") ? note.getString("id"):"error";
                if (t_id != "error"){
                    current_note_json = note;
                    current_id.setText(t_id); // Shows the new ID for the latest entry
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "Updating a note in the dB");
            Log.d(TAG, "ID: "+t_id);
            int my_int_id = 0;
            try{
                my_int_id = Integer.parseInt(t_id);
            }catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            old_note = my_db.get_note(my_int_id);
            try {
                note.put("id", t_id);
                note.put("title", "");
                note.put("contents", note_body);
                //note.put("creation_time", old_note.has("creation_time") ? old_note.getString("creation_time") : today);
                note.put("creation_time", today);

                note.put("last_modified", today);
                try {
                    my_db.update_note(note);
                    current_note_json = note;
                } catch (Error error) {
                    error.printStackTrace();

                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        all_notes = my_db.get_all_notes();
        Log.d(TAG, all_notes.toString());

    }
    /******************************** DB ? Note Function ***********************************/

}//Main Activity
