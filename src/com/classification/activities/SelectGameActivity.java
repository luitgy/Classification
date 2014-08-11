package com.classification.activities;

import java.util.ArrayList;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.data.DataBaseController;
import com.classification.entities.Game;
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

public class SelectGameActivity extends AppsAbstractActivity {

	private Button btnCreate;

	private LinearLayout lytNoGames;

	private ListView listViewGames;

	private ArrayList<Game> listGames;

	private AdapterList adapterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_game);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.commons.activity.AppsAbstractActivity#initControls()
	 */
	@Override
	protected void initControls() {

		lytNoGames = (LinearLayout) findViewById(R.id.lytNoGames);

		listViewGames = (ListView) findViewById(R.id.listViewGames);

		btnCreate = (Button) findViewById(R.id.btnCreateGame);

		AppsGuiUtils
				.addButtonEffectClick(
						getResources().getColor(R.color.effect_click_button),
						btnCreate);

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

				Intent intCreate = new Intent(view.getContext(),
						CreateGameActivity.class);
				startActivity(intCreate);
				finish();

			}
		});

		listViewGames
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Game game = listGames.get(position);

						((GlobalApp) getApplicationContext()).setGame(game);

						Intent intSelectPlayers = new Intent(view.getContext(),
								SelectPlayersActivity.class);
						startActivity(intSelectPlayers);
						finish();

					}
				});

		listViewGames
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
														.deleteGame(((Game) listGames
																.get(index))
																.getId());

												listGames.remove(index);

												adapterList
														.notifyDataSetChanged();

												if (AppsUtils
														.isEmpty(listGames)) {
													lytNoGames
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
								.getTitleSpannable(getString(R.string.title_activity_select_game)));

		DataBaseController dbController = new DataBaseController(this);

		listGames = dbController.loadGames();

		if (!AppsUtils.isEmpty(listGames)) {

			lytNoGames.setVisibility(View.INVISIBLE);

			adapterList = new AdapterList(this, listGames);

			listViewGames.setAdapter(adapterList);

		}

	}

	private static class AdapterList extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<Game> listGames;

		public AdapterList(Context context, ArrayList<Game> listGames) {
			mInflater = LayoutInflater.from(context);
			this.listGames = listGames;
		}

		@Override
		public int getCount() {

			if (!AppsUtils.isEmpty(listGames)) {
				return listGames.size();
			}

			return 0;

		}

		@Override
		public Object getItem(int position) {

			if (!AppsUtils.isEmpty(listGames)) {
				return listGames.get(position);
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

			Game game = listGames.get(position);

			lblName.setText(game.getName());
			lytImageSelected.setVisibility(View.INVISIBLE);

			return convertView;

		}

	}

}
