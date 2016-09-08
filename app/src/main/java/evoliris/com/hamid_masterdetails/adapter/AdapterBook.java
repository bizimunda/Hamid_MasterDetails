package evoliris.com.hamid_masterdetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import evoliris.com.hamid_masterdetails.R;
import evoliris.com.hamid_masterdetails.model.Book;

/**
 * Created by temp on 8/09/2016.
 */
public class AdapterBook extends ArrayAdapter<Book> {


    public AdapterBook(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book model = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tv_listItem_title);
        titleTextView.setText(String.valueOf(model.getId()));

        return listItemView;
    }
}

