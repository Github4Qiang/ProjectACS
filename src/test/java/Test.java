import bean.ListEntry;
import dao.ListDAO;
import utils.DateTimeUtils;

import java.util.Date;

/**
 * Created by sky on 2017/3/16.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ListEntry listEntry = new ListEntry();
        listEntry.setUserId(3);
        listEntry.setAdId(3);
        listEntry.setDate(new Date(DateTimeUtils.getTodayRoundTime()));
//
        System.out.println(new Date(DateTimeUtils.getTodayRoundTime()));
//
        System.out.println(new ListDAO().getAllBlackList());
//
        System.out.println(new ListDAO().existInBlackList(listEntry));
    }

}
