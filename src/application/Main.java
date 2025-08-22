package application;

import db.DB;
import db.DbException;
import model.entities.Department;

import java.sql.*;

public class Main {

    public static void main(String[] args) {


        Department d = new Department(1, "Books");
        System.out.println(d);

//        Connection conn = null;
//        Statement st = null;
//
//        final String QUERY = "";
//        final String QUERY2 = "";
//
//        try {
//            conn = DB.getConnection();
//            conn.setAutoCommit(false);
//
//        } catch (SQLException e) {
//            throw new DbException(e.getMessage());
//        }
//        finally {
//            DB.closeStatement(st);
//            DB.closeConnection();
//        }
    }
}