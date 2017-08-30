package nikam.com.gridview_multiplechoicelistener;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    MovieAdapter movieAdapter;
    List<MovieData> movieDataList = new ArrayList<MovieData>();
    LayoutInflater inflater;

    protected Cursor mCursor;
    protected int columnIndex;

    private static final int PERMISSION_REQUEST_CODE = 200;

    int[] poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the view from listview_main.xml
        setContentView(R.layout.movie_main);

        // Generate sample data into string arrays

        poster = new int[]{R.drawable.airlift, R.drawable.barfi, R.drawable.ready,
                R.drawable.bajrangi_bhaijaan, R.drawable.dilwale,
                R.drawable.madras_cafe, R.drawable.queen, R.drawable.robot,
                R.drawable.talaash};



        for (int i = 0; i < poster.length; i++) {
            MovieData movieData = new MovieData(poster[i]);
            movieDataList.add(movieData);
        }

        // Locate the ListView in listview_main.xml
        gridView = (GridView) findViewById(R.id.gridView1);

        // Pass results to ListViewAdapter Class

        movieAdapter = new MovieAdapter(this, R.layout.movie_item, movieDataList, inflater);

        // movieAdapter = new MovieAdapter(Context, R.layout.movie_item,movieDataList);

        // Binds the Adapter to the ListView
        gridView.setAdapter(movieAdapter);
        gridView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // Capture ListView item click
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                // Capture total checked items
                final int checkedCount = gridView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                movieAdapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected =movieAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                MovieData selecteditem = movieAdapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                movieAdapter.remove(selecteditem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {
                movieAdapter.removeSelection();
            }
        });
    }
    }

