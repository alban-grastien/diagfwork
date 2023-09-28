package diag.reiter;

import diag.DiagnosisReport;

/**
 * An extended diagnosis report is a diagnosis report 
 * that may contain a solution to a diagnosis testing 
 * or a conflict.  
 * 
 * @author Alban Grastien
 * */
public class ExtendedDiagnosisReport<C extends Conflict> {

	/**
	 * The diagnosis report (if the test was successful).  
	 * */
	private final DiagnosisReport _report;
	/**
	 * The conflict (if the test failed).  
	 * */
	private final C _con;
	
	/**
	 * Builds a successful extended diagnosis report.  
	 * 
	 * @param rep the diagnosis report.
	 * */
	public ExtendedDiagnosisReport(DiagnosisReport rep) {
		_report = rep;
		_con = null;
	}
	
	/**
	 * Builds an unsuccessful extended diagnosis report.  
	 * 
	 * @param con the conflict.  
	 * */
	public ExtendedDiagnosisReport(C con) {
		_report = null;
		_con = con;
	}
	
	/**
	 * Returns the diagnosis report (if successful).  
	 * 
	 * @return the diagnosis report if the test was successful, 
	 * <code>null</code> otherwise.  
	 * */
	public DiagnosisReport getReport() {
		return _report;
	}
	
	/**
	 * Returns the conflict (if unsuccessful).  
	 * 
	 * @return the conflict produced by this test being unsuccessful, 
	 * <code>null</code> if the test was successful.  
	 * */
	public C getConflict() {
		return _con;
	}
}
