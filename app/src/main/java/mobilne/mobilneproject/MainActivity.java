package mobilne.mobilneproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCallback, DeleteItemCallback {

    private final int STATIC_RESPONSE = 55;
    private FrameLayout frameLayout;
    private String jsonString;
    private ArrayList<Item> itemsList;

    private void loadJSONFile() {
        InputStream is = getResources().openRawResource(R.raw.data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonString = writer.toString();
    }

    private void createItemsList() {
        itemsList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray attributesArray = jsonObject.getJSONArray("attributes");
                ArrayList<String> attributesList = new ArrayList<>();
                for (int j = 0; j < attributesArray.length(); j++) {
                    attributesList.add(attributesArray.getString(j));
                }

                JSONArray parametersArray = jsonObject.getJSONArray("parameters");
                ArrayList<String> parametersList = new ArrayList<>();
                for (int j = 0; j < parametersArray.length(); j++) {
                    parametersList.add(parametersArray.getString(j));
                }

                itemsList.add(new Item(
                        jsonObject.getInt("type"),
                        jsonObject.getString("name"),
                        jsonObject.getString("desc"),
                        attributesList,
                        parametersList,
                        Uri.parse("android.resource://mobilne.mobilneproject/drawable/" + jsonObject.getString("image"))
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.frameLayout = findViewById(R.id.fragment_container);
        loadJSONFile();
        createItemsList();
        addListFragment();
    }

    private void addListFragment() {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemsList", itemsList);
        fragment.setArguments(bundle);
        showFragment(fragment);
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.enter_right, R.animator.exit_left, R.animator.enter_left, R.animator.exit_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if(!(fragment instanceof ListFragment))
            fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void deleteItem(Item item) {
        SnackbarManager.show(
                Snackbar.with(getApplicationContext())
                        .text("Usunięto: " + item.getName()).duration(1000), this);
        itemsList.remove(item);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, NewItemActivity.class);
        startActivityForResult(i, STATIC_RESPONSE);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case STATIC_RESPONSE: {
                if (resultCode == Activity.RESULT_OK) {
                    itemsList.add((Item) data.getExtras().getSerializable("newItem"));
                }
                break;
            }
        }
    }
}
