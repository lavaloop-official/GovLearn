package com.unimuenster.govlearnapi.initializer;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.TagRepository;
import com.unimuenster.govlearnapi.tags.repository.UserTagRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import com.unimuenster.govlearnapi.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class RealInitializerService {
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

    private UserEntity user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11;
    private Course course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15;
    private Category businessCategory, isitManagementCategory, organizationCategory, publicPolicyCategory, lawCategory, otherExperienceCategory, softSkillsCategory, personalityTraitsCategory, analyticalCategory, selfManagementCategory;
    private Tag tagBusinessStrategicPlanning, tagBusinessProjectManagement, tagBusinessProgramManagement, tagIsItSystems, tagItSkills, tagCyberSecurity, tagOrgDesign, tagAdminProcesses, tagCoordination, tagPublicPolicy, tagKnowledgeAgencyPolicy, tagAdminLaw, tagLegalDataManagement, tagProfessionalExperience, tagEvaluationResearch, tagLeadership, tagConflictManagement, tagTolerance, tagContinuousLearning, tagCriticalThinking, tagDecisionMaking, tagSelfOrganization, tagSelfControl;
    private UserTag userTag1, userTag2, userTag3;

    public void init() {
        insertUser();
        insertCategories();
        insertCourse();
        insertTag();
        addTagsToUsers();
        addTagsToCourses();
        addFeedbackToCourses();
    }

    public void insertUser(){
        String test = passwordEncoder.encode("test");

        user1 = new UserEntity();
        user1.setActivated(true);
        user1.setName("Julian Sibbing");
        user1.setEmail("juliansibbing@gmail.com");
        user1.setPassword(test);

        userRepository.save(user1);

        // User 2
        user2 = new UserEntity();
        user2.setActivated(true);
        user2.setName("Anna Müller");
        user2.setEmail("anna.mueller@example.com");
        user2.setPassword(test);
        userRepository.save(user2);

// User 3
        user3 = new UserEntity();
        user3.setActivated(true);
        user3.setName("Maximilian Wagner");
        user3.setEmail("maximilian.wagner@example.com");
        user3.setPassword(test);
        userRepository.save(user3);

        // User 4
        user4 = new UserEntity();
        user4.setActivated(true);
        user4.setName("Sophie Fischer");
        user4.setEmail("sophie.fischer@example.com");
        user4.setPassword(test);
        userRepository.save(user4);

// User 5
        user5 = new UserEntity();
        user5.setActivated(true);
        user5.setName("Alexander Schulz");
        user5.setEmail("alexander.schulz@example.com");
        user5.setPassword(test);
        userRepository.save(user5);

// User 6
        user6 = new UserEntity();
        user6.setActivated(true);
        user6.setName("Lena Becker");
        user6.setEmail("lena.becker@example.com");
        user6.setPassword(test);
        userRepository.save(user6);

// User 7
        user7 = new UserEntity();
        user7.setActivated(true);
        user7.setName("Nico Keller");
        user7.setEmail("nico.keller@example.com");
        user7.setPassword(test);
        userRepository.save(user7);

// User 8
        user8 = new UserEntity();
        user8.setActivated(true);
        user8.setName("Hannah Schmitt");
        user8.setEmail("hannah.schmitt@example.com");
        user8.setPassword(test);
        userRepository.save(user8);

// User 9
        user9 = new UserEntity();
        user9.setActivated(true);
        user9.setName("Finn Braun");
        user9.setEmail("finn.braun@example.com");
        user9.setPassword(test);
        userRepository.save(user9);

// User 10
        user10 = new UserEntity();
        user10.setActivated(true);
        user10.setName("Elena Keller");
        user10.setEmail("elena.keller@example.com");
        user10.setPassword(test);
        userRepository.save(user10);

        user11 = new UserEntity();
        user11.setActivated(true);
        user11.setName("Fabian");
        user11.setEmail("fuhlit@uni-muenster.de");
        user11.setPassword(test);
        userRepository.save(user11);

    }

    private void insertCategories() {
// Business Category
        businessCategory = new Category();
        businessCategory.setName("Business");
        categoryRepository.save(businessCategory);

// IS/IT Management Category
        isitManagementCategory = new Category();
        isitManagementCategory.setName("IS/IT Management");
        categoryRepository.save(isitManagementCategory);

// Organization Category
        organizationCategory = new Category();
        organizationCategory.setName("Organization");
        categoryRepository.save(organizationCategory);

// Public Policy Category
        publicPolicyCategory = new Category();
        publicPolicyCategory.setName("Public Policy");
        categoryRepository.save(publicPolicyCategory);

// Law Category
        lawCategory = new Category();
        lawCategory.setName("Law");
        categoryRepository.save(lawCategory);

// Other Professional Experience Category
        otherExperienceCategory = new Category();
        otherExperienceCategory.setName("Other Professional Experience");
        categoryRepository.save(otherExperienceCategory);

// Soft Skills Category
        softSkillsCategory = new Category();
        softSkillsCategory.setName("Soft Skills");
        categoryRepository.save(softSkillsCategory);

// Personality Traits Category
        personalityTraitsCategory = new Category();
        personalityTraitsCategory.setName("Personality Traits");
        categoryRepository.save(personalityTraitsCategory);

// Analytical Category
        analyticalCategory = new Category();
        analyticalCategory.setName("Analytical");
        categoryRepository.save(analyticalCategory);

// Self-Management Category
        selfManagementCategory = new Category();
        selfManagementCategory.setName("Self-Management");
        categoryRepository.save(selfManagementCategory);
    }

    public void insertCourse(){
        // Course 1
        course1 = new Course();
        course1.setCreator(this.getUser1());
        course1.setStartDate(new Date());
        course1.setLink("");
        course1.setName("Scrum für den öffentlichen Dienst");
        course1.setDescription("Ein Kurs, der grundlegende Prinzipien von Scrum für Mitarbeiter im öffentlichen Dienst vermittelt.");
        course1.setProvider("Digitale Bildungsinstitute GmbH");
        course1.setImage("https://t3.ftcdn.net/jpg/03/01/70/16/360_F_301701619_I7tuZjCIeb5erP72AJgY7Up29h8fHLLP.jpg");
        course1.setDuration("2 Stunden");
        course1.setSkilllevel(Skilllevel.Anfaenger);
        course1.setFormat(Format.Hybrid);
        course1.setDomainSpecific(true);
        course1.setCostFree(true);
        course1.setCertificate(true);

        courseRepository.save(course1);

        // Course 2
        course2 = new Course();
        course2.setCreator(user2);
        course2.setStartDate(new Date());
        course2.setLink("");
        course2.setName("Einführung in Projektmanagement");
        course2.setDescription("Grundlegende Einführung in die Prinzipien und Methoden des Projektmanagements.");
        course2.setProvider("Projektakademie GmbH");
        course2.setImage("https://static.vecteezy.com/ti/gratis-vektor/p1/13166712-projektmanagement-wortkonzepte-blaue-fahne-strategie-infografiken-mit-bearbeitbaren-symbolen-auf-farbigem-hintergrund-isolierte-typografieillustration-mit-text-vektor.jpg");
        course2.setDuration("3 Stunden");
        course2.setSkilllevel(Skilllevel.Anfaenger);
        course2.setFormat(Format.OnlineSelbstorganisiert);
        course2.setDomainSpecific(false);
        course2.setCostFree(false);
        course2.setCertificate(true);

        courseRepository.save(course2);

// Course 3
        course3 = new Course();
        course3.setCreator(user3);
        course3.setStartDate(new Date());
        course3.setLink("");
        course3.setName("IT-Sicherheit für Einsteiger");
        course3.setDescription("Grundlagen der IT-Sicherheit für Anfänger, um sich vor Cyberbedrohungen zu schützen.");
        course3.setProvider("IT Security Academy");
        course3.setImage("https://www.netaachen.de/cms/api/fileadmin/_processed_/2/4/csm_Cyber_Security_AdobeStock_493619792_f222ca18f8.jpg");
        course3.setDuration("2.5 Stunden");
        course3.setSkilllevel(Skilllevel.Anfaenger);
        course3.setFormat(Format.Hybrid);
        course3.setDomainSpecific(false);
        course3.setCostFree(true);
        course3.setCertificate(true);

        courseRepository.save(course3);

// Course 4
        course4 = new Course();
        course4.setCreator(user4);
        course4.setStartDate(new Date());
        course4.setLink("");
        course4.setName("Effektive Organisationsgestaltung");
        course4.setDescription("Strategien zur effektiven Gestaltung von Organisationen und administrativen Prozessen.");
        course4.setProvider("Organisationsberatung & Co.");
        course4.setImage("https://leanbase.de/autoimg/uploads/w3200/k4nu575r33k8sg6e2z8q29dhxnrubmrspaxn2f77.jpg");
        course4.setDuration("4 Stunden");
        course4.setSkilllevel(Skilllevel.Fortgeschritten);
        course4.setFormat(Format.OnlineLive);
        course4.setDomainSpecific(true);
        course4.setCostFree(true);
        course4.setCertificate(true);

        courseRepository.save(course4);

// Course 5
        course5 = new Course();
        course5.setCreator(user5);
        course5.setStartDate(new Date());
        course5.setLink("");
        course5.setName("Psychologie für Führungskräfte");
        course5.setDescription("Um erfolgreich zu führen, ist es wichtig, menschliche Verhaltensmuster zu verstehen. Denn diese spiegeln sich im unterschiedlichen Agieren und Reagieren Ihrer Mitarbeitenden wider. Sobald Sie diese Muster erkennen, können Sie einfacher damit umgehen. In diesem Seminar erfahren Sie, wie Sie Ihre Wahrnehmung im Umgang mit Mitarbeitenden sensibilisieren, um diese besser einzuschätzen und zu motivieren.");
        course5.setProvider("Führungsinstitut GmbH");
        course5.setImage("https://w2.forschung-und-lehre.de/fileadmin/user_upload/Rubriken/Management/2021/4-21/Humble_Leadership_mauritius_images_11538516.jpg");
        course5.setDuration("3 Stunden");
        course5.setSkilllevel(Skilllevel.Fortgeschritten);
        course5.setFormat(Format.Hybrid);
        course5.setDomainSpecific(false);
        course5.setCostFree(true);
        course5.setCertificate(false);

        courseRepository.save(course5);

// Course 6
        course6 = new Course();
        course6.setCreator(user6);
        course6.setStartDate(new Date());
        course6.setLink("");
        course6.setName("Grundlagen des Verwaltungsrechts");
        course6.setDescription("Eine Einführung in die grundlegenden Aspekte des Verwaltungsrechts.");
        course6.setProvider("Juristisches Institut");
        course6.setImage("https://raeluebbert.de/wp-content/uploads/2019/12/verwaltungsrecht.jpg");
        course6.setDuration("2 Stunden");
        course6.setSkilllevel(Skilllevel.Anfaenger);
        course6.setFormat(Format.Praesenz);
        course6.setDomainSpecific(true);
        course6.setCostFree(true);
        course6.setCertificate(true);

        courseRepository.save(course6);

// Course 7
        course7 = new Course();
        course7.setCreator(user7);
        course7.setStartDate(new Date());
        course7.setLink("");
        course7.setName("Effektive Konfliktlösung und Verhandlungsführung");
        course7.setDescription("Techniken für effektive Konfliktlösung und Verhandlungsführung in verschiedenen Kontexten.");
        course7.setProvider("Institut für Konfliktmanagement");
        course7.setImage("https://web.arbeitsagentur.de/dropsolid-prod-media/prod/s3fs-public/styles/webp/public/2022-12/iStock-1277920767_2500px-2048x1448.jpg.webp");
        course7.setDuration("3.5 Stunden");
        course7.setSkilllevel(Skilllevel.Fortgeschritten);
        course7.setFormat(Format.Hybrid);
        course7.setDomainSpecific(false);
        course7.setCostFree(false);
        course7.setCertificate(true);

        courseRepository.save(course7);

// Course 8
        course8 = new Course();
        course8.setCreator(user8);
        course8.setStartDate(new Date());
        course8.setLink("");
        course8.setName("Sozialpolitik im Überblick");
        course8.setDescription("Eine Übersicht über Grundlagen und Entwicklungen in der Sozialpolitik.");
        course8.setProvider("Sozialwissenschaftliches Institut");
        course8.setImage("https://arbeitgeber.de/wp-content/uploads/2022/05/bda-arbeitgeber-agenda-thema_der_woche_zeitenwende_sozialpolitik-@adobestock_snyGGG-552x274px-2022_05_12.jpg");
        course8.setDuration("2.5 Stunden");
        course8.setSkilllevel(Skilllevel.Anfaenger);
        course8.setFormat(Format.OnlineSelbstorganisiert);
        course8.setDomainSpecific(false);
        course8.setCostFree(false);
        course8.setCertificate(false);


        courseRepository.save(course8);

// Course 9
        course9 = new Course();
        course9.setCreator(user9);
        course9.setStartDate(new Date());
        course9.setLink("");
        course9.setName("Kreatives Problemlösen");
        course9.setDescription("Methoden und Ansätze für kreatives Denken und Problemlösung.");
        course9.setProvider("Institut für Kreativität und Innovation");
        course9.setImage("https://media.licdn.com/dms/image/D5612AQF6u-JnD4hQDw/article-cover_image-shrink_720_1280/0/1684945759396?e=2147483647&v=beta&t=pCHczuaGsT7b8Z0i7sxBOfHVw3sQ9qIe-xD5rjiahts");
        course9.setDuration("4 Stunden");
        course9.setSkilllevel(Skilllevel.Fortgeschritten);
        course9.setFormat(Format.Hybrid);
        course9.setDomainSpecific(true);
        course9.setCostFree(true);
        course9.setCertificate(true);

        courseRepository.save(course9);

// Course 10
        course10 = new Course();
        course10.setCreator(user10);
        course10.setStartDate(new Date());
        course10.setLink("");
        course10.setName("Selbstmanagement und persönliche Effektivität");
        course10.setDescription("Strategien zur Selbstorganisation und Steigerung der persönlichen Effektivität.");
        course10.setProvider("Persönlichkeitsentwicklungsakademie");
        course10.setImage("https://www.regiomanager.de/wp-content/uploads/1995/08/regio-1908-s-selbstmanagement-2nd-part-2-adobestock-234602455.jpg");
        course10.setDuration("3 Stunden");
        course10.setSkilllevel(Skilllevel.Fortgeschritten);
        course10.setFormat(Format.OnlineLive);
        course10.setDomainSpecific(true);
        course10.setCostFree(false);
        course10.setCertificate(true);

        courseRepository.save(course10);

        // Course 11
        course11 = new Course();
        course11.setCreator(user1);
        course11.setStartDate(new Date());
        course11.setLink("");
        course11.setName("Finanzmanagement für Nicht-Finanzexperten");
        course11.setDescription("Grundlagen des Finanzmanagements für Fachkräfte ohne Finanzhintergrund.");
        course11.setProvider("Finanzakademie GmbH");
        course11.setImage("https://images.ctfassets.net/8dreszsahte7/OWZH7RItGd67u8K0cMwOL/b2f9308236e087f4973dc95af159f0ed/image_60.jpg");
        course11.setDuration("2.5 Stunden");
        course11.setSkilllevel(Skilllevel.Anfaenger);
        course11.setFormat(Format.Hybrid);
        course11.setDomainSpecific(false);
        course11.setCostFree(false);
        course11.setCertificate(true);

        courseRepository.save(course11);

// Course 12
        course12 = new Course();
        course12.setCreator(user2);
        course12.setStartDate(new Date());
        course12.setLink("");
        course12.setName("Enterprise Architecture: Grundlagen und Anwendungen");
        course12.setDescription("Eine Einführung in die Grundlagen und Anwendungen von Enterprise Architecture.");
        course12.setProvider("IT-Architektur Institut");
        course12.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course12.setDuration("3 Stunden");
        course12.setSkilllevel(Skilllevel.Fortgeschritten);
        course12.setFormat(Format.OnlineLive);
        course12.setDomainSpecific(false);
        course12.setCostFree(false);
        course12.setCertificate(true);

        courseRepository.save(course12);

// Course 13
        course13 = new Course();
        course13.setCreator(user3);
        course13.setStartDate(new Date());
        course13.setLink("");
        course13.setName("Politikprozesse verstehen und gestalten");
        course13.setDescription("Analyse und Gestaltung von politischen Prozessen und Entscheidungsfindung.");
        course13.setProvider("Institut für Politikwissenschaft");
        course13.setImage("https://www.kopernikus-projekte.de/lw_resource/datapool/systemfiles/agent/lw_articlesystem/245/live/image2_cropped/AdobeStock_434744303_%C2%A9VectorMine_-_stock.adobe.com.jpeg");
        course13.setDuration("4 Stunden");
        course13.setSkilllevel(Skilllevel.Fortgeschritten);
        course13.setFormat(Format.Hybrid);
        course13.setDomainSpecific(false);
        course13.setCostFree(false);
        course13.setCertificate(false);

        courseRepository.save(course13);

// Course 14
        course14 = new Course();
        course14.setCreator(user4);
        course14.setStartDate(new Date());
        course14.setLink("");
        course14.setName("Einführung in die Evaluation und Forschungsmethoden");
        course14.setDescription("Grundlagen der Evaluation und Forschungsmethoden in verschiedenen Kontexten.");
        course14.setProvider("Forschungsinstitut für Sozialwissenschaften");
        course14.setImage("https://www.fbzhl.fau.de/files/2017/12/evaluation.jpg");
        course14.setDuration("3 Stunden");
        course14.setSkilllevel(Skilllevel.Anfaenger);
        course14.setFormat(Format.OnlineSelbstorganisiert);
        course14.setDomainSpecific(false);
        course14.setCostFree(false);
        course14.setCertificate(false);
        courseRepository.save(course14);

// Course 15
        course15 = new Course();
        course15.setCreator(user5);
        course15.setStartDate(new Date());
        course15.setLink("");
        course15.setName("Erfolgreiche Führungskräfte: Strategien für Einflussnahme");
        course15.setDescription("Strategien und Praktiken für erfolgreiche Führungskräfte zur effektiven Einflussnahme.");
        course15.setProvider("Führungsinstitut GmbH");
        course15.setImage("https://www.chieflearningofficer.com/wp-content/uploads/2023/05/AdobeStock_509757335.jpeg");
        course15.setDuration("4 Stunden");
        course15.setSkilllevel(Skilllevel.Fortgeschritten);
        course15.setFormat(Format.Hybrid);
        course15.setDomainSpecific(false);
        course15.setCostFree(false);
        course15.setCertificate(true);

        courseRepository.save(course15);


    }

    public void insertTag(){
        tagBusinessStrategicPlanning = new Tag();
        tagBusinessStrategicPlanning.setName("Strategic Planning");
        tagBusinessStrategicPlanning.setCategory(businessCategory);
        tagRepository.save(tagBusinessStrategicPlanning);

        tagBusinessProjectManagement = new Tag();
        tagBusinessProjectManagement.setName("Project Management");
        tagBusinessProjectManagement.setCategory(businessCategory);
        tagRepository.save(tagBusinessProjectManagement);

        tagBusinessProgramManagement = new Tag();
        tagBusinessProgramManagement.setName("Program Management");
        tagBusinessProgramManagement.setCategory(businessCategory);
        tagRepository.save(tagBusinessProgramManagement);

// IS/IT Management Category
        tagIsItSystems = new Tag();
        tagIsItSystems.setName("Information Systems");
        tagIsItSystems.setCategory(isitManagementCategory);
        tagRepository.save(tagIsItSystems);

        tagItSkills = new Tag();
        tagItSkills.setName("IT Skills");
        tagItSkills.setCategory(isitManagementCategory);
        tagRepository.save(tagItSkills);

        tagCyberSecurity = new Tag();
        tagCyberSecurity.setName("Cyber Security");
        tagCyberSecurity.setCategory(isitManagementCategory);
        tagRepository.save(tagCyberSecurity);

// Organization Category
        tagOrgDesign = new Tag();
        tagOrgDesign.setName("Organizational Design");
        tagOrgDesign.setCategory(organizationCategory);
        tagRepository.save(tagOrgDesign);

        tagAdminProcesses = new Tag();
        tagAdminProcesses.setName("Administrative Processes & Workflows");
        tagAdminProcesses.setCategory(organizationCategory);
        tagRepository.save(tagAdminProcesses);

        tagCoordination = new Tag();
        tagCoordination.setName("Coordination/Implementation");
        tagCoordination.setCategory(organizationCategory);
        tagRepository.save(tagCoordination);

// Public Policy Category
        tagPublicPolicy = new Tag();
        tagPublicPolicy.setName("Public Policy");
        tagPublicPolicy.setCategory(publicPolicyCategory);
        tagRepository.save(tagPublicPolicy);

        tagKnowledgeAgencyPolicy = new Tag();
        tagKnowledgeAgencyPolicy.setName("Knowledge of Agency's Policy Area");
        tagKnowledgeAgencyPolicy.setCategory(publicPolicyCategory);
        tagRepository.save(tagKnowledgeAgencyPolicy);

// Law Category
        tagAdminLaw = new Tag();
        tagAdminLaw.setName("Administrative Law");
        tagAdminLaw.setCategory(lawCategory);
        tagRepository.save(tagAdminLaw);

        tagLegalDataManagement = new Tag();
        tagLegalDataManagement.setName("Legal Aspects for Data Management");
        tagLegalDataManagement.setCategory(lawCategory);
        tagRepository.save(tagLegalDataManagement);

// Other Professional Experience Category
        tagProfessionalExperience = new Tag();
        tagProfessionalExperience.setName("Professional Experience");
        tagProfessionalExperience.setCategory(otherExperienceCategory);
        tagRepository.save(tagProfessionalExperience);

        tagEvaluationResearch = new Tag();
        tagEvaluationResearch.setName("Evaluation & Research");
        tagEvaluationResearch.setCategory(otherExperienceCategory);
        tagRepository.save(tagEvaluationResearch);

// Soft Skills Category
        tagLeadership = new Tag();
        tagLeadership.setName("Leadership");
        tagLeadership.setCategory(softSkillsCategory);
        tagRepository.save(tagLeadership);

        tagConflictManagement = new Tag();
        tagConflictManagement.setName("Conflict Management/Negotiation");
        tagConflictManagement.setCategory(softSkillsCategory);
        tagRepository.save(tagConflictManagement);

// Personality Traits Category
        tagTolerance = new Tag();
        tagTolerance.setName("Tolerance");
        tagTolerance.setCategory(personalityTraitsCategory);
        tagRepository.save(tagTolerance);

        tagContinuousLearning = new Tag();
        tagContinuousLearning.setName("Continuous Learning");
        tagContinuousLearning.setCategory(personalityTraitsCategory);
        tagRepository.save(tagContinuousLearning);

// Analytical Category
        tagCriticalThinking = new Tag();
        tagCriticalThinking.setName("Critical Thinking");
        tagCriticalThinking.setCategory(analyticalCategory);
        tagRepository.save(tagCriticalThinking);

        tagDecisionMaking = new Tag();
        tagDecisionMaking.setName("Decision Making");
        tagDecisionMaking.setCategory(analyticalCategory);
        tagRepository.save(tagDecisionMaking);

// Self-Management Category
        tagSelfOrganization = new Tag();
        tagSelfOrganization.setName("Self-Organization");
        tagSelfOrganization.setCategory(selfManagementCategory);
        tagRepository.save(tagSelfOrganization);

        tagSelfControl = new Tag();
        tagSelfControl.setName("Self-Control");
        tagSelfControl.setCategory(selfManagementCategory);
        tagRepository.save(tagSelfControl);

    }

    private void addTagsToUsers() {

        userTag1 = new UserTag();
        userTag1.setUser(user11);
        userTag1.setRating(1);
        userTag1.setTag(tagLeadership);

        userTagRepository.save(userTag1);

        userTag2 = new UserTag();
        userTag2.setUser(user11);
        userTag2.setRating(1);
        userTag2.setTag(tagCriticalThinking);

        userTagRepository.save(userTag2);

        userTag3 = new UserTag();
        userTag3.setUser(user11);
        userTag3.setRating(1);
        userTag3.setTag(tagDecisionMaking);

        userTagRepository.save(userTag3);

    }

    private void addTagsToCourses() {

// Associate tags with Course 1
        CourseTag courseTagScrum = new CourseTag();
        courseTagScrum.setCourse(course1);
        courseTagScrum.setTag(tagBusinessStrategicPlanning);
        courseTagRepository.save(courseTagScrum);

        CourseTag courseTagPublicSector = new CourseTag();
        courseTagPublicSector.setCourse(course1);
        courseTagPublicSector.setTag(tagBusinessProjectManagement);
        courseTagRepository.save(courseTagPublicSector);

// Associate tags with Course 2
        CourseTag courseTagProjectManagement2 = new CourseTag();
        courseTagProjectManagement2.setCourse(course2);
        courseTagProjectManagement2.setTag(tagBusinessProjectManagement);
        courseTagRepository.save(courseTagProjectManagement2);

        CourseTag courseTagMethodology = new CourseTag();
        courseTagMethodology.setCourse(course2);
        courseTagMethodology.setTag(tagCriticalThinking);
        courseTagRepository.save(courseTagMethodology);

// Associate tags with Course 3
        CourseTag courseTagCyberSecurity3 = new CourseTag();
        courseTagCyberSecurity3.setCourse(course3);
        courseTagCyberSecurity3.setTag(tagCyberSecurity);
        courseTagRepository.save(courseTagCyberSecurity3);

        CourseTag courseTagIsItSkills3 = new CourseTag();
        courseTagIsItSkills3.setCourse(course3);
        courseTagIsItSkills3.setTag(tagItSkills);
        courseTagRepository.save(courseTagIsItSkills3);

// Associate tags with Course 4
        CourseTag courseTagOrganizationalDesign4 = new CourseTag();
        courseTagOrganizationalDesign4.setCourse(course4);
        courseTagOrganizationalDesign4.setTag(tagOrgDesign);
        courseTagRepository.save(courseTagOrganizationalDesign4);

        CourseTag courseTagAdministrativeProcesses = new CourseTag();
        courseTagAdministrativeProcesses.setCourse(course4);
        courseTagAdministrativeProcesses.setTag(tagCriticalThinking);
        courseTagRepository.save(courseTagAdministrativeProcesses);

// Associate tags with Course 5
        CourseTag courseTagPublicPolicy5 = new CourseTag();
        courseTagPublicPolicy5.setCourse(course5);
        courseTagPublicPolicy5.setTag(tagLeadership);
        courseTagRepository.save(courseTagPublicPolicy5);

        CourseTag courseTagPoliticsProcesses5 = new CourseTag();
        courseTagPoliticsProcesses5.setCourse(course5);
        courseTagPoliticsProcesses5.setTag(tagSelfControl);
        courseTagRepository.save(courseTagPoliticsProcesses5);

// Associate tags with Course 6
        CourseTag courseTagAdministrativeLaw6 = new CourseTag();
        courseTagAdministrativeLaw6.setCourse(course6);
        courseTagAdministrativeLaw6.setTag(tagAdminLaw);
        courseTagRepository.save(courseTagAdministrativeLaw6);

        CourseTag courseTagLegalAspectsDataManagement6 = new CourseTag();
        courseTagLegalAspectsDataManagement6.setCourse(course6);
        courseTagLegalAspectsDataManagement6.setTag(tagLegalDataManagement);
        courseTagRepository.save(courseTagLegalAspectsDataManagement6);

// Associate tags with Course 7
        CourseTag courseTagProfessionalExperience7 = new CourseTag();
        courseTagProfessionalExperience7.setCourse(course7);
        courseTagProfessionalExperience7.setTag(tagLeadership);
        courseTagRepository.save(courseTagProfessionalExperience7);

        CourseTag courseTagConflictManagement7 = new CourseTag();
        courseTagConflictManagement7.setCourse(course7);
        courseTagConflictManagement7.setTag(tagConflictManagement);
        courseTagRepository.save(courseTagConflictManagement7);

// Associate tags with Course 8
        CourseTag courseTagLeadership8 = new CourseTag();
        courseTagLeadership8.setCourse(course8);
        courseTagLeadership8.setTag(tagLeadership);
        courseTagRepository.save(courseTagLeadership8);

        CourseTag courseTagTolerance8 = new CourseTag();
        courseTagTolerance8.setCourse(course8);
        courseTagTolerance8.setTag(tagTolerance);
        courseTagRepository.save(courseTagTolerance8);

// Associate tags with Course 9
        CourseTag courseTagCriticalThinking9 = new CourseTag();
        courseTagCriticalThinking9.setCourse(course9);
        courseTagCriticalThinking9.setTag(tagCriticalThinking);
        courseTagRepository.save(courseTagCriticalThinking9);

        CourseTag courseTagDecisionMaking9 = new CourseTag();
        courseTagDecisionMaking9.setCourse(course9);
        courseTagDecisionMaking9.setTag(tagDecisionMaking);
        courseTagRepository.save(courseTagDecisionMaking9);

// Associate tags with Course 10
        CourseTag courseTagSelfOrganization10 = new CourseTag();
        courseTagSelfOrganization10.setCourse(course10);
        courseTagSelfOrganization10.setTag(tagSelfOrganization);
        courseTagRepository.save(courseTagSelfOrganization10);

        CourseTag courseTagSelfControl10 = new CourseTag();
        courseTagSelfControl10.setCourse(course10);
        courseTagSelfControl10.setTag(tagSelfControl);
        courseTagRepository.save(courseTagSelfControl10);

// Associate tags with Course 11
        CourseTag courseTagFinanceManagement11 = new CourseTag();
        courseTagFinanceManagement11.setCourse(course11);
        courseTagFinanceManagement11.setTag(tagBusinessStrategicPlanning);
        courseTagRepository.save(courseTagFinanceManagement11);

        CourseTag courseTagLegalFramework11 = new CourseTag();
        courseTagLegalFramework11.setCourse(course11);
        courseTagLegalFramework11.setTag(tagAdminLaw);
        courseTagRepository.save(courseTagLegalFramework11);

// Associate tags with Course 12
        CourseTag courseTagEnterpriseArchitecture12 = new CourseTag();
        courseTagEnterpriseArchitecture12.setCourse(course12);
        courseTagEnterpriseArchitecture12.setTag(tagItSkills);
        courseTagRepository.save(courseTagEnterpriseArchitecture12);

        CourseTag courseTagTechnologyManagement12 = new CourseTag();
        courseTagTechnologyManagement12.setCourse(course12);
        courseTagTechnologyManagement12.setTag(tagIsItSystems);
        courseTagRepository.save(courseTagTechnologyManagement12);

// Associate tags with Course 13
        CourseTag courseTagPoliticsProcesses13 = new CourseTag();
        courseTagPoliticsProcesses13.setCourse(course13);
        courseTagPoliticsProcesses13.setTag(tagPublicPolicy);
        courseTagRepository.save(courseTagPoliticsProcesses13);

        CourseTag courseTagLegalAspects13 = new CourseTag();
        courseTagLegalAspects13.setCourse(course13);
        courseTagLegalAspects13.setTag(tagLegalDataManagement);
        courseTagRepository.save(courseTagLegalAspects13);

// Associate tags with Course 14
        CourseTag courseTagEvaluationResearch14 = new CourseTag();
        courseTagEvaluationResearch14.setCourse(course14);
        courseTagEvaluationResearch14.setTag(tagEvaluationResearch);
        courseTagRepository.save(courseTagEvaluationResearch14);

        CourseTag courseTagSocioTechnicalSkills14 = new CourseTag();
        courseTagSocioTechnicalSkills14.setCourse(course14);
        courseTagSocioTechnicalSkills14.setTag(tagIsItSystems);
        courseTagRepository.save(courseTagSocioTechnicalSkills14);

// Associate tags with Course 15
        CourseTag courseTagLeadership15 = new CourseTag();
        courseTagLeadership15.setCourse(course15);
        courseTagLeadership15.setTag(tagLeadership);
        courseTagRepository.save(courseTagLeadership15);

        CourseTag courseTagInfluencing15 = new CourseTag();
        courseTagInfluencing15.setCourse(course15);
        courseTagInfluencing15.setTag(tagCriticalThinking);
        courseTagRepository.save(courseTagInfluencing15);
    }

    private void addFeedbackToCourses() {
        // Feedback for Course 1
        Feedback feedback1Course1 = new Feedback();
        feedback1Course1.setCourse(course1);
        feedback1Course1.setUser(user1);
        feedback1Course1.setRating(5);
        feedback1Course1.setTitle("Super Kurs!");
        feedback1Course1.setDescription("Ich habe sehr viel gelernt und kann den Kurs nur weiterempfehlen.");
        feedback1Course1.setCreatedAt(new Date());
        course1.addFeedback(feedback1Course1);
        courseRepository.save(course1);

// Feedback for Course 2
        Feedback feedback1Course2 = new Feedback();
        feedback1Course2.setCourse(course2);
        feedback1Course2.setUser(user2);
        feedback1Course2.setRating(4);
        feedback1Course2.setTitle("Sehr informativ");
        feedback1Course2.setDescription("Der Kurs hat meine Erwartungen erfüllt und ich konnte mein Wissen im Projektmanagement erweitern.");
        feedback1Course2.setCreatedAt(new Date());
        course2.addFeedback(feedback1Course2);
        courseRepository.save(course2);

// Feedback for Course 3
        Feedback feedback1Course3 = new Feedback();
        feedback1Course3.setCourse(course3);
        feedback1Course3.setUser(user3);
        feedback1Course3.setRating(3);
        feedback1Course3.setTitle("Gut, aber verbesserungsfähig");
        feedback1Course3.setDescription("Der Kurs gibt einen guten Überblick über IT-Sicherheit, aber einige Themen könnten detaillierter behandelt werden.");
        feedback1Course3.setCreatedAt(new Date());
        course3.addFeedback(feedback1Course3);
        courseRepository.save(course3);

        // Feedback for Course 4
        Feedback feedback1Course4 = new Feedback();
        feedback1Course4.setCourse(course4);
        feedback1Course4.setUser(user4);
        feedback1Course4.setRating(4);
        feedback1Course4.setTitle("Interessanter Kurs");
        feedback1Course4.setDescription("Die Strategien zur effektiven Gestaltung von Organisationen waren sehr praxisnah und hilfreich.");
        feedback1Course4.setCreatedAt(new Date());
        course4.addFeedback(feedback1Course4);
        courseRepository.save(course4);

// Feedback for Course 5
        Feedback feedback1Course5 = new Feedback();
        feedback1Course5.setCourse(course5);
        feedback1Course5.setUser(user5);
        feedback1Course5.setRating(5);
        feedback1Course5.setTitle("Empfehlenswert!");
        feedback1Course5.setDescription("Sehr informative Einführung in die Grundkonzepte der öffentlichen Politik.");
        feedback1Course5.setCreatedAt(new Date());
        course5.addFeedback(feedback1Course5);
        courseRepository.save(course5);

// Feedback for Course 6
        Feedback feedback1Course6 = new Feedback();
        feedback1Course6.setCourse(course6);
        feedback1Course6.setUser(user6);
        feedback1Course6.setRating(3);
        feedback1Course6.setTitle("Solide Einführung");
        feedback1Course6.setDescription("Der Kurs bietet eine solide Einführung in die grundlegenden Aspekte des Verwaltungsrechts.");
        feedback1Course6.setCreatedAt(new Date());
        course6.addFeedback(feedback1Course6);
        courseRepository.save(course6);

// Feedback for Course 7
        Feedback feedback1Course7 = new Feedback();
        feedback1Course7.setCourse(course7);
        feedback1Course7.setUser(user7);
        feedback1Course7.setRating(4);
        feedback1Course7.setTitle("Effektive Techniken");
        feedback1Course7.setDescription("Die vorgestellten Techniken zur Konfliktlösung und Verhandlungsführung waren sehr praxisorientiert.");
        feedback1Course7.setCreatedAt(new Date());
        course7.addFeedback(feedback1Course7);
        courseRepository.save(course7);

// Feedback for Course 8
        Feedback feedback1Course8 = new Feedback();
        feedback1Course8.setCourse(course8);
        feedback1Course8.setUser(user8);
        feedback1Course8.setRating(3);
        feedback1Course8.setTitle("Guter Überblick");
        feedback1Course8.setDescription("Der Kurs bietet einen guten Überblick über Grundlagen und Entwicklungen in der Sozialpolitik.");
        feedback1Course8.setCreatedAt(new Date());
        course8.addFeedback(feedback1Course8);
        courseRepository.save(course8);

// Feedback for Course 9
        Feedback feedback1Course9 = new Feedback();
        feedback1Course9.setCourse(course9);
        feedback1Course9.setUser(user9);
        feedback1Course9.setRating(5);
        feedback1Course9.setTitle("Kreativität gefördert");
        feedback1Course9.setDescription("Der Kurs hat meine kreativen Denkprozesse angeregt und mir neue Lösungsansätze aufgezeigt.");
        feedback1Course9.setCreatedAt(new Date());
        course9.addFeedback(feedback1Course9);
        courseRepository.save(course9);

// Feedback for Course 10
        Feedback feedback1Course10 = new Feedback();
        feedback1Course10.setCourse(course10);
        feedback1Course10.setUser(user10);
        feedback1Course10.setRating(4);
        feedback1Course10.setTitle("Effektive Selbstmanagementstrategien");
        feedback1Course10.setDescription("Die vermittelten Strategien zur Selbstorganisation und persönlichen Effektivität sind sehr praktisch anwendbar.");
        feedback1Course10.setCreatedAt(new Date());
        course10.addFeedback(feedback1Course10);
        courseRepository.save(course10);

// Feedback for Course 11
        Feedback feedback1Course11 = new Feedback();
        feedback1Course11.setCourse(course11);
        feedback1Course11.setUser(user1);
        feedback1Course11.setRating(4);
        feedback1Course11.setTitle("Verständliche Finanzgrundlagen");
        feedback1Course11.setDescription("Der Kurs vermittelt Finanzgrundlagen verständlich und praxisnah.");
        feedback1Course11.setCreatedAt(new Date());
        course11.addFeedback(feedback1Course11);
        courseRepository.save(course11);

// Feedback for Course 12
        Feedback feedback1Course12 = new Feedback();
        feedback1Course12.setCourse(course12);
        feedback1Course12.setUser(user2);
        feedback1Course12.setRating(5);
        feedback1Course12.setTitle("Top-Einführung in Enterprise Architecture");
        feedback1Course12.setDescription("Sehr informative Einführung in die Grundlagen und Anwendungen von Enterprise Architecture.");
        feedback1Course12.setCreatedAt(new Date());
        course12.addFeedback(feedback1Course12);
        courseRepository.save(course12);

// Feedback for Course 13
        Feedback feedback1Course13 = new Feedback();
        feedback1Course13.setCourse(course13);
        feedback1Course13.setUser(user3);
        feedback1Course13.setRating(3);
        feedback1Course13.setTitle("Analytische Betrachtung");
        feedback1Course13.setDescription("Der Kurs bietet eine analytische Betrachtung von politischen Prozessen, könnte jedoch detaillierter sein.");
        feedback1Course13.setCreatedAt(new Date());
        course13.addFeedback(feedback1Course13);
        courseRepository.save(course13);

// Feedback for Course 14
        Feedback feedback1Course14 = new Feedback();
        feedback1Course14.setCourse(course14);
        feedback1Course14.setUser(user4);
        feedback1Course14.setRating(4);
        feedback1Course14.setTitle("Gute Einführung in Forschungsmethoden");
        feedback1Course14.setDescription("Der Kurs bietet eine gute Einführung in Evaluations- und Forschungsmethoden.");
        feedback1Course14.setCreatedAt(new Date());
        course14.addFeedback(feedback1Course14);
        courseRepository.save(course14);

// Feedback for Course 15
        Feedback feedback1Course15 = new Feedback();
        feedback1Course15.setCourse(course15);
        feedback1Course15.setUser(user5);
        feedback1Course15.setRating(5);
        feedback1Course15.setTitle("Effektive Führungskräftestrategien");
        feedback1Course15.setDescription("Der Kurs vermittelt effektive Strategien für Führungskräfte zur erfolgreichen Einflussnahme.");
        feedback1Course15.setCreatedAt(new Date());
        course15.addFeedback(feedback1Course15);
        courseRepository.save(course15);


    }
}
