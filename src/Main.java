import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;

        final String QUERY = "";
        final String QUERY2 = "";

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}