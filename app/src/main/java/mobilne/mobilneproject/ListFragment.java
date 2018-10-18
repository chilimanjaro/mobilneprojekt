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


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityCallback activityCallback;


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCallback = (ActivityCallback) getActivity();
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
            //Tworzenie widoku
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ItemViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.view_holder, parent, false));
            }

            //Bind, gdy juz jest stworzony
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                ((ItemViewHolder) holder).configure(position, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailsFragment(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 5;
            }

            private void startDetailsFragment(int position) {
                activityCallback.showFragment(DetailsFragment.newInstance(position));
            }
        });
    }
}
