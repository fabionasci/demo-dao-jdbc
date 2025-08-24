package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller getById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select seller.*, department.Name as DepName " +
                     "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                     "WHERE seller.Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));

                Seller s = new Seller();
                s.setId(rs.getInt("Id"));
                s.setName(rs.getString("Name"));
                s.setEmail(rs.getString("Email"));
                s.setBirthDate(rs.getDate("BirthDate"));
                s.setBaseSalary(rs.getDouble("BaseSalary"));
                s.setDepartment(dep);

                return s;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> getAll() {
        return List.of();
    }
}
