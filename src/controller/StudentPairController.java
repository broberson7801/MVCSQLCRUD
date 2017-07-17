package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import data.Student;
import data.StudentPairDAO;

@Controller
public class StudentPairController {

	@Autowired
	private StudentPairDAO studentPairDao;

	public StudentPairDAO getStateDao() {
		return studentPairDao;
	}

	public void setStudentPairDao(StudentPairDAO studentPairDAO) {
		this.studentPairDao = studentPairDAO;
	}

	@RequestMapping(path = "NewStudent.do", method = RequestMethod.POST)
	public ModelAndView addStudentToFile(Student student, RedirectAttributes redir) {
		studentPairDao.addStudent(student);
		ModelAndView mv = new ModelAndView();
		redir.addFlashAttribute("student", student);
		mv.setViewName("redirect:studentAdded.do");
		return mv;
	}

	@RequestMapping(path = "studentAdded.do", method = RequestMethod.GET)
	public ModelAndView studentAdded(Student student) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("studentList", studentPairDao.getStudentList());
		System.out.println(studentPairDao.getStudentList().size());
		mv.setViewName("newStudent.jsp");
		return mv;
	}

	@RequestMapping(path = "newStudentForm.do", method = RequestMethod.GET)
	public ModelAndView goToNewStudentForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("newStudent.jsp");
		return mv;
	}

	@RequestMapping(path = "RemoveStudent", method = RequestMethod.POST)
	public ModelAndView removeStudent(Student student) {
		ModelAndView mv = new ModelAndView();
		studentPairDao.removeStudent(student);
		mv.addObject("studentList", studentPairDao.getStudentList());
		mv.setViewName("newStudent.jsp");
		return mv;
	}

	@RequestMapping(path = "GenerateRandomPair.do", method = RequestMethod.GET)
	public ModelAndView generateRandomPairs(@RequestParam("GenerateRandomPair") String groupSizeString) {
		ModelAndView mv = new ModelAndView();
		int groupSize = 0;
		try {
			groupSize = Integer.parseInt(groupSizeString);
			studentPairDao.getStudentPairs(groupSize);
			mv.addObject("groupMap", studentPairDao.getStudentPairs(groupSize));
			mv.setViewName("newStudent.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", "You did not enter a valid group size!");
			mv.addObject("studentList", studentPairDao.getStudentList());
			mv.setViewName("newStudent.jsp");
		}

		return mv;
	}

	@RequestMapping(path = "RandomizeAgain.do", method = RequestMethod.GET)
	public ModelAndView randomizeAgain(@RequestParam("RandomizeAgain") String groupSizeString) {
		ModelAndView mv = new ModelAndView();
		int groupSize = 0;
		try {
			groupSize = Integer.parseInt(groupSizeString);
			studentPairDao.getStudentPairs(groupSize);
			mv.addObject("groupMap", studentPairDao.getStudentPairs(groupSize));
			mv.setViewName("newStudent.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", "You did not enter a valid group size!");
			mv.addObject("studentList", studentPairDao.getStudentList());
			mv.setViewName("newStudent.jsp");
		}

		return mv;
	}
}
