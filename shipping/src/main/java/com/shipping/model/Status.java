package com.shipping.model;

public enum Status {
	ordered,shipped,intransit,delivered,returned,recieved;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
