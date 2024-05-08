package application;

public class Category {
	private int cat_id;
	private String categores_name;
	private String Description;

	public Category() {
		super();
	}

	static Category cat;

	public Category(int cat_id, String categores_name, String Description) {
		super();
		this.cat_id = cat_id;
		this.categores_name = categores_name;
		this.Description = Description;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getCategores_name() {
		return categores_name;
	}

	public void setCategores_name(String categores_name) {
		this.categores_name = categores_name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}


}
