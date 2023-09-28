package edu.supercom.util.auto.changer;

import edu.supercom.util.auto.label.DatedLabel;

/**
 * This class removes the time in a dated label. 
 * It is an implementation of LabelChanger.
 * 
 * @author Carole Aujames
 * @since 1.0
 */
public class TimeAbstraction<L> implements LabelChanger<DatedLabel<L>,L> {

    @Override
    public L getLabel(DatedLabel<L> key) {
        return key.getCore();
    }

}
