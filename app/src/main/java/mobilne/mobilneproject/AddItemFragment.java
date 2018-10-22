package mobilne.mobilneproject;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private EditText itemName;
    private EditText itemDesc;
    private Spinner itemType;
    private Button addButton;
    private AddNewItemCallback addItemCallback;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemName = view.findViewById(R.id.new_item_name);
        itemDesc = view.findViewById(R.id.new_item_desc);
        itemType = view.findViewById(R.id.new_item_type_spinner);
        addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itemToSend = new Item(
                        0,
                        itemName.getText().toString(),
                        itemDesc.getText().toString(),
                null,
                null,
                null);
                addItemCallback.addItem(itemToSend);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addItemCallback = (AddNewItemCallback) activity;
    }
}
