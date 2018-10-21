package mobilne.mobilneproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    private int type;
    private String name;
    private String desc;
    private ArrayList<String> attributes;
    private ArrayList<String> parameters;
    private String image;

    public Item(int type, String name, String desc, ArrayList<String> attributes, ArrayList<String> parameters, String image) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.attributes = attributes;
        this.parameters = parameters;
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
