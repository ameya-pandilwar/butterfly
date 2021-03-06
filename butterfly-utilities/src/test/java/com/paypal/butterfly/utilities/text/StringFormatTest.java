package com.paypal.butterfly.utilities.text;

import com.paypal.butterfly.extensions.api.TUExecutionResult;
import com.paypal.butterfly.extensions.api.exception.TransformationDefinitionException;
import com.paypal.butterfly.extensions.api.exception.TransformationUtilityException;
import com.paypal.butterfly.utilities.TransformationUtilityTestHelper;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.MissingFormatArgumentException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link StringFormat}
 *
 * @author facarvalho
 */
public class StringFormatTest extends TransformationUtilityTestHelper {

    @Test
    public void test() {
        Mockito.when(transformationContext.get("object")).thenReturn("house");
        Mockito.when(transformationContext.get("color")).thenReturn("blue");

        StringFormat stringFormat = new StringFormat().setFormat("This %s is %s").setAttributeNames("object", "color");
        TUExecutionResult executionResult = stringFormat.execution(transformedAppFolder, transformationContext);
        assertEquals(executionResult.getType(), TUExecutionResult.Type.VALUE);
        assertEquals(executionResult.getValue(), "This house is blue");

        assertEquals(stringFormat.getFormat(), "This %s is %s");
        assertEquals(stringFormat.getAttributeNames(), new String[]{"object", "color"});
        assertEquals(stringFormat.getDescription(), "Apply transformation context attributes [object, color] to 'This %s is %s'");
    }

    @Test(expectedExceptions = TransformationDefinitionException.class, expectedExceptionsMessageRegExp = "Attribute names cannot be null or empty")
    public void invalidArguments1() {
        new StringFormat("This %s is %s").setAttributeNames(new String[]{});
    }

    @Test(expectedExceptions = TransformationDefinitionException.class, expectedExceptionsMessageRegExp = "Attribute names cannot be null or empty")
    public void invalidArguments2() {
        new StringFormat("This %s is %s").setAttributeNames((String[]) null);
    }

    @Test
    public void formatArgumentMismatchTest() {
        StringFormat stringFormat = new StringFormat("This %s is %s");
        assertEquals(stringFormat.getFormat(), "This %s is %s");
        assertEquals(stringFormat.getAttributeNames(), new String[]{});
        assertEquals(stringFormat.getDescription(), "Apply transformation context attributes [] to 'This %s is %s'");

        TUExecutionResult executionResult = stringFormat.execution(transformedAppFolder, transformationContext);
        assertEquals(executionResult.getType(), TUExecutionResult.Type.ERROR);
        assertNull(executionResult.getValue());
        assertEquals(executionResult.getException().getClass(), TransformationUtilityException.class);
        assertEquals(executionResult.getException().getMessage(), "String format and arguments don't match");
        assertEquals(executionResult.getException().getCause().getClass(), MissingFormatArgumentException.class);
        assertEquals(executionResult.getException().getCause().getMessage(), "Format specifier '%s'");
    }

}
