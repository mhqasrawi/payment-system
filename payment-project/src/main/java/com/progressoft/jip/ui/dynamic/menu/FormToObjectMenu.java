package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.ShowFormAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;

public class FormToObjectMenu<MENU_CONTEXT extends MenuContext, INTERFACE_TYPE> extends MenuImpl<MENU_CONTEXT> {

	FormToObjectMenu(String description,List<Menu<MENU_CONTEXT>> subMenu, Form form, Class<?> interfaceClass,
			ObjectProcessingStrategy<MENU_CONTEXT,INTERFACE_TYPE> defaultObjectProvider) {
		super(description,subMenu,
				new FormToObjectAction<MENU_CONTEXT, INTERFACE_TYPE>(form, interfaceClass, defaultObjectProvider));
	}

	FormToObjectMenu(String description,List<Menu<MENU_CONTEXT>> subMenu,  Form form, Class<?> interfaceClass,
			ObjectProcessingStrategy<MENU_CONTEXT,INTERFACE_TYPE> objectProssingStratege, Function<MENU_CONTEXT,INTERFACE_TYPE>  defaultObjectProvider) {
		super(description,subMenu,
				new FormToObjectAction<MENU_CONTEXT, INTERFACE_TYPE>(form, interfaceClass, objectProssingStratege)
						.setDefaultObjectStrategy(defaultObjectProvider));
	}

	static class FormToObjectAction<MENU_CONTEXT extends MenuContext, INTERFACE_TYPE> implements Action<MENU_CONTEXT> {

		private Map<String, Object> values = new HashMap<>();
		private Form form;
		private Class<?> interfaceClass;
		private ObjectProcessingStrategy<MENU_CONTEXT, INTERFACE_TYPE> objectProssingStratege;
		private Function<MENU_CONTEXT,INTERFACE_TYPE>  defaultObjectProvider;

		public FormToObjectAction(Form form, Class<?> interfaceClass,
				ObjectProcessingStrategy<MENU_CONTEXT, INTERFACE_TYPE> objectProssingStratege) {
			this.form = form;
			this.interfaceClass = interfaceClass;
			this.objectProssingStratege = objectProssingStratege;
		}

		private FormToObjectAction<MENU_CONTEXT, INTERFACE_TYPE> setDefaultObjectStrategy(Function<MENU_CONTEXT,INTERFACE_TYPE> defaultObjectProvider) {
			this.defaultObjectProvider = defaultObjectProvider;
			return this;
		}

		@Override
		public MENU_CONTEXT doAction(MENU_CONTEXT menuContext) {
			(new ShowFormAction<MENU_CONTEXT>(form)).doAction(menuContext);
			for (Field<?> field : form.getAllFields()) {
				values.put(field.getName(), field.getValue());
			}
			@SuppressWarnings("unchecked")
			INTERFACE_TYPE newProxyInstance = ((INTERFACE_TYPE) Proxy.newProxyInstance(this.getClass().getClassLoader(),
					new Class[] { interfaceClass }, new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							String variableName = getVariableName(method);
							Object value = values.get(variableName);
							if (value != null) {
								return value;
							} else if (defaultObjectProvider != null) {
								return method.invoke(defaultObjectProvider.apply(menuContext), args);
							}
							if(method.getReturnType().isPrimitive()){
								return PrimitiveDefaults.getDefaultValue(method.getReturnType());
							}
							return null;
						}

						protected String getVariableName(Method method) {
							String name = method.getName();
							char firstChar = Character.toLowerCase(name.charAt(3));
							String variableName = firstChar + name.substring(4);
							return variableName;
						}

					}));
			objectProssingStratege.process(menuContext, newProxyInstance);
			return menuContext;
		}

	}

}
