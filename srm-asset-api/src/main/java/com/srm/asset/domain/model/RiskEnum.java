package com.srm.asset.domain.model;

public enum RiskEnum {
	A("A"), B("B"), C("C");

	private final String value;

	RiskEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
