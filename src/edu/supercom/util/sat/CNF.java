/*
 * CNF.java
 *
 * Created on 27 February 2007, 15:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.supercom.util.Pair;

/**
 * This class contains useful methods for manipulating CNF.
 * 
 * @author Alban Grastien
 * @version 2.0
 * @since 1.0.0
 * @todo replace method bt finalizeCNF(String,String,String..)
 */
public class CNF {

	/**
	 * A Boolean that indicates whether the copyright notice should be included.
	 */
	public static boolean _copy;

        // set by CNF.solve on each call: this is to allow caller to
        // distinguish between sat problem unsolvable and sat solver
        // crashed.
        public static boolean solverErrorFlag = false;

	/**
	 * Creates a CNF file from the specified file where the specified file only
	 * contains the clauses.
	 * 
	 * @param description
	 *            the description of the CNF file.
	 * @param input
	 *            the name of the input file.
	 * @param output
	 *            the name of the output file.
	 * @throws FileNotFoundException
	 *             if <code>input</code> or <code>output</code> does not exist,
	 *             is a directory rather than a regular file, or for some other
	 *             reason cannot be opened for reading/writing.
	 * @throws SecurityException
	 *             if a security manager exists and its checkWrite method denies
	 *             write access to the file.
	 * @throws NumberFormatException
	 *             if the format of the input file is not correct.
	 */
	public static void finalizeCNF(String description, String input,
			String output) throws FileNotFoundException, IOException,
			NumberFormatException {
		// Get the number of clauses and the maximum variable.
		int nbVar = 0;
		int nbClauses = 0;
		{
			final BufferedReader reader = new BufferedReader(new FileReader(
					input));
			while (reader.ready()) {
				final String line = reader.readLine();
				if (line.startsWith("c")) {
					continue;
				}
				final String[] split = line.split("\\s+");
				for (int i = 0; i < split.length; i++) {
					if (split[i].equals("")) {
						continue;
					}
					try {
						final int var = Math.abs(Integer.parseInt(split[i]));
						if (var > nbVar) {
							nbVar = var;
						}
					} catch (NumberFormatException e) {
						System.err.println("ERROR WITH " + line);
						System.exit(0);
					}
				}
			}
			reader.close();
		}

		{
			final BufferedWriter out = new BufferedWriter(
					new FileWriter(output));
			final String[] split = description.split("\\n");
			for (int i = 0; i < split.length; i++) {
				out.write("c " + split[i] + "\n");
			}
			out.write("p cnf " + nbVar + " " + nbClauses + "\n");
			final StringBuilder buf = new StringBuilder();
			final BufferedReader reader = new BufferedReader(new FileReader(
					input));
			while (reader.ready()) {
				final String line = reader.readLine();
				buf.append(line).append("\n");
				if (buf.length() > 10000) {
					out.write(buf.toString());
					buf.delete(0, buf.length());
				}
			}
			out.write(buf.toString());
			out.flush();
			out.close();
			reader.close();
		}
	}

	// Extremely inefficient.
	@Deprecated
	public static void finalizeCNF(String description, String[] input,
			String output) throws FileNotFoundException, IOException,
			NumberFormatException {
		// Get the number of clauses and the maximum variable.
		int nbVar = 0;
		int nbClauses = 0;
		for (int inp = 0; inp < input.length; inp++) {
			final BufferedReader reader = new BufferedReader(new FileReader(
					input[inp]));
			while (reader.ready()) {
				final String line = reader.readLine();
				if (line.length() == 0) {
					continue;
				}
				if (line.startsWith("c")) {
					continue;
				}
				int currentVar = 0;
				boolean started = false;
				int size = line.length();
				for (int i = 0; i < size; i++) {
					char c = line.charAt(i);
					if (c == '-' || c == '+') {
						if (started) {
							throw new NumberFormatException(line);
						}
						started = true;
					}
					if (c >= '0' && c <= '9') {
						started = true;
						currentVar = (10 * currentVar) + (c - '0');
					}
					if (Character.isWhitespace(c)) {
						if (started) {
							if (currentVar == 0) {
								nbClauses++;
							} else {
								nbVar = (nbVar > currentVar) ? nbVar
										: currentVar;
							}
							currentVar = 0;
							started = false;
						}
					}
				}
				/*
				 * final String[] split = line.split("\\s+"); for (int i = 0; i
				 * < split.length; i++) { if (split[i].equals("")) { continue; }
				 * try { final int var = Math.abs(Integer.parseInt(split[i]));
				 * if (var > nbVar) { nbVar = var; } } catch
				 * (NumberFormatException e) {
				 * System.err.println("Format exception for CNF."); System.err
				 * .println(
				 * "A common mistake is to with clauses in the same file from different writers."
				 * ); e.printStackTrace(); System.exit(1); } }
				 */
			}
			reader.close();
		}

		final BufferedWriter out = new BufferedWriter(new FileWriter(output));
		{
			final String[] split = description.split("\\n");
			for (int i = 0; i < split.length; i++) {
				out.write("c " + split[i] + "\n");
			}
		}
		out.write("p cnf " + nbVar + " " + nbClauses + "\n");
		for (int inp = 0; inp < input.length; inp++) {
			final StringBuilder buf = new StringBuilder();
			final BufferedReader reader = new BufferedReader(new FileReader(
					input[inp]));
			while (reader.ready()) {
				final String line = reader.readLine();
				buf.append(line).append("\n");
				if (buf.length() > 10000) {
					out.write(buf.toString());
					buf.delete(0, buf.length());
				}
			}
			out.write(buf.toString());
			reader.close();
		}
		out.flush();
		out.close();
	}
	
	/**
	 * Indicates the number of clauses of the number of the largest variable 
	 * in the specified file.
	 * 
	 * @param input the filename that contains a CNF with or without header.  
	 * @return a pair containing the largest number referring to a variable in <code>input</code> 
	 * together with the number of clauses in <code>input</code>.  
	 * */
	public static Pair<Integer, Integer> readCNFProperties(String input) 
	  throws FileNotFoundException, IOException,
	  NumberFormatException {
		int nbVar = 0;
		int nbClauses = 0;
		final BufferedReader reader = new BufferedReader(new FileReader(input));
		while (reader.ready()) {
			final String line = reader.readLine();
			if (line.length() == 0) {
				continue;
			}
			{
				final char c = line.charAt(0);
				if (c == 'c' || c == 'p') {
					continue;
				}
			}
			
			int currentVar = 0;
			boolean started = false;
			int size = line.length();
			for (int i = 0; i < size; i++) {
				char c = line.charAt(i);
				if (c == '-' || c == '+') {
					continue; // Who cares?  We are not parsing here.
				}
				if (c >= '0' && c <= '9') {
					started = true;
					currentVar = (10 * currentVar) + (c - '0');
				}
				if (Character.isWhitespace(c)) {
					if (started) {
						if (currentVar == 0) {
							nbClauses++;
						} else {
							nbVar = (nbVar > currentVar) ? nbVar
									: currentVar;
						}
						currentVar = 0;
						started = false;
					}
				}
			}
			if (started) {
				if (currentVar == 0) {
					nbClauses++;
				} else {
					nbVar = (nbVar > currentVar) ? nbVar
							: currentVar;
				}
				currentVar = 0;
				started = false;
			}
		}
		
		return new Pair<Integer, Integer>(nbVar, nbClauses);
	}

	/**
	 * A simple version that just concatenates the files
	 * */
	public static void simpleFinalizeCNF(String description, String[] input,
			String output) throws FileNotFoundException, IOException,
			NumberFormatException {

		final BufferedWriter out = new BufferedWriter(new FileWriter(output));
		{
			final String[] split = description.split("\\n");
			for (int i = 0; i < split.length; i++) {
				out.write("c " + split[i] + "\n");
			}
		}
		for (int inp = 0; inp < input.length; inp++) {
			final StringBuilder buf = new StringBuilder();
			final BufferedReader reader = new BufferedReader(new FileReader(
					input[inp]));
			while (reader.ready()) {
				final String line = reader.readLine();
				buf.append(line).append("\n");
				if (buf.length() > 10000) {
					out.write(buf.toString());
					buf.delete(0, buf.length());
				}
			}
			out.write(buf.toString());
			reader.close();
		}
		out.flush();
		out.close();
	}

	/**
	 * A simple version that just concatenates the files
	 * */
	public static void simplerFinalizeCNF(String description, String[] input,
			String output) throws FileNotFoundException, IOException,
			NumberFormatException {

		final Runtime rt = Runtime.getRuntime();
		String command = "lib/header " + output;
		for (String in: input) {
			command += " " + in;
		}
		//System.out.println("Finalise command = " + command);
		final Process p = rt.exec(command);

		{
			final InputStream normal = p.getErrorStream();
			final InputStreamReader reader = new InputStreamReader(normal);
			final BufferedReader br = new BufferedReader(reader);
			for (;;) {
				final String l = br.readLine();
				if (l == null) {
					break;
				}
			}
		}
		{
			final InputStream normal = p.getInputStream();
			final InputStreamReader reader = new InputStreamReader(normal);
			final BufferedReader br = new BufferedReader(reader);
			for (;;) {
				final String l = br.readLine();
				if (l == null) {
					break;
				}
			}
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Another simple version that just concatenates the files
	 * */
	public static void anotherSimpleFinalizeCNF(String description, String[] input,
			String output) throws FileNotFoundException, IOException,
			NumberFormatException {

		Pair<Integer,Integer> pair = new Pair<Integer, Integer>(0, 0);
		{
			for (final String i: input) {
				final Pair<Integer,Integer> p = readCNFProperties(i);
				pair = new Pair<Integer, Integer>(
						Math.max(pair.first(), p.first()), 
						pair.second() + p.second());
			}
		}
		
		final BufferedWriter out = new BufferedWriter(new FileWriter(output));
		{
			out.write("p cnf " + pair.first() + " " + pair.second() + "\n");
			final String[] split = description.split("\\n");
			for (int i = 0; i < split.length; i++) {
				out.write("c " + split[i] + "\n");
			}
		}
		for (int inp = 0; inp < input.length; inp++) {
			final StringBuilder buf = new StringBuilder();
			final BufferedReader reader = new BufferedReader(new FileReader(
					input[inp]));
			while (reader.ready()) {
				final String line = reader.readLine();
				buf.append(line).append("\n");
				if (buf.length() > 10000) {
					out.write(buf.toString());
					buf.delete(0, buf.length());
				}
			}
			out.write(buf.toString());
			reader.close();
		}
		out.flush();
		out.close();
	}

	/**
	 * Runs the specified SAT solver on the CNF at the specified address, and
	 * return a satisfying assignment if any is found.
	 * 
	 * @param solver
	 *            the command that is used to run the solver.
	 * @param cnfFile
	 *            the address where the CNF can be found.
	 * @return a satisfying assignment if any can be found, <code>null</code>
	 *         otherwise.
	 */
	public static List<Boolean> solve(String solver, String cnfFile) {
	    System.out.println("Will call " + solver + " with " + cnfFile);
	        solverErrorFlag = false;
		try {
			final File tmp = File.createTempFile("TMP", "SATRESULT");
			tmp.deleteOnExit();

			final Runtime rt = Runtime.getRuntime();
			final String command;
			if (solver.contains("picosat")) { // OK, I need to do something about that
				command = solver + " -o " + tmp.getPath() + " " + cnfFile;
			} else {
				command = solver + " " + cnfFile + " " + tmp.getPath();
			}
			final Process p = rt.exec(command);

			{
				final InputStream normal = p.getErrorStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();
					if (l == null) {
						break;
					}
				}
			}
			{
				final InputStream normal = p.getInputStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();
					if (l == null) {
						break;
					}
				}
			}
			p.waitFor();

			final List<Boolean> result = new ArrayList<Boolean>();
			final BufferedReader br = new BufferedReader(new FileReader(tmp));
			String line = br.readLine();
			if (line.equalsIgnoreCase("unsatisfiable")
					|| line.equalsIgnoreCase("unsat")
					|| line.equalsIgnoreCase("s UNSATISFIABLE")) {
				return null;
			}
			int nextVar = 1;
			final Pattern pat = Pattern.compile("(-?\\d+)");
			while (br.ready()) {
				line = br.readLine();
				final Matcher mat = pat.matcher(line);
				while (mat.find()) {
					final int val = Integer.parseInt(mat.group(1));
					if (val == 0) {
						break;
					}
					final int currentVar = Math.abs(val);
					while (nextVar != currentVar) {
						result.add(false);
					}
					result.add(val > 0);
					nextVar++;
				}
			}
			return Collections.unmodifiableList(result);
		} catch (Exception e) {
			e.printStackTrace();
			solverErrorFlag = true;
			return null;
		}
	}


	/**
	 * Runs the specified SAT solver with given args, and return
	 * a satisfying assignment if one is found, otherwise nil.
	 * The solverErrorFlag will be set on return.
	 * 
	 * @param solver
	 *            the command that is used to run the solver.
	 * @param args
	 *            the address where the CNF can be found.
	 * @return a satisfying assignment if any can be found, <code>null</code>
	 *         otherwise.
	 */
	public static List<Boolean> solve2(String solver, String[] args) {
	  solverErrorFlag = false;
	  try {
	    final File tmp = File.createTempFile("TMP", "SATRESULT");
	    tmp.deleteOnExit();
	    final Runtime rt = Runtime.getRuntime();
	    String command = solver;
	    for (int i = 0; i < args.length; i++) {
	      command += (" " + args[i]);
	    }
	    command += (" " + tmp);
	    final Process p = rt.exec(command);
	    {
	      final InputStream normal = p.getErrorStream();
	      final InputStreamReader reader = new InputStreamReader(normal);
	      final BufferedReader br = new BufferedReader(reader);
	      for (;;) {
		final String l = br.readLine();
		if (l == null) {
		  break;
		}
	      }
	    }
	    {
	      final InputStream normal = p.getInputStream();
	      final InputStreamReader reader = new InputStreamReader(normal);
	      final BufferedReader br = new BufferedReader(reader);
	      for (;;) {
		final String l = br.readLine();
		if (l == null) {
		  break;
		}
	      }
	    }
	    p.waitFor();

	    final List<Boolean> result = new ArrayList<Boolean>();
	    final BufferedReader br = new BufferedReader(new FileReader(tmp));
	    String line = br.readLine();
	    if (line.equalsIgnoreCase("unsatisfiable")
		|| line.equalsIgnoreCase("unsat")
		|| line.equalsIgnoreCase("s UNSATISFIABLE")) {
	      return null;
	    }
	    final Pattern pat = Pattern.compile("(-?\\d+)");
	    int nextVar = 1;
	    while (br.ready()) {
	      line = br.readLine();
	      //System.out.println("read line: " + line);
	      final Matcher mat = pat.matcher(line);
	      while (mat.find()) {
		final int val = Integer.parseInt(mat.group(1));
		if (val == 0) {
		  break;
		}
		final int currentVar = Math.abs(val);
		while (nextVar != currentVar) {
		  result.add(false);
		  nextVar++;
		}
		result.add(val > 0);
		nextVar++;
	      }
	    }
	    return Collections.unmodifiableList(result);
	  } catch (Exception e) {
	    e.printStackTrace();
	    solverErrorFlag = true;
	    return null;
	  }
	}

	/**
	 * Runs a SAT solver on the SAT problem stored in the file with specified
	 * address. This method waits until the solver is done, which means that
	 * this method should be called in a separate thread if the solver is to be
	 * stopped after a given delay. This implementation is based on MiniSAT
	 * behaviour. It will soon become deprecated.
	 * 
	 * @param solver
	 *            the command of the SAT solver.
	 * @param cnfFile
	 *            the CNF file for which the command is called.
	 * @return true if the problem is satisfiable, false otherwise.
	 */
	public static boolean runSolver(String solver, String cnfFile) {
		try {
			final Runtime rt = Runtime.getRuntime();
			final String command = solver + " " + cnfFile;
			final Process p = rt.exec(command);
			boolean result = true;
			{
				final InputStream normal = p.getErrorStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();
					if (l == null) {
						break;
					}
				}
			}
			{
				final InputStream normal = p.getInputStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();
					if (l == null) {
						break;
					}
					if (l.equals("UNSATISFIABLE")) {
						result = false;
					}
				}
			}
			p.waitFor();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Runs the specified SAT solver on the CNF at the specified address with
	 * the specified assumption file, saves a satisfying assignment if existing
	 * and returns a conflict on the assumptions otherwise.
	 * 
	 * @param solver
	 *            the command that is used to run the solver.
	 * @param cnfFile
	 *            the address where the CNF can be found.
	 * @param assFile
	 *            the file of assumptions.
	 * @param solution
	 *            where the list of assignments will be stored if a solution is found.
	 * @return a conflict on the variables of assFile if no solution is found.
	 */
	public static List<Integer> solveWithAssumption(String solver,
			String cnfFile, String assFile, List<Boolean> solution) {

		try {
			List<Integer> result = new ArrayList<Integer>();

			final File tmp = File.createTempFile("TMP", "SATRESULT");
			tmp.deleteOnExit();

			final Runtime rt = Runtime.getRuntime();
			final String command = solver + " -ass-file=" + assFile + " "
					+ cnfFile + " " + tmp.getPath();
			final Process p = rt.exec(command);

			{
				// final Pattern pat = Pattern.compile("Conflict: ([-0-9 ]+)");
				final Pattern pat = Pattern.compile("Conflict: ([-\\d\\s]+)");
				final InputStream normal = p.getErrorStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();

					if (l == null) {
						break;
					}

					final Matcher mat = pat.matcher(l);
					if (mat.matches()) {
						String conf = mat.group(1);
						final Pattern pa = Pattern.compile("^(-?\\d+)\\s*(.*)");
						Matcher ma = pa.matcher(conf);
						while (ma.matches()) {
							final int lit = Integer.parseInt(ma.group(1));
							conf = ma.group(2);
							result.add(lit);
							ma = pa.matcher(conf);
						}
					}
				}
			}
			{
				final InputStream normal = p.getInputStream();
				final InputStreamReader reader = new InputStreamReader(normal);
				final BufferedReader br = new BufferedReader(reader);
				for (;;) {
					final String l = br.readLine();
					if (l == null) {
						break;
					}
				}
			}
			p.waitFor();

			// final List<Boolean> result = new ArrayList<Boolean>();
			solution.clear();
			final BufferedReader br = new BufferedReader(new FileReader(tmp));
			String line = br.readLine();
//			if (line == null) {
//				System.out.println("LINE EQUALS NULL");
//				return result;
//			} else {
				if (line.equalsIgnoreCase("unsatisfiable")
						|| line.equalsIgnoreCase("unsat")) {
					return result;
				}
				line = br.readLine();
				final Pattern pat = Pattern.compile("(-?\\d+)");
				final Matcher mat = pat.matcher(line);
				int nextVar = 1;
				while (mat.find()) {
					final int val = Integer.parseInt(mat.group(1));
					if (val == 0) {
						break;
					}
					final int currentVar = Math.abs(val);
					while (nextVar != currentVar) {
						solution.add(false);
						nextVar++;
					}
					solution.add(val > 0);
					nextVar++;
				}
				return null;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    /**
     * Solves the SAT problem split in the files 
     * of the specified array of String filename 
     * using the specified SAT solver command 
     * with the list of assumptions in the specified String filename.  
     * 
     * @param solver the solver command used to solve the SAT problem.  
     * @param assFile the filename where the assumptions are stored, 
     * <code>null</code> if there are no assumptions.  
     * @param cnfFiles the files that contain the SAT problem.  
     * @return a pair whose first element is a conflict on the problem assumptions
     * (or <code>null</code> if the problem is satisfiable) 
     * and whose second element is a solution to the SAT problem 
     * (or <code>null</code> if the problem is not satisfiable.  
     */
	public static Pair<List<Integer>,List<Boolean>> solveWithAssumption2(String solver, 
            String assFile,
//            String cnfFile, 
			String... cnfFiles) 
            throws IOException, InterruptedException
    {

        List<Integer> conflict = new ArrayList<Integer>();
        List<Boolean> solution = new ArrayList<Boolean>();

        final File tmp = File.createTempFile("TMP", "SATRESULT");
        tmp.deleteOnExit();

        final Runtime rt = Runtime.getRuntime();
        final String command;
        {
            final StringBuilder buf = new StringBuilder();
            buf.append(solver);
            if (assFile != null) {
                buf.append(" -ass-file=").append(assFile);
            }
//                buf.append(" ").append(cnfFile);
            for (final String cf : cnfFiles) {
                buf.append(" ").append(cf);
            }
            buf.append(" ").append(tmp.getPath());
            command = buf.toString();
        }
        final Process p = rt.exec(command);

        {
            // final Pattern pat = Pattern.compile("Conflict: ([-0-9 ]+)");
            final InputStream normal = p.getErrorStream();
            final InputStreamReader reader = new InputStreamReader(normal);
            final BufferedReader br = new BufferedReader(reader);
            final Pattern pat = Pattern.compile("Conflict: ([-\\d\\s]+)");
            for (;;) {
                final String l = br.readLine();

                if (l == null) {
                    break;
                }

                final Matcher mat = pat.matcher(l);
                if (mat.matches()) {
                    String conf = mat.group(1);
                    final Pattern pa = Pattern.compile("^(-?\\d+)\\s*(.*)");
                    Matcher ma = pa.matcher(conf);
                    while (ma.matches()) {
                        final int lit = Integer.parseInt(ma.group(1));
                        conf = ma.group(2);
                        conflict.add(lit);
                        ma = pa.matcher(conf);
                    }
                }
            }
        }
        {
            final InputStream normal = p.getInputStream();
            final InputStreamReader reader = new InputStreamReader(normal);
            final BufferedReader br = new BufferedReader(reader);
            final Pattern pat = Pattern.compile("Conflict: ([-\\d\\s]+)");
            for (;;) {
                final String l = br.readLine();

                if (l == null) {
                    break;
                }

                final Matcher mat = pat.matcher(l);
                if (mat.matches()) {
                    String conf = mat.group(1);
                    final Pattern pa = Pattern.compile("^(-?\\d+)\\s*(.*)");
                    Matcher ma = pa.matcher(conf);
                    while (ma.matches()) {
                        final int lit = Integer.parseInt(ma.group(1));
                        conf = ma.group(2);
                        conflict.add(lit);
                        ma = pa.matcher(conf);
                    }
                }
            }
        }
        p.waitFor();

        // final List<Boolean> result = new ArrayList<Boolean>();
        final BufferedReader br = new BufferedReader(new FileReader(tmp));
        String line = br.readLine();
//			if (line == null) {
//				System.out.println("LINE EQUALS NULL");
//				return result;
//			} else {
        if (line.equalsIgnoreCase("unsatisfiable")
                || line.equalsIgnoreCase("unsat")) {
            return Pair.newPair(conflict, null);
        }
        line = br.readLine();
        final Pattern pat = Pattern.compile("(-?\\d+)");
        final Matcher mat = pat.matcher(line);
        int nextVar = 1;
        while (mat.find()) {
            final int val = Integer.parseInt(mat.group(1));
            if (val == 0) {
                break;
            }
            final int currentVar = Math.abs(val);
            while (nextVar != currentVar) {
                solution.add(false);
                nextVar++;
            }
            solution.add(val > 0);
            nextVar++;
        }
        return Pair.newPair(null, solution);
	}
	
	public static void main (String [] args) {
//		final String filename = args[0];
		try {
//			CNF.finalizeCNF("DESC", args, "/tmp/zongo");
//			CNF.simpleFinalizeCNF("DESC", args, "/tmp/zongo");
			CNF.anotherSimpleFinalizeCNF("DESC", args, "/tmp/zongo");
//			System.out.println(readCNFProperties(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
