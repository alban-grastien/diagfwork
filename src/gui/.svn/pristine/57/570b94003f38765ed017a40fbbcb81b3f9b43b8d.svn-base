package gui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;
import lang.Network;
import lang.State;
import lang.VisOptions;
import lang.YAMLDComponent;
import lang.YAMLDConnection;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;

/**
 * The component in charge of drawing the network.
 * */

public class NetworkDisplayManager implements ComponentChanger
{
	/**
	 * A mapping: component -> Figure
	 * */
	private Map<YAMLDComponent, VisFig> _figures;

	public boolean showConnLabels = false;
	private boolean logicalConnsFlag = true;
	
	private final Network _net;
	private Figure _mainFig;
	private int x_offset = 0, max_x = 0;
	private int y_offset = 0, max_y = 0;
    private final Set<ComponentListener> _componentListeners;
	
	public NetworkDisplayManager(Figure newMainFig, Network net) 
	{
		_net = net;
		_mainFig = newMainFig;
		_figures = new HashMap<YAMLDComponent, VisFig>();
        _componentListeners = new HashSet<ComponentListener>();
	}

	/**
	 * @see removeComponentFig 
	 * 
	 * @param c
	 * @param vf
	 */
	
	public void reAddComponentFig(final YAMLDComponent c, VisFig vf)
	{
		_mainFig.add(vf.figure());
		_figures.put(c, vf);
		vf.figure().addMouseListener(new MouseListener.Stub() {
			public void mousePressed(MouseEvent event) {
                notifyComponentListener(c);
//				MainWindow.getSingletonObject().chooseComponent(c);
			}
		});
	}

	/**
	 * Removes component c's figure from the display as well
	 * as accompanying logical connections between c and other
	 * components.  To add them again, @see reAddComponentFig.
	 * 
	 * @param c
	 */
	
	public void removeComponentFig(YAMLDComponent c)
	{
		ArrayList<Figure> removeConns = new ArrayList<Figure>();
		Figure fig = _figures.get(c).figure();
		
		if (_mainFig.getChildren().contains(fig))
			_mainFig.remove(fig);
		
		// Store the connections that need to be removed, so we can
		// add them via reAddComponentFig again.
		for (Object f : _mainFig.getChildren()) {
			if (f instanceof PolylineConnection) {
				ChopboxAnchor sa = (ChopboxAnchor) 
									((PolylineConnection)f).getSourceAnchor();
				ChopboxAnchor ta = (ChopboxAnchor)
									((PolylineConnection)f).getTargetAnchor();
				if (sa.getOwner() == fig)
					removeConns.add((PolylineConnection)f);
				else if (ta.getOwner() == fig)
					removeConns.add((PolylineConnection)f);
			}
		}
		for (Figure currConn : removeConns)
			_mainFig.remove(currConn);
		_figures.remove(c);
	}

	/**
	 * Toggles the display of logical connections between components.
	 * Does not actually delete or modify data!
	 */
	
	public void toggleLogicalConnections()
	{
		logicalConnsFlag = !logicalConnsFlag;
		for (Object f : _mainFig.getChildren())
			if (f instanceof PolylineConnection)
				((PolylineConnection)f).setVisible(logicalConnsFlag);
	}
	
	public void addVisFigs(InteractiveInterface ii)
	{
		// The components
		for (final YAMLDComponent comp : _net.getComponents()) {
			final VisOptions opt = comp.visOptions();
			
			if (opt.coords().size() == 0)
				continue; // Not drawing this component.

			VisFig visFig = new VisFig(comp,ii,this);
			
			visFig.figure().addMouseListener(new MouseListener.Stub() {
				public void mousePressed(MouseEvent event) {
					notifyComponentListener(comp);
				}
			});
			
			_mainFig.add(visFig.figure());
			_figures.put(comp, visFig);
		}

		// Now make the components' logical connections
		for (YAMLDComponent comp : _net.getComponents()) {
			final VisFig fig1 = _figures.get(comp);
			if (fig1 == null) {
				continue;
			}
			for (final YAMLDConnection conn : comp.conns()) {
				YAMLDComponent target = conn.target();
				final VisFig fig2 = _figures.get(target);
				if (fig2 == null) {
					continue;
				}

				PolylineConnection c = new PolylineConnection();
				
				c.setLineWidth(1);
				c.setForegroundColor(ColorConstants.gray);
				c.setLineDash(new float[] { 5.0f, 5.0f });
				c.setConnectionRouter(new ManhattanConnectionRouter());
//				c.addMouseListener(new MouseListener.Stub() {
//					public void mousePressed(MouseEvent event) {
//						MainWindow.getSingletonObject().
//												showConnectionControls(conn);
//					}
//				});

				ChopboxAnchor sourceAnchor = new ChopboxAnchor(fig1.figure());
				ChopboxAnchor targetAnchor = new ChopboxAnchor(fig2.figure());
				c.setSourceAnchor(sourceAnchor);
				c.setTargetAnchor(targetAnchor);
	
				_mainFig.add(c);
			}
		}
	}

	/**
	 * Called only once in the beginning to set the initial state.
	 * 
	 * @param state
	 */
	
	public void setState(State state) 
	{
		for (final YAMLDComponent comp : _figures.keySet()) {
			final VisFig vfig = _figures.get(comp);
			if (vfig == null) {
				continue;
			}
			vfig.newStateHandler(state);
		}
	}

	/** 
	 * This method updates the coordinates of the components' figures
	 * on the canvas.  It is used for scrolling the canvas.
	 * */

	public void updateCompCoords(int x, int y)
	{
		max_x = 0; max_y = 0;
		x_offset += x; y_offset += y;
		
		for (final YAMLDComponent comp : _figures.keySet()) {
			final VisFig vfig = _figures.get(comp);
			
			if (vfig != null) {
				if (vfig.figure() instanceof Polyline) {
					final Polyline pl = (Polyline)vfig.figure();
					pl.getPoints().translate(x, y);
				}
				vfig.figure().setLocation(new Point(
						vfig.figure().getLocation().x + x,
						vfig.figure().getLocation().y + y));
				if (vfig.figure().getLocation().x + x > max_x)
					max_x = vfig.figure().getLocation().x + x;
				if (vfig.figure().getLocation().y + y > max_y)
					max_y = vfig.figure().getLocation().y + y;
			}

		}
	}

	/**
	 * This method computes the dimension of the entire network to be
	 * drawn and overestimates it by 100 pixel in both dimensions, so that
	 * sufficiently much space is between the window border and the
	 * network elements.
	 * 
	 * @return height (y) and width (x) of the network to be displayed.  
	 */
	
	public Point figDimensions()
	{
		return new Point(Math.abs(x_offset) + max_x + 100,
					     Math.abs(y_offset) + max_y + 100);
	}
		
	public Network getNetwork() 
	{
		return _net;
	}
	
	public Point offset()
	{
		return new Point(x_offset, y_offset);
	}

    @Override
    public void addComponentListener(ComponentListener cl) {
        _componentListeners.add(cl);
    }

    @Override
    public void removeComponentListener(ComponentListener cl) {
        _componentListeners.remove(cl);
    }
    
    // Notifies the component listener that the specified component was selected. 
    private void notifyComponentListener(YAMLDComponent c) {
        for (final ComponentListener cl: _componentListeners) {
            cl.newComponent(c);
        }
    }
}
