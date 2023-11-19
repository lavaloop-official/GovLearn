package com.unimuenster.govlearnapi.webCrawler.service;

import com.unimuenster.govlearnapi.core.config.enums.Skilllevel;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.repository.CourseRepository;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class FindigShWebCrawler extends WebCrawler{

    private final CourseRepository courseRepository;

    private final String targetUrl = "https://findig.sh/web/guest/kurse";
    @Override
    public void crawl(UserEntity user) {
        //Write the crawler logic here

        // Driver for Win64 can be downloaded here: https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/119.0.6045.105/win64/chromedriver-win64.zip
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Star-Lord\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Create a new instance of the Chrome driver
        WebDriver driver = new ChromeDriver();

        // Navigate to the desired URL
        driver.get(targetUrl);

        loadAllData(driver);

        // Find and print the title of the page
        List<WebElement> cardTitles = driver.findElements(By.cssSelector("div.card"));

        for(WebElement cardTitle : cardTitles){
            Course course = createCourse(cardTitle, user);
            storeCourse(course);
        }

        driver.quit();
    }

    private void storeCourse(Course course){
        courseRepository.save(course);
    }

    private Course createCourse(WebElement cardTitle, UserEntity currentUser){
        String title = getTextIfExists(cardTitle, "span.card-title");
        String provider = getTextIfExists(cardTitle, "div.ucc-course-provider");
        String duration = getTextIfExists(cardTitle, "div.ucc-course-duration");
        String competence_level = getTextIfExists(cardTitle, "div.ucc-course-competence-level div.ucc-course-attribute");
        String image = getCourseImg(cardTitle, "img");
        boolean isFree = doesExist(cardTitle, "div.corner-top-left");

        return Course
                .builder()
                .name(title)
                .description("Not filled")
                .provider(provider)
                .image(image)
                .costFree(isFree)
                .skilllevel(
                        mapCompetence(competence_level)
                )
                .link("")
                .duration(duration)
                .creator(currentUser)
                .build();
    }

    private static Skilllevel mapCompetence(String competence_level){

        if(competence_level.toLowerCase().contains("einsteiger")) {
            return Skilllevel.Anfaenger;
        } else if (competence_level.toLowerCase().contains("fortgeschritten")) {
            return Skilllevel.Fortgeschritten;
        } else if (competence_level.toLowerCase().contains("experte")) {
            return Skilllevel.Experte;
        }

        return Skilllevel.Anfaenger;
    }

    private static boolean doesExist(WebElement element, String cssSelector){
        try{
            element.findElement(By.cssSelector(cssSelector));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private static String getCourseImg(WebElement element, String cssSelector) {
        try{
            return element.findElement(By.cssSelector(cssSelector)).getAttribute("src");
        }catch (Exception e){
            return "";
        }
    }

    private static String getTextIfExists(WebElement element, String cssSelector){
        try{
            return element.findElement(By.cssSelector(cssSelector)).getText();
        }catch (Exception e){
            return "";
        }
    }

    private void loadAllData(WebDriver driver){
        long startTime = System.currentTimeMillis();

        while(true) {
            long scrollHeight = getScrollHeight(driver);

            scrollToEnd(driver);

            try {
                // In chrome takes 0.75 ms to load new data
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (getScrollHeight(driver) == scrollHeight) {
                break;
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Total time taken to load all data: " + (endTime - startTime)/1000 + " s");
    }

    private static void scrollToEnd(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Helper method to get the scroll height using JavaScript
    private static long getScrollHeight(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
    }
}
