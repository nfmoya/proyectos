package com.bbva.cfs.commons.utils;

public class FlagEntityIndetifier {

	private final String id;

	public FlagEntityIndetifier(String id) {
		this.id = id;
	}

	public static final FlagEntityIndetifier SCHOLARSHIP_HOLDERS = new FlagEntityIndetifier(
			String.valueOf("BECARIOS"));

	public static final FlagEntityIndetifier RECHARGES = new FlagEntityIndetifier(String
			.valueOf("RECARGAS"));

	public String getId() {
		return id;
	}
}
