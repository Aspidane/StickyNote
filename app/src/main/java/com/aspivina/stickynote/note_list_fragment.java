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
import com.aspivina.stickynote.note_list.Entry;

import java.util.ArrayList;

public class note_list_fragment extends Fragment implements note_list.ListItemClickListener {

	private static final String TAG = note_list_fragment.class.getSimpleName();
	//RecyclerView setup
	private RecyclerView m_note_list_rv;
	private note_list m_note_list;

	LinearLayoutManager layout_manager;

	public class rossi_random{
		//Fields
		private int id;
		private String title;
		private String contents;
		private String creation_time;
		private String last_modified;

		//If something is selected or not
		public boolean selected;
		//
		public int get_id(){ return id; }
		public String get_title(){ return title; }
		public String get_contents(){ return contents; }
		public String get_creation_time(){ return creation_time; }
		public String get_last_modified(){ return last_modified; }

		public rossi_random(){
			id=-1;
			title="";
			contents="";
			creation_time="";
			last_modified="";
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		fake_function();
		View view = inflater.inflate(R.layout.note_list_fragment_layout, container, false);
		// Replace 'android.R.id.list' with the 'id' of your RecyclerView
		m_note_list_rv = (RecyclerView) view.findViewById(R.id.rv_note_list);
		layout_manager = new LinearLayoutManager(this.getActivity());

		Log.d("debugMode", "Checkpoint?");
		m_note_list_rv.setLayoutManager(layout_manager);
		//Create the note_list (adapter for the RecyclerView)
		//mAdapter = new GreenAdapter(NUM_LIST_ITEMS,los_datos, this); --Rossi version
		//m_note_list=new note_list(5, this);
		m_note_list=new note_list(15, get_fake_data(), this);
		//mNumbersList.setAdapter(mAdapter); --Rossi version
		m_note_list_rv.setAdapter(m_note_list);
		return view;
	}

	@Override
	public void onListItemClick(int clickedItemIndex) {

	}

	private ArrayList<Entry> rossi_global=new ArrayList<Entry>(); //TODO DELETE THIS

	public void fake_function(){
		//rossi_global=new Entry();
		Log.d(TAG, " in note_list_fragment");
		for(int i=0; i<15;i++) {
			rossi_global.add(new Entry());
			rossi_global.get(i).set_id(i);
		}
		Log.d(TAG, "size: "+rossi_global.size());
		for(int i=0;i<rossi_global.size();i++){
			Log.d(TAG, "Entry "+i+" id: "+rossi_global.get(i).get_id());
		}
	};

	public ArrayList<Entry> get_fake_data(){
		return rossi_global;
	}

}
