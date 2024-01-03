package com.unimuenster.govlearnapi.course.service;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseFilterWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.course.service.mapper.ServiceCourseMapper;
import com.unimuenster.govlearnapi.tags.service.TagService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CourseFilteringService {
    private final CourseRepository courseRepository;
    private final ServiceCourseMapper serviceCourseMapper;
    private final TagService tagService;
    private final CourseService courseService;

    public List<CourseDTO> filterCourses(Integer limit, Integer offset, Optional<String> nameSearch, CourseFilterWsTo courseFilterWsTo) {

        String search = "%%";
        if(nameSearch.isPresent())
        {
            search = "%"+nameSearch.get()+"%";
        }

        if (courseFilterWsTo.getTagIDs().isEmpty()) {
            courseFilterWsTo.setTagIDs(tagService.getTags().stream().map(tag -> tag.id()).toList());
        }

        if (courseFilterWsTo.getAnbieter().isEmpty()) {
            courseFilterWsTo.setAnbieter(courseService.getAllCourseProviders().stream().toList());
        }

        if (courseFilterWsTo.getKompetenzstufe().isEmpty()) {
            courseFilterWsTo.setKompetenzstufe(Arrays.asList(Skilllevel.Anfaenger,Skilllevel.Fortgeschritten, Skilllevel.Experte));
        }
        List<Long> Kompetenzstufen = new ArrayList<Long>();
        for (Skilllevel skilllevel : courseFilterWsTo.getKompetenzstufe()) {
            Kompetenzstufen.add(skilllevel.getLongValue());
        }

        if (courseFilterWsTo.getFormat().isEmpty()) {
            courseFilterWsTo.setFormat(Arrays.asList(Format.OnlineLive,Format.Praesenz, Format.Hybrid, Format.OnlineSelbstorganisiert));
        }
        List<Long> Formate = new ArrayList<Long>();
        for (Format format : courseFilterWsTo.getFormat()) {
            Formate.add(format.getLongValue());
        }

        if (courseFilterWsTo.getStartdatum() == null) {
            courseFilterWsTo.setStartdatum(getEarliestDate());
        }

        List<Course> results = courseRepository
                .findCoursesByAttributesAndTags(
                        limit,
                        offset,
                        search,
                        courseFilterWsTo.getAnbieter(),
                        Formate,
                        Kompetenzstufen,
                        courseFilterWsTo.getTagIDs(),
                        courseFilterWsTo.getKostenlos(),
                        courseFilterWsTo.getVerwaltungsspezifisch(),
                        courseFilterWsTo.getZertifikat(),
                        // Startdatum bedeutet, der Kurs noch nicht gestartet
                        courseFilterWsTo.getStartdatum(),
                        courseFilterWsTo.getDauerInMinLaengerAls(),
                        courseFilterWsTo.getDauerInMinKuerzerAls()
                );

        return results.stream().map( course -> serviceCourseMapper.map(course) ).collect(Collectors.toList());
    }

    private static Date getEarliestDate(){
        // January 1, 1970
        return new Date(0);
    }
}
