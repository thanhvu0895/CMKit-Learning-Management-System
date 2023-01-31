package codingmentor.javabackend.k3.repository;

import java.util.List;

import codingmentor.javabackend.k3.model.DepartmentProfessor;


public interface DepartmentProfessorRepository {
	/*
	 * GET LIST METHOD
	 */
	
    /**
	 * Get list of DepartmentProfessors
     * @category LIST
	 * @return a list of all DepartmentProfessors if found or null
	 */
    List<DepartmentProfessor> getDepartmentProfessors();
        
    /**
     * Get Department Professor by Department's id
     * @return DepartmentProfessor if exists and null if not
     */
    List<DepartmentProfessor>  getDepartmentProfessorsByDepartmentId(int departmentId);
    
	/*
	 * GET Item
	 */
    /**
     * @category ITEM
     * Get Department by DepartmentProfessor's id
     * @return DepartmentProfessor if exists and null if not
     */
    DepartmentProfessor getDepartmentProfessorById(int id);
    
    
	/*
	 * GET Check True/false METHOD
	 */
    /**
     * @category CHECK
     */
    
    /*
	 * POST(CREATE) PUT(REPLACE) PATCH(UPDATE) METHODS
	 */
	
    //POST(INSERT INTO)
    /**
     * @category POST
     * Insert a new DepartmentProfessor to DB
     * @return newly inserted Department id if inserted and -1 otherwise
     */
    int insertDepartmentProfessor (int user_id, int department_id, boolean admin);
    
    //PATCH(UPDATE)
    /**
     * @category PATCH
     * Update admin status of Department Professor by Department Professor's id
     * @return
     */
    public boolean updateAdminByDepartmentProfessorId(boolean admin, int id);
    
    
	/**
     * @category DELETE
	 *  Remove DepartmentProfessor with given id
	 */
    boolean deleteDepartmentProfessor(int departmentProfessorId);

}