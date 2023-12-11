package com.david.qmul_room_timetable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;

public class Main {

    public static class Search implements Runnable {

        private String building;
        private String[] rooms;

        public Search(String building, String[] rooms) {
            this.building = building;
            this.rooms = rooms;
        }

        @Override
        public void run() {
            WebDriver driver = new ChromeDriver();

            driver.get("https://timetables.qmul.ac.uk/default.aspx");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement locationsBtn = driver.findElement(By.id("LinkBtn_locations"));
            locationsBtn.click();

            Select dropdown = new Select(driver.findElement(By.id("dlFilter2")));
            dropdown.selectByVisibleText("Mile End Campus");

            dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlFilter")))));
            dropdown.selectByVisibleText(this.building);

            dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlObject")))));

            for (String room: this.rooms)
                dropdown.selectByVisibleText(room);

            dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("lbWeeks")))));
            dropdown.deselectByVisibleText("All Weeks");
            dropdown.selectByVisibleText("This Week");

            String day = String.valueOf(LocalDate.now().getDayOfWeek());
            day = day.charAt(0) + day.substring(1).toLowerCase();
            dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("lbDays")))));
            dropdown.selectByVisibleText(day);

            WebElement viewTimetableBtn = driver.findElement(By.id("bGetTimetable"));
            viewTimetableBtn.click();

//            System.out.println(driver.getPageSource());
        }
    }

    public static void main(String[] args) {
        String[] roomsJones = new String[]{"G.O.Jones:LG7 (18)", "G.O.Jones:LG1 (60)"};
        String[] roomsQueens = new String[]{"Queens: QB-210 (30)", "Queens: QB-211 (18)"};
        new Thread(new Search("B/G.O.Jones", roomsJones)).start();
        new Thread(new Search("B/Queens'", roomsQueens)).start();
    }

}
