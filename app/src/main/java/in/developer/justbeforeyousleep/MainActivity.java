package in.developer.justbeforeyousleep;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import in.developer.justbeforeyousleep.model.ImageModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private ArrayList<ImageModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addImage();
    }

    private void init() {
        recyclerView = findViewById(R.id.list_rv);
    }

    private void addImage() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            arrayList.add(new ImageModel("0", "Sundram Singh", "2", R.drawable.our_vision));
        }
        adapter = new HomeAdapter(arrayList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public static boolean setWallpaper(Activity activity, Bitmap bitmap){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Bitmap tempBitmap = scaleCenterCrop(bitmap, height, width);


        WallpaperManager wallaperManager = WallpaperManager.getInstance(activity);
        try
        {

            wallaperManager.setBitmap(tempBitmap);
            return true;
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
}
