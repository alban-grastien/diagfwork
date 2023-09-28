package diag.reiter2;

public class SatSolver {

    private static String SOLVER_ADDRESS = "/usr/local/bin/multisat_core";

    public static String solverAddress() {
	return SOLVER_ADDRESS;
    }

    public static void setSolverAddress(String ad) {
	SOLVER_ADDRESS = ad;
    }

}
