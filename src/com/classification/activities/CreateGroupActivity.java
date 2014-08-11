package com.classification.activities;

import java.util.ArrayList;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.data.DataBaseController;
import com.classification.entities.Group;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;
import com.commons.util.AppsUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CreateGroupActivity extends AppsAbstractActivity {

	private Context context = this;

	private Button btnAddPlayer;
	private Button btnSaveGroup;

	private EditText txtGroupName;

	private ListView listViewPlayers;

	private LayoutInflater inflater;
	private AdapterList adapterList;

	private ArrayList<String> listPlayersNames = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, SelectPlayersActivity.class);

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

			Intent intent = new Intent(this, SelectPlayersActivity.class);

			startActivity(intent);
			finish();

		}

		return Boolean.TRUE;

	}

	@Override
	protected void initControls() {

		btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
		btnSaveGroup = (Button) findViewById(R.id.btnSaveGroup);

		txtGroupName = (EditText) findViewById(R.id.txtGroupName);

		listViewPlayers = (ListView) findViewById(R.id.listViewPlayers);

		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button),
				btnAddPlayer);
		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button),
				btnSaveGroup);

	}

	@Override
	protected void initAction() {

		btnAddPlayer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				AlertDialog.Builder builder = new AlertDialog.Builder(view
						.getContext());

				// Cargamos el layout personalizado para el dialogo
				// Pasamos null como padre pues este layout ira en el dialogo
				View layout = inflater
						.inflate(R.layout.dialog_add_player, null);

				// Definimos el EditText para poder acceder a el posteriormente
				final EditText txtNamePlayer = ((EditText) layout
						.findViewById(R.id.txtPlayerName));

				// Substituimos la vista por la que acabamos de cargar.
				builder.setView(layout);
				builder.setTitle(getString(R.string.player));

				builder.setPositiveButton(getString(R.string.accept),
						new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								String name = txtNamePlayer.getText()
										.toString();

								if (AppsUtils.isEmpty(name)) {
									AppsGuiUtils
											.showToastLong(
													context,
													getString(R.string.emptyPlayerName));
								} else if (isDuplicate(name)) {
									AppsGuiUtils
											.showToastLong(
													context,
													getString(R.string.duplicatePlayerName));
								} else {

									listPlayersNames.add(name.trim());
									loadList();

								}

							}
						});
				builder.setNegativeButton(getString(R.string.cancel),
						new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				AlertDialog dialogo = builder.create();
				dialogo.show();

			}
		});

		btnSaveGroup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				if (AppsUtils.isEmpty(txtGroupName.getText().toString())) {
					AppsGuiUtils.showToastLong(view.getContext(),
							getString(R.string.emptyGroupName));
				} else if (AppsUtils.isEmpty(listPlayersNames)
						|| listPlayersNames.size() < 2) {
					AppsGuiUtils.showToastLong(view.getContext(),
							getString(R.string.minimumPLayers));
				} else {

					DataBaseController dbController = new DataBaseController(
							view.getContext());

					Group group = dbController.saveGroup(txtGroupName.getText()
							.toString(), listPlayersNames);

					((GlobalApp) getApplicationContext()).setGroup(group);
					((GlobalApp) getApplicationContext())
							.setListPlayersNames(listPlayersNames);

					Intent intSelectGroup = new Intent(view.getContext(),
							SelectPlayersActivity.class);
					startActivity(intSelectGroup);

					finish();

				}

			}
		});

		listViewPlayers
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, final int index, long arg3) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								view.getContext());

						builder.setTitle(getString(R.string.confirmation));
						builder.setMessage(getString(R.string.deletePlayerSure));

						builder.setPositiveButton(getString(R.string.accept),
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										listPlayersNames.remove(index);

										AppsGuiUtils
												.showToastLong(
														context,
														getString(R.string.deletePlayerOk));

										loadList();
										adapterList.notifyDataSetChanged();

									}
								});

						builder.setNegativeButton(getString(R.string.cancel),
								new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});

						builder.create();
						builder.show();

						return Boolean.TRUE;

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
								.getTitleSpannable(getString(R.string.title_activity_create_group)));

		inflater = getLayoutInflater();

		loadList();

	}

	private void loadList() {

		adapterList = new AdapterList(this, listPlayersNames);

		listViewPlayers.setAdapter(adapterList);
		
	}

	private boolean isDuplicate(String name) {

		boolean duplicate = Boolean.FALSE;

		if (!AppsUtils.isEmpty(listPlayersNames)) {

			Iterator<String> iterNamePlayers = listPlayersNames.iterator();

			while (iterNamePlayers.hasNext()) {

				String tmpName = iterNamePlayers.next();

				if (tmpName.trim().equalsIgnoreCase(name.trim())) {
					duplicate = Boolean.TRUE;
					break;
				}

			}

		}

		return duplicate;

	}

	private static class AdapterList extends BaseAdapter {

		private LayoutInflater mInflater;
		private ArrayList<String> listPlayer;

		public AdapterList(Context context, ArrayList<String> listPlayer) {
			mInflater = LayoutInflater.from(context);
			this.listPlayer = listPlayer;
		}

		@Override
		public int getCount() {

			if (!AppsUtils.isEmpty(listPlayer)) {
				return listPlayer.size();
			}

			return 0;

		}

		@Override
		public Object getItem(int position) {

			if (!AppsUtils.isEmpty(listPlayer)) {
				return listPlayer.get(position);
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

			String namePlayer = listPlayer.get(position);

			lblName.setText(namePlayer);

			lytImageSelected.setVisibility(View.INVISIBLE);

			return convertView;

		}

	}

}
