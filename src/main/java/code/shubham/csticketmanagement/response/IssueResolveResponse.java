package code.shubham.csticketmanagement.response;

import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.issue.IssueType;

public class IssueResolveResponse {
    private final IssueType issueType;
    private final Agent agent;

    public IssueResolveResponse(IssueType issueType, Agent agent) {
        this.issueType = issueType;
        this.agent = agent;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public Agent getAgent() {
        return agent;
    }
}
