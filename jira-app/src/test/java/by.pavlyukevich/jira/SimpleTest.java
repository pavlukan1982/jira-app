package by.pavlyukevich.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.common.collect.Iterables.transform;

public class SimpleTest {

    final static Logger logger = Logger.getLogger(SimpleTest.class);

    @Test
    public void test() throws IOException{
        logger.error("OK");

        URI jiraServerUri = URI.create("https://testjirainstance1.atlassian.net");

        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "admin", "testjira");

        try {
            final List<Promise<BasicIssue>> promises = Lists.newArrayList();
            final IssueRestClient issueClient = restClient.getIssueClient();

            System.out.println("Sending issue creation requests...");
            for (int i = 0; i < 1; i++) {
                final String summary = "NewIssue#" + i;
                final IssueInput newIssue = new IssueInputBuilder("TJP", 10002L, summary).build();
                System.out.println("\tCreating: " + summary);
                promises.add(issueClient.createIssue(newIssue));
            }

            System.out.println("Collecting responses...");
            final Iterable<BasicIssue> createdIssues = transform(promises, new Function<Promise<BasicIssue>, BasicIssue>() {
                @Override
                public BasicIssue apply(Promise<BasicIssue> promise) {
                    return promise.claim();
                }
            });

            System.out.println("Created issues:\n" + Joiner.on("\n").join(createdIssues));
        } finally {
            restClient.close();
        }
    }

    @Test
    public void testGetIssue() {
        URI jiraServerUri = URI.create("https://testjirainstance1.atlassian.net");

        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "admin", "testjira");

        final IssueRestClient issueClient = restClient.getIssueClient();
        Promise<Issue> issue = issueClient.getIssue("TJP-1");
        try {
            issue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
