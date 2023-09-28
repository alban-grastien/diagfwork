package diag.reiter2;

import edu.supercom.util.Option;
import edu.supercom.util.Options;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableLoader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import lang.YAMLDEvent;

/**
 * Class that contains the options used in package diag.reiter2.
 */
public class PackageOptions {
    
    public static Option HYPOTHESIS_SPACE = new Option(
            "The class of the hypothesis space that should be used, "
            + "default value is given by diag.reiter2.PackageOptions#DEFAULT_SPACE.  "
            + "The space is build by calling a constructor with a set of events "
            + "(generally, the faults).", 
            "space", "hypothesisspace", "hspace");
    
    public static Option SAT_TRANSLATOR = new Option(
            "The translator used to translate the hypothesis space, "
            + "default value is given by diag.reiter2.PackageOptions#DEFAULT_TRANSLATOR.", 
            "trans");
    
    public static Option NB_UNOBS_TRANS_PER_OBS = new Option(
            "The number of unobservable transitions between two consecutive observations, "
            + "default value is diag.reiter2.PackageOptions#DEFAULT_NB_UNOBS_TRANS_PER_OBS.", 
            "stepsPerObs");
    
    public static Option TESTER = new Option(
            "The SAT tester used to perform tests, "
            + "default value is given by diag.reiter2.PackageOptions#DEFAULT_TESTER.  "
            + "The tester is built by calling a constructor with the following signature: "
            + "(VariableLoader, TimedSemanticFactory, SATTranslator, "
            + "DiagnosisProblem, int , int , Collection).",
            "tester");
    
    public static Option TIGHT_PROPERTY_COMPUTER = new Option(
            "The tight property computer used to generate the tight properties "
            + "associated with a hypothesis; "
            + "default value is given by diag.reiter2.PackageOptions#DEFAULT_TPC", 
            "tpc");

    public static Option PROPERTIES_FILE = new Option(
            "The file that contains the list of properties that must be tested "
            + "(all properties tested in one go)", 
            "props", "properties");
    
    public static final int DEFAULT_NB_UNOBS_TRANS_PER_OBS = 6;

    public static final String DEFAULT_TESTER = "diag.reiter2.IndependantTester";
    
    public static final String DEFAULT_SPACE = "diag.reiter2.seq.SequentialHypothesisSpace";
    
    public static final String DEFAULT_TRANSLATOR = "diag.reiter2.seq.SequentialTranslator";
    
    public static final String DEFAULT_TPC = "diag.reiter2.tight.AncestorsComputer";
    
    @SuppressWarnings("unchecked")
    public static HypothesisSpace getSpace(edu.supercom.util.Options opt, Set<YAMLDEvent> faults) 
            throws DiagnosisIOException {
        try {
            final String ad = HYPOTHESIS_SPACE.getOption(opt, null, false, DEFAULT_SPACE);
            final Class<? extends HypothesisSpace> cl = 
                (Class<? extends HypothesisSpace>)Class.forName(ad);
            final HypothesisSpace space = cl.getConstructor(Set.class).newInstance(faults);
            return space;
        } catch (ClassNotFoundException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InstantiationException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static SATTranslator getTranslator(edu.supercom.util.Options opt) 
            throws DiagnosisIOException {
        try {
            final String ad = SAT_TRANSLATOR.getOption(opt, null, false, DEFAULT_TRANSLATOR);
            final Class<? extends SATTranslator> cl = 
                (Class<? extends SATTranslator>)Class.forName(ad);
            final SATTranslator trans = cl.getConstructor().newInstance();
            return trans;
        } catch (ClassNotFoundException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InstantiationException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
    public static int nbUnobsPerTimeStep(edu.supercom.util.Options opt) {
        final String stringResult = NB_UNOBS_TRANS_PER_OBS.getOption(opt, null, false, null);
        if (stringResult != null) {
            try {
                final int result = Integer.parseInt(stringResult);
                if (result >= 0) {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
            System.err.println("Problem with specified option, reading default value instead.");
        }
        
        return DEFAULT_NB_UNOBS_TRANS_PER_OBS;
    }
    
    @SuppressWarnings("unchecked")
    public static Tester getTester(Options opt, VariableLoader loader, TimedSemanticFactory fact, 
            SATTranslator trans, DiagnosisProblem prob, int firstTimeStep , int lastTimeStep, Collection faults) 
            throws DiagnosisIOException {
        try {
            final String ad = TESTER.getOption(opt, null, false, DEFAULT_TESTER);
            final Class<? extends Tester> cl = 
                (Class<? extends Tester>)Class.forName(ad);
            final Tester tester = cl.getConstructor(VariableLoader.class, TimedSemanticFactory.class, 
                    SATTranslator.class, DiagnosisProblem.class, int.class, int.class, Collection.class)
                    .newInstance(loader, fact, trans, prob, firstTimeStep, lastTimeStep, faults);
            return tester;
        } catch (ClassNotFoundException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InstantiationException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static TightPropertyComputer getTightPropertyComputer(Options opt) 
            throws DiagnosisIOException {
        try {
            final String ad = TIGHT_PROPERTY_COMPUTER.getOption(opt, null, false, DEFAULT_TPC);
            final Class<? extends TightPropertyComputer> cl = 
                (Class<? extends TightPropertyComputer>)Class.forName(ad);
            final TightPropertyComputer tpc = cl.getConstructor().newInstance();
            return tpc;
        } catch (ClassNotFoundException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InstantiationException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <H extends Hypothesis> Collection<Property<H>> getProperties(Options opt, HypothesisSpace<H> space) 
            throws DiagnosisIOException {
        try {
            final String ad = PROPERTIES_FILE.getOption(opt, null, false, DEFAULT_TPC);
            final BufferedReader reader = new BufferedReader(new FileReader(ad));
            
            final Collection<Property<H>> result = new ArrayList<Property<H>>();
            for (;;) {
                final Property<H> p = Property.parseProperty(reader, space);
                if (p == null) {
                    return result;
                }
                result.add(p);
            }
        } catch (IOException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
}
