package codingmentor.javabackend.k3.model;

import java.time.LocalDateTime;

import codingmentor.javabackend.k3.Utils.DateValidatorDateTimeFormatter;
import codingmentor.javabackend.k3.Utils.EnumUtils;
import codingmentor.javabackend.k3.repository.impl.AssignmentRepository;

public class Assigned {
	private int id;
	private int assignment_id;
	private int klass_id;
	private LocalDateTime due_date;
	private boolean allow_late_submissions;
	private int max_contributors;
	private Double max_points_override;
	private Double point_value_scale;
	private int repo_id;
	private boolean limit_resubmissions;
	private int resubmission_limit;
	private int allow_resubmissions;
	private boolean hide_grades;

   /**
    * Repository Functions
    */
	private AssignmentRepository assignmentRepository =  AssignmentRepository.getInstance();
	
	public String getAllowResubmissions() {
		return EnumUtils.allow_resubmissionsEnum.values()[this.allow_resubmissions].toString();
	}
	
	/**
	 *  OTHER FUNCTIONS:
	 */
	
	public String formatDueDate() {
		return DateValidatorDateTimeFormatter.formatLocalDateTime(this.due_date);
	}

	// #Get actual maximum points after adjusted
	public double getAdjustedMaxGrade() {
		return (point_value_scale == null) ? this.point_value_scale : getUnscaledMaxGrade();
	}
	
	//   #Get max points, either from override or total of problems
	public double getUnscaledMaxGrade() {
		return (max_points_override == null) ? this.max_points_override : assignmentRepository.getAssignmentById(this.assignment_id).getPointValue();
	}
	
	/*
	 * Getters, Setters
	 */
	public int getId() {
		return id;
	}
	
	public int getAssignment_id() {
		return assignment_id;
	}
	
	public int getKlass_id() {
		return klass_id;
	}
	
	public LocalDateTime getDue_date() {
		return due_date;
	}
	
	public boolean isAllow_late_submissions() {
		return allow_late_submissions;
	
	}
	
	public int getMax_contributors() {
		return max_contributors;
	}
	
	public Double getMax_points_override() {
		return max_points_override;
	}
	
	public Double getPoint_value_scale() {
		return point_value_scale;
	}
	
	public int getRepo_id() {
		return repo_id;
	}
	
	public boolean isLimit_resubmissions() {
		return limit_resubmissions;
	}
	
	public int getResubmission_limit() {
		return resubmission_limit;
	}
	
	
	public int getAllow_resubmissions() {
		return allow_resubmissions;
	}

	public boolean isHide_grades() {
		return hide_grades;
	}
	
	// fluent style api
	public Assigned() {}
	
	public Assigned id(int id) {
		this.id = id;
		return this;
    }
	
	public Assigned assignment_id(int assignment_id) {
	    this.assignment_id = assignment_id;
	    return this;
	}
	
	public Assigned klass_id(int klass_id) {
	    this.klass_id = klass_id;
	    return this;
	}
	
	public Assigned due_date(LocalDateTime due_date) {
	    this.due_date = due_date;
	    return this;
	}
	
	public Assigned allow_late_submissions(boolean allow_late_submissions) {
	    this.allow_late_submissions = allow_late_submissions;
	    return this;
	}
	
	public Assigned max_contributors(int max_contributors) {
	    this.max_contributors = max_contributors;
	    return this;
	}
	
	public Assigned max_points_override(Double max_points_override) {
	    this.max_points_override = max_points_override;
	    return this;
	}
	
	public Assigned point_value_scale(Double point_value_scale) {
	    this.point_value_scale = point_value_scale;
	    return this;
	}
	
	public Assigned repo_id(int repo_id) {
	    this.repo_id = repo_id;
	    return this;
	}
	
	public Assigned limit_resubmissions(boolean limit_resubmissions) {
	    this.limit_resubmissions = limit_resubmissions;
	    return this;
	}
	
	public Assigned resubmission_limit(int resubmission_limit) {
	    this.resubmission_limit = resubmission_limit;
	    return this;
	}
	
	public Assigned allow_resubmissions(int allow_resubmissions) {
	    this.allow_resubmissions = allow_resubmissions;
	    return this;
	}
	
	public Assigned hide_grades(boolean hide_grades) {
	    this.hide_grades = hide_grades;
	    return this;
	}
}

