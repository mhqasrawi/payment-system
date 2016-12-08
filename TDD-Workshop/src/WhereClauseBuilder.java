import jdk.nashorn.internal.objects.annotations.Where;
import org.omg.SendingContext.RunTime;

import java.util.Objects;

/**
 * Created by mhqasrawi on 07/12/16.
 */
public class WhereClauseBuilder {
    private String whereClause = "";

    public String build() {
        return whereClause;
    }

    public WhereClauseBuilder addFilter(String property, String operator, String value) {
        if (Objects.isNull(property) || property.isEmpty() || property.trim().isEmpty())
            throw new InvalidPropertyException();
        if (Objects.isNull(operator))
            throw new InvalidOperatorException();
        whereClause = property + operator + "'" + value + "'";
        return this;
    }

    class InvalidPropertyException extends RuntimeException {

    }

    public class InvalidOperatorException extends RuntimeException {

    }
}
