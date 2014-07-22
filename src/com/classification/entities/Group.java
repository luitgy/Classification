package com.classification.entities;

public class Group {

	public static String CREATE_TABLE_PLAYER = "CREATE TABLE "
			+ Group.TABLE_NAME + " (" + Group.COLUMN_ID + " NUMBER(10), "
			+ Player.COLUMN_NAME + " VARCHAR(10))";

	public static final String TABLE_NAME = "groups";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";

	private Integer id;
	private String name;

	public Group(String name) {
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

}
