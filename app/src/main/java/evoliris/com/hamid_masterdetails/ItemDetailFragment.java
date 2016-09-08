package evoliris.com.hamid_masterdetails;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import evoliris.com.hamid_masterdetails.dummy.DummyContent;
import evoliris.com.hamid_masterdetails.task.AsynctaskShowOnlyOneBook;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements AsynctaskShowOnlyOneBook.GetAsyncTaskCreateOnlyOneBookCallback {


    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private AsynctaskShowOnlyOneBook onlyOneBook;

    public static final String ARG_ITEM_ID = "item_id";
    private DummyContent.DummyItem mItem;
    private long id;

    private TextView tvDetailFragmentTitle;


    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ITEM_ID)) {
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            return;
        }

        id = getArguments().getLong(ARG_ITEM_ID);

        cm = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            onlyOneBook = new AsynctaskShowOnlyOneBook(ItemDetailFragment.this);
            onlyOneBook.execute("http://192.168.1.120:1337/book/"+id);
        } else {
            Toast.makeText(this.getContext(), "No data connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

           tvDetailFragmentTitle = ((TextView) rootView.findViewById(R.id.item_detail));


        return rootView;
    }

    @Override
    public void onPostGetOnlyOneBook(String s) throws JSONException {
        Log.i("ItemDetailFragment", s);

        JSONObject finalObject= new JSONObject(s);
        String title=finalObject.getString("title");

        tvDetailFragmentTitle.setText(title);

    }
}
