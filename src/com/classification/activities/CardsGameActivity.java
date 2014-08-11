package com.classification.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.data.DataBaseController;
import com.classification.entities.Game;
import com.classification.entities.Player;
import com.classification.entities.Record;
import com.classification.util.Constants;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;
import com.commons.util.AppsUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CardsGameActivity extends AppsAbstractActivity {

	private Context context = this;

	private Game game;

	private TextView lblDescTarget;
	private TextView lblValueTarget;

	private TextView lblPlayerMaxRecord;
	private TextView lblPlayerMinRecord;
	private TextView lblValueMaxRecord;
	private TextView lblValueMinRecord;

	private LinearLayout lytMaxRecord;
	private LinearLayout lytMinRecord;

	private Button btnNextRound;

	private ListView listViewClassification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cards_game);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(getString(R.string.exitGameSure))
					.setTitle(getString(R.string.exit))
					.setPositiveButton(getString(R.string.accept),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent intent = new Intent(context,
											MainActivity.class);

									startActivity(intent);
									finish();

									dialog.cancel();
								}
							})
					.setNegativeButton(getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			builder.create().show();

			return Boolean.TRUE;

		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(getString(R.string.exitGameSure))
					.setTitle(getString(R.string.exit))
					.setPositiveButton(getString(R.string.accept),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent intent = new Intent(context,
											MainActivity.class);

									startActivity(intent);
									finish();

									dialog.cancel();
								}
							})
					.setNegativeButton(getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			builder.create().show();

		}

		return Boolean.TRUE;

	}

	@Override
	protected void initControls() {

		lblDescTarget = (TextView) findViewById(R.id.lblDescTarget);
		lblValueTarget = (TextView) findViewById(R.id.lblValueTarget);

		lblPlayerMaxRecord = (TextView) findViewById(R.id.lblPlayerMaxRecord);
		lblPlayerMinRecord = (TextView) findViewById(R.id.lblPlayerMinRecord);

		lblValueMaxRecord = (TextView) findViewById(R.id.lblValueMaxRecord);
		lblValueMinRecord = (TextView) findViewById(R.id.lblValueMinRecord);

		listViewClassification = (ListView) findViewById(R.id.listViewClassification);

		btnNextRound = (Button) findViewById(R.id.btnNextRound);

		lytMaxRecord = (LinearLayout) findViewById(R.id.lytMaxRecord);
		lytMinRecord = (LinearLayout) findViewById(R.id.lytMinRecord);
		
		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button), btnNextRound);

	}

	@Override
	protected void initAction() {

		btnNextRound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intNextRound = new Intent(view.getContext(),
						AddScoreActivity.class);
				startActivity(intNextRound);

				finish();

			}
		});

	}

	@Override
	protected void initValues() {

		game = ((GlobalApp) getApplicationContext()).getGame();

		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);
		getSupportActionBar().setTitle(
				AppsGuiUtils.getTitleSpannable(game.getName()));

		initInfo();

		initRecords();

		listViewClassification.setAdapter(new AdapterList(this,
				((GlobalApp) getApplicationContext()).getListPlayers(), game
						.getOrderType()));

	}

	private void initRecords() {

		DataBaseController dbController = new DataBaseController(this);

		Integer groupId = ((GlobalApp) getApplicationContext()).getGroup()
				.getId();

		Integer gameId = ((GlobalApp) getApplicationContext()).getGame()
				.getId();

		ArrayList<Record> listRecord = dbController.loadGroupRecord(groupId,
				gameId);

		if (!AppsUtils.isEmpty(listRecord)) {

			Iterator<Record> iterRecord = listRecord.iterator();

			while (iterRecord.hasNext()) {

				Record record = iterRecord.next();

				if (record.getType().equals(Constants.MAX_RECORD_VALUE)) {

					lblPlayerMaxRecord.setText(record.getPlayerName());
					lblValueMaxRecord.setText(record.getValue().toString());

					((GlobalApp) getApplicationContext()).setMaxRecord(record
							.getValue());

				} else if (record.getType().equals(Constants.MIN_RECORD_VALUE)) {

					lblPlayerMinRecord.setText(record.getPlayerName());
					lblValueMinRecord.setText(record.getValue().toString());

					((GlobalApp) getApplicationContext()).setMinRecord(record
							.getValue());

				}

			}

		} else {

			((GlobalApp) getApplicationContext()).setMaxRecord(null);
			((GlobalApp) getApplicationContext()).setMinRecord(null);

			lytMaxRecord.removeAllViews();
			lytMinRecord.removeAllViews();

			TextView lblNoRecords = new TextView(this);

			lblNoRecords.setText(getString(R.string.noRecords));

			lytMaxRecord.addView(lblNoRecords);

		}

	}

	private void initInfo() {

		lblDescTarget.setText(Constants.EMPTY_STRING);
		lblValueTarget.setText(Constants.EMPTY_STRING);

		if (game.getTypeGame().equals(Constants.ID_TYPE_GAME_ROUNDS)) {
			lblDescTarget.setText(getString(R.string.rounds));
			lblValueTarget.setText(((GlobalApp) getApplicationContext())
					.getRoundGame() + "/" + game.getGoalGame().toString());
		} else {
			lblDescTarget.setText(getString(R.string.topScore));
			lblValueTarget.setText(game.getGoalGame().toString());
		}

	}

	private static class AdapterList extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<Player> listPlayers;
		private String order;

		public AdapterList(Context context, ArrayList<Player> listPlayers,
				String order) {
			this.mInflater = LayoutInflater.from(context);
			this.listPlayers = listPlayers;
			this.order = order;
		}

		@Override
		public int getCount() {

			if (!AppsUtils.isEmpty(listPlayers)) {
				return listPlayers.size();
			}

			return 0;

		}

		@Override
		public Object getItem(int position) {

			if (!AppsUtils.isEmpty(listPlayers)) {
				return listPlayers.get(position);
			}

			return null;

		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			preUpdateList();

			TextView lblPosition;
			TextView lblName;
			TextView lblScore;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.cell_classification,
						null);
			}

			lblPosition = (TextView) convertView.findViewById(R.id.lblPosition);
			lblName = (TextView) convertView.findViewById(R.id.lblName);
			lblScore = (TextView) convertView.findViewById(R.id.lblScore);

			Player player = listPlayers.get(position);

			lblPosition.setText(player.getPosition().toString());
			lblName.setText(player.getName());
			lblScore.setText(player.getScore().toString());

			return convertView;

		}

		private void preUpdateList() {

			Collections.sort(listPlayers, new Comparator<Player>() {

				public int compare(Player p1, Player p2) {

					if (order.equals(Constants.ID_ORDER_ASCENDEND)) {

						if (p2.getScore() < p1.getScore()) {
							return -1;
						} else if (p2.getScore() > p1.getScore()) {
							return 1;
						} else {
							return 0;
						}

					} else {

						if (p1.getScore() < p2.getScore()) {
							return -1;
						} else if (p1.getScore() > p2.getScore()) {
							return 1;
						} else {
							return 0;
						}

					}

				}

			});

			if (!AppsUtils.isEmpty(listPlayers)) {

				Iterator<Player> iterPlayer = listPlayers.iterator();

				Integer position = 1;

				while (iterPlayer.hasNext()) {

					Player player = iterPlayer.next();

					player.setPosition(position++);

				}

			}

		}

	}

}
