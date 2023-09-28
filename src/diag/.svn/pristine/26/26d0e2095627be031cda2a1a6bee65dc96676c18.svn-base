package diag.auto;

import edu.supercom.util.junctionTree.Cluster;
import edu.supercom.util.junctionTree.Edge;
import edu.supercom.util.junctionTree.Graph;
import edu.supercom.util.junctionTree.JunctionTree;
import edu.supercom.util.junctionTree.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lang.MMLDSynchro;
import lang.Network;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

/**
 * A <code>JTDiagnoser</code>, i.e., a JT diagnoser, 
 * is a diagnoser that uses automata and junction trees.  
 */
public class JTDiagnoser {
    
    private final Map<YAMLDComponent,Node> _componentToNode;
    private final Map<Node,YAMLDComponent> _nodeToComponent;
    
    private final JunctionTree _jt;
    
    public JTDiagnoser(Network net) {
        final Graph networkGraph = new Graph();
        _componentToNode = new HashMap<YAMLDComponent, Node>();
        _nodeToComponent = new HashMap<Node, YAMLDComponent>();
        
        for (final YAMLDComponent comp: net.getComponents()) {
            final Node node = new Node(comp);
            _componentToNode.put(comp, node);
            _nodeToComponent.put(node, comp);
            networkGraph.newNode(node);
        }
        
        { // The connections
            for (final MMLDSynchro synch: net.getSynchros()) {
                final YAMLDComponent firstComponent = synch.getEvent().getComponent();
                final Node n1 = _componentToNode.get(firstComponent);
                for (final YAMLDEvent secondEvent: synch.getSynchronizedEvents()) {
                    final YAMLDComponent secondComponent = secondEvent.getComponent();
                    final Node n2 = _componentToNode.get(secondComponent);
                    final Edge edge = new Edge(n1, n2, 1);
                    networkGraph.newEdge(edge);
                }
            }
        }
        
        System.out.println(networkGraph.dotRepresentation());
        
        _jt = JunctionTree.graphToJtree(networkGraph);
        {
            int width = 0;
            for (final Cluster cl: _jt.nodes()) {
                final int clusterWidth = cl.size()-1;
                if (clusterWidth > width) {
                    width = clusterWidth;
                }
            }
            System.out.println("Tree width = " + width);
        }
        
    }
    
    public void updateDiagnosis(Diagnosis diag) {
        final Map<Cluster,Language> map = computeClusterDiagnosis(diag);
        final Cluster root = _jt.nodes().iterator().next();
        gather(root, null, map);
        distribute(root, null, map);
        updateDiagnosis(map, diag);
    }
    
    private Map<Cluster,Language> computeClusterDiagnosis(Diagnosis diag) {
        final Map<Cluster,Language> result = new HashMap<Cluster, Language>();
        
        for (final Cluster cl: _jt.nodes()) {
            System.out.println("Computing diagnosis for cluster " + cl);
            Language l = new Language();
            
            final Set<YAMLDComponent> componentsToSynchronise = new HashSet<YAMLDComponent>();
            for (final Node node: cl.getElements()) {
                final YAMLDComponent comp = _nodeToComponent.get(node);
                componentsToSynchronise.add(comp);
            }
            
            while (!componentsToSynchronise.isEmpty()) {
                // Pick the best component (i.e., the one that introduces the fewest number of events).
                YAMLDComponent bestComponent = null;
                int record = Integer.MAX_VALUE;
                
                for (final YAMLDComponent currentComponent: componentsToSynchronise) {
                    final Language componentLanguage = diag.getDiagnosis(currentComponent);
                    final Alphabet newEvents = componentLanguage.getAlphabet().remove(l.getAlphabet());
                    final int nbNewEvents = newEvents.size();
                    if (nbNewEvents < record) {
                        record = nbNewEvents;
                        bestComponent = currentComponent;
                    }
                }
                
                componentsToSynchronise.remove(bestComponent);
                final Language componentLanguage = diag.getDiagnosis(bestComponent);
                l = Language.intersect(l, componentLanguage);
            }

//            for (final Node node: cl.getElements()) {
//                final YAMLDComponent comp = _nodeToComponent.get(node);
//                final Language componentLanguage = diag.getDiagnosis(comp);
//                l = Language.intersect(l, componentLanguage);
//            }
            
            result.put(cl, l);
        }
        
        return result;
    }
    
    private void gather(Cluster currentCluster, Cluster parent, Map<Cluster,Language> diag) {
        System.out.println("gather " + currentCluster + " from " + parent);
        
        Language currentDiag = diag.get(currentCluster);
        
        for (final Cluster child: _jt.getNeighbours(currentCluster)) {
            if (child == parent) {
                continue;
            }
            
            gather(child, currentCluster, diag);
            
            final Language childDiagnosis = diag.get(child);
            final Language projectedDiagnosis = childDiagnosis.project(currentDiag.getAlphabet());
            currentDiag = Language.intersect(currentDiag, projectedDiagnosis);
        }
        
        diag.put(currentCluster, currentDiag);
    }
    
    private void distribute(Cluster currentCluster, Cluster parent, Map<Cluster,Language> diag) {
        System.out.println("distribute " + currentCluster + " from " + parent);
        
        final Language currentDiag = diag.get(currentCluster);
        
        for (final Cluster child: _jt.getNeighbours(currentCluster)) {
            if (child == parent) {
                continue;
            }
            
            final Language childDiagnosis = diag.get(child);
            final Language projection = childDiagnosis.project(currentDiag.getAlphabet());
            final Language newChildDiagnosis = Language.intersect(projection, childDiagnosis);
            
            diag.put(child, newChildDiagnosis);
            
            distribute(child, currentCluster, diag);
        }
    }
    
    private void updateDiagnosis(Map<Cluster,Language> jtDiag, Diagnosis diag) {
        for (final YAMLDComponent comp: diag.getComponents()) {
            System.out.println("update diagnosis of " + comp.name());
            
            final Cluster componentCluster;
            {
                final Node compNode = _componentToNode.get(comp);
                Cluster tmp = null;
                for (final Cluster cl: _jt.nodes()) {
                    if (cl.contains(compNode)) {
                        tmp = cl;
                        break;
                    }
                }
                // Assertion: tmp != null
                componentCluster = tmp;
            }
            
            final Language clusterLanguage = jtDiag.get(componentCluster);
            final Alphabet events = diag.getDiagnosis(comp).getAlphabet();
            final Language newClusterLanguage = clusterLanguage.project(events);
            diag.setDiagnosis(comp, newClusterLanguage);
        }
    }
    
    Language diagnosisLanguage(Diagnosis diag, Alphabet faults) {
        final Map<Cluster,Language> map = computeClusterDiagnosis(diag);
        final Cluster root = _jt.nodes().iterator().next();
        //gather(root, null, map);
        //distribute(root, null, map);
        
        final Language rootLanguage = gatherDiagnosisLanguage(root, null, faults, map);
        final Language result = rootLanguage.project(faults);
        return result;
    }
    
    Language gatherDiagnosisLanguage(Cluster currentCluster, Cluster parent, Alphabet faults, Map<Cluster,Language> map) {
        System.out.println("Gather diagnosis of " + currentCluster + " to " + parent);
        
        Language currentLanguage = map.get(currentCluster);
        
        for (final Cluster child: _jt.getNeighbours(currentCluster)) {
            if (child == parent) {
                continue;
            }
            
            final Language childLanguage = gatherDiagnosisLanguage(child, currentCluster, faults, map);
//            final Alphabet projectionAlphabet = childLanguage.getAlphabet().project(currentLanguage.getAlphabet().union(faults));
//            final Language projectedLanguage = childLanguage.project(projectionAlphabet);
//            currentLanguage = Language.intersect(currentLanguage, projectedLanguage);
            
            final Language synchronisation = Language.intersect(currentLanguage, childLanguage);
            final Alphabet projectionAlphabet = currentLanguage.getAlphabet().union(childLanguage.getAlphabet().project(faults));
            //final Alphabet projectionAlphabet = currentLanguage.getAlphabet();
            currentLanguage = synchronisation.project(projectionAlphabet);
        }
        
        System.out.println("done");
        return currentLanguage;
    }
}
