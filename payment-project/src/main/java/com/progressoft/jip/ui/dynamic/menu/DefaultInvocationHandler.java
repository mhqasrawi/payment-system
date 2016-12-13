package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.ui.menu.MenuContext;

/**
 * @author Ahmad.Jardat
 *
 */
public class DefaultInvocationHandler<C extends MenuContext,T> implements InvocationHandler{
	
	private Map<String, Object> values = new HashMap<>();
	private C menuContext ;
	private DefaultValueProvider<C,T>  defaultObjectProvider;

	public DefaultInvocationHandler(Map<String, Object> values,C menuContext,DefaultValueProvider<C,T>  defaultObjectProvider){
		this.values = values;
		this.menuContext = menuContext;
		this.defaultObjectProvider = defaultObjectProvider;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String variableName = getVariableName(method);
		Object value = values.get(variableName);
		if (value != null) {
			return value;
		} else if (defaultObjectProvider != null) {
			return method.invoke(defaultObjectProvider.defaultValue(menuContext), args);
		}
		if(method.getReturnType().isPrimitive()){
			return PrimitiveDefaultsUtility.getDefaultValue(method.getReturnType());
		}
		return null;
	}


	protected String getVariableName(Method method) {
		String name = method.getName();
		char firstChar = Character.toLowerCase(name.charAt(3));
		String variableName = firstChar + name.substring(4);
		return variableName;
	}


}
