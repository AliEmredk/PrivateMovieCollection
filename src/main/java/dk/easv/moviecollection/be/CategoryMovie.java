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
}
