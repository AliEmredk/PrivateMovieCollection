package dk.easv.moviecollection.be;


public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private String director;
    private String description;
    private String moviePath;
    private int rating;

    public Movie(){
        this.id = 0;
        this.title = null;
        this.releaseDate = null;
        this.director = null;
        this.description = null;
        this.rating = 0;
    }

    public Movie(int id, String title, String releaseDate, String director, String description, int rating) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.director = director;
        this.description = description;
        this.rating = rating;
    }
    public Movie(String title, String releaseDate, String director, String description, int rating) {
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

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public String getDescription()
    {
        return description;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public int getRating()
    {
        return rating;
    }

    public String getDirector()
    {
        return director;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public void setMoviePath(String moviePath){this.moviePath = moviePath;}

}
