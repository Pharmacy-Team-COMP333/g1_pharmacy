package application;

public class Category {
    private int cat_id;
    private String categories_name;
    private String Description;

    public Category() {
        super();
    }

    static Category cat;

    public Category(int cat_id, String categories_name, String Description) {
        super();
        this.cat_id = cat_id;
        this.categories_name = categories_name;
        this.Description = Description;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategories_name() {
        return categories_name;
    }

    public void setCategories_name(String categories_name) {
        this.categories_name = categories_name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}