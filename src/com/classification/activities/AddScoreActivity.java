package com.classification.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.classification.R;
import com.classification.application.GlobalApp;
import com.classification.entities.Game;
import com.classification.entities.Player;
import com.classification.util.Constants;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsConstants;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddScoreActivity extends AppsAbstractActivity {

	private Button btnSave;

	private ListView listViewScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_score);
		initControls();
		initAction();
		initValues();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(this, CardsGameActivity.class);

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

			Intent intent = new Intent(this, CardsGameActivity.class);

			startActivity(intent);
			finish();

		}

		return Boolean.TRUE;

	}

	@Override
	protected void initControls() {

		btnSave = (Button) findViewById(R.id.btnSave);

		listViewScore = (ListView) findViewById(R.id.listViewScore);

		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button), btnSave);

	}

	@Override
	protected void initAction() {

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				btnSave.setFocusable(Boolean.TRUE);
				btnSave.setFocusableInTouchMode(Boolean.TRUE);
				btnSave.requestFocus();

				Iterator<Player> iterPlayers = ((GlobalApp) getApplicationContext())
						.getListPlayers().iterator();

				boolean correctScore = Boolean.TRUE;

				while (iterPlayers.hasNext()) {

					Player player = iterPlayers.next();

					if (player.getRoundScore() == null) {

						correctScore = Boolean.FALSE;
						AppsGuiUtils.showToastLong(
								view.getContext(),
								getString(R.string.emptyScore) + " "
										+ player.getName());

					}

				}

				if (correctScore) {

					iterPlayers = ((GlobalApp) getApplicationContext())
							.getListPlayers().iterator();

					Integer rounds = ((GlobalApp) getApplicationContext())
							.getRoundGame();

					while (iterPlayers.hasNext()) {

						Player player = iterPlayers.next();

						if (player.getMinScoreGame() == null
								|| (player.getMinScoreGame() > player
										.getRoundScore())) {
							player.setMinScoreGame(player.getRoundScore());
						}

						if (player.getMaxScoreGame() == null
								|| (player.getMaxScoreGame() < player
										.getRoundScore())) {
							player.setMaxScoreGame(player.getRoundScore());
						}

						player.setAverageScoreGame(player.getScore() / rounds);

						player.setScore(player.getScore()
								+ player.getRoundScore());
						player.setRoundScore(null);

					}

					if (!endGameVerify()) {

						((GlobalApp) getApplicationContext())
								.setRoundGame(((GlobalApp) getApplicationContext())
										.getRoundGame() + 1);

						Intent intCardsGame = new Intent(view.getContext(),
								CardsGameActivity.class);
						startActivity(intCardsGame);

						finish();

					} else {

						finallyOrder();

						Intent intEndGame = new Intent(view.getContext(),
								EndGameActivity.class);
						startActivity(intEndGame);

						finish();

					}

				}

			}
		});

	}

	private void finallyOrder() {

		ArrayList<Player> listPlayers = ((GlobalApp) getApplicationContext())
				.getListPlayers();
		final String order = ((GlobalApp) getApplicationContext()).getGame()
				.getOrderType();

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

	private boolean endGameVerify() {

		boolean endGame = Boolean.FALSE;

		Game game = ((GlobalApp) getApplicationContext()).getGame();

		if (game.getTypeGame().equals(Constants.ID_TYPE_GAME_ROUNDS)) {

			Integer roundGame = ((GlobalApp) getApplicationContext())
					.getRoundGame();

			if (roundGame >= game.getGoalGame()) {
				endGame = Boolean.TRUE;
			}

		} else {

			Iterator<Player> iterPlayer = ((GlobalApp) getApplicationContext())
					.getListPlayers().iterator();

			while (iterPlayer.hasNext()) {

				Player player = iterPlayer.next();

				if (player.getScore() >= game.getGoalGame()) {
					endGame = Boolean.TRUE;
					break;
				}

			}

		}

		return endGame;

	}

	@Override
	protected void initValues() {

		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);

		getSupportActionBar()
				.setTitle(
						AppsGuiUtils
								.getTitleSpannable(getString(R.string.title_activity_add_score)));

		listViewScore.setAdapter(new AdapterList(this,
				((GlobalApp) getApplicationContext()).getListPlayers()));

	}

	private static class AdapterList extends BaseAdapter {

		private Context context;
		private LayoutInflater mInflater;
		private ArrayList<Player> listPlayer;

		public AdapterList(Context context, ArrayList<Player> listPlayer) {
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
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

			// TextView lblName;
			final EditText txtScore;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.cell_score, null);
			}

			// lblName = (TextView)
			// convertView.findViewById(R.id.lblPlayerName);
			txtScore = (EditText) convertView.findViewById(R.id.txtScore);

			final Player player = listPlayer.get(position);

			// lblName.setText(player.getName());

			txtScore.setHint(context.getString(R.string.score)
					+ AppsConstants.SPACE + player.getName());

			txtScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {

				@Override
				public void onFocusChange(View view, boolean hasFocus) {

					if (!hasFocus) {

						String score = txtScore.getText().toString();

						if (!AppsUtils.isEmpty(score)) {

							try {
								player.setRoundScore(Integer.parseInt(score));
							} catch (NumberFormatException ex) {

								AppsGuiUtils.showToastLong(context, context
										.getString(R.string.numberFormat));
								player.setRoundScore(null);

							}

						}

					}

				}
			});

			return convertView;

		}

	}

}
