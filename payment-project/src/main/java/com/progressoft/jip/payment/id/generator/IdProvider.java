package com.progressoft.jip.payment.id.generator;

public interface IdProvider {

	long getId(String tableName);
}
