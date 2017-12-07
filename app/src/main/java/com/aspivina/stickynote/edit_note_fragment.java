package com.aspivina.stickynote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tyler on 12/3/2017.
 */

public class edit_note_fragment extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_note_layout);
	}

	/*
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.edit_note_layout, container, false);
	}
	*/
}
