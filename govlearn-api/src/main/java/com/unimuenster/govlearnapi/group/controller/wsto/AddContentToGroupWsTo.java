package com.unimuenster.govlearnapi.group.controller.wsto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class AddContentToGroupWsTo {
    private Long groupId;
    private Long courseId;
}
