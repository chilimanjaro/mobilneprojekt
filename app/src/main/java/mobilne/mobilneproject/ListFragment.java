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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityCallback activityCallback;
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
                        jsonObject.getString("image")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCallback = (ActivityCallback) getActivity();
        loadJSONFile();
        createItemsList();
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
                ((ItemViewHolder) holder).configure(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailsFragment(position);
                    }
                }, itemsList.get(position).getName(), itemsList.get(position).getDesc(), holder.itemView.getResources().getIdentifier(itemsList.get(position).getImage(), "drawable", getActivity().getPackageName()));
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
