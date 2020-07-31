package hu.java.eight.testng;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public final class TestNGTemplateTest {
    private MagicClass underTest;

    @DataProvider(name = "strings")
    public Object[][] createStrings() {
        final String magic = "Magic!";
        final String description = "My favorite product";
        return new Object[][] {
                {magic, magic.length()},
                {description, description.length()},
        };
    }

    @BeforeMethod
    public void setUp() {
        this.underTest = new MagicClass();
    }

    @Test(dataProvider = "strings")
    public void testMagicFunctionality(final String magicString, final int expectedLength) {
        final int actualLength = this.underTest.magicFunction(magicString);

        assertEquals("Magic test", expectedLength, actualLength);
    }
}
