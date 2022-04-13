package datalink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Salary;

/**
 *
 * @author Saleh
 */
public class CRUDSalary {

    private static Connection conn;

    public static boolean create(Salary salary) {

        int insert = 0;

        try {
            String sql = "INSERT INTO salaries ("
                    + "`employee_id`, `subject_year_month`, `date_given`,`agreed_salary` ,`payable`)"
                    + " VALUES (?, ?, ?, ?, ?)";
            conn = Connect.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setInt(1, salary.getEmployeeId());
            p.setObject(2, salary.getYearMonthSubject());
            p.setObject(3, salary.getDateGiven());
            p.setBigDecimal(4, salary.getAgreedSalary());
            p.setBigDecimal(5, salary.getPayable());
            insert = p.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDSalary.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connect.cleanUp();
        }
        return insert > 0;
    }

    public static Salary getById(int id) {
        throw new UnsupportedOperationException("CRUDSalary getById placeholder");
    }

    public static boolean update(Salary salaryAdvance) {
        throw new UnsupportedOperationException("CRUDSalary update placeholder");
    }

    public static boolean delete(Integer id) {
        throw new UnsupportedOperationException("CRUDSalary delete placeholder");
    }

}
