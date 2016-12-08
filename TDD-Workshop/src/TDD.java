import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mhqasrawi on 07/12/16.
 */

public class TDD {

    private WhereClauseBuilder whereClauseBuilder;


    private WhereClauseBuilder createWhereClauseBuilderWithFilter(String property1, String value1) {
        return new WhereClauseBuilder().addFilter(property1, "=", value1);
    }

    private void createWhereClauseBuilderWithFilter(String property, String operator, String anyValue) {
        new WhereClauseBuilder().addFilter(property, operator, anyValue);
    }

    @Before
    public void setUp() throws Exception {
        whereClauseBuilder = new WhereClauseBuilder();
    }

    @Test
    public void givenNewlyInitializedBuilder_WhenCallingBuild_ThenEmptyStringShouldBeGenerated() throws Exception {
        assertEquals("", whereClauseBuilder.build());
    }

    @Test
    public void givenWhereClauseBuilder_WhenAddingFilter_AndCallingBuilder_ReturnExpectedResult() throws Exception {
        assertEquals("property1='value1'", createWhereClauseBuilderWithFilter("property1", "value1").build());
        assertEquals("property2='value2'", createWhereClauseBuilderWithFilter("property2", "value2").build());
    }

    @Test(expected = WhereClauseBuilder.InvalidPropertyException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWithNullProperty_ThenThrowException() throws Exception {

        createWhereClauseBuilderWithFilter(null, "anyValue");
    }


    @Test(expected = WhereClauseBuilder.InvalidPropertyException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWitEmptyProperty_AndThenThrowException() throws Exception {

        createWhereClauseBuilderWithFilter("", "AnyValue");
    }


    @Test(expected = WhereClauseBuilder.InvalidPropertyException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWithPropertyHaveSpaces_AndThenThrowException() throws Exception {
        createWhereClauseBuilderWithFilter(" ", "AnyValue");
    }

    @Test(expected = WhereClauseBuilder.InvalidOperatorException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWithNullOperator_ShouldThrowException() throws Exception {
        createWhereClauseBuilderWithFilter("property1", null, "anyValue");

    }
}
