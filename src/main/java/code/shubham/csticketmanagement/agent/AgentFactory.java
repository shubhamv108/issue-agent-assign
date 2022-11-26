package code.shubham.csticketmanagement.agent;

import code.shubham.csticketmanagement.issue.IssueType;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AgentFactory {

    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Agent get(String name, String email, List<IssueType> issueTypes) {
        return new Agent("A" + idGenerator.getAndIncrement(), name, email, issueTypes);
    }
}
