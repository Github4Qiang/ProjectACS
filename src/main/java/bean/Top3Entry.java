package bean;

import java.util.Date;

/**
 * Created by sky on 2017/3/15.
 */
public class Top3Entry {
    private String province;
    private int adId;
    private Date date;
    private Long count;

    public Top3Entry(String province, int adId, Date date, Long count) {
        this.province = province;
        this.adId = adId;
        this.date = date;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
}
