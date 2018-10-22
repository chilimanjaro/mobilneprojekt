package mobilne.mobilneproject;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */


public class DetailsFragment extends Fragment {
    private Item item;
    private Button deleteButton;
    private ImageView imageView;
    private TextView itemTitle;
    private TextView itemDesc;
    private RecyclerView attributesList;
    private RecyclerView parametersList;
    private DeleteItemCallback diCallback;

    public DetailsFragment() { }

    public static DetailsFragment newInstance(Item item) {
        Bundle args = new Bundle();
        args.putSerializable("item", item);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.imageView = view.findViewById(R.id.item_image);
        this.itemTitle = view.findViewById(R.id.item_title);
        this.itemDesc = view.findViewById(R.id.item_description);
        this.attributesList = view.findViewById(R.id.item_attributes_list);
        this.parametersList = view.findViewById(R.id.item_parametres_list);
        this.deleteButton = view.findViewById(R.id.delete_button);

        item = (Item) getArguments().getSerializable("item");

        Bitmap imageBitmap = null;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), item.getImage());
            this.imageView.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.itemTitle.setText(item.getName());
        this.itemDesc.setText(item.getDesc());

        configurateLists();

        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diCallback.deleteItem(item);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        diCallback = (DeleteItemCallback) activity;
    }

    private void configurateLists() {
        this.attributesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.attributesList.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new DetailViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.detail_view_holder, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                ((DetailViewHolder) holder).configure(item.getAttributes().get(position));
            }

            @Override
            public int getItemCount() {
                return item.getAttributes().size();
            }
        });

        this.parametersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.parametersList.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new DetailViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.detail_view_holder, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                ((DetailViewHolder) holder).configure(item.getParameters().get(position));
            }

            @Override
            public int getItemCount() {
                return item.getParameters().size();
            }
        });
    }
}
