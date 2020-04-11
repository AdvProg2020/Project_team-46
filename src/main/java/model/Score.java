package model;

public class Score {
    private Account user;
    private double score;
    private Product product;

    public Score(Account user, double score, Product product) {
        this.user = user;
        this.score = score;
        this.product = product;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
