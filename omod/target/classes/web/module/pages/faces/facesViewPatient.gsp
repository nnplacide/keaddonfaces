<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">


    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td width="40%" valign="top">

                ${ ui.includeFragment("kenyaemr", "patient/patientRelationships", [ patient: currentPatient ]) }

            </td>
            <td width="60%" valign="top" style="padding-left: 5px">
                ${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: null ]) }
                ${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: null ]) }

            </td>
        </tr>
    </table>
</div>

