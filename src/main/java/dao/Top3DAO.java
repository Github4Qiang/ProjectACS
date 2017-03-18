package dao;

import bean.Top3Entry;
import jdbc.JDBCHelper;

/**
 * Created by sky on 2017/3/16.
 */
public class Top3DAO {
    //添加top3一条记录
    public static void insert(Top3Entry top3Entry) throws Exception {
        String sql = "INSERT INTO top3pp(province, adId, date, count) VALUES(?,?,?,?)";
        Object[] params = {top3Entry.getProvince(), top3Entry.getAdId(), top3Entry.getDate(), top3Entry.getCount()};
        if (!JDBCHelper.getInstanse().executeInsert(sql, params)) {
            update(top3Entry);
        }
    }

    //更新一条top3记录
    public static void update(Top3Entry top3Entry) {
        String sql = "UPDATE top3pp SET count=? WHERE date=? AND province=? AND adId=?";
        Object[] params = {top3Entry.getCount(), top3Entry.getDate(), top3Entry.getProvince(), top3Entry.getAdId()};
        JDBCHelper.getInstanse().executeUpdate(sql, params);
    }
}
