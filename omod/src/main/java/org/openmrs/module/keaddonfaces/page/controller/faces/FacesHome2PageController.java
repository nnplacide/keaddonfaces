package org.openmrs.module.keaddonfaces.page.controller.faces;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.keaddonfaces.FacesConstants;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyacore.program.ProgramDescriptor;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 3/19/14
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
@AppPage(FacesConstants.APP_FACES)
public class FacesHome2PageController {

    public void controller(@RequestParam(required=false, value="patientId") Patient patient,
                           @RequestParam(required = false, value = "visitId") Visit visit,
                           @RequestParam(required = false, value = "formUuid") String formUuid,
                           @RequestParam(required = false, value = "programId") Program program,
                           @SpringBean FormManager formManager,
                           @SpringBean ProgramManager programManager,
                           PageRequest pageRequest,
                           @SpringBean KenyaUiUtils kenyaUi,
                           PageModel model,
                           UiUtils ui) {

        if ("".equals(formUuid)) {
            formUuid = null;
        }

        Collection<ProgramDescriptor> progams = programManager.getPatientPrograms(patient);

        model.addAttribute("programs", progams);
        model.addAttribute("programSummaries", programSummaries(patient, progams, programManager, kenyaUi));
        model.addAttribute("visits", Context.getVisitService().getVisitsByPatient(patient));

        Form form = null;
        String selection = null;

        if (visit != null) {
            selection = "visit-" + visit.getVisitId();
        }
        else if (formUuid != null) {
            selection = "form-" + formUuid;

            form = Context.getFormService().getFormByUuid(formUuid);
            List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, null, null, false);
            Encounter encounter = encounters.size() > 0 ? encounters.get(0) : null;
            model.addAttribute("encounter", encounter);
        }
        else if (program != null) {
            selection = "program-" + program.getProgramId();
        }

        model.addAttribute("form", form);
        model.addAttribute("visit", visit);
        model.addAttribute("patient", patient);
        model.addAttribute("selection", selection);
    }

    /**
     * Creates a one line summary for each program
     * @return the map of programs to summaries
     */
    private Map<Program, String> programSummaries(Patient patient, Collection<ProgramDescriptor> programs, ProgramManager programManager, KenyaUiUtils kenyaUi) {
        Map<Program, String> summaries = new HashMap<Program, String>();

        for (ProgramDescriptor descriptor : programs) {
            Program program = descriptor.getTarget();
            List<PatientProgram> allEnrollments = programManager.getPatientEnrollments(patient, program);
            PatientProgram lastEnrollment = allEnrollments.get(allEnrollments.size() - 1);
            String summary = lastEnrollment.getActive()
                    ? "Enrolled on " + kenyaUi.formatDate(lastEnrollment.getDateEnrolled())
                    : "Completed on " + kenyaUi.formatDate(lastEnrollment.getDateCompleted());

            summaries.put(descriptor.getTarget(), summary);
        }

        return summaries;
    }
}
