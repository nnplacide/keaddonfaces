<%
    def onFormClick = { form ->
        def opts = [ appId: currentApp.id, patientId: currentPatient.id, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
        """ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
    }

    def onEncounterClick = { encounter ->
        """kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
    }
%>
<div class="ke-panel-frame">
    <div class="ke-panel-heading">FACES and SEARCH forms entry</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF">

        <fieldset>
            <legend>New Forms</legend>

            ${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: facesAvailableForms, onFormClick: onFormClick ]) }
        </fieldset>
        <br />
        <fieldset>
            <legend>Previously Completed Forms</legend>
            ${ ui.includeFragment("kenyaemr", "widget/encounterStack", [ encounters: page1Encounters, onEncounterClick: onEncounterClick ]) }
        </fieldset>
    </div>
</div>

<div class="ke-panel-frame">
    <div class="ke-panel-heading">Page 2 (HEI Initial and Followup Visits)</div>
    <div class="ke-panel-content" style="background-color: #F3F9FF">
        ${ ui.includeFragment("kenyaemr", "widget/encounterStack", [ encounters: page2Encounters, onEncounterClick: onEncounterClick ]) }
        <br />
        <div align="center">
            ${ ui.includeFragment("kenyaui", "widget/button", [
                    label: "Add Hei Visit Summary",
                    extra: "From column",
                    iconProvider: "kenyaui",
                    icon: "buttons/visit_retrospective.png",
                    href: ui.pageLink("kenyaemr", "enterForm", [ appId: currentApp.id, patientId: currentPatient, formUuid: page2Form.uuid, returnUrl: ui.thisUrl() ])
            ]) }
        </div>
    </div>
</div>

