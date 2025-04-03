package com.qa_task.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa_task.constants.enums;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver, WebDriverWait driverWait) {
        super(driver, driverWait);
    }

    private final By boardTitle = By.className("text-2xl");
    private final By searchIconNavbar = By.className("anticon-search");
    private final By searchInputNavbar = By
            .xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div/div/search-drawer/div[1]/j-input/div/input");
    private final By navbarSearchDrawer = By.className("ant-drawer-body");
    private final By createTaskBtn = By.className("anticon-plus");
    private final By myIssuesFilterBtn = By.xpath("//*[@id=\"content\"]/board/div/board-filter/div/j-button[1]/button");
    private final By ignoreResolvedFilterBtn = By
            .xpath("//*[@id=\"content\"]/board/div/board-filter/div/j-button[2]/button");
    private final By clearFiltersBtn = By
            .xpath("//*[@id=\"content\"]/board/div/board-filter/div/div[2]/j-button/button");
    private final By searchFilterInput = By
            .xpath("//*[@id=\"content\"]/board/div/board-filter/div/form/j-input/div/input");
    private final By doneList = By.id("Done");
    private final By modal = By.tagName("issue-modal");
    private final By issueTypeLocator = By.tagName("issue-type");
    private final By issueTitleLocator = By.tagName("issue-title");
    private final By issueDescriptionLocator = By.tagName("issue-description");
    private final By issuePriorityLocator = By.className("priority-label");
    private final By issueReporterLocator = By.tagName("issue-reporter");
    private final By issueAssigneesLocator = By.tagName("issue-assignees");
    private final By closeIssueModalBtn = By.cssSelector("*[icon=\"times\"]");
    private final By issueStatusLocator = By.tagName("issue-status");
    private final By issueStatusListLocator = By.className("ant-dropdown-menu-root");
    private final By issueAddAssigneeListLocator = By.className("ant-dropdown-menu-root");
    private final By deleteIssueButtonLocator = By.cssSelector("*[icon=\"trash\"]");
    private final By deleteIssueModalLocator = By.tagName("issue-delete-modal");

    public void openCreateIssueModal() {
        getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(createTaskBtn)).click();
    }

    public void closeIssueModal() {
        getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(closeIssueModalBtn)).click();
    }

    private Boolean isFilterApplied(By locator) {
        WebElement button = getDriver().findElement(locator);
        getDriverWait().until(ExpectedConditions.elementToBeClickable(button));
        String classValue = button.getDomAttribute("class");
        return classValue.contains("is-active");
    }

    public Boolean isMyIssuesFilterActive() {
        return isFilterApplied(myIssuesFilterBtn);
    }

    public Boolean isIgnoreResolvedFilterActive() {
        return isFilterApplied(ignoreResolvedFilterBtn);
    }

    public String getBoardHeader() {
        return getDriver().findElement(boardTitle).getText();
    }

    public void filterByMyIssues() {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(myIssuesFilterBtn)).click();
    }

    public void ignoreResolvedIssues() {
        getDriverWait().until(ExpectedConditions.elementToBeClickable(ignoreResolvedFilterBtn)).click();
    }

    public void clearFilters() {
        getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(clearFiltersBtn)).click();
    }

    private List<WebElement> getTasksFromCategory(By locator) {
        WebElement taskList = getDriver().findElement(doneList);
        List<WebElement> tasks = taskList.findElements(By.xpath("./*"));
        return tasks;
    }

    private List<WebElement> getAllIssues() {
        try {
            Thread.sleep(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("status-list")))
                .stream()
                .flatMap(issueList -> issueList.findElements(By.className("issue")).stream())
                .collect(Collectors.toList());
    }

    public Boolean isDoneListEmpty() {
        List<WebElement> tasks = getTasksFromCategory(doneList);
        return tasks.isEmpty();
    }

    public void filterBySearch(String search) {
        WebElement searchInput = getDriver().findElement(searchFilterInput);
        searchInput.clear();
        searchInput.sendKeys(search);
    }

    public Boolean checkSearchResults(String searchInput) {
        return getAllIssues().stream().anyMatch(task -> task.getText().contains(searchInput));
    }

    private void selectSearchFromNavbar() {
        WebElement searchIcon = getDriver().findElement(searchIconNavbar);
        searchIcon.click();
    }

    public void enterSearchCriteria(String searchCriteria) {
        selectSearchFromNavbar();
        WebElement searchInput = getDriverWait().until(ExpectedConditions.presenceOfElementLocated(searchInputNavbar));
        searchInput.sendKeys(searchCriteria);
    }

    private List<WebElement> getNavbarSearchResults() {
        WebElement searchResultsContainer = getDriver().findElement(navbarSearchDrawer);
        try {
            Thread.sleep(Duration.ofSeconds(1)); // Since the data is mocked, there is no http request to be intercepted
                                                 // and fixed time duration is needed to ensure that the elements are
                                                 // "loaded"
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> searchResults = searchResultsContainer.findElements(By.tagName("issue-result"));
        return searchResults;
    }

    public void openResult() {
        List<WebElement> results = getNavbarSearchResults();
        results.get(0).click();
    }

    public void openIssue(String summary) {
        getAllIssues().stream().filter(issue -> issue.getText().contains(summary)).findFirst()
                .ifPresent(issue -> issue.click());
    }

    public String getIssueType() {
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, issueTypeLocator))
                .getText();
    }

    public String getIssueDescription() {
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, issueDescriptionLocator))
                .getText();
    }

    public String getIssueReporter() {
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, issueReporterLocator))
                .getText();
    }

    public String getIssueSummary() {
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, issueTitleLocator))
                .findElement(By.tagName("textarea"))
                .getDomProperty("value");
    }

    public String getIssuePriority() {
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, issuePriorityLocator))
                .getText();
    }

    public List<String> getIssueAssignees() {
        return getDriverWait().until(
                ExpectedConditions.presenceOfNestedElementsLocatedBy(issueAssigneesLocator, By.tagName("button")))
                .stream().map(option -> option.getText()).toList();
    }

    public void setIssueStatus(enums.IssueStatus status) {
        getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(issueStatusLocator))
                .findElement(By.tagName("button")).click();
        getDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(issueStatusListLocator))
                .findElements(By.tagName("li")).stream()
                .filter(listOption -> listOption.getText().equals(status.toString())).findFirst()
                .ifPresent(listOption -> listOption.click());
    }

    public void assignIssue(String assignTo) {
        getDriver().findElement(issueAssigneesLocator).findElement(By.tagName("a")).click();
        getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(issueAddAssigneeListLocator))
                .findElements(By.tagName("li")).stream().filter(assignee -> assignee.getText().strip().equals(assignTo))
                .findFirst().ifPresent(assignee -> assignee.click());
    }

    public String getIssueStatus() {
        getDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(issueStatusListLocator));
        return getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(issueStatusLocator, By.tagName("button")))
                .getText();

    }

    public void deleteIssue() {
        getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(modal, deleteIssueButtonLocator))
                .click();
        getDriverWait()
                .until(ExpectedConditions.presenceOfNestedElementsLocatedBy(deleteIssueModalLocator,
                        By.tagName("button")))
                .stream().filter(button -> button.getText().strip().equals("Delete")).findFirst()
                .ifPresent(button -> button.click());

    }
}
