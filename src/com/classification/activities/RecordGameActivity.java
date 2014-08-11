package com.classification.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.R.layout;
import com.classification.R.string;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;

public class RecordGameActivity extends AppsAbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_game);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, RecordActivity.class);

			startActivity(intent);
			finish();

			return Boolean.TRUE;

		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			Intent intent = new Intent(this, RecordActivity.class);

			startActivity(intent);
			finish();

		}

		return Boolean.TRUE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.activity.AppsAbstractActivity#initControls()
	 */
	@Override
	protected void initControls() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.activity.AppsAbstractActivity#initAction()
	 */
	@Override
	protected void initAction() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.activity.AppsAbstractActivity#initValues()
	 */
	@Override
	protected void initValues() {

		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar()
				.setTitle(
						AppsGuiUtils
								.getTitleSpannable(getString(R.string.title_activity_record_game)));

	}

}
