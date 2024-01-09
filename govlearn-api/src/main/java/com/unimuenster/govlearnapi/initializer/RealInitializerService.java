package com.unimuenster.govlearnapi.initializer;

import com.unimuenster.govlearnapi.category.entity.Category;
import com.unimuenster.govlearnapi.category.repository.CategoryRepository;
import com.unimuenster.govlearnapi.core.config.enums.Format;
import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.core.config.enums.Verantwortungsbereich;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.feedback.entity.Feedback;
import com.unimuenster.govlearnapi.tags.entity.CourseTag;
import com.unimuenster.govlearnapi.tags.entity.Role;
import com.unimuenster.govlearnapi.tags.entity.Tag;
import com.unimuenster.govlearnapi.tags.entity.UserTag;
import com.unimuenster.govlearnapi.tags.repository.CourseTagRepository;
import com.unimuenster.govlearnapi.tags.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTagRepository userTagRepository;
    private final CourseTagRepository courseTagRepository;
    private final CategoryRepository categoryRepository;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    private UserEntity user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11;
    private Course course1, course2, course3, course4, course5, course6, course7, course8, course9, course10, course11, course12, course13, course14, course15, course16, course17, course18, course19, course20, course21, course22, course23, course24, course25, course26;
    private Category businessCategory, organisatorischCategory, RechtlichCategory, softSkillsCategory, sozioTechnischCategory, technischCategory, oeffenlichPolitischCategory;
    private Tag tagChangemanagement, tagProjektmanagement, tagProjektplanung, tagProjekterfolgskontrolle, tagResourcenmanagement, tagMarketing, tagQulitaetsmanagement, tagRisikomanagement, tagVerwaltungsstruktur, tagProzessmanagement, tagOrganisationsformen, tagVerwaltungsprozesse, tagVerwaltungsrecht, tagItRecht, tageGovernmentRecht, tagSelbstorganisation, tagTeamFaehigkeit, tagFuehrungskompentenzen, tagDesignThinking, tagStressbewaeltigung, tagKonfliktmanagement, tagMedienkompetenz, tagKommunikation, tagVirtuellesArbeiten, tagPraesentationstechnicken, tagLernkompetenzen, tagKundenbeduerfnisseAnforderungsmanagement, tagStakeholderAnalyse, tagDigitaleTrends, tagProgrammieren, tagSoftwarearchitektur, tagEntwicklungvonSicherheitskonzepten, tagBetirebssymsteme, tagErpSysteme, tagDataMining, tagDatenbankmanagement, tagKI, tagGrafikdesignBilbearbeitung, tagKollaborationstools, tagOutlook, tagWord, tagExcel, tagItSicherheit, tagHardwarekompetenz, tagBuergerzentrierung, tagDatenschutz, tagCompliance, tagFremdsprache, tagInformationsicherheit;
    private UserTag userTag1, userTag2, userTag3, userTag4;
    private Role OrganisationStratege, OrganisationEntscheidungsträger, OrganisationUmsetzer, DigitalisierungStratege, DigitalisierungEntscheidungsträger, DigitalisierungUmsetzer, InformationstechnikStratege, InformationstechnikEntscheidungsträger, InformationstechnikUmsetzer, SmartCityStratege, SmartCityEntscheidungsTräger, SmartCityUmsetzer, NichtDigitalStratege, NichtDigitalEntscheidungsträger, NichtDigitalUmsetzer, PersonalStratege, PersonalEntscheidungsträger, PersonalUmsetzer;

    public void init() {
        insertUser();
        insertCategories();
        insertCourse();
        insertTag();
        addTagsToUsers();
        addTagsToCourses();
        addFeedbackToCourses();
        insertRoles();
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
        user2.setName("Anna Mueller");
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

// Organisatorisch Category
        organisatorischCategory = new Category();
        organisatorischCategory.setName("Organisatorisch");
        categoryRepository.save(organisatorischCategory);

// Rechtlich Category
        RechtlichCategory = new Category();
        RechtlichCategory.setName("Rechtlich");
        categoryRepository.save(RechtlichCategory);

// Soft Skills Category
        softSkillsCategory = new Category();
        softSkillsCategory.setName("Soft Skills");
        categoryRepository.save(softSkillsCategory);

// Sozio-technisch Category
        sozioTechnischCategory = new Category();
        sozioTechnischCategory.setName("Sozio-technisch");
        categoryRepository.save(sozioTechnischCategory);


// Technisch Category
        technischCategory = new Category();
        technischCategory.setName("Technisch");
        categoryRepository.save(technischCategory);

// oeffentlich/Politisch Category
        oeffenlichPolitischCategory = new Category();
        oeffenlichPolitischCategory.setName("Öffentlich/Politisch");
        categoryRepository.save(oeffenlichPolitischCategory);
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
        course1.setDurationInMinutes(120);
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
        course2.setDurationInMinutes(180);
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
        course3.setName("IT-Sicherheit fuer Einsteiger");
        course3.setDescription("Grundlagen der IT-Sicherheit fuer Anfänger, um sich vor Cyberbedrohungen zu schützen.");
        course3.setProvider("IT Security Academy");
        course3.setImage("https://www.netaachen.de/cms/api/fileadmin/_processed_/2/4/csm_Cyber_Security_AdobeStock_493619792_f222ca18f8.jpg");
        course3.setDurationInMinutes(150);
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
        course4.setDurationInMinutes(240);
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
        course5.setName("Psychologie fuer Führungskraefte");
        course5.setDescription("Um erfolgreich zu führen, ist es wichtig, menschliche Verhaltensmuster zu verstehen. Denn diese spiegeln sich im unterschiedlichen Agieren und Reagieren Ihrer Mitarbeitenden wider. Sobald Sie diese Muster erkennen, koennen Sie einfacher damit umgehen.");
        course5.setProvider("Führungsinstitut GmbH");
        course5.setImage("https://w2.forschung-und-lehre.de/fileadmin/user_upload/Rubriken/Management/2021/4-21/Humble_Leadership_mauritius_images_11538516.jpg");
        course5.setDurationInMinutes(180);
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
        course6.setDurationInMinutes(120);
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
        course7.setDurationInMinutes(150);
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
        course8.setName("Kundenbedürfnisse im Überblick");
        course8.setDescription("Eine Übersicht über Grundlagen und Entwicklungen von Kundenbedürfnissen und Anforderungsmanagement.");
        course8.setProvider("Sozialwissenschaftliches Institut");
        course8.setImage("https://arbeitgeber.de/wp-content/uploads/2022/05/bda-arbeitgeber-agenda-thema_der_woche_zeitenwende_sozialpolitik-@adobestock_snyGGG-552x274px-2022_05_12.jpg");
        course8.setDurationInMinutes(150);
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
        course9.setProvider("Institut fuer Kreativität und Innovation");
        course9.setImage("https://media.licdn.com/dms/image/D5612AQF6u-JnD4hQDw/article-cover_image-shrink_720_1280/0/1684945759396?e=2147483647&v=beta&t=pCHczuaGsT7b8Z0i7sxBOfHVw3sQ9qIe-xD5rjiahts");
        course9.setDurationInMinutes(240);
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
        course10.setDurationInMinutes(180);
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
        course11.setDescription("Grundlagen des Finanzmanagements für Fachkraefte ohne Finanzhintergrund.");
        course11.setProvider("Finanzakademie GmbH");
        course11.setImage("https://images.ctfassets.net/8dreszsahte7/OWZH7RItGd67u8K0cMwOL/b2f9308236e087f4973dc95af159f0ed/image_60.jpg");
        course11.setDurationInMinutes(150);
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
        course12.setDurationInMinutes(180);
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
        course13.setName("Bürgerzentrierung verstehen und gestalten");
        course13.setDescription("Analyse und Gestaltung von Bürgerzentrierung und Entscheidungsfindung.");
        course13.setProvider("Institut fuer Politikwissenschaft");
        course13.setImage("https://www.kopernikus-projekte.de/lw_resource/datapool/systemfiles/agent/lw_articlesystem/245/live/image2_cropped/AdobeStock_434744303_%C2%A9VectorMine_-_stock.adobe.com.jpeg");
        course13.setDurationInMinutes(240);
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
        course14.setDurationInMinutes(180);
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
        course15.setName("Erfolgreiche Führungskraefte: Strategien für Einflussnahme");
        course15.setDescription("Strategien und Praktiken für erfolgreiche Fuehrungskräfte zur effektiven Einflussnahme.");
        course15.setProvider("Fuehrungsinstitut GmbH");
        course15.setImage("https://www.chieflearningofficer.com/wp-content/uploads/2023/05/AdobeStock_509757335.jpeg");
        course15.setDurationInMinutes(240);
        course15.setSkilllevel(Skilllevel.Fortgeschritten);
        course15.setFormat(Format.Hybrid);
        course15.setDomainSpecific(false);
        course15.setCostFree(false);
        course15.setCertificate(true);

        courseRepository.save(course15);

// Course 16
        course16 = new Course();
        course16.setCreator(user5);
        course16.setStartDate(new Date());
        course16.setLink("");
        course16.setName("eGovernment-Recht");
        course16.setDescription("Einfuehrung in die Rechte und Pflichten bei eGovernment.");
        course16.setProvider("Institut für Politikwissenschaft");
        course16.setImage("https://www.chieflearningofficer.com/wp-content/uploads/2023/05/AdobeStock_509757335.jpeg");
        course16.setDurationInMinutes(600);
        course16.setSkilllevel(Skilllevel.Anfaenger);
        course16.setFormat(Format.Hybrid);
        course16.setDomainSpecific(false);
        course16.setCostFree(true);
        course16.setCertificate(true);

        courseRepository.save(course16);

// Course 17
        course17 = new Course();
        course17.setCreator(user2);
        course17.setStartDate(new Date());
        course17.setLink("");
        course17.setName("Java: Grundlagen der Programmierung");
        course17.setDescription("Eine Einfuehrung in die Grundlagen und Anwendungen von Java.");
        course17.setProvider("IT-Architektur Institut");
        course17.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course17.setDurationInMinutes(420);
        course17.setSkilllevel(Skilllevel.Anfaenger);
        course17.setFormat(Format.OnlineLive);
        course17.setDomainSpecific(false);
        course17.setCostFree(false);
        course17.setCertificate(true);

        courseRepository.save(course17);

// Course 18
        course18 = new Course();
        course18.setCreator(user2);
        course18.setStartDate(new Date());
        course18.setLink("");
        course18.setName("Selbstorganisation mit Hilfe von Outlook");
        course18.setDescription("Hilfe bei der Selbstorganisation mit Outlook.");
        course18.setProvider("Outlook Institut");
        course18.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course18.setDurationInMinutes(300);
        course18.setSkilllevel(Skilllevel.Anfaenger);
        course18.setFormat(Format.OnlineSelbstorganisiert);
        course18.setDomainSpecific(false);
        course18.setCostFree(false);
        course18.setCertificate(true);

        courseRepository.save(course18);

// Course 19
        course19 = new Course();
        course19.setCreator(user2);
        course19.setStartDate(new Date());
        course19.setLink("");
        course19.setName("Qualitätsmanagement");
        course19.setDescription("Sicherung der Kundenbedürfnise durch Qualitätssicherung.");
        course19.setProvider("Qualitäts GmbH");
        course19.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course19.setDurationInMinutes(300);
        course19.setSkilllevel(Skilllevel.Fortgeschritten);
        course19.setFormat(Format.OnlineSelbstorganisiert);
        course19.setDomainSpecific(false);
        course19.setCostFree(false);
        course19.setCertificate(true);

        courseRepository.save(course19);

// Course 20
        course20 = new Course();
        course20.setCreator(user2);
        course20.setStartDate(new Date());
        course20.setLink("");
        course20.setName("Risikomanagement");
        course20.setDescription("Wie plant man mit Risikofaktoren?");
        course20.setProvider("Institut fuer Risikoforschung");
        course20.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course20.setDurationInMinutes(300);
        course20.setSkilllevel(Skilllevel.Fortgeschritten);
        course20.setFormat(Format.OnlineSelbstorganisiert);
        course20.setDomainSpecific(false);
        course20.setCostFree(false);
        course20.setCertificate(true);

        courseRepository.save(course20);

// Course 21
        course21 = new Course();
        course21.setCreator(user2);
        course21.setStartDate(new Date());
        course21.setLink("");
        course21.setName("Teamführung");
        course21.setDescription("Der richtige Umgang mit Streit im Arbeitsteam");
        course21.setProvider("Streitschlichter GmbH");
        course21.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course21.setDurationInMinutes(600);
        course21.setSkilllevel(Skilllevel.Fortgeschritten);
        course21.setFormat(Format.OnlineSelbstorganisiert);
        course21.setDomainSpecific(false);
        course21.setCostFree(false);
        course21.setCertificate(true);

        courseRepository.save(course21);

// Course 22
        course22 = new Course();
        course22.setCreator(user2);
        course22.setStartDate(new Date());
        course22.setLink("");
        course22.setName("Business Englisch");
        course22.setDescription("Englisch für Fortgeschrittene.");
        course22.setProvider("Streitschlichter GmbH");
        course22.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course22.setDurationInMinutes(600);
        course22.setSkilllevel(Skilllevel.Fortgeschritten);
        course22.setFormat(Format.OnlineSelbstorganisiert);
        course22.setDomainSpecific(false);
        course22.setCostFree(false);
        course22.setCertificate(true);

        courseRepository.save(course22);

// Course 23
        course23 = new Course();
        course23.setCreator(user2);
        course23.setStartDate(new Date());
        course23.setLink("");
        course23.setName("Compliance in der oeffentlichen Verwaltung");
        course23.setDescription("Compliance ist die betriebswirtschaftliche und rechtswissenschaftliche Umschreibung für die Regeltreue von Unternehmen.");
        course23.setProvider("Institut für Compliance");
        course23.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course23.setDurationInMinutes(600);
        course23.setSkilllevel(Skilllevel.Anfaenger);
        course23.setFormat(Format.Praesenz);
        course23.setDomainSpecific(true);
        course23.setCostFree(false);
        course23.setCertificate(true);

        courseRepository.save(course23);

// Course 24
        course24 = new Course();
        course24.setCreator(user2);
        course24.setStartDate(new Date());
        course24.setLink("");
        course24.setName("Digitale Trends in der Arbeitswelt");
        course24.setDescription("Die digitalen Trends, wie Homeoffice für Einsteiger.");
        course24.setProvider("Institut für Compliance");
        course24.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course24.setDurationInMinutes(600);
        course24.setSkilllevel(Skilllevel.Anfaenger);
        course24.setFormat(Format.OnlineLive);
        course24.setDomainSpecific(true);
        course24.setCostFree(false);
        course24.setCertificate(true);

        courseRepository.save(course24);

// Course 25
        course25 = new Course();
        course25.setCreator(user2);
        course25.setStartDate(new Date());
        course25.setLink("");
        course25.setName("Präsentationen richtig halten");
        course25.setDescription("Die besten Präsentationstechnicken, um Arbeitsergebnisse zu präsentieren.");
        course25.setProvider("Institut für Compliance");
        course25.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course25.setDurationInMinutes(600);
        course25.setSkilllevel(Skilllevel.Anfaenger);
        course25.setFormat(Format.OnlineLive);
        course25.setDomainSpecific(true);
        course25.setCostFree(false);
        course25.setCertificate(true);

        courseRepository.save(course25);

// Course 26
        course26 = new Course();
        course26.setCreator(user2);
        course26.setStartDate(new Date());
        course26.setLink("");
        course26.setName("Datenschutz im Umgang mit KI");
        course26.setDescription("Wie dürfen KIs eigesetzt werden und wie können Daten dabei geschützt werden.");
        course26.setProvider("Uni Münster");
        course26.setImage("https://nolijconsulting.com/wp-content/uploads/2022/04/blogimg-nolij-insight-1.jpg");
        course26.setDurationInMinutes(600);
        course26.setSkilllevel(Skilllevel.Experte);
        course26.setFormat(Format.Praesenz);
        course26.setDomainSpecific(true);
        course26.setCostFree(false);
        course26.setCertificate(true);

        courseRepository.save(course26);


    }

    public void insertTag(){

// Business Category
        tagChangemanagement = new Tag();
        tagChangemanagement.setName("Change Management");
        tagChangemanagement.setCategory(businessCategory);
        tagRepository.save(tagChangemanagement);

        tagProjektmanagement = new Tag();
        tagProjektmanagement.setName("Projekt Management");
        tagProjektmanagement.setCategory(businessCategory);
        tagRepository.save(tagProjektmanagement);

        tagProjektplanung = new Tag();
        tagProjektplanung.setName("Projektplanung");
        tagProjektplanung.setCategory(businessCategory);
        tagRepository.save(tagProjektplanung);

        tagProjekterfolgskontrolle = new Tag();
        tagProjekterfolgskontrolle.setName("Projekterfolgskontrolle");
        tagProjekterfolgskontrolle.setCategory(businessCategory);
        tagRepository.save(tagProjekterfolgskontrolle);

        tagResourcenmanagement = new Tag();
        tagResourcenmanagement.setName("Resourcenmanagement");
        tagResourcenmanagement.setCategory(businessCategory);
        tagRepository.save( tagResourcenmanagement);

        tagMarketing = new Tag();
        tagMarketing.setName("(Online-)Marketing");
        tagMarketing.setCategory(businessCategory);
        tagRepository.save(tagMarketing);

        tagQulitaetsmanagement = new Tag();
        tagQulitaetsmanagement.setName("Qulitätsmanagement");
        tagQulitaetsmanagement.setCategory(businessCategory);
        tagRepository.save(tagQulitaetsmanagement);

        tagRisikomanagement = new Tag();
        tagRisikomanagement.setName("Risikomanagement");
        tagRisikomanagement.setCategory(businessCategory);
        tagRepository.save(tagRisikomanagement);

// Organisatorisch Category
        tagVerwaltungsstruktur = new Tag();
        tagVerwaltungsstruktur.setName("Verwaltungsstruktur");
        tagVerwaltungsstruktur.setCategory(organisatorischCategory);
        tagRepository.save(tagVerwaltungsstruktur);

        tagProzessmanagement = new Tag();
        tagProzessmanagement.setName("Prozessmanagement");
        tagProzessmanagement.setCategory(organisatorischCategory);
        tagRepository.save(tagProzessmanagement);

        tagOrganisationsformen = new Tag();
        tagOrganisationsformen.setName("Organisationsformen");
        tagOrganisationsformen.setCategory(organisatorischCategory);
        tagRepository.save(tagOrganisationsformen);

        tagVerwaltungsprozesse = new Tag();
        tagVerwaltungsprozesse.setName("Verwaltungsprozesse");
        tagVerwaltungsprozesse.setCategory(organisatorischCategory);
        tagRepository.save(tagVerwaltungsprozesse);

// Rechtlich Category
        tagVerwaltungsrecht = new Tag();
        tagVerwaltungsrecht.setName("Verwaltungsrecht");
        tagVerwaltungsrecht.setCategory(RechtlichCategory);
        tagRepository.save(tagVerwaltungsrecht);

        tagItRecht = new Tag();
        tagItRecht.setName("IT-Recht");
        tagItRecht.setCategory(RechtlichCategory);
        tagRepository.save(tagItRecht);

        tageGovernmentRecht = new Tag();
        tageGovernmentRecht.setName("eGovernment-Recht");
        tageGovernmentRecht.setCategory(RechtlichCategory);
        tagRepository.save(tageGovernmentRecht);

// Softskills Category
        tagSelbstorganisation = new Tag();
        tagSelbstorganisation.setName("Selbstorganisation");
        tagSelbstorganisation.setCategory(softSkillsCategory);
        tagRepository.save(tagSelbstorganisation);

        tagTeamFaehigkeit = new Tag();
        tagTeamFaehigkeit.setName("Teamfähigkeit");
        tagTeamFaehigkeit.setCategory(softSkillsCategory);
        tagRepository.save(tagTeamFaehigkeit);

        tagFuehrungskompentenzen = new Tag();
        tagFuehrungskompentenzen.setName("Führungskompetenz");
        tagFuehrungskompentenzen.setCategory(softSkillsCategory);
        tagRepository.save(tagFuehrungskompentenzen);

        tagDesignThinking = new Tag();
        tagDesignThinking.setName("Design Thinking");
        tagDesignThinking.setCategory(softSkillsCategory);
        tagRepository.save(tagDesignThinking);

        tagStressbewaeltigung = new Tag();
        tagStressbewaeltigung.setName("Stressbewältigung");
        tagStressbewaeltigung.setCategory(softSkillsCategory);
        tagRepository.save(tagStressbewaeltigung);

        tagKonfliktmanagement = new Tag();
        tagKonfliktmanagement.setName("Konfliktmanagement");
        tagKonfliktmanagement.setCategory(softSkillsCategory);
        tagRepository.save(tagKonfliktmanagement);

        tagMedienkompetenz = new Tag();
        tagMedienkompetenz.setName("Medienkompetenz");
        tagMedienkompetenz.setCategory(softSkillsCategory);
        tagRepository.save(tagMedienkompetenz);

        tagKommunikation = new Tag();
        tagKommunikation.setName("Kommunikation");
        tagKommunikation.setCategory(softSkillsCategory);
        tagRepository.save(tagKommunikation);

        tagVirtuellesArbeiten = new Tag();
        tagVirtuellesArbeiten.setName("Virtuelles Arbeiten");
        tagVirtuellesArbeiten.setCategory(softSkillsCategory);
        tagRepository.save(tagVirtuellesArbeiten);

        tagPraesentationstechnicken = new Tag();
        tagPraesentationstechnicken.setName("Präsentationstechnicken");
        tagPraesentationstechnicken.setCategory(softSkillsCategory);
        tagRepository.save(tagPraesentationstechnicken);

        tagLernkompetenzen = new Tag();
        tagLernkompetenzen.setName("Lernkompetenz");
        tagLernkompetenzen.setCategory(softSkillsCategory);
        tagRepository.save(tagLernkompetenzen);

        tagVerwaltungsstruktur = new Tag();
        tagVerwaltungsstruktur.setName("Vermittlungskompetenz");
        tagVerwaltungsstruktur.setCategory(softSkillsCategory);
        tagRepository.save(tagVerwaltungsstruktur);


// Sozio-technisch Category
        tagKundenbeduerfnisseAnforderungsmanagement = new Tag();
        tagKundenbeduerfnisseAnforderungsmanagement.setName("Kundenbedürfnisse/ Anforderungsmanagement");
        tagKundenbeduerfnisseAnforderungsmanagement.setCategory(sozioTechnischCategory);
        tagRepository.save(tagKundenbeduerfnisseAnforderungsmanagement);

        tagStakeholderAnalyse = new Tag();
        tagStakeholderAnalyse.setName("Stakeholder-Analysen");
        tagStakeholderAnalyse.setCategory(sozioTechnischCategory);
        tagRepository.save(tagStakeholderAnalyse);

        tagDigitaleTrends = new Tag();
        tagDigitaleTrends.setName("Digitale Trends");
        tagDigitaleTrends.setCategory(sozioTechnischCategory);
        tagRepository.save(tagDigitaleTrends);

// Technisch Category
        tagProgrammieren = new Tag();
        tagProgrammieren.setName("Programmierung");
        tagProgrammieren.setCategory(technischCategory);
        tagRepository.save(tagProgrammieren);

        tagSoftwarearchitektur = new Tag();
        tagSoftwarearchitektur.setName("Softwarearchitektur");
        tagSoftwarearchitektur.setCategory(technischCategory);
        tagRepository.save(tagSoftwarearchitektur);

        tagEntwicklungvonSicherheitskonzepten = new Tag();
        tagEntwicklungvonSicherheitskonzepten.setName("Entwicklungs von Sicherheitskonzepten");
        tagEntwicklungvonSicherheitskonzepten.setCategory(technischCategory);
        tagRepository.save(tagEntwicklungvonSicherheitskonzepten);

        tagBetirebssymsteme = new Tag();
        tagBetirebssymsteme.setName("Betriebssysteme");
        tagBetirebssymsteme.setCategory(technischCategory);
        tagRepository.save(tagBetirebssymsteme);

        tagErpSysteme = new Tag();
        tagErpSysteme.setName("ERP-Systeme");
        tagErpSysteme.setCategory(technischCategory);
        tagRepository.save(tagErpSysteme);

        tagDataMining = new Tag();
        tagDataMining.setName("Data Mining");
        tagDataMining.setCategory(technischCategory);
        tagRepository.save(tagDataMining);

        tagDatenbankmanagement = new Tag();
        tagDatenbankmanagement.setName("Datenbankmanagement");
        tagDatenbankmanagement.setCategory(technischCategory);
        tagRepository.save(tagDatenbankmanagement);

        tagKI = new Tag();
        tagKI.setName("KI");
        tagKI.setCategory(technischCategory);
        tagRepository.save(tagKI);

        tagGrafikdesignBilbearbeitung = new Tag();
        tagGrafikdesignBilbearbeitung.setName("Grafikdesign & Bildbearbeitung");
        tagGrafikdesignBilbearbeitung.setCategory(technischCategory);
        tagRepository.save(tagGrafikdesignBilbearbeitung);

        tagKollaborationstools = new Tag();
        tagKollaborationstools.setName("Kollaborations-Tools");
        tagKollaborationstools.setCategory(technischCategory);
        tagRepository.save(tagKollaborationstools);

        tagOutlook = new Tag();
        tagOutlook.setName("Outlook");
        tagOutlook.setCategory(technischCategory);
        tagRepository.save(tagOutlook);

        tagWord = new Tag();
        tagWord.setName("Word");
        tagWord.setCategory(technischCategory);
        tagRepository.save(tagWord);

        tagExcel = new Tag();
        tagExcel.setName("Excel");
        tagExcel.setCategory(technischCategory);
        tagRepository.save(tagExcel);

        tagItSicherheit = new Tag();
        tagItSicherheit.setName("It-Sicherheit");
        tagItSicherheit.setCategory(technischCategory);
        tagRepository.save(tagItSicherheit);

        tagHardwarekompetenz = new Tag();
        tagHardwarekompetenz.setName("Hardwarekompetenz");
        tagHardwarekompetenz.setCategory(technischCategory);
        tagRepository.save(tagHardwarekompetenz);

// oeffentlich / Politisch Category
        tagBuergerzentrierung = new Tag();
        tagBuergerzentrierung.setName("Bürgerzentrierung");
        tagBuergerzentrierung.setCategory(oeffenlichPolitischCategory);
        tagRepository.save(tagBuergerzentrierung);

        tagDatenschutz = new Tag();
        tagDatenschutz.setName("Datenschutz");
        tagDatenschutz.setCategory(oeffenlichPolitischCategory);
        tagRepository.save(tagDatenschutz);

        tagCompliance = new Tag();
        tagCompliance.setName("Compliance");
        tagCompliance.setCategory(oeffenlichPolitischCategory);
        tagRepository.save(tagCompliance);

        tagFremdsprache = new Tag();
        tagFremdsprache.setName("Fremdsprache");
        tagFremdsprache.setCategory(oeffenlichPolitischCategory);
        tagRepository.save(tagFremdsprache);

        tagInformationsicherheit = new Tag();
        tagInformationsicherheit.setName("Informationssicherheit");
        tagInformationsicherheit.setCategory(oeffenlichPolitischCategory);
        tagRepository.save(tagInformationsicherheit);
    }

    private void addTagsToUsers() {

        userTag1 = new UserTag();
        userTag1.setUser(user11);
        userTag1.setRating(1);
        userTag1.setTag(tagDesignThinking);

        userTagRepository.save(userTag1);

        userTag2 = new UserTag();
        userTag2.setUser(user11);
        userTag2.setRating(1);
        userTag2.setTag(tagProjektmanagement);

        userTagRepository.save(userTag2);

        userTag3 = new UserTag();
        userTag3.setUser(user11);
        userTag3.setRating(1);
        userTag3.setTag(tagProjektplanung);

        userTagRepository.save(userTag3);

        userTag4 = new UserTag();
        userTag4.setUser(user11);
        userTag4.setRating(1);
        userTag4.setTag(tagTeamFaehigkeit);

        userTagRepository.save(userTag4);

    }

    private void addTagsToCourses() {

// Associate tags with Course 1
        CourseTag courseTagProjektPlanung1 = new CourseTag();
        courseTagProjektPlanung1.setCourse(course1);
        courseTagProjektPlanung1.setTag(tagProjektplanung);
        courseTagRepository.save(courseTagProjektPlanung1);

        CourseTag courseTagOrganisationsformen1 = new CourseTag();
        courseTagOrganisationsformen1.setCourse(course1);
        courseTagOrganisationsformen1.setTag(tagOrganisationsformen);
        courseTagRepository.save(courseTagOrganisationsformen1);

        CourseTag courseTagFuehrungskompetenz = new CourseTag();
        courseTagFuehrungskompetenz.setCourse(course1);
        courseTagFuehrungskompetenz.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskompetenz);

// Associate tags with Course 2
        CourseTag courseTagProjectManagement2 = new CourseTag();
        courseTagProjectManagement2.setCourse(course2);
        courseTagProjectManagement2.setTag(tagProjektmanagement);
        courseTagRepository.save(courseTagProjectManagement2);

        CourseTag courseTagFuehrungskompetenz2 = new CourseTag();
        courseTagFuehrungskompetenz2.setCourse(course2);
        courseTagFuehrungskompetenz2.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskompetenz2);

// Associate tags with Course 3
        CourseTag courseTagItSicherheit3 = new CourseTag();
        courseTagItSicherheit3.setCourse(course3);
        courseTagItSicherheit3.setTag(tagItSicherheit);
        courseTagRepository.save(courseTagItSicherheit3);

        CourseTag courseTagDatenschutz3 = new CourseTag();
        courseTagDatenschutz3.setCourse(course3);
        courseTagDatenschutz3.setTag(tagDatenschutz);
        courseTagRepository.save(courseTagDatenschutz3);

// Associate tags with Course 4
        CourseTag courseTagOrganisationsformen4 = new CourseTag();
        courseTagOrganisationsformen4.setCourse(course4);
        courseTagOrganisationsformen4.setTag(tagOrganisationsformen);
        courseTagRepository.save(courseTagOrganisationsformen4);

        CourseTag courseTagProzessmanagement4 = new CourseTag();
        courseTagProzessmanagement4.setCourse(course4);
        courseTagProzessmanagement4.setTag(tagProzessmanagement);
        courseTagRepository.save(courseTagProzessmanagement4);

        CourseTag courseTagFuehrungskompetenz4 = new CourseTag();
        courseTagFuehrungskompetenz4.setCourse(course4);
        courseTagFuehrungskompetenz4.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskompetenz4);

// Associate tags with Course 5
        CourseTag courseTagFuehrungskompetenz5 = new CourseTag();
        courseTagFuehrungskompetenz5.setCourse(course5);
        courseTagFuehrungskompetenz5.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskompetenz5);

        CourseTag courseTagKonfliktmanagement5 = new CourseTag();
        courseTagKonfliktmanagement5.setCourse(course5);
        courseTagKonfliktmanagement5.setTag(tagKonfliktmanagement);
        courseTagRepository.save(courseTagKonfliktmanagement5);

        CourseTag courseTagKommunikation5 = new CourseTag();
        courseTagKommunikation5.setCourse(course5);
        courseTagKommunikation5.setTag(tagKommunikation);
        courseTagRepository.save(courseTagKommunikation5);

// Associate tags with Course 6
        CourseTag courseTagVerwaltungsrecht6 = new CourseTag();
        courseTagVerwaltungsrecht6.setCourse(course6);
        courseTagVerwaltungsrecht6.setTag(tagVerwaltungsrecht);
        courseTagRepository.save(courseTagVerwaltungsrecht6);


// Associate tags with Course 7
        CourseTag courseTagKonfliktmanagement7 = new CourseTag();
        courseTagKonfliktmanagement7.setCourse(course7);
        courseTagKonfliktmanagement7.setTag(tagKonfliktmanagement);
        courseTagRepository.save(courseTagKonfliktmanagement7);

        CourseTag coursetagVerwaltungsstruktur7 = new CourseTag();
        coursetagVerwaltungsstruktur7.setCourse(course7);
        coursetagVerwaltungsstruktur7.setTag(tagVerwaltungsstruktur);
        courseTagRepository.save(coursetagVerwaltungsstruktur7);

// Associate tags with Course 8
        CourseTag courseTagKundenbeduerfnisse8 = new CourseTag();
        courseTagKundenbeduerfnisse8.setCourse(course8);
        courseTagKundenbeduerfnisse8.setTag(tagKundenbeduerfnisseAnforderungsmanagement);
        courseTagRepository.save(courseTagKundenbeduerfnisse8);

        CourseTag courseTagKommunikation8 = new CourseTag();
        courseTagKommunikation8.setCourse(course8);
        courseTagKommunikation8.setTag(tagKommunikation);
        courseTagRepository.save(courseTagKommunikation8);

// Associate tags with Course 9
        CourseTag courseTagKonfliktmanagement9 = new CourseTag();
        courseTagKonfliktmanagement9.setCourse(course9);
        courseTagKonfliktmanagement9.setTag(tagKonfliktmanagement);
        courseTagRepository.save(courseTagKonfliktmanagement9);

// Associate tags with Course 10
        CourseTag courseTagSelbstorganisation10 = new CourseTag();
        courseTagSelbstorganisation10.setCourse(course10);
        courseTagSelbstorganisation10.setTag(tagSelbstorganisation);
        courseTagRepository.save(courseTagSelbstorganisation10);

        CourseTag courseTagStessbewaeltigung10 = new CourseTag();
        courseTagStessbewaeltigung10.setCourse(course10);
        courseTagStessbewaeltigung10.setTag(tagStressbewaeltigung);
        courseTagRepository.save(courseTagStessbewaeltigung10);

// Associate tags with Course 11
        CourseTag courseTagRessourcenmanagement11 = new CourseTag();
        courseTagRessourcenmanagement11.setCourse(course11);
        courseTagRessourcenmanagement11.setTag(tagResourcenmanagement);
        courseTagRepository.save(courseTagRessourcenmanagement11);

        CourseTag courseTagVerwaltungsstruktur11 = new CourseTag();
        courseTagVerwaltungsstruktur11.setCourse(course11);
        courseTagVerwaltungsstruktur11.setTag(tagVerwaltungsstruktur);
        courseTagRepository.save(courseTagVerwaltungsstruktur11);

// Associate tags with Course 12
        CourseTag courseTagSoftwarearchitektur12 = new CourseTag();
        courseTagSoftwarearchitektur12.setCourse(course12);
        courseTagSoftwarearchitektur12.setTag(tagSoftwarearchitektur);
        courseTagRepository.save(courseTagSoftwarearchitektur12);

        CourseTag courseTagDatenbankmanagement12 = new CourseTag();
        courseTagDatenbankmanagement12.setCourse(course12);
        courseTagDatenbankmanagement12.setTag(tagDatenbankmanagement);
        courseTagRepository.save(courseTagDatenbankmanagement12);

// Associate tags with Course 13
        CourseTag courseTagBuergerzentrierung = new CourseTag();
        courseTagBuergerzentrierung.setCourse(course13);
        courseTagBuergerzentrierung.setTag(tagBuergerzentrierung);
        courseTagRepository.save(courseTagBuergerzentrierung);

        CourseTag courseTagVerwaltungsprozesse = new CourseTag();
        courseTagVerwaltungsprozesse.setCourse(course13);
        courseTagVerwaltungsprozesse.setTag(tagVerwaltungsprozesse);
        courseTagRepository.save(courseTagVerwaltungsprozesse);

// Associate tags with Course 14
        CourseTag courseTagLernkompetenzen14 = new CourseTag();
        courseTagLernkompetenzen14.setCourse(course14);
        courseTagLernkompetenzen14.setTag(tagLernkompetenzen);
        courseTagRepository.save(courseTagLernkompetenzen14);

        CourseTag courseTagInformationssicherheit14 = new CourseTag();
        courseTagInformationssicherheit14.setCourse(course14);
        courseTagInformationssicherheit14.setTag(tagInformationsicherheit);
        courseTagRepository.save(courseTagInformationssicherheit14);

// Associate tags with Course 15
        CourseTag courseTagfuehrungskompentenze15 = new CourseTag();
        courseTagfuehrungskompentenze15.setCourse(course15);
        courseTagfuehrungskompentenze15.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagfuehrungskompentenze15);

        CourseTag coursetagVerwaltungsstruktur15 = new CourseTag();
        coursetagVerwaltungsstruktur15.setCourse(course15);
        coursetagVerwaltungsstruktur15.setTag(tagVerwaltungsstruktur);
        courseTagRepository.save(coursetagVerwaltungsstruktur15);

// Associate tags with Course 16
        CourseTag courseTageGovernmentRecht = new CourseTag();
        courseTageGovernmentRecht.setCourse(course16);
        courseTageGovernmentRecht.setTag(tageGovernmentRecht);
        courseTagRepository.save(courseTageGovernmentRecht);

// Associate tags with Course 17
        CourseTag courseTagProgrammieren17 = new CourseTag();
        courseTagProgrammieren17.setCourse(course17);
        courseTagProgrammieren17.setTag(tagProgrammieren);
        courseTagRepository.save(courseTagProgrammieren17);

// Associate tags with Course 18
        CourseTag courseTagSelbstorganisation18 = new CourseTag();
        courseTagSelbstorganisation18.setCourse(course18);
        courseTagSelbstorganisation18.setTag(tagSelbstorganisation);
        courseTagRepository.save(courseTagSelbstorganisation18);

        CourseTag courseTagOutlook18 = new CourseTag();
        courseTagOutlook18.setCourse(course18);
        courseTagOutlook18.setTag(tagOutlook);
        courseTagRepository.save(courseTagOutlook18);

// Associate tags with Course 19
        CourseTag courseTagQualitaetsmanagement19 = new CourseTag();
        courseTagQualitaetsmanagement19.setCourse(course19);
        courseTagQualitaetsmanagement19.setTag(tagQulitaetsmanagement);
        courseTagRepository.save(courseTagQualitaetsmanagement19);

        CourseTag courseTagKundenbeduerfnisse19 = new CourseTag();
        courseTagKundenbeduerfnisse19.setCourse(course19);
        courseTagKundenbeduerfnisse19.setTag(tagKundenbeduerfnisseAnforderungsmanagement);
        courseTagRepository.save(courseTagKundenbeduerfnisse19);

// Associate tags with Course 20
        CourseTag courseTagRisikomanagement20 = new CourseTag();
        courseTagRisikomanagement20.setCourse(course20);
        courseTagRisikomanagement20.setTag(tagQulitaetsmanagement);
        courseTagRepository.save(courseTagRisikomanagement20);

        CourseTag courseTagFuehrungskompetenz20 = new CourseTag();
        courseTagFuehrungskompetenz20.setCourse(course20);
        courseTagFuehrungskompetenz20.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskompetenz20);

// Associate tags with Course 21
        CourseTag courseTagKonfliktmanagement21 = new CourseTag();
        courseTagKonfliktmanagement21.setCourse(course21);
        courseTagKonfliktmanagement21.setTag(tagKonfliktmanagement);
        courseTagRepository.save(courseTagKonfliktmanagement21);

        CourseTag courseTagKommunikation21 = new CourseTag();
        courseTagKommunikation21.setCourse(course21);
        courseTagKommunikation21.setTag(tagKommunikation);
        courseTagRepository.save(courseTagKommunikation21);

        CourseTag courseTagFuehrungskometenz21 = new CourseTag();
        courseTagFuehrungskometenz21.setCourse(course21);
        courseTagFuehrungskometenz21.setTag(tagFuehrungskompentenzen);
        courseTagRepository.save(courseTagFuehrungskometenz21);

// Associate tags with Course 22
        CourseTag courseTagFremdsprachen22 = new CourseTag();
        courseTagFremdsprachen22.setCourse(course22);
        courseTagFremdsprachen22.setTag(tagFremdsprache);
        courseTagRepository.save(courseTagFremdsprachen22);

// Associate tags with Course 23
        CourseTag courseTagCompliance23 = new CourseTag();
        courseTagCompliance23.setCourse(course23);
        courseTagCompliance23.setTag(tagCompliance);
        courseTagRepository.save(courseTagCompliance23);

// Associate tags with Course 24
        CourseTag courseTagDigitaleTrends24 = new CourseTag();
        courseTagDigitaleTrends24.setCourse(course24);
        courseTagDigitaleTrends24.setTag(tagDigitaleTrends);
        courseTagRepository.save(courseTagDigitaleTrends24);

        CourseTag courseTagVirtuellesArbeiten = new CourseTag();
        courseTagVirtuellesArbeiten.setCourse(course24);
        courseTagVirtuellesArbeiten.setTag(tagVirtuellesArbeiten);
        courseTagRepository.save(courseTagVirtuellesArbeiten);

// Associate tags with Course 25
        CourseTag courseTagPraesentationstechnicken25 = new CourseTag();
        courseTagPraesentationstechnicken25.setCourse(course25);
        courseTagPraesentationstechnicken25.setTag(tagPraesentationstechnicken);
        courseTagRepository.save(courseTagPraesentationstechnicken25);

        CourseTag courseTagMedienkompetent25 = new CourseTag();
        courseTagMedienkompetent25.setCourse(course25);
        courseTagMedienkompetent25.setTag(tagMedienkompetenz);
        courseTagRepository.save(courseTagMedienkompetent25);

// Associate tags with Course 26
        CourseTag courseTagDatenschutz26 = new CourseTag();
        courseTagDatenschutz26.setCourse(course26);
        courseTagDatenschutz26.setTag(tagDatenschutz);
        courseTagRepository.save(courseTagDatenschutz26);

        CourseTag courseTagKI26 = new CourseTag();
        courseTagKI26.setCourse(course26);
        courseTagKI26.setTag(tagKI);
        courseTagRepository.save(courseTagKI26);
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
        feedback1Course2.setDescription("Der Kurs hat meine Erwartungen erfuellt und ich konnte mein Wissen im Projektmanagement erweitern.");
        feedback1Course2.setCreatedAt(new Date());
        course2.addFeedback(feedback1Course2);
        courseRepository.save(course2);

// Feedback for Course 3
        Feedback feedback1Course3 = new Feedback();
        feedback1Course3.setCourse(course3);
        feedback1Course3.setUser(user3);
        feedback1Course3.setRating(3);
        feedback1Course3.setTitle("Gut, aber verbesserungsfähig");
        feedback1Course3.setDescription("Der Kurs gibt einen guten Überblick ueber IT-Sicherheit, aber einige Themen könnten detaillierter behandelt werden.");
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
        feedback1Course9.setDescription("Der Kurs hat meine kreativen Denkprozesse angeregt und mir neue Loesungsansätze aufgezeigt.");
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
        feedback1Course15.setTitle("Effektive Fuehrungskräftestrategien");
        feedback1Course15.setDescription("Der Kurs vermittelt effektive Strategien für Fuehrungskräfte zur erfolgreichen Einflussnahme.");
        feedback1Course15.setCreatedAt(new Date());
        course15.addFeedback(feedback1Course15);
        courseRepository.save(course15);


    }

    private void insertRoles(){
        OrganisationStratege = new Role();
        OrganisationStratege.setName("Organisation");
        OrganisationStratege.setDescription("");
        OrganisationStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(OrganisationStratege);

        OrganisationEntscheidungsträger = new Role();
        OrganisationEntscheidungsträger.setName("Organisation");
        OrganisationEntscheidungsträger.setDescription("");
        OrganisationEntscheidungsträger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(OrganisationEntscheidungsträger);

        OrganisationUmsetzer = new Role();
        OrganisationUmsetzer.setName("Organisation");
        OrganisationUmsetzer.setDescription("");
        OrganisationUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(OrganisationUmsetzer);

        DigitalisierungStratege = new Role();
        DigitalisierungStratege.setName("Digitalisierung");
        DigitalisierungStratege.setDescription("");
        DigitalisierungStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(DigitalisierungStratege);

        DigitalisierungEntscheidungsträger = new Role();
        DigitalisierungEntscheidungsträger.setName("Digitalisierung");
        DigitalisierungEntscheidungsträger.setDescription("");
        DigitalisierungEntscheidungsträger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(DigitalisierungEntscheidungsträger);

        DigitalisierungUmsetzer = new Role();
        DigitalisierungUmsetzer.setName("Digitalisierung");
        DigitalisierungUmsetzer.setDescription("");
        DigitalisierungUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(DigitalisierungUmsetzer);

        InformationstechnikStratege = new Role();
        InformationstechnikStratege.setName("Informationstechnik");
        InformationstechnikStratege.setDescription("");
        InformationstechnikStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(InformationstechnikStratege);

        InformationstechnikEntscheidungsträger = new Role();
        InformationstechnikEntscheidungsträger.setName("Informationstechnik");
        InformationstechnikEntscheidungsträger.setDescription("");
        InformationstechnikEntscheidungsträger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(InformationstechnikEntscheidungsträger);

        InformationstechnikUmsetzer = new Role();
        InformationstechnikUmsetzer.setName("Informationstechnik");
        InformationstechnikUmsetzer.setDescription("");
        DigitalisierungUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(InformationstechnikUmsetzer);

        SmartCityStratege = new Role();
        SmartCityStratege.setName("Smart City");
        SmartCityStratege.setDescription("");
        SmartCityStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(SmartCityStratege);

        SmartCityEntscheidungsTräger = new Role();
        SmartCityEntscheidungsTräger.setName("Smart City");
        SmartCityEntscheidungsTräger.setDescription("");
        SmartCityEntscheidungsTräger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(SmartCityEntscheidungsTräger);

        SmartCityUmsetzer = new Role();
        SmartCityUmsetzer.setName("Smart City");
        SmartCityUmsetzer.setDescription("");
        SmartCityUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(SmartCityUmsetzer);

        NichtDigitalStratege = new Role();
        NichtDigitalStratege.setName("Nicht-digital");
        NichtDigitalStratege.setDescription("");
        NichtDigitalStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(NichtDigitalStratege);

        NichtDigitalEntscheidungsträger = new Role();
        NichtDigitalEntscheidungsträger.setName("Nicht-digital");
        NichtDigitalEntscheidungsträger.setDescription("");
        NichtDigitalEntscheidungsträger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(NichtDigitalEntscheidungsträger);

        NichtDigitalUmsetzer = new Role();
        NichtDigitalUmsetzer.setName("Nicht-digital");
        NichtDigitalUmsetzer.setDescription("");
        NichtDigitalUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(NichtDigitalUmsetzer);

        PersonalStratege = new Role();
        PersonalStratege.setName("Personal");
        PersonalStratege.setDescription("");
        PersonalStratege.setVerantwortungsbereich(Verantwortungsbereich.Stratege);
        roleRepository.save(PersonalStratege);

        PersonalEntscheidungsträger = new Role();
        PersonalEntscheidungsträger.setName("Personal");
        PersonalEntscheidungsträger.setDescription("");
        PersonalEntscheidungsträger.setVerantwortungsbereich(Verantwortungsbereich.Entscheidungsträger);
        roleRepository.save(PersonalEntscheidungsträger);

        PersonalUmsetzer = new Role();
        PersonalUmsetzer.setName("Personal");
        PersonalUmsetzer.setDescription("");
        PersonalUmsetzer.setVerantwortungsbereich(Verantwortungsbereich.Umsetzer);
        roleRepository.save(PersonalUmsetzer);
    }
}
