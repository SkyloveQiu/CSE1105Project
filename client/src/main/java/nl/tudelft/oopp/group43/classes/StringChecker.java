package nl.tudelft.oopp.group43.classes;

public class StringChecker {

    public static boolean containsIgnoreCase(String query, String target) {
        if (query == null || target == null || query.length() > target.length()) {
            return false;
        }

        if (target.equalsIgnoreCase(query)) {
            return true;
        }

        String queryLower = query.toLowerCase();
        String queryUpper = query.toUpperCase();

        int i = 0;
        while (i < query.length() && i + query.length() < target.length()) {
            if (queryLower.charAt(i) == target.charAt(i) || queryUpper.charAt(i) == target.charAt(i)) {
                i++;
                while (i < query.length() && i < target.length()) {
                    if (queryLower.charAt(i) != target.charAt(i) && queryUpper.charAt(i) != target.charAt(i)) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
            i++;
        }

        return false;
    }

}
