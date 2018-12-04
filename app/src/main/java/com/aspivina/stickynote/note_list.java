package com.aspivina.stickynote;

/**
 * Created by Aspivina on 09/12/2017.
 */

import android.content.Context;
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
	//String to use when logging
	private static final String TAG = note_list.class.getSimpleName();
	//Number of view holders
	private static int view_holder_count;
	//Number of items
	private int m_item_count;
	//Listener that helps the main activity interact wwith this (as I understand it)
	final private ListItemClickListener m_on_click_listener;
	/******************************** ListItemClickListener Function ***********************************/
	public interface ListItemClickListener{
		void onListItemClick(int id);
	}
	/******************************** note_list Function ***********************************/
	public note_list(int itemCount, ListItemClickListener listener){
		m_on_click_listener=listener;
		view_holder_count=0;
		m_item_count=itemCount;
	}
    /******************************** note_view_holder Function ***********************************/
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
    /******************************** onBindViewHolder Function ***********************************/
	@Override
	public void onBindViewHolder(note_view_holder holder, int position) {
		Log.d(TAG, "#" + position);
		holder.bind(position);
	}
    /******************************** onBindViewHolder Function ***********************************/
	/*@Override
	public void onBindViewHolder(note_view_holder holder, int position, List<Object> payloads) {
		Log.d(TAG, "#"+position);
		holder.bind(position);
	}*/
    /******************************** getItemCount Function ***********************************/
	@Override
	public int getItemCount() {
        //Get the number of items
	    return m_item_count;
	}
    /******************************** note_view_holder Function ***********************************/
	class note_view_holder extends RecyclerView.ViewHolder
			implements View.OnClickListener{
		TextView list_item_view;
		TextView view_holder_instance;

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
			list_item_view.setText( "izquierda"); // Here I write the data from the API call
			view_holder_instance.setText( "derecha");
		}

		@Override
		public void onClick(View view){
			Context context = view.getContext();
			int clickedPosition = getAdapterPosition();
			m_on_click_listener.onListItemClick(clickedPosition);
			note_view_holder view_holder = note_view_holder.this;
			view_holder.view_holder_instance.setText("hice click"); // Here I can change the text again
			return;
		}
	}

}
