package nl.tudelft.oopp.group43.classes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class StringCheckerTest {

    @Test
    public void stringContainsIgnoreCaseTrueTest() {
        String query = "pathe";
        String target = "Pathe delft";
        assertTrue(StringChecker.containsIgnoreCase(query, target));
    }

    @Test
    public void stringContainsIgnoreCaseFalseTest() {
        String query = "pathede";
        String target = "Pathe delft";
        assertFalse(StringChecker.containsIgnoreCase(query, target));
    }

    @Test
    public void stringContainsIgnoreCaseLargerTest() {
        String query = "patheeeeeeeeeeee";
        String target = "Pathe delft";
        assertFalse(StringChecker.containsIgnoreCase(query, target));
    }

    @Test
    public void stringContainsIgnoreCaseQueryNullTest() {
        String target = "Pathe delft";
        assertFalse(StringChecker.containsIgnoreCase(null, target));
    }

    @Test
    public void stringContainsIgnoreCaseTargetNullTest() {
        String query = "pathe";
        assertFalse(StringChecker.containsIgnoreCase(query, null));
    }

    @Test
    public void stringContainsIgnoreCaseEqualsTest() {
        String query = "pAtHe DeLfT";
        String target = "Pathe delft";
        assertTrue(StringChecker.containsIgnoreCase(query, target));
    }

    @Test
    public void stringContainsIgnoreCaseAppliedSciencesTest() {
        String query = "pathe";
        String target = "Applied Sciences (AS / TNW zuid)";
        assertFalse(StringChecker.containsIgnoreCase(query, target));
    }

}
