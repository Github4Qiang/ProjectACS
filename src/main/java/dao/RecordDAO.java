package dao;

import bean.RecordEntry;
import jdbc.JDBCHelper;

/**
 * Created by sky on 2017/3/16.
 */
public class RecordDAO {
    //添加一条原始记录
    public static void insert(RecordEntry recordEntry) {
        String sql = "INSERT INTO recordraw(time, userId, adId, city, count) VALUES(?, ?, ?, ?, ?)";
        Object[] params = {recordEntry.getTime(), recordEntry.getUserId(),
                recordEntry.getAdId(), recordEntry.getCity(), recordEntry.getCount()};
        if (!JDBCHelper.getInstanse().executeInsert(sql, params)){
            update(recordEntry);
        }
    }

    public static void update(RecordEntry recordEntry) {
        String sql = "UPDATE recordraw SET count=? WHERE time=? AND userId=? AND adId=? AND city=?";
        Object[] params = {recordEntry.getCount(), recordEntry.getTime(), recordEntry.getUserId(), recordEntry
                .getAdId(), recordEntry.getCity()};
        JDBCHelper.getInstanse().executeUpdate(sql, params);
    }

}
