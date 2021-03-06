package org.ei.drishti.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.motechproject.util.DateUtil;

import java.util.Map;

@TypeDiscriminator("doc.type === 'Action'")
public class Action extends MotechBaseDataObject {
    @JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String caseID;
    @JsonProperty
    private Map<String, String> data;
    @JsonProperty
    private String actionType;
    @JsonProperty
    private long timeStamp;

    private Action() {
    }

    public Action(String caseId, String anmIdentifier, ActionData actionData) {
        this.anmIdentifier = anmIdentifier;
        this.caseID = caseId;
        this.data = actionData.data();
        this.actionType = actionData.type();
        this.timeStamp = DateUtil.now().getMillis();
    }

    public String anmIdentifier() {
        return anmIdentifier;
    }

    public String caseID() {
        return caseID;
    }

    public Map<String, String> data() {
        return data;
    }

    public String actionType() {
        return actionType;
    }

    public long timestamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false, null, new String[]{"timeStamp"});
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
