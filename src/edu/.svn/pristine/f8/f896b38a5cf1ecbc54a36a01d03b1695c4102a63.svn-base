/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import edu.supercom.util.auto.changer.LabelChanger;

/**
 * Simple implementation of embedded state label.
 *
 * @author Alban Grastien
 */
public class MyEmbeddedLabel<SL> implements EmbeddedLabel<SL> {

    /**
     * The embedded state label.
     */
    private SL _sl;

    private MyEmbeddedLabel(SL sl) { _sl = sl; }

    /**
     * Creates an embedded state label that embeds the specified label.
     *
     * @param sl the embedded state label.
     * @return a state label that embeds <code>sl</code>.
     */
    public static <SL> EmbeddedLabel<SL> embed(SL sl) {
        return new MyEmbeddedLabel<SL>(sl);
    }

    @Override
    public SL getLabel() {
        return _sl;
    }

    @Override
    public String toString() {
        return "~" + getLabel().toString() + "~";
    }

    public static <SL> LabelChanger<EmbeddedLabel<SL>,SL> extractChanger() {
        return new Unembedder<SL>();
    }

    public static <SL> LabelChanger<SL,EmbeddedLabel<SL>> embedChanger() {
        return new Embedder();
    }

}



class Unembedder<SL> implements LabelChanger<EmbeddedLabel<SL>,SL> {

    @Override
    public SL getLabel(EmbeddedLabel<SL> key) {
        return key.getLabel();
    }

}

class Embedder<SL> implements LabelChanger<SL,EmbeddedLabel<SL>> {

    @Override
    public EmbeddedLabel<SL> getLabel(SL key) {
        return MyEmbeddedLabel.embed(key);
    }
}