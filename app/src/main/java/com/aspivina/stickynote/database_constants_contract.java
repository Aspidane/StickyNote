package com.aspivina.stickynote;

import android.provider.BaseColumns;

/**
 * Created by Tyler on 3/17/2018.
 */

public class database_constants_contract {

	// To prevent someone from accidentally instantiating the contract class,
	// make the constructor private.
	private database_constants_contract() {}

	/* Inner class that defines the table contents */
	public static class constants implements BaseColumns {

		//Variables for the database's name and version number
		public static final String DATABASE_NAME="sticky_note_db";
		public static final int DATABASE_VERSION=1;

		//Table name and the options
		public static final String TABLE_NAME = "note";
		//public static final String ID_COLUMN = "id"; //This is replaced by the inherited _ID field
		public static final String TITLE_COLUMN = "title";
		public static final String CONTENTS_COLUMN = "contents";
		public static final String CREATION_TIME_COLUMN = "creation_time";
		public static final String LAST_MODIFIED_COLUMN = "last_modified";
		public static final String PRE_OPENED_BLOB_COLUMN = "pre_opened_options_blob";
		public static final String OPTIONS_BLOB_COLUMN = "options_blob";

		//Data types
		public static final String INTEGER_TYPE="INTEGER";
		public static final String TEXT_TYPE="TEXT";
		public static final String DATETIME_TYPE="TEXT";
		public static final String BLOB_TYPE="BLOB";

	}
}


