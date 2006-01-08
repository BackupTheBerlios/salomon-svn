package pl.capitol.tree.plugins.dataloader;

import org.apache.log4j.Logger;

import pl.capitol.tree.plugins.dataloader.components.TestAttrSetResultComponent;
import pl.capitol.tree.plugins.dataloader.components.TestAttrSetSettingComponent;
import pl.capitol.tree.plugins.util.TestAttrSetResults;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.attribute.*;
import salomon.platform.data.attribute.description.*;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TestAttrSetPlugin implements IPlugin {

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		test1(eng);
		test2(eng);
		return new TestAttrSetResults();
	}

	private void test2(IDataEngine eng) {
		try {
			IAttributeManager attributeManager = eng.getAttributeManager();
			IAttributeSet [] iAttributeSets = attributeManager.getAll();
			
		} catch (PlatformException e) {
			LOGGER.error("", e);
		}
	}

	private void test1(IDataEngine eng) {
		try {
			IAttributeManager attributeManager = eng.getAttributeManager();
			IAttributeDescription nameAttributeDescription = attributeManager
					.createStringAttributeDescription("Name");
			IAttributeDescription ageAttributeDescription = attributeManager
					.createIntegerAttributeDescription("Age");
			IAttributeDescription genderAttributeDescription = attributeManager
					.createEnumAttributeDescription("Gender", new Object[] {
							"M", "F" });
			IAttributeDescription numberAttributeDescription = attributeManager
					.createRealAttributeDescription("FavouriteNumber");
			IAttributeDescription dateAttributeDescription = attributeManager
					.createDateAttributeDescription("DateOfBirth");

			IAttribute krzychoNameAttr = nameAttributeDescription
					.createAttribute("Krzysztof");
			IAttribute krzychoAgeAttr = ageAttributeDescription
					.createAttribute("24");
			IAttribute krzychoGenderAttr = genderAttributeDescription
					.createAttribute("M");
			IAttribute krzychoNumberAttr = numberAttributeDescription
					.createAttribute(Math.PI);
			IAttribute krzychoDateAttr = dateAttributeDescription
					.createAttribute(new java.sql.Date(System
							.currentTimeMillis()));

			IAttribute genoNameAttr = nameAttributeDescription
					.createAttribute("Genowefa");
			IAttribute genoAgeAttr = ageAttributeDescription
					.createAttribute("24");
			IAttribute genoGenderAttr = genderAttributeDescription
					.createAttribute("F");
			IAttribute genoNumberAttr = numberAttributeDescription
					.createAttribute(Math.E);
			IAttribute genoDateAttr = dateAttributeDescription
					.createAttribute(new java.sql.Date(0));

			IAttributeSet peopleSet = attributeManager
					.createAttributeSet(new IAttributeDescription[] {
							nameAttributeDescription, ageAttributeDescription,
							genderAttributeDescription,
							numberAttributeDescription,
							dateAttributeDescription });
			peopleSet.add(new IAttribute[] { krzychoNameAttr, krzychoAgeAttr,
					krzychoGenderAttr, krzychoNumberAttr, krzychoDateAttr });
			peopleSet.add(new IAttribute[] { genoNameAttr, genoAgeAttr,
					genoGenderAttr, genoNumberAttr, genoDateAttr });
			peopleSet.setName("PeopleSet");
			peopleSet.setInfo("This set contains peoples' defnitions");
			attributeManager.add(peopleSet); // dopiero teraz wszystko
		} catch (PlatformException e) {
			LOGGER.error("", e);
		}
		// wszystko
		// dodawana jest do bazy, albo koñczona jest transakcja
	}

	public ISettingComponent getSettingComponent() {
		return new TestAttrSetSettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new TestAttrSetResultComponent();
	}

	private static final Logger LOGGER = Logger
			.getLogger(TestAttrSetPlugin.class);

}