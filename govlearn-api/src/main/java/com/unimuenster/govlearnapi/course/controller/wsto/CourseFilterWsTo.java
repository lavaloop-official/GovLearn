package com.unimuenster.govlearnapi.course.controller.wsto;

import java.util.Date;
import java.util.List;

import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;

import lombok.Builder;
import lombok.Data;

import com.unimuenster.govlearnapi.core.config.enums.Format;

@Data
@Builder
public class CourseFilterWsTo {
    List<Long> tagIDs;
    List<String> Anbieter;
    Boolean Verwaltungsspezifisch;
    Boolean Zertifikat;
    List<Skilllevel> Kompetenzstufe;
    Integer DauerInMinLaengerAls;
    Integer DauerInMinKuerzerAls;
    List<Format> Format;
    Date Startdatum;
    Boolean Kostenlos;
}
