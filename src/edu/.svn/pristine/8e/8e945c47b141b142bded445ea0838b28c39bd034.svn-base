/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 *
 * @author Alban Grastien
 */
public class TimeInterval {

    /**
     * The min time (inclusive).
     */
    private final int _min;
    /**
     * The max time (exclusive).
     */
    private final int _max;

    private TimeInterval(int min, int max) {
        _min = min;
        _max = max;
    }

    public static TimeInterval timeInterval(int min, int max) {
        if (max <= min) {
            return null;
        }

        return new TimeInterval(min, max);
    }

    public static TimeInterval synchronise(TimeInterval t1, TimeInterval t2) {
        final int min = Math.max(t1._min, t2._min);
        final int max = Math.min(t1._max, t2._max);

        return timeInterval(min, max);
    }

    public static Synchroniser<TimeInterval,TimeInterval,TimeInterval,TimeInterval,TimeInterval> getSynchroniser() {
        return new AbstractSynchroniser<TimeInterval, TimeInterval, TimeInterval, TimeInterval, TimeInterval>() {

            @Override
            public TimeInterval synchroniseLabel(TimeInterval l1, TimeInterval l2) {
                return TimeInterval.synchronise(l1, l2);
            }

            @Override
            public TimeInterval projection1(TimeInterval l) {
                return l;
            }

            @Override
            public TimeInterval projection2(TimeInterval l) {
                return l;
            }

            @Override
            public boolean synchroniseProjectedLabels(TimeInterval l1, TimeInterval l2) {
                return synchroniseLabel(l1, l2) != null;
            }
        };
    }
}
