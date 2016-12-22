package com.progressoft.jip.ui.xml.element;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MenuXml {

	@XmlElement(name="menu-id",required=true)
	private String menuId;

	@XmlElement(required = true)
	private String description;
	
	@XmlElement(name = "action-id")
	private String actionId;
	
	@XmlElement(name = "sub-menus")
	private List<String> subMenuId;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public List<String> getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(List<String> subMenuId) {
		this.subMenuId = subMenuId;
	}
}