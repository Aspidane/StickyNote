package com.aspivina.stickynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aspivina.stickynote.database_constants_contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tyler on 3/8/2018.
 */

public class sticky_note_db extends SQLiteOpenHelper {

    //Variables for the database's name and version number
    //private static String database_name="sticky_note_db";
    //private static int database_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + database_constants_contract.constants.TABLE_NAME + " (" +
                        database_constants_contract.constants._ID + " "+database_constants_contract.constants.INTEGER_TYPE+" PRIMARY KEY," +
                        database_constants_contract.constants.TITLE_COLUMN + " "+database_constants_contract.constants.TEXT_TYPE+"," +
                        database_constants_contract.constants.CONTENTS_COLUMN + " "+database_constants_contract.constants.TEXT_TYPE+"," +
                        database_constants_contract.constants.CREATION_TIME_COLUMN + " "+database_constants_contract.constants.DATETIME_TYPE+"," +
                        database_constants_contract.constants.LAST_MODIFIED_COLUMN + " "+database_constants_contract.constants.DATETIME_TYPE+"," +
                        //	PRE_OPENED_BLOB_COLUMN + " "+BLOB_TYPE+"," +
                        database_constants_contract.constants.OPTIONS_BLOB_COLUMN + " "+database_constants_contract.constants.BLOB_TYPE+")";

        db.execSQL(SQL_CREATE_ENTRIES);

        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + database_constants_contract.constants.TABLE_NAME;


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    //Gets all the notes
    public JSONArray get_all_notes(){

        //Get the database
        SQLiteDatabase db=this.getReadableDatabase();

		//Cursor to handle the results. Not sure why it's called a cursor, but it's what db.query returns.
        Cursor cursor = db.query(
            database_constants_contract.constants.TABLE_NAME,   // The table to query
			null,	// The array of columns to return (pass null to get all)
            null,	// The columns for the WHERE clause
            null,	// The values for the WHERE clause
            null,	// don't group the rows
            null,	// don't filter by row groups
            null	// The sort order
        );

        //Initialize the array
        JSONArray notes=new JSONArray();

		//Need to "move" before doing any operation. moveToNext returns false when we go out of bounds
		while(cursor.moveToNext()){

			try {

				//Create a new JSON object to hold the row
				JSONObject row = new JSONObject();

				row.put("id", cursor.getColumnIndex(database_constants_contract.constants._ID));
				row.put("title", cursor.getColumnIndex(database_constants_contract.constants.TITLE_COLUMN));
				row.put("contents", cursor.getColumnIndex(database_constants_contract.constants.CONTENTS_COLUMN));
				row.put("creation_time", cursor.getColumnIndex(database_constants_contract.constants.CREATION_TIME_COLUMN));
				row.put("last_modified", cursor.getColumnIndex(database_constants_contract.constants.LAST_MODIFIED_COLUMN));
				//row.put("options_blob", cursor.getColumnIndex(database_constants_contract.constants.OPTIONS_BLOB_COLUMN));

				//Add the row to the JSON array
				notes.put(row);
			} catch (JSONException error){
				Log.d("\n\nERROR: ", "get_all_notes when reading cursor: "+error.getMessage()+"\n\n");
			}
		}

		//Close the cursor
		cursor.close();

		//Return the array
        return notes;
    }

    //Wrapper for the other get_note function below; uses and int instead of a string
	public JSONObject get_note(int id){

    	//Make a string version of the id
    	String new_id=Integer.toString(id);

    	//Call the below function
    	return get_note(new_id);
	}

	//Gets a specific note using its ID
	public JSONObject get_note(String id){
    	JSONObject note=new JSONObject();

		//Get the database
		SQLiteDatabase db=this.getReadableDatabase();

		//Android requires a String array so do it like this
		String[] selection={ id };

		//Cursor to handle the results. Not sure why it's called a cursor, but it's what db.query returns.
		Cursor cursor = db.query(
				database_constants_contract.constants.TABLE_NAME,   // The table to query
				null,	// The array of columns to return (pass null to get all)
				database_constants_contract.constants._ID,	// The columns for the WHERE clause
				selection,	// The values for the WHERE clause
				null,	// don't group the rows
				null,	// don't filter by row groups
				null	// The sort order
		);

		//If there are no items in the results
		if(0 == cursor.getCount()){
			return null; //Return null
		}

		//If there are results, move to the next one
		cursor.moveToNext();

		try {
			note.put("id", cursor.getColumnIndex(database_constants_contract.constants._ID));
			note.put("title", cursor.getColumnIndex(database_constants_contract.constants.TITLE_COLUMN));
			note.put("contents", cursor.getColumnIndex(database_constants_contract.constants.CONTENTS_COLUMN));
			note.put("creation_time", cursor.getColumnIndex(database_constants_contract.constants.CREATION_TIME_COLUMN));
			note.put("last_modified", cursor.getColumnIndex(database_constants_contract.constants.LAST_MODIFIED_COLUMN));

		} catch (JSONException error){
			Log.d("\n\nERROR: ", "get_note when reading cursor: "+error.getMessage()+"\n\n");
			return null;
		}
    	return note;
	}

	//Wrapper for the insert function; this is called to insert a note into the database
	public long add_note(JSONObject object){

		//Get the database
		SQLiteDatabase db=this.getWritableDatabase();

		//Initialize the ContentValues
		ContentValues values=new ContentValues();

		try {

			//Get the data from the JSON
			String title = object.has("title") ? object.getString("title") : "";
			String contents = object.has("contents") ? object.getString("contents") : "";
			String creation_time = object.has("creation_time") ? object.getString("creation_time") : "";
			String last_modified = object.has("last_modified") ? object.getString("last_modified") : "";

			//Put everything into the ContentValues
			values.put("title", title);
			values.put("contents", contents);
			values.put("creation_time", creation_time);
			values.put("last_modified", last_modified);

		} catch (JSONException error){
			Log.d("\n\nERROR: ", "add_note when reading cursor: "+error.getMessage()+"\n\n");
		}

		return insert(db, values);
	}

	//Function to insert data into the DB
	//Returns the new ID or -1 if there is an error
    private long insert(SQLiteDatabase db, ContentValues values){
    	//Try to insert it; if values is null, do nothing.
		//Return the result (the new ID or -1)
        return db.insert(database_constants_contract.constants.TABLE_NAME, null, values);
    }

	//Delete function; this deletes a note with the given ID
	//Note, this is not a wrapper, it is not needed for deletion
	public long delete_note(int id){

		//Get the database
		SQLiteDatabase db=this.getWritableDatabase();

		//The query part; the "?" represents arguments I think
		String query = database_constants_contract.constants._ID+" LIKE ?";

		//The id to delete formatted for the delete function
		String[] query_argument={Integer.toString(id)};



		//Calls the delete function and returns how many rows were deleted
		return db.delete(database_constants_contract.constants.TABLE_NAME, query, query_argument);
	}

	//Wrapper for updating notes; it takes a JSON object and then processes it and calls the update function
	public long update_note(JSONObject object){

		//Get the database
		SQLiteDatabase db=this.getWritableDatabase();

		//Initialize the ContentValues
		ContentValues values=new ContentValues();

		try {

			//Get the data from the JSON

			//Initialize the id to -1; this will be overwritten later
			int id=-1;

			//Ensure it has an id
			if(object.has("id")){
				String idString = object.has("id") ? object.getString("id") : "-1"; //Return -1 if not found
				id=Integer.parseInt(idString);

				//If it does not, there is an error
			} else {
				Log.d("\n\nERROR: ","id was not found. Abandoning operation.\n\n");
				return -1; //And return -1 to indicate an error was given
			}

			//If the title is being updated
			if(object.has("title")){
				//Add the new title to the list
				values.put("title", object.getString("title"));
			}

			//Repeat for contents
			if(object.has("contents")){
				values.put("contents", object.getString("contents"));
			}

			//Repeat for creation_time
			if(object.has("creation_time")){
				values.put("creation_time", object.getString("creation_time"));
			}

			//Repeat for last_modified
			if(object.has("last_modified")){
				values.put("last_modified", object.getString("last_modified"));
			}

			//The query part; the "?" represents arguments I think
			String query = database_constants_contract.constants._ID+" LIKE ?";

			//The id to delete formatted for the delete function
			String[] query_argument={Integer.toString(id)};

			//Do the update
			return update(db, values, query, query_argument);

		} catch (JSONException error){
			Log.d("\n\nERROR: ", "update_note when reading cursor: "+error.getMessage()+"\n\n");
			return -1;
		}

	}

	//Function to insert data into the DB
	//Returns the new ID or -1 if there is an error
	private long update(SQLiteDatabase db, ContentValues values, String query, String[] query_arguments){
		//Try to do the update
		//Return the result (the number of rows updated)
		return db.update(database_constants_contract.constants.TABLE_NAME, values, query, query_arguments);
	}

    //Returns
    public sticky_note_db(Context context){
        super(context, database_constants_contract.constants.DATABASE_NAME, null, database_constants_contract.constants.DATABASE_VERSION);

    }
}
