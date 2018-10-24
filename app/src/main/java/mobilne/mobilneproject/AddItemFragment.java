package mobilne.mobilneproject;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private static final int PICK_FROM_GALLERY = 66;

    private TableLayout tableLayout;
    private EditText itemName;
    private EditText itemDesc;
    private Spinner itemType;
    private ImageView itemImage;

    private AddNewItemCallback addItemCallback;
    private String message;

    private ArrayList<EditText> attEditTextList;
    private ArrayList<EditText> paramEditTextList;
    private Uri imageUri;

    public AddItemFragment() {
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
        itemName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        itemDesc = view.findViewById(R.id.new_item_desc);
        itemDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        itemType = view.findViewById(R.id.new_item_type_spinner);
        Button addButton = view.findViewById(R.id.add_button);
        Button addRowAtt = view.findViewById(R.id.add_row_att);
        Button addRowParam = view.findViewById(R.id.add_row_param);
        tableLayout = view.findViewById(R.id.new_item_table);
        itemImage = view.findViewById(R.id.new_item_image);

        attEditTextList = new ArrayList<>();
        paramEditTextList = new ArrayList<>();

        imageUri = null;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Znalezione błędy:\n";
                if (validateName() & validateDesc() & validateAtt() & validateParam() & validateImage()) {
                    ArrayList<String> newAttributes = new ArrayList<>();
                    ArrayList<String> newParameters = new ArrayList<>();

                    for (int i = 0; i < attEditTextList.size(); i++) {
                        newAttributes.add(attEditTextList.get(i).getText().toString());
                    }
                    for (int i = 0; i < paramEditTextList.size(); i++) {
                        newParameters.add(paramEditTextList.get(i).getText().toString());
                    }

                    Item itemToSend = new Item(
                            itemType.getSelectedItemPosition(),
                            itemName.getText().toString(),
                            itemDesc.getText().toString(),
                            newAttributes,
                            newParameters,
                            imageUri
                    );
                    addItemCallback.addItem(itemToSend);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Błąd walidacji");
                    builder.setMessage(message);
                    builder.setCancelable(true);
                    builder.show();
                }
            }

            private boolean validateName() {
                if (itemName.getText().toString().equals("")) {
                    message += "- Puste pole nazwy\n";
                    return false;
                } else
                    return true;
            }

            private boolean validateDesc() {
                if (itemDesc.getText().toString().equals("")) {
                    message += "- Puste pole opisu\n";
                    return false;
                } else
                    return true;
            }

            private boolean validateAtt() {
                if (attEditTextList.size() == 0) {
                    message += "- Brakuje pól dla właściwości\n";
                    return false;
                } else {
                    for (int i = 0; i < attEditTextList.size(); i++)
                        if (attEditTextList.get(i).getText().toString().equals("")) {
                            message += "- Pola właściwości nie mogą być puste\n";
                            return false;
                        }
                    return true;
                }
            }

            private boolean validateParam() {
                if (paramEditTextList.size() == 0) {
                    message += "- Brakuje pól dla parametrów\n";
                    return false;
                } else {
                    for (int i = 0; i < paramEditTextList.size(); i++)
                        if (paramEditTextList.get(i).getText().toString().equals("")) {
                            message += "- Pola parametrów nie mogą być puste\n";
                            return false;
                        }
                    return true;
                }
            }

            private boolean validateImage() {
                if (imageUri == null) {
                    message += "- Brakuje zdjęcia\n";
                    return false;
                } else
                    return true;
            }
        });

        addRowAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = new TableRow(getContext());
                tableLayout.addView(tr, tableLayout.indexOfChild(tableLayout.findViewById(R.id.new_att_row)));
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                params.span = 2;
                EditText et = new EditText(getContext());
                tr.addView(et);
                et.setLayoutParams(params);
                et.setHint("Wprowadź właściwość");
                et.setTextSize(14);
                et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });

                tr.setVisibility(View.VISIBLE);
                et.setVisibility(View.VISIBLE);
                attEditTextList.add(et);
            }
        });

        addRowParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr = new TableRow(getContext());
                tableLayout.addView(tr, tableLayout.indexOfChild(tableLayout.findViewById(R.id.new_param_row)));
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                params.span = 2;
                EditText et = new EditText(getContext());
                tr.addView(et);
                et.setLayoutParams(params);
                et.setHint("Wprowadź parametr");
                et.setTextSize(14);
                et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });

                tr.setVisibility(View.VISIBLE);
                et.setVisibility(View.VISIBLE);
                paramEditTextList.add(et);
            }
        });

        itemImage.setClickable(true);
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addItemCallback = (AddNewItemCallback) activity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            itemImage.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TableRow message = tableLayout.findViewById(R.id.image_permission_row);
                    message.setVisibility(View.GONE);

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    TableRow message = tableLayout.findViewById(R.id.image_permission_row);
                    message.setVisibility(View.VISIBLE);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
