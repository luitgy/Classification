package com.classification.entities;

import java.util.ArrayList;

import com.commons.database.entity.AppsAbstractEntity;

/**
 * @author Lluis Alonso Asc—n
 * 
 * @date 26/07/2014
 */
public class Record implements AppsAbstractEntity {

	public static String CREATE_TABLE_RECORD = "CREATE TABLE "
			+ Record.TABLE_NAME + " (" + Record.COLUMN_ID + " INTEGER, "
			+ Record.COLUMN_GROUP_ID + " INTEGER, " + Record.COLUMN_GAME_ID
			+ " TEXT, " + Record.COLUMN_PLAYER_NAME + " TEXT, "
			+ Record.COLUMN_TYPE + " TEXT, " + Record.COLUMN_VALUE
			+ " INTEGER)";

	public static final String TABLE_NAME = "records";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_GROUP_ID = "group_id";
	public static final String COLUMN_GAME_ID = "game_id";
	public static final String COLUMN_PLAYER_NAME = "player_name";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_VALUE = "value";

	private Integer id;
	private Integer groupId;
	private Integer gameId;
	private String playerName;
	private String type;
	private Integer value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.database.entity.AppsAbstractEntity#getTable()
	 */
	@Override
	public String getTable() {
		return TABLE_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.database.entity.AppsAbstractEntity#getColumns()
	 */
	@Override
	public ArrayList<String> getColumns() {

		ArrayList<String> columns = new ArrayList<String>();
		columns.add(COLUMN_ID);
		columns.add(COLUMN_GROUP_ID);
		columns.add(COLUMN_GAME_ID);
		columns.add(COLUMN_PLAYER_NAME);
		columns.add(COLUMN_TYPE);
		columns.add(COLUMN_VALUE);

		return columns;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.database.entity.AppsAbstractEntity#getValues()
	 */
	@Override
	public ArrayList<Object> getValues() {

		ArrayList<Object> values = new ArrayList<Object>();

		values.add(getId());
		values.add(getGroupId());
		values.add(getGameId());
		values.add(getPlayerName());
		values.add(getType());
		values.add(getValue());

		return values;

	}

}
