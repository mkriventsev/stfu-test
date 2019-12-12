package com.stfu.ui;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        tags = "@SmokeTest or @RegressionTest or @FunctionalTest",
        plugin = {"pretty",
                "html:target/cucumber-html-report",
                "json: cucumber-html-reports/cucumber.json",
                "de.monochromata.cucumber.report.PrettyReports"},
        features = "src/test/java/com/stfu/ui/features",
        glue = {"com.stfu.ui.steps",},
        dryRun = false)
public class RunTests {
}

