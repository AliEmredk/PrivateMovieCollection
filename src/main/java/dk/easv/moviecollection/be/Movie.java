package dk.easv.moviecollection.be;

import java.util.Date;

public class Movie {
    private String title;
    private Date releaseDate;
    private String director;
    private String description;
    private int rating;

    public Movie(String title, Date releaseDate, String director, String description, int rating) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.director = director;
        this.description = description;
        this.rating = rating;
    }
}
