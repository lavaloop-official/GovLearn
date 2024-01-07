package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrieveInvitationWsTo {

    Long groupId;
    String groupName;
    String groupDescription;
    Long invitationId;

}
