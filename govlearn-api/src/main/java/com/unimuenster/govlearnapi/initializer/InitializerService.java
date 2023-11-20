package com.unimuenster.govlearnapi.initializer;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class InitializerService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTagRepository userTagRepository;
    private final CourseTagRepository courseTagRepository;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    private UserEntity user1, user2, recommendationUser;
    private Course course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15;
    private Tag tag1, tag2, tag3, tag4, tag5;
    private UserTag userTag1, userTag2, userTag3, userTag4, userTag5;
    private CourseTag courseTag1, courseTag2, courseTag3, courseTag4, courseTag5;


    public void init() {
        insertUser();
        insertCourse();
        insertMassiveCourseList();
        insertTag();
        addTagsToUsers();
        addTagsToCourses();
    }

    public void insertUser(){
        String test = passwordEncoder.encode("test");

        user1 = new UserEntity();
        user1.setActivated(true);
        user1.setName("test@mail.com");
        user1.setEmail("test");
        user1.setPassword(test);

        userRepository.save(user1);

        String test2 = passwordEncoder.encode("test2");
        user2 = new UserEntity();
        user2.setActivated(true);
        user2.setName("test2@mail.com");
        user2.setEmail("test2");
        user2.setPassword(test2);

        userRepository.save(user2);

        String recommendationUserPasswort = passwordEncoder.encode("test3@mail.com");
        recommendationUser = new UserEntity();
        recommendationUser.setActivated(true);
        recommendationUser.setName("test3");
        recommendationUser.setEmail("test3");
        recommendationUser.setPassword(recommendationUserPasswort);

        userRepository.save(recommendationUser);
    }

    public void insertCourse(){
        course1 = new Course();
        course1.setName("course 1");
        course1.setCreator(user1);
        course1.setDescription("description 1");
        course1.setProvider("provider 1");
        course1.setStartDate(new Date());
        course1.setLink("");

        courseRepository.save(course1);

        course2 = new Course();
        course2.setName("course 2");
        course2.setCreator(user2);
        course2.setDescription("description 2");
        course2.setProvider("provider 2");
        course2.setStartDate(new Date());
        course2.setLink("");

        courseRepository.save(course2);

        course3 = new Course();
        course3.setName("course 3");
        course3.setCreator(user1);
        course3.setDescription("description 3");
        course3.setProvider("provider 3");
        course3.setStartDate(new Date());
        course3.setLink("");

        courseRepository.save(course3);

        course4 = new Course();
        course4.setName("course 4");
        course4.setCreator(user2);
        course4.setDescription("description 4");
        course4.setProvider("provider 4");
        course4.setStartDate(new Date());
        course4.setLink("");

        courseRepository.save(course4);

        course5 = new Course();
        course5.setName("course 5");
        course5.setCreator(user1);
        course5.setDescription("description 5");
        course5.setProvider("provider 5");
        course5.setStartDate(new Date());
        course5.setLink("");

        courseRepository.save(course5);

        course6 = new Course();
        course6.setName("course 6");
        course6.setCreator(user1);
        course6.setDescription("description 6");
        course6.setProvider("provider 6");
        course6.setStartDate(new Date());
        course6.setLink("");

        courseRepository.save(course6);

        course7 = new Course();
        course7.setName("course 7");
        course7.setCreator(user2);
        course7.setDescription("description 7");
        course7.setProvider("provider 7");
        course7.setStartDate(new Date());
        course7.setLink("");

        courseRepository.save(course7);

        course8 = new Course();
        course8.setName("course 8");
        course8.setCreator(user1);
        course8.setDescription("description 8");
        course8.setProvider("provider 8");
        course8.setStartDate(new Date());
        course8.setLink("");

        courseRepository.save(course8);

        course9 = new Course();
        course9.setName("course 9");
        course9.setCreator(user2);
        course9.setDescription("description 9");
        course9.setProvider("provider 9");
        course9.setStartDate(new Date());
        course9.setLink("");

        courseRepository.save(course9);

        course10 = new Course();
        course10.setName("course 10");
        course10.setCreator(user1);
        course10.setDescription("description 10");
        course10.setProvider("provider 10");
        course10.setStartDate(new Date());
        course10.setLink("");

        courseRepository.save(course10);

        course11 = new Course();
        course11.setName("course 11");
        course11.setCreator(user1);
        course11.setDescription("description 11");
        course11.setProvider("provider 11");
        course11.setStartDate(new Date());
        course11.setLink("");

        courseRepository.save(course1);

        course12 = new Course();
        course12.setName("course 12");
        course12.setCreator(user2);
        course12.setDescription("description 12");
        course12.setProvider("provider 12");
        course12.setStartDate(new Date());
        course12.setLink("");

        courseRepository.save(course12);

        course13 = new Course();
        course13.setName("course 13");
        course13.setCreator(user1);
        course13.setDescription("description 13");
        course13.setProvider("provider 13");
        course13.setStartDate(new Date());
        course13.setLink("");

        courseRepository.save(course13);

        course14 = new Course();
        course14.setName("course 14");
        course14.setCreator(user2);
        course14.setDescription("description 14");
        course14.setProvider("provider 14");
        course14.setStartDate(new Date());
        course14.setLink("");

        courseRepository.save(course14);

        course15 = new Course();
        course15.setName("course 15");
        course15.setCreator(user1);
        course15.setDescription("description 15");
        course15.setProvider("provider 15");
        course15.setStartDate(new Date());
        course15.setLink("");

        courseRepository.save(course15);
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

        tag3 = new Tag();
        tag3.setName("Tag 3");
        tag3.setCategory("Category 3");

        tagRepository.save(tag3);

        tag4 = new Tag();
        tag4.setName("Tag 4");
        tag4.setCategory("Category 4");

        tagRepository.save(tag4);

        tag5 = new Tag();
        tag5.setName("Tag 5");
        tag5.setCategory("Category 5");

        tagRepository.save(tag5);
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

    private void insertMassiveCourseList(){
        ClassPathResource resource = new ClassPathResource("course.sql");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
