package dao;

import bean.ResultEntry;
import jdbc.JDBCHelper;

/**
 * Created by sky on 2017/3/16.
 */
public class ResultDAO {
    //增加一条结果记录
    public static void insert(ResultEntry resultEntry) {
        String sql = "INSERT INTO resultpca(city, adId, date, count) VALUES(?, ?, ?, ?)";
        Object[] params = {resultEntry.getCity(), resultEntry.getAdId(), resultEntry.getDate(), resultEntry.getCount()};
        if (!JDBCHelper.getInstanse().executeInsert(sql, params)){
            update(resultEntry);
        }
    }

    //更新一条记录
    public static void update(ResultEntry resultEntry) {
        String sql = "UPDATE resultpca SET count=? WHERE city=? AND adId=? AND date=?";
        Object[] params = {resultEntry.getCount(), resultEntry.getCity(), resultEntry.getAdId(), resultEntry.getDate()};
        JDBCHelper.getInstanse().executeUpdate(sql, params);
    }


}
