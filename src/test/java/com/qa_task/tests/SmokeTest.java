package com.qa_task.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa_task.constants.enums;
import com.qa_task.pages.CreateIssuePage;

public class SmokeTest extends BaseTest {
    @Test(description = "Verifying the board title.", priority = 1)
    public void verifyBoardHeader() {
        String expected = "Kanban board";

        Assert.assertEquals(homePage.getBoardHeader(), expected);
    }

    @Test(description = "Verifying that the resolved issues are not displayed when the filter is applied.", priority = 2)
    public void verifyIgnoreResolvedIssuesFilter() {
        homePage.ignoreResolvedIssues();

        Assert.assertTrue(homePage.isDoneListEmpty());
    }

    @Test(description = "Verifying that filter resets after the \"Clear all\" button is clicked.", priority = 3)
    public void verifyFilterClear() {
        homePage.filterByMyIssues();
        homePage.ignoreResolvedIssues();

        homePage.clearFilters();

        Assert.assertFalse(homePage.isMyIssuesFilterActive());
        Assert.assertFalse(homePage.isIgnoreResolvedFilterActive());
    }

    @Test(description = "Verifying filtering by searched criteria.", priority = 4)
    public void verifyFilterBySearch() {
        String searchInput = "Angular";

        homePage.filterBySearch(searchInput);

        Assert.assertTrue(homePage.checkSearchResults(searchInput));
    }

    @Test(description = "Verifying integrity of the side menu search results.", priority = 5)
    public void verifySearchResults() {
        String searchCriteria = "P/s: This is Totoro, my most favorite character";

        homePage.enterSearchCriteria(searchCriteria);
        homePage.openResult();
        String fullDescription = homePage.getIssueDescription();
        homePage.closeIssueModal();

        Assert.assertTrue(fullDescription.contains(searchCriteria));
    }

    @Test(description = "Verifying that the issue is sucessfully created.", priority = 6)
    public void verifyTicketCreation() {
        String summary = "Ajvar";

        homePage.openCreateIssueModal();
        CreateIssuePage createIssuePage = new CreateIssuePage(homePage.getDriver(), homePage.getDriverWait());
        createIssuePage.setType(enums.IssueType.BUG);
        createIssuePage.setSummary(summary);
        createIssuePage.setPriority(enums.IssuePriority.HIGHEST);
        createIssuePage.setDescription("BABOON BABOON");
        createIssuePage.setReporter("Trung Vo");
        createIssuePage.createIssue();
        homePage.filterBySearch(summary);
        Boolean validateResults = homePage.checkSearchResults(summary);

        Assert.assertTrue(validateResults);
    }

    @Test(description = "Verifying that issue is readable.", priority = 7)
    public void verifyIssueReadability() {
        homePage.openIssue("Ajvar");

        Assert.assertTrue(homePage.getIssueSummary().contains("Ajvar"));
        Assert.assertTrue(homePage.getIssueDescription().contains("BABOON BABOON"));
        Assert.assertTrue(homePage.getIssueReporter().contains("Trung Vo"));
        Assert.assertTrue(homePage.getIssueType().contains(enums.IssueType.BUG.toString()));
        Assert.assertTrue(homePage.getIssuePriority().toUpperCase().contains(enums.IssuePriority.HIGHEST.toString()));
    }

    @Test(description = "Verifying that the issue status is editable.", priority = 8)
    public void verifyIsssueStatusIsEdatable() {
        homePage.setIssueStatus(enums.IssueStatus.SELECTED_FOR_DEVELOPMENT);

        Assert.assertTrue(
                homePage.getIssueStatus().strip().equals(enums.IssueStatus.SELECTED_FOR_DEVELOPMENT.toString()));
    }

    @Test(description = "Verifying that the issue can be assigned.", priority = 9)
    public void verifyIsssueAssigneeIsEdatable() {
        String assignee = "Spider Man";

        homePage.assignIssue(assignee);

        Assert.assertTrue(homePage.getIssueAssignees().contains(assignee));
    }

    @Test(description = "Verifying issue deletion.", priority = 10)
    public void verifyIssueIsDeleted() {
        homePage.deleteIssue();

        homePage.filterBySearch("Ajvar");

        Assert.assertFalse(homePage.checkSearchResults("Ajvar"));
    }

}
