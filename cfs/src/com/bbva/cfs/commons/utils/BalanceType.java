package com.bbva.cfs.commons.utils;

public class BalanceType {

	private final String id;

	public BalanceType(String id) {
		this.id = id;
	}

	public static final BalanceType SURRENDER_EXPENSE = new BalanceType(String.valueOf("I"));
	public static final BalanceType RECHARGE = new BalanceType(String.valueOf("F"));

	public String getId() {
		return id;
	}

}
