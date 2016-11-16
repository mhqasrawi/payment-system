package com.progressoft.jip.payment.id.generator;

public interface IdDAO {

	int update(String tableName);

	void insert(String tableName);

	long getMaxId(String tableName);

}
