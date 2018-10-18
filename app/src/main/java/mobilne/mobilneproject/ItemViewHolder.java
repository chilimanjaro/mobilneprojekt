package mobilne.mobilneproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.view_holder_text_view);
    }

    public void configure(int position, View.OnClickListener clickListener) {
        this.textView.setText("Element na pozycji" + position);

        this.itemView.setOnClickListener(clickListener);
    }
}
