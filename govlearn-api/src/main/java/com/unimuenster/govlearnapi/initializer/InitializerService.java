package com.unimuenster.govlearnapi.initializer;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.core.config.security.CustomUserDetails;
import com.unimuenster.govlearnapi.core.config.security.JwtService;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.user.entity.Token;
import com.unimuenster.govlearnapi.user.entity.TokenType;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.TokenRepository;
import com.unimuenster.govlearnapi.user.repository.UserRepository;

import com.unimuenster.govlearnapi.user.service.dto.TokenDTO;
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
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    private UserEntity user1, user2, recommendationUser;
    private TokenDTO user1Token, user2Token, recommendationUserToken;
    private String clearPassword = "test";
    private Course course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15;
    private Tag tag1, tag2, tag3, tag4, tag5, tag6;
    private UserTag userTag1, userTag2, userTag3, userTag4, userTag5;
    private CourseTag courseTag1, courseTag2, courseTag3, courseTag4, courseTag5, courseTag6, courseTag7, courseTag8, courseTag9, courseTag10, courseTag11, courseTag12, courseTag13, courseTag14, courseTag15, courseTag16, courseTag17, courseTag18;
    private Category category1, category2, category3, category4, category5;

    public void init() {
        insertUser();
        insertCategories();
        insertCourse();
        //insertMassiveCourseList();
        insertTag();
        addTagsToUsers();
        addTagsToCourses();
        addBookmarkToUser();
    }

    private TokenDTO authenticate(UserEntity user){
        // Normaler AuthenticationService kann nicht verwendet werden,
        // da updating/deleting queries ausgeführt werden und dies im Testkontext Fehler schmeisst.
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        String jwt = jwtService.generateToken(customUserDetails);
        tokenRepository.save(Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build());

        return new TokenDTO(jwt);
    }

    public void insertUser(){
        String test = passwordEncoder.encode(clearPassword);

        user1 = new UserEntity();
        user1.setActivated(true);
        user1.setName("test");
        user1.setEmail("test@mail.com");
        user1.setPassword(test);

        userRepository.save(user1);
        user1Token = authenticate(user1);

        String test2 = passwordEncoder.encode(clearPassword);
        user2 = new UserEntity();
        user2.setActivated(true);
        user2.setName("test2");
        user2.setEmail("test2@mail.com");
        user2.setPassword(test2);

        userRepository.save(user2);
        user2Token = authenticate(user2);

        String recommendationUserPasswort = passwordEncoder.encode(clearPassword);
        recommendationUser = new UserEntity();
        recommendationUser.setActivated(true);
        recommendationUser.setName("test3");
        recommendationUser.setEmail("test3@mail.com");
        recommendationUser.setPassword(recommendationUserPasswort);

        userRepository.save(recommendationUser);
        recommendationUserToken = authenticate(recommendationUser);
    }

    private void insertCategories() {
        category1 = new Category();
        category1.setName("Lerne Scrum");

        categoryRepository.save(category1);

        category2 = new Category();
        category2.setName("Category 2");

        categoryRepository.save(category2);

        category3 = new Category();
        category3.setName("E-Government");

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
        course1.setCreator(user1);
        course1.setStartDate(new Date());
        course1.setLink("");
        course1.setName("Scrum für den öffentlichen Dienst");
        course1.setDescription("Ein Kurs, der grundlegende Prinzipien von Scrum für Mitarbeiter im öffentlichen Dienst vermittelt.");
        course1.setProvider("Digitale Bildungsinstitute GmbH");
        course1.setImage("https://t3.ftcdn.net/jpg/03/01/70/16/360_F_301701619_I7tuZjCIeb5erP72AJgY7Up29h8fHLLP.jpg");
        course1.setDurationInMinutes(120);
        course1.setSkilllevel(Skilllevel.Anfaenger);
        course1.setFormat(Format.Hybrid);
        course1.setCostFree(false);

        courseRepository.save(course1);

        course2 = new Course();
        course2.setCreator(user2);
        course2.setStartDate(new Date());
        course2.setLink("");
        course2.setName("Datenmanagement im öffentlichen Sektor");
        course2.setDescription("Ein Schulungsprogramm zur sicheren Handhabung von Daten und Einhaltung von Datenschutzbestimmungen.");
        course2.setProvider("DataSkills Academy");
        course2.setImage("https://www.shutterstock.com/blog/wp-content/uploads/sites/5/2019/09/4-3.jpg");
        course2.setDurationInMinutes(120);
        course2.setSkilllevel(Skilllevel.Fortgeschritten);
        course2.setFormat(Format.Hybrid);
        course2.setCostFree(true);
        course2.setDomainSpecific(true);

        courseRepository.save(course2);

        course3 = new Course();
        course3.setName("E-Government und digitale Transformation");
        course3.setCreator(user1);
        course3.setDescription("Schulungen zur Implementierung von E-Government-Diensten und digitalen Transformationsprozessen.");
        course3.setProvider("GovTech Solutions");
        course3.setStartDate(new Date());
        course3.setLink("");
        course3.setImage("https://media.istockphoto.com/id/498323251/de/foto/colleagues-discussing-over-digital-tablet.jpg?s=612x612&w=0&k=20&c=L9wHTzShsicC42CDSUilPZ4N8YduB2V6Zr_ffp9mxaU=");
        course3.setDurationInMinutes(120);
        course3.setSkilllevel(Skilllevel.Fortgeschritten);
        course3.setFormat(Format.Hybrid);
        course3.setCostFree(false);

        courseRepository.save(course3);

        course4 = new Course();
        course4.setName("course 4");
        course4.setCreator(user2);
        course4.setDescription("description 4");
        course4.setProvider("provider 4");
        course4.setStartDate(new Date());
        course4.setLink("");
        course4.setImage("https://media.istockphoto.com/id/682143876/de/foto/kleine-unternehmen-arbeiten-im-b%C3%BCro.jpg?s=612x612&w=0&k=20&c=mGKkkC43B0kl2w6efy5XWY7Wh29mN5WEaqak67DVHeY=");
        course4.setDurationInMinutes(121);
        course4.setSkilllevel(Skilllevel.Fortgeschritten);
        course4.setFormat(Format.Hybrid);
        course4.setCostFree(false);

        courseRepository.save(course4);

        course5 = new Course();
        course5.setName("course 5");
        course5.setCreator(user1);
        course5.setDescription("description 5");
        course5.setProvider("provider 5");
        course5.setStartDate(new Date());
        course5.setLink("");
        course5.setImage("https://media.istockphoto.com/id/682143876/de/foto/kleine-unternehmen-arbeiten-im-b%C3%BCro.jpg?s=612x612&w=0&k=20&c=mGKkkC43B0kl2w6efy5XWY7Wh29mN5WEaqak67DVHeY=");
        course5.setDurationInMinutes(125);
        course5.setSkilllevel(Skilllevel.Fortgeschritten);
        course5.setFormat(Format.Hybrid);
        course5.setCostFree(true);

        courseRepository.save(course5);

        course6 = new Course();
        course6.setName("course 6");
        course6.setCreator(user1);
        course6.setDescription("description 6");
        course6.setProvider("provider 6");
        course6.setStartDate(new Date());
        course6.setLink("");
        course6.setImage("https://media.istockphoto.com/id/682143876/de/foto/kleine-unternehmen-arbeiten-im-b%C3%BCro.jpg?s=612x612&w=0&k=20&c=mGKkkC43B0kl2w6efy5XWY7Wh29mN5WEaqak67DVHeY=");
        course6.setDurationInMinutes(120);
        course6.setSkilllevel(Skilllevel.Fortgeschritten);
        course6.setFormat(Format.Hybrid);
        course6.setCertificate(true);
        course6.setCostFree(false);

        courseRepository.save(course6);

        course7 = new Course();
        course7.setName("course 7");
        course7.setCreator(user2);
        course7.setDescription("description 7");
        course7.setProvider("provider 7");
        course7.setStartDate(new Date());
        course7.setLink("");
        course7.setImage("https://media.istockphoto.com/id/682143876/de/foto/kleine-unternehmen-arbeiten-im-b%C3%BCro.jpg?s=612x612&w=0&k=20&c=mGKkkC43B0kl2w6efy5XWY7Wh29mN5WEaqak67DVHeY=");
        course7.setDurationInMinutes(120);
        course7.setSkilllevel(Skilllevel.Fortgeschritten);
        course7.setFormat(Format.Hybrid);
        course7.setCostFree(true);

        courseRepository.save(course7);

        course8 = new Course();
        course8.setName("course 8");
        course8.setCreator(user1);
        course8.setDescription("description 8");
        course8.setProvider("provider 8");
        course8.setStartDate(new Date());
        course8.setLink("");
        course8.setImage("https://media.istockphoto.com/id/682143876/de/foto/kleine-unternehmen-arbeiten-im-b%C3%BCro.jpg?s=612x612&w=0&k=20&c=mGKkkC43B0kl2w6efy5XWY7Wh29mN5WEaqak67DVHeY=");
        course8.setDurationInMinutes(120);
        course8.setSkilllevel(Skilllevel.Fortgeschritten);
        course8.setFormat(Format.Hybrid);
        course8.setCostFree(false);

        courseRepository.save(course8);

        course9 = new Course();
        course9.setName("course 9");
        course9.setCreator(user2);
        course9.setDescription("description 9");
        course9.setProvider("provider 9");
        course9.setStartDate(new Date());
        course9.setLink("");
        course9.setCostFree(true);

        courseRepository.save(course9);

        course10 = new Course();
        course10.setName("course 10");
        course10.setCreator(user1);
        course10.setDescription("description 10");
        course10.setProvider("provider 10");
        course10.setStartDate(new Date());
        course10.setLink("");
        course10.setCostFree(false);

        courseRepository.save(course10);

        course11 = new Course();
        course11.setName("course 11");
        course11.setCreator(user1);
        course11.setDescription("description 11");
        course11.setProvider("provider 11");
        course11.setStartDate(new Date(94583443543244L));
        course11.setLink("");
        course11.setCostFree(true);

        courseRepository.save(course11);

        course12 = new Course();
        course12.setName("course 12");
        course12.setCreator(user2);
        course12.setDescription("description 12");
        course12.setProvider("provider 12");
        course12.setStartDate(new Date());
        course12.setLink("");
        course12.setCostFree(false);

        courseRepository.save(course12);

        course13 = new Course();
        course13.setName("course 13");
        course13.setCreator(user1);
        course13.setDescription("description 13");
        course13.setProvider("provider 13");
        course13.setStartDate(new Date());
        course13.setLink("");
        course13.setCostFree(true);

        courseRepository.save(course13);

        course14 = new Course();
        course14.setName("course 14");
        course14.setCreator(user2);
        course14.setDescription("description 14");
        course14.setProvider("provider 14");
        course14.setStartDate(new Date());
        course14.setDurationInMinutes(120);
        course14.setLink("");
        course14.setCostFree(true);

        courseRepository.save(course14);

        course15 = new Course();
        course15.setName("course 15");
        course15.setCreator(user1);
        course15.setDescription("description 15");
        course15.setProvider("provider 15");
        course15.setStartDate(new Date());
        course15.setDurationInMinutes(10);
        course15.setLink("");
        course15.setCostFree(false);

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

        tag6 = new Tag();
        tag6.setName("Tag 6");
        tag6.setCategory(category1);

        tagRepository.save(tag6);
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
        courseTag4.setTag(tag3);

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
        courseTag9.setTag(tag5);
        courseTag9.setTag(tag6);

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

        courseTag16 = new CourseTag();
        courseTag16.setCourse(course6);
        courseTag16.setTag(tag2);

        courseTagRepository.save(courseTag16);

        courseTag17 = new CourseTag();
        courseTag17.setCourse(course11);
        courseTag17.setTag(tag2);

        courseTagRepository.save(courseTag17);

        courseTag18 = new CourseTag();
        courseTag18.setCourse(course3);
        courseTag18.setTag(tag1);

        courseTagRepository.save(courseTag18);
    }

    private void addBookmarkToUser(){
        this.getUser1().getBookmarked().add(this.getCourse1());
        this.getCourse1().getBookmarkedBy().add(this.getUser1());
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
    public void dropAllTables() throws Exception{
        Query dropAllTables = entityManager.createNativeQuery("""
            DROP TABLE bookmark CASCADE;
            DROP TABLE course_tag CASCADE;
            DROP TABLE course CASCADE;
            DROP TABLE feedback_report CASCADE;
            DROP TABLE feedback CASCADE;
            DROP TABLE tag CASCADE;
            DROP TABLE token CASCADE;
            DROP TABLE user_entity CASCADE;
            DROP TABLE user_tag CASCADE; 
            DROP TABLE category CASCADE;
            DROP TABLE role_role_tags CASCADE;
            DROP TABLE role CASCADE;
            DROP TABLE role_tag CASCADE;
            """
        );
        try {
            dropAllTables.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
}