package com.graystonemobile.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileStorage {
	private Context mctx;
	private String id;
	private String TAG = "File Storage";
	public FileStorage(Context ctx){
		mctx = ctx;
	}
	
	public void saveImage(Bitmap image, String id){
		this.id = id;
		File pictureFile = getOutputMediaFile();
	    if (pictureFile == null) {
	        Log.d(TAG ,
	                "Error creating media file, check storage permissions: ");// e.getMessage());
	        return;
	    } 
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.d(TAG, "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d(TAG, "Error accessing file: " + e.getMessage());
	    }  
	}
	
	//delete issue cover
		public void deleteImage(String id){
			
		}
		//get issue cover
		
		public Bitmap getImage(String id){
			Bitmap bitmap = null;
			File sdCard = Environment.getExternalStorageDirectory();

			File directory = new File (sdCard.getAbsolutePath() + "/Android/data/"
		            + mctx.getPackageName()
		            + "/Files");

			File file = new File(directory, id.trim()+".jpg"); //or any other format supported

			try {
				FileInputStream streamIn = new FileInputStream(file);

				bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

				streamIn.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
		}
	
	private  File getOutputMediaFile(){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this. 
	    File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + "/Android/data/"
	            + mctx.getPackageName()
	            + "/Files"); 
	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    } 
	    //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
	    File mediaFile;
	        String mImageName = this.id +".jpg";
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    return mediaFile;
	} 
}
