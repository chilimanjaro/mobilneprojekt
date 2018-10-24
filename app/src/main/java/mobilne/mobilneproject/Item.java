package mobilne.mobilneproject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable {
    private int type;
    private String name;
    private String desc;
    private ArrayList<String> attributes;
    private ArrayList<String> parameters;
    private Uri image;

    public Item(int type, String name, String desc, ArrayList<String> attributes, ArrayList<String> parameters, Uri image) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.attributes = attributes;
        this.parameters = parameters;
        this.image = image;
    }

    protected Item(Parcel in) {
        type = in.readInt();
        name = in.readString();
        desc = in.readString();
        attributes = in.createStringArrayList();
        parameters = in.createStringArrayList();
        image = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeStringList(attributes);
        dest.writeStringList(parameters);
        dest.writeParcelable(image, flags);
    }
}
