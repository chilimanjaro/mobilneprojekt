package mobilne.mobilneproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

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

    public void configure(Activity activity, View.OnClickListener clickListener, String name, String desc, Uri image) throws IOException {
        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), image);
        this.imageView.setImageBitmap(imageBitmap);
        this.itemTitle.setText(name);
        this.itemDesc.setText(desc);
        this.itemView.setOnClickListener(clickListener);
    }
}
