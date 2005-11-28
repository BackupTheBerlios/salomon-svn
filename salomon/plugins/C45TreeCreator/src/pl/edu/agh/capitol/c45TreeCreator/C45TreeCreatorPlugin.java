package pl.edu.agh.capitol.c45TreeCreator;

import org.apache.log4j.Logger;

import pl.edu.agh.capitol.c45TreeCreator.components.C45TreeCreatorResultComponent;
import pl.edu.agh.capitol.c45TreeCreator.components.C45TreeCreatorSettingsComponent;
import pl.edu.agh.capitol.c45TreeCreator.logic.TreeConstructionTask;
import pl.edu.agh.capitol.c45TreeCreator.util.C45TreeCreatorResult;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;

/**
 * @author Lukasz
 * <br><br>
 * Plugin tworz¹cy drzewa decyzyjne na podstawie danych. Drzewa s¹ tworzone 
 * na bazie struktury <code>IDataSource</code> a zapisywane do <code>ITree</code>.
 * Zaimplementowany algorytm tworzenia drzew to ID3 
 * 
 */
public class C45TreeCreatorPlugin implements IPlugin {

	private static final Logger LOGGER = Logger
			.getLogger(C45TreeCreatorPlugin.class);

	
	/**
	 * G³ówna metoda licz¹ca pluginu. Wykonuje 3 czynnoœci
	 * <li> pobiera z <code>Environment<code/> id DataSource'a i na tej 
	 * podstawie pobiera samego DataSource'a
	 * <li> wykonuje zasadnicze obliczenia - metoda 
	 * <code>performCalculations</code>. Stworzone drzewo jest zapisywane 
	 * dobazy danych 
	 * <li> ustawia w <code>Environment</code> id stworzego drzewa
	 * 
	 */
	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		IDataSource dane = null;
		int result = -1;
		try {
			dane = getIDataSourceFromEnv(eng, env);
			/** ************ Calculations *************** */
			result = performCalculations(dane, eng);
			/** ************ Calculations done ********** */
			IVariable iv = env.createEmpty("tree_name");
			IObject io = new SimpleString("" + result);
			iv.setValue(io);
			env.add(iv);
		} catch (PlatformException e) {
			LOGGER.error("PlatformException occured ", e);

			return getDefaultErrorResult("PlatformException occured "
					+ e.getLocalizedMessage());
		}

		return new C45TreeCreatorResult();
	}

	/**
	 * Zwraca komponent Settingsów (pusty)
	 */
	public ISettingComponent getSettingComponent() {
		return new C45TreeCreatorSettingsComponent();
	}

	/**
	 * Zwraca komponent z rezultatem (pusty)
	 */
	public IResultComponent getResultComponent() {
		return new C45TreeCreatorResultComponent();
	}

	/**
	 * Metoda uruchamiaj¹ce logikê tworzenia drzewa
	 * @param ds DataSource na podstawie którego tworzymy drzewa
	 * @param eng IDataEngine - coby mo¿naby³o pobraæ dane z DataSource'a
	 * @return stworzone drzewko (i tak zapisane do bazy)
	 * @throws PlatformException - w wypadku problemów ze stworzeniem drzewa
	 */
	public int performCalculations(IDataSource ds, IDataEngine eng) throws PlatformException {
		TreeConstructionTask tc = new TreeConstructionTask();
		tc.loadFromDataSource(ds, eng.getTreeManager().getTreeDataSourceData(ds));
		tc.createTree();
		
		return tc.returnResult(eng.getTreeManager(), ds);
	}

	/**
	 * Rezultat zwracany w przypadku wyst¹pienia b³êdu
	 * @param result - deskrpytor b³êdu
	 * @return rezultat (IResult)
	 */
	IResult getDefaultErrorResult(String result) {
		C45TreeCreatorResult res = new C45TreeCreatorResult();
		res.setSuccessful(false);
		res.setResult(result);
		return res;
	}
	
	/**
	 * Metoda pomocnicza pobieraj¹ca IDataSource'a z IEnvironment'u 
	 * @param eng
	 * @param env
	 * @return <code>IDataSource</code> z danymi
	 * @throws PlatformException
	 */
	public IDataSource getIDataSourceFromEnv(IDataEngine eng, IEnvironment env)
			throws PlatformException {
		try {
			int id = ((SimpleInteger)env.getVariable("DATA_SOURCE_ID").getValue()).getValue();

			return eng.getTreeManager().getTreeDataSource(id);
			//zaslepka :/
			/*return eng.getTreeManager().getTreeDataSource(18);*/
		} catch (NullPointerException e) {
			LOGGER.error("Variable DATA_SOURCE_ID not set in env", e);
			throw new PlatformException("Variable DATA_SOURCE_ID not set in env");
		} catch (PlatformException e) {
			LOGGER.error("PlatformException occured ", e);
			throw new PlatformException("PlatformException occured ");
		} catch (Throwable e) {
			LOGGER.error("Exception occured ", e);
			throw new PlatformException("Exception occured ");
		}
	}
}
