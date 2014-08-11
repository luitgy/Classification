package com.classification.entities;

import java.util.ArrayList;

import com.commons.database.entity.AppsAbstractEntity;

public class Group implements AppsAbstractEntity {

	public static String CREATE_TABLE_GROUP = "CREATE TABLE "
			+ Group.TABLE_NAME + " (" + Group.COLUMN_ID + " INTEGER, "
			+ Group.COLUMN_NAME + " TEXT)";

	public static final String TABLE_NAME = "groups";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";

	private Integer id;
	private String name;
	private boolean selected = Boolean.FALSE;

	public Group(String name) {
		this.name = name;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

}
