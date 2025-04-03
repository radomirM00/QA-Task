package com.qa_task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa_task.constants.enums;

public class CreateIssuePage extends BasePage {
    private final By modalLocator = By.tagName("add-issue-modal");
    private final By typeLocator = By.tagName("issue-type-select");
    private final By priorityLocator = By.tagName("issue-priority-select");
    private final By summaryLocator = By.cssSelector("*[formcontrolname=\"title\"]");
    private final By descriptionLocator = By.cssSelector(".ql-container.ql-snow .ql-editor");
    private final By reporterLocator = By.tagName("issue-reporter-select");
    private final By createBtnLocator = By.cssSelector("*[type=\"submit\"]");

    private WebElement issueModal;
    String summary;

    public CreateIssuePage(WebDriver driver, WebDriverWait driverWait) {
        super(driver, driverWait);
        this.issueModal = getDriver().findElement(modalLocator);
    }

    public void setSummary(String text) {
        this.issueModal.findElement(summaryLocator).sendKeys(text);
    }

    public void setType(enums.IssueType type) {
        this.issueModal.findElement(typeLocator).click();
        getDriverWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("nz-option-item"))).stream()
                .filter(typeOption -> typeOption.getText().equals(type.toString())).findFirst()
                .ifPresent(typeOption -> typeOption.click());
    }

    public void setPriority(enums.IssuePriority priority) {
        this.issueModal.findElement(priorityLocator).click();
        getDriverWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("nz-option-item"))).stream()
                .filter(priorityOption -> priorityOption.getText().equals(priority.toString())).findFirst()
                .ifPresent(priorityOption -> priorityOption.click());
    }

    public void setDescription(String text) {
        this.issueModal.findElement(descriptionLocator).sendKeys(text);
    }

    public void setReporter(String name) {
        this.issueModal.findElement(reporterLocator).click();
        getDriverWait().until(
                ExpectedConditions.attributeContains(By.cssSelector("*[nznoanimation]"), "class", "ant-select-open"));
        getDriverWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("nz-option-item"))).stream()
                .filter(reporter -> reporter.getText().strip().equals(name)).findFirst()
                .ifPresent(reporter -> reporter.click());
    }

    public void createIssue() {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(createBtnLocator)).click();
    }
}