package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Main {

    public static void main(String[] args) {


        Department d = new Department(1, "Books");
        System.out.println(d);

        Seller s = new Seller(1, "Bob", "bobmail@gmial.com", new Date(), 3000.0);
        System.out.println(s);


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