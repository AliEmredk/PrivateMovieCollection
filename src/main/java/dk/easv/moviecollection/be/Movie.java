package dk.easv.moviecollection.be;

import java.util.Date;

public class Movie {
    private int id;
    private String title;
    private Date releaseDate;
    private String director;
    private String description;
    private int rating;

    public Movie(int id, String title, Date releaseDate, String director, String description, int rating) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.director = director;
        this.description = description;
        this.rating = rating;
    }
    public Movie(String title, Date releaseDate, String director, String description, int rating) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.director = director;
        this.description = description;
        this.rating = rating;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public String getDescription()
    {
        return description;
    }

    public int getRating()
    {
        return rating;
    }

    public String getDirector()
    {
        return director;
    }
}
