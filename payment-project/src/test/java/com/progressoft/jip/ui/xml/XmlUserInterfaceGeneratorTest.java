package com.progressoft.jip.ui.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.progressoft.jip.ui.action.NullableAction;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;

/**
 * @author Ahmad.Jardat
 *
 */
public class XmlUserInterfaceGeneratorTest {

	@Test
	public void GivenXmlWithMainMenu_WhenCallGenerateUserInterface_ThenUserInterfaceWithOnlyMainMenuWillReturn() {
		byte[] buf = String.join("", "<user-interface>", "<menus><menu>", "<menu-id>main-menu</menu-id>",
				"<description>Choose  Option : </description>", "</menu></menus></user-interface>").getBytes();
		InputStream is = new ByteArrayInputStream(buf);
		XmlUserInterfaceGenerator generator = new XmlUserInterfaceGenerator(is);
		Menu mainMenu = generator.generateUserInterface();
		assertEquals(mainMenu, new MenuImpl<>("Choose  Option : "));
	}

	@Test
	public void GivenXmlWithMainMenuAndAction_WhenCallGenerateUserInterface_ThenUserInterfaceWithActionMainMenuWillReturn() {
		byte[] buf = String
				.join("", "<user-interface>", "<actions>", "<action>", "<action-id>Dummy Action 1</action-id>",
						"<action-class-name>com.progressoft.jip.ui.action.NullableAction</action-class-name>",
						"</action>", "</actions>", "<menus>", "<menu>", "<menu-id>main-menu</menu-id>",
						"<description>Choose  Option : </description>", "<action-id>Dummy Action 1</action-id>",
						"</menu>", "</menus>", "</user-interface>")
				.getBytes();

		InputStream is = new ByteArrayInputStream(buf);
		XmlUserInterfaceGenerator generator = new XmlUserInterfaceGenerator(is);
		Menu mainMenu = generator.generateUserInterface();
		assertEquals(mainMenu, new MenuImpl<>("Choose  Option : ", NullableAction.INSTANCE));

	}

	@Test
	public void GivenXmlWithMainMenuAndSubMenu_WhenCallGenerateUserInterface_ThenUserInterfaceWithMainMenuSubMenuWillReturn() {
		byte[] buf = String.join("", "<user-interface>", "<menus>", "<menu><menu-id>",
				"Pikup Account</menu-id><description>", "Pikup Account</description></menu>", "<menu>",
				"<menu-id>main-menu</menu-id>", "<sub-menus>Pikup Account</sub-menus>",
				"<description>Choose  Option : </description>", "</menu>", "</menus></user-interface>").getBytes();
		InputStream is = new ByteArrayInputStream(buf);
		XmlUserInterfaceGenerator generator = new XmlUserInterfaceGenerator(is);
		Menu mainMenu = generator.generateUserInterface();
		MenuImpl<MenuContext> menuImpl = new MenuImpl<>("Choose  Option : ",
				Arrays.asList(new MenuImpl<>("Pikup Account")));
		assertEquals(mainMenu, menuImpl);
	}

	@Test
	public void GivenXmlWithMainMenuAndSubMenuWithAction_WhenCallGenerateUserInterface_ThenUserInterfaceWithMainMenuSubMenuWithActionWillReturn() {
		byte[] buf = String
				.join("", "<user-interface>", "<actions>", "<action>", "<action-id>Dummy Action 1</action-id>",
						"<action-class-name>com.progressoft.jip.ui.action.NullableAction</action-class-name>",
						"</action>", "</actions>", "<menus>", "<menu><menu-id>", "Pikup Account</menu-id><description>",
						"Pikup Account</description>", "<action-id>Dummy Action 1</action-id>", "</menu>", "<menu>",
						"<menu-id>main-menu</menu-id>", "<sub-menus>Pikup Account</sub-menus>",
						"<description>Choose  Option : </description>", "</menu>", "</menus></user-interface>")
				.getBytes();
		InputStream is = new ByteArrayInputStream(buf);
		XmlUserInterfaceGenerator generator = new XmlUserInterfaceGenerator(is);
		Menu mainMenu = generator.generateUserInterface();
		MenuImpl<MenuContext> menuImpl = new MenuImpl<>("Choose  Option : ",
				Arrays.asList(new MenuImpl<>("Pikup Account",NullableAction.INSTANCE)));
		assertEquals(mainMenu, menuImpl);
	}
}
