package com.progressoft.jip;

@FunctionalInterface
public interface DateRulesValidationProvider {

    Iterable<IdDescreptionPair> getDateRules();

}
