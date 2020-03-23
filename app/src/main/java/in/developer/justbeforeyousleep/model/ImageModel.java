package in.developer.justbeforeyousleep.model;

public class ImageModel {

    private String id;
    private String name;
    private String likes;
    private String image_path;
    private int image_paths;

    public int getImage_paths() {
        return image_paths;
    }

    public void setImage_paths(int image_paths) {
        this.image_paths = image_paths;
    }

    public ImageModel(String id, String name, String likes, int image_paths) {
        this.id = id;
        this.name = name;
        this.likes = likes;
        this.image_paths = image_paths;
    }

    public ImageModel(){}

    public ImageModel(String id, String name, String likes, String image_path) {
        this.id = id;
        this.name = name;
        this.likes = likes;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
