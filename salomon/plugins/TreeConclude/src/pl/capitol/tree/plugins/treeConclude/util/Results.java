package pl.capitol.tree.plugins.treeConclude.util;

import java.util.Collection;

import salomon.plugin.IResult;

public class Results implements IResult {

	private boolean success = false;
	private String errorMessage = "Nale¿y najpierw uruchomiæ plugin, by otrzymaæ rezultat.";
	private String treeName;
	private int allTests;
	private int positiveTests;
	private Collection<Object[]> invalidRows;
	
	private String result = "";
	public void parseResult(String result) {
		this.result = result;
	}

	public String resultToString() {
		return this.result;
	}

	public boolean isSuccessful() {
		return success;
	}

	public int getAllTests() {
		return allTests;
	}

	public void setAllTests(int allTests) {
		this.allTests = allTests;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getPositiveTests() {
		return positiveTests;
	}

	public void setPositiveTests(int positiveTests) {
		this.positiveTests = positiveTests;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Collection<Object[]> getInvalidRows() {
		return invalidRows;
	}

	public void setInvalidRows(Collection<Object[]> invalidRows) {
		this.invalidRows = invalidRows;
	}
	

}
