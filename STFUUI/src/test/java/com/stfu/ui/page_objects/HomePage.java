package com.stfu.ui.page_objects;

import com.stfu.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private final By leaderboardSelector = By.cssSelector("table.sc-jTzLTM.ciUzJU");
    private final By clickButtonSelector = By.cssSelector("form button");
    private final By inputTeamNameSelector = By.cssSelector("input#name");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage checkLeaderboardIsShown() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement leaderboard = driver.findElement(leaderboardSelector);

        wait.until(ExpectedConditions.visibilityOfElementLocated(leaderboardSelector));

        return new HomePage(driver);
    }

    public HomePage checkClickButtonIsShownAndClickable() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(clickButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(clickButtonSelector));

        return new HomePage(driver);
    }

    public HomePage checkInputTeamName() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(inputTeamNameSelector));
        wait.until(ExpectedConditions.elementToBeClickable(clickButtonSelector));

        return new HomePage(driver);
    }

    public void typeInputValue(String teamName) {
        WebElement inputTeamName = driver.findElement(inputTeamNameSelector);
        inputTeamName.sendKeys(teamName);
    }

    public void pressClickButton() {
        driver.findElement(clickButtonSelector).click();
    }
    //signInButton.submit();

}