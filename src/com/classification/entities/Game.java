package com.classification.entities;

public class Game {

	private String id;
	private String description;
	private Integer rounds;
	private Integer topScore;
	private String orderType;
	private boolean enabledRounds = Boolean.TRUE;
	private boolean enableTopScore = Boolean.TRUE;
	private boolean enableOrderType = Boolean.TRUE;

	public Game(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRounds() {
		return rounds;
	}

	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}

	public Integer getTopScore() {
		return topScore;
	}

	public void setTopScore(Integer topScore) {
		this.topScore = topScore;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean isEnabledRounds() {
		return enabledRounds;
	}

	public void setEnabledRounds(boolean enabledRounds) {
		this.enabledRounds = enabledRounds;
	}

	public boolean isEnableTopScore() {
		return enableTopScore;
	}

	public void setEnableTopScore(boolean enableTopScore) {
		this.enableTopScore = enableTopScore;
	}

	public boolean isEnableOrderType() {
		return enableOrderType;
	}

	public void setEnableOrderType(boolean enableOrderType) {
		this.enableOrderType = enableOrderType;
	}

}
