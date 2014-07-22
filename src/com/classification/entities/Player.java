package com.classification.entities;

public class Player {

	public static String CREATE_TABLE_PLAYER = "CREATE TABLE "
			+ Player.TABLE_NAME + " (" + Player.COLUMN_PLAYER_ID
			+ " NUMBER(10), " + Player.COLUMN_GROUP_ID + " NUMBER(10), "
			+ Player.COLUMN_NAME + " VARCHAR(10))";

	public static final String TABLE_NAME = "players";
	public static final String COLUMN_PLAYER_ID = "player_id";
	public static final String COLUMN_GROUP_ID = "group_id";
	public static final String COLUMN_NAME = "name";

	private Integer position;
	private String name;
	private Integer score;
	private Integer roundScore;

	public Player(String name) {
		this.name = name;
		this.position = 1;
		this.score = 0;
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

}
