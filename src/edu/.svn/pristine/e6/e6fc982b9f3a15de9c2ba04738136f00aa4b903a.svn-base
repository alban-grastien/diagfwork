package edu.supercom.util.auto.changer;

import edu.supercom.util.auto.label.DatedLabel;

/**
 * A core changer is an implementation of LabelChanger where the label
 * is a dated label.
 *
 * @author Carole Aujames
 * @version 1.0
 * @since 1.0
 */
public class CoreMapper<L1,L2> implements LabelChanger<DatedLabel<L1>,DatedLabel<L2>> {

	/**
	 * the label changer used to determine the new core label.
	 */
	private LabelChanger<L1,L2> _cl;
	
	/**
	 * Constructs a core changer.
	 * @param cl the label changer used to determine the new core label.
	 */
	public CoreMapper(LabelChanger<L1,L2> cl){
		_cl=cl;
	}
	
    @Override
    public DatedLabel<L2> getLabel(DatedLabel<L1> key) {
        DatedLabel<L1> d1 = (DatedLabel<L1>)key;
        return new DatedLabel<L2>(_cl.getLabel(d1.getCore()), d1.getMin(), d1.getMax());
    }

}
