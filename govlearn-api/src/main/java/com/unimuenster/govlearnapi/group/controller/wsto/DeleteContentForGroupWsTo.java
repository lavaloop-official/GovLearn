package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteContentForGroupWsTo {
    private Long groupId;
    private Long courseId;
}
