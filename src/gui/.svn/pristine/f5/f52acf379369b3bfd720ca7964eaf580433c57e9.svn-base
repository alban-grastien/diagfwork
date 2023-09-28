package gui;

import edu.supercom.util.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A <code>GuiOptions</code>, i.e., the gui options, 
 * is a static class that list all the options that the GUI can encounter.  
 * The (currently) preferred way to define option 
 * is to statically define the option in this file.  
 * This way, the options are not scattered in different files.  
 * Plus, it is possible to access all of them.  
 */
public class GuiOptions {
    
    /**
     * The list of options.  
     */
    private final static List<Option> _options = new ArrayList<Option>();
    
    //////////////// The list of options
    public static final Option MODEL_DIR = 
            register("The directory where the model can be found, "
            + "default value is given by MainWindow.DEFAULT_MODEL_DIR "
            + "(or the current directory if that one does not exist)", 
            "moddir", "datadir");
    public static final Option VISMAP_DIR = 
            register("The directory where the visualisation can be found, "
            + "default value is given by MainWindow.DEFAULT_VISMAP_DIR "
            + "(or the current directory if that one does not exist)", 
            "vismapdir", "datadir");
    public static final Option IMGLIB_DIR = 
            register("The directory where the images "
            + "associated with the visualisation map "
            + "can be found; "
            + "default value is given by is VisMapShapeDef.DEFAULT_IMG_DIR", 
            "imglib", "datadir");
    public static final Option MENUIMGLIB_DIR = 
            register("The directory where the images "
            + "used in the menu can be found "
            + "default value is given by is VisMapShapeDef.DEFAULT_MENU_IMG_DIR", 
            "menuimg");
    public static final Option MODEL_ADDRESS = 
            register("The filename where the model can be found, "
            + "if this information is already available at initialisation.", 
            "model", "mod", "net");
    public static final Option VISMAP_ADDRESS = 
            register("The filename where the visualisation map can be found, "
            + "if this information is already available at initialisation.", 
            "vmap", "vismap");
    public static final Option IMPOSSIBLE_EVENTS_PATTERNS = 
            register("The patterns used to determine that an event is impossible.  "
            + "Any event, whose name (without the component name) "
            + "matches one of these patterns, is assumed to be impossible.  "
            + "For an explanation on what an impossible event is, "
            + "see method computeGlobalTransition in util.Util", 
            "@impossible");
    public static final Option GUI_SEED = 
            register("The seed used by the GUI (and the simulator).", "seed");
    //////////////// End of the list
    
    
    // To prevent instantiations of a GuiOptions.  
    private GuiOptions() {}
    
    /**
     * Registers the option defined with the specified explanation 
     * and the specified list of keywords.  
     * 
     * @param explanation the explanation why the option will be used.  
     * @param ks the list of keywords that can refer to this option.  
     * @return an identifier for this option.  
     */
    public static Option register(String explanation, String... ks) {
        final Option opt = new Option(explanation, ks);
        _options.add(opt);
        return opt;
    }
    
    /**
     * Sends the list of options.  
     * 
     * @return the list of options defined for the GUI.  
     */
    public static Collection<Option> getOptions() {
        return _options;
    }
}
