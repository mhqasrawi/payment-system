package com.progressoft.jip;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.ui.menu.KeyWithoutValueException;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

public class MenuContextImpl implements PaymentMenuContext {

	private Map<String, Object> values = new HashMap<String, Object>();
	private Deque<Menu<PaymentMenuContext>> menuStack = new LinkedList<>();
	private AccountDTO currentAccount;

	public void put(String key, Object value) {
		values.put(key, value);
	}

	public <T> T get(String key) {
		Object value = values.get(key);
		if (value == null) {
			throw new KeyWithoutValueException(String.format("Key %s have null value", key));
		}
		return (T) value;
	}

	public void pushMenuStack(Menu menu) {
		menuStack.push(menu);
	}

	@Override
	public Menu<PaymentMenuContext> popMenuStack() {
		return menuStack.pop();
	}

	@Override
	public void setCurrentAccount(AccountDTO account) {
		currentAccount = account;
	}

	@Override
	public AccountDTO getCurrentAccount() {
		return currentAccount;
	}
}
