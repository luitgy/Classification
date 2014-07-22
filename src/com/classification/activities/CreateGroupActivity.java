package com.classification.activities;

import java.util.ArrayList;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CreateGroupActivity extends AppsAbstractActivity {

	private Context context = this;

	private Button btnAddPlayer;
	private Button btnSaveGroup;

	private EditText txtGroupName;

	private ListView listViewPlayers;

	private LayoutInflater inflater;
	private ArrayAdapter<String> listAdapter;

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

					// TODO - GUARDAR GRUPO EN BBDD

					((GlobalApp) getApplicationContext())
							.setGroupName(txtGroupName.getText().toString()
									.trim());
					((GlobalApp) getApplicationContext())
							.setListPlayersNames(listPlayersNames);

					Intent intNewItem = new Intent(view.getContext(),
							NewItemActivity.class);
					startActivity(intNewItem);

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
										listAdapter.notifyDataSetChanged();

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

		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listPlayersNames);

		listViewPlayers.setAdapter(listAdapter);

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

}
