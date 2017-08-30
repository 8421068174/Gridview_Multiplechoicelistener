package nikam.com.gridview_multiplechoicelistener;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nikam on 24/08/2017.
 */
public class MovieAdapter extends ArrayAdapter<MovieData> {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<MovieData> movieDataList;
    private SparseBooleanArray mSelectedItemsIds;


    public MovieAdapter(Context context, int resource, List<MovieData> movieDataList,
                        LayoutInflater inflater) {
        super(context, resource,movieDataList);

        this.context=context;
        this.movieDataList = movieDataList;


        mSelectedItemsIds = new SparseBooleanArray();
       this.inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        ImageView poster;

    }

    public int dpToPx(int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        int imageID = 0;

        int wPixel = dpToPx(120);
        int hPixel = dpToPx(120);


            if (view == null) {
                holder = new ViewHolder();

                view = inflater.inflate(R.layout.movie_item,null);


                // Locate the ImageView in listview_item.xml
                holder.poster = (ImageView) view.findViewById(R.id.image);
                // Set height and width constraints for the image view
               holder.poster.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

                holder.poster.setImageURI(
                        Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID)
                );
                // Image should be cropped towards the center
                holder.poster.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // Set Padding for images
                holder.poster.setPadding(10, 10, 10,10);

                // Crop the image to fit within its padding
                holder.poster.setCropToPadding(true);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            // Capture position and set to the ImageView
            holder.poster.setImageResource(movieDataList.get(position).getPoster());

        return view;
       }


    @Override
    public void remove(MovieData object) {
        movieDataList.remove(object);
        notifyDataSetChanged();
    }

    public List<MovieData> getMovieDataList() {
        return movieDataList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
