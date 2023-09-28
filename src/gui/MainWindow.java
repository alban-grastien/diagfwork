package gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;

import lang.DataLoader;
import lang.MMLDModelLoader;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.TimedState;
import lang.VisMapLoader;
import lang.YAMLDConnection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.supercom.util.Options;
import edu.supercom.util.PseudoRandom;
import edu.supercom.util.SeededPseudoRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lang.YAMLDEvent;

public class MainWindow extends VisWindow implements StateListener
//implements AccessInterface//, InteractiveInterface
{
	// This class follows the singleton design pattern.
//	private static MainWindow singletonObject;

	// private Display displ = new Display();
	private ArrayList<VisFig> visFigs = new ArrayList<VisFig>();
	private ScrollBar hBar, vBar;
	private Label simLabel;
	
	private String _networkAddress;
	
//    private ScenarioManager sm = null;
//	private ComponentManager cm = null;
//	private EventManager em = null;
	protected DiagnosisManager dm = null;
	protected ComponentControl _cc;
	private Point origin;

	// GUI items
	private Menu menu;
	private ToolBar toolBar;
	private ToolItem openModelItem, showLogConnsItem, testItem;
	private Composite propsComp;
	private Composite simComp;
	private Composite miniStatsComp;
	private Scale scale;
	private Button simLeftBut;
	private Button simRightBut;
	private MenuItem openItem = null;
	private MenuItem openModelMappingItem =	null;
	private Label mouseXL = null;
	
	private int x_off = 0, y_off = 0;

    public static final String DEFAULT_MENU_IMG_DIR = "images/";
    public static final String DEFAULT_MODEL_DIR = "data/";
    public static final String DEFAULT_VISMAP_DIR = "data/";
    public final String _menuImgDir;
    public final Options _opt;
    public final PseudoRandom _rand;

    private Network _net;
    private final Set<YAMLDEvent> _impossibleEvents;
    
	public MainWindow()
	{ 
        super(YAMLDSim.OPTIONS);
        
        // TEMPORARY
//        if (singletonObject == null) {
//            singletonObject = this;
//        }
        
        _opt = YAMLDSim.OPTIONS;
        {
            final String sseed = GuiOptions.GUI_SEED.getOption(_opt, null, false, "NOTHING");
            final int seed = sseed.hashCode();
            _rand = new SeededPseudoRandom(seed);
        }
        _menuImgDir = GuiOptions.MENUIMGLIB_DIR.getOption(
                _opt, null, false, DEFAULT_MENU_IMG_DIR);
        _impossibleEvents = new HashSet<YAMLDEvent>();
//        addNetworkListener(_ss);
        _ss.addStateListener(this);
    }
	
//	public static synchronized MainWindow getSingletonObject() 
//	{
//		if (singletonObject == null) {
//			singletonObject = new MainWindow();
//		}
//		return singletonObject;
//	}
	
//	@Override
//	public void setState(TimedState state) 
//	{
//		super.setState(state);
//		
//		simLabel.setText("Simulation Progress (" 
//				+ new DecimalFormat("0.####").format(_ss.getCurrentTime()) + " / " 
//				+ new DecimalFormat("0.####").format(_ss.getFinalTime()) + ")");
//	}
	
	/**
	 * Indicates whether there is a model loaded.  
	 * 
	 * @return <code>true</code> if a model has already been loaded.  
	 * */
	public boolean hasModel() 
	{
		return _net != null;
	}
	
	/**
	 * Deals with a failure.  
	 * 
	 * @param reason explains why a failure happened.  
	 * @param mess indicates whether the reason should be printed out.  
	 * @param popup indicates whether a window should popup displaying the reason.
	 * */
	public void failure(String reason, boolean mess, boolean popup) 
	{
		if (mess) {
			System.err.println(reason);
		}
		if (popup) {
			MessageBox messageBox = new MessageBox(child.shell(), 
					SWT.ICON_ERROR | SWT.CANCEL);
			messageBox.setText("Error");
			messageBox.setMessage(reason);
			messageBox.open();
		}
	}
	
	/**
	 * Sets the model (if possible).  
	 * 
	 * @param filename the name of the filename that contains the model.  
	 * @param mess indicates whether a message should be printed out if the opening fails.  
	 * @param popup indicates whether a window should popup if the opening fails.  
	 * @return <code>true</code> if the opening was successful, <code>false</code> otherwise.  
	 * */
	public boolean setModel(String filename, boolean mess, boolean popup) 
	{ // TODO: Merge with parseInputFile
		if (hasModel()) {
			failure("Model already set", mess, popup);
			return false;
		}

		if (filename == null || filename.equals("")) {
			failure("Filename not specified", mess, popup);
			return false;
		}
				
		
		try {
			final DataLoader loader = new MMLDModelLoader(filename);
			_networkAddress = filename;
			if (!loader.load()) {
				failure("Parsing error", mess, popup);
				_net = null;
				return false;
			}
            _net = MMLDlightParser.net;
			setupVisArea();
//			getScenarioManager().createTimedScenario(new MapTimedState(MMLDlightParser.st),
//				"Initial scenario");
			openItem.setEnabled(false);
			openModelItem.setEnabled(false);
			openModelMappingItem.setEnabled(true);
			showLogConnsItem.setEnabled(true);
			getNetworkDisplayManager().toggleLogicalConnections();
            _ss.newNetwork(_net);
//            notifyNetworkChanged();
	
			handleCanvasResizeEvent(null);
		} catch (IOException e) {
			failure(e.getMessage(),mess,popup);
			_net = null;
			return false;
		}
		
        updateImpossibleEvents();
        
		return true;
	}
	
	/**
	 * Sets the vmap (if possible).  
	 * 
	 * @param filename the name of the filename that contains the vmap.  
	 * @param mess indicates whether a message should be printed out if the opening fails.  
	 * @param popup indicates whether a window should popup if the opening fails.  
	 * @return <code>true</code> if the opening was successful, <code>false</code> otherwise.  
	 * */
	public boolean setVmap(String filename, boolean mess, boolean popup) 
	{
		if (!hasModel()) {
			failure("Cannot load vmap: model missing", mess, popup);
			return false;
		}

		if (filename == null || filename.equals("")) {
			failure("Filename not specified", mess, popup);
			return false;
		}
		
		try {
			final DataLoader loader = new VisMapLoader(filename);

			if (!loader.load()) {
				failure("Parsing error", mess, popup);
				_net = null;
				return false;
			}

			handleCanvasResizeEvent(null);
            _ss.notifyStateChanged();
			//notifyStateHandlers();
			
		} catch (IOException e) {
			failure(e.getMessage(),mess,popup);
			_net = null;
			return false;
		}
		
		return true;
	}

    @Override
	public Object clone() throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException();
	}

    @Override
	public void display() 
	{
		setMenu();
        
		composite = new Composite(child.shell(), SWT.BORDER);
		//composite = new SashForm(shell, SWT.BORDER);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Toolbar
		Image openDocImg = new Image(Display.getCurrent(), _menuImgDir + "/document-open.png");
		
		toolBar = new ToolBar(composite, SWT.BORDER);
		GridData tbGridData = new GridData(GridData.FILL_HORIZONTAL);
		tbGridData.horizontalSpan = 2;
		toolBar.setLayoutData(tbGridData);
		
		openModelItem = new ToolItem(toolBar, SWT.PUSH);
		openModelItem.setToolTipText("Open file");
		openModelItem.setImage(openDocImg);
		openModelItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{ openModelFile(); }
		});
		
		showLogConnsItem = new ToolItem(toolBar, SWT.PUSH);
		Image logConnsImg = new Image(Display.getCurrent(), _menuImgDir + "/logical-connection.png");
		showLogConnsItem.setImage(logConnsImg);
		showLogConnsItem.setToolTipText("Toggle logical connections");
		showLogConnsItem.setEnabled(false);
		showLogConnsItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{ toggleLogicalConnections(); }
		});
		
		// *****************************************************************
		// TODO: Test for additional window, remove later!
//		final VisWindow testWin = new VisWindow();
//		testItem = new ToolItem(toolBar, SWT.PUSH);
//		testItem.setText("Don't press!");
//		testItem.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{ 
//				testWin.composite = new Composite(testWin.child.shell(), SWT.BORDER);
//				testWin.composite.setLayout(new GridLayout(2, false));
//				testWin.composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//				testWin.canvas = new Canvas(testWin.composite, SWT.NO_BACKGROUND 
//						 | SWT.NO_REDRAW_RESIZE
//						 | SWT.BORDER
//						 | SWT.V_SCROLL 
//						 | SWT.H_SCROLL);
//				
//				testWin.setupVisArea();
//				
//				GridData testCanvasGridData = new GridData(GridData.FILL_BOTH);
//				testWin.canvas.setLayoutData(testCanvasGridData);
//			    testWin.canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
//			    
//				testWin.ndm = new NetworkDisplayManager(testWin.figure, MMLDlightParser.net);
//				testWin.ndm.setState(MMLDlightParser.st);
//				testWin.ndm.addVisFigs();
//
//				testWin.display(); 
//			}
//		});
		// *****************************************************************
		
		// *****************************************************************
		// TODO: Button to open alarm processing window, remove later!
//		final ScenarioFilteringWindow sfw = new ScenarioFilteringWindow(null);
//		testItem = new ToolItem(toolBar, SWT.PUSH);
//		testItem.setText("Alarm processing");
//		testItem.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				sfw.display();
//			}
//		});
		// *****************************************************************

		toolBar.pack();

		// Visualisation Canvas
		canvas = new Canvas(composite, SWT.NO_BACKGROUND 
									 | SWT.NO_REDRAW_RESIZE
									 | SWT.BORDER
									 | SWT.V_SCROLL 
									 | SWT.H_SCROLL);
		GridData canvasGridData = new GridData(GridData.FILL_BOTH);
		canvas.setLayoutData(canvasGridData);
	    canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		origin = new Point(0, 0);

		hBar = canvas.getHorizontalBar();
		hBar.setEnabled(false);

		hBar.addListener(SWT.Selection, new Listener() {
            @Override
			public void handleEvent(Event e) {
				handleHBarEvent(e);
			}
		});

		vBar = canvas.getVerticalBar();
		vBar.setEnabled(false);

		vBar.addListener(SWT.Selection, new Listener() {
            @Override
			public void handleEvent(Event e) {
				handleVBarEvent(e);
			}
		});

		canvas.addListener(SWT.Resize, new Listener() {
            @Override
			public void handleEvent(Event e) {
				handleCanvasResizeEvent(e);
			}
		});

		canvas.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if (mouseXL != null)
					mouseXL.setText(e.x + ", " + e.y);
			}
		});
		
//		_lws = new LightweightSystem(canvas);
		
		// Composite where the components' stats are displayed
		propsComp = new Composite(composite, SWT.BORDER);
		FillLayout propsCompGL = new FillLayout();
		propsComp.setLayout(propsCompGL);
		GridData propsGridData = new GridData(SWT.FILL, SWT.FILL, false, true);
		propsGridData.widthHint = 300;
		propsComp.setLayoutData(propsGridData);
		
		// Composite hosting the simulation scalebar
		simComp = new Composite(composite, SWT.BORDER);
		GridLayout simGridLayout = new GridLayout(3, false);
		simComp.setLayout(simGridLayout);
		GridData simCompGD = new GridData(GridData.FILL_HORIZONTAL);
		simCompGD.heightHint = 60;
		simComp.setLayoutData(simCompGD);
		simLabel = new Label(simComp, SWT.NULL);
		GridData simLabelGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		simLabel.setLayoutData(simLabelGD);
		simLabelGD.horizontalSpan = 3;
		simLabel.setText("Simulation Progress: ");
		scale = new Scale(simComp, SWT.BORDER);
		GridData scaleGD = new GridData(GridData.FILL_HORIZONTAL);
		scale.setLayoutData(scaleGD);
		scale.setSize(200, 64);
		scale.setMaximum(40);
		scale.setPageIncrement(5);
// TODO: Is scale used otherwise?
        //		sm.setScale(scale);
		simLeftBut = new Button(simComp, SWT.BORDER);
		Image leftArrowImg = new Image(Display.getCurrent(), _menuImgDir + "/arrow-left.png");
		simLeftBut.setImage(leftArrowImg);
		simLeftBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
                _ss.previous();
//				previousEvent();
				// sm.goToPreviousState();
			}
		});
		simRightBut = new Button(simComp, SWT.BORDER);
		Image rightArrowImg = new Image(Display.getCurrent(), _menuImgDir + "/arrow-right.png");
		simRightBut.setImage(rightArrowImg);
		simRightBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
                _ss.nextEvent();
			}
		});

		_cc = new ComponentControl(propsComp, _opt, _ss, _rand);

		// Composite in the lower right corner of the main screen
		miniStatsComp = new Composite(composite, SWT.BORDER);
		GridLayout miniStatsCompGL = new GridLayout();
		miniStatsComp.setLayout(miniStatsCompGL);
		GridData miniStatsGD = new GridData();
		miniStatsGD.heightHint = 60;
		miniStatsGD.widthHint = 300;
		miniStatsComp.setLayoutData(miniStatsGD);  
		mouseXL = new Label(miniStatsComp, SWT.NULL);
		GridData mouseXLGD = new GridData(GridData.FILL_HORIZONTAL);
		mouseXL.setLayoutData(mouseXLGD);
		mouseXL.setText("<Mouse coordinates>");
		
		{
            final String mmldModel = GuiOptions.MODEL_ADDRESS.getOption(_opt, null, false, null);
			if (mmldModel != null) {
                setModel(mmldModel,true,false);
                final String vmapFile = GuiOptions.VISMAP_ADDRESS.getOption(_opt, null, false, null);
                if (vmapFile != null) {
                    setVmap(vmapFile,true,false);
                }
			}
		}
        
		child.shell().setMenuBar(menu);
		child.shell().open();
	}

	/**
	 * Invokes a parser to load either a network of components,
	 * or a visualisation mapping.
	 * 
	 * @param content Determines what is to be loaded, can be either 
	 * "components" or "mapping", i.e. this determines which parser will
	 * be called.
	 * @return true upon success, false otherwise.
	 */
	
	public boolean parseInputFile(String content) 
	{
		FileDialog fd = new FileDialog(child.shell(), SWT.OPEN);

		fd.setText("Open");
        final String dir;
		String[] filterExt;
		if (content.equals("components")) {
			filterExt = new String[]{ "*.mmld", "*.*" };
            dir = GuiOptions.MODEL_DIR.getOption(_opt, null, false, DEFAULT_MODEL_DIR);
        } else {
			filterExt = new String[]{ "*.vmap", "*.*" };
            dir = GuiOptions.VISMAP_DIR.getOption(_opt, null, false, DEFAULT_VISMAP_DIR);
        }
		fd.setFilterPath(dir);
			
		fd.setFilterExtensions(filterExt);

		String selected = fd.open();
		try {
			DataLoader loader;
			
			if (selected != null) {
				/* Model file, i.e., a file with component definitions */
				if (content.equals("components")) {
					_networkAddress = selected;
					loader = new MMLDModelLoader(URI.create(
							"file://" + selected.toString()).toURL().getPath());
				/* Visualisation Mapping */
				} else {
					loader = new VisMapLoader(URI.create(
							"file://" + selected.toString()).toURL().getPath());
				}
			} 
			else
				return false;

			if (!loader.load()) {
				MessageBox messageBox = new MessageBox(child.shell(), 
											SWT.ICON_ERROR | SWT.CANCEL);
				messageBox.setText("Loading of " + content + " failed");
				messageBox.setMessage("No " + content + " found in file. "
						+ "Previous data may be lost, you might have "
						+ "to (re-) load a proper data file.");
				messageBox.open();
				return false;
			}
            
            if (content.equals("components")) {
                _net = MMLDlightParser.net;
                _ss.newNetwork(_net);
                updateImpossibleEvents();
            } else {
				//notifyStateHandlers();
			}
		} 
		catch (MalformedURLException e) {
            System.err.println(e);
			MessageBox messageBox = new MessageBox(child.shell(), 
										SWT.ICON_ERROR | SWT.CANCEL);
			messageBox.setText("MalformedURLException thrown");
			messageBox.setMessage(e.toString());
			messageBox.open();
		}
		catch (IOException e) {
            System.err.println(e);
			MessageBox messageBox = new MessageBox(child.shell(), 
										SWT.ICON_ERROR | SWT.CANCEL);
			messageBox.setText("IOException thrown");
			messageBox.setMessage(e.toString());
			messageBox.open();
		}

		return true;
	}
    
    
	// Updates the impossible events. 
    private void updateImpossibleEvents() {
        _impossibleEvents.clear();
        final List<String> pats = GuiOptions.IMPOSSIBLE_EVENTS_PATTERNS.getOptions(_opt);
        _impossibleEvents.addAll(util.Util.findEvents(_net, pats));
    }
	
	@Override
	public void setupVisArea() 
	{
		super.setupVisArea();
		
		ndm = new NetworkDisplayManager(figure, _net);
		ndm.setState(MMLDlightParser.st);
		ndm.addVisFigs(_ss);
        
        ndm.addComponentListener(_cc);

//		cm.setNetwork(MMLDlightParser.net);
//		em.setNetwork(MMLDlightParser.net);
		
		hBar.setEnabled(true);
		vBar.setEnabled(true);
		handleVBarEvent(null);
		handleHBarEvent(null);
	}

	// Do not delete method! Remove comment when used.
	@SuppressWarnings("unused")  
	private void clearMemory() 
	{
		if (_net != null)
			_net.removeAllComponents();
		visFigs.clear();
	}

	private void setMenu() 
	{
		menu = new Menu(child.shell(), SWT.BAR);
		
		{ // File
			final MenuItem file = new MenuItem(menu, SWT.CASCADE);
			file.setText("&File");
			final Menu filemenu = new Menu(child.shell(), SWT.DROP_DOWN);
			file.setMenu(filemenu);

			openItem = new MenuItem(filemenu, SWT.PUSH);
			openModelMappingItem =	new MenuItem(filemenu, SWT.PUSH);

			openModelMappingItem.setEnabled(false);
			
			{
				openItem.setText("&Open Model File...\tCTRL+O");
				openItem.setAccelerator(SWT.CTRL + 'O');
				
				class Open implements SelectionListener 
				{
					@Override
					public void widgetSelected(SelectionEvent event) 
					{ openModelFile(); }

					@Override
					public void widgetDefaultSelected(SelectionEvent event) 
					{ }
				}

				openItem.addSelectionListener(new Open());
			}

			{
				openModelMappingItem.
					setText("Open Visualisation &Mapping...\tCTRL+M");
				openModelMappingItem.setAccelerator(SWT.CTRL + 'M');
				
				class OpenModelMapping implements SelectionListener
				{
					@Override
					public void widgetSelected(SelectionEvent event)
					{ openVisMapFile();	}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) 
					{
					}
				}
				
				openModelMappingItem.
						addSelectionListener(new OpenModelMapping());
			}
			
			final MenuItem fileMenuItem = new MenuItem(filemenu, SWT.SEPARATOR);

			{
				final MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
				exitItem.setText("E&xit\tCTRL+Q");
				exitItem.setAccelerator(SWT.CTRL + 'Q');
				exitItem.addSelectionListener(new SelectionAdapter() {
                    @Override
					public void widgetSelected(SelectionEvent e) {
						MessageBox messageBox = new MessageBox(child.shell(),
								SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						messageBox.setMessage("Do you really want to exit?");
						messageBox.setText("Exiting Application");
						int response = messageBox.open();
						if (response == SWT.YES)
							System.exit(0);
					}
				});
			}
		}

//		{ // Scenario
//			final MenuItem sce = new MenuItem(menu, SWT.CASCADE);
//			sce.setText("&Scenarios");
//			final Menu scenarioMenu = new Menu(child.shell(), 
//											SWT.DROP_DOWN);
//			sce.setMenu(scenarioMenu);
//
//			sm = new ScenarioManager(child.shell(), scenarioMenu, _rand, _ss);
//		}

		{ // New scenario
			final MenuItem sce = new MenuItem(menu, SWT.CASCADE);
			sce.setText("&Scenarios");
			//sce.setText("NEWSCENARIOS");
			final Menu scenarioMenu = new Menu(child.shell(), 
											SWT.DROP_DOWN);
			sce.setMenu(scenarioMenu);

            final ScenarioMenu sm = new ScenarioMenu(child.shell(), _ss, scenarioMenu, _opt);
			//sm = new ScenarioManager(child.shell(), scenarioMenu, _rand, this);
		}
		
		{ // Components
			final MenuItem cpt= new MenuItem(menu, SWT.CASCADE);
			cpt.setText("&Components");
			final Menu cptMenu = new Menu(child.shell(), 
									SWT.DROP_DOWN);
			cpt.setMenu(cptMenu);

			//cm = 
            new ComponentManager(Display.getCurrent(), child.shell(), cptMenu, _opt, _ss);
		}

		{ // Events
			final MenuItem evt = new MenuItem(menu, SWT.CASCADE);
			evt.setText("&Events");
			final Menu evtMenu = new Menu(child.shell(), SWT.DROP_DOWN);
			evt.setMenu(evtMenu);

            EventManager.createEventManager(evtMenu, _ss);
		}
		
		{ // Diagnosis

			final MenuItem dgs = new MenuItem(menu, SWT.CASCADE);
			dgs.setText("&Diagnosis");
			final Menu dgsMenu = new Menu(child.shell(), SWT.DROP_DOWN);
			dgs.setMenu(dgsMenu);

			dm = new DiagnosisManager(child.shell(), dgsMenu);
		}
		
//		{ // TMP: GlobalTransition
//			final MenuItem gtmi = new MenuItem(menu, SWT.CASCADE);
//			gtmi.setText("&Global Transition");
//			final Menu gtMenu = new Menu(child.shell(), SWT.DROP_DOWN);
//			gtmi.setMenu(gtMenu);
//            final MenuItem m = new MenuItem(gtMenu, SWT.PUSH);
//            m.setText("Global Transition");
//            m.addSelectionListener(new SelectionListener() {
//			
//                @Override
//                public void widgetSelected(SelectionEvent arg0) {
//                	final GlobalTransitionExplorer ex = 
//                        new GlobalTransitionExplorer(_opt);
//                    ex.display();
//                }
//			
//                @Override
//                public void widgetDefaultSelected(SelectionEvent arg0) {
//                }
//            });
//		}
	}

	public boolean openModelFile() 
	{
		if (parseInputFile("components")) {
			setupVisArea();
            
//			getScenarioManager().
//				createTimedScenario(new MapTimedState(MMLDlightParser.st),
//					"Initial scenario");
			openItem.setEnabled(false);
			openModelItem.setEnabled(false);
			openModelMappingItem.setEnabled(true);
			showLogConnsItem.setEnabled(true);
			getNetworkDisplayManager().toggleLogicalConnections();
			
			// TODO: The following call is somewhat hackish
			// but the resize handler (un)sets the canvas
			// scrollbars, and those need to be checked after
			// loading a model.  So we may as well call the
			// handler explicitly, i.e., without an event.
			handleCanvasResizeEvent(null);
			
			return true;
		}
		
		return false;
	}

	public void toggleLogicalConnections()
	{
		getNetworkDisplayManager().toggleLogicalConnections();
	}
	
	/** 
	 * @see comment inside openModelFile() 
	 */

	public boolean openVisMapFile()
	{
		if (parseInputFile("mapping")) {
			handleCanvasResizeEvent(null);
			_ss.notifyStateChanged();
            //notifyStateHandlers();
			return true;
		}
		
		return false;
	}
	
	public void showConnectionControls(YAMLDConnection conn) 
	{
		System.out.println("Clicked on connection " + conn.name());
	}

	public void handleCanvasResizeEvent(Event e) 
	{
		if (figure != null) {
			if ((canvas.getClientArea().width >= ndm.figDimensions().x)
					&& x_off != 0) {
				ndm.updateCompCoords(-x_off, 0);
				x_off = 0;
				origin.x = 0;
			}

			if ((canvas.getClientArea().height >= ndm.figDimensions().y)
					&& y_off != 0) {
				ndm.updateCompCoords(0, -y_off);
				y_off = 0;
				origin.y = 0;
			}

			hBar.setMaximum(ndm.figDimensions().x);
			hBar.setThumb(Math.min(ndm.figDimensions().x, canvas
					.getClientArea().width));

			vBar.setMaximum(ndm.figDimensions().y);
			vBar.setThumb(Math.min(ndm.figDimensions().y, canvas
					.getClientArea().height));

			canvas.getVerticalBar().
			  setVisible(ndm.figDimensions().y > canvas.getClientArea().height);
			canvas.getHorizontalBar().
			  setVisible(ndm.figDimensions().x > canvas.getClientArea().width);
		}
		else {
			canvas.getVerticalBar().setVisible(false);
			canvas.getHorizontalBar().setVisible(false);			
		}
	}

	public void handleHBarEvent(Event e) 
	{
		if (figure != null) {
			hBar.setMaximum(ndm.figDimensions().x);
			hBar.setThumb(Math.min(ndm.figDimensions().x, canvas
					.getClientArea().width));

			int hSelection = hBar.getSelection();
			int destX = -hSelection - origin.x;
			canvas.scroll(destX, 0, 0, 0, 
						  figure.getBounds().width, 
						  figure.getBounds().height, 
						  false);
			origin.x = -hSelection;
			ndm.updateCompCoords(destX, 0);

			x_off += destX;
		}
		canvas.redraw();
	}

	public void handleVBarEvent(Event e) 
	{
		if (figure != null) {
			vBar.setMaximum(ndm.figDimensions().y);
			vBar.setThumb(Math.min(ndm.figDimensions().y, canvas
					.getClientArea().height));

			int vSelection = vBar.getSelection();
			int destY = -vSelection - origin.y;
			canvas.scroll(0, destY, 0, 0, figure.getBounds().width, figure
					.getBounds().height, false);
			origin.y = -vSelection;
			ndm.updateCompCoords(0, destY);

			y_off += destY;
		}
		canvas.redraw();
	}

	public NetworkDisplayManager getNetworkDisplayManager() 
	{
		return ndm;
	}

//	public ScenarioManager getScenarioManager() 
//	{
//		return sm;
//	}

//	public EventManager getEventManager() 
//	{
//		return em;
//	}

    // TO REMOVE
    //@Override
//	public void elapseTime(double d) 
//	{
//		sm.elapseTime(d);
//	}

//	public void previousEvent() 
//	{
//		sm.goToPreviousState();
//	}

    // TO REMOVE
    //@Override
//	public void nextEvent() 
//	{
//		if (!sm.goToNextState()) {
//			sm.nextEvent();
//		}
//	}

    // TO REMOVE
    //@Override
//	public boolean elapseOrOneForcedTransition(double d) 
//	{
//		return sm.elapseOrOneForcedTransition(d);
//	}
    
//	public void triggerGlobalTransition(MMLDGlobalTransition tr) 
//	{
//		sm.triggerGlobalTransition(tr);
//	}
    
    
			
	/**
	 * Returns the address of the model.  
	 * 
	 * @return the address where the model was loaded from if there is a model, 
	 * <code>null</code> otherwise.  
	 * */
	public String getNetworkAddress() 
	{
		return _networkAddress;
	}
    
    /**
     * Notifies all the network listeners that the network changed.  
     */
//    public void notifyNetworkChanged() {
//        for (final NetworkListener nl: _networkListeners) {
//            nl.newNetwork(_net);
//        }
//    }

// TO REMOVE
    //    @Override
    public Network getNetwork() {
        return _net;
    }

    // TO REMOVE
    //@Override
//    public boolean isInFinalState() {
//        return sm.getFinalState() == _tstate;
//    }

    // TO REMOVE
    //@Override
//    public boolean canBeTriggered(MMLDTransition tr) {
//        final MMLDGlobalTransition gt = util.Util.computeGlobalTransition(tr, 
//                    _net, _ii.getFinalState().getState(), _impossibleEvents, _rand);
//        return gt != null;
//    }

    // TO REMOVE
    //@Override
//    public void trigger(MMLDTransition tr) {
//        final MMLDGlobalTransition gt = util.Util.computeGlobalTransition(tr, 
//                    _net, _state, _impossibleEvents, _rand);
//        if (gt != null) {
//            sm.triggerGlobalTransition(gt);
//        }
//    }

    // TO REMOVE
    //@Override
//    public boolean setFirst(MMLDTransition tr, YAMLDFormula f) {
//        return sm.setFirst(tr,f);
//    }

    // TO REMOVE
    //@Override
//    public TimedState getFinalState() {
//        return sm.getFinalState();
//    }

    // TO REMOVE
    //@Override
//    public double getCurrentTime() {
//        return sm.getCurrentTime();
//    }

    // TO REMOVE
    //@Override
//    public double willTrigger(YAMLDFormula pr, MMLDTransition tr) {
//        return sm.willTrigger(pr, tr);
//    }

    // TO REMOVE
    //@Override
//    public void setSchedule(MMLDTransition trans, YAMLDFormula f, double d) {
//        sm.setSchedule(trans, f, d);
//    }

    @Override
    public void newStateHandler(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newStateHandler(TimedState s) {
        
		simLabel.setText("Simulation Progress (" 
				+ _ss.getCurrentTime() + " / " 
				+ _ss.getFinalTime() + ")");
//				+ new DecimalFormat("0.####").format(_ss.getCurrentTime()) + " / " 
//				+ new DecimalFormat("0.####").format(_ss.getFinalTime()) + ")");
        scale.setIncrement(1);
        scale.setMaximum(_ss.nbTransitions());
        scale.setSelection(_ss.getCurrentTransitionPosition());
        scale.setEnabled(false);
    }
}
