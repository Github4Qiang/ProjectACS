package bean;

import java.util.Date;

/**
 * Created by sky on 2017/3/15.
 *
 * @author zhihong_shee
 *         实时计算各省各城市广告点击量，时时更新到数据库
 */
public class ResultEntry {
    private Date date;
    private String city;
    private int adId;
    private Long count;

    public ResultEntry() {
    }

    public ResultEntry(Date date, String city, int adId, Long count) {
        this.date = date;
        this.city = city;
        this.adId = adId;
        this.count = count;
    }

    public ResultEntry(Long date, String city, int adId, Long count) {
        this.date = new Date(date);
        this.city = city;
        this.adId = adId;
        this.count = count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
