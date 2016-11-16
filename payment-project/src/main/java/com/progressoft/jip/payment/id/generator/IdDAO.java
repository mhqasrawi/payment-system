package com.progressoft.jip.payment.id.generator;

public interface IdDAO {

	int update(String tableName);

	long insert(String tableName);

	long getMaxId(String tableName);

}
