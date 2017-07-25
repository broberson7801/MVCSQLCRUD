package data;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class StudentPairDAO_Impl implements StudentPairDAO {
	private int groupSize;
	private List<Student> studentList;
	private Map<Integer, List<Student>> studentPairs;
	private static final String FILE_NAME = "student_list.txt";

	public void setStudentPairs(Map<Integer, List<Student>> studentPairs) {
		this.studentPairs = studentPairs;
	}

	@Autowired
	private WebApplicationContext wac;

	@Override
	public List<Student> getStudentList() {
		return this.studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public void createStudentFile() {
		String filePath = wac.getServletContext().getRealPath(FILE_NAME);
		System.out.println(filePath);

		try {
			PrintWriter out = new PrintWriter(new FileWriter(filePath));
			for (Student s : studentList) {
				out.println(s.getFirstName() + "," + s.getLastName() + "," + s.getOtherInformation());

			}
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	@PostConstruct
	public void init() {
		try (InputStream is = wac.getServletContext().getResourceAsStream(FILE_NAME);
				BufferedReader buf = new BufferedReader(new InputStreamReader(is));) {
			String line = buf.readLine();
			while ((line = buf.readLine()) != null) {
				String[] tokens = line.split(",");
				String firstName = tokens[0];
				String lastName = tokens[1];
				String otherInformation = tokens[2];

				studentList.add(new Student(firstName, lastName, otherInformation));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
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
	public void addStudent(Student student) {
		if (studentList == null) {
			studentList = new ArrayList<>();
			studentList.add(student);
		} else {
			studentList.add(student);
		}
		createStudentFile();
	}

	@Override
	public void removeStudent(Student student) {
		if (studentList.remove(student)) {
			createStudentFile();

		} else {
			System.out.println("No student removed");
		}

	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public int getGroupSize() {
		return groupSize;
	}

	@Override
	public Map<Integer, List<Student>> getStudentPairs(Integer groupSize) {
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
		return studentPairs;
	}

}
