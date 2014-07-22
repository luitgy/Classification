package com.classification.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SelectPlayersActivity extends AppsAbstractActivity {

	private Button btnStartGame;
	private Button btnCreateGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_players);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, NewItemActivity.class);

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

			Intent intent = new Intent(this, NewItemActivity.class);

			startActivity(intent);
			finish();

		}

		return Boolean.TRUE;

	}

	@Override
	protected void initControls() {

		btnStartGame = (Button) findViewById(R.id.btnStartGame);
		btnCreateGroup = (Button) findViewById(R.id.btnCreateGroup);

	}

	@Override
	protected void initAction() {

		btnStartGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				((GlobalApp) getApplicationContext()).resetScore();

				((GlobalApp) getApplicationContext()).setRoundGame(1);

				Intent intCardsGame = new Intent(view.getContext(),
						CardsGameActivity.class);
				startActivity(intCardsGame);

				finish();

			}
		});

		btnCreateGroup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intCreateGroup = new Intent(view.getContext(),
						CreateGroupActivity.class);
				startActivity(intCreateGroup);
				finish();

			}
		});

	}

	@Override
	protected void initValues() {

		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar()
				.setTitle(
						AppsGuiUtils
								.getTitleSpannable(getString(R.string.title_activity_select_players)));

	}

}
