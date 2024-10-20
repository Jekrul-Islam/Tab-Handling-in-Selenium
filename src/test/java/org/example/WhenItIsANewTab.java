package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class WhenItIsANewTab {

    @Test
    public void newTab() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        WebDriver browser = new ChromeDriver(options);
        browser.get("https://bugbug.io/blog/testing-frameworks/best-selenium-practice-websites/");
        String parent = browser.getWindowHandle();
        System.out.println(parent);
        browser.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(9000));

        WebElement clickableLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'https://toolsqa.com/')]")));

        smoothScrollToElement(browser, clickableLink);
        clickableLink.click();


        Set<String> allWindows = browser.getWindowHandles();
        System.out.println(allWindows);
        for (String child : allWindows) {
            if (!parent.equalsIgnoreCase(child)) {
                browser.switchTo().window(child);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='new-training__read-more']"))).click();
                Thread.sleep(2000);
                browser.close();
            }
        }


        browser.switchTo().window(parent);
        Thread.sleep(1500);
        browser.quit();
    }


    public void smoothScrollToElement(WebDriver browser, WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) browser;
        int yScrollPosition = 0;
        int elementPosition = element.getLocation().getY() - 300;


        while (yScrollPosition < elementPosition) {
            yScrollPosition += 35;
            js.executeScript("window.scrollTo(0, arguments[0]);", yScrollPosition);
            Thread.sleep(50);
        }
    }
}
