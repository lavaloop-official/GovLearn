package com.unimuenster.govlearnapi.bookmark.controller;

import com.unimuenster.govlearnapi.bookmark.service.BookmarkService;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.mapper.ControllerCourseMapper;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseWsTo;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.feedback.service.FeedbackService;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
@Slf4j
public class BookmarkController {
    private final AuthenticationService authenticationService;
    private final BookmarkService bookmarkService;
    private final ControllerCourseMapper controllerCourseMapper;
    private final FeedbackService feedbackService;

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Get bookmarked courses of authenticated user."
    )
    @PreAuthorize("hasAuthority('user')")
    @GetMapping()
    public ResponseEntity<Response> getBookmarks(){
        UserEntity currentUser = authenticationService.getCurrentUser();

        List<CourseDTO> bookmarksDTOs = bookmarkService.getBookmarks(currentUser);

        List<CourseWsTo> bookmarksWsTos = controllerCourseMapper.mapList(bookmarksDTOs);

        bookmarksWsTos.stream().forEach(courseWsTo -> {
            courseWsTo.setRatingAverage(feedbackService.getAverageFeedbackByCourseID(courseWsTo.getId()));
            courseWsTo.setRatingAmount(feedbackService.getAmountFeedbackByCourseID(courseWsTo.getId()));
        });

        return ResponseEntity.ok(Response.of(bookmarksWsTos, true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Create bookmark for course for authenticated user."
    )
    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/{courseId}")
    public ResponseEntity<Response> addBookmark(@PathVariable Long courseId){
        UserEntity currentUser = authenticationService.getCurrentUser();

        bookmarkService.addBookmark(currentUser, courseId);

        return ResponseEntity.ok(Response.of(true));
    }

    @Operation(
            security = { @SecurityRequirement(name = "Authorization") },
            description = "Delete bookmark from authenticated user."
    )
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Response> deleteBookmark(@PathVariable Long courseId){

        UserEntity currentUser = authenticationService.getCurrentUser();

        bookmarkService.deleteBookmark(currentUser, courseId);

        return ResponseEntity.ok(Response.of(true));
    }
}
