package bean;

import java.util.Date;

/**
 * Created by sky on 2017/3/15.
 */
public class MintEntry {

    private Date minute;
    private int adId;
    private long count;

    public MintEntry() {
    }

    public MintEntry(Long minute, int adId, long count) {
        this.minute = new Date(minute);
        this.adId = adId;
        this.count = count;
    }

    public Date getMinute() {
        return minute;
    }

    public void setMinute(Date minute) {
        this.minute = minute;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
