<%

    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])
    def menuItems = [
            [
                    label: "Overview",
                    href: ui.pageLink("keaddonfaces", "faces/facesViewPatient", [ patientId: currentPatient.id, section: "overview" ]),
                    active: (selection == "section-overview"),
                    iconProvider: "kenyaui",
                    icon: "buttons/patient_overview.png"
            ],
            [
                    label: "FACES and SEARCH forms",
                    href: ui.pageLink("keaddonfaces", "faces/facesViewPatient", [ patientId: currentPatient.id, section: "hei" ]),
                    active: (selection == "section-hei"),
                    iconProvider: "kenyaui",
                    icon: "forms/moh257.png"
            ]
    ];


%>

<div class="ke-page-sidebar">
    <div class="ke-panel-frame">
        <% menuItems.each { item -> print ui.includeFragment("kenyaui", "widget/panelMenuItem", item) } %>
    </div>

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
                        href: ui.pageLink("keaddonfaces", "faces/facesViewPatient", [ patientId: currentPatient.id, visitId: visit.id ]),
                        extra: kenyaui.formatVisitDates(visit),
                        active: (selection == "visit-" + visit.id)
                ])
            }
        } %>
    </div>

</div>

<div class="ke-page-content">

    <% if (visit) { %>

    ${ ui.includeFragment("kenyaemr", "visitSummary", [ visit: visit ]) }
    <% if (!visit.voided) { %>
    ${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: visit ]) }
    ${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: visit ]) }
    <% } %>

    <% } else if (form) { %>

    <div class="ke-panel-frame">
        <div class="ke-panel-heading">${ ui.format(form) }</div>
        <div class="ke-panel-content">

            <% if (encounter) { %>
            ${ ui.includeFragment("kenyaemr", "form/viewHtmlForm", [ encounter: encounter ]) }
            <% } else { %>
            <em>Not filled out</em>
            <% } %>

        </div>
    </div>

    <% } else if (section == "overview") { %>

    ${ ui.includeFragment("kenyaemr", "program/programCarePanels", [ patient: currentPatient, complete: true, activeOnly: false ]) }

    <% } else if (section == "hei") { %>

    ${ ui.includeFragment("keaddonfaces", "hei", [ patient: currentPatient ]) }

    <% } %>

</div>