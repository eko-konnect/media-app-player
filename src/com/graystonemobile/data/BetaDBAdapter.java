package com.graystonemobile.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BetaDBAdapter {
	//family table
	public static final String KEY_VIDEO_ID = "videoid";
	public static final String KEY_VIDEO_NAME = "videoname";
	public static final String KEY_VIDEO_TYPE = "type";
	public static final String KEY_VIDEO_SEASON = "season";	
	public static final String KEY_SERIES_ID = "seriesid";
	public static final String KEY_YOUTUBE_ID = "youtubeid";	
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String KEY_VIDEO_PATH = "path";
	public static final String KEY_VIDEO_DESCRIPTION = "desc";
	public static final String KEY_VIDEO_SHARE_PHRASE = "sharephrase";
	public static final String KEY_VIDEO_CREATE_DATE = "createdate";	
	public static final String KEY_VIDEO_LANGUAGE = "language";
	public static final String KEY_VIDEO_LENGHT = "lenght";	
	
	private static final String FAMILY_TABLE = "NaijaFamilyTable";
	
	private static final String FAMILY_TABLE_CREATE =
            "CREATE TABLE NaijaFamilyTable (_id integer primary key autoincrement, "
    		+ "videoid text not null, videoname text not null, type text not null," 
            + " season text not null, seriesid text not null,"
            + " youtubeid text not null, thumbnail text not null,"
            + " path text not null, desc text not null,"
            + " sharephrase text not null, createdate text not null,"
            + " language text not null, lenght text not null);";
	//naso table
	private static final String NASO_TABLE = "NaSOTable";
	
	private static final String NASO_TABLE_CREATE =
            "CREATE TABLE NaSOTable (_id integer primary key autoincrement, "
    		+ "videoid text not null, videoname text not null, type text not null," 
            + " season text not null, seriesid text not null,"
            + " youtubeid text not null, thumbnail text not null,"
            + " path text not null, desc text not null,"
            + " sharephrase text not null, createdate text not null,"
            + " language text not null, lenght text not null);";
	
	//shortfilms table
	private static final String SHORT_FILM_TABLE = "ShortFilmTable";
	
	private static final String SHORT_FILM_TABLE_CREATE =
            "CREATE TABLE ShortFilmTable (_id integer primary key autoincrement, "
    		+ "videoid text not null, videoname text not null, type text not null," 
            + " season text not null, seriesid text not null,"
            + " youtubeid text not null, thumbnail text not null,"
            + " path text not null, desc text not null,"
            + " sharephrase text not null, createdate text not null,"
            + " language text not null, lenght text not null);";
	
	private static final String TAG = "BetaDBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
	
	private static final String DATABASE_NAME = "Betadb";
    private static final int DATABASE_VERSION = 2;
	
    private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
    	
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(FAMILY_TABLE_CREATE);
			db.execSQL(NASO_TABLE_CREATE);
			db.execSQL(SHORT_FILM_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+FAMILY_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+NASO_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+SHORT_FILM_TABLE);
            onCreate(db);
		}
    }
    
    public BetaDBAdapter(Context ctx){
    	this.mCtx = ctx;
    }
    
    public BetaDBAdapter open() throws SQLException {
    	mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close(){
    	mDbHelper.close();
    }
    //family videos
    public long InsertNewFamilyVideo(String videoid,String title,String seriesid,
    		String series,String description,String thumbnail,String language
    		,String type,String lenght,String season,String youtube,String path,String share){
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_VIDEO_ID, videoid);
    	initialValues.put(KEY_VIDEO_NAME, title);
    	initialValues.put(KEY_SERIES_ID, seriesid);
    	initialValues.put(KEY_VIDEO_DESCRIPTION, description);
    	initialValues.put(KEY_VIDEO_LANGUAGE, language);
    	initialValues.put(KEY_VIDEO_TYPE, type);
    	initialValues.put(KEY_VIDEO_LENGHT, lenght);
    	initialValues.put(KEY_VIDEO_SEASON, season);
    	initialValues.put(KEY_YOUTUBE_ID, youtube);
    	initialValues.put(KEY_VIDEO_PATH, path);
    	initialValues.put(KEY_VIDEO_SHARE_PHRASE, share);
    	initialValues.put(KEY_THUMBNAIL, "");
    	initialValues.put(KEY_VIDEO_CREATE_DATE, "");
    	
    	return mDb.insert(FAMILY_TABLE, null, initialValues);
    }
    
    public boolean deleteAllFamilyVideos(){
    	return mDb.delete(FAMILY_TABLE, "1", null) > 0;    	
    }
    
    public boolean deleteFamilyVideoByID(String videoid){
    	return mDb.delete(FAMILY_TABLE, KEY_VIDEO_ID+"="+videoid.trim(), null) > 0;
    }
    
    public Cursor fetchAllFamilyBySeasonAndLanguage(String season,String language){
    	return mDb.query(FAMILY_TABLE, null, KEY_VIDEO_LANGUAGE+"="+"'"+language.trim()+"'", null, null, null, null);
    }
    
    public Cursor fetchAllFamilyVideos(){
    	return mDb.query(FAMILY_TABLE, null, null, null, null, null, null);    	
    }
	//get family by season and language
	//get family by language
	//get
    //naso e be videos
    public long InsertNewNaSoVideo(String videoid,String title,String seriesid,
    		String series,String description,String thumbnail,String language
    		,String type,String lenght,String season,String youtube,String path,String share){
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_VIDEO_ID, videoid);
    	initialValues.put(KEY_VIDEO_NAME, title);
    	initialValues.put(KEY_SERIES_ID, seriesid);
    	initialValues.put(KEY_VIDEO_DESCRIPTION, description);
    	initialValues.put(KEY_VIDEO_LANGUAGE, language);
    	initialValues.put(KEY_VIDEO_TYPE, type);
    	initialValues.put(KEY_VIDEO_LENGHT, lenght);
    	initialValues.put(KEY_VIDEO_SEASON, season);
    	initialValues.put(KEY_YOUTUBE_ID, youtube);
    	initialValues.put(KEY_VIDEO_PATH, path);
    	initialValues.put(KEY_VIDEO_SHARE_PHRASE, share);
    	initialValues.put(KEY_THUMBNAIL, "");
    	initialValues.put(KEY_VIDEO_CREATE_DATE, "");
    	
    	return mDb.insert(NASO_TABLE, null, initialValues);
    }
    
    public boolean deleteAllNaSoVideos(){
    	return mDb.delete(NASO_TABLE, "1", null) > 0;    	
    }
    
    public boolean deleteNaSoVideoByID(String videoid){
    	return mDb.delete(NASO_TABLE, KEY_VIDEO_ID+"="+videoid.trim(), null) > 0;
    	
    }
    
    public Cursor fetchAllNaSoBySeasonAndLanguage(String season,String language){
    	return mDb.query(NASO_TABLE, null, KEY_VIDEO_LANGUAGE+"="+"'"+language.trim()+"'", null, null, null, null);   	
    }
    
    public Cursor fetchAllNaSoVideos(){
    	return mDb.query(NASO_TABLE, null, null, null, null, null, null);    	
    }
  //short film videos
    public long InsertShortFilmVideo(String videoid,String title,String seriesid,
    		String series,String description,String thumbnail,String language
    		,String type,String lenght,String season,String youtube,String path,String share){
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_VIDEO_ID, videoid);
    	initialValues.put(KEY_VIDEO_NAME, title);
    	initialValues.put(KEY_SERIES_ID, seriesid);
    	initialValues.put(KEY_VIDEO_DESCRIPTION, description);
    	initialValues.put(KEY_VIDEO_LANGUAGE, language);
    	initialValues.put(KEY_VIDEO_TYPE, type);
    	initialValues.put(KEY_VIDEO_LENGHT, lenght);
    	initialValues.put(KEY_VIDEO_SEASON, season);
    	initialValues.put(KEY_YOUTUBE_ID, youtube);
    	initialValues.put(KEY_VIDEO_PATH, path);
    	initialValues.put(KEY_VIDEO_SHARE_PHRASE, share);
    	initialValues.put(KEY_THUMBNAIL, "");
    	initialValues.put(KEY_VIDEO_CREATE_DATE, "");
    	
    	return mDb.insert(SHORT_FILM_TABLE, null, initialValues);
    }
    
    public boolean deleteAllShortFilmsVideos(){
    	return mDb.delete(SHORT_FILM_TABLE, "1", null) > 0;    	
    }
    
    public boolean deleteShortFilmVideoByID(String videoid){
    	return mDb.delete(SHORT_FILM_TABLE, KEY_VIDEO_ID+"="+videoid.trim(), null) > 0;
    }
    
    public Cursor fetchAllShortFilmBySeasonAndLanguage(String season,String language){
    	return mDb.query(SHORT_FILM_TABLE, null, KEY_VIDEO_LANGUAGE+"="+"'"+language.trim()+"'", null, null, null, null);
    }
    
    public Cursor fetchAllShortFilmVideos(){
    	return mDb.query(SHORT_FILM_TABLE, null, null, null, null, null, null);    	
    }
}
