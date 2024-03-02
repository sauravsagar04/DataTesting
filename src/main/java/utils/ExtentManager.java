package utils;

public class ExtentManager {
    public static String testDescription;
    public static String executedQuery;
    public static String executedQueryResult;

    public static String getTestDescription(){
        return testDescription;
    }
    public static String getExecutedQuery() {
        return executedQuery;
    }
    public static String getExecutedQueryResult() {
        return executedQueryResult;
    }
}
