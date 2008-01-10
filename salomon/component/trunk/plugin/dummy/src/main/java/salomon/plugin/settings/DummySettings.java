/*
 * Copyright (C) 2008 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */
/**
 * 
 */
package salomon.plugin.settings;

import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * @author Nikodem.Jura
 * 
 */
public class DummySettings extends SimpleStruct implements ISettings {

	private String _word;

	private static final String WORD = "word";

	public void init(IObject object) {
		SimpleStruct struct = (SimpleStruct) object;

		// setting struct fields
		SimpleString word = (SimpleString) struct.getField(WORD);
		setField(WORD, word);
		_word = word.getValue();
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return _word;
	}

	/**
	 * @param word
	 *            the word to set
	 */
	public void setWord(String word) {
		_word = word;
		setField(WORD, _word);
	}
	
	@Override
	public String toString() {
		return WORD + "=" + _word;
	}
}
