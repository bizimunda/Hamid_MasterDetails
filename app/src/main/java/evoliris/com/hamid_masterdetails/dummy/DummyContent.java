package evoliris.com.hamid_masterdetails.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {


    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();


    public static Map<String, DummyItem> ITEM_MAP =
            new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "eBookFrenzy", "http://www.ebookfrenzy.com"));
        addItem(new DummyItem("2", "Google", "http://www.google.com"));
        addItem(new DummyItem("3", "Android3", "http://www.android.com"));
        addItem(new DummyItem("4", "Android4", "http://www.android.com"));
        addItem(new DummyItem("5", "Android5", "http://www.android.com"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class DummyItem {
        public String id;
        public String website_name;
        public String website_url;

        public DummyItem(String id, String website_name,
                         String website_url) {
            this.id = id;
            this.website_name = website_name;
            this.website_url = website_url;
        }

        @Override
        public String toString() {
            return website_name;
        }
    }
}
