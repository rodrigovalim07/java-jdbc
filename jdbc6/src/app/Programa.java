package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Programa {

	public static void main(String[] args) {

		Connection conn = null;
		
		Statement st = null;
		
		try {
			conn = DB.getConnection();

			conn.setAutoCommit(false);
			
			st = conn.createStatement();
					
			int linhas1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*int x = 1;	testando o rollback
			if (x < 2) {
				throw new SQLException("falso erro");
			}*/
			
			int linhas2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit();
			
			System.out.println("linhas DepartmentId = 1 afetadas: " + linhas1);
			System.out.println("linhas DepartmentId = 2 afetadas: " + linhas2);
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transação revertida. Causa do erro: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Erro tentando reverter a operação. Causa do erro: " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}
}
