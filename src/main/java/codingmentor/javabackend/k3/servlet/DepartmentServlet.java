package codingmentor.javabackend.k3.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import codingmentor.javabackend.k3.Utils.AccountsMailer;
import codingmentor.javabackend.k3.Utils.JspUtils;
import codingmentor.javabackend.k3.Utils.RandomUtils;
import codingmentor.javabackend.k3.Utils.UrlUtils;
import codingmentor.javabackend.k3.model.Course;
import codingmentor.javabackend.k3.model.Department;
import codingmentor.javabackend.k3.model.DepartmentProfessor;
import codingmentor.javabackend.k3.model.Klass;
import codingmentor.javabackend.k3.model.User;
import codingmentor.javabackend.k3.repository.impl.CourseRepository;
import codingmentor.javabackend.k3.repository.impl.DepartmentProfessorRepository;
import codingmentor.javabackend.k3.repository.impl.DepartmentRepository;
import codingmentor.javabackend.k3.repository.impl.KlassRepository;
import codingmentor.javabackend.k3.repository.impl.RepoRepository;
import codingmentor.javabackend.k3.repository.impl.UserRepository;


@WebServlet(urlPatterns = {
		UrlUtils.NEW_DEPARTMENT_PATH,
		UrlUtils.DEPARTMENTS_ALL_PATH
})
public class DepartmentServlet extends HttpServlet{
	private static final long serialVersionUID = 1515497142397284883L;
	private DepartmentRepository departmentRepository = null;
	private RepoRepository repoRepository = null;
	private CourseRepository courseRepository = null;
	private UserRepository userRepository = null;
	private DepartmentProfessorRepository departmentProfessorRepository = null;
	private KlassRepository klassRepository = null;
	private final Logger logger =  LogManager.getLogger("codingmentor");
	
	@Override
	public void init() throws ServletException {
		super.init();
		departmentRepository = DepartmentRepository.getInstance();
		repoRepository = RepoRepository.getInstance();
		courseRepository = CourseRepository.getInstance();
		departmentProfessorRepository = DepartmentProfessorRepository.getInstance();
		userRepository = UserRepository.getInstance();
		klassRepository = KlassRepository.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()) {
		case UrlUtils.DEPARTMENTS_PATH:
			String pathInfo = req.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				getDepartmentsIndex(req, resp);
				return;
			}
			
			String[] pathParts = pathInfo.split("/");
			int pathInfoLength = pathParts.length;
			if (pathInfoLength == 3 && UrlUtils.isInteger(pathParts[1])) { 
				int departmentId = Integer.parseInt(pathParts[1]);
				switch (pathParts[2]) {
				case "edit":
					getDepartmentEdit(req, resp, departmentId);			
					break;
				case "courses":
					getDepartmentCourses(req, resp, departmentId);
					break;
				case "files":
					getDepartmentFiles(req, resp, departmentId);
					break;
				case "klasses":
					getDepartmentKlasses(req, resp, departmentId);
					break;
				}
			return;
			}
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		case UrlUtils.NEW_DEPARTMENT_PATH: 					
			getDepartmentsNew(req, resp);
			break;
		}	
	}
	
	private void getDepartmentsIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			List<Department> departments = departmentRepository.getDepartments();
			req.setAttribute("departments", departments);
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_INDEX)
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void getDepartmentCourses(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws ServletException, IOException {
		try {
			Department department = departmentRepository.getDepartmentById(departmentId);
			List<Course> courses = courseRepository.getCoursesByDepartmentId(departmentId);
			if (department == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			req.setAttribute("department", department);
			req.setAttribute("courses", courses);
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_COURSES)
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void getDepartmentEdit(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws ServletException, IOException {
		try {
			Department department = departmentRepository.getDepartmentById(departmentId);
			if (department == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			List<DepartmentProfessor> departmentProfessors = departmentProfessorRepository.getDepartmentProfessorsByDepartmentId(departmentId);
			List<User> departmentProfessorUsers = userRepository.getUsersFromDepartmentId(departmentId);
			req.setAttribute("department", department);
			req.setAttribute("department_professors", departmentProfessors);
			req.setAttribute("department_professor_users", departmentProfessorUsers);
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_EDIT)
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
		

	
	private void getDepartmentFiles(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws ServletException, IOException {
		try {
			Department department = departmentRepository.getDepartmentById(departmentId);
			
			if (department == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			req.setAttribute("department", department);	
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_FILES)
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void getDepartmentKlasses(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws ServletException, IOException {
		try {
			Department department = departmentRepository.getDepartmentById(departmentId);
			
			if (department == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			List<Course> coursesByDepartments = courseRepository.getCoursesWithKlassByDepartmentId(departmentId);
			List<Klass> courseKlasses = klassRepository.getKlassesFromDepartmentId(departmentId);
			req.setAttribute("department", department);
			req.setAttribute("klasses", courseKlasses);
			req.setAttribute("courses", coursesByDepartments);	
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_KLASSES)
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void getDepartmentsNew(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			req.getRequestDispatcher(JspUtils.DEPARTMENTS_NEW) 
				.forward(req, resp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()) {
		case UrlUtils.DEPARTMENTS_PATH:
			String pathInfo = req.getPathInfo();
			
			if (pathInfo == null || pathInfo.equals("/")) { // if request is /users/ or /users
				postDepartmentsCreate(req, resp);
				break;
			}

			String[] pathParts = pathInfo.split("/");
			int pathInfoLength = pathParts.length;
			
			if (pathInfoLength == 2 && UrlUtils.isInteger(pathParts[1])) {
				int departmentId = Integer.parseInt(pathParts[1]);
				switch (req.getParameter("method")) {
				case "PATCH":
					patchDepartmentUpdate(req, resp, departmentId);
					break;
				case "DELETE":
					deleteDepartmentDestroy(req, resp, departmentId);
					break;
				}
			}
			
			if (pathInfoLength == 3 && UrlUtils.isInteger(pathParts[1]) && pathParts[2].equals("department_professors")) {
				int departmentId = Integer.parseInt(pathParts[1]);
				postDepartmentProfessors(req, resp, departmentId);
				return;
				
			}
			
			if (pathInfoLength == 4 && UrlUtils.isInteger(pathParts[1]) && pathParts[2].equals("department_professors") && UrlUtils.isInteger(pathParts[3])) { 
				int departmentId = Integer.parseInt(pathParts[1]);
				int departmentProfessorId = Integer.parseInt(pathParts[3]);
				switch (req.getParameter("method")) {
				case "PATCH":
					patchDepartmentProfessorUpdate(req, resp, departmentId, departmentProfessorId);
					break;
				case "DELETE":
					deleteDepartmentProfessorDestroy(req, resp, departmentId, departmentProfessorId);
					break;
				}
			}
		}
	}
	
	private void postDepartmentsCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			String title = req.getParameter("department[title]");
			if (title.isEmpty()) {
				req.getSession(false).setAttribute("alert", "Title can't be blank");
				resp.sendRedirect(req.getContextPath() + UrlUtils.NEW_DEPARTMENT_PATH);
				return;
			}
			
			if (departmentRepository.existedByTitle(title)) {
				req.getSession(false).setAttribute("alert", "Department with the same title already existed");
				resp.sendRedirect(req.getContextPath() + UrlUtils.NEW_DEPARTMENT_PATH);
				return;
			}
			
			int repoId = repoRepository.insertRepo();
			
			if (repoId != 0) {
				int departmentId = departmentRepository.insertDepartment(title, repoId);
				req.getSession(false).setAttribute("notice", "Department was successfully created.");
				resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.DEPARTMENT_COURSES_PATH, departmentId));
				return;
			}	
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void patchDepartmentUpdate(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws IOException {
		try {
			String title = req.getParameter("department[title]");
			if (title.isEmpty()) {
				req.getSession(false).setAttribute("alert", "Title can't be blank");
				resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.EDIT_DEPARTMENT_PATH, departmentId));
				return;
			}
			departmentRepository.updateDepartmentTitleById(title, departmentId);
			req.getSession(false).setAttribute("notice", "Department was successfully updated.");
			resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.DEPARTMENT_COURSES_PATH, departmentId));
			return;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	private void deleteDepartmentDestroy(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws IOException {
		try {
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void postDepartmentProfessors(HttpServletRequest req, HttpServletResponse resp, int departmentId) throws IOException {
		try {
			//#Counters for success message
			List<String> added = new ArrayList<>();
			List<String> invited = new ArrayList<>();
			List<String> failed = new ArrayList<>();
			String token = RandomUtils.unique64();
			
			String[] departmentProfessorEmailList = req.getParameter("department_professor[emails]")
					.replaceAll("\\s","")
					.split(",");
			boolean admin = (req.getParameterValues("department_professor[admin]").length == 2);
			for (String email : departmentProfessorEmailList) {
				if (email.isEmpty()) {
					continue;
				}
				email = email.toLowerCase();
				logger.info("Value of email is: " + email);
				User u = userRepository.findUserByEmail(email);
				if (u != null  && !u.isDeleted() && !userRepository.isDepartmentProfessorByDepartmentId(u.getId(), departmentId)) {
					// User already exists and not a department professor
					int newDpId = departmentProfessorRepository.insertDepartmentProfessor(u.getId(), departmentId, admin);
					if (newDpId == 0) {
						failed.add(email);
					} else {
						added.add(email);
					}
					continue;
				}
				
				if (u == null) {
					boolean inviteSent = userRepository.createUserSendInvite(email, admin);
					u =  userRepository.findUserByEmail(email);
					int new_user_id = u.getId();
					userRepository.updateResetDigest(new_user_id, RandomUtils.SHA256Base64(token));
					AccountsMailer.inviteUserEmail(req, u, token);
					if (inviteSent) {
						int newDpId = departmentProfessorRepository.insertDepartmentProfessor(new_user_id, departmentId, admin);
						if (newDpId != 0) {
							invited.add(email);
						} else {
							failed.add(email);
						}
					} else {
						failed.add(email);
					}
					continue;		
				}
				
				if (u.isDeleted()) {
					boolean recoverUser = userRepository.recoverUser(u.getId());
					if (recoverUser) {
						userRepository.updateResetDigest(u.getId(), RandomUtils.SHA256Base64(token));
						AccountsMailer.inviteUserEmail(req, u, token);
						invited.add(email);
						if (!userRepository.isDepartmentProfessorByDepartmentId(u.getId(), departmentId)) {
							int newDpId = departmentProfessorRepository.insertDepartmentProfessor(u.getId(), departmentId, admin);
							if (newDpId != 0) {
								invited.add(email);	
							} else {
								failed.add(email);
							}
						}
					} else {
						failed.add(email);
					}
					continue;
				}
			}
			req.getSession(false).setAttribute("notice", added.size() + " professors added: " + String.join(",", added) + ";" 
					+ invited.size() + " professors invited: " + String.join(",", invited) + ";");
			req.getSession(false).setAttribute("alert", "Failed to add " + failed.size() + " professors: " + String.join(",", failed) + ";");
			resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.EDIT_DEPARTMENT_PATH, departmentId));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void patchDepartmentProfessorUpdate(HttpServletRequest req, HttpServletResponse resp, int departmentId, int departmentProfessorId) throws IOException {
		try {
			boolean admin = (req.getParameterValues("department_professor[admin]").length == 2);
			departmentProfessorRepository.updateAdminByDepartmentProfessorId(admin, departmentProfessorId);
			req.getSession(false).setAttribute("notice", "Professor updated.");
			resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.EDIT_DEPARTMENT_PATH, departmentId));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	private void deleteDepartmentProfessorDestroy(HttpServletRequest req, HttpServletResponse resp, int departmentId, int departmentProfessorId) throws IOException {
		try {
			departmentProfessorRepository.deleteDepartmentProfessor(departmentProfessorId);
			req.getSession(false).setAttribute("notice", "Professor removed.");
			resp.sendRedirect(req.getContextPath() + UrlUtils.putIdInPath(UrlUtils.EDIT_DEPARTMENT_PATH, departmentId));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
