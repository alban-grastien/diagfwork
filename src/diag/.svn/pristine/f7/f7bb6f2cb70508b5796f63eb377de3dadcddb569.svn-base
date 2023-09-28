package diag.auto;

import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.util.HashMap;
import java.util.Map;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import util.AlarmLog;

/**
 * A <code>Diagnosis</code>, i.e., a diagnosis, 
 * is a mapping that associates each component with a language.  
 */
public class Diagnosis {
    
    /**
     * The map that associates each component with a language.  
     */
    private final Map<YAMLDComponent,Language> _componentLanguages;
    
    private final UnmodifiableList<YAMLDComponent> _comps;
    
    public Diagnosis(Network net, State initialState, AlarmLog log) {
        _componentLanguages = new HashMap<YAMLDComponent, Language>();
        for (final YAMLDComponent comp: net.getComponents()) {
            System.out.println("Diagnosis >> computing local diagnosis of " + comp.name());
            final Language compModel = new Language(net, comp, initialState);
            System.out.println("Model.");
            final Language compObs = new Language(net, comp, log);
            System.out.println("Observations.");
            final Language compDiag = Language.intersect(compModel, compObs);
            _componentLanguages.put(comp, compDiag);
            System.out.println("Diag.");
        }
        
        final UnmodifiableListConstructor<YAMLDComponent> con = new UnmodifiableListConstructor<YAMLDComponent>();
        for (final YAMLDComponent comp: net.getComponents()) {
            con.addElement(comp);
        }
        _comps = con.getList();
    }
    
    public Language getDiagnosis(YAMLDComponent comp) {
        return _componentLanguages.get(comp);
    }
    
    public void setDiagnosis(YAMLDComponent comp, Language diag) {
        _componentLanguages.put(comp, diag);
    }
    
    public UnmodifiableList<YAMLDComponent> getComponents() {
        return _comps;
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        for (final Map.Entry<YAMLDComponent,Language> entry: _componentLanguages.entrySet()) {
            final YAMLDComponent comp = entry.getKey();
            final Language diag = entry.getValue();
            
            result.append(diag.toString(comp.name())).append("\n");
        }
        
        return result.toString();
    }
}
