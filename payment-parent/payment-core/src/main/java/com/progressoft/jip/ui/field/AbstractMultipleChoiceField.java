package com.progressoft.jip.ui.field;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultipleChoiceField<T> extends AbstractField<T> {

	private List<String> choices = new ArrayList<>();
	private String selectedChoice;

	public String getSelectedChoice() {
		return selectedChoice;
	}

	public void selectedChoice(int choiceNumber) {
		selectedChoice = choices.get(choiceNumber);
	}

	public Iterable<String> getChoices() {
		return choices;
	}

	protected void addChoice(String choice) {
		choices.add(choice);
	}

}
