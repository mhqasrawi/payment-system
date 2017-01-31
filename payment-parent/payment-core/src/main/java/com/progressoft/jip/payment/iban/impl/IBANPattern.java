package com.progressoft.jip.payment.iban.impl;

import java.util.Iterator;
import java.util.LinkedList;

public class IBANPattern {

    private LinkedList<IBANSequencePair> patternSet = new LinkedList<>();
    private String countryCode;
    private int ibanLength;

    public int getIbanLength() {
        return ibanLength;
    }

    public void setIbanLength(int ibanLength) {
        this.ibanLength = ibanLength;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void addSubset(IBANPatternType type, int patternLength) {
        patternSet.add(new IBANSequencePair(type, patternLength));
    }

    public Iterator<IBANSequencePair> getPatterns() {
        return patternSet.iterator();
    }

    public enum IBANPatternType {
        NUMERIC, ALPHABETIC, ALPHANUMERIC;
    }

    public class IBANSequencePair {
        private IBANPatternType patternType;
        private int patternLength;

        public IBANSequencePair(IBANPatternType pattern, int length) {
            this.patternType = pattern;
            this.patternLength = length;
        }

        public IBANPatternType getType() {
            return patternType;
        }

        public void setType(IBANPatternType type) {
            this.patternType = type;
        }

        public int getLength() {
            return patternLength;
        }

        public void setLength(int length) {
            this.patternLength = length;
        }
    }
}