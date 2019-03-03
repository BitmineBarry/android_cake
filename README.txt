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
	    
    
3. StreamUtils

	
=========================================================================================================

move fetch of json data to AsyncTask

move fetch of image to AsyncTask

Sort out the layouts to make a reasonable list format that can be rotated.
Stil need to confirm different font sizes and their effects
Need to return the layout to reasonable colours. Colour highlighting has been used to identify layout areas.


Cakes seem to be repeated in the list.
I fetched the JSON from the URL with firefox to confirm the list.
The list does repeat the same 5 cakes 4 times.
The JSON list is :
[  
   {  
      "title":"Lemon cheesecake",
      "desc":"A cheesecake made of lemon",
      "image":"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
   },
   {  
      "title":"victoria sponge",
      "desc":"sponge with jam",
      "image":"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg"
   },
   {  
      "title":"Carrot cake",
      "desc":"Bugs bunnys favourite",
      "image":"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg"
   },
   {  
      "title":"Banana cake",
      "desc":"Donkey kongs favourite",
      "image":"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg"
   },
   {  
      "title":"Birthday cake",
      "desc":"a yearly treat",
      "image":"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg"
   },
.
.
.
]


=====================================================================================
Add HTTP caching to the application...
Images load slowly because the carrot cake image is very large. 
Approx 770k. resolution of 2500x 3750. This takes about 2 seconds to fetch from the cache and will take time to scale to the required size. 

It would help, in this simple instance, to find a way of caching the reduced images ready for the display component.

=====================================================================================


