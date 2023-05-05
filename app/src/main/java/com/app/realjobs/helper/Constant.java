package com.app.realjobs.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Constant {

   public static final String MainBaseUrl = "https://realjobs.graymatterworks.com/";

    public static final String BaseUrl = MainBaseUrl + "api/";
    public static final String LOGIN_URL = BaseUrl + "login.php";
    public static final String SEND_ADMIN_NOTIFY_URL = BaseUrl + "sendadminnotify.php";
    public static final String REALJOBSLIST = BaseUrl + "realjobslist.php";
    public static final String REGISTER_URL = BaseUrl + "register.php";
    public static final String FAKEJOBSLIST = BaseUrl + "fakejobslist.php";
    public static final String USER_DETAILS = BaseUrl + "user_details.php";

    public static final String CHECKFAKEJOBS = BaseUrl + "checkfakejobs.php";
    public static final String CHECKFAKELIST = BaseUrl + "checkfakelist.php";
    public static final String PAYMENTS = BaseUrl + "payments.php";
    public static final String PAYMENT_STATUS_URL = BaseUrl + "payment_status.php";
    public static final String UPDATE_USER_DETAILS = BaseUrl + "update_user_details.php";





    public static final String RATINGS_URL = BaseUrl + "ratings.php";

    public static final String SETTINGS_URL = BaseUrl + "settings.php";
    public static final String AUTHORIZATION = "Authorization";
    public static final String AccessKey = "accesskey";
    public static final String AccessKeyVal = "90336";
    public static final String JWT_KEY = "Abcd@2022";



    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String MOBILE = "mobile";
    public static final String IMAGE = "image";
    public static final String PAYMENT_STATUS = "payment_status";

    public static final String TYPE = "type";
    public static final String SUPPORT = "support";
    public static final String TIMESTAMP = "timestamp";
    public static final String EARN = "earn";
    public static final String WITHDRAWAL = "withdrawal";
    public static final String TOTAL_REFERRALS = "total_referrals";
    public static final String LAST_UPDATED_SETTINGS_DATE = "last_updated_settings_date";
    public static final String LAST_UPDATED_DATE_SETTINGS = "last_updated_date_settings";
    public static final String RISE_TICKET_STATUS = "rise_ticket_status";
    public static final String FCM_ID = "fcm_id";
    public static final String TASK_TYPE = "task_type";
    public static final String TRIAL_EXPIRED = "trial_expired";
    public static final String CHAMPION_TASK_ELIGIBLE = "champion_task_eligible";
    public static final String 	TRIAL_COUNT = "trial_count";



    public static final String 	MCG_TIMER = "mcg_timer";
    public static final String 	SECURITY = "security";
    public static final String 	CLOSED_JOINING = "closed_joining";
    public static final String 	FOLLOWUP_TICKET = "followup_ticket";
    public static final String 	EMP_NAME = "emp_name";



    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";
    public static final String REFER_CODE = "refer_code";
    public static final String TODAY_CODES = "today_codes";
    public static final String TOTAL_CODES = "total_codes";


    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String REFUND_WALLET = "refund_wallet";

    public static final String DESCRIPTION = "description";

    public static final String SCREENSHOT = "screenshot";

    public static final String DATE = "date";
    public static final String DOB = "dob";


    public static final String CITY = "city";
    public static final String PASSWORD = "password";
    public static final String DEVICE_ID = "device_id";
    public static final String STATUS = "status";
    public static final String REFERRED_BY = "referred_by";
    public static final String REFER_BONUS_SENT = "refer_bonus_sent";
    public static final String CODE_GENERATE = "code_generate";
    public static final String CHAT_SUPPORT = "chat_support";
    public static final String CODE_GENERATE_TIME = "code_generate_time";
    public static final String PENDING_TICKET = "pending_ticket";
    public static final String JOINING_TICKET = "joining_ticket";
    public static final String CLOSED_TICKET = "closed_ticket";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String LAST_UPDATED = "last_updated";
    public static final String JOINED_DATE = "joined_date";
    public static final String REFER_BALANCE = "refer_balance";
    public static final String WITHDRAWAL_STATUS = "withdrawal_status";
    public static final String ONGOING_SA_BALANCE = "ongoing_sa_balance";
    public static final String SALARY_ADVANCE_BALANCE = "salary_advance_balance";
    public static final String SA_REFER_COUNT = "sa_refer_count";
    public static final String TOTAL_REFUND = "total_refund";
    public static final String PLACE = "place";
    public static final String SKILLS = "skills";
    public static final String WORKING_EXPERIENCE = "working_experience";



    @SuppressLint("HardwareIds")
    public static final  String getDeviceId(Activity activity) {
        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            // Toast.makeText(context,  deviceId, Toast.LENGTH_SHORT).show();

        } else {

            final TelephonyManager mTelephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);


            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                //  Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();
            }
        }

        return deviceId;

    }
    public static final String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
    public static final  String getHistoryDays(String joinDate) {
        Date userDob = null;
        int numOfDays = 0;
        Date today = new Date();
        try {
            userDob = new SimpleDateFormat("yyyy-MM-dd").parse(joinDate);
            long diff =  today.getTime() - userDob.getTime();
            int numOfYear = (int) ((diff / (1000 * 60 * 60 * 24))/365);
            numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) (diff / (1000 * 60));
            int seconds = (int) (diff / (1000));


        } catch (ParseException e) {
            e.printStackTrace();
        }




        return ("" + (int) numOfDays);
    }
}