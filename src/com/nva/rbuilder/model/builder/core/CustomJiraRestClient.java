package com.nva.rbuilder.model.builder.core;//package core;
//
//import com.atlassian.jira.rest.client.api.JiraRestClient;
//import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
//import com.atlassian.jira.rest.client.api.domain.Issue;
//import com.atlassian.jira.rest.client.api.domain.User;
//import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
//import com.atlassian.util.concurrent.Promise;
//
//import java.net.URI;
//
//public class CustomJiraRestClient {
//    public static void jiraAuthentication() throws Exception{
//        Assets.print("Jira Authentication...");
//        try {
//            // Build JRJC Client
//            final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//            final URI uri = new URI(Assets.getProjectURL());
//            final JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, Assets.getLogin(), Assets.getPassword());
//            Assets.println("Done!");
//
//            // Invoke JRJC Client
//            Promise<User> promise = client.getUserClient().getUser(Assets.getLogin());
//            User user = promise.claim();
//
//            Promise<Issue> issuePromise = client.getIssueClient().getIssue("LBGMBML-2");
//            Issue issue = issuePromise.claim();
//
//            Assets.println("some TEST-1 details " + issue.getAssignee() + "   " + issue.getSummary());
//        }catch (Exception ex){
//            Assets.println("Failed!");
//        }
//
//    }
//}