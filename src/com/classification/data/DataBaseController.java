package com.classification.data;

import java.util.ArrayList;
import java.util.Iterator;

import com.classification.entities.Game;
import com.classification.entities.Group;
import com.classification.entities.Player;
import com.classification.entities.Record;
import com.classification.util.Constants;
import com.commons.util.AppsUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseController extends SQLiteOpenHelper {

	private static CursorFactory cursorFactory = null;

	public DataBaseController(Context context) {
		super(context, Constants.DATABASE_NAME, cursorFactory,
				Constants.DATABASE_VERSION);
	}

	public ArrayList<String> loadRecordsGame(Integer gameId) {

		ArrayList<String> listRecords = new ArrayList<String>();

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sql = "SELECT " + Group.COLUMN_NAME + ","
				+ Record.COLUMN_PLAYER_NAME + "," + Record.COLUMN_VALUE + ","
				+ Record.COLUMN_TYPE + " FROM " + Record.TABLE_NAME + ", "
				+ Group.TABLE_NAME + " WHERE " + Record.COLUMN_GROUP_ID + " = "
				+ Group.COLUMN_ID + " AND " + Record.COLUMN_GAME_ID + " = "
				+ gameId;

		dbSelect.close();

		return listRecords;

	}

	public ArrayList<Record> loadRecordsGroups(Integer groupId) {

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sql = "SELECT " + Record.COLUMN_ID + ", "
				+ Record.COLUMN_GROUP_ID + "," + Record.COLUMN_GAME_ID + ","
				+ Record.COLUMN_PLAYER_NAME + "," + Record.COLUMN_TYPE + ","
				+ Record.COLUMN_VALUE + " FROM " + Record.TABLE_NAME
				+ " WHERE " + Record.COLUMN_GROUP_ID + " = " + groupId;

		Cursor cursor = dbSelect.rawQuery(sql, null);

		ArrayList<Record> listRecords = new ArrayList<Record>();

		if (cursor.moveToFirst()) {

			do {

				Record record = new Record();

				record.setId(cursor.getInt(0));
				record.setGroupId(cursor.getInt(1));
				record.setGameId(cursor.getInt(2));
				record.setPlayerName(cursor.getString(3));
				record.setType(cursor.getString(4));
				record.setValue(cursor.getInt(5));

				listRecords.add(record);

			} while (cursor.moveToNext());

		}

		dbSelect.close();

		return listRecords;

	}

	public ArrayList<Group> loadGroups() {

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sql = "SELECT " + Group.COLUMN_ID + ", " + Group.COLUMN_NAME
				+ " FROM " + Group.TABLE_NAME;

		Cursor cursor = dbSelect.rawQuery(sql, null);

		ArrayList<Group> listGroups = new ArrayList<Group>();

		if (cursor.moveToFirst()) {

			do {

				Group group = new Group(cursor.getString(1));

				group.setId(cursor.getInt(0));

				listGroups.add(group);

			} while (cursor.moveToNext());

		}

		dbSelect.close();

		return listGroups;

	}

	public ArrayList<Player> loadGroupPlayers(Integer id) {

		ArrayList<Player> listPlayers = new ArrayList<Player>();

		String sql = "SELECT " + Player.COLUMN_PLAYER_ID + ", "
				+ Player.COLUMN_GROUP_ID + ", " + Player.COLUMN_NAME + " FROM "
				+ Player.TABLE_NAME + " WHERE " + Player.COLUMN_GROUP_ID
				+ " = " + id;

		SQLiteDatabase dbSelect = getReadableDatabase();

		Cursor cursor = dbSelect.rawQuery(sql, null);

		if (cursor != null && (cursor.moveToFirst())) {

			do {

				Player player = new Player(cursor.getString(cursor
						.getColumnIndex(Player.COLUMN_NAME)));

				player.setGroupId(cursor.getInt(cursor
						.getColumnIndex(Player.COLUMN_GROUP_ID)));
				player.setId(cursor.getInt(cursor
						.getColumnIndex(Player.COLUMN_PLAYER_ID)));

				listPlayers.add(player);

			} while (cursor.moveToNext());

		}

		dbSelect.close();

		return listPlayers;

	}

	public ArrayList<Game> loadGames() {

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sql = "SELECT " + Game.COLUMN_ID + ", " + Game.COLUMN_NAME
				+ " , " + Game.COLUMN_TYPE_GAME + " , " + Game.COLUMN_GOAL_GAME
				+ " , " + Game.COLUMN_ORDER_TYPE + " FROM " + Game.TABLE_NAME;

		Cursor cursor = dbSelect.rawQuery(sql, null);

		ArrayList<Game> listGames = new ArrayList<Game>();

		if (cursor.moveToFirst()) {

			do {

				Game game = new Game();

				game.setId(cursor.getInt(0));
				game.setName(cursor.getString(1));
				game.setTypeGame(cursor.getString(2));
				game.setGoalGame(cursor.getInt(3));
				game.setOrderType(cursor.getString(4));

				listGames.add(game);

			} while (cursor.moveToNext());

		}

		dbSelect.close();

		return listGames;

	}

	public void deleteGame(Integer gameId) {

		SQLiteDatabase dbDelete = getWritableDatabase();

		String deleteGame = "DELETE FROM " + Game.TABLE_NAME + " WHERE "
				+ Game.COLUMN_ID + " = " + gameId;

		String deleteRecord = "DELETE FROM " + Record.TABLE_NAME + " WHERE "
				+ Record.COLUMN_GAME_ID + " = " + gameId;

		dbDelete.execSQL(deleteGame);
		dbDelete.execSQL(deleteRecord);

		dbDelete.close();

	}

	public void saveGame(Game game) {

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sqlMaxContGame = "SELECT max(" + Game.COLUMN_ID + ") id FROM "
				+ Game.TABLE_NAME;

		Cursor cursorMaxContGame = dbSelect.rawQuery(sqlMaxContGame, null);

		Integer gameId = 1;

		if (cursorMaxContGame.moveToFirst()) {

			gameId = cursorMaxContGame.getInt(0);

			do {
				gameId = cursorMaxContGame.getInt(0);
			} while (cursorMaxContGame.moveToNext());

			if (gameId == null) {
				gameId = 1;
			} else {
				gameId += 1;
			}

		}

		dbSelect.close();

		SQLiteDatabase dbInsert = getWritableDatabase();

		if (dbInsert != null) {

			String sqlInsert = "INSERT INTO " + Game.TABLE_NAME + " ("
					+ Game.COLUMN_ID + "," + Game.COLUMN_NAME + ", "
					+ Game.COLUMN_TYPE_GAME + "," + Game.COLUMN_GOAL_GAME + ","
					+ Game.COLUMN_ORDER_TYPE + ") VALUES (" + gameId + ", '"
					+ game.getName() + "', '" + game.getTypeGame() + "',"
					+ game.getGoalGame() + ",'" + game.getOrderType() + "')";

			dbInsert.execSQL(sqlInsert);

			dbInsert.close();

		}

	}

	public void deleteGroup(Integer groupId) {

		SQLiteDatabase dbDelete = getWritableDatabase();

		String deleteGroup = "DELETE FROM " + Group.TABLE_NAME + " WHERE "
				+ Group.COLUMN_ID + " = " + groupId;

		String deletePlayers = "DELETE FROM " + Player.TABLE_NAME + " WHERE "
				+ Player.COLUMN_GROUP_ID + " = " + groupId;

		String deleteRecord = "DELETE FROM " + Record.TABLE_NAME + " WHERE "
				+ Record.COLUMN_GROUP_ID + " = " + groupId;

		dbDelete.execSQL(deleteGroup);
		dbDelete.execSQL(deletePlayers);
		dbDelete.execSQL(deleteRecord);

		dbDelete.close();

	}

	public ArrayList<Record> loadGroupRecord(Integer groupId, Integer gameId) {

		ArrayList<Record> listRecords = new ArrayList<Record>();

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sqlRecords = "SELECT " + Record.COLUMN_ID + ", "
				+ Record.COLUMN_GROUP_ID + "," + Record.COLUMN_GAME_ID + ","
				+ Record.COLUMN_PLAYER_NAME + "," + Record.COLUMN_TYPE + ","
				+ Record.COLUMN_VALUE + " FROM " + Record.TABLE_NAME
				+ " WHERE " + Record.COLUMN_GROUP_ID + " = " + groupId
				+ " AND " + Record.COLUMN_GAME_ID + " = " + gameId;

		Cursor cursorRecords = dbSelect.rawQuery(sqlRecords, null);

		if (cursorRecords.moveToFirst()) {

			do {

				Record record = new Record();

				record.setId(cursorRecords.getInt(0));
				record.setGroupId(cursorRecords.getInt(1));
				record.setGameId(cursorRecords.getInt(2));
				record.setPlayerName(cursorRecords.getString(3));
				record.setType(cursorRecords.getString(4));
				record.setValue(cursorRecords.getInt(5));

				listRecords.add(record);

			} while (cursorRecords.moveToNext());

		}

		return listRecords;

	}

	public void saveRecord(Record record) {

		SQLiteDatabase dbDelete = getWritableDatabase();

		String deleteRecord = "DELETE FROM " + Record.TABLE_NAME + " WHERE "
				+ Record.COLUMN_GROUP_ID + " = " + record.getGroupId()
				+ " AND " + Record.COLUMN_GAME_ID + " = " + record.getGameId()
				+ " AND " + Record.COLUMN_TYPE + " = '" + record.getType()
				+ "'";

		dbDelete.execSQL(deleteRecord);

		dbDelete.close();

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sqlMaxContRecords = "SELECT max(" + Record.COLUMN_ID
				+ ") id FROM " + Record.TABLE_NAME;

		Cursor cursorMaxContRecords = dbSelect
				.rawQuery(sqlMaxContRecords, null);

		Integer recordId = 1;

		if (cursorMaxContRecords.moveToFirst()) {

			recordId = cursorMaxContRecords.getInt(0);

			do {
				recordId = cursorMaxContRecords.getInt(0);
			} while (cursorMaxContRecords.moveToNext());

			if (recordId == null) {
				recordId = 1;
			} else {
				recordId += 1;
			}

		}

		dbSelect.close();

		SQLiteDatabase dbInsert = getWritableDatabase();

		if (dbInsert != null) {

			String sqlInsert = "INSERT INTO " + Record.TABLE_NAME + " ("
					+ Record.COLUMN_ID + "," + Record.COLUMN_GROUP_ID + ", "
					+ Record.COLUMN_GAME_ID + "," + Record.COLUMN_PLAYER_NAME
					+ "," + Record.COLUMN_TYPE + ", " + Record.COLUMN_VALUE
					+ ") VALUES (" + recordId + ", " + record.getGroupId()
					+ ", '" + record.getGameId() + "','"
					+ record.getPlayerName() + "','" + record.getType() + "',"
					+ record.getValue() + ")";

			dbInsert.execSQL(sqlInsert);
			dbInsert.close();

		}

	}

	public Group saveGroup(String groupName, ArrayList<String> listPlayersNames) {

		SQLiteDatabase dbSelect = getReadableDatabase();

		String sqlMaxContGroups = "SELECT max(" + Group.COLUMN_ID
				+ ") id FROM " + Group.TABLE_NAME;

		Cursor cursorMaxContGroups = dbSelect.rawQuery(sqlMaxContGroups, null);

		Integer groupId = 1;

		if (cursorMaxContGroups.moveToFirst()) {

			groupId = cursorMaxContGroups.getInt(0);

			do {
				groupId = cursorMaxContGroups.getInt(0);
			} while (cursorMaxContGroups.moveToNext());

			if (groupId == null) {
				groupId = 1;
			} else {
				groupId += 1;
			}

		}

		SQLiteDatabase dbInsert = getWritableDatabase();

		Group group = new Group(groupName);

		group.setSelected(Boolean.TRUE);
		group.setId(groupId);

		if (dbInsert != null) {

			String sqlInsert = "INSERT INTO " + Group.TABLE_NAME + " ("
					+ Group.COLUMN_ID + "," + Group.COLUMN_NAME + ") VALUES ("
					+ groupId + ",'" + groupName + "')";

			dbInsert.execSQL(sqlInsert);

		}

		if (!AppsUtils.isEmpty(listPlayersNames)) {

			String sqlMaxContPlayers = "SELECT max(" + Player.COLUMN_PLAYER_ID
					+ ") id FROM " + Player.TABLE_NAME;

			Cursor cursorMaxContPlayers = dbSelect.rawQuery(sqlMaxContPlayers,
					null);

			Integer playerId = 1;

			if (cursorMaxContPlayers.moveToFirst()) {

				playerId = cursorMaxContPlayers.getInt(0);

				do {
					playerId = cursorMaxContPlayers.getInt(0);
				} while (cursorMaxContPlayers.moveToNext());

				if (playerId == null) {
					playerId = 1;
				} else {
					playerId += 1;
				}

			}

			Iterator<String> iterPlayersName = listPlayersNames.iterator();

			SQLiteDatabase dbInsertPlayers = getWritableDatabase();

			while (iterPlayersName.hasNext()) {
				savePlayer(playerId, groupId, iterPlayersName.next(),
						dbInsertPlayers);
				playerId++;
			}

			dbInsertPlayers.close();

		}

		return group;

	}

	private void savePlayer(Integer playerId, Integer groupId,
			String namePlayer, SQLiteDatabase dbInsertPlayers) {

		String sqlInsert = "INSERT INTO " + Player.TABLE_NAME + " ("
				+ Player.COLUMN_PLAYER_ID + "," + Player.COLUMN_GROUP_ID + ","
				+ Player.COLUMN_NAME + ") VALUES (" + playerId + "," + groupId
				+ ",'" + namePlayer + "')";

		dbInsertPlayers.execSQL(sqlInsert);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// CREATE TABLES
		db.execSQL(Game.CREATE_TABLE_GAME);
		db.execSQL(Group.CREATE_TABLE_GROUP);
		db.execSQL(Player.CREATE_TABLE_PLAYER);
		db.execSQL(Record.CREATE_TABLE_RECORD);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
