package utils;

public class ExtentManager {
    private static ThreadLocal<String> testDescription = new ThreadLocal<>();
    private static ThreadLocal<String> executedQuery = new ThreadLocal<>();
    private static ThreadLocal<String> executedQueryResult = new ThreadLocal<>();

    public static String getTestDescription() {
        return testDescription.get();
    }

    public static void setTestDescription(String description) {
        testDescription.set(description);
    }

    public static String getExecutedQuery() {
        return executedQuery.get();
    }

    public static void setExecutedQuery(String query) {
        executedQuery.set(query);
    }

    public static String getExecutedQueryResult() {
        return executedQueryResult.get();
    }

    public static void setExecutedQueryResult(String result) {
        executedQueryResult.set(result);
    }

    public static void clearAll() {
        testDescription.remove();
        executedQuery.remove();
        executedQueryResult.remove();
    }
}
