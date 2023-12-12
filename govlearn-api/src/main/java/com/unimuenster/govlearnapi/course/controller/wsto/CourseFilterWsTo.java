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
    List<String> Wissensbezug;
    Boolean Verwaltungsspezifisch;
    Boolean Zertifikat;
    List<Skilllevel> Kompetenzstufe;
    List<String> Dauer;
    List<Format> Format;
    Date Startdatum;
    Boolean Kosten;
    List<String> Sonstiges;
}
