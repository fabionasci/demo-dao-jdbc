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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowCreated = ps.executeUpdate();

            if (rowCreated > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    seller.setId(rs.getInt(1));
                }
            } else {
                throw new DbException("Insert failed");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement ps = null;

        String sql = "UPDATE seller " +
                     "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                     "WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        String sql = "DELETE FROM seller " +
                     "WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
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
                Department dep = initDepartment(rs);
                Seller s = initSeller(rs, dep);
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                "ORDER BY seller.Id";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> depMap = new HashMap<>();

            while (rs.next()){
                // Map para definir se Department já foi recuperado 1 vez
                Department dep = depMap.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = initDepartment(rs);
                    depMap.put(rs.getInt("DepartmentId"), dep);
                }

                Seller s = initSeller(rs, dep);
                sellers.add(s);
            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> getByDepartmentId(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                "WHERE seller.DepartmentId = ? " +
                "ORDER BY seller.Name";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> depMap = new HashMap<>();

            while (rs.next()){
                // Map para definir se Department já foi recuperado 1 vez
                Department dep = depMap.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = initDepartment(rs);
                    depMap.put(rs.getInt("DepartmentId"), dep);
                }

                Seller s = initSeller(rs, dep);
                sellers.add(s);
            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }


    private Department initDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller initSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setDepartment(dep);
        return seller;
    }
}
