package com.classification.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.entities.Game;
import com.classification.util.Constants;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewItemActivity extends AppsAbstractActivity {

	private LinearLayout lytGameType;
	private LinearLayout lytOrderType;

	private TextView lblGameType;
	private TextView lblOrderType;

	private EditText txtRounds;
	private EditText txtTopScore;

	private Button btnNext;

	private Game gameActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_item);
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

		lytGameType = (LinearLayout) findViewById(R.id.lytGameType);
		lytOrderType = (LinearLayout) findViewById(R.id.lytOrderType);

		lblGameType = (TextView) findViewById(R.id.lblGameType);
		lblOrderType = (TextView) findViewById(R.id.lblOrderType);

		txtRounds = (EditText) findViewById(R.id.txtRounds);
		txtTopScore = (EditText) findViewById(R.id.txtTopScore);

		btnNext = (Button) findViewById(R.id.btnNext);

	}

	@Override
	protected void initAction() {

		lytGameType.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Game continental = new Game(Constants.ID_GAME_CONTINENTAL);

				continental.setDescription(getString(R.string.continental));
				continental.setEnabledRounds(Boolean.FALSE);
				continental.setEnableTopScore(Boolean.FALSE);
				continental.setRounds(7);
				continental.setTopScore(null);
				continental.setOrderType(Constants.ID_ORDER_FALLING);
				continental.setEnableOrderType(Boolean.FALSE);

				Game dados = new Game(Constants.ID_GAME_DADOS);

				dados.setDescription(getString(R.string.dados));
				dados.setEnabledRounds(Boolean.FALSE);
				dados.setEnableTopScore(Boolean.FALSE);
				dados.setRounds(12);
				dados.setTopScore(null);
				dados.setOrderType(Constants.ID_ORDER_ASCENDEND);
				dados.setEnableOrderType(Boolean.FALSE);

				Game remijio = new Game(Constants.ID_GAME_REMIJIO);

				remijio.setDescription(getString(R.string.remijio));
				remijio.setEnabledRounds(Boolean.FALSE);
				remijio.setEnableTopScore(Boolean.TRUE);
				remijio.setRounds(null);
				remijio.setTopScore(150);
				remijio.setOrderType(Constants.ID_ORDER_FALLING);
				remijio.setEnableOrderType(Boolean.FALSE);

				Game pers = new Game(Constants.ID_GAME_PERSONALIZADO);

				pers.setDescription(getString(R.string.personalised));
				pers.setEnabledRounds(Boolean.TRUE);
				pers.setEnableTopScore(Boolean.TRUE);
				pers.setRounds(7);
				pers.setTopScore(null);
				pers.setOrderType(Constants.ID_ORDER_ASCENDEND);
				pers.setEnableOrderType(Boolean.TRUE);

				final Game[] games = { continental, dados, remijio, pers };

				final String[] items = { getString(R.string.continental),
						getString(R.string.dados), getString(R.string.remijio),
						getString(R.string.personalised) };

				AlertDialog.Builder builderTypeFood = new AlertDialog.Builder(
						view.getContext());

				builderTypeFood.setTitle(getString(R.string.gameType))
						.setItems(items, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int item) {

								gameActive = games[item];

								lblGameType.setText(gameActive.getDescription());

								txtRounds.setEnabled(gameActive
										.isEnabledRounds());
								txtTopScore.setEnabled(gameActive
										.isEnableTopScore());

								if (gameActive.getRounds() != null) {
									txtRounds.setText(gameActive.getRounds()
											.toString());
								} else {
									txtRounds.setText(Constants.EMPTY_STRING);
								}

								if (gameActive.getTopScore() != null) {
									txtTopScore.setText(gameActive
											.getTopScore().toString());
								} else {
									txtTopScore.setText(Constants.EMPTY_STRING);
								}

								if (gameActive.getOrderType().equals(
										Constants.ID_ORDER_ASCENDEND)) {
									lblOrderType
											.setText(getString(R.string.ascendant));
								} else {
									lblOrderType
											.setText(getString(R.string.falling));
								}

								lytOrderType.setEnabled(gameActive
										.isEnableOrderType());

							}

						});

				AlertDialog dialogTypeFood = builderTypeFood.create();
				dialogTypeFood.show();

			}
		});

		lytOrderType.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				final String[] items = { getString(R.string.ascendant),
						getString(R.string.falling) };

				AlertDialog.Builder builderTypeFood = new AlertDialog.Builder(
						view.getContext());

				builderTypeFood.setTitle(getString(R.string.orderType))
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								lblOrderType.setText(items[item]);
							}
						});

				AlertDialog dialogTypeFood = builderTypeFood.create();
				dialogTypeFood.show();

			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				((GlobalApp) getApplicationContext()).setGame(gameActive);

				Intent intSelectPlayers = new Intent(view.getContext(),
						SelectPlayersActivity.class);
				startActivity(intSelectPlayers);

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
								.getTitleSpannable(getString(R.string.title_activity_new_item)));

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		lytOrderType.setEnabled(Boolean.FALSE);

		txtRounds.setEnabled(Boolean.FALSE);
		txtTopScore.setEnabled(Boolean.FALSE);

		txtRounds.setText("7");

		gameActive = new Game(Constants.ID_GAME_CONTINENTAL);

		gameActive.setDescription(getString(R.string.continental));
		gameActive.setEnabledRounds(Boolean.FALSE);
		gameActive.setEnableTopScore(Boolean.FALSE);
		gameActive.setRounds(7);
		gameActive.setTopScore(null);
		gameActive.setOrderType(Constants.ID_ORDER_FALLING);
		gameActive.setEnableOrderType(Boolean.FALSE);

	}

}
