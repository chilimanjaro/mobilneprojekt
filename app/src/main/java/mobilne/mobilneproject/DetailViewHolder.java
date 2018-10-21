package mobilne.mobilneproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DetailViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.detail_text_view);
    }

    public void configure(String text) {
        this.textView.setText(text);
    }
}
