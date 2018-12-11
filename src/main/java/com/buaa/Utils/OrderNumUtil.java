package com.buaa.Utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单编号
 */
public class OrderNumUtil {
    public static String genOrderNum()
    {
        QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

        //得到当前日期，根据日期查询是否有订单。
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String date = sdf.format(now);

        StringBuffer sb = new StringBuffer(date.replace("-",""));

        Integer num;
        try {
            num = (Integer) qr.query("select num from ordernum where prefix=?", new ScalarHandler<>(1), date);
            //如果没查到，说明当前没有订单，将订单号设置为1，表示当天的第一个订单；如果查到，就将订单号加一。
            if(num == null)
            {
                num = new Integer(1);
                qr.update("insert into ordernum(prefix,num) values(?,?)",date, num);
            }
            else
            {
                num = num + 1;
                qr.update("update ordernum set num=? where prefix=?",num, date);
            }
            int numLength = num.toString().length();
            //为什么是11？？？？？
            for(int i = 0; i < 11-numLength; i++)
            {
                sb.append("0");    //补零。
            }
            sb.append(num);
//            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
