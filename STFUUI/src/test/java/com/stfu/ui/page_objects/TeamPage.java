package com.stfu.ui.page_objects;

import com.stfu.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TeamPage extends BasePage {

    private final By clickButtonSelector = By.cssSelector("main button");
    private final By leaderboardSelector = By.xpath("//main//table//tr[7]/td[3]");
    private final By yourClicksSelector = By.xpath("//main/div/div[1]//h3/following-sibling::p");
    private final By teamClicksSelector = By.xpath("//main/div/div[2]//h3/following-sibling::p");
    private final By teamClicksLeaderboardSelector = By.xpath("//table//tr[4]/td[3]");

    public TeamPage(WebDriver driver) {
        super(driver);
    }

    public By getTeamClicksLeaderboardSelector() {
        return teamClicksLeaderboardSelector;
    }

    public TeamPage checkLeaderboardIsShown() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(leaderboardSelector));

        return new TeamPage(driver);
    }

    public TeamPage checkClickButtonIsShownAndClickable() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(clickButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(clickButtonSelector));

        return new TeamPage(driver);
    }

    public void clickButton(String i) {
        int prevYourClicks = getYourClicks();
        int prevTeamClicks = getTeamClicks();

        for (int j = 0; j < Integer.parseInt(i); j++) {
            WebDriverWait wait = new WebDriverWait(driver, 30);

            WebElement clickButton = driver.findElement(clickButtonSelector);

            clickButton.click();
            WebElement currYourClicks = driver.findElement(yourClicksSelector);
            System.out.println(" " + currYourClicks.getAttribute("innerText"));
        }
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.attributeToBe(yourClicksSelector, "innerText", String.valueOf(prevYourClicks + Integer.parseInt(i))));

        String data = String.valueOf(prevTeamClicks + Integer.parseInt(i));
        StringBuilder sb = new StringBuilder(data);
        for (int ij = sb.length() - 3; ij > 0; ij -= 3)
            sb.insert(ij, ' ');
        data = sb.toString();
        System.out.println(data);

        wait.until(ExpectedConditions.attributeToBe(teamClicksLeaderboardSelector, "innerText", data));
        System.out.print(" " + driver.findElement(yourClicksSelector).getAttribute("innerText"));
    }

    public int getYourClicks() {
        return Integer.parseInt(driver.findElement(yourClicksSelector).getText().replaceAll("\\s", ""));
    }

    public int getTeamClicks() {
        return Integer.parseInt(driver.findElement(teamClicksSelector).getText().replaceAll("\\s", ""));
    }

    public int getTeamClicksLeaderboard() {
        return Integer.parseInt(driver.findElement(teamClicksLeaderboardSelector).getText().replaceAll("\\s", ""));
    }

    public String getTeamClicksLeaderboardString() {
        return driver.findElement(teamClicksLeaderboardSelector).getText();
    }
}