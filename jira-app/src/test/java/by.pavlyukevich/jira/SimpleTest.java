package by.pavlyukevich.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.common.collect.Iterables.transform;

public class SimpleTest {

    final private static Logger logger = Logger.getLogger(SimpleTest.class);
    final public static JiraRestClient restClient;
    static {
        URI jiraServerUri = URI.create("https://testjirainstance1.atlassian.net");
        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "admin", "testjira");
    }

    @Test
    public void testAddIssue() throws IOException{
        logger.debug("Start creating issue");

        IssueRestClient issueClient = restClient.getIssueClient();
        IssueInput issueInput = new IssueInputBuilder("TJP", 10002L, "New issue 1").build();
        Promise<BasicIssue> issue = issueClient.createIssue(issueInput);
        issue.claim();
    }

    @Test
    public void testGetIssue() {
        final IssueRestClient issueClient = restClient.getIssueClient();
        Promise<Issue> issue = issueClient.getIssue("TJP-1");
        System.out.println(issue.claim());
    }

    @Test
    public void testGetAllIssueTypes() {
        MetadataRestClient metadataClient = restClient.getMetadataClient();
        Promise<Iterable<IssueType>> issueTypes = metadataClient.getIssueTypes();
        Iterator<IssueType> iterator = issueTypes.claim().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @After
    public void closeRestClient() throws IOException{
        restClient.close();
    }

}
