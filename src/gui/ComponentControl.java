package gui;

import edu.supercom.util.Options;
import edu.supercom.util.PseudoRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import lang.MMLDRule;
import lang.MMLDTransition;
import lang.Network;
import lang.Period;
import lang.State;
import lang.PeriodInterval;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDFormula;
import lang.YAMLDGenericVar;
import lang.YAMLDValue;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import util.MMLDGlobalTransition;

/**
 * The component control is the object in charge of showing the state and the
 * transitions enabled of a component.
 * 
 * @author Andreas Bauer
 * @author Alban Grastien
 * */
public class ComponentControl implements 
        StateListener, ComponentListener, NetworkListener {

	/**
	 * The component that is shown
	 * */
	private YAMLDComponent _comp;

	private final Composite propsComp;
    
    Composite compStatsComp;
    
    /**
     * The choice element.  
     */
    private Composite _choiceComposite; 
    
    /**
     * The component element.
     */
    private Composite _compComposite; 

	/**
	 * The map that indicates where the variable values should be written.
	 * */
	private Map<YAMLDGenericVar, Text> _varTexts;

	/**
	 * The map that indicates which button corresponds to a given transition.
	 * */
	private Map<MMLDTransition, Button> _transButtons;

	/**
	 * The current state.
	 * */
	private TimedState _state;
    
    /**
     * The network.  
     */
    private Network _net;
    
    /**
     * Map component -> int of the combo.  
     */
    private final Map<YAMLDComponent, Integer> _indexOfComponent;
    
    /**
     * The combo used to show the name of current component, 
     * and to select a different component.  
     */
    private Combo _combo;
    
    /**
     * The set of impossible events.  
     * @todo: Should disappear as these events are now in the MainWindow
     */
    private Set<YAMLDEvent> _impossible;
    
    /**
     * The list of options.  
     */
    private final Options _opt;

    /**
     * The global transition triggered if the transition is triggered.  
     * @todo: Should disappear as this is now dealt with by the MainWindow
     */
    final Map<MMLDTransition, MMLDGlobalTransition> _trToGlob;
    
    final InteractiveInterface _ii;
    
    final PseudoRandom _rand;
    
	/**
	 * Builds the component control for the main window.
	 * */
	public ComponentControl(Composite propsComp, Options opt, 
            InteractiveInterface ii, PseudoRandom rand) {
        _opt = opt;
        _ii = ii;
        _rand = rand;
        _trToGlob = new HashMap<MMLDTransition, MMLDGlobalTransition>();
        _ii.addStateListener(this);
        _ii.addNetworkListener(this);
//		MainWindow.getSingletonObject().addComponentListener(this);
		this.propsComp = propsComp;
        _indexOfComponent = new HashMap<YAMLDComponent, Integer>();
        
		propsComp.setLayout(new GridLayout(1, false));
		GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, false, false);
		propsComp.setLayoutData(compStatsCompGD);
        
        _choiceComposite = new Composite(propsComp, 0);
        _compComposite = new Composite(propsComp,0);
	}

    public void build() {
        
		if (_compComposite != null)
			_compComposite.dispose();

        _compComposite = new Composite(propsComp, 0);

        {
            _compComposite.setLayout(new GridLayout(1, false));
    		GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, false, false);
            _compComposite.setLayoutData(compStatsCompGD);
        }
        
//        FillLayout fillLayout = new FillLayout();
//        fillLayout.type = SWT.VERTICAL;
//        _compComposite.setLayout(fillLayout);
        
		ScrolledComposite compScroll = new ScrolledComposite(_compComposite, 
                SWT.H_SCROLL | SWT.V_SCROLL);
		Composite localComp = new Composite(compScroll, SWT.NULL);

		localComp.setLayout(new GridLayout(2, false));
		GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, false, false);
		localComp.setLayoutData(compStatsCompGD);

		if (_comp != null) {
            createComponentDescription(localComp);
        }

		localComp.pack();

		compScroll.setContent(localComp);
		compScroll.setMinWidth(250);
		compScroll.setMinHeight(localComp.getClientArea().height);

		compScroll.setExpandHorizontal(true);
		compScroll.setExpandVertical(true);
        _choiceComposite.pack();
        _compComposite.pack();
        
		propsComp.redraw();
		propsComp.layout();

	}

	private void showCurrentState() {
		final State state = _state.getState();
		
        _trToGlob.clear();
        
		for (final Map.Entry<MMLDTransition, Button> ent : _transButtons.entrySet()) {
			final MMLDTransition tr = ent.getKey();
			final Button butt = ent.getValue();

			boolean satisfiable;
			if (tr.isSpontaneous()) {
				satisfiable = false; // Obviously the default rule is satisfiable but it has no effect
				for (final MMLDRule r : tr.getRules()) {
					if (r.getCondition().satisfied(state,tr.getComponent())) {
						satisfiable = true;
					}
				}
			} else {
				satisfiable = false;
				for (final YAMLDFormula f : tr.getPreconditions()) {
					final PeriodInterval ti = tr.getConditionTime(f);
					final Period time = _state.satisfiedFor(tr.getComponent(), f);
					if (ti.contains(time)) {
						satisfiable = true;
					}
				}
			}
            
            if (!satisfiable) {
                butt.setEnabled(false);
                continue;
            }

            // is it possible to find a GlobalTransition?
            final MMLDGlobalTransition gt = util.Util.computeGlobalTransition(
                    tr, _net, state, _impossible, _rand);
            if (gt != null) {
                butt.setEnabled(true);
                _trToGlob.put(tr,gt);
            } else {
                butt.setEnabled(false);
            }
		}
		
		// Show state
		for (final Map.Entry<YAMLDGenericVar,Text> entry: _varTexts.entrySet()) {
			final YAMLDGenericVar var = entry.getKey();
			final Text text = entry.getValue();
			final YAMLDValue val = state.getValue(var);
			text.setText(val.toString());
		}
	}

	@Override
	public void newStateHandler(State s) {
		
	}

	@Override
	public void newComponent(YAMLDComponent c) {
		_comp = c;
		build();
		showCurrentState();
        setComboComponent(c);
	}

	@Override
	public void newStateHandler(TimedState s) {
		_state = s;
		if (_comp != null) {
			showCurrentState();
		}
	}

	private void buildTransitionButton(final MMLDTransition tr,
			Composite compStatsComp) {
		Button b = new Button(compStatsComp, SWT.NULL);
		GridData eventsBGD = new GridData(SWT.FILL, SWT.FILL, true, false);
		eventsBGD.horizontalSpan = 2;
		b.setLayoutData(eventsBGD);
		b.setText(tr.getName());
		b.setEnabled(false);
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				_ii.trigger(tr);
				//MainWindow.getSingletonObject().triggerGlobalTransition(_trToGlob.get(tr));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		_transButtons.put(tr, b);
	}

    private void setComboComponent(YAMLDComponent c) {
        final int index = _indexOfComponent.get(c);
        _combo.select(index);
    }
    
    // The combo box that shows the name of the current state + allows to choose a component
    private void createCombo(Composite compStatsComp) {
        _indexOfComponent.clear();

        _combo = new Combo(compStatsComp, SWT.NULL);
        {
            _combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            int i=0;
            for (final YAMLDComponent c: _net.getComponents()) {
                _combo.add(c.name(),i);
                _indexOfComponent.put(c, i);
                i++;
            }
        }
        _combo.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent se) {
                final Combo c = (Combo)se.getSource();
                final int i = c.getSelectionIndex();
                final String selectedCompName = c.getItem(i);
                final YAMLDComponent comp = _net.getComponent(selectedCompName);
                newComponent(comp);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent se) {
            }
        });
    }
//    private void createCombo(Composite compStatsComp) {
//        Tree combo = new Tree(compStatsComp, SWT.NULL);
//        TreeItem root = new TreeItem(combo,0);
//        {
//            combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//            int i=0;
//            for (final YAMLDComponent c: _net.getComponents()) {
//                TreeItem ti = new TreeItem(root, 0);
//                ti.setText(c.name());
//            }
//        }
////        combo.addSelectionListener(new SelectionListener() {
////
////            @Override
////            public void widgetSelected(SelectionEvent se) {
////                final Combo c = (Combo)se.getSource();
////                final int i = c.getSelectionIndex();
////                final String selectedCompName = c.getItem(i);
////                final YAMLDComponent comp = _net.getComponent(selectedCompName);
////                newComponent(comp);
////            }
////
////            @Override
////            public void widgetDefaultSelected(SelectionEvent se) {
////            }
////        });
//        combo.setSize(200, 100);
//        combo.pack();
//    }
    
    // Gives the state + the transitions
    private void createComponentDescription(Composite compStatsComp) {
        
		_varTexts = new HashMap<YAMLDGenericVar, Text>();
		if (_comp.vars().size() > 0) {
			Label sep = new Label(compStatsComp, SWT.SEPARATOR | SWT.HORIZONTAL);
			GridData sepGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
			sepGD.horizontalSpan = 2;
			sep.setLayoutData(sepGD);

			Label vars = new Label(compStatsComp, SWT.NULL);
			vars.setText("Variables:");
			GridData varsGD = new GridData(GridData.FILL_HORIZONTAL);
			varsGD.horizontalSpan = 2;
			vars.setLayoutData(varsGD);

			Label[] varL = new Label[_comp.vars().size()];
			Text[] varT = new Text[_comp.vars().size()];
			for (int i = 0; i < _comp.vars().size(); i++) {
				varL[i] = new Label(compStatsComp, SWT.NULL);
				YAMLDGenericVar var = _comp.vars().get(i);
				varL[i].setText(var.name() + ":");
				varT[i] = new Text(compStatsComp, SWT.BORDER | SWT.READ_ONLY);
				varT[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
						false));
				// varT[i].setText(_state.getValue(var).toString());
				_varTexts.put(var, varT[i]);
			}
		}

		if (_comp.dvars().size() > 0) {
			Label sep = new Label(compStatsComp, SWT.SEPARATOR | SWT.HORIZONTAL);
			GridData sepGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
			sepGD.horizontalSpan = 2;
			sep.setLayoutData(sepGD);

			Label dvars = new Label(compStatsComp, SWT.NULL);
			dvars.setText("Dependent Variables:");

			GridData dvarsGD = new GridData(GridData.FILL_HORIZONTAL);
			dvarsGD.horizontalSpan = 2;
			dvars.setLayoutData(dvarsGD);

			Label[] dvarL = new Label[_comp.dvars().size()];
			Text[] dvarT = new Text[_comp.dvars().size()];
			for (int i = 0; i < _comp.dvars().size(); i++) {
				dvarL[i] = new Label(compStatsComp, SWT.NULL);
				YAMLDGenericVar dvar = _comp.dvars().get(i);
				dvarL[i].setText(dvar.name() + ":");
				dvarT[i] = new Text(compStatsComp, SWT.BORDER | SWT.READ_ONLY);
				dvarT[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
						false));
				// dvarT[i].setText(_state.getValue(dvar).toString());
				_varTexts.put(dvar, dvarT[i]);
			}
		}

		_transButtons = new HashMap<MMLDTransition, Button>();

		// The spontaneous transitions
		{
			final List<MMLDTransition> spon = new ArrayList<MMLDTransition>();
			for (final MMLDTransition tr : _comp.transitions()) {
				if (tr.isSpontaneous()) {
					spon.add(tr);
				}
			}
			if (!spon.isEmpty()) {
				Label sep = new Label(compStatsComp, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				GridData sepGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
				sepGD.horizontalSpan = 2;
				sep.setLayoutData(sepGD);

				Label eventsL = new Label(compStatsComp, SWT.NULL);
				eventsL.setText("Spontaneous transitions:");
				GridData eventsLGD = new GridData(GridData.FILL_HORIZONTAL);
				eventsLGD.horizontalSpan = 2;
				eventsL.setLayoutData(eventsLGD);

				for (final MMLDTransition tr : spon) {
					buildTransitionButton(tr, compStatsComp);
				}
			}
		}

		// The forced transitions
		{
			final List<MMLDTransition> forc = new ArrayList<MMLDTransition>();
			for (final MMLDTransition tr : _comp.transitions()) {
				if (!tr.getPreconditions().isEmpty()) {
					forc.add(tr);
				}
			}
			if (!forc.isEmpty()) {
				Label sep = new Label(compStatsComp, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				GridData sepGD = new GridData(SWT.FILL, SWT.CENTER, true, false);
				sepGD.horizontalSpan = 2;
				sep.setLayoutData(sepGD);

				Label eventsL = new Label(compStatsComp, SWT.NULL);
				eventsL.setText("Forced transitions:");
				GridData eventsLGD = new GridData(GridData.FILL_HORIZONTAL);
				eventsLGD.horizontalSpan = 2;
				eventsL.setLayoutData(eventsLGD);

				for (final MMLDTransition tr : forc) {
					buildTransitionButton(tr, compStatsComp);
				}
			}
		}
    }

    @Override
    public void newNetwork(Network n) {
        _net = n;
        
        // Computes the impossible events.  
        final List<String> pats = GuiOptions.IMPOSSIBLE_EVENTS_PATTERNS.getOptions(_opt);
        _impossible = util.Util.findEvents(_net, pats);
        
        
        // The choice composite
        _choiceComposite.setLayout(new GridLayout(2, false));
		GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, true, false);
		_choiceComposite.setLayoutData(compStatsCompGD);
		Label compNameLabel = new Label(_choiceComposite, SWT.NULL);
		compNameLabel.setText("Component:");
        createCombo(_choiceComposite);
        _choiceComposite.pack();

        // The component composite
        _compComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        _compComposite.setLayout(new StackLayout());
        FillLayout fillLayout = new FillLayout();
        fillLayout.type = SWT.VERTICAL;
        _compComposite.setLayout(fillLayout);
        final Label l = new Label(_compComposite, 0);
        l.setText("No component selected.");
        _compComposite.pack();
        
        propsComp.redraw();
        propsComp.layout();
    }
}
