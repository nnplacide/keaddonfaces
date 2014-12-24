/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.keaddonfaces.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.keaddonfaces.metadata.FacesMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 4/9/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class HeiFragmentController {

    public void controller(@FragmentParam("patient")
                           Patient patient,
                           FragmentModel model,
                           UiUtils ui) {

        //String[] specialFacesFormUuids = { HivMetadata._Form.HIV_ENROLLMENT, HivMetadata._Form.FAMILY_HISTORY };
        String[]specialFacesFormUuids = {
                FacesMetadata._Form.DISCONTINUATION_FORM,
                FacesMetadata._Form.HEI_INITIAL_FORM,
                //FacesMetadata._Form.ART_ELIBILITY_FORM
        };

        List<SimpleObject> facesAvailableForms = new ArrayList<SimpleObject>();
        List<Encounter> page1Encounters = new ArrayList<Encounter>();

        for (String page1FormUuid : specialFacesFormUuids) {
            Form page1Form = MetadataUtils.getForm(page1FormUuid);
            List<Encounter> formEncounters = getPatientEncounterByForm(patient, page1Form);

            if (formEncounters.size() == 0) {
                facesAvailableForms.add(ui.simplifyObject(page1Form));
            }
            else {
                page1Encounters.addAll(formEncounters);
            }
        }

        Form heiVisitForm = MetadataUtils.getForm( FacesMetadata._Form.HEI_FOLLOWUP_FORM);
        List<Encounter> heiVisitSummaryEncounters = getPatientEncounterByForm(patient, heiVisitForm);

        model.addAttribute("facesAvailableForms", facesAvailableForms);
        model.addAttribute("page1Encounters", page1Encounters);
        model.addAttribute("page2Form", heiVisitForm);
        model.addAttribute("page2Encounters", heiVisitSummaryEncounters);


    }

    /**
     * Convenience method to get encounters from the given form
     * @param patient the patient
     * @param form the form
     * @return the encounters
     */
    private static List<Encounter> getPatientEncounterByForm(Patient patient, Form form) {
        return Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, null, null, false);
    }
}
