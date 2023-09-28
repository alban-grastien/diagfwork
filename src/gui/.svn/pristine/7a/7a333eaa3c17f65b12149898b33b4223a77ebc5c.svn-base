package gui;

import lang.MMLDTransition;
import lang.State;
import lang.TimedState;
import lang.VisMap;
import lang.VisMapShapeDef;
import lang.YAMLDComponent;
import lang.YAMLDFormula;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class VisFig implements StateListener
{
	private Figure fig = new Figure();
	private VisMapShapeDef visMapShapeDef = null;
	private final YAMLDComponent _comp;
    private final NetworkDisplayManager _ndm;
    
//	private boolean init = true;
	
	private final Border kNORMALBORDER =
		new LineBorder(new Color(null, 0, 0, 0), 1);
	private final Border kFORCEDBORDER = 
		new LineBorder(new Color(null, 255, 0, 0), 3);
	
	public VisFig(YAMLDComponent comp, InteractiveInterface ii, NetworkDisplayManager ndm) 
	{		
	    _comp = comp;
        _ndm = ndm;
        ii.addStateListener(this);

		fig.setLayoutManager(new XYLayout());
		fig.setBounds(new Rectangle(_comp.visOptions().coords().get(0).x(), 
									_comp.visOptions().coords().get(0).y(), 
									24, 24));
		fig.setBorder(kNORMALBORDER);
		fig.setOpaque(true);
		fig.setBackgroundColor(ColorConstants.green);
		fig.repaint();
		
        ii.addStateListener(this);
	}

	public YAMLDComponent component() 
	{
		return _comp;
	}

	public Figure figure() 
	{
		return fig;
	}

	@Override
	public void newStateHandler(TimedState s) 
	{
		newStateHandler(s.getState());
	}

	/**
	 * Method gets invoked when the state changes.
	 */
	
	@Override
	public void newStateHandler(State s) 
	{

		// If the VisFig has custom drawing information associated with it
		// draw it now, and thereby override the default look.
		VisMapShapeDef newShape = VisMap.getSingletonObject().getChangedShape(_comp);
		
		if (newShape == null) {
			visMapShapeDef = null;
		} else if (visMapShapeDef == null || !visMapShapeDef.equals(newShape)) {
			// Is there a forced transition enabled?
			boolean forced = false;
			for (final MMLDTransition tr: _comp.transitions()) {
				for (final YAMLDFormula f: tr.getPreconditions()) {
					if (f.satisfied(s, _comp)) {
						forced = true;
						break;
					}
				}
				if (forced) {
					break;
				}
			}
			fig.setBorder(forced? kFORCEDBORDER : kNORMALBORDER);
			
			// There is a new shape
			visMapShapeDef = newShape;
			// If the VisFig has a custom icon attached, draw it now...
			if (visMapShapeDef.line() == null) {
				// If there were previous labels attached, remove them now.
				if (fig.getChildren().size() > 0)
					fig.remove((Figure)fig.getChildren().get(0));

					fig.setBounds(new Rectangle(fig.getLocation().x,
												fig.getLocation().y,
												visMapShapeDef.size().x,
												visMapShapeDef.size().y));
					
				// Attach new label.
				fig.add(visMapShapeDef.imgLabel(), 
						new Rectangle(0, 0, visMapShapeDef.size().x,
											visMapShapeDef.size().y));
			}
			// ... the figure is, in fact, a Polyline.
			else {
				_ndm.removeComponentFig(_comp);

				Polyline newFig = visMapShapeDef.line(); 
				newFig.setLineWidth(visMapShapeDef.lineWidth());
				newFig.setForegroundColor(visMapShapeDef.foregroundColor());
				newFig.setBackgroundColor(visMapShapeDef.backgroundColor());
				fig = newFig;
				fig.setBorder(null);

				_ndm.reAddComponentFig(_comp, this);
			}
		} else {
		}
//		init = false;
	}
}
