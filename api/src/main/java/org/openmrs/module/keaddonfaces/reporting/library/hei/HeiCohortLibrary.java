package org.openmrs.module.keaddonfaces.reporting.library.hei;

import org.openmrs.EncounterType;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 5/5/14
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class HeiCohortLibrary {

    @Autowired
    private CommonCohortLibrary commonCohorts;

    /**
     * Number of HEI newly enrolled entered into OpenMRS
     * @return the cohort definition
     */
    public CohortDefinition heiEnrolled()
    {
        EncounterType heiEnroll= MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHCS_ENROLLMENT);

        return commonCohorts.hasEncounter(heiEnroll);
    }
}
