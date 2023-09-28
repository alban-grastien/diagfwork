package lang;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

@Deprecated
public class YAMLDModelLoader extends DataLoader
{
	private Reader input;

	/**
	 * Loads the model from a YAMLD text file pointed at by filename.
	 * 
	 * @param filename String representation of filename URI
	 * @throws IOException
	 */
	public YAMLDModelLoader(String filename) throws IOException
	{
        InputStream inputStream = new FileInputStream(filename);
        this.input = new InputStreamReader(inputStream);
	}

	/**
	 * Load component network.
	 * @return true if components were loaded, false otherwise.
	 */
	public boolean load()
	{
		try {
			YAMLDlightLexer   lexer  = 
				new YAMLDlightLexer(new ANTLRReaderStream(input));
	        CommonTokenStream tokens = new CommonTokenStream(lexer);
	        YAMLDlightParser  parser = new YAMLDlightParser(tokens);
	        parser.net();

	        // TODO: Print loaded components for testing only.
//		    for (YAMLDComponent tempComp : YAMLDlightParser.net.getComponents())
//		    	System.out.println(tempComp.toFormattedString());
		    
		    return !YAMLDlightParser.net.getComponents().isEmpty();
	    } 
		catch (IOException e) {
			throw new RuntimeException(e);
	    } 
		catch (RecognitionException e) {
			throw new RuntimeException(e);
	    }
	}
}
