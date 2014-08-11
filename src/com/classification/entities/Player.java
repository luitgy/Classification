package com.classification.entities;

import java.util.ArrayList;

import com.commons.database.entity.AppsAbstractEntity;

public class Player implements AppsAbstractEntity {

	public static String CREATE_TABLE_PLAYER = "CREATE TABLE "
			+ Player.TABLE_NAME + " (" + Player.COLUMN_PLAYER_ID
			+ " NUMBER(10), " + Player.COLUMN_GROUP_ID + " NUMBER(10), "
			+ Player.COLUMN_NAME + " VARCHAR(10))";

	public static final String TABLE_NAME = "players";
	public static final String COLUMN_PLAYER_ID = "player_id";
	public static final String COLUMN_GROUP_ID = "group_id";
	public static final String COLUMN_NAME = "name";

	private Integer id;
	private Integer groupId;
	private Integer position;
	private String name;
	private Integer score;
	private Integer roundScore;
	private Integer minScoreGame;
	private Integer maxScoreGame;
	private Integer averageScoreGame;

	public Player(String name) {
		this.name = name;
		this.position = 1;
		this.score = 0;
		this.minScoreGame = null;
		this.maxScoreGame = null;
		this.averageScoreGame = null;
	}

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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRoundScore() {
		return roundScore;
	}

	public void setRoundScore(Integer roundScore) {
		this.roundScore = roundScore;
	}

	public Integer getMinScoreGame() {
		return minScoreGame;
	}

	public void setMinScoreGame(Integer minScoreGame) {
		this.minScoreGame = minScoreGame;
	}

	public Integer getMaxScoreGame() {
		return maxScoreGame;
	}

	public void setMaxScoreGame(Integer maxScoreGame) {
		this.maxScoreGame = maxScoreGame;
	}

	public Integer getAverageScoreGame() {
		return averageScoreGame;
	}

	public void setAverageScoreGame(Integer averageScoreGame) {
		this.averageScoreGame = averageScoreGame;
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
		columns.add(COLUMN_PLAYER_ID);
		columns.add(COLUMN_GROUP_ID);
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
		values.add(getGroupId());
		values.add(getName());

		return values;

	}

}
