
package assign10;

import java.util.Comparator;

/**
 * A string comparator for testing purposes.
 * @author Joseph Porta
 *
 */
public class StringComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return o1.length() - o2.length();
	}

}
