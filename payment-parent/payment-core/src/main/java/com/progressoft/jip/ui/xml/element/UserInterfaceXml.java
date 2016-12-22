package com.progressoft.jip.ui.xml.element;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ahmad.Jardat
 *
 */
@XmlRootElement(name = "user-interface")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInterfaceXml {

	@XmlElementWrapper(name = "menus")
	@XmlElement(name="menu")
	private List<MenuXml> menus;
	
	@XmlElementWrapper(name = "actions")
	@XmlElement(name="action")
	private List<ActionXml> actions;

	public List<MenuXml> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuXml> menus) {
		this.menus = menus;
	}

	public List<ActionXml> getActions() {
		return actions;
	}

	public void setActions(List<ActionXml> actions) {
		this.actions = actions;
	}

}
