package com.classification.entities;

import java.util.ArrayList;

import com.commons.database.entity.AppsAbstractEntity;

public class Game implements AppsAbstractEntity {

	public static String CREATE_TABLE_GAME = "CREATE TABLE " + Game.TABLE_NAME
			+ " (" + Game.COLUMN_ID + " INTEGER, " + Game.COLUMN_NAME
			+ " TEXT, " + Game.COLUMN_TYPE_GAME + " TEXT, "
			+ Game.COLUMN_GOAL_GAME + " INTEGER, " + Game.COLUMN_ORDER_TYPE
			+ " TEXT)";

	public static final String TABLE_NAME = "games";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TYPE_GAME = "type_game";
	public static final String COLUMN_GOAL_GAME = "goal_game";
	public static final String COLUMN_ORDER_TYPE = "order_type";

	private Integer id;
	private String name;
	private String typeGame;
	private Integer goalGame;
	private String orderType;
	private boolean selected = Boolean.FALSE;

	public Game() {
	}

	public Game(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeGame() {
		return typeGame;
	}

	public void setTypeGame(String typeGame) {
		this.typeGame = typeGame;
	}

	public Integer getGoalGame() {
		return goalGame;
	}

	public void setGoalGame(Integer goalGame) {
		this.goalGame = goalGame;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
		columns.add(COLUMN_NAME);

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
		values.add(getName());

		return values;

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

}
