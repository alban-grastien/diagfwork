package lang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class VisMapLoader extends DataLoader
{
	private Reader input;

	/**
	 * Loads the visualisation mapping from text file pointed at by filename.
	 * 
	 * @param filename String representation of filename URI
	 * @throws IOException
	 */
	public VisMapLoader(String filename) throws IOException
	{
        InputStream inputStream = new FileInputStream(filename);
        this.input = new InputStreamReader(inputStream);
	}

	/**
	 * Load visualisation mapping
	 * @return true if mapping was loaded, false otherwise.
	 */
	public boolean load()
	{
		try {
			VisMapLexer lexer = new VisMapLexer(new ANTLRReaderStream(input));
	        CommonTokenStream tokens = new CommonTokenStream(lexer);
	        VisMapParser parser = new VisMapParser(tokens);

	        // First remove all existing mappings, in case a mapping is
	        // re-loaded by the user.
	        VisMap.getSingletonObject().delVisMappings();
	        
	        parser.mapping();
	        
	        return true;
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
	    } 
		catch (RecognitionException e) {
			throw new RuntimeException(e);
	    }
	}
}
