package com.classification.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.entities.Player;
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

public class RecordGameActivity extends AppsAbstractActivity {

	private ListView listViewRecordsGame;

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

		listViewRecordsGame = (ListView) findViewById(R.id.listViewRecordsGame);

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

			TextView lblGroupName;
			TextView lblNumberPlayers;
			TextView lblPlayerMaxRecord;
			TextView lblPlayerMinRecord;
			TextView lblValueMaxRecord;
			TextView lblValueMinRecord;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.cell_records_game_list, null);
			}

			lblGroupName = (TextView) convertView
					.findViewById(R.id.lblGroupName);
			lblNumberPlayers = (TextView) convertView
					.findViewById(R.id.lblNumberPlayers);
			lblPlayerMaxRecord = (TextView) convertView
					.findViewById(R.id.lblPlayerMaxRecord);
			lblPlayerMinRecord = (TextView) convertView
					.findViewById(R.id.lblPlayerMinRecord);
			lblValueMaxRecord = (TextView) convertView
					.findViewById(R.id.lblValueMaxRecord);
			lblValueMinRecord = (TextView) convertView
					.findViewById(R.id.lblValueMinRecord);

			Player player = listPlayers.get(position);

			return convertView;

		}

	}

}
