package cgi;

import java.io.Serializable;

public class SalaryReport implements Serializable {

	private static final long serialVersionUID = -3995069930906369556L;
	
	private int id;
	private int employeeId;
	private float paycheck;
	
	//getters and setters 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public float getPaycheck() {
		return paycheck;
	}
	public void setPaycheck(float paycheck) {
		this.paycheck = paycheck;
	}
	
	
}
