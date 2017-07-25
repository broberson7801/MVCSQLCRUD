package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentPairDAO_Impl implements StudentPairDAO {
	private int groupSize;
	private List<Student> studentList;
	private Map<Integer, List<Student>> studentPairs;
	private static String url = "jdbc:mysql://localhost:3306/studentpairdb";
	private String user = "studentuser";
	private String pass = "studentuser";

	public StudentPairDAO_Impl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error loading MySQL Driver!!!");
		}
	}

	public void setStudentPairs(Map<Integer, List<Student>> studentPairs) {
		this.studentPairs = studentPairs;
	}

	@Override
	public List<Student> getStudentList() {
		List<Student> tempList = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT id, first_name, last_name, other_information FROM STUDENT WHERE id IS NOT NULL";
			PreparedStatement stmt = conn.prepareStatement(sql);
		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt(1));
				student.setFirstName(rs.getString(2));
				student.setLastName(rs.getString(3));
				student.setOtherInformation(rs.getString(4));
				tempList.add(student);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	@Override
	public Student getStudentName(String name) {
		Student s = null;
		try {
			for (Student student : studentList) {
				if (student.getFirstName().equalsIgnoreCase(name) || student.getLastName().equalsIgnoreCase(name)) {
					s = student;
					break;
				}
				break;
			}
		} catch (NullPointerException e) {
			System.out.println("NullPointer Exception Caught");
			e.printStackTrace();
		}

		return s;

	}

	@Override
	public boolean addStudent(Student student) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "INSERT INTO student (first_name, last_name, other_information) " + " VALUES (?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getOtherInformation());
			int updateCount = stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				student.setId(rs.getInt(1));
				
			}
			conn.commit(); // COMMIT TRANSACTION

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			throw new RuntimeException("Error inserting student " + student);
		}
		return true;
	}

	@Override
	public boolean removeStudent(Student student) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "DELETE FROM student WHERE id IS NOT NULL";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setObject(1, student);
			int updateCount = stmt.executeUpdate();
			sql = "DELETE FROM student WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setObject(1, student);
			updateCount = stmt.executeUpdate();
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		}
		return true;

	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public int getGroupSize() {
		return groupSize;
	}

	@Override
	public Map<Integer, List<Student>> getStudentPairs(Integer groupSize) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION

			studentPairs = new HashMap<>();
			List<Student> tempList = new ArrayList<Student>(studentList);
			Collections.shuffle(tempList);
			int result = tempList.size() / groupSize;
			int remainder = tempList.size() % groupSize;
			if (tempList.size() >= groupSize) {
				for (int i = 0; i < result; i++) {
					List<Student> group = new ArrayList<Student>();
					for (int j = 0; j < groupSize; j++) {

						group.add(tempList.remove(0));
					}
					studentPairs.put(i + 1, group);
				}
				while (remainder > 0) {
					for (int k = 0; k < studentPairs.size(); k++) {
						if (remainder != 0) {
							List<Student> newList = studentPairs.get(k + 1);
							newList.add(tempList.remove(0));
							remainder--;
							studentPairs.put(k + 1, newList);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentPairs;
	}

}
