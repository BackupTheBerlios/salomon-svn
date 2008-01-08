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
package salomon.engine.domain;

/**
 * @author Nikodem.Jura
 * 
 */
public class DomainDataSource implements IDomainDataSource {
	private long _dataSourceId;

	private String _url;

	private String _password;

	private String _userName;

	/**
	 * @return the dataSourceId
	 */
	public long getDataSourceId() {
		return _dataSourceId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return _url;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return _userName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		_url = url;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		_userName = userName;
	}

	/**
	 * @param dataSourceId
	 *            the dataSourceId to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setDataSourceId(long dataSourceId) {
		_dataSourceId = dataSourceId;
	}
}
