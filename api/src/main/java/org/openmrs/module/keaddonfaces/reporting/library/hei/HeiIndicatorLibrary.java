package org.openmrs.module.keaddonfaces.reporting.library.hei;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 5/5/14
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class HeiIndicatorLibrary {



    @Autowired
    private HeiCohortLibrary heiCohorts;

    /**
     * Number of patients who are currently on ART
     * @return the indicator
     */
    public CohortIndicator heiEnrolled() {
        return cohortIndicator("Number of HEI Enrolled", ReportUtils.map(heiCohorts.heiEnrolled(), "fromDate=${startDate},toDate=${endDate}"));
    }

}
