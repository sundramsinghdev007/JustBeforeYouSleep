package in.developer.justbeforeyousleep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import in.developer.justbeforeyousleep.model.ImageModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<ImageModel> mList;
    private Context mContext;
    private View view;
    Bitmap b;

    public HomeAdapter(ArrayList<ImageModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ImageModel data = mList.get(position);
        holder.id_tv.setText(data.getId());
        holder.name_tv.setText(data.getName());
        holder.like_tv.setText(data.getLikes());
        Glide.with(mContext).load(data.getImage_paths()).into(holder.image);
//        holder.image.buildDrawingCache();
//        Bitmap image = holder.image.getDrawingCache();
//        final Bundle extras = new Bundle();
//        extras.putParcelable("image",image);
//
//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,FullScreenActivity.class);
//                intent.putExtras(extras);
//                mContext.getApplicationContext().startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id_tv, name_tv, like_tv;
        private View itemView;
        private ImageView image;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_tv = itemView.findViewById(R.id.image_id);
            name_tv = itemView.findViewById(R.id.uploaded_by);
            like_tv = itemView.findViewById(R.id.likes);
            image = itemView.findViewById(R.id.post_img);
            cardView = itemView.findViewById(R.id.image_cv);
            image.setOnClickListener(this);
//            this.itemView = itemView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.post_img:
                    image.buildDrawingCache();
                    Bitmap bmp = image.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytes = stream.toByteArray();
//                    setresult.putExtra("BMP",bytes);
//                    Bundle extras = new Bundle();
//                    extras.putParcelable("imageResource", images);
                    Intent intent = new Intent(mContext,FullScreenActivity.class);
                    intent.putExtra("imageResource",bytes);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
