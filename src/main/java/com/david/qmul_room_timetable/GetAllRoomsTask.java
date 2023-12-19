package com.david.qmul_room_timetable;

import com.david.qmul_room_timetable.dto.Campus;
import com.david.qmul_room_timetable.dto.Campus.Building;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GetAllRoomsTask {

    @Getter
    private static class GetRoomsInCampusTask implements Runnable {

        private final Campus campus;

        public GetRoomsInCampusTask(Campus campus) {
            this.campus = campus;
        }

        @Override
        public void run() {
            WebDriver driver = new ChromeDriver();

            driver.get("https://timetables.qmul.ac.uk/default.aspx");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement locationsBtn = driver.findElement(By.id("LinkBtn_locations"));
            locationsBtn.click();

            Select dropdown = new Select(driver.findElement(By.id("dlFilter2")));
            dropdown.selectByVisibleText(campus.getCampus());

            dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlFilter")))));
            List<String> buildingsName = dropdown.getOptions()
                    .stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.equals("(Any Building)")
                            || campus.getCampus().equals("IoT")
                            || campus.getCampus().equals("Lincoln's Inn Fields Campus"))
                    .toList();

            System.out.println(campus.getCampus() + ": " + buildingsName);
            List<Building> buildings = new ArrayList<>();

            for (String buildingName : buildingsName) {
                dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlFilter")))));
                dropdown.selectByVisibleText(buildingName);

                dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlObject")))));
                List<String> rooms = dropdown.getOptions()
                        .stream()
                        .map(WebElement::getText)
                        .toList();

                Building building = new Building(buildingName, rooms.toArray(new String[0]));
                buildings.add(building);
            }

            campus.setBuildings(buildings);

            driver.close();

        }
    }

    public Campus[] getAllRooms() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://timetables.qmul.ac.uk/default.aspx");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement locationsBtn = driver.findElement(By.id("LinkBtn_locations"));
        locationsBtn.click();

        Select dropdown = new Select(driver.findElement(By.id("dlFilter2")));
        List<Campus> campuses = dropdown.getOptions()
                .stream()
                .filter(option -> !option.getText().equals("(Any Campus)"))
                .map(option -> new Campus(option.getText()))
                .toList();

        driver.close();

        List<GetRoomsInCampusTask> tasks = campuses.stream()
                .map(GetRoomsInCampusTask::new)
                .toList();

        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        tasks.forEach(executorService::execute);
        executorService.shutdown();
        executorService.awaitTermination(30,  TimeUnit.SECONDS);

        return tasks.stream()
                .map(GetRoomsInCampusTask::getCampus)
                .toArray(Campus[]::new);
    }

}
