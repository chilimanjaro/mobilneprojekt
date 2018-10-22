package mobilne.mobilneproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

public class NewItemActivity extends AppCompatActivity implements AddNewItemCallback {

    private FrameLayout addContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        this.addContainer = findViewById(R.id.add_fragment_container);

        AddItemFragment addItemFragment = new AddItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_fragment_container, addItemFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void addItem(Item item) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newItem", item);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
