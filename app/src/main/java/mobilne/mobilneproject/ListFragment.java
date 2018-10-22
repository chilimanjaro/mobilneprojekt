package mobilne.mobilneproject;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityCallback activityCallback;
    private ArrayList<Item> itemsList;
    private FloatingActionButton addButton;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCallback = (ActivityCallback) getActivity();
        itemsList = (ArrayList<Item>) getArguments().getSerializable("itemsList");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        activityCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recyclerView = view.findViewById(R.id.recycler_view);
        configRecyclerView();
    }

    private void configRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ItemViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.view_holder, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                View.OnClickListener ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailsFragment(position);
                    }
                };
                String name = itemsList.get(position).getName();
                String desc = itemsList.get(position).getDesc();
                Uri img = itemsList.get(position).getImage();
                try {
                    ((ItemViewHolder) holder).configure(getActivity(), ocl, name, desc, img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getItemCount() {
                return itemsList.size();
            }

            private void startDetailsFragment(int position) {
                activityCallback.showFragment(DetailsFragment.newInstance(itemsList.get(position)));
            }
        });
    }
}
