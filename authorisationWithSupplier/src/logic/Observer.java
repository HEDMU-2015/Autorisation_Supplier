package logic;

import java.util.List;


public interface Observer {
	/**
	 * Implementering af update må kun benytte get metoder i controlleren.
	 * @param tilstande
	 */
	public void update(List<Tilstand> tilstande);

}
