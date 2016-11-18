package com.progressoft.jip.ui.field;

public abstract class AbstractField<T> implements Field<T> {

	private String name;
	protected T value;
	private String description;

	/* (non-Javadoc)
	 * @see com.progressoft.jip.Filed#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public AbstractField<T> setName(String name) {
		this.name = name;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.progressoft.jip.Filed#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	abstract public AbstractField<T> setValue(String value) ;
	
	/* (non-Javadoc)
	 * @see com.progressoft.jip.Filed#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	public AbstractField<T> setDescription(String description) {
		this.description = description;
		return this;
	}

	
}
