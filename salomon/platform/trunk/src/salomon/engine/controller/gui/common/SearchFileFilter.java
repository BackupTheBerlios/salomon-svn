/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.engine.controller.gui.common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SearchFileFilter extends FileFilter
{

	String _description;

	private String _extension;

	public SearchFileFilter(String extension, String description)
	{
		_extension = extension;
		_description = description;
	}

	public boolean accept(File file)
	{
		if (file != null) {
			if (file.isDirectory()) {
				return true;
			}
			String ext = getExtension(file);
			if (ext != null) {
				return _extension.equalsIgnoreCase(ext);
			}
		}
		return false;
	}

	public String getDescription()
	{
		return _description + " (*." + _extension + ")";
	}

	public void setDescription(String description)
	{
		_description = description;
	}

	private String getExtension(File f)
	{
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}
}