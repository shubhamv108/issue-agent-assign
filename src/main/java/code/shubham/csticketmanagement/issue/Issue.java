package code.shubham.csticketmanagement.issue;

import code.shubham.csticketmanagement.agent.Agent;

import java.util.HashSet;
import java.util.Set;

public class Issue {

    private String id;
    private final String transactionId;
    private IssueType issueType;
    private String subject;
    private String description;
    private final String email;

    private String status;

    private boolean isResolved;
    private String resolution;

    private Agent assignedTo;

    private Set<Agent> workedBy = new HashSet<>();

    public Issue(String transactionId, IssueType issueType, String subject, String description, String email) {
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.subject = subject;
        this.description = description;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean resolve(String resolution) {
        this.resolution = resolution;
        this.assignedTo = null;
        return this.isResolved = true;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setAssignedTo(Agent assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Agent getAssignedTo() {
        return assignedTo;
    }

    public boolean isResolved() {
        return isResolved;
    }

    @Override
    public String toString() {
        return id;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}
