package evoliris.com.hamid_masterdetails;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import evoliris.com.hamid_masterdetails.adapter.AdapterBook;
import evoliris.com.hamid_masterdetails.model.Book;
import evoliris.com.hamid_masterdetails.task.AsynctaskShowBooks;


public class ItemListFragment extends ListFragment implements AsynctaskShowBooks.GetAsyncTaskCallback{


    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private AsynctaskShowBooks taskShowBooks;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;


    public interface Callbacks {
        public void onItemSelected(long id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }
    };

    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cm = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            taskShowBooks = new AsynctaskShowBooks(ItemListFragment.this);
            taskShowBooks.execute("http://192.168.1.120:1337/book/");
        } else {
            Toast.makeText(this.getContext(), "No data connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPostGet(String s) {
        Gson gson = new Gson();
        JSONArray jsonArray;
        ArrayList<Book> books = new ArrayList<>();

        try {
            jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                books.add(gson.fromJson(jsonArray.get(i).toString(), Book.class));
                Log.i("BOOK", books.get(i).getTitle());
                AdapterBook adapterBook= new AdapterBook(this.getContext(), books);
                setListAdapter(adapterBook);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Book selectedBook = ((AdapterBook)listView.getAdapter()).getItem(position);
        mCallbacks.onItemSelected(selectedBook.getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        mActivatedPosition = position;
    }
}
