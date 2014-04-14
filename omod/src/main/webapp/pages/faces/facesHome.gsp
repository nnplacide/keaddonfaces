<%

    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])
%>

<div class="ke-page-sidebar">
     <div class="ke-panel-frame">
        <div class="ke-panel-heading">Visits</div>
         <% if (!visits) {
             print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
                     label: ui.message("general.none"),
             ])
         }
         else {
             visits.each { visit ->
                 print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
                         label: ui.format(visit.visitType),
                         href: ui.pageLink("keaddonfaces", "faces/facesHome", [ patientId: currentPatient.id, visitId: visit.id ]),
                         extra: kenyaui.formatVisitDates(visit),
                         active: (selection == "visit-" + visit.id)
                 ])
             }
         } %>
    </div>

</div>

<div class="ke-page-content">
       <% if (currentPatient) { %>
            ${ ui.includeFragment("keaddonfaces", "hei", [ patient: currentPatient ]) }
            <% if (visit) { %>
                ${ ui.includeFragment("kenyaemr", "visitSummary", [ visit: visit ]) }
                <% if (!visit.voided) { %>
                    ${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: visit ]) }
                    ${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: visit ]) }
                <% } %>

            <% } %>

    <% } else { %>
    ${ ui.decorate("kenyaui", "panel", [ heading: "Faces Forms" ], "Select a patient with another app to see a form list here") }

    <% } %>

</div>