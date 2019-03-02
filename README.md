# android_cake

This is a solution to the Waracle android cake test


## Notes
The following are notes made along the way...

---

### original structure

1. MainActivity.java
* public class MainActivity extends AppCompatActivity    
*      protected void onCreate(Bundle savedInstanceState)  
*      public boolean onCreateOptionsMenu(Menu menu) 
*	  public boolean onOptionsItemSelected(MenuItem item)  
* public static class PlaceholderFragment extends ListFragment
      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
              public void onActivityCreated(Bundle savedInstanceState)
        private JSONArray loadData() throws IOException, JSONException
                public static String parseCharset(String contentType) 
  *  private class MyAdapter extends BaseAdapter   
                public MyAdapter()   
            public MyAdapter(JSONArray items)  
            public Object getItem(int position)
            public long getItemId(int position) 
            public View getView(int position, View convertView, ViewGroup parent)  
                        public void setItems(JSONArray items) 
  
  
  
   
1. ImageLoader

1. StreamUtils


	