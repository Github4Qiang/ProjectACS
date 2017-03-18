package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sky on 2017/3/15.
 *
 * @author zhihong_shee
 *         黑名单表：对某个广告点击超过100次，记为“黑名单用户”，该表用于该统计
 *         需求3
 *         <p>
 *         bean包就是专门放置属性类的，如在数据库中创建一个表，那么你可以把该表每个字段，
 *         定义成属性放置在一个类中，声明setter和getter方法，并把该类放于bean包下
 */
public class ListEntry implements Serializable{

    private Date date;
    private int userId;
    private int adId;

    public ListEntry() {}

    public ListEntry(Date date, int userId, int adId) {
        this.date = date;
        this.userId = userId;
        this.adId = adId;
    }

    public ListEntry(Long date, int userId, int adId) {
        this.date = new Date(date);
        this.userId = userId;
        this.adId = adId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ListEntry{" +
                "date=" + date +
                ", userId=" + userId +
                ", adId=" + adId +
                '}';
    }
}
