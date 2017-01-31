package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.report.core.ReportException;

/**
 * Created by u624 on 1/26/2017.
 */
@FunctionalInterface
public interface WriteOperation {
    public final class DefaultWriteOperations {
        public static final WriteOperation noOp = () -> {
            /* a no operation */
        };

        private DefaultWriteOperations() {
            /* inner class for constants */
        }
    }

    void execute() throws ReportException;
}
