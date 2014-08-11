package com.classification.activities;

import java.util.ArrayList;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.data.DataBaseController;
import com.classification.entities.Group;
import com.classification.entities.Player;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;
import com.commons.util.AppsUtils;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SelectPlayersActivity extends AppsAbstractActivity {

	private LinearLayout lytNoGroups;

	private Button btnStartGame;
	private Button btnCreateGroup;

	private ListView listViewGroups;

	private ArrayList<Group> listGroup;

	// private Integer groupIdSelected = null;

	private Group groupSelected = null;

	private AdapterList adapterList;

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

	@Override
	protected void initControls() {

		lytNoGroups = (LinearLayout) findViewById(R.id.lytNoGroups);

		btnStartGame = (Button) findViewById(R.id.btnStartGame);
		btnCreateGroup = (Button) findViewById(R.id.btnCreateGroup);

		listViewGroups = (ListView) findViewById(R.id.listViewGroups);

		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button),
				btnStartGame);
		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button),
				btnCreateGroup);

	}

	@Override
	protected void initAction() {

		listViewGroups
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Group group = listGroup.get(position);

						groupSelected = group;

						// groupIdSelected = group.getId();

						group.setSelected(Boolean.TRUE);

						adapterList.notifyDataSetChanged();

						Iterator<Group> iterGroups = listGroup.iterator();

						while (iterGroups.hasNext()) {

							Group tmpGroup = iterGroups.next();

							if (tmpGroup.getId() != group.getId()) {
								tmpGroup.setSelected(Boolean.FALSE);
							}

						}

					}
				});

		listViewGroups
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0,
							final View view, final int index, long arg3) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								view.getContext());

						builder.setMessage(getString(R.string.deleteGroupSure))
								.setTitle(getString(R.string.confirmation))
								.setPositiveButton(getString(R.string.accept),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {

												DataBaseController dbController = new DataBaseController(
														view.getContext());

												dbController
														.deleteGroup(((Group) listGroup
																.get(index))
																.getId());

												listGroup.remove(index);

												adapterList
														.notifyDataSetChanged();

												if (AppsUtils
														.isEmpty(listGroup)) {
													lytNoGroups
															.setVisibility(View.VISIBLE);
												}

												dialog.cancel();

											}
										})
								.setNegativeButton(getString(R.string.cancel),
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						builder.create().show();

						return Boolean.TRUE;

					}
				});

		btnStartGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				if (groupSelected == null) {
					AppsGuiUtils.showToastLong(view.getContext(),
							getString(R.string.groupNoSelected));
				} else {

					DataBaseController dbController = new DataBaseController(
							view.getContext());

					ArrayList<Player> listPlayers = dbController
							.loadGroupPlayers(groupSelected.getId());

					((GlobalApp) getApplicationContext())
							.setListPlayers(listPlayers);

					((GlobalApp) getApplicationContext()).resetPlayersScore();

					((GlobalApp) getApplicationContext()).setRoundGame(1);

					((GlobalApp) getApplicationContext())
							.setGroup(groupSelected);

					Intent intCardsGame = new Intent(view.getContext(),
							CardsGameActivity.class);
					startActivity(intCardsGame);

					finish();

				}

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

		DataBaseController dbController = new DataBaseController(this);

		listGroup = dbController.loadGroups();

		if (!AppsUtils.isEmpty(listGroup)) {

			lytNoGroups.setVisibility(View.INVISIBLE);

			adapterList = new AdapterList(this, listGroup);

			listViewGroups.setAdapter(adapterList);

		}

	}

	private static class AdapterList extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<Group> listGroups;

		public AdapterList(Context context, ArrayList<Group> listGroups) {
			mInflater = LayoutInflater.from(context);
			this.listGroups = listGroups;
		}

		@Override
		public int getCount() {

			if (!AppsUtils.isEmpty(listGroups)) {
				return listGroups.size();
			}

			return 0;

		}

		@Override
		public Object getItem(int position) {

			if (!AppsUtils.isEmpty(listGroups)) {
				return listGroups.get(position);
			}

			return null;

		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			TextView lblName;
			LinearLayout lytImageSelected;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.cell_group_selected,
						null);
			}

			lblName = (TextView) convertView.findViewById(R.id.lblNameGroup);

			lytImageSelected = (LinearLayout) convertView
					.findViewById(R.id.lytImageSelected);

			Group group = listGroups.get(position);

			lblName.setText(group.getName());

			if (group.isSelected()) {
				lytImageSelected.setVisibility(View.VISIBLE);
			} else {
				lytImageSelected.setVisibility(View.INVISIBLE);
			}

			return convertView;

		}

	}

}
