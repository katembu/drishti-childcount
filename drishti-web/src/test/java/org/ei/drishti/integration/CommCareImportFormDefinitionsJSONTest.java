package org.ei.drishti.integration;

import org.ei.commcare.api.contract.CommCareFormDefinition;
import org.ei.commcare.api.contract.CommCareModuleDefinition;
import org.ei.commcare.api.util.CommCareImportProperties;
import org.ei.drishti.contract.*;
import org.ei.drishti.controller.DrishtiController;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.*;

import static org.junit.Assert.fail;

public class CommCareImportFormDefinitionsJSONTest {
    private final String definitionsJSONPath = "commcare-import-form-definitions.json";
    private List<CommCareFormDefinition> forms = new ArrayList<CommCareFormDefinition>();

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.put(CommCareImportProperties.COMMCARE_IMPORT_DEFINITION_FILE, "/" + definitionsJSONPath);
        CommCareImportProperties importProperties = new CommCareImportProperties(properties);

        for (CommCareModuleDefinition moduleDefinition : importProperties.moduleDefinitions().modules()) {
            forms.addAll(moduleDefinition.definitions());
        }
    }

    @Test
    public void everyFormInTheJSONShouldHaveAllTheCorrectMappings() {
        Map<String, Class<?>> classEveryFormMappingConvertsTo = new HashMap<String, Class<?>>();

        classEveryFormMappingConvertsTo.put("registerMother", AnteNatalCareEnrollmentInformation.class);
        classEveryFormMappingConvertsTo.put("updateANCCareInformation", AnteNatalCareInformation.class);
        classEveryFormMappingConvertsTo.put("updateOutcomeOfANC", AnteNatalCareOutcomeInformation.class);
        classEveryFormMappingConvertsTo.put("closeANCCase", AnteNatalCareCloseInformation.class);
        classEveryFormMappingConvertsTo.put("registerNewChild", ChildRegistrationRequest.class);
        classEveryFormMappingConvertsTo.put("updateChildImmunization", ChildImmunizationUpdationRequest.class);
        classEveryFormMappingConvertsTo.put("closeChildCase", ChildCloseRequest.class);
        classEveryFormMappingConvertsTo.put("registerEligibleCouple", EligibleCoupleRegistrationRequest.class);
        classEveryFormMappingConvertsTo.put("closeEligibleCouple", EligibleCoupleCloseRequest.class);

        assertEveryFormDefinitionInTheJSONHasBeenRepresentedInThisTest(classEveryFormMappingConvertsTo);
        assertThatTheControllerHasTheMethodsCorrespondingToTheseFormNames(DrishtiController.class, classEveryFormMappingConvertsTo);
    }

    private void assertEveryFormDefinitionInTheJSONHasBeenRepresentedInThisTest(Map<String, Class<?>> classEveryFormMappingConvertsTo) {
        for (CommCareFormDefinition formDefinition : forms) {
            String formName = formDefinition.name();
            Class<?> typeUsedForMappingsInForm = classEveryFormMappingConvertsTo.get(formName);

            if (typeUsedForMappingsInForm == null) {
                fail("Missing form. Change this test to add a mapping for the form: " + formName + " above.");
            }

            assertThatRightHandSideOfEveryFormMappingMapsToAFieldInTheObject(formName, typeUsedForMappingsInForm);
        }
    }

    private void assertThatRightHandSideOfEveryFormMappingMapsToAFieldInTheObject(String formName, Class<?> typeUsedForObjectsInForm) {
        for (String fieldInObject : findForm(formName).mappings().values()) {
            try {
                typeUsedForObjectsInForm.getDeclaredField(fieldInObject);
            } catch (NoSuchFieldException e) {
                fail("Could not find field: " + fieldInObject + " in class: " + typeUsedForObjectsInForm +
                        ". Check the form: " + formName + " in " + definitionsJSONPath + ".");
            }
        }
    }

    private void assertThatTheControllerHasTheMethodsCorrespondingToTheseFormNames(Class<?> controllerClass, Map<String, Class<?>> classEveryFormMappingConvertsTo) {
        for (Map.Entry<String, Class<?>> formNameToClassEntry : classEveryFormMappingConvertsTo.entrySet()) {
            String formName = formNameToClassEntry.getKey();
            Class<?> parameterTypeOfTheMethod = formNameToClassEntry.getValue();
            try {
                controllerClass.getDeclaredMethod(formName, parameterTypeOfTheMethod);
            } catch (NoSuchMethodException e) {
                fail(MessageFormat.format("There should be a method in {0} like this: public void {1}({2}). If it is " +
                        "not present, the dispatcher will not be able to do anything for form submissions of this form: {3}.",
                        controllerClass.getSimpleName(), formName, parameterTypeOfTheMethod.getSimpleName(), formName));
            }
        }
    }

    private CommCareFormDefinition findForm(String formName) {
        for (CommCareFormDefinition definition : forms) {
            if (definition.name().equals(formName)) {
                return definition;
            }
        }
        return null;
    }
}
