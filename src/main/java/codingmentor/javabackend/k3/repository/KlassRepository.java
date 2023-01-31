package codingmentor.javabackend.k3.repository;
import java.time.LocalDate;
import java.util.List;

import codingmentor.javabackend.k3.model.Klass;


public interface KlassRepository {
	/*
	 * GET LIST METHOD
	 */
	
    /**
	 * Get list of Klasss
	 * @category LIST
	 * @return a list of all Klasss if found or null
	 */
	public List<Klass> getklasses();
	

    /**
	 * @category LIST
     * @param department's is
     * @return list of courses 
     */
    public List<Klass> getKlassesFromDepartmentId (int departmentId);
    
	/*
	 * GET ITEM METHOD
	 */
    
    /**
	 * @category ITEM
     * Get Klass by Klass's id
     * @return Klass if exists and null if not
     */
    Klass getKlassById(int id);
    
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
     * Add a new Klass to DB
     * @return newly inserted Klass id if inserted and -1 otherwise
     */
    public int insertKlass (int course_id, int repo_id, String semester, Integer section, LocalDate startDate, LocalDate endDate);

    //PATCH(UPDATE)
    /** Update Klass's semester, section, start date and end date
	 * @category PATCH
     * @return true if updated, and false if update was unsuccessful
     */
    public boolean updateKlassById(String semester, Integer section, LocalDate startDate, LocalDate endDate, int klassId);
    
    /**
	 * @category DELETE
     */
}