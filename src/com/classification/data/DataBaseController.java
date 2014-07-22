package com.classification.data;

import com.classification.entities.Group;
import com.classification.entities.Player;
import com.classification.util.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseController extends SQLiteOpenHelper {

	private static CursorFactory cursorFactory = null;

	public DataBaseController(Context context) {
		super(context, Constants.DATABASE_NAME, cursorFactory,
				Constants.DATABASE_VERSION);
	}

	public void saveGroup(String groupName) {
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// CREATE TABLES
		db.execSQL(Group.CREATE_TABLE_PLAYER);
		db.execSQL(Player.CREATE_TABLE_PLAYER);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
