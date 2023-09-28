package lang;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;


public class MMLDModelLoader extends DataLoader
{
	private Reader input;

	/**
	 * Loads the model from a YAMLD text file pointed at by filename.
	 * 
	 * @param filename String representation of filename URI
	 * @throws IOException
	 */
	public MMLDModelLoader(String filename) throws IOException
	{
        InputStream inputStream = new FileInputStream(filename);
        this.input = new InputStreamReader(inputStream);
	}

	/**
	 * Load component network.
	 * @return true if components were loaded, false otherwise.
	 */
    @Override
	public boolean load()
	{
		try {
			MMLDlightLexer   lexer  = 
				new MMLDlightLexer(new ANTLRReaderStream(input));
	        CommonTokenStream tokens = new CommonTokenStream(lexer);
	        MMLDlightParser  parser = new MMLDlightParser(tokens);
	        parser.net();

	        // TODO: Print loaded components for testing only.
//		    for (YAMLDComponent tempComp : MMLDlightParser.net.getComponents())
//		    	System.out.println(tempComp.name());
		    
		    return !MMLDlightParser.net.getComponents().isEmpty();
	    } 
		catch (IOException e) {
			throw new RuntimeException(e);
	    } 
		catch (RecognitionException e) {
			throw new RuntimeException(e);
	    }
	}
}
