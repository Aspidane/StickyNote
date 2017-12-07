package com.aspivina.stickynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		edit_note_fragment edit_note=new edit_note_fragment();
		note_list_fragment note_list=new note_list_fragment();

        setContentView(R.layout.activity_main);
    }
}
