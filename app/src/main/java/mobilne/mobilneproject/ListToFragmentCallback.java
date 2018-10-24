package mobilne.mobilneproject;

import java.util.ArrayList;

public interface ListToFragmentCallback {
    void sendData(ArrayList<Item> itemsList);
    void notifyAboutChange();
}
