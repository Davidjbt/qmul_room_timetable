package com.david.qmul_room_timetable;

import com.david.qmul_room_timetable.dto.RoomTimetableQuery;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class FetchRoomTimetableTask implements Runnable {

    private final RoomTimetableQuery roomTimetableQuery;
    private String roomTimetable;

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
        dropdown.selectByVisibleText(this.roomTimetableQuery.getBuilding());

        dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("dlObject")))));

        for (String room: this.roomTimetableQuery.getRooms())
            dropdown.selectByVisibleText(room);

        dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("lbWeeks")))));
        dropdown.deselectByVisibleText("All Weeks");
        dropdown.selectByVisibleText(this.roomTimetableQuery.getWeek());

        String day = String.valueOf(LocalDate.now().getDayOfWeek());
        day = day.charAt(0) + day.substring(1).toLowerCase();
        dropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("lbDays")))));
        dropdown.selectByVisibleText(day);

        WebElement viewTimetableBtn = driver.findElement(By.id("bGetTimetable"));
        viewTimetableBtn.click();

        roomTimetable = driver.getPageSource()
                .replaceAll("\n", "")
                .replaceAll("\"",  "'");

        driver.close();
    }

}
