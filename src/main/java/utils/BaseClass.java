package utils;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


public class BaseClass {
    public static ExtentReports extent;
    public static ExtentHtmlReporter htmlReporter;
    public ExtentTest test;
    public static String extentpath;
    public static String globalEnvironment;
    public static String testingType;
    private boolean healthCheck = true;
    private String isEmailSend = "";

    @BeforeSuite
    public static ExtentReports before() throws MalformedURLException
    {
        //Adding TIMESTAMP at the end of the report
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);

        String workingDir = System.getProperty("user.dir");
        extentpath = workingDir + "/report_logs/ExtentReport"+formattedDate+".html";
        htmlReporter = new ExtentHtmlReporter(extentpath);

        htmlReporter.config().setDocumentTitle(" DataTesting Report");
        htmlReporter.config().setReportName(" TestSuite Result");
        htmlReporter.config().enableTimeline(false);
        htmlReporter.config().setAutoCreateRelativePathMedia(true);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        Properties props = System.getProperties();
        String os = props.getProperty("os.name");
        String javaVersion = props.getProperty("java.version");
        String username = System.getProperty("user.name");
        String hostname = "Unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        extent.setSystemInfo("OS", os);
        extent.setSystemInfo("User Name", username);
        extent.setSystemInfo("Java Version", javaVersion);
        extent.setSystemInfo("Host Name", hostname);

//        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
        return extent;
    }

//    @BeforeMethod
//    protected void startReporting(Method method) {
//        String testName;
//        testName = this.getClass().getSimpleName() + " : " + method.getName();
//        test = extent.createTest(testName, method.getAnnotation(Test.class).description());
//    }

    @BeforeMethod
    protected void startReporting(Method method) {
        String testName = method.getDeclaringClass().getSimpleName() + " : " + method.getName();
        test = extent.createTest(testName);
//        ExtentManager.setExtentTest(test); // Set the test in ExtentManager
    }

    @AfterMethod
    public void reportClosure(ITestResult result) {

        test.log(Status.INFO, "Verifying Table :  " + ExtentManager.getTestDescription());
        test.log(Status.INFO, "Test execution started.");

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,"Test Case "+result.getName() );
            test.log(Status.FAIL,ExtentManager.getExecutedQuery() );
        }
        else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS,"Executed Query - "+ExtentManager.getExecutedQuery() );
            test.log(Status.PASS, result.getName() + " Executed Query result -> "+ ExtentManager.getExecutedQueryResult());
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Name of Skipped Test case is:  " + result.getName());
        }
        test.log(Status.INFO, "Test execution completed.");
    }

    @AfterTest
    public void tearDown(){
        extent.flush();
    }

    @AfterSuite
    public void utlity(){
        //Call sendmail functionality
    }

}
