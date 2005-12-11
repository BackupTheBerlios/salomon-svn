package pl.capitol.tree.plugins.treeConclude.util;

import salomon.plugin.IResult;

public class Results implements IResult {

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
