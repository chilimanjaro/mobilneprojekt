package mobilne.mobilneproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView itemTitle;
    private TextView itemDesc;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.item_image_list);
        this.itemTitle = itemView.findViewById(R.id.view_holder_item_title);
        this.itemDesc = itemView.findViewById(R.id.view_holder_item_desc);
    }

    public void configure(View.OnClickListener clickListener, String name, String desc, int resourceImage) {
        this.imageView.setImageResource(resourceImage);
        this.itemTitle.setText(name);
        this.itemDesc.setText(desc);
        this.itemView.setOnClickListener(clickListener);
    }
}
