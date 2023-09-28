package lang;

import edu.supercom.util.Options;
import gui.GuiOptions;
import gui.YAMLDSim;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class VisMapShapeDef 
{
	private String _id = null;
	private String _imgPath = null;
	private Image  _img = null;
	private Color  _bgcol = null, _fgcol = null;
	private int    _lineWidth = 0;
    
    public static final String DEFAULT_IMG_DIR = "./";
    public static final String _imagesDir;
    static {
        final Options opt = YAMLDSim.OPTIONS;
        _imagesDir = GuiOptions.IMGLIB_DIR.getOption(opt, null, false, DEFAULT_IMG_DIR);
    }
	
	// **************
	// *** README ***
	// **************
	// To differentiate a line from another image, one can simply
	// check if _line == null, if so, draw the image, otherwise
	// draw the line with optional line properties.
	private ArrayList<Point> _lineCoords = null;
	private Polyline         _line       = null;
	// **************
	
	// TODO: Default icon size is 24x24 (as before).  Hardcoded, yikes!!!
	private Point _size = new Point(24, 24);

    @Override
	public VisMapShapeDef clone()
	{
		VisMapShapeDef newDef = new VisMapShapeDef();
		
		if (this.imgPath() != null)
			newDef.setImgPath(this.imgPath());

		if (this.id() != null)
			newDef.setId(this.id());
		
		if (this.imgLabel() != null)
			newDef.setImg(this.imgLabel().getIcon());
	
		if (this.foregroundColor() != null)
			newDef.setForegroundColor(this.foregroundColor());

		if (this.backgroundColor() != null)
			newDef.setBackgroundColor(this.backgroundColor());

		if (this.size() != null)
			newDef.setSize(this.size());
		
		if (this.line() != null) 
			newDef.setLine(this.line());
		
		if (this.lineWidth() != 0)
			newDef.setLineWidth(this.lineWidth());
		
		return newDef;
	}
	
	public void setForegroundColor(Color col)
	{
		_fgcol = col;
	}
	
	public void setForegroundColor(int r, int g, int b)
	{
		_fgcol = new Color(Display.getCurrent(), r, g, b);
	}
	
	public Color foregroundColor()
	{
		return _fgcol;
	}
	
	public void setBackgroundColor(Color col)
	{
		_bgcol = col;
	}

	public void setBackgroundColor(int r, int g, int b)
	{
		_bgcol = new Color(Display.getCurrent(), r, g, b);
	}
	
	public Color backgroundColor()
	{
		return _bgcol;
	}
	
	public void setImgPath(String uristring) 
	{
		_imgPath = uristring;
		
		try {
			setImg(new Image(Display.getCurrent(), 
					 new FileInputStream(new File(_imagesDir + _imgPath))));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String imgPath() 
	{
		return _imgPath;
	}

	public void setId(String _id) 
	{
		this._id = _id;
	}

	public String id() 
	{
		return _id;
	}

	public void setImg(Image _img) 
	{
		this._img = _img;
	}

	public Label imgLabel() 
	{
		// TODO:
		// It seems to be important to return a fresh copy of a label
		// every time it is added to a (component) figure, as it seems
		// that every label can only be added to exactly one figure.  So
		// if several components share the same label, it otherwise only
		// shows up on exactly one component.
		
		Label _imgLabel = null; 

		if (_img != null) {
			_imgLabel = new Label(_img);
			_imgLabel.setOpaque(true);
			_imgLabel.setBackgroundColor(_bgcol);
		}
		else {
			_imgLabel = new Label();
			_imgLabel.setOpaque(true);
			_imgLabel.setBackgroundColor(_bgcol);
		}
		
		return _imgLabel;
	}

	public void setSize(Point size) 
	{
		_size = size;
	}

	public void setWidth(int w)
	{
		_size.x = w;
	}

	public void setHeight(int h)
	{
		_size.y = h;
	}
	
	public Point size() 
	{
		return _size;
	}

	/**
	 * Create a Polyline, defined by an ArrayList of Points.
	 * 
	 * @param points The points which the PolyLine should pass through.
	 */
	
	public void createLine(ArrayList<Point> points)
	{
		_line = new Polyline();

		for (Point p : points)
			_line.addPoint(p);
	}
	
	public Polyline line()
	{
		return _line;
	}
		
	public void pushLineCoords(int x, int y)
	{
		if (_lineCoords == null)
			_lineCoords = new ArrayList<Point>();
		_lineCoords.add(new Point(x, y));
	}

	public void setLine(Polyline l)
	{
		_line = l;
	}
	
	/**
	 * Returns and removes the line coordinates stored so far.
	 * 
	 * @return The line coordinates stored so far.
	 */
	
	public ArrayList<Point> popLineCoords()
	{
		ArrayList<Point> coords = new ArrayList<Point>(_lineCoords);
		_lineCoords.clear();
		return coords;
	}
	
	public void setLineWidth(int w)
	{
		_lineWidth = w;
	}
	
	public int lineWidth()
	{
		return _lineWidth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_bgcol == null) ? 0 : _bgcol.hashCode());
		result = prime * result + ((_fgcol == null) ? 0 : _fgcol.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result
				+ ((_imgPath == null) ? 0 : _imgPath.hashCode());
		result = prime * result
				+ ((_lineCoords == null) ? 0 : _lineCoords.hashCode());
		result = prime * result + _lineWidth;
		result = prime * result + ((_size == null) ? 0 : _size.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VisMapShapeDef)) {
			return false;
		}
		VisMapShapeDef other = (VisMapShapeDef) obj;
		if (_bgcol == null) {
			if (other._bgcol != null) {
				return false;
			}
		} else if (!_bgcol.equals(other._bgcol)) {
			return false;
		}
		if (_fgcol == null) {
			if (other._fgcol != null) {
				return false;
			}
		} else if (!_fgcol.equals(other._fgcol)) {
			return false;
		}
		if (_id == null) {
			if (other._id != null) {
				return false;
			}
		} else if (!_id.equals(other._id)) {
			return false;
		}
		if (_imgPath == null) {
			if (other._imgPath != null) {
				return false;
			}
		} else if (!_imgPath.equals(other._imgPath)) {
			return false;
		}
		if (_lineCoords == null) {
			if (other._lineCoords != null) {
				return false;
			}
		} else if (!_lineCoords.equals(other._lineCoords)) {
			return false;
		}
		if (_lineWidth != other._lineWidth) {
			return false;
		}
		if (_size == null) {
			if (other._size != null) {
				return false;
			}
		} else if (!_size.equals(other._size)) {
			return false;
		}
		return true;
	}

}
