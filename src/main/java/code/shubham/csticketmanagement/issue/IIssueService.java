package code.shubham.csticketmanagement.issue;

import code.shubham.csticketmanagement.response.IssueResolveResponse;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IIssueService {

    Issue create(Issue issue);

    Issue update(String issueId, String status, String resolution);
    IssueResolveResponse resolve(String issueId, String resolution);

    Collection<Issue> get(Map<String, Object> filter);

    Issue getById(String id);
}
