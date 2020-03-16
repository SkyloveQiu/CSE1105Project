package nl.tudelft.oopp.group43.classes;

public class StringChecker {

    /**
     * Checks if a string contains the provided query, this check ignores upper and lowercase.
     * @param query the search query
     * @param target the target string
     * @return returns true if the query is in the target, false if otherwise.
     */
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
        while (i < target.length() && i + query.length() < target.length()) {
            if (queryLower.charAt(0) == target.charAt(i) || queryUpper.charAt(0) == target.charAt(i)) {
                i++;
                int j = 1;
                while (j < query.length() && i < target.length()) {
                    if (queryLower.charAt(j) == target.charAt(i) || queryUpper.charAt(j) == target.charAt(i)) {
                        System.out.print("" + queryLower.charAt(j) + target.charAt(i));
                        i++;
                        j++;
                        continue;
                    }
                    System.out.println("\n" + target + " is false");
                    return false;
                }
                System.out.println("\n" + target + " is correct");
                return true;
            }
            i++;
        }

        return false;
    }

}
