package com.progressoft.jip.ui.xml;

import com.progressoft.jip.ui.xml.element.ActionXml;

/**
 *
 */
@FunctionalInterface
public interface ActionGenerator {

    Object generateAction(ActionXml actionXml);

}
