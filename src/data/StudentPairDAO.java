package data;

import java.util.List;
import java.util.Map;

public interface StudentPairDAO {
	public List<Student> getStudentList();
	public Map<Integer, List<Student>> getStudentPairs(Integer groupSize);
	public Student getStudentName(String name);
	public boolean addStudent(Student student);
	public boolean removeStudent(Student student);
	public int getGroupSize();
}
