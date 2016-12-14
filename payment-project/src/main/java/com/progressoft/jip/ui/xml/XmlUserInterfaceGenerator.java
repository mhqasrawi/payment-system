package com.progressoft.jip.ui.xml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;
import com.progressoft.jip.ui.xml.element.ActionXml;
import com.progressoft.jip.ui.xml.element.MenuXml;
import com.progressoft.jip.ui.xml.element.UserInterfaceXml;

/**
 * @author Ahmad.Jardat
 *
 */
public class XmlUserInterfaceGenerator<T extends MenuContext> implements UserInterfaceGenerator<T> {

	private static final String MAIN_MENU_ID = "main-menu";
	private final InputStream is;
	private final Map<String, Menu<T>> loadedMenus = new HashMap<>();
	private final Map<String, Action<T>> loadedActions = new HashMap<>();

	public XmlUserInterfaceGenerator(InputStream is) {
		this.is = is;
	}

	public Menu<T> generateUserInterface() {
		try {
			UserInterfaceXml uiXml = readInputStream(is);
			generateActions(uiXml.getActions());
			generateMenus(uiXml.getMenus());
			return getMainMenu();
		} catch (JAXBException e) {
			throw new UserInterfaceParsingException(e);
		}
	}

	private void generateActions(List<ActionXml> actions) {
		if (actions == null)
			return;
		for (ActionXml actionXml : actions) {
			Action<T> generatedAction = generateAction(actionXml);
			loadedActions.put(actionXml.getActionId(), generatedAction);
		}
	}

	private Action<T> generateAction(ActionXml actionXml) {
		try {
			Object newInstance = newActionInstance(actionXml);
			@SuppressWarnings("unchecked")
			Action<T> action = ((Action<T>) newInstance);
			return action;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException
				| NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
			throw new UserInterfaceParsingException(e);
		}
	}

	private Object newActionInstance(ActionXml actionXml) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<?> loadClass = this.getClass().getClassLoader().loadClass(actionXml.getActionClassName());
		Constructor<?> constructor = loadClass.getDeclaredConstructor();
		if (!constructor.isAccessible())
			constructor.setAccessible(true);
		Object newInstance = constructor.newInstance();
		return newInstance;
	}

	private void generateMenus(List<MenuXml> meuns) {
		for (MenuXml menuXml : meuns) {
			Menu<T> generatedMenu = generateNewMenu(menuXml.getMenuId(), menuXml.getDescription(),
					menuXml.getActionId(), menuXml.getSubMenuId());
			loadedMenus.put(menuXml.getMenuId(), generatedMenu);
		}
	}

	private Menu<T> getMainMenu() {
		Menu<T> mainMenu = loadedMenus.get(MAIN_MENU_ID);
		if (mainMenu == null) {
			throw new UserInterfaceParsingException("No Main Menu Defined Main Menu Must Have Id = " + MAIN_MENU_ID);
		}
		return mainMenu;
	}

	private Menu<T> generateNewMenu(String menuId, String description, String actionId, List<String> subMenuId) {
		if (actionId != null) {
			if (subMenuId != null && subMenuId.size() > 0) {
				return new MenuImpl<>(description, getSubMenus(subMenuId), getAction(actionId));
			} else {
				return new MenuImpl<>(description, getAction(actionId));
			}
		} else if (subMenuId != null && subMenuId.size() > 0) {
			return new MenuImpl<>(description, getSubMenus(subMenuId));
		}
		return new MenuImpl<>(description);
	}

	private Action<T> getAction(String actionId) {
		Action<T> action = loadedActions.get(actionId);
		if (action == null)
			throw new UserInterfaceParsingException("No Action With Id " + actionId);
		return action;
	}

	private List<Menu<T>> getSubMenus(List<String> subMenuId) {
		final List<Menu<T>> subMenus = new ArrayList<>();
		subMenuId.forEach((String subMenu) -> {
			Menu<T> menu = loadedMenus.get(subMenu);
			if (menu == null)
				throw new UserInterfaceParsingException("No Menu With Id " + subMenu);
			subMenus.add(menu);
		});
		return subMenus;
	}

	private UserInterfaceXml readInputStream(InputStream is) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(UserInterfaceXml.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		UserInterfaceXml uiXml = (UserInterfaceXml) unmarshaller.unmarshal(is);
		return uiXml;
	}

}