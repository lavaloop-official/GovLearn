package com.unimuenster.govlearnapi;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
@Getter
@Slf4j
@RequiredArgsConstructor
public class Initializer {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTagRepository userTagRepository;
    private final CourseTagRepository courseTagRepository;

    private UserEntity user1, user2, recommendationUser;
    private Course course1, course2;
    private Tag tag1, tag2;
    private UserTag userTag1, userTag2;
    private CourseTag courseTag1, courseTag2;


    @Bean
    public CommandLineRunner appReady(){
        return args -> {
            System.out.println("Initializing database");
            insertUser();
            insertCourse();
            insertTag();
            addTagsToUsers();
            addTagsToCourses();
        };
    }



    public void insertUser(){
        String test = passwordEncoder.encode("test");

        user1 = new UserEntity();
        user1.setActivated(true);
        user1.setName("test");
        user1.setEmail("test");
        user1.setPassword(test);

        userRepository.save(user1);

        String test2 = passwordEncoder.encode("test2");
        user2 = new UserEntity();
        user2.setActivated(true);
        user2.setName("test2");
        user2.setEmail("test2");
        user2.setPassword(test2);

        userRepository.save(user2);

        String recommendationUserPasswort = passwordEncoder.encode("test3");
        recommendationUser = new UserEntity();
        recommendationUser.setActivated(true);
        recommendationUser.setName("test3");
        recommendationUser.setEmail("test3");
        recommendationUser.setPassword(recommendationUserPasswort);

        userRepository.save(recommendationUser);
    }

    public void insertCourse(){
        course1 = new Course();
        course1.setDescription("course 1");
        course1.setCreator(user1);

        courseRepository.save(course1);

        course2 = new Course();
        course2.setDescription("course 2");
        course2.setCreator(user2);

        courseRepository.save(course2);
    }

    public void insertTag(){
        tag1 = new Tag();
        tag1.setName("Tag 1");
        tag1.setCategory("Category 1");

        tagRepository.save(tag1);

        tag2 = new Tag();
        tag2.setName("Tag 2");
        tag2.setCategory("Category 2");

        tagRepository.save(tag2);
    }

    private void addTagsToUsers() {

        userTag1 = new UserTag();
        userTag1.setUser(recommendationUser);
        userTag1.setRating(2);
        userTag1.setTag(tag1);

        userTagRepository.save(userTag1);

        userTag2 = new UserTag();
        userTag2.setUser(user2);
        userTag2.setRating(2);
        userTag2.setTag(tag2);

        userTagRepository.save(userTag2);
    }

    private void addTagsToCourses() {

        courseTag1 = new CourseTag();
        courseTag1.setCourse(course1);
        courseTag1.setTag(tag1);

        courseTagRepository.save(courseTag1);

        courseTag2 = new CourseTag();
        courseTag2.setCourse(course2);
        courseTag2.setTag(tag2);

        courseTagRepository.save(courseTag2);
    }
}
