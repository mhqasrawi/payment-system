package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.ShowFormAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;

public class FormToObjectMenu<T extends MenuContext,V> extends MenuImpl<T> {

	private FormToObjectMenu(String description, Form form, Class<?> interfaceClass,
			Consumer<V> objectProssingStratege) {
		super( description, new FormToObjectAction<T,V>(form, interfaceClass, objectProssingStratege));
	}

	private static class FormToObjectAction<V extends MenuContext,V2> implements Action<V> {
		
		private Map<String, Object> values = new HashMap<>();
		private Form form;
		private Class<?> interfaceClass;
		private Consumer<V2> objectProssingStratege;

		public FormToObjectAction(Form form, Class<?> interfaceClass, Consumer<V2> objectProssingStratege) {
			this.form = form;
			this.interfaceClass = interfaceClass;
			this.objectProssingStratege = objectProssingStratege;
		}

		@Override
		public V doAction(V menuContext) {
			(new ShowFormAction<V>(form)).doAction(menuContext);
			for (Field<?> field : form.getAllFields()) {
				values.put(field.getName(), field.getValue());
			}
			@SuppressWarnings("unchecked")
			V2 newProxyInstance = ((V2) Proxy.newProxyInstance(this.getClass().getClassLoader(),
					new Class[] { interfaceClass }, new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							String variableName = getVariableName(method);
							Object value = values.get(variableName);
							if (value != null) {
								return value;
							}
							return null;
						}

						private String getVariableName(Method method) {
							String name = method.getName();
							char firstChar = Character.toLowerCase(name.charAt(3));
							String variableName = firstChar + name.substring(4);
							return variableName;
						}

					}));
			objectProssingStratege.accept(newProxyInstance);
			return menuContext;
		}

	}

	public static class FormToObjectBuilder<T extends MenuContext,V> {

		private String description;
		private Form form;
		private Class<?> interfaceClass;
		private Consumer<V> objectProssingStratege;

		

		public FormToObjectBuilder<T,V> setDescription(String description) {
			this.description = description;
			return this;
		}

		public FormToObjectBuilder<T,V> setForm(Form form) {
			this.form = form;
			return this;
		}

		public FormToObjectBuilder<T,V> setInterfaceType(Class<?> interfaceClass) {
			this.interfaceClass = interfaceClass;
			return this;
		}

		public FormToObjectBuilder<T,V> setProcessingStrategy(Consumer<V> objectProssingStratege) {
			this.objectProssingStratege = objectProssingStratege;
			return this;
		}

		public FormToObjectMenu<T,V> build() {
			return new FormToObjectMenu<T,V>(description, form, interfaceClass, objectProssingStratege);
		}
	}

}
