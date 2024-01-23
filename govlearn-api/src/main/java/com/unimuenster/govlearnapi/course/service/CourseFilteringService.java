package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseFilterWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseFilterRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.tags.service.TagService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseFilteringService {
    private final CourseFilterRepository courseFilterRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final TagService tagService;
    private final CourseService courseService;

    public List<CourseDTO> filterCourses(Integer limit, Integer offset, Optional<String> nameSearch, CourseFilterWsTo courseFilterWsTo) {

        String search = extractSearchString(nameSearch);

        validateTags(courseFilterWsTo);

        validateAnbieter(courseFilterWsTo);

        validateKompetenzstufen(courseFilterWsTo);

        List<Long> kompetenzstufen = extractKompetenzstufen(courseFilterWsTo);

        List<Long> formate = extractAndSetFormate(courseFilterWsTo);

        validateStartDatum(courseFilterWsTo);

        List<Course> results = courseFilterRepository
                .findCoursesByAttributesAndTags(
                        limit,
                        offset,
                        search,
                        courseFilterWsTo.getAnbieter(),
                        formate,
                        kompetenzstufen,
                        courseFilterWsTo.getTagIDs(),
                        courseFilterWsTo.getKostenlos(),
                        courseFilterWsTo.getVerwaltungsspezifisch(),
                        courseFilterWsTo.getZertifikat(),
                        // Startdatum bedeutet, der Kurs noch nicht gestartet
                        courseFilterWsTo.getStartdatum(),
                        courseFilterWsTo.getDauerInMinLaengerAls(),
                        courseFilterWsTo.getDauerInMinKuerzerAls()
                );

        return mapResults(results);
    }

    private List<CourseDTO> mapResults(List<Course> results) {
        return results.stream().map( course -> serviceCourseMapper.map(course) ).collect(Collectors.toList());
    }

    private String extractSearchString(Optional<String> nameSearch) {
        String search = null;

        if(nameSearch.isPresent())
        {
            search = "%"+nameSearch.get()+"%";
        }

        return search;
    }

    private void validateTags(CourseFilterWsTo courseFilterWsTo) {
        if (courseFilterWsTo.getTagIDs().isEmpty()) {
            courseFilterWsTo.setTagIDs(tagService.getTags().stream().map(tag -> tag.id()).toList());
        }
    }

    private void validateAnbieter(CourseFilterWsTo courseFilterWsTo) {
        if (courseFilterWsTo.getAnbieter().isEmpty()) {
            courseFilterWsTo.setAnbieter(courseService.getAllCourseProviders().stream().toList());
        }
    }

    private void validateKompetenzstufen(CourseFilterWsTo courseFilterWsTo) {
        if (courseFilterWsTo.getKompetenzstufe().isEmpty()) {
            courseFilterWsTo.setKompetenzstufe(Arrays.asList(Skilllevel.Anfaenger,Skilllevel.Fortgeschritten, Skilllevel.Experte));
        }
    }

    private List<Long> extractKompetenzstufen(CourseFilterWsTo courseFilterWsTo) {
        List<Long> kompetenzstufen = new ArrayList<Long>();
        for (Skilllevel skilllevel : courseFilterWsTo.getKompetenzstufe()) {
            kompetenzstufen.add(skilllevel.getLongValue());
        }

        return kompetenzstufen;
    }

    private List<Long> extractAndSetFormate(CourseFilterWsTo courseFilterWsTo) {
        if (courseFilterWsTo.getFormat().isEmpty()) {
            courseFilterWsTo.setFormat(Arrays.asList(Format.OnlineLive,Format.Praesenz, Format.Hybrid, Format.OnlineSelbstorganisiert));
        }

        List<Long> formate = new ArrayList<>();

        for (Format format : courseFilterWsTo.getFormat()) {
            formate.add(format.getLongValue());
        }

        return formate;
    }

    private void validateStartDatum(CourseFilterWsTo courseFilterWsTo) {
        if (courseFilterWsTo.getStartdatum() == null) {
            courseFilterWsTo.setStartdatum(getEarliestDate());
        }
    }

    private static Date getEarliestDate(){
        // January 1, 1970
        return new Date(0);
    }
}
