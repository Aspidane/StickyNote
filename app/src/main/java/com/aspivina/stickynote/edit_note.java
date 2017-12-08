package com.aspivina.stickynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by Aspivina on 05/12/2017.
 */

public class edit_note extends AppCompatActivity {
    private EditText edit_note_box;
    private String temp_text;


    @Override
    protected void onCreate (Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.edit_note_layout);
        edit_note_box = (EditText) findViewById(R.id.et_edit_note);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                temp_text = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                edit_note_box.setText(temp_text);
            }
        }

    }// onCreate

    //must to create a custom menu for go_editing that include Save / Cancel / ? options
}
