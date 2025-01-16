package dk.easv.moviecollection.be;

public class CategoryMovie {
    private int id;
    private int movieId;
    private int categoryId;

    public CategoryMovie(int id, int movieId, int categoryId) {
        this.id = id;
        this.movieId = movieId;
        this.categoryId = categoryId;
    }
    public CategoryMovie(int movieId, int categoryId) {
        this.movieId = movieId;
        this.categoryId = categoryId;
    }
    public CategoryMovie() {
    }
    public int getId() {
        return id;
    }
    public int getMovieId() {
        return movieId;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
