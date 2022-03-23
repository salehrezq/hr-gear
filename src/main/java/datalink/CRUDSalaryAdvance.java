package datalink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SalaryAdvance;

/**
 *
 * @author Saleh
 */
public class CRUDSalaryAdvance {

    private static Connection conn;

    public static boolean create(SalaryAdvance salaryAdvance) {

        int insert = 0;

        try {
            String sql = "INSERT INTO salary_advances ("
                    + "`employee_id`, `subject_year_month`, `date_taken`, `amount`)"
                    + " VALUES (?, ?, ?, ?)";
            conn = Connect.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setInt(1, salaryAdvance.getEmployeeId());
            p.setObject(2, salaryAdvance.getYearMonthSubject());
            p.setObject(3, salaryAdvance.getDateTaken());
            p.setBigDecimal(4, salaryAdvance.getAmount());
            insert = p.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDSalaryAdvance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connect.cleanUp();
        }
        return insert > 0;
    }

    public static SalaryAdvance getById(int id) {

        SalaryAdvance salaryAdvance = null;
        try {
            String sql = "SELECT * FROM `salary_advances` WHERE id = ? LIMIT 1";
            conn = Connect.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet result = p.executeQuery();

            while (result.next()) {
                salaryAdvance = new SalaryAdvance();
                salaryAdvance.setId(id);
                salaryAdvance.setEmployeeId(result.getInt("employee_id"));
                salaryAdvance.setYearMonthSubject(result.getObject("subject_year_month", LocalDate.class));
                salaryAdvance.setDateTaken(result.getObject("date_taken", LocalDate.class));
                salaryAdvance.setAmount(result.getBigDecimal("amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSalaryAdvance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connect.cleanUp();
        }
        return salaryAdvance;
    }

    public static List getSalaryAdvancesRecordByEmployeeByMonth(int employeeID, YearMonth ym) {

        List<SalaryAdvance> salaryAdvancesList = new ArrayList<>();

        try {
            LocalDate firstOfThisMonth = ym.atDay(1);
            LocalDate firstOfNextMonth = ym.plusMonths(1).atDay(1);

            String sql = "SELECT * FROM `salary_advances`"
                    + " WHERE `employee_id` = ? AND `subject_year_month` >= ? AND `subject_year_month` < ?"
                    + " ORDER BY `date_taken` ASC";

            conn = Connect.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setInt(1, employeeID);
            p.setObject(2, firstOfThisMonth);
            p.setObject(3, firstOfNextMonth);

            ResultSet result = p.executeQuery();

            while (result.next()) {

                SalaryAdvance salaryAdvance = new SalaryAdvance();
                salaryAdvance.setId(result.getInt("id"));
                salaryAdvance.setEmployeeId(result.getInt("employee_id"));
                salaryAdvance.setYearMonthSubject(result.getObject("subject_year_month", LocalDate.class));
                salaryAdvance.setDateTaken(result.getObject("date_taken", LocalDate.class));
                salaryAdvance.setAmount(result.getBigDecimal("amount"));
                salaryAdvancesList.add(salaryAdvance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSalaryAdvance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connect.cleanUp();
        }
        return salaryAdvancesList;
    }

    public static boolean update(SalaryAdvance salaryAdvance) {

        int update = 0;

        try {
            String sql = "UPDATE `salary_advances` "
                    + "SET "
                    + "`subject_year_month`= ?, "
                    + "`date_taken` = ?, "
                    + "`amount` = ? "
                    + "WHERE `id` = ?";
            conn = Connect.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setObject(1, salaryAdvance.getYearMonthSubject());
            p.setObject(2, salaryAdvance.getDateTaken());
            p.setBigDecimal(3, salaryAdvance.getAmount());
            p.setInt(4, salaryAdvance.getId());

            update = p.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDSalaryAdvance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connect.cleanUp();
        }
        return update > 0;
    }

}
