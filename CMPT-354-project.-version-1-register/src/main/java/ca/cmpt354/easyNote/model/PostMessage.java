package ca.cmpt354.easyNote.model;

import ca.cmpt354.easyNote.dao.BasicDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PostMessage {
    private int postID;
    private int userID;
    private String message;
    private String author;
    private Date time_stamp;

    // https://blog.csdn.net/cdn1998/article/details/84534685?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control
    // https://blog.csdn.net/achenyuan/article/details/88733005
    private Date getSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date res  = null;

        try {
            res = simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            res = null;
            BasicDao.printErrorMessage("PostMessage", "getSystemTime()");
            e.printStackTrace();
        } finally {
            date = null;
        }

        return res;
    }

    public PostMessage() {

    }

    public PostMessage(int postID, int userID, String message, String author, Date time_stamp) {
        this.postID = postID;
        this.userID = userID;
        this.message = message;
        this.author = author;
        this.time_stamp = time_stamp;
    }

    public PostMessage(int postID, int userID, String message, String author) {
        this.postID = postID;
        this.userID = userID;
        this.message = message;
        this.author = author;
        this.time_stamp = getSystemTime();
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTimestamp() {
        return time_stamp;
    }

    // timeStamp cannot be change once this object is created
    public void setAll(int postID, int userID, String massage, String author) {
        this.postID = postID;
        this.userID = userID;
        this.message = massage;
        this.author = author;
    }

    @Override
    public String toString() {
        return ("PostMessage("+utc2Local(time_stamp)+", "+postID+", "+userID+", "+author+", "+message+", "+")");
    }

    // using time format: "yyyy-MM-dd HH:mm:ss"
    public String utc2Local(Date utcDate) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date utcTime = null;
        try {
            String utcTimeInString = timeFormat.format(utcDate);
            utcTime = timeFormat.parse(utcTimeInString);
        } catch (ParseException e) {
            BasicDao.printErrorMessage("PostMessage", "utc2Local(String utcTimeInString)");
            e.printStackTrace();
        }

        timeFormat.setTimeZone(TimeZone.getDefault());
        assert utcTime != null;
        return timeFormat.format(utcTime.getTime());
    }

    public String getTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getDefault());

        return timeFormat.format(time_stamp);
    }
}
