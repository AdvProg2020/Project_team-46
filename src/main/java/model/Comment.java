package model;

public class Comment {
    private Account user;
    private Product product;
    private String body;
    private boolean hasUserBought;
    private CommentStatus commentStatus;

    public Comment(Account user, Product product, String body, boolean hasUserBought, CommentStatus commentStatus) {
        this.user = user;
        this.product = product;
        this.body = body;
        this.hasUserBought = hasUserBought;
        this.commentStatus = commentStatus;
    }
}
