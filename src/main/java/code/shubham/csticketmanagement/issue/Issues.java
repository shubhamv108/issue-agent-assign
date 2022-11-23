package code.shubham.csticketmanagement.issue;

import code.shubham.csticketmanagement.exceptions.RequestException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Issues {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<String, Issue> issues = new HashMap<>();
    private final Set<Issue> resolvedIssues = new HashSet<>();

    public Issue add(Issue issue) {
        issue.setId("I" + idGenerator.getAndIncrement());
        this.issues.put(issue.getId(), issue);
        return issue;
    }

    public Issue resolve(String issueId, String resolution) {
        Issue issue = this.getById(issueId);
        if (issue == null)
            throw new RequestException("Issue Not Found");
        issue.resolve(resolution);
        this.resolvedIssues.add(issue);
        return issue;
    }

    public Issue getById(String id) {
        return this.issues.get(id);
    }
}
