package ca.freedommobile.api.framework.reports;

import ca.freedommobile.api.framework.utils.CommonUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @project api-framework
 * Created By Rsingh on 2019-05-15.
 */
public class HTMLReport {

    private ExtentHtmlReporter htmlReporter;
    private ExtentReports reports;

    private static HTMLReport instance;
    private Logger logger = LogManager.getLogger(HTMLReport.class);

    private HTMLReport(){
        logger.log(Level.INFO,"Opening Logfile {} ",CommonUtils.getReportFileName() );
        htmlReporter = new ExtentHtmlReporter(CommonUtils.getReportFileName());
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
    }

    public static HTMLReport getInstance() {
        if(instance==null){
            instance = new HTMLReport();
        }
        return instance;
    }

    public ExtentReports getReports()
    { return reports;
    }

}
