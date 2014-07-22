package com.classification.application;

import java.util.ArrayList;
import java.util.Iterator;

import com.classification.entities.Game;
import com.classification.entities.Player;
import com.commons.util.AppsUtils;

import android.app.Application;

public class GlobalApp extends Application {

	private Game game;
	private String groupName;
	private ArrayList<String> listPlayersNames;
	private ArrayList<Player> listPlayers = new ArrayList<Player>();
	private Integer roundGame = 1;

	public void resetValues() {

		game = null;
		groupName = null;
		listPlayersNames = null;
		listPlayers = new ArrayList<Player>();
		roundGame = 1;

	}

	public void resetScore() {

		if (!AppsUtils.isEmpty(listPlayers)) {

			Iterator<Player> iterPlayers = listPlayers.iterator();

			while (iterPlayers.hasNext()) {

				Player player = iterPlayers.next();

				player.setScore(0);

			}

		}

	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
