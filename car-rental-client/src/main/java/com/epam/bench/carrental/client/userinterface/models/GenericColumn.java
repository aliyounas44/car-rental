package com.epam.bench.carrental.client.userinterface.models;

import java.util.function.Function;

public class GenericColumn<T> {
    String columnName = null;
    Function<T, Object> function = null;

    public GenericColumn(String name, Function<T, Object> function) {
	this.columnName = name;
	this.function = function;
    }

    public String getColumnName() {
	return columnName;
    }

    public Object getData(T dto) {
	return function.apply(dto);
    }
}
