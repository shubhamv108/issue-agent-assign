package code.shubham.csticketmanagement.issue;

import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.exceptions.RequestException;
import code.shubham.csticketmanagement.response.IssueResolveResponse;
import code.shubham.csticketmanagement.strategies.filter.FilterStrategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IssueService implements IIssueService {

    private Integer idGenerator = 1;
    private final Map<String, Issue> issues;
    private final Set<Issue> resolvedIssues = new HashSet<>();

    private final FilterStrategy<Issue> filterStrategy;

    public IssueService(Map<String, Issue> issues, FilterStrategy<Issue> filterStrategy) {
        this.issues = issues;
        this.filterStrategy = filterStrategy;
    }

    public Issue create(Issue issue) {
        Issue existing = this.issues.get(issue);
        if (existing != null)
            throw new RequestException("Already exists");
        this.issues.put(issue.getId(), issue);
        this.filterStrategy.add(issue);
        return issue;
    }

    @Override
    public Issue getById(String id) {
        return this.issues.get(id);
    }

    private Issue getByIdOrThrow(String id) {
        Issue issue =  this.getById(id);
        if (issue == null)
            throw new RequestException("Issue Not Found");
        return issue;
    }

    @Override
    public Issue update(String id, String status, String resolution) {
        Issue issue =  this.getByIdOrThrow(id);
        issue.setStatus(status);
        issue.setResolution(resolution);
        return issue;
    }

    @Override
    public IssueResolveResponse resolve(String id, String resolution) {
        Issue issue = this.getByIdOrThrow(id);
        Agent agent = issue.resolve(resolution);
        this.resolvedIssues.add(issue);
        return new IssueResolveResponse(issue.getIssueType(), agent);
    }

    @Override
    public Collection<Issue> get(Map<String, Object> filter) {
        return this.filterStrategy.filter(filter);
    }
}
