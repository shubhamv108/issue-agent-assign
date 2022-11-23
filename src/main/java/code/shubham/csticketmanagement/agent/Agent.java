package code.shubham.csticketmanagement.agent;

import code.shubham.commons.AbstractEntity;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Agent extends AbstractEntity<String> {
    private String name;
    private final String email;
    private final Set<IssueType> issueTypes;

    private Issue assignedIssue;

    private Set<Issue> workedOn = new HashSet<>();

    public Agent(String name, String email, Collection<IssueType> issueTypes) {
        this.name = name;
        this.email = email;
        this.issueTypes = new HashSet<>(issueTypes);
    }

    public void setAvailableForAssiginignIssue() {
        this.assignedIssue = null;
    }

    public void setAssignedIssue(Issue assignedIssue) {
        this.assignedIssue = assignedIssue;
        this.workedOn.add(assignedIssue);
        assignedIssue.setAssignedTo(this);
    }

    public Set<Issue> getWorkedOn() {
        return workedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent agent)) return false;
        return id.equals(agent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }

    public Set<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public Issue getAssignedIssue() {
        return assignedIssue;
    }
}
