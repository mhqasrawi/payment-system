package com.progressoft.jip;

import java.util.List;

public interface Menu {

    String getDescription();

    List<Menu> getSubMenu();

    Action getRelatedAction();

}
