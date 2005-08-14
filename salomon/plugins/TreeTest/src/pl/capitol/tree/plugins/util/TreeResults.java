package pl.capitol.tree.plugins.util;

import salomon.plugin.IResult;

public class TreeResults implements IResult {

	private String result = "";
	public void parseResult(String result) {
		this.result = result;
	}

	public String resultToString() {
		return this.result;
	}

	public boolean isSuccessful() {
		return true;
	}

}
