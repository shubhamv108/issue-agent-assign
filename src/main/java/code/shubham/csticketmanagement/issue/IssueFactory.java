package code.shubham.csticketmanagement.issue;

public class IssueFactory {
    private Integer idGenerator = 1;

    public Issue get(String transactionId, IssueType issueType, String subject, String description, String email) {
        return new Issue("I"+(idGenerator++), transactionId, issueType, subject, description, email);
    }
}
