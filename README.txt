Coding Test (fictitious)


***
NOTE: PLEASE DO NOT USE THIRD PARTY LIBRARIES. However, feel free to state which third party libraries you might have used.
***


Attached you’ll find the code for a simple mobile app to browse cakes. The developer who wrote this code was a summer intern and unfortunately did not manage to finish the project.  The project compiles but the app crashes as soon as it runs.

The app loads a JSON feed containing a repeated list of cakes, with title, image and description from a URL, then displays the contents of the feed in a scrollable list.

We would like you to fix the crash bug(s), ensure the functionality of the app works as expected (all images display correctly on the table, all text is readable) and performs smoothly.  Use of 3rd party libraries are prohibited for this project due to its sensitive nature.

Additionally, feel free to refactor and optimise the code where appropriate. Ideally, what we’d be looking for is:

* Simple fixes for crash bug(s)
* Application to support rotation
* Safe and efficient loading of images
* Removal of any redundant code
* Refactoring of code to best practices
* Migration to material design components (e.g. recycler view)

This is your opportunity to show us how you think an Android app should be architected, please make any changes you feel would benefit the app.

The test should take around 2 hours, certainly no longer than 3 hours. Good luck!


=========================================================================================================
Possible libraries (not used in this solution) :

Picasso - library for image download and caching



=========================================================================================================

## Notes
The following are notes made along the way...

---

### original structure

1. MainActivity.java

 	public class MainActivity extends AppCompatActivity    
      	protected void onCreate(Bundle savedInstanceState)  
      	public boolean onCreateOptionsMenu(Menu menu) 
	  	public boolean onOptionsItemSelected(MenuItem item)  

 	public static class PlaceholderFragment extends ListFragment
      	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
        public void onActivityCreated(Bundle savedInstanceState)
        private JSONArray loadData() throws IOException, JSONException 
        public static String parseCharset(String contentType) 

	private class MyAdapter extends BaseAdapter   
            public MyAdapter()   
            public MyAdapter(JSONArray items)  
            public Object getItem(int position)
            public long getItemId(int position) 
            public View getView(int position, View convertView, ViewGroup parent)  
            public void setItems(JSONArray items) 
  
  
  
   
2. ImageLoader
	public class ImageLoader 
	
	    private static final String TAG = ImageLoader.class.getSimpleName();
	
	    public ImageLoader() 
	
	    public void load(String url, ImageView imageView) 
	private static byte[] loadImageData(String url) throws IOException 
	
	private static Bitmap convertToBitmap(byte[] data) 
	private static void setImageView(ImageView imageView, Bitmap bitmap)
	    
    
1. StreamUtils


