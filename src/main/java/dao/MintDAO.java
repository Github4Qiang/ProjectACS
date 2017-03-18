package dao;

import bean.MintEntry;
import jdbc.JDBCHelper;

/**
 * Created by sky on 2017/3/16.
 */
public class MintDAO {

    //    添加一条广告点击记录
    public static void insert(MintEntry mintEntry) {
        String sql = "INSERT INTO recordpm(time, adId, count) values(?, ?, ?)";
        Object[] params = {mintEntry.getMinute(), mintEntry.getAdId(), mintEntry.getCount()};
        if (!JDBCHelper.getInstanse().executeInsert(sql, params)){
            update(mintEntry);
        }
    }

    public static void update(MintEntry mintEntry) {
        String sql = "UPDATE recordpm SET count=? WHERE time=? AND adId=?";
        Object[] params = {mintEntry.getCount(), mintEntry.getMinute(), mintEntry.getAdId()};
        JDBCHelper.getInstanse().executeUpdate(sql, params);
    }
}
