package org.openmrs.module.keaddonfaces.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.encounterType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.form;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 3/19/14
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class FacesMetadata extends AbstractMetadataBundle {

    public static class _EncounterType {

        public static final String ADHERENCE = "155c23a5-a6e3-4d33-aceb-7a1a735c51de";
        //public static final String ARV_THERAPY_ENCOUNTER = "aeaaa866-7ed1-455c-9002-ba40e67a9882";
        public static final String COUNSELLING = "6eb40e54-1a73-4adf-be2a-464731cf842b";
        public static final String CONGENITAL_SCREENING = "f5768c5a-a543-4da9-a4b1-aee21406a39f";
        public static final String DISCONTINUATION = "2bdada65-4c72-4a48-8730-859890e25cee";
       // public static final String EXAMPLE = "d69dedbd-3933-4e44-8292-bea939ce980a";
        //public static final String PSC_INITIAL = "3e87713a-1ccb-4ca3-a5eb-5a962f63d1b0";
        public static final String HEI_FOLLOWUP = "20222135-4da8-4327-9178-84362702906b";
        public static final String HEI_INITIAL = "65f7e74d-f3f2-473d-9e32-20c90fd0ecba";
        public static final String LABORATORY_INVESTIGATION = "17a381d1-7e29-406a-b782-aa903b963c28";
        public static final String TB_SCREENING = "ed6dacc9-0827-4c82-86be-53c0d8c449be";

    }

    public static class _Form {

        public static final String ADHERENCE = "db64f32b-1ce5-4767-a0d1-45eeb6b81eb8";
        public static final String LAB_FORM = "82cffa17-55db-4fb9-ab88-983ae26979d8";
       // public static final String EXAMPLE = "b694b1bc-2086-47dd-a4ad-ba48f9471e4b";
        public static final String COUNSELLING_FORM = "8ced7cda-b13b-43ea-ada2-4c47fb94c751";
        public static final String HEI_INITIAL_FORM = "a938c14d-1ca8-456e-b992-f44b37a55ae2";
        public static final String HEI_FOLLOWUP_FORM = "38b3108e-624c-4dc7-89de-0903679c53e2";
        public static final String DISCONTINUATION_FORM = "e4fce01e-3bbe-40c1-9578-30935d43b335";
        public static final String TB_ICF_FORM = "d292bba8-f559-4948-866e-8b502eb12c68";
        public static final String CONGENITAL_SCREENING_FORM = "42a1122e-27bc-470a-af94-bac85d8a229f";
    }

    /**
     * @see org.openmrs.module.kenyacore.metadata.bundle.AbstractMetadataBundle#install()
     */
    @Override
    public void install() {

        //install encounter_types
        install(encounterType("Adherence encounter", "Adherence visit details", _EncounterType.ADHERENCE));
        install(encounterType("General Counselling", "Encounter type that involves counselling", _EncounterType.COUNSELLING));
        install(encounterType("Congenital Abnormality Screening", "Encounter type that involves Screening Infants for Congenital Abnormality", _EncounterType.CONGENITAL_SCREENING));
        install(encounterType("HEI Initial", "Out-patient Initial visit for HEI patients at the PSC", _EncounterType.HEI_INITIAL));
        install(encounterType("HEI Followup", "Out-patient followup visit for HEI patients at the PSC", _EncounterType.HEI_FOLLOWUP));
        //install(encounterType("Example encounter", "Just an example", _EncounterType.EXAMPLE));


        //Install forms

        install(form("Adherence form", null, _EncounterType.ADHERENCE, "1", _Form.ADHERENCE));
        install(form("General counselling form", null, _EncounterType.COUNSELLING, "1", _Form.COUNSELLING_FORM));
        install(form("Congenital Abnormality Screening form", null, _EncounterType.CONGENITAL_SCREENING, "1", _Form.CONGENITAL_SCREENING_FORM));
        install(form("Discontinuation form", null, _EncounterType.DISCONTINUATION, "1", _Form.DISCONTINUATION_FORM));
        install(form("HEI Follow-Up Card, page 1", null, _EncounterType.HEI_INITIAL, "1", _Form.HEI_INITIAL_FORM));
        install(form("HEI Follow-Up Card: Growth, Nutrition and Development Monitoring", null, _EncounterType.HEI_FOLLOWUP, "1", _Form.HEI_FOLLOWUP_FORM));
        install(form("Laboratory investigation form", null, _EncounterType.LABORATORY_INVESTIGATION, "1", _Form.LAB_FORM));
        install(form("TB ICF form", null, _EncounterType.TB_SCREENING, "1", _Form.TB_ICF_FORM));

       // install(form("Example form", null, _EncounterType.EXAMPLE, "1", _Form.EXAMPLE));
    }
}