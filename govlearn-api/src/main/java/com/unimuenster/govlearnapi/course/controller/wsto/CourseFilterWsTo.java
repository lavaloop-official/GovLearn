package com.unimuenster.govlearnapi.course.controller.wsto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;

import lombok.Builder;
import lombok.Data;

import com.unimuenster.govlearnapi.core.config.enums.Format;

@Data
@Builder
public class CourseFilterWsTo {
    @Builder.Default
    List<Long> tagIDs = new ArrayList<>();
    @Builder.Default
    List<String> Anbieter = new ArrayList<>();;
    Boolean Verwaltungsspezifisch;
    Boolean Zertifikat;
    @Builder.Default
    List<Skilllevel> Kompetenzstufe = new ArrayList<>();;
    Integer DauerInMinLaengerAls;
    Integer DauerInMinKuerzerAls;
    @Builder.Default
    List<Format> Format = new ArrayList<>();;
    Date Startdatum;
    Boolean Kostenlos;
}
