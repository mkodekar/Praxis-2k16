package com.appnirman.praxis2k16.LoginAndRegister.Utilities.AppnirmanServer;

/**
 * Created by santosh on 14-06-2015.
 * this file is used to handle sqlite database. all sqlite database related functions are
 * defined in this file and can be accessed from any class.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "shiningFuture";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "fname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILENO = "mobileno";
    private static final String KEY_REFERRAL = "referral";
    private static final String KEY_OTP = "otp";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DOB = "dob";
    private static final String KEY_CNOTIFICATION = "cnotification";
    private static final String KEY_ONOTIFICATION = "onotification";
    // State table name
    public static final String TABLE_STATE = "state";
    //State Table Columns names
    private static final String KEY_STATE_ID = "id";
    private static final String KEY_STATE_NAME = "statename";

    //District table name
    public static String TABLE_DISTRICT = "district";
    //district Table Columns names
    private static final String KEY_DISTRICT_ID = "id";
    private static final String KEY_DISTRICT_NAME = "districtname";
    private static final String KEY_DISTRICT_STATE_ID = "stateid";

    //locality table name
    public static String TABLE_LOCALITY = "locality";
    //locality Table Columns names
    private static final String KEY_LOCALITY_ID = "id";
    private static final String KEY_LOCALITY_NAME = "localityname";
    private static final String KEY_LOCALITY_DISTRICT_ID = "districtid";

    //Corporator table name
    public static String TABLE_CORPORATOR = "corporator";
    //Corporator Table Columns
    private static final String KEY_CORPORATOR_ID = "id";
    private static final String KEY_CORPORATOR_NAME = "name";
    private static final String KEY_CORPORATOR_GENDER = "gender";
    private static final String KEY_CORPORATOR_DOB = "dob";
    private static final String KEY_CORPORATOR_EMAIL = "email";
    private static final String KEY_CORPORATOR_LOGINID = "loginid";
    private static final String KEY_CORPORATOR_PARTY_NAME = "partyname";
    private static final String KEY_CORPORATOR_THOUGHT = "thought";
    private static final String KEY_CORPORATOR_PARTY_SIGN = "party_sign";
    private static final String KEY_CORPORATOR_PROFILE_PIC = "profile_pic";
    private static final String KEY_CORPORATOR_OFFICIAL = "official";
    private static final String KEY_CORPORATOR_FOLLOW = "follow";
    private static final String KEY_CORPORATOR_MISSION = "mission";
    private static final String KEY_CORPORATOR_ADDRESS = "address";
    private static final String KEY_CORPORATOR_WEBSITE = "website";
    private static final String KEY_CORPORATOR_FBLINK = "fblink";
    private static final String KEY_CORPORATOR_DESC = "desc";
    private static final String KEY_CORPORATOR_WARD = "ward";

    //Ward table name
    public static String TABLE_WARD = "ward";
    //Ward Table Columns names
    private static final String KEY_WARD_ID = "id";
    private static final String KEY_WARD_NAME = "wardname";
    private static final String KEY_WARD_LOCALITY_ID = "localityid";
    private static final String KEY_WARD_CORP_ID = "corpid";

    // corporate table name
    public static final String TABLE_CORPORATE = "corporate";
    //Corporate Table Columns names
    private static final String KEY_CORPORATE_ID = "id";
    private static final String KEY_CORPORATE_NAME = "corporatename";

    // Chosen ward table name
    private static final String TABLE_CHOSEN_WARD = "chosenward";
    //Chosen ward Table Columns names
    private static final String KEY_CHOSEN_WARD_TABLEID = "id";
    private static final String KEY_CHOSEN_WARD_ID = "wardid";
    private static final String KEY_CHOSEN_WARD_NAME = "wardname";

    // Chosen ward table name
    private static final String TABLE_PINCODE = "chosenpincode";
    //Chosen ward Table Columns names
    private static final String KEY_CHOSEN_PINCODE_TABLEID = "id";
    private static final String KEY_CHOSEN_PINCODE = "pincode";

    //category table
    public static String TABLE_CATEGORY = "category";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    //child category
    public static final String TABLE_CHILD_CATEGORY = "childcategory";
    private static final String KEY_CHILD_CATEGORY_ID = "child_cat_id";
    private static final String KEY_CHILD_CATEGORY_NAME = "child_cat_name";
    private static final String KEY_CHILDCAT_CATEGORY_ID = "category_id";
    //grand child table
    public static final String TABLE_GRAND_CHILD_CATEGORY = "grandchildcategory";
    private static final String KEY_GRAND_CHILD_CATEGORY_ID = "grand_child_cat_id";
    private static final String KEY_GRAND_CHILD_CAT_NAME = "grand_child_cat_name";
    private static final String KEY_GARNDCHILD_CHILDCATEGORY_ID = "child_cat_id";

    public String TABLE_AVAILABILITY = "availability";
    public String COL_AVAILABILITY_ID = "availability_id";
    public String COL_AVAILABILITY_NAME = "availability";

    public String TABLE_CAT_WISE_AVAILABILITY = "catwiseavailability";
    public String COL_CAT_WISE_AVAILABILITY_ID = "cat_availability_id";
    public String COL_CAT_WISE_AVAILABILITY_NAME = "cat_availability";
    public String KEY_CAT_WISE_ID ="_cat_id";

    public String TABLE_COMPLAINT_TYPE="copmlaint_type";
    public String COL_COMPLAINT_TYPE = "type";

    //District table name
    public static String TABLE_FAQ = "faq";
    //district Table Columns names
    private static final String KEY_QUESTION_ID = "q_id";
    private static final String COL_QUESTION = "question";
    private static final String COL_ANSWER = "answer";

    //MyDeals table name
    public static String TABLE_MYDEALS = "mydeals";
    //district Table Columns names
    private static final String KEY_MYDEALS_ID = "id";
    private static final String KEY_MYDEALS_ADID = "adid";
    private static final String KEY_MYDEALS_TITLE = "title";
    private static final String KEY_MYDEALS_QRCODE = "qrcode";
    private static final String KEY_MYDEALS_EXPIRY = "expiry";
    private static final String KEY_MYDEALS_NAME = "shopname";
    private static final String KEY_MYDEALS_ADDRESS = "shopaddress";
    private static final String KEY_MYDEALS_CONTACT = "shopcontact";

    //timestamp table
    private static String TABLE_TIMESTAMP = "timestamp";
    //Corporator Table Columns
    public static String KEY_TIMESTAMP_ID = "id";
    public static String KEY_TIMESTAMP_AVAILABILITY= "availability";
    public static String KEY_TIMESTAMP_CATEGORY = "category";
    public static String KEY_TIMESTAMP_CATEGORY_OFFERS = "category_offers";
    public static String KEY_TIMESTAMP_CATEGORY_WISE_AVAILABILITY = "category_wise_availability";
    public static String KEY_TIMESTAMP_COMPLAINT_TYPE= "complaint_type";
    public static String KEY_TIMESTAMP_CORPORATE = "corporate";
    public static String KEY_TIMESTAMP_CORPORATORS = "corporators";
    public static String KEY_TIMESTAMP_DISTRICTS = "districts";
    public static String KEY_TIMESTAMP_FAQ = "faq";
    public static String KEY_TIMESTAMP_GRAND_CHILD_CATEGORY = "grand_child_category";
    public static String KEY_TIMESTAMP_LOCALITY = "locality";
    public static String KEY_TIMESTAMP_PARTY_INFO = "party_info";
    public static String KEY_TIMESTAMP_STATE = "state";
    public static String KEY_TIMESTAMP_SUB_CATEGORY= "sub_category";
    public static String KEY_TIMESTAMP_WARD = "ward";

    //MyDeals table name
    public static String TABLE_DEVICE_CONTACTS = "contacts";
    //district Table Columns names
    private static final String KEY_DEVICE_CONTACTS_ID = "id";
    private static final String KEY_DEVICE_CONTACTS_NAME = "name";
    private static final String KEY_DEVICE_CONTACTS_PHONE = "phone";
    private static final String KEY_DEVICE_CONTACTS_STATUS = "status";

    private String follow="1";
    private String unfollow="0";

    //request table
    public static String TABLE_REQUEST = "request";
    private static final String KEY_REQUEST_ID = "request_id";
    private static final String KEY_REQUEST_STATUS = "request_status";
    private static final String KEY_REQUESTED_WARD = "requested_ward";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_MOBILENO + " TEXT,"
                + KEY_REFERRAL + " TEXT,"
                + KEY_OTP + " TEXT,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_CNOTIFICATION + " TEXT,"
                + KEY_ONOTIFICATION + " TEXT"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_CORPORATOR_TABLE = "CREATE TABLE " + TABLE_CORPORATOR + "("
                + KEY_CORPORATOR_ID + " INTEGER PRIMARY KEY,"
                + KEY_CORPORATOR_NAME + " TEXT,"
                + KEY_CORPORATOR_GENDER + " TEXT,"
                + KEY_CORPORATOR_DOB+ " TEXT,"
                + KEY_CORPORATOR_EMAIL + " TEXT,"
                + KEY_CORPORATOR_LOGINID + " TEXT,"
                + KEY_CORPORATOR_PARTY_NAME + " TEXT,"
                + KEY_CORPORATOR_THOUGHT + " TEXT,"
                + KEY_CORPORATOR_PARTY_SIGN +" TEXT,"
                + KEY_CORPORATOR_PROFILE_PIC + " TEXT,"
                + KEY_CORPORATOR_OFFICIAL +" TEXT,"
                + KEY_CORPORATOR_FOLLOW + " TEXT,"
                + KEY_CORPORATOR_MISSION + " TEXT,"
                + KEY_CORPORATOR_ADDRESS + " TEXT,"
                + KEY_CORPORATOR_WEBSITE +" TEXT,"
                + KEY_CORPORATOR_FBLINK + " TEXT,"
                + KEY_CORPORATOR_DESC +" TEXT,"
                + KEY_CORPORATOR_WARD + " TEXT)";
        db.execSQL(CREATE_CORPORATOR_TABLE);

        String CREATE_TIMESTAMP_TABLE = "CREATE TABLE " + TABLE_TIMESTAMP + "("
                + KEY_TIMESTAMP_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIMESTAMP_AVAILABILITY + " TEXT,"
                + KEY_TIMESTAMP_CATEGORY + " TEXT,"
                + KEY_TIMESTAMP_CATEGORY_OFFERS+ " TEXT,"
                + KEY_TIMESTAMP_CATEGORY_WISE_AVAILABILITY + " TEXT,"
                + KEY_TIMESTAMP_COMPLAINT_TYPE + " TEXT,"
                + KEY_TIMESTAMP_CORPORATE + " TEXT,"
                + KEY_TIMESTAMP_CORPORATORS + " TEXT,"
                + KEY_TIMESTAMP_DISTRICTS +" TEXT,"
                + KEY_TIMESTAMP_FAQ + " TEXT,"
                + KEY_TIMESTAMP_GRAND_CHILD_CATEGORY +" TEXT,"
                + KEY_TIMESTAMP_LOCALITY+" TEXT,"
                + KEY_TIMESTAMP_PARTY_INFO +" TEXT,"
                + KEY_TIMESTAMP_STATE +" TEXT,"
                + KEY_TIMESTAMP_SUB_CATEGORY +" TEXT,"
                + KEY_TIMESTAMP_WARD + " TEXT)";
        db.execSQL(CREATE_TIMESTAMP_TABLE);

        String CREATE_STATE_TABLE = "CREATE TABLE " + TABLE_STATE + "("
                + KEY_STATE_ID + " INTEGER PRIMARY KEY,"
                + KEY_STATE_NAME + " TEXT" + ")";
        db.execSQL(CREATE_STATE_TABLE);

        String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
                + KEY_DISTRICT_ID + " INTEGER PRIMARY KEY, "
                + KEY_DISTRICT_NAME + " TEXT, "
                + KEY_DISTRICT_STATE_ID + " TEXT" + ")";
        db.execSQL(CREATE_DISTRICT_TABLE);

        String CREATE_LOCALITY_TABLE = "CREATE TABLE " + TABLE_LOCALITY + "("
                + KEY_LOCALITY_ID + " INTEGER PRIMARY KEY, "
                + KEY_LOCALITY_NAME + " TEXT, "
                + KEY_LOCALITY_DISTRICT_ID + " TEXT" + ")";
        db.execSQL(CREATE_LOCALITY_TABLE);

        String CREATE_WARD_TABLE = "CREATE TABLE " + TABLE_WARD + "("
                + KEY_WARD_ID + " INTEGER PRIMARY KEY,"
                + KEY_WARD_NAME + " TEXT, "
                + KEY_WARD_LOCALITY_ID + " TEXT, "
                + KEY_WARD_CORP_ID + " TEXT" + ")";
        db.execSQL(CREATE_WARD_TABLE);

        String CREATE_COMPLAINT_TYPE_TABLE = "CREATE TABLE " + TABLE_COMPLAINT_TYPE + "("
                + COL_COMPLAINT_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_COMPLAINT_TYPE_TABLE);

        String CREATE_CORPORATE_TABLE = "CREATE TABLE " + TABLE_CORPORATE + "("
                + KEY_CORPORATE_ID + " INTEGER PRIMARY KEY,"
                + KEY_CORPORATE_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CORPORATE_TABLE);

        String CREATE_PINCODE_TABLE = "CREATE TABLE " + TABLE_PINCODE + "("
                + KEY_CHOSEN_PINCODE_TABLEID + " INTEGER PRIMARY KEY,"
                + KEY_CHOSEN_PINCODE + " TEXT" + ")";
        db.execSQL(CREATE_PINCODE_TABLE);

        String CREATE_CHOSEN_WARD_TABLE = "CREATE TABLE " + TABLE_CHOSEN_WARD + "("
                + KEY_CHOSEN_WARD_TABLEID + " INTEGER PRIMARY KEY,"
                + KEY_CHOSEN_WARD_ID + " TEXT, "
                + KEY_CHOSEN_WARD_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CHOSEN_WARD_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEGORY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String CREATE_CHILD_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CHILD_CATEGORY + "("
                + KEY_CHILD_CATEGORY_ID + " INTEGER PRIMARY KEY, "
                + KEY_CHILD_CATEGORY_NAME + " TEXT, "
                + KEY_CHILDCAT_CATEGORY_ID + " TEXT" + ")";
        db.execSQL(CREATE_CHILD_CATEGORY_TABLE);

        String CREATE_GRAND_CHILD_CAT_TABLE = "CREATE TABLE " + TABLE_GRAND_CHILD_CATEGORY + "("
                + KEY_GRAND_CHILD_CATEGORY_ID + " INTEGER PRIMARY KEY, "
                + KEY_GRAND_CHILD_CAT_NAME + " TEXT, "
                + KEY_GARNDCHILD_CHILDCATEGORY_ID + " TEXT" + ")";
        db.execSQL(CREATE_GRAND_CHILD_CAT_TABLE);

        String CREATE_AVAILABILITY_TABLE = "CREATE TABLE " + TABLE_AVAILABILITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + COL_AVAILABILITY_ID + " TEXT, "
                + COL_AVAILABILITY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_AVAILABILITY_TABLE);

        String CREATE_CAT_WISE_AVAILABILITY_TABLE = "CREATE TABLE " + TABLE_CAT_WISE_AVAILABILITY + "("
                + KEY_CAT_WISE_ID + " INTEGER PRIMARY KEY, "
                + COL_CAT_WISE_AVAILABILITY_ID + " TEXT, "
                + COL_CAT_WISE_AVAILABILITY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CAT_WISE_AVAILABILITY_TABLE);

        String CREATE_FAQ_TABLE = "CREATE TABLE " +TABLE_FAQ + "("
                + KEY_QUESTION_ID + " TEXT, "
                + COL_QUESTION + " TEXT, "
                + COL_ANSWER + " TEXT" + ")";
        db.execSQL(CREATE_FAQ_TABLE);

        String CREATE_MYDEAL_TABLE = "CREATE TABLE " + TABLE_MYDEALS + "("
                + KEY_MYDEALS_ID + " INTEGER PRIMARY KEY,"
                + KEY_MYDEALS_ADID + " TEXT, "
                + KEY_MYDEALS_TITLE + " TEXT, "
                + KEY_MYDEALS_QRCODE + " TEXT, "
                + KEY_MYDEALS_EXPIRY + " TEXT, "
                + KEY_MYDEALS_NAME + " TEXT, "
                + KEY_MYDEALS_ADDRESS + " TEXT, "
                + KEY_MYDEALS_CONTACT + " TEXT" + ")";
        db.execSQL(CREATE_MYDEAL_TABLE);

        String CREATE_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_REQUEST + "("
                + KEY_REQUEST_ID + " INTEGER PRIMARY KEY,"
                + KEY_REQUESTED_WARD + " TEXT, "
                + KEY_REQUEST_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_REQUEST_TABLE);

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVICE_CONTACTS + "("
                + KEY_DEVICE_CONTACTS_ID + " INTEGER PRIMARY KEY,"
                + KEY_DEVICE_CONTACTS_NAME + " TEXT, "
                + KEY_DEVICE_CONTACTS_PHONE + " TEXT, "
                + KEY_DEVICE_CONTACTS_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALITY);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WARD);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORPORATE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOSEN_WARD);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PINCODE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESTAMP);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYDEALS);

        // Create tables again
//        onCreate(db);

        if(oldVersion<2){
            String CREATE_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_REQUEST + "("
                    + KEY_REQUEST_ID + " INTEGER PRIMARY KEY,"
                    + KEY_REQUESTED_WARD + " TEXT, "
                    + KEY_REQUEST_STATUS + " TEXT" + ")";
            db.execSQL(CREATE_REQUEST_TABLE);
        }

        String upgradeCorporatorTableAddMission = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_MISSION +" TEXT";
        String upgradeCorporatorTableAddAddress = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_ADDRESS +" TEXT";
        String upgradeCorporatorTableAddWebsite = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_WEBSITE +" TEXT";
        String upgradeCorporatorTableAddFbLink = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_FBLINK +" TEXT";
        String upgradeCorporatorTableAddDesc = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_DESC +" TEXT";
        String upgradeCorporatorTableAddWard = "ALTER TABLE "+TABLE_CORPORATOR+" ADD COLUMN " + KEY_CORPORATOR_WARD +" TEXT";

        if (oldVersion <3) {
            db.execSQL(upgradeCorporatorTableAddMission);
            db.execSQL(upgradeCorporatorTableAddAddress);
            db.execSQL(upgradeCorporatorTableAddWebsite);
            db.execSQL(upgradeCorporatorTableAddFbLink);
            db.execSQL(upgradeCorporatorTableAddDesc);
            db.execSQL(upgradeCorporatorTableAddWard);

        }
        if(oldVersion<4){
            String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVICE_CONTACTS + "("
                    + KEY_DEVICE_CONTACTS_ID + " INTEGER PRIMARY KEY,"
                    + KEY_DEVICE_CONTACTS_NAME + " TEXT, "
                    + KEY_DEVICE_CONTACTS_PHONE + " TEXT, "
                    + KEY_DEVICE_CONTACTS_STATUS + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String mobileno, String referral, String password, String dob, String userID, String cNotificaton, String oNotification, String otp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // FirstName
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_MOBILENO, mobileno); // mobileno
        values.put(KEY_REFERRAL, referral); // referral
        values.put(KEY_PASSWORD, password); // password
        values.put(KEY_DOB, dob); // dob
        values.put(KEY_ID, userID);
        values.put(KEY_CNOTIFICATION, cNotificaton); // c_notification
        values.put(KEY_ONOTIFICATION, oNotification); // o_notification
        values.put(KEY_OTP, otp);
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    public void addCorporator(String name, String gender, String dob, String email, String loginid, String partyName, String thought, String party_sign, String profile_pic, String official, String follow, String mission, String address, String website, String fblink, String desc, String ward) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CORPORATOR_NAME, name); // FirstName
        values.put(KEY_CORPORATOR_GENDER, gender); // GENDER
        values.put(KEY_CORPORATOR_DOB, dob); // DOB
        values.put(KEY_CORPORATOR_EMAIL, email); // EMAIL
        values.put(KEY_CORPORATOR_LOGINID, loginid); // login id
        values.put(KEY_CORPORATOR_PARTY_NAME, partyName); // partyName
        values.put(KEY_CORPORATOR_THOUGHT, thought); // thought
        values.put(KEY_CORPORATOR_PARTY_SIGN, party_sign);//party sign
        values.put(KEY_CORPORATOR_PROFILE_PIC, profile_pic);// profile pic
        values.put(KEY_CORPORATOR_OFFICIAL, official);// is official
        values.put(KEY_CORPORATOR_FOLLOW, follow);// is following
        values.put(KEY_CORPORATOR_MISSION, mission);//mission
        values.put(KEY_CORPORATOR_ADDRESS, address);// address
        values.put(KEY_CORPORATOR_WEBSITE, website);// website
        values.put(KEY_CORPORATOR_FBLINK, fblink);// fblink
        values.put(KEY_CORPORATOR_DESC, desc);// website
        values.put(KEY_CORPORATOR_WARD, ward);// fblink
        // Inserting Row
        db.insert(TABLE_CORPORATOR, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Storing state names in database
     * */
    public void addState(String state) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATE_NAME, state); // State Name

        // Inserting Row
        db.insert(TABLE_STATE, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing district names in database
     * */
    public void addDistrict(String district, String stateID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISTRICT_NAME, district); // District Name
        values.put(KEY_DISTRICT_STATE_ID, stateID); // State Id

        // Inserting Row
        db.insert(TABLE_DISTRICT, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing locality names in database
     * */
    public void addLocality(String locality, String districtID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCALITY_NAME, locality); // locality Name
        values.put(KEY_LOCALITY_DISTRICT_ID, districtID); // district ID

        // Inserting Row
        db.insert(TABLE_LOCALITY, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing ward names in database
     * */
    public void addWard(String ward, String localityID, String corpID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WARD_NAME, ward); // ward Name
        values.put(KEY_WARD_LOCALITY_ID, localityID); // locality ID
        values.put(KEY_WARD_CORP_ID, corpID); // corp ID

        // Inserting Row
        db.insert(TABLE_WARD, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing new ward names in database
     * */
    public void addWardNew(String ward, String wardID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WARD_NAME, ward); // ward Name
        values.put(KEY_WARD_ID, wardID); // locality ID

        // Inserting Row
        db.insert(TABLE_WARD, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing chosen ward name in database
     * */
    public void addChosenWard(String wardID, String ward) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_CHOSEN_WARD);
        ContentValues values = new ContentValues();
        values.put(KEY_CHOSEN_WARD_ID, wardID); // ward ID
        values.put(KEY_CHOSEN_WARD_NAME, ward); // ward Name

        // Inserting Row
        db.insert(TABLE_CHOSEN_WARD, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Storing corporate names in database
     * */
    public void addCorporate(String corporate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CORPORATE_NAME, corporate); // Corporate Name

        // Inserting Row
        db.insert(TABLE_CORPORATE, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Storing deals in database
     * */
    public void addMydeals(String adid, String title, String qrcode, String expiry, String name, String address, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MYDEALS_ADID, adid);
        values.put(KEY_MYDEALS_TITLE, title);
        values.put(KEY_MYDEALS_QRCODE, qrcode);
        values.put(KEY_MYDEALS_EXPIRY, expiry);
        values.put(KEY_MYDEALS_NAME, name);
        values.put(KEY_MYDEALS_ADDRESS, address);
        values.put(KEY_MYDEALS_CONTACT, contact);

        // Inserting Row
        db.insert(TABLE_MYDEALS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("mobileno", cursor.getString(3));
            user.put("referral", cursor.getString(4));
            user.put("otp", cursor.getString(5));
            user.put("created_at", cursor.getString(6));
            user.put("password", cursor.getString(7));
            user.put("dob", cursor.getString(8));

            user.put("cnotification", cursor.getString(9));
            user.put("onotification", cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    /**
     * Getting state data from database
     *
     * @param */
    public HashMap<String, String> getStateNames(int id){
        HashMap<String,String> state = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_STATE + " WHERE id = "+id+"+1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            state.put("statename", cursor.getString(1));

        }
        cursor.close();
        db.close();
        // return state
        return state;
    }


    public String getLocalityID(String posItem, String stateID, String localgovID) {
        String selectQuery = "SELECT DISTINCT l.id, l.localityname, d.id "+
                " FROM state s "+
                " JOIN district d ON ( s.id = d.stateid ) "+
                " JOIN locality l ON ( l.districtid = d.id ) "+
                " JOIN ward w ON ( w.localityid = l.id ) "+
                " JOIN corporate c ON ( c.id = w.corpid ) "+
                " WHERE s.id =  "+stateID+"+1"+
                " AND c.id = "+localgovID+"+1" +
                " AND l.localityname = '"+posItem+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        String locality="";
        if(cursor.getCount() > 0){
            locality= cursor.getString(0);
        }
        cursor.close();
        db.close();
        // return locality
        return locality;
    }


    public String getLocalGovID(String corpname, String locality) {

        String selectQuery = "SELECT DISTINCT c.id , c.corporatename "+
                " FROM corporate c "+
                " JOIN ward w ON ( c.id = w.corpid ) "+
                " JOIN locality l ON ( w.localityid = l.id ) "+
                " WHERE l.id =  " +
                " (SELECT l.id FROM locality l " +
                " WHERE l.localityname = '"+ locality +"'" +
                " AND c.corporatename = '"+corpname+"')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        String localGovId="";
        if(cursor.getCount() > 0){
            localGovId= cursor.getString(0);
        }
        cursor.close();
        db.close();
        // return local gov
        return localGovId;
    }

    /**
     * Getting ward data from database
     *
     * @param */
    public HashMap<String, String> getCorporateNames(int id){
        HashMap<String,String> ward = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORPORATE + " WHERE id = "+id+"+1";



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            ward.put("corporatename", cursor.getString(1));

        }
        cursor.close();
        db.close();
        // return corporate
        return ward;
    }

    /**
     * Getting OTP status data from database
     * */
    public int getOtpStatus(){
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE "+KEY_OTP + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int user = cursor.getCount();
        cursor.close();
        db.close();
        // return user
        return user;
    }

    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Getting row count of state table
     * */
    public int getStateRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STATE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Getting row count of district table
     * */
    public int getDistrictRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DISTRICT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    /**
     * Getting row count of locality table
     * */
    public int getLocalityRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCALITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Getting row count of locality table
     * */
    public int getWardRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    /**
     * Getting row count of corporate table
     * */
    public int getCorporateRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CORPORATE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_MYDEALS, null, null);
        db.delete(TABLE_REQUEST,null,null);
        db.close();
    }

    //Insert OTP status in Login table
    public void OtpStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OTP, status);
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting ward data from database
     *
     * @param */
    public Cursor queryGetAllWard(String localityId) {
        String[] cols = { KEY_WARD_ID, KEY_WARD_NAME};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_WARD, cols,
                KEY_WARD_LOCALITY_ID + "=" + localityId, null, null, null, null);

        return c;

    }
    public Cursor queryGetAllWard() {
        String[] cols = { KEY_WARD_ID, KEY_WARD_NAME};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_WARD, cols,
                null, null, null, null, null);

        return c;

    }

    /**
     * Getting Wards if GPS data from database
     *
     * @param */
    public Cursor queryGetAllGPSWard(String gpsLocalgovID, String gpsLocalityID) {
        String[] cols = { KEY_WARD_ID, KEY_WARD_NAME};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_WARD, cols,
                KEY_WARD_CORP_ID + "=" + gpsLocalgovID + " AND " + KEY_WARD_LOCALITY_ID + " = " + gpsLocalityID, null, null, null, null);
        return c;
    }

    /**
     * Getting locality data from database
     *
     * @param */

    public Cursor queryGetAllLocality(String stateID, String localgovID) {
        String selectQuery = "SELECT DISTINCT l.id, l.localityname, d.id "+
                " FROM state s "+
                " JOIN district d ON ( s.id = d.stateid ) "+
                " JOIN locality l ON ( l.districtid = d.id ) "+
                " JOIN ward w ON ( w.localityid = l.id ) "+
                " JOIN corporate c ON ( c.id = w.corpid ) "+
                " WHERE s.id =  "+stateID+"+1" +
                " AND c.id = " + localgovID + "+1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    /**
     * Getting corporate data if GPS from database
     *
     * @param */

    public Cursor queryGetAllLocalGov(String locality) {
        String selectQuery = "SELECT DISTINCT c.id, c.corporatename "+
                " FROM corporate c "+
                " JOIN ward w ON ( c.id = w.corpid ) "+
                " JOIN locality l ON ( w.localityid = l.id ) "+
                " WHERE l.id =  " +
                " (SELECT l.id FROM locality l " +
                " WHERE l.localityname = '"+ locality +"')";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    /**
     * Getting locality id from database
     *
     * @param */
    public Cursor queryGetLocalityId(String locality) {
        String selectQuery = "SELECT l.id FROM locality l " +
                " WHERE l.localityname = '"+ locality+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }


    public Cursor getWardID(String ward) {
        String selectQuery = "SELECT w.id FROM ward w " +
                " WHERE w.wardname = '"+ ward +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getUserID() {
        String selectQuery = "SELECT "+KEY_ID+" FROM "+TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public Cursor getCurrentUserName() {
        String selectQuery = "SELECT "+KEY_NAME+" FROM "+TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getChosenWard() {
        String[] cols = { KEY_CHOSEN_WARD_ID, KEY_CHOSEN_WARD_NAME};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_CHOSEN_WARD, cols, null, null, null, null, null);
        return c;
    }

    public Cursor getUserMobile() {
        String selectQuery = "SELECT "+KEY_MOBILENO+" FROM "+TABLE_LOGIN+" WHERE "+KEY_MOBILENO + " is not null";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getPassword() {
        String selectQuery = "SELECT "+KEY_PASSWORD+" FROM "+TABLE_LOGIN+" WHERE "+KEY_PASSWORD+" is not null";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor getEmail() {
        String selectQuery = "SELECT "+KEY_EMAIL+" FROM "+TABLE_LOGIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public void updatePersonalInfo(String newName, String newDob, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,newName);
        cv.put(KEY_DOB,newDob);
        db.update(TABLE_LOGIN, cv, KEY_MOBILENO+"="+mobile, null);
    }

    public Cursor getUserEmail() {
        String selectQuery = "SELECT "+KEY_EMAIL+" FROM "+TABLE_LOGIN+" WHERE "+KEY_MOBILENO+" is not null";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }



    public void updatePassword(String newPass, String mobile) {
        //  String UpdateQuery= "UPDATE "+TABLE_LOGIN+" SET "+KEY_PASSWORD+" = '"+newPass+"' WHERE "+KEY_MOBILENO+ " = '"+mobile+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        //  db.rawQuery(UpdateQuery, null);
        ContentValues cv = new ContentValues();
        cv.put(KEY_PASSWORD, newPass);
        db.update(TABLE_LOGIN, cv, KEY_MOBILENO + "=" + mobile, null);
    }

    public void updateCorpNotification(String choice, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CNOTIFICATION, choice);
        db.update(TABLE_LOGIN, cv, KEY_MOBILENO + "=" +mobile , null);
    }

    public void updateOfferNotification(String choice, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ONOTIFICATION, choice);
        db.update(TABLE_LOGIN, cv, KEY_MOBILENO + "=" +mobile , null);
    }

    public int getCorpRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CORPORATOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    public String getFaqRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAQ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return String.valueOf(rowCount);
    }
    public int getCategoryRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getChildCategoryRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CHILD_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getGrandChildCatRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GRAND_CHILD_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public void addCategory(String id, String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID, id);
        values.put(KEY_CATEGORY_NAME, category);

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
    }
    public void addChildCategory(String id, String childCategory, String categoryID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHILD_CATEGORY_ID, id);
        values.put(KEY_CHILD_CATEGORY_NAME, childCategory); // District Name
        values.put(KEY_CHILDCAT_CATEGORY_ID, categoryID); // State Id

        // Inserting Row
        db.insert(TABLE_CHILD_CATEGORY, null, values);
        db.close(); // Closing database connection
    }

    public void addGrandChildCat(String id, String grandChildCat, String childCatID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GRAND_CHILD_CATEGORY_ID, id);
        values.put(KEY_GRAND_CHILD_CAT_NAME, grandChildCat); // locality Name
        values.put(KEY_GARNDCHILD_CHILDCATEGORY_ID, childCatID); // district ID

        // Inserting Row
        db.insert(TABLE_GRAND_CHILD_CATEGORY, null, values);
        db.close(); // Closing database connection
    }
    public int getCategoryID(String name) {
        int categoryid=0;
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY
                + " WHERE " + KEY_CATEGORY_NAME
                + " = '" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            categoryid= Integer.parseInt(cursor.getString(0));

        }
        cursor.close();
        db.close();
        // return state
        return categoryid;
    }
    public int getChildCategoryID(String name) {
        int categoryid=0;
        String selectQuery = "SELECT  * FROM " + TABLE_CHILD_CATEGORY
                + " WHERE " + KEY_CHILD_CATEGORY_NAME
                + " = '" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            categoryid= Integer.parseInt(cursor.getString(0));

        }
        cursor.close();
        db.close();
        // return state
        return categoryid;
    }

    public Cursor queryGetAllLChildCategory(int catID) {
        String selectQuery = "SELECT  * FROM " + TABLE_CHILD_CATEGORY
                + " WHERE " +KEY_CHILDCAT_CATEGORY_ID
                + " = " + catID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public void updateUserSettings(String name, String mobile, String c_notification, String o_notification, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_MOBILENO, mobile);
        cv.put(KEY_CNOTIFICATION, c_notification);
        cv.put(KEY_ONOTIFICATION, o_notification);
        db.update(TABLE_LOGIN, cv, KEY_EMAIL + "='" + email + "'", null);
    }

    public Cursor getCorpDetails() {
        String selectQuery="SELECT * FROM "+TABLE_CORPORATOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public Cursor getCorpDetails(String id) {
        String selectQuery="SELECT * FROM "+TABLE_CORPORATOR+ " WHERE "+KEY_CORPORATOR_LOGINID+" ='"+id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public int getChosenWardRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CHOSEN_WARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public void addPincode(String pincode) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PINCODE);

        ContentValues values = new ContentValues();
        values.put(KEY_CHOSEN_PINCODE, pincode); // ward ID

        // Inserting Row
        db.insert(TABLE_PINCODE, null, values);
        db.close(); // Closing database connection
    }

    public int getChosenPincodeRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PINCODE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getCompCount() {
        int compCount=0;
        String countQuery = "SELECT  * FROM " + TABLE_COMPLAINT_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        compCount = cursor.getCount();
        db.close();
        cursor.close();

        return compCount;
    }
    public Cursor getChosenPincode() {
        String[] cols = { KEY_CHOSEN_PINCODE};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_PINCODE, cols, null, null, null, null, null);
        return c;
    }

    public void setFollow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CORPORATOR_FOLLOW, follow);
        db.update(TABLE_CORPORATOR, cv, KEY_CORPORATOR_LOGINID + "='" + id + "'", null);
    }

    public void setUnfollow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CORPORATOR_FOLLOW, unfollow);
        db.update(TABLE_CORPORATOR, cv, KEY_CORPORATOR_LOGINID + "='" + id + "'", null);
    }
    public int getAvailabilityRowCount() {

        String countQuery = "SELECT  * FROM " + TABLE_AVAILABILITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void addAvailability(String id, String availability) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_AVAILABILITY_ID, id);
        values.put(COL_AVAILABILITY_NAME, availability);
        // Inserting Row
        db.insert(TABLE_AVAILABILITY, null, values);
    }

    public int getCatWiseAvailabilityRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CAT_WISE_AVAILABILITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void addCatWiseAvailability(String id, String availability) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CAT_WISE_AVAILABILITY_ID, id);
        values.put(COL_CAT_WISE_AVAILABILITY_NAME, availability);
        // Inserting Row
        db.insert(TABLE_CAT_WISE_AVAILABILITY, null, values);
    }

    public Cursor getAvailabilityName(String value) {
        String[] cols = { COL_AVAILABILITY_NAME};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_AVAILABILITY, cols, COL_AVAILABILITY_ID + "=?", new String[]{value}, null, null, null);
        return c;
    }

    public Cursor getFaqQuestion() {
        String FaqQuery = "SELECT  * FROM " + TABLE_FAQ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(FaqQuery, null);
        return cursor;
    }

    public void insertFaqTable(String q_id, String question, String answer) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION_ID, q_id);
        values.put(COL_QUESTION, question);
        values.put(COL_ANSWER, answer);
        db.insert(TABLE_FAQ, null, values);
        db.close();
    }

    public void updateFaqTable(String q_id, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(KEY_QUESTION_ID, q_id);
        cv.put(COL_QUESTION, question);
        cv.put(COL_ANSWER, answer);
        db.update(TABLE_FAQ, cv, KEY_QUESTION_ID + "='" + q_id + "'", null);
    }


    public Cursor getCompType() {
        String FaqQuery = "SELECT  * FROM " + TABLE_COMPLAINT_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(FaqQuery, null);
        return cursor;
    }

    public void addComplaintType(String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_COMPLAINT_TYPE, type); // State Name

        // Inserting Row
        db.insert(TABLE_COMPLAINT_TYPE, null, values);
        db.close(); // Closing database connection

    }


    public int getTimestampTableCount() {

        String countQuery = "SELECT  * FROM " + TABLE_TIMESTAMP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void addTimestampTable(String id, String availability, String category, String categoryOffers, String categoryWiseAvailability, String complaintType, String corporate, String corporator, String district, String faq, String locality, String grandChildCategory, String subcategory, String ward, String partyInfo, String states) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP_ID, id);
        values.put(KEY_TIMESTAMP_AVAILABILITY, availability);
        values.put(KEY_TIMESTAMP_CATEGORY, category);
        values.put(KEY_TIMESTAMP_CATEGORY_OFFERS, categoryOffers);
        values.put(KEY_TIMESTAMP_CATEGORY_WISE_AVAILABILITY, categoryWiseAvailability);
        values.put(KEY_TIMESTAMP_COMPLAINT_TYPE, complaintType);
        values.put(KEY_TIMESTAMP_CORPORATE, corporate);
        values.put(KEY_TIMESTAMP_CORPORATORS, corporator);
        values.put(KEY_TIMESTAMP_DISTRICTS, district);
        values.put(KEY_TIMESTAMP_FAQ, faq);
        values.put(KEY_TIMESTAMP_GRAND_CHILD_CATEGORY, grandChildCategory);
        values.put(KEY_TIMESTAMP_LOCALITY, locality);
        values.put(KEY_TIMESTAMP_PARTY_INFO, partyInfo);
        values.put(KEY_TIMESTAMP_STATE, states);
        values.put(KEY_TIMESTAMP_SUB_CATEGORY, subcategory);
        values.put(KEY_TIMESTAMP_WARD, ward);
        // Inserting Row
        db.insert(TABLE_TIMESTAMP, null, values);
    }

    public Cursor getTableTimestamp() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_TIMESTAMP, null, null, null, null, null, null);
        return c;
    }

    public void DropTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public void updateTimestampTable(String timestamp, String timestampKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(timestampKey, timestamp);
        db.update(TABLE_TIMESTAMP, cv, null, null);

    }

    public String getWardName(String wardId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WARD, null, KEY_WARD_ID + "=" + wardId, null, null, null, null);
        // Move to first row
        cursor.moveToFirst();
        String wardName="";
        if(cursor.getCount() > 0){
            wardName= cursor.getString(1);
        }
        cursor.close();
        db.close();
        return wardName;
    }

    public boolean checkAdIdInMydealsExists(String finalAdId) {
        String countQuery = "SELECT  * FROM " + TABLE_MYDEALS + " WHERE " + KEY_MYDEALS_ADID + " = '" + finalAdId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        if(rowCount>0){
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteAdidFromMydeals(String finalAdId) {
        String deleteQuery = "DELETE FROM " + TABLE_MYDEALS + " WHERE " + KEY_MYDEALS_ADID + " = '" + finalAdId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery(deleteQuery, null);
        System.out.println("ADID deleted from mydeals table");
    }

    public int getMyDealsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MYDEALS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public HashMap<String, String> getMyDealsData(int i) {
        HashMap<String,String> MyDeals = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MYDEALS+ " WHERE " + KEY_MYDEALS_ID + " = "+i+"+1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            MyDeals.put("ADID", cursor.getString(1));
            MyDeals.put("TITLE", cursor.getString(2));
            MyDeals.put("QRCODE", cursor.getString(3));
            MyDeals.put("EXPIRY", cursor.getString(4));
            MyDeals.put("SHOPNAME", cursor.getString(5));
            MyDeals.put("SHOPADDRESS", cursor.getString(6));
            MyDeals.put("SHOPCONTACT", cursor.getString(7));

        }
        cursor.close();
        db.close();
        // return state
        return MyDeals;
    }

    public HashMap<String,String> getMyDealsFromTitle(String itemAtPosition) {
        HashMap<String,String> MyDeals = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MYDEALS + " WHERE " + KEY_MYDEALS_TITLE + " ='"+itemAtPosition + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            MyDeals.put("ADID", cursor.getString(1));
            MyDeals.put("TITLE", cursor.getString(2));
            MyDeals.put("QRCODE", cursor.getString(3));
            MyDeals.put("EXPIRY", cursor.getString(4));
            MyDeals.put("SHOPNAME", cursor.getString(5));
            MyDeals.put("SHOPADDRESS", cursor.getString(6));
            MyDeals.put("SHOPCONTACT", cursor.getString(7));
        }
        cursor.close();
        db.close();
        // return state
        return MyDeals;
    }

    public void addRequested(String yes, String finalWard_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REQUEST_STATUS, yes); // status
        values.put(KEY_REQUESTED_WARD, finalWard_id); // ward ID

        // Inserting Row
        db.insert(TABLE_REQUEST, null, values);
        db.close(); // Closing database connection
    }

    public int getRequestTableCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REQUEST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public boolean isAlreadyRequested(String chosenward_id) {
        String countQuery = "SELECT  * FROM " + TABLE_REQUEST + " WHERE " + KEY_REQUESTED_WARD + " = '" + chosenward_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        if(rowCount>0){
            return true;
        }
        else {
            return false;
        }
    }


    public void addContacts(String name, String phone, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DEVICE_CONTACTS_NAME, name);
        values.put(KEY_DEVICE_CONTACTS_PHONE, phone);
        values.put(KEY_DEVICE_CONTACTS_STATUS, status);

        // Inserting Row
        db.insert(TABLE_DEVICE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    public boolean checkNumberInContactsExists(String phone) {
        String countQuery = "SELECT  * FROM " + TABLE_DEVICE_CONTACTS + " WHERE " + KEY_DEVICE_CONTACTS_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        if(rowCount>0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> contactList;
        contactList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM "+TABLE_DEVICE_CONTACTS
                +" where "+KEY_DEVICE_CONTACTS_STATUS+" = '"+"0"+"'";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(KEY_DEVICE_CONTACTS_NAME,cursor.getString(1));
                map.put(KEY_DEVICE_CONTACTS_PHONE, cursor.getString(2));

                contactList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(contactList);
    }

    public void updateSyncStatus(String phone, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update "+TABLE_DEVICE_CONTACTS+" set "+KEY_DEVICE_CONTACTS_STATUS+" = '"+ status +"' where "+KEY_DEVICE_CONTACTS_PHONE+" = "+"'"+ phone +"'";
        database.execSQL(updateQuery);
        database.close();
    }

}
