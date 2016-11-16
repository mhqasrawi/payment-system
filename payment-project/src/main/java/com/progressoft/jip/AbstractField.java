package com.progressoft.jip;

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

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.progressoft.jip.Filed#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	abstract public void setValue(String value) ;
	
	/* (non-Javadoc)
	 * @see com.progressoft.jip.Filed#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
