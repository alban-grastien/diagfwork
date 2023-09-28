package gui;

import edu.supercom.util.Options;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A <code>GlobalTransitionExplorer</code>, i.e., a global transition explorer, 
 * is a window that allows to build global transitions from a given state.  
 */
public class GlobalTransitionExplorer {
    /**
     * The list of options.  
     */
    private final Options _opt;
	private final Shell _shell;
    private Composite _transComposite;
    private Button _button;
	
	public GlobalTransitionExplorer(Options opt) {
        _opt = opt;
        _shell = new Shell(SWT.RESIZE | SWT.BORDER);
	}
    
    public void display() {
		_shell.setText("Global Transition Explorer");

        {
                GridLayout layout = new GridLayout(1, false);
                GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, false, false);
                _shell.setLayout(layout);
                _shell.setLayoutData(compStatsCompGD);
        }
        
        {
            final Composite treeComposite = new Composite(_shell, 0);
            
            {
                RowLayout layout = new RowLayout();
                layout.type = SWT.VERTICAL;
                treeComposite.setLayout(layout);
            }
            
            final Label label = new Label(treeComposite, 0);
            label.setText("TODO");
        }
        
        {
            final Composite buttonComposite = new Composite(_shell, 0);
           
            {
                GridLayout layout = new GridLayout(2, false);
                GridData compStatsCompGD = new GridData(SWT.FILL, SWT.FILL, false, false);
                buttonComposite.setLayout(layout);
                buttonComposite.setLayoutData(compStatsCompGD);
            }
            
            _button = new Button(buttonComposite, 0);
            _button.setText("Submit");
            
            final Button cancel = new Button(buttonComposite, 0);
            cancel.setText("Cancel");
            cancel.addSelectionListener(new SelectionListener() {

                @Override
                public void widgetSelected(SelectionEvent se) {
                    _shell.dispose();
                }

                @Override
                public void widgetDefaultSelected(SelectionEvent se) {
                }
            });

            }

        _shell.pack();
		_shell.open();
        
                
        _shell.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
//                if (ke.character == SWT.ESC) {
//                    _shell.dispose();
//                }
                System.out.println("Key pressed.");
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                System.out.println("Key released.");
            }
        });
    }
    
}
