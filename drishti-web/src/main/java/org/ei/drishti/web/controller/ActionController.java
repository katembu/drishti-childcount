package org.ei.drishti.web.controller;

import ch.lambdaj.function.convert.Converter;
import org.ei.drishti.domain.Action;
import org.ei.drishti.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;

@Controller
public class ActionController {
    private ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/actions")
    @ResponseBody
    public List<ActionItem> getNewActionForANM(@RequestParam("anmIdentifier") String anmIdentifier, @RequestParam("timeStamp") Long timeStamp){
        List<Action> alertActions = actionService.getNewAlertsForANM(anmIdentifier, timeStamp);
        return with(alertActions).convert(new Converter<Action, ActionItem>() {
            @Override
            public ActionItem convert(Action action) {
                return ActionItem.from(action);
            }
        });
    }

}
