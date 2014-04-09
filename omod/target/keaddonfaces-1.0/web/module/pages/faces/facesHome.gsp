<%

    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])
%>

<div class="ke-page-sidebar">
     <div class="ke-panel-frame">
        <div class="ke-panel-heading">Visits</div>


    </div>

</div>

<div class="ke-page-content">
    ${ ui.decorate("kenyaui", "panel", [ heading: "Welcome" ], "This is a page added by Faces KenyaEMR add-on module") }

    <% if (currentPatient) { %>
            ${ ui.includeFragment("keaddonfaces", "hei", [ patient: currentPatient ]) }
        <% if(visit) { %>
                ${ ui.includeFragment("kenyaemr","availableForms", [ visit: visit ]) }
                ${ ui.includeFragment("kenyaemr","completedForms", [ visit: visit ]) }
        <%} %>

    <% } else { %>
    ${ ui.decorate("kenyaui", "panel", [ heading: "Faces Forms" ], "Select a patient with another app to see a form list here") }

    <% } %>

</div>