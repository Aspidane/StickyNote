package com.aspivina.stickynote;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aspivina.stickynote.note_list;


public class note_list_fragment extends Fragment implements note_list.ListItemClickListener {

	//RecyclerView setup
	private RecyclerView m_note_list_rv;
	private note_list m_note_list;

	LinearLayoutManager layout_manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.note_list_fragment_layout, container, false);

		// Replace 'android.R.id.list' with the 'id' of your RecyclerView
		m_note_list_rv = (RecyclerView) view.findViewById(R.id.rv_note_list);
		layout_manager = new LinearLayoutManager(this.getActivity());

		Log.d("debugMode", "Checkpoint?");

		m_note_list_rv.setLayoutManager(layout_manager);

		//Create the note_list (adapter for the RecyclerView)
		//mAdapter = new GreenAdapter(NUM_LIST_ITEMS,los_datos, this); --Rossi version
		m_note_list=new note_list(5, this);

		//mNumbersList.setAdapter(mAdapter); --Rossi version
		m_note_list_rv.setAdapter(m_note_list);

		return view;
	}

	@Override
	public void onListItemClick(int clickedItemIndex) {

	}

}
