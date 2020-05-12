package model;

public class Comment {
    private Account user;
    private Product product;
    private String title;
    private String body;
    private boolean hasUserBought;
    private CommentStatus commentStatus;

    public Comment(Account user, Product product, String title, String body,
                   boolean hasUserBought, CommentStatus commentStatus) {
        this.user = user;
        this.product = product;
        this.title = title;
        this.body = body;
        this.hasUserBought = hasUserBought;
        this.commentStatus = commentStatus;
    }

    public Account getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isHasUserBought() {
        return hasUserBought;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }
}
