package com.aspivina.stickynote;

/**
 * Created by Aspivina on 09/12/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.aspivina.stickynote.MainActivity;

/**
 * Created by Tyler on 12/3/2017.
 */

public class note_list extends RecyclerView.Adapter<note_list.note_view_holder> {

	//The entries of the notes
	private ArrayList<Entry> entries;

	//String to use when logging
	private static final String TAG=note_list.class.getSimpleName();

	//Number of view holders
	private static int view_holder_count;

	//Index of the current entry
	private int m_adapter_index;

	//Listener that helps the main activity interact wwith this (as I understand it)
	final private ListItemClickListener m_on_click_listener;

	public note_list(int itemCount, ListItemClickListener listener){

		m_on_click_listener=listener;

		view_holder_count=0;

	}

	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.note_list_layout);
	}

    @Override
    public note_view_holder onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.note_list_fragment_layout, container, false);

        return null;
    }
    */

    public note_view_holder onCreateViewHolder(ViewGroup viewGroup, int viewType){

    	return null;
	};


	@Override
	public void onBindViewHolder(note_view_holder holder, int position) {

	}

	@Override
	public void onBindViewHolder(note_view_holder holder, int position, List<Object> payloads) {
		Log.d(TAG, "#"+position);
		holder.bind(position);
		m_adapter_index=position;
	}

	public interface ListItemClickListener{
		void onListItemClick(int id);
	}

	//Get the number of items
	@Override
	public int getItemCount() {
		return entries.size();
	}

	class Entry{
		private int id;
		private String title;
		private String contents;
		private String creation_time;
		private String last_modified;

		public boolean selected;

		public int get_id(){ return id; }
		public String get_title(){ return title; }
		public String get_contents(){ return contents; }
		public String get_creation_time(){ return creation_time; }
		public String get_last_modified(){ return last_modified; }

	}

	class note_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView list_item_view;
		TextView view_holder_instance;

		int view_index;

		public note_view_holder(View item_view){
			super(item_view);

			//Note: setOnClickListener is a built in function;
			//I didn't make it so it doesn't conform to our style guide
			item_view.setOnClickListener(this);
		}

		//Bind data to the viewHolder
		void bind(int list_index){

			//Display the index if desired
			//list_item_view.setText(String.valueOf(list_index));

			//Ensure entries has data and we are not out of bounds
			if(list_index >= entries.size()) return;

			Context context=itemView.getContext();

			//
			Entry entry=entries.get(list_index);
		}

		@Override
		public void onClick(View view){

			//If there are not enough entries
			if(entries.size()<=view_index){
				//Return
				return;
			}

			//Get where the user clicked
			int clicked_position=getAdapterPosition();

			//Get the entry
			Entry entry=entries.get(clicked_position);

			//Get the data
			int id=entry.get_id();

			m_on_click_listener.onListItemClick(id);

			return;
		}
	}

}
