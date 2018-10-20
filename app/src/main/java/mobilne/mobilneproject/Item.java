package mobilne.mobilneproject;

import java.util.ArrayList;

public class Item {
    private int type;
    private String name;
    private String desc;
    private ArrayList<String> attributes;
    private ArrayList<String> parametres;

    public Item(int type, String name, String desc, ArrayList<String> attributes, ArrayList<String> parametres) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.attributes = attributes;
        this.parametres = parametres;
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

    public ArrayList<String> getParametres() {
        return parametres;
    }

    public void setParametres(ArrayList<String> parametres) {
        this.parametres = parametres;
    }
}
