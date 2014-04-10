package org.openmrs.module.keaddonfaces.page.controller.faces;

import org.openmrs.Patient;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.keaddonfaces.FacesConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 3/19/14
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
@AppPage(FacesConstants.APP_FACES)
public class FacesHomePageController {

    public void controller(@RequestParam(required=false, value="patientId") Patient patient,
                           PageModel model,
                           UiUtils ui) {

        model.addAttribute("patient", patient);
    }
}
