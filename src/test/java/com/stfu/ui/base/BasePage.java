package com.stfu.ui.base;
import com.stfu.ui.page_objects.HomePage;
import com.stfu.ui.page_objects.TeamPage;
import com.stfu.ui.params.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }


    public TeamPage navigateToTeamPage(String teamName) {
        By header2Selector = By.cssSelector("h2 b");
        driver.navigate().to(Constants.BASE_URL+teamName);
        assertEquals(teamName+" | STFU AND CLICK", driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header2Selector));

        return new TeamPage(driver);
    }
    public HomePage navigateToHomePage() {
        driver.navigate().to(Constants.BASE_URL);
        assertEquals("STFU AND CLICK", driver.getTitle());

        By headerSelector = By.cssSelector("header h1");
        By leaderboardSelector = By.xpath("//main//table//tr[7]/td[3]");

        WebElement header = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(headerSelector));

        WebElement leaderboard = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(leaderboardSelector));


        return new HomePage(driver);
    }

}