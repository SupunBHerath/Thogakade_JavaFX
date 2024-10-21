package util;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql,Object... args) throws SQLException {
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0;i<args.length;i++){
            psTm.setObject(i+1,args[i]);
        }

        if(sql.startsWith("SELECT")||sql.startsWith("select")){
            return (T) psTm.executeQuery();
        }
        return (T) (Boolean) (psTm.executeUpdate()>0);
    }

//    public void sum(Integer... numbers){
//        for (int i =0;i<numbers.length;i++){
//            System.out.println(numbers[i]);
//        }
//    }
//
//
//    CrudUtil(){
//        sum(10,10,20,30,60,50,40,80,90,90,100,101,102,103,106,105);
//        sum(10,10,20,30,60,50,40,80,90,90,100,101,102,103,106,105,200,50,30,60,50,0,80,80);
//    }
}
