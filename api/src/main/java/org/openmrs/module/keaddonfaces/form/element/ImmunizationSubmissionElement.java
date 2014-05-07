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
package org.openmrs.module.keaddonfaces.form.element;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.module.htmlformentry.FormEntryContext;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.htmlformentry.FormSubmissionError;
import org.openmrs.module.htmlformentry.HtmlFormEntryUtil;
import org.openmrs.module.htmlformentry.action.FormSubmissionControllerAction;
import org.openmrs.module.htmlformentry.element.HtmlGeneratorElement;
import org.openmrs.module.htmlformentry.widget.CheckboxWidget;
import org.openmrs.module.htmlformentry.widget.ErrorWidget;
import org.openmrs.module.htmlformentry.widget.Widget;
import org.openmrs.util.OpenmrsUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 5/7/14
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */

public class ImmunizationSubmissionElement implements HtmlGeneratorElement , FormSubmissionControllerAction {
    private Concept vaccineConcept;
    private Integer sequenceNumber;
    private String label;
    private String id;
    private Widget widget;

    private ErrorWidget errorWidget;

    private Obs existingObsGroup;

    // Question concepts
    private static final String IMMUNIZATION_GROUP = "1421AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String IMMUNIZATION_VACCINE = "984AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String IMMUNIZATION_SEQUENCE_NUMBER = "1418AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    /**
     * Constructs a new immunization submission element
     * @param context the form entry context
     * @param parameters the tag parameters
     */

    public ImmunizationSubmissionElement(FormEntryContext context,Map<String, String> parameters) {
        String vaccineConceptId = parameters.get("vaccineConceptId");
        if (StringUtils.isEmpty(vaccineConceptId)) {
            throw new RuntimeException("vaccineConceptId attribute required");
        }


        vaccineConcept = HtmlFormEntryUtil.getConcept(vaccineConceptId);
        sequenceNumber = parameters.containsKey("sequenceNumber") ? Integer.parseInt(parameters.get("sequenceNumber")) : null;
        label = parameters.get("label");

        widget = new CheckboxWidget();
        errorWidget = new ErrorWidget();

        context.registerWidget(widget);
        context.registerErrorWidget(widget, errorWidget);
    }

    @Override
    public String generateHtml(FormEntryContext context) {
        boolean viewMode = context.getMode().equals(FormEntryContext.Mode.VIEW);
        StringBuilder sb = new StringBuilder();


        if (id != null) {
            sb.append("<span id=\"" + id + "\">");
            context.registerPropertyAccessorInfo(id + ".value", context.getFieldNameIfRegistered(widget), null, null, null);
            context.registerPropertyAccessorInfo(id + ".error", context.getFieldNameIfRegistered(errorWidget), null, null, null);
        }

        sb.append(widget.generateHtml(context));

        if (label != null) {
            sb.append(label);
        } else {
            sb.append(vaccineConcept.getName().getName());

            if (sequenceNumber != null) {
                sb.append("-");
                sb.append(sequenceNumber);
            }
        }

        if (!viewMode) {
            sb.append(errorWidget.generateHtml(context));
        }

        if (id != null) {
            sb.append("</span>");
        }


        return sb.toString();
    }

    @Override
    public Collection<FormSubmissionError> validateSubmission(FormEntryContext formEntryContext, HttpServletRequest httpServletRequest) {
        return Collections.emptyList();
    }

    @Override
    public void handleSubmission(FormEntrySession formEntrySession, HttpServletRequest httpServletRequest) {
        // TODO
    }

    /**
     * Matches an existing obs group
     * @param context the form entry context
     * @return the obs or null
     */
    protected Obs matchExistingObsGroup(FormEntryContext context) {
        Concept immunizationGroupConcept = HtmlFormEntryUtil.getConcept(IMMUNIZATION_GROUP);
        Concept immunizationVaccineConcept = HtmlFormEntryUtil.getConcept(IMMUNIZATION_VACCINE);
        Concept immunizationSequenceNumberConcept = HtmlFormEntryUtil.getConcept(IMMUNIZATION_SEQUENCE_NUMBER);

        for (Map.Entry<Obs, Set<Obs>> entry : context.getExistingObsInGroups().entrySet()) {
            Obs group = entry.getKey();

            // Skip if obs group isn't an immunization obs grouping
            if (!group.getConcept().equals(immunizationGroupConcept)) {
                continue;
            }

            Concept vaccineAnswer = null;
            Integer sequenceNumberAnswer = null;

            // Look through obs group members to find vaccine and sequence number
            for (Obs memberObs : entry.getValue()) {
                if (memberObs.getConcept().equals(immunizationVaccineConcept)) {
                    vaccineAnswer = memberObs.getValueCoded();
                }
                else if (memberObs.getConcept().equals(immunizationSequenceNumberConcept)) {
                    sequenceNumberAnswer = memberObs.getValueNumeric().intValue();
                }
            }

            // Remove and return and group if both vaccine and sequence number match
            if (OpenmrsUtil.nullSafeEquals(vaccineAnswer, vaccineConcept) && OpenmrsUtil.nullSafeEquals(sequenceNumberAnswer, sequenceNumber)) {
                context.getExistingObsInGroups().remove(group);
                return group;
            }
        }

        return null;
    }

}
