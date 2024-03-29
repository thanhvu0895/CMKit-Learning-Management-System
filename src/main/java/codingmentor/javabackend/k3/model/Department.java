package codingmentor.javabackend.k3.model;
import java.util.List;

import codingmentor.javabackend.k3.repository.impl.CourseRepository;
import codingmentor.javabackend.k3.repository.impl.DepartmentRepository;

public class Department {
	private int id;
	private String title;
	private int repo_id;
	
   /**
    * Repository Functions
    */
	private DepartmentRepository departmentRepository =  DepartmentRepository.getInstance();
	private CourseRepository courseRepository =  CourseRepository.getInstance();
	
	public List<Course> getCourses() {
		return courseRepository.getCoursesByDepartmentId(this.id);
	} 
			
	public boolean isDepartmentAdmin(User user) {
		return departmentRepository.isDepartmentAdmin(user.getId(), this.id) || user.isAdmin();
	}
	
	public boolean isDepartmentProfessor(User user) {
		return departmentRepository.isDepartmentProfessor(user.getId(), this.id) || user.isAdmin();
	}
	
	/*
	 * Getters, Setters
	 */
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getRepo_id() {
		return repo_id;
	}

	// fluent style api
	public Department() {}
	
	public Department id(int id) {
		this.id = id;
		return this;
    }
	
	public Department title(String title) {
		this.title = title;
		return this;
	}
	
	public Department repo_id(int repo_id) {
		this.repo_id = repo_id;
		return this;
    }
}
