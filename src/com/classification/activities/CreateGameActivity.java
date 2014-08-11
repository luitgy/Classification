package com.classification.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.data.DataBaseController;
import com.classification.entities.Game;
import com.classification.util.Constants;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsConstants;
import com.commons.util.AppsGuiUtils;
import com.commons.util.AppsUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateGameActivity extends AppsAbstractActivity {

	private String orderTypeSelected = Constants.ID_ORDER_ASCENDEND;
	private String gameTypeSelected = Constants.ID_TyPE_GAME_TOP_SCORE;

	private Button btnCreate;

	private EditText txtNameGroup;
	private EditText txtGoalGame;

	private LinearLayout lytOrderType;
	private LinearLayout lytGameType;

	private TextView lblTypeGame;
	private TextView lblOrderType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, SelectGameActivity.class);

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

			Intent intent = new Intent(this, SelectGameActivity.class);

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

		btnCreate = (Button) findViewById(R.id.btnCreateGame);

		AppsGuiUtils
				.addButtonEffectClick(
						getResources().getColor(R.color.effect_click_button),
						btnCreate);

		txtNameGroup = (EditText) findViewById(R.id.txtGameName);
		txtGoalGame = (EditText) findViewById(R.id.txtGoalGame);

		lytOrderType = (LinearLayout) findViewById(R.id.lytOrderType);
		lytGameType = (LinearLayout) findViewById(R.id.lytGameType);

		lblTypeGame = (TextView) findViewById(R.id.lblGameType);
		lblOrderType = (TextView) findViewById(R.id.lblOrderType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.activity.AppsAbstractActivity#initAction()
	 */
	@Override
	protected void initAction() {

		btnCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				String gameName = txtNameGroup.getText().toString();

				if (AppsUtils.isEmpty(gameName)) {
					AppsGuiUtils.showToastLong(view.getContext(),
							getString(R.string.emptyGameName));
				} else {

					String goalGame = txtGoalGame.getText().toString();

					if (AppsUtils.isEmpty(goalGame)) {
						AppsGuiUtils.showToastLong(view.getContext(),
								getString(R.string.emptyGoalGame_1)
										+ AppsConstants.SPACE
										+ txtGoalGame.getHint().toString()
										+ AppsConstants.SPACE
										+ getString(R.string.emptyGoalGame_2));
					} else {

						DataBaseController dbController = new DataBaseController(
								view.getContext());

						Game game = new Game();

						game.setName(gameName);
						game.setGoalGame(Integer.parseInt(goalGame));
						game.setOrderType(orderTypeSelected);
						game.setTypeGame(gameTypeSelected);

						dbController.saveGame(game);

						Intent intSelectGame = new Intent(view.getContext(),
								SelectGameActivity.class);
						startActivity(intSelectGame);

						finish();

					}

				}

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

								if (item == 0) {
									orderTypeSelected = Constants.ID_ORDER_ASCENDEND;
								} else {
									orderTypeSelected = Constants.ID_ORDER_FALLING;
								}

							}
						});

				AlertDialog dialogTypeFood = builderTypeFood.create();
				dialogTypeFood.show();

			}
		});

		lytGameType.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				final String[] items = { getString(R.string.rounds),
						getString(R.string.topScore) };

				AlertDialog.Builder builderTypeFood = new AlertDialog.Builder(
						view.getContext());

				builderTypeFood.setTitle(getString(R.string.orderType))
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {

								lblTypeGame.setText(items[item]);

								txtGoalGame.setHint(items[item]);

								if (item == 0) {
									gameTypeSelected = Constants.ID_TYPE_GAME_ROUNDS;
								} else {
									gameTypeSelected = Constants.ID_TyPE_GAME_TOP_SCORE;
								}

							}
						});

				AlertDialog dialogTypeFood = builderTypeFood.create();
				dialogTypeFood.show();

			}
		});

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
								.getTitleSpannable(getString(R.string.title_activity_create_game)));

	}

}
