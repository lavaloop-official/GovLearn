package com.unimuenster.govlearnapi.initializer;

import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.entity.Category;
import com.unimuenster.govlearnapi.tags.entity.Category;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.CategoryRepository;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

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
    private final CategoryRepository categoryRepository;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    private UserEntity user1, user2, recommendationUser;
    private Course course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15;
    private Tag tag1, tag2, tag3, tag4, tag5;
    private UserTag userTag1, userTag2, userTag3, userTag4, userTag5;
    private CourseTag courseTag1, courseTag2, courseTag3, courseTag4, courseTag5, courseTag6, courseTag7, courseTag8, courseTag9, courseTag10, courseTag11, courseTag12, courseTag13, courseTag14, courseTag15;
    private Category category1, category2, category3, category4, category5;

    public void init() {
        insertUser();
        insertCategories();
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
        user1.setName("test");
        user1.setEmail("test@mail.com");
        user1.setPassword(test);

        userRepository.save(user1);

        String test2 = passwordEncoder.encode("test2");
        user2 = new UserEntity();
        user2.setActivated(true);
        user2.setName("test2");
        user2.setEmail("test2@mail.com");
        user2.setPassword(test2);

        userRepository.save(user2);

        String recommendationUserPasswort = passwordEncoder.encode("test3");
        recommendationUser = new UserEntity();
        recommendationUser.setActivated(true);
        recommendationUser.setName("test3");
        recommendationUser.setEmail("test3@mail.com");
        recommendationUser.setPassword(recommendationUserPasswort);

        userRepository.save(recommendationUser);
    }

    private void insertCategories() {
        category1 = new Category();
        category1.setName("Category 1");

        categoryRepository.save(category1);

        category2 = new Category();
        category2.setName("Category 2");

        categoryRepository.save(category2);

        category3 = new Category();
        category3.setName("Category 3");

        categoryRepository.save(category3);

        category4 = new Category();
        category4.setName("Category 4");

        categoryRepository.save(category4);

        category5 = new Category();
        category5.setName("Category 5");

        categoryRepository.save(category5);
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

        courseRepository.save(course11);

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
        tag1.setCategory(category1);

        tagRepository.save(tag1);

        tag2 = new Tag();
        tag2.setName("Tag 2");
        tag2.setCategory(category2);

        tagRepository.save(tag2);

        tag3 = new Tag();
        tag3.setName("Tag 3");
        tag3.setCategory(category3);

        tagRepository.save(tag3);

        tag4 = new Tag();
        tag4.setName("Tag 4");
        tag4.setCategory(category4);

        tagRepository.save(tag4);

        tag5 = new Tag();
        tag5.setName("Tag 5");
        tag5.setCategory(category5);

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

        userTag3 = new UserTag();
        userTag3.setUser(recommendationUser);
        userTag3.setRating(2);
        userTag3.setTag(tag3);

        userTagRepository.save(userTag3);

        userTag4 = new UserTag();
        userTag4.setUser(user2);
        userTag4.setRating(2);
        userTag4.setTag(tag4);

        userTagRepository.save(userTag4);

        userTag5 = new UserTag();
        userTag5.setUser(user2);
        userTag5.setRating(2);
        userTag5.setTag(tag5);

        userTagRepository.save(userTag5);
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

        courseTag3 = new CourseTag();
        courseTag3.setCourse(course3);
        courseTag3.setTag(tag3);

        courseTagRepository.save(courseTag3);

        courseTag4 = new CourseTag();
        courseTag4.setCourse(course4);
        courseTag4.setTag(tag4);

        courseTagRepository.save(courseTag4);

        courseTag5 = new CourseTag();
        courseTag5.setCourse(course5);
        courseTag5.setTag(tag5);

        courseTagRepository.save(courseTag5);

        courseTag6 = new CourseTag();
        courseTag6.setCourse(course6);
        courseTag6.setTag(tag1);

        courseTagRepository.save(courseTag6);

        courseTag7 = new CourseTag();
        courseTag7.setCourse(course7);
        courseTag7.setTag(tag2);

        courseTagRepository.save(courseTag7);

        courseTag8 = new CourseTag();
        courseTag8.setCourse(course8);
        courseTag8.setTag(tag3);

        courseTagRepository.save(courseTag8);

        courseTag9 = new CourseTag();
        courseTag9.setCourse(course9);
        courseTag9.setTag(tag4);

        courseTagRepository.save(courseTag9);

        courseTag10 = new CourseTag();
        courseTag10.setCourse(course10);
        courseTag10.setTag(tag5);

        courseTagRepository.save(courseTag10);

        courseTag11 = new CourseTag();
        courseTag11.setCourse(course11);
        courseTag11.setTag(tag1);

        courseTagRepository.save(courseTag11);

        courseTag12 = new CourseTag();
        courseTag12.setCourse(course12);
        courseTag12.setTag(tag2);

        courseTagRepository.save(courseTag12);

        courseTag13 = new CourseTag();
        courseTag13.setCourse(course13);
        courseTag13.setTag(tag3);

        courseTagRepository.save(courseTag13);

        courseTag14 = new CourseTag();
        courseTag14.setCourse(course14);
        courseTag14.setTag(tag4);

        courseTagRepository.save(courseTag14);

        courseTag15 = new CourseTag();
        courseTag15.setCourse(course15);
        courseTag15.setTag(tag5);

        courseTagRepository.save(courseTag15);
    }

    private void insertMassiveCourseList(){
        ClassPathResource resource = new ClassPathResource("course.sql");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteAllData() throws Exception{
        Query dropAllTables = entityManager.createNativeQuery("""
            DELETE FROM course_tag CASCADE;
            DELETE FROM user_tag CASCADE;
            DELETE FROM course CASCADE;
            DELETE FROM feedback CASCADE;
            DELETE FROM tag CASCADE;
            DELETE FROM token CASCADE;
            DELETE FROM user_entity CASCADE;             
            """
        );
        try {
            dropAllTables.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
}