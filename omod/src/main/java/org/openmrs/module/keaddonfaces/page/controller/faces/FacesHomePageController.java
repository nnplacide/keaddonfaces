package org.openmrs.module.keaddonfaces.page.controller.faces;

import org.openmrs.Patient;
import org.openmrs.module.keaddonfaces.FacesConstants;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 4/18/14
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
@AppPage(FacesConstants.APP_FACES)
public class FacesHomePageController {

    public String controller(@RequestParam(required=false, value="patientId") Patient patient,
                                UiUtils ui, PageModel model) {
        //Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);

        if (patient != null) {
            return "redirect:" + ui.pageLink(FacesConstants.MODULE_ID, "faces/facesViewPatient", SimpleObject.create("patientId", patient.getId()));
        } else {
            return null;
        }
    }
}
