package com.stfu.ui.steps;


import com.stfu.ui.config.DriverBase;

import com.stfu.ui.page_objects.HomePage;
import com.stfu.ui.page_objects.TeamPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageSteps extends DriverBase {

    private HomePage homePage;
    private TeamPage teamPage;

    private ArrayList<String> tabs;
    private WebDriver driver;
    private int teamClicksLeaderboardStart;
    private int teamClicksStart;
    private int teamClicks1;
    private int teamClicks2;
    private String teamClicksLeaderboard1;
    private String teamClicksLeaderboard2;

    private int yourClicks1;
    private int yourClicks2;

    private void verifyTeamPage(String teamName) throws InterruptedException {
        By header2Selector = By.cssSelector("h2 b");
//        Thread.sleep(10*1000);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header2Selector));
        assertEquals(teamName + " | STFU AND CLICK", driver.getTitle());
    }

    private void waitLeaderboardSync() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        System.out.println("====Sync====");
        System.out.println(yourClicks1 + "\t" + teamClicks1 + "\t\t\t" + yourClicks2 + "\t" + +teamClicks2);

        driver.switchTo().window(tabs.get(0));

        teamClicksLeaderboard1 = teamPage.getTeamClicksLeaderboardString();
        driver.switchTo().window(tabs.get(1));
        teamClicksLeaderboard2 = teamPage.getTeamClicksLeaderboardString();
        System.out.println(teamClicksLeaderboard1 + "\t\t\t\t" + teamClicksLeaderboard2);

        wait.until(ExpectedConditions.attributeToBe(teamPage.getTeamClicksLeaderboardSelector(), "innerText",
                teamClicksLeaderboard1));

        System.out.println("after sync:");
        yourClicks2 = teamPage.getYourClicks();
        teamClicks2 = teamPage.getTeamClicks();
        teamClicksLeaderboard2 = teamPage.getTeamClicksLeaderboardString();
        driver.switchTo().window(tabs.get(0));
        yourClicks1 = teamPage.getYourClicks();
        teamClicks1 = teamPage.getTeamClicks();
        teamClicksLeaderboard1 = teamPage.getTeamClicksLeaderboardString();

        System.out.println(yourClicks1 + "\t" + teamClicks1 + "\t\t\t" + yourClicks2 + "\t" + teamClicks2);
        System.out.println(teamClicksLeaderboard1 + "\t\t\t\t" + teamClicksLeaderboard2);
    }

    @Given("I am at the Home page")
    public void i_am_at_the_Home_page() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Then("Leaderboard is shown on {string} page")
    public void leaderboardIsShown(String pageName) {
        if (pageName.equals("Home"))
            homePage.checkLeaderboardIsShown();
        if (pageName.equals("Team"))
            teamPage.checkLeaderboardIsShown();
    }

    @And("Team name input works")
    public void teamNameInputWorks() {
        homePage.checkInputTeamName();
    }

    @And("All buttons are clickable on {string} page")
    public void buttonsAreClickable(String pageName) {
        if (pageName.equals("Home"))
            homePage.checkClickButtonIsShownAndClickable();
        if (pageName.equals("Team"))
            teamPage.checkClickButtonIsShownAndClickable();

    }

    @Given("I am at the {string} Team page")
    public void iAmAtTheTeamPage(String teamName) {
        teamPage = new TeamPage(driver);
        teamPage.navigateToTeamPage(teamName);
    }

    @When("I type {string} team name")
    public void iTypeTeamName(String teamName) {
        homePage.typeInputValue(teamName);
    }

    @And("Press the CLICK buttom")
    public void pressTheCLICKButtom() {
        homePage.pressClickButton();

    }

    @Then("I should be at the {string} team page")
    public void iShouldBeAtTheTeamPage(String teamName) throws InterruptedException {
        verifyTeamPage(teamName);
    }


    @And("I open second tab with {string} Team page")
    public void iOpenSecondTabWithTeamPage(String teamName) {
        //WebElement body =  driver.findElement(By.cssSelector("body"));
//        String osName = System.getProperty("os.name").toLowerCase();
//        boolean isMacOs = osName.startsWith("mac os x");
//        if (isMacOs)
//            Keys.chord(Keys.COMMAND, "t");
//        else
//            Keys.chord(Keys.CONTROL, "t");

        teamClicksStart = teamPage.getTeamClicks();
        teamClicksLeaderboardStart = teamPage.getTeamClicksLeaderboard();

        teamClicks1 = teamPage.getTeamClicks();
        teamClicksLeaderboard1 = teamPage.getTeamClicksLeaderboardString();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        teamPage.navigateToTeamPage(teamName);
        teamClicks2 = teamPage.getTeamClicks();
        teamClicksLeaderboard2 = teamPage.getTeamClicksLeaderboardString();
        assertTrue(teamClicks2 == teamClicks1);
        assertTrue(teamClicksLeaderboard2.equals(teamClicksLeaderboard1));
        System.out.println(teamClicksLeaderboard1);
        System.out.println(teamClicksLeaderboard2);


    }

    @When("I press the click button {string} times on the {int} tab")
    public void iPressTheClickButtonTimesOnTheFirstTab(String i, int tabNumber) {
        driver.switchTo().window(tabs.get(tabNumber - 1));
        System.out.println("before clicking on " + tabNumber + " tab: \n counter = " + teamPage.getTeamClicks() +
                " \n leaderboard = " + teamPage.getTeamClicksLeaderboard());

        teamPage.clickButton(i);

        System.out.print("\nAfter clicking on " + tabNumber + " tab:\n team clicks" + tabNumber + " = ");
        if (tabNumber == 1) {
            yourClicks1 = teamPage.getYourClicks();
            teamClicks1 = teamPage.getTeamClicks();
            System.out.println(yourClicks1 + "\t" + teamClicks1);
        } else if (tabNumber == 2) {
            yourClicks2 = teamPage.getYourClicks();
            teamClicks2 = teamPage.getTeamClicks();
            System.out.println(yourClicks2 + "\t" + teamClicks2);
        }
        waitLeaderboardSync();
    }

    @Then("team clicks counter shows incremented value by {string} in the {int} tab")
    public void teamClicksCounterShowsIncrementedValueByInTheTab(String i, int tabNumber) {
        driver.switchTo().window(tabs.get(tabNumber - 1));
        int teamClicksHaveToBe = teamClicksStart + Integer.parseInt(i);
        System.out.println(teamClicksHaveToBe);
        System.out.println(teamClicksStart);
        System.out.println(Integer.parseInt(i));

        assertTrue(teamClicksHaveToBe == teamPage.getTeamClicks());
        System.out.println("text");
        waitLeaderboardSync();
    }


    @And("team clicks in leaderboard incremented by {string} in the both tabs")
    public void teamClicksInLeaderboardIncrementedByInTheBothTabs(String i) {
        waitLeaderboardSync();
        //firs tab is on
        System.out.println("team clicks in leaderboard incremented by {string} in the both tabs");
        System.out.println("after clicks = " + Integer.parseInt(i));
        System.out.println(yourClicks1 + "\t" + teamClicks1 + "\t\t\t" + yourClicks2 + "\t" + teamClicks2);

        System.out.println(teamClicksLeaderboard1 + "\t\t\t\t" + teamClicksLeaderboard2);

        assertTrue(Math.abs(teamClicks1 - teamClicks2) == Integer.parseInt(i));
        System.out.println();
    }


    @Before
    public void startUp() throws Exception {
        driver = DriverBase.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./target/screenshots/" + scenario.getName() + " " + System.nanoTime() + ".png"));
            System.out.println("the Screenshot is taken");
        }

        if (driver != null) {
            driver.quit();
        }
    }


}
