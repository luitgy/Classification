package com.classification.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class LoadItemActivity extends AppsAbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_item);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, MainActivity.class);

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

			Intent intent = new Intent(this, MainActivity.class);

			startActivity(intent);
			finish();

		}

		return Boolean.TRUE;

	}

	@Override
	protected void initControls() {
	}

	@Override
	protected void initAction() {
	}

	@Override
	protected void initValues() {

		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar()
				.setTitle(
						AppsGuiUtils
								.getTitleSpannable(getString(R.string.title_activity_load_item)));

	}

}
