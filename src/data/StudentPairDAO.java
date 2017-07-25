package data;

import java.util.List;
import java.util.Map;

public interface StudentPairDAO {
	public List<Student> getStudentList();
	public Map<Integer, List<Student>> getStudentPairs(Integer groupSize);
	public Student getStudentName(String name);
	public Student addStudent(Student student);
	public boolean removeStudent(Integer id);
	public int getGroupSize();

}
