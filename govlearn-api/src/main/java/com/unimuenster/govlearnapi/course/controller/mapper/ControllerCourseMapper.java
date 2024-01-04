package com.unimuenster.govlearnapi.course.controller.mapper;

import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ControllerCourseMapper {

    public CourseCreationDTO map(CourseCreationWsTo courseCreationWsTo){
        return new CourseCreationDTO(
                courseCreationWsTo.name(),
                courseCreationWsTo.image(),
                courseCreationWsTo.description(),
                courseCreationWsTo.createdAt(),
                courseCreationWsTo.provider(),
                courseCreationWsTo.instructor(),
                courseCreationWsTo.certificate(),
                courseCreationWsTo.skilllevel(),
                convertToMinutes(courseCreationWsTo.durationInHours()),
                courseCreationWsTo.format(),
                courseCreationWsTo.startDate(),
                courseCreationWsTo.costFree(),
                courseCreationWsTo.domainSpecific(),
                courseCreationWsTo.link()
        );
    }

    public CourseWsTo map(CourseDTO courseDTO) {
        return CourseWsTo
                .builder()
                .id(courseDTO.id())
                .name(courseDTO.name())
                .image(courseDTO.image())
                .description(courseDTO.description())
                .createdAt(courseDTO.createdAt())
                .provider(courseDTO.provider())
                .instructor(courseDTO.instructor())
                .certificate(courseDTO.certificate())
                .skilllevel(courseDTO.skilllevel())
                .durationInHours(convertToHours(courseDTO.durationInMinutes()))
                .format(courseDTO.format())
                .startDate(courseDTO.startDate())
                .costFree(courseDTO.costFree())
                .domainSpecific(courseDTO.domainSpecific())
                .link(courseDTO.link())
                .build();
    }

    public static int convertToMinutes(String input) {
        Pattern pattern = Pattern.compile("(\\d+\\.?\\d*)\\s+Stunden?");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            double hours = Double.parseDouble(matcher.group(1));
            return (int) (hours * 60);
        } else {
            System.out.println("Invalid input format");
            return -1; // or throw an exception, depending on your requirements
        }
    }

    public static String convertToHours(int minutes) {
        if (minutes < 0) {
            System.out.println("Invalid input: minutes should be non-negative");
            return ""; // or throw an exception, depending on your requirements
        }

        double hours = (double) minutes / 60;
        String formattedHours = (hours % 1 == 0)
                ? String.format("%.0f", hours)
                : String.format("%.1f", hours).replace(",", ".");

        if ( formattedHours.endsWith(".0") ) {
            formattedHours = formattedHours.substring(0, formattedHours.length() - 2);
        }

        String hourSuffix = " Stunden";
        if ( formattedHours.equals("1") ) {
            hourSuffix = " Stunde";
        }

        return formattedHours + hourSuffix;
    }

    public List<CourseWsTo> mapList(List<CourseDTO> courseDTO) {
        return courseDTO
                .stream()
                .map(courseDTO1 -> map(courseDTO1))
                .collect(Collectors.toList());
    }
}
