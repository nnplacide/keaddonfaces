package org.openmrs.module.keaddonfaces.metadata;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.module.keaddonfaces.FacesConstants;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * Created with IntelliJ IDEA.
 * User: werick
 * Date: 4/24/14
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.

**
        * Security metadata bundle
        */
@Component
public class FacesSecurityMetadata extends AbstractMetadataBundle{

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    public static final class _Role {
        public static final String FACES_DATA_CLERK = "Faces Data Clerk";
        public static final String FACES_API_PRIVILEGES_VIEW_AND_EDIT = "API Privileges (View and Edit)";

    }

    /**
     * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
     */
    @Override
    public void install() {

        String[] appIds = {
                FacesConstants.APP_FACES
        };

        // Ensure a privilege exists for each app. App framework does create these but not always before this
        // bundle is installed
        for (String appId : appIds) {
            install(privilege(app(appId), "Access to the " + appId + " app"));
        }

        install(role(_Role.FACES_API_PRIVILEGES_VIEW_AND_EDIT, "All viewing and editing API privileges",
                null, getApiPrivileges(false))
        );
        // Add custom privileges


        install(role(_Role.FACES_DATA_CLERK, "Can access the Faces KenyaEmr add on apps",
                idSet(_Role.FACES_API_PRIVILEGES_VIEW_AND_EDIT),
                idSet(app(FacesConstants.APP_FACES))
        ));


    }

    /**
     * Fetches sets of API privileges
     * @param incDestructive include destructive (delete, purge) privileges
     * @return the privileges
     */
    protected Set<String> getApiPrivileges(boolean incDestructive) {
        Set<String> privileges = new HashSet<String>();

        for (Privilege privilege : userService.getAllPrivileges()) {
            if (privilege.getPrivilege().startsWith("App: ") || privilege.getPrivilege().startsWith("Emr: ")) {
                continue;
            }

            boolean isDestructive = privilege.getPrivilege().startsWith("Delete ") || privilege.getPrivilege().startsWith("Purge ");

            if (!incDestructive && isDestructive) {
                continue;
            }
            privileges.add(privilege.getPrivilege());
        }

        return privileges;
    }

    /**
     * Creates an app privilege from an app ID
     * @param appId the app ID
     * @return the privilege
     */
    protected String app(String appId) {
        return "App: " + appId;
    }
}
