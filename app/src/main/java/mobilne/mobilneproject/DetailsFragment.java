package mobilne.mobilneproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */

//TODO - pobawdzić bundle, żeby wysyłać klasę Item do DetailsFragment


public class DetailsFragment extends Fragment {
    private ImageView imageView;
    private TextView itemTitle;
    private TextView itemDesc;
    private RecyclerView attributesList;
    private RecyclerView parametersList;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Item item) {
        Bundle args = new Bundle();
        args.putSerializable("item", item);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.imageView = view.findViewById(R.id.item_image);
        this.itemTitle = view.findViewById(R.id.item_title);
        this.itemDesc = view.findViewById(R.id.item_description);

        Item item = (Item) getArguments().getSerializable("item");

        int resourceImage = view.getResources().getIdentifier(item.getImage(), "drawable", getActivity().getPackageName());
        this.imageView.setImageResource(resourceImage);

        this.itemTitle.setText(item.getName());
        this.itemDesc.setText(item.getDesc());

        this.attributesList = view.findViewById(R.id.item_attributes_list);
        this.parametersList = view.findViewById(R.id.item_parametres_list);

        configurateLists();
    }

    private void configurateLists() {
        final Item item = (Item) getArguments().getSerializable("item");

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
