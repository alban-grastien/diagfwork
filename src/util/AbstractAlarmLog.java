package util;

import java.util.Iterator;

/**
 * An abstract implementation of the alarm log.  All methods but 
 * {@link #get(int)} and {@link #nbEntries()} are implemented 
 * in this implementation.  
 * 
 * @author Alban Grastien
 * */
public abstract class AbstractAlarmLog implements AlarmLog {

	@Override 
	public Iterator<AlarmLog.AlarmEntry> iterator() {
		return new Iterator<AlarmLog.AlarmEntry>() {

			int _pos = 0;
			
			@Override
			public boolean hasNext() {
				return _pos < nbEntries();
			}

			@Override
			public AlarmEntry next() {
				final AlarmEntry entry = get(_pos);
				_pos++;
				return entry;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Cannot remove entries from an alarm log.");
			}
		};
	}
	
	@Override 
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		for (int i=0 ; i<nbEntries() ; i++) {
			result.append("// entry number " + i + "\n");
			result.append(get(i).toFormattedString());
		}
		
		return result.toString();
	}
	
}
