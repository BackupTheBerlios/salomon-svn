package salomon;


import java.io.File;
import java.sql.SQLException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.SQLConsole;
import salomon.engine.SalomonEngineContext;
import salomon.engine.controller.IController;
import salomon.engine.controller.LibraryController;
import salomon.engine.database.DBManager;
import salomon.engine.platform.IManagerEngine;
import salomon.platform.exception.ConfigurationException;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;

/**
 * Class starts application execution.
 */
public final class Starter
{

    private static final Logger LOGGER = Logger.getLogger(Starter.class);

    private static Starter _instance;

    static {
        try {
// FIXME:            
//            PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
//            PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
//            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (Exception e) {
            LOGGER.warn("Cannot set look&feel!", e); //$NON-NLS-1$
        }
    }

    /**
     * 
     * @uml.property name="_contoroller"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IController _contoroller;

    /**
     * 
     * @uml.property name="_managerEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IManagerEngine _managerEngine;

    private Options _options;

    private Starter()
    {
        // empty body
    }

    /**
     * creates library controller
     * 
     * @return the created LibraryController
     */
    public static LibraryController createLibraryController()
    {
        return Starter.startLibrary();
    }

    /**
     * performs clean-up and exits
     */
    public static void exit()
    {
        getInstance().exitImpl();
    }

    /**
     * main method for starting Salomon
     * 
     * @param args parameters from the command line
     */
    public static void main(String[] args)
    {
        getInstance().startApplication(args);
    }

    private void startApplication(String[] args)
    {
        // currently only LOCAL mode is supported,
        // other modes have been remove from config file
        // reading configuration
        try {
            Config.readConfiguration();
        } catch (ConfigurationException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CONFIGURATION_ERROR"));
            exit();
        }

        // parsing command line arguments        
        initOptions();
        try {
            parseOptions(args);
        } catch (ParseException e) {
            LOGGER.fatal(e.getLocalizedMessage());
        }
    }

    private void initOptions()
    {
        _options = new Options();
        _options.addOption("h", "help", false,
                "Print help for this application");
        _options.addOption("f", "file", true, "Batch file to be executed");
    }

    private void parseOptions(String[] args) throws ParseException
    {
        BasicParser parser = new BasicParser();
        CommandLine cmdLine = parser.parse(_options, args);

        // if any options provided process them
        // otherwise run application in standard way
        if (cmdLine.getArgs().length > 0 || cmdLine.getOptions().length > 0) {
            if (cmdLine.hasOption("h")) {
                usage();
            } else if (cmdLine.hasOption("f")) {
                try {
                    executeBatch(cmdLine.getOptionValue("f"));
                } catch (PlatformException e) {
                    LOGGER.fatal(e.getLocalizedMessage());
                }
            } else {
                usage();
            }
        } else {
            LOGGER.info("### Application started ###");
            startLocal();
        }
    }

    private void executeBatch(String fileName) throws PlatformException
    {
        File file = new File(fileName);
        if (file.exists() && file.canRead()) {
            DBManager manager = new DBManager();
            try {
                manager.connect();
                SQLConsole console = new SQLConsole(manager);
                console.executeBatch(file);
                manager.disconnect();
            } catch (Exception e) {
                throw new PlatformException(e);
            } finally {
                try {
                    manager.disconnect();
                } catch (SQLException e) {
                    LOGGER.fatal(e.getLocalizedMessage());
                }
            }
        } else {
            throw new PlatformException("Cannot open file: " + file.getAbsolutePath());
        }
    }

    private void usage()
    {
        HelpFormatter f = new HelpFormatter();
        // autoUsage == true
        f.printHelp(" ", _options, true);
    }

    private static Starter getInstance()
    {
        if (_instance == null) {
            _instance = new Starter();
        }

        return _instance;
    }

    @Deprecated
    private static void startClient()
    {
        throw new UnsupportedOperationException(
                "Method Starter.startClient() not implemented yet!");
    }

    @Deprecated
    private static LibraryController startLibrary()
    {
        throw new UnsupportedOperationException(
                "Method Starter.startLibrary() not implemented yet!");
    }

    private static void startLocal()
    {
        getInstance().startLocalImpl();
    }

    @Deprecated
    private static void startServer()
    {
        throw new UnsupportedOperationException(
                "Method Starter.startServer() not implemented yet!");
    }

    private void exitImpl()
    {
        LOGGER.debug("controller: " + _contoroller.getClass());
        _contoroller.exit();
        LOGGER.info("###  Application exited  ###");
        System.exit(0);
    }

    private void start()
    {
        try {
            _contoroller.start();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_MAIN_CONNECTION_ERROR"));
            exit();
        }
    }
 
    private LibraryController startLibraryImpl()
    {
        LOGGER.debug("starting MasterController");
        _contoroller = new LibraryController();
        start();

        return (LibraryController) _contoroller;
    }

    private void startLocalImpl()
    {
        LOGGER.debug("starting LocalController");
        _contoroller = (IController) SalomonEngineContext.getBean("localController");
        start();
    }
}
