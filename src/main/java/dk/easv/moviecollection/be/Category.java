package dk.easv.moviecollection.be;

public class Category {
    private int id;
    private String name;
    private String path;

    public Category() {
        this.id = 0;
        this.name = null;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public void setPath(String path){
        this.path = path;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
