package com.classification.application;

import java.util.ArrayList;
import java.util.Iterator;

import com.classification.entities.Game;
import com.classification.entities.Group;
import com.classification.entities.Player;
import com.commons.util.AppsUtils;

import android.app.Application;

public class GlobalApp extends Application {

	private Game game;
	private Group group;
	private ArrayList<String> listPlayersNames;
	private ArrayList<Player> listPlayers = new ArrayList<Player>();
	private Integer roundGame = 1;
	private Integer maxRecord;
	private Integer minRecord;

	public void resetValues() {

		game = null;
		group = null;
		listPlayersNames = null;
		listPlayers = new ArrayList<Player>();
		roundGame = 1;

	}

	public void resetGame() {
	
		resetPlayersScore();
		
		roundGame = 1;
		
	}

	public void resetPlayersScore() {

		if (!AppsUtils.isEmpty(listPlayers)) {

			Iterator<Player> iterPlayers = listPlayers.iterator();

			while (iterPlayers.hasNext()) {
				Player player = iterPlayers.next();
				player.setScore(0);
				player.setMinScoreGame(null);
				player.setMaxScoreGame(null);
				player.setAverageScoreGame(null);
			}

		}

	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public ArrayList<String> getListPlayersNames() {
		return listPlayersNames;
	}

	public void setListPlayersNames(ArrayList<String> listPlayersNames) {
		this.listPlayersNames = listPlayersNames;
	}

	public ArrayList<Player> getListPlayers() {
		return listPlayers;
	}

	public void setListPlayers(ArrayList<Player> listPlayers) {
		this.listPlayers = listPlayers;
	}

	public Integer getRoundGame() {
		return roundGame;
	}

	public void setRoundGame(Integer roundGame) {
		this.roundGame = roundGame;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Integer getMinRecord() {
		return minRecord;
	}

	public void setMinRecord(Integer minRecord) {
		this.minRecord = minRecord;
	}

}
