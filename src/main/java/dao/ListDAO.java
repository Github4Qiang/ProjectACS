package dao;

import bean.ListEntry;
import jdbc.JDBCHelper;
import utils.DateTimeUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sky on 2017/3/15.
 *
 * @author zhihong_shee
 *         DAO是data Access object数据访问接口。于数据库打交道，夹在业务逻辑和数据库资源之间
 */
public class ListDAO implements JDBCHelper.QueryCallback, Serializable {

    private ArrayList<ListEntry> entries;

    public static final String USER_ID = "userId";
    public static final String AD_ID = "adId";
    public static final String DATE = "date";

    //添加一条黑名单记录
    public static void insert(ListEntry listentry) {
        String sql = "INSERT INTO blacklist(userId, adId, date) values(?, ?, ?)";
        Object[] params = {listentry.getUserId(), listentry.getAdId(), listentry.getDate()};
        JDBCHelper.getInstanse().executeInsert(sql, params);
    }

    // 获取所有黑名单列表
    public ArrayList<ListEntry> getAllBlackList() {
        entries = new ArrayList<>();
        String sql = "SELECT * FROM blacklist";
        JDBCHelper.getInstanse().executeQuery(sql, null, this);
        return this.entries;
    }

    // 查询黑名单中是否存在该记录
    // 存在：  FALSE
    // 不存在：TRUE
    public boolean existInBlackList(ListEntry listEntry) {
        entries = new ArrayList<>();
        String sql = "SELECT * FROM blacklist WHERE userId=? AND adId=? AND date BETWEEN ? AND ?";
        Object[] params = {listEntry.getUserId(),
                listEntry.getAdId(),
                new Date(DateTimeUtils.roundDate(listEntry.getDate().getTime())),
                new Date(DateTimeUtils.roundDate(DateTimeUtils.tomorrow(listEntry.getDate().getTime())))};
        JDBCHelper.getInstanse().executeQuery(sql, params, this);
        return entries.size() != 0;
    }

    @Override
    public void process(ResultSet rs) throws Exception {
        while (rs.next()) {
            ListEntry entry = new ListEntry();
            entry.setUserId(rs.getInt(USER_ID));
            entry.setAdId(rs.getInt(AD_ID));
            entry.setDate(rs.getDate(DATE));
            entries.add(entry);
        }
    }
}