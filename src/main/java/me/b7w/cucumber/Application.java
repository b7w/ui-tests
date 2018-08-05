package me.b7w.cucumber;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.FileSystemResultsWriter;
import io.qameta.allure.junit4.AllureJunit4;
import me.b7w.cucumber.test.CucumberTest;
import me.b7w.cucumber.test.SpringKotlinTest;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        AllureJunit4 listener = new AllureJunit4(new AllureLifecycle(new FileSystemResultsWriter(Paths.get("target/allure-results"))));

        JUnitCore junit = new JUnitCore();
        junit.addListener(listener);
        junit.addListener(new TextListener(System.out));
        junit.run(CucumberTest.class, SpringKotlinTest.class);

        AllureReportGenerator report = new AllureReportGenerator("target/allure-results");
        report.writeTo("target/allure-report");
    }

}
