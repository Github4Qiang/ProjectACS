package bean;


import java.sql.Timestamp;


/**
 * Created by sky on 2017/3/15.
 */
public class RecordEntry {
    private Timestamp time;
    private String city;
    private int userId;
    private int adId;
    private long count;

    public RecordEntry() {
    }

    public RecordEntry(Timestamp time, String city, int userId, int adId, long count) {
        this.time = time;
        this.city = city;
        this.userId = userId;
        this.adId = adId;
        this.count = count;
    }

    public RecordEntry(Long time, String city, int userId, int adId, long count) {
        this.time = new Timestamp(time);
        this.city = city;
        this.userId = userId;
        this.adId = adId;
        this.count = count;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
