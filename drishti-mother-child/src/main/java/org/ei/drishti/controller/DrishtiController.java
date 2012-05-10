package org.ei.drishti.controller;

import org.ei.commcare.listener.CommCareFormSubmissionRouter;
import org.ei.drishti.contract.*;
import org.ei.drishti.contract.ChildCloseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DrishtiController {
    private static Logger logger = LoggerFactory.getLogger(DrishtiController.class.toString());


    @Autowired
    public DrishtiController(CommCareFormSubmissionRouter router) {
        router.registerForDispatch(this);
    }


    public void registerUnderFive(UnderFiveRegistrationInformation childInformation) {
        logger.info("Child Registration: " + childInformation);
    }

    public void registerPregnancy(PregnancyRegistrationInformation pregnancyInformation) {
        logger.info("Pregnancy Registration: " + pregnancyInformation);
    }
}
