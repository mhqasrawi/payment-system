package com.progressoft.jip.ui.web.form.parameter;

public class ParameterValueTuple {

	private final String value;
	private final String parameter;

	public ParameterValueTuple(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getParameterName() {
		return parameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParameterValueTuple other = (ParameterValueTuple) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParameterValueTuple [value=" + value + ", parameter=" + parameter + "]";
	}

}
