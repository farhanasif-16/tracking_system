package com.shipping.model;

import java.util.List;
@FunctionalInterface
public interface Filter {
	public List<Object> filter(Integer args);
}
