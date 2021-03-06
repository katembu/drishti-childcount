package org.ei.drishti.web.controller;

import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.ActionData;
import org.ei.drishti.service.ActionService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.motechproject.delivery.schedule.util.SameItems.hasSameItemsAs;

public class ActionControllerTest {

    @Mock
    private ActionService actionService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldGiveAlertActionForANMSinceTimeStamp() throws Exception {
        Action alertAction = new Action("Case X", "ANM 1", ActionData.createAlert("Theresa", "Thaayi 1", "ANC 1", "due", DateTime.now()));
        when(actionService.getNewAlertsForANM("ANM 1", 0L)).thenReturn(Arrays.asList(alertAction));

        ActionItem expectedAlertActionItem = ActionItem.from(alertAction);
        ActionController alertController = new ActionController(actionService);

        assertThat(Arrays.asList(expectedAlertActionItem), hasSameItemsAs(alertController.getNewActionForANM("ANM 1", 0L)));
    }
}
