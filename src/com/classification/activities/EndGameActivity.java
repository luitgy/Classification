package com.classification.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.data.DataBaseController;
import com.classification.entities.Player;
import com.classification.entities.Record;
import com.classification.util.Constants;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;
import com.commons.util.AppsUtils;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EndGameActivity extends AppsAbstractActivity {

	private ListView listViewClassification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		SubMenu subMenu1 = menu
				.addSubMenu(getString(R.string.icon_more_options));

		subMenu1.setIcon(getResources().getDrawable(
				R.drawable.ic_action_overflow));

		subMenu1.add(R.string.moreOptions, Constants.MENU_RESET_GAME, 0,
				getString(R.string.resetGame));

		MenuItem subMenu1Item = subMenu1.getItem();

		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return Boolean.TRUE;

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

		} else if (item.getItemId() == R.id.menuResetGame) {

			((GlobalApp) getApplicationContext()).resetGame();

			Intent intResetGame = new Intent(this, CardsGameActivity.class);

			startActivity(intResetGame);
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

		listViewClassification = (ListView) findViewById(R.id.listViewClassificationEndGame);

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
								.getTitleSpannable(getString(R.string.title_activity_end_game)));

		ArrayList<Player> listPlayers = ((GlobalApp) getApplicationContext())
				.getListPlayers();

		Integer maxRecord = ((GlobalApp) getApplicationContext())
				.getMaxRecord();
		Integer minRecord = ((GlobalApp) getApplicationContext())
				.getMinRecord();

		String orderGame = ((GlobalApp) getApplicationContext()).getGame()
				.getOrderType();

		saveRecord(listPlayers, maxRecord, minRecord, orderGame);

		listViewClassification.setAdapter(new AdapterList(this, listPlayers,
				maxRecord, minRecord, orderGame));

	}

	private void saveRecord(ArrayList<Player> listPlayers, Integer maxRecord,
			Integer minRecord, String orderGame) {

		Integer maxScore = listPlayers.get(0).getScore();
		Integer minScore = listPlayers.get(listPlayers.size() - 1).getScore();

		if (orderGame.equals(Constants.ID_ORDER_ASCENDEND)) {

			if (maxRecord == null || (maxScore > maxRecord)) {

				DataBaseController dbController = new DataBaseController(this);

				Record record = new Record();

				record.setGroupId(((GlobalApp) getApplicationContext())
						.getGroup().getId());
				record.setGameId(((GlobalApp) getApplicationContext())
						.getGame().getId());
				record.setPlayerName(listPlayers.get(0).getName());
				record.setType(Constants.MAX_RECORD_VALUE);
				record.setValue(maxScore);

				dbController.saveRecord(record);

			}

			if (minRecord == null || (minScore < minRecord)) {

				DataBaseController dbController = new DataBaseController(this);

				Record record = new Record();

				record.setGroupId(((GlobalApp) getApplicationContext())
						.getGroup().getId());
				record.setGameId(((GlobalApp) getApplicationContext())
						.getGame().getId());
				record.setPlayerName(listPlayers.get(listPlayers.size() - 1)
						.getName());
				record.setType(Constants.MIN_RECORD_VALUE);
				record.setValue(minScore);

				dbController.saveRecord(record);

			}

		} else {

			if (maxRecord == null || (maxScore < maxRecord)) {

				DataBaseController dbController = new DataBaseController(this);

				Record record = new Record();

				record.setGroupId(((GlobalApp) getApplicationContext())
						.getGroup().getId());
				record.setGameId(((GlobalApp) getApplicationContext())
						.getGame().getId());
				record.setPlayerName(listPlayers.get(0).getName());
				record.setType(Constants.MAX_RECORD_VALUE);
				record.setValue(maxScore);

				dbController.saveRecord(record);

			}

			if (minRecord == null || (minScore > minRecord)) {

				DataBaseController dbController = new DataBaseController(this);

				Record record = new Record();

				record.setGroupId(((GlobalApp) getApplicationContext())
						.getGroup().getId());
				record.setGameId(((GlobalApp) getApplicationContext())
						.getGame().getId());
				record.setPlayerName(listPlayers.get(listPlayers.size() - 1)
						.getName());
				record.setType(Constants.MIN_RECORD_VALUE);
				record.setValue(minScore);

				dbController.saveRecord(record);

			}

		}

	}

	private static class AdapterList extends BaseAdapter {

		private Context context;
		private LayoutInflater mInflater;
		private ArrayList<Player> listPlayers;
		private Integer minRecord;
		private Integer maxRecord;
		private String order;

		public AdapterList(Context context, ArrayList<Player> listPlayers,
				Integer maxRecord, Integer minRecord, String order) {
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			this.listPlayers = listPlayers;
			this.minRecord = minRecord;
			this.maxRecord = maxRecord;
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

			TextView lblPosition;
			TextView lblName;
			TextView lblScore;
			TextView lblRecord;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.cell_end_classification, null);
			}

			lblPosition = (TextView) convertView.findViewById(R.id.lblPosition);
			lblName = (TextView) convertView.findViewById(R.id.lblName);
			lblScore = (TextView) convertView.findViewById(R.id.lblScore);
			lblRecord = (TextView) convertView
					.findViewById(R.id.lblAchievedRecord);

			Player player = listPlayers.get(position);

			lblPosition.setText(player.getPosition().toString());
			lblName.setText(player.getName());
			lblScore.setText(player.getScore().toString());

			if (order.equals(Constants.ID_ORDER_ASCENDEND)) {
				if ((position == 0 && (maxRecord == null || (player.getScore() > maxRecord)))
						|| (position == (listPlayers.size() - 1) && (minRecord == null || (player
								.getScore() < minRecord)))) {
					lblRecord.setText(context.getString(R.string.newRecord));
				} else {
					lblRecord.setText(Constants.EMPTY_STRING);
				}
			} else {
				if ((position == 0 && (maxRecord == null || (player.getScore() < maxRecord)))
						|| (position == (listPlayers.size() - 1) && (minRecord == null || (player
								.getScore() > minRecord)))) {
					lblRecord.setText(context.getString(R.string.newRecord));
				} else {
					lblRecord.setText(Constants.EMPTY_STRING);
				}
			}

			return convertView;

		}

	}

}
