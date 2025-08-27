package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
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

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO department (Name) VALUES (?)";

        try {
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());

            int rowCreated = ps.executeUpdate();

            if (rowCreated > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    department.setId(rs.getInt(1));
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
    public void update(Department department) {
        PreparedStatement ps = null;

        String sql = "UPDATE department " +
                "SET Name = ? " +
                "WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());

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

        String sql = "DELETE FROM department " +
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
    public Department getById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select department.* " +
                     "FROM department " +
                     "WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()){
                Department dep = initDepartment(rs);
                return dep;
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
    public List<Department> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select department.* " +
                     "FROM department " +
                     "ORDER BY Id";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            List<Department> departments = new ArrayList<>();

            while (rs.next()){
                Department dep = initDepartment(rs);
                departments.add(dep);
            }

            return departments;

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
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
