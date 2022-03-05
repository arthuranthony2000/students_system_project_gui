package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.StudentDao;
import model.entities.Student;

public class StudentDaoJDBC implements StudentDao {

	private Connection conn;

	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tbl_student " +
							"(pk_number, name_student, course, nota1, nota2, nota3, nota4) " +
							"VALUES " +
							"(?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getNumber());
			st.setString(2, obj.getName());
			st.setString(3, obj.getCourse());
			st.setFloat(4, obj.getNota1());
			st.setFloat(5, obj.getNota2());
			st.setFloat(6, obj.getNota3());
			st.setFloat(7, obj.getNota4());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setNumber(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE tbl_student " +
				"SET pk_number = ?, name_student = ?, course = ?, nota1 = ?, " +
				 "nota2 = ?, nota3 = ?, nota4 = ?" +
				"WHERE pk_number = ?");

			st.setInt(1, obj.getNumber());
			st.setString(2, obj.getName());
			st.setString(3, obj.getCourse());
			st.setFloat(4, obj.getNota1());
			st.setFloat(5, obj.getNota2());
			st.setFloat(6, obj.getNota3());
			st.setFloat(7, obj.getNota4());

			st.setInt(8, obj.getNumber());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM tbl_student WHERE pk_number = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch(SQLException e){
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Student findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbl_student WHERE pk_number = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Student obj = new Student();
				obj.setNumber(rs.getInt("pk_number"));
				obj.setName(rs.getString("name_student"));
				obj.setCourse(rs.getString("course"));
				obj.setNota1(rs.getFloat("nota1"));
				obj.setNota2(rs.getFloat("nota2"));
				obj.setNota3(rs.getFloat("nota3"));
				obj.setNota4(rs.getFloat("nota4"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Student> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM tbl_student ORDER BY pk_number");
			rs = st.executeQuery();

			List<Student> list = new ArrayList<Student>();

			while (rs.next()) {
				Student obj = new Student(rs.getInt("pk_number"), rs.getString("name_student"),
						rs.getString("course"), rs.getFloat("nota1"), rs.getFloat("nota2"),
						rs.getFloat("nota3"), rs.getFloat("nota4"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
