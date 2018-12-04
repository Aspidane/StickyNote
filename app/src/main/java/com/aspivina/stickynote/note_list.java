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

public class note_list extends RecyclerView.Adapter<note_list.note_view_holder> {
	//The entries of the notes
	private ArrayList<Entry> entries;
	//String to use when logging
	private static final String TAG=note_list.class.getSimpleName();
	//Number of view holders
	private static int view_holder_count;
	//Index of the current entry
	private int m_adapter_index;
	//Number of items
	private int m_item_count;
	//Listener that helps the main activity interact with this (as I understand it)
	final private ListItemClickListener m_on_click_listener;
/*
	public note_list(int itemCount, ListItemClickListener listener){
		entries=new ArrayList<Entry>();
		m_on_click_listener=listener;
		view_holder_count=0;
		m_item_count=itemCount;
	}*/

	public note_list(int itemCount, ArrayList<Entry> los_Rossi, ListItemClickListener listener){
		entries=los_Rossi;
		m_on_click_listener=listener;
		view_holder_count=0;
		m_item_count=itemCount;
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
    	Context context=viewGroup.getContext();
    	int layout_id= R.layout.number_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean shouldAttachToParentImmediately = false;

		View view = inflater.inflate(layout_id, viewGroup, shouldAttachToParentImmediately);
		note_view_holder viewHolder = new note_view_holder(view);

		viewHolder.view_holder_instance.setText("ViewHolder index: " + view_holder_count); // AQUI CAMBIAR DATA DE NUEVO
        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance_Blue(context,view_holder_count, true);
		view_holder_count++;
		Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
				+ view_holder_count);
		viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
		return viewHolder;

	};

	@Override
	public void onBindViewHolder(note_view_holder holder, int position) {
		Log.d(TAG, "#" + position);
		holder.bind(position);
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
		return m_item_count;
	}

	//Entry class; holds info on the note in the list
	public static class Entry{

		//Fields
		private int id;
		private String title;
		private String contents;
		private String creation_time;
		private String last_modified;

		//If something is selected or not
		public boolean selected;

		//Getters
		public int get_id(){ return id; }
		public String get_title(){ return title; }
		public String get_contents(){ return contents; }
		public String get_creation_time(){ return creation_time; }
		public String get_last_modified(){ return last_modified; }

		//Setters
		public void set_id(int new_id){ id=new_id; }
		public void get_title(String new_title){  title=new_title; }
		public void get_contents(String new_contents){ contents=new_contents; }
		public void get_creation_time(String new_creation_time){ creation_time=new_creation_time; }
		public void get_last_modified(String new_last_modified){ last_modified=new_last_modified; }

		//Default constructor
		public Entry(){
			id=-1;
			title="default title";
			contents="default contents";
			creation_time="default creation";
			last_modified="default last modified";
		}

		//Constructor that accepts parameters
		public Entry(int new_id, String new_title, String new_contents, String new_creation_time, String new_last_modified){
			id=new_id;
			title=new_title;
			contents=new_contents;
			creation_time=new_creation_time;
			last_modified=new_last_modified;
		}
	}

	class note_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{
		TextView list_item_view;
		TextView view_holder_instance;
		int view_index;

		public note_view_holder(View item_view){
			super(item_view);
			list_item_view = (TextView) itemView.findViewById(R.id.tv_item_number);
			view_holder_instance=(TextView) itemView.findViewById(R.id.tv_view_holder_index);
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
			list_item_view.setText( String.valueOf( entries.get(list_index).get_id() ) ); // Here I write the data from the API call
			view_holder_instance.setText( entries.get(list_index).get_title() );
		}

		@Override
		public void onClick(View view){
			//If there are not enough entries
			if(entries.size()<=view_index){
				//Return
				return;
			}
			Context context = view.getContext();
			int clickedPosition = getAdapterPosition();
			m_on_click_listener.onListItemClick(entries.get(clickedPosition).get_id());
			note_view_holder view_holder = note_view_holder.this;
			view_holder.view_holder_instance.setText(entries.get(clickedPosition).get_contents()); // Here I can change the text again
			return;
		}
	}

}
