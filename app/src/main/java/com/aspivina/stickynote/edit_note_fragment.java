package com.aspivina.stickynote;
/*
 * Created by Aspivina on 09/12/2017.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

public class edit_note_fragment extends Fragment{
    private TextView tv_current_note;
    private EditText et_current_note;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_note_fragment_layout, container, false);
    }

}
