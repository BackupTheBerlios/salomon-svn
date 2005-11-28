package pl.edu.agh.capitol.c45TreeCreator.util;

import salomon.plugin.IResult;

public class C45TreeCreatorResult implements IResult{
	private String result = "";
	private boolean successful = false;
	public void parseResult(String result) {
		this.setResult(result);
	}

	public String resultToString() {
		return this.getResult();
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
