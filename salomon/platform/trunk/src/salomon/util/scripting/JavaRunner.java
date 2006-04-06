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

package salomon.util.scripting;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import koala.dynamicjava.interpreter.Interpreter;
import koala.dynamicjava.interpreter.InterpreterException;
import koala.dynamicjava.interpreter.TreeInterpreter;
import koala.dynamicjava.parser.wrapper.JavaCCParserFactory;

import org.apache.log4j.Logger;

public class JavaRunner
{
    private Interpreter _interpreter;

    public JavaRunner()
    {
        _interpreter = new TreeInterpreter(new JavaCCParserFactory());
        defineFunctions();
    }

    public void run(String script, OutputStream outputStream)
    {
        PrintStream newPrintStream = new PrintStream(outputStream);
        try {
            PrintStream oldOut = System.out;
            PrintStream oldErr = System.err;
            System.setOut(newPrintStream);
            System.setErr(newPrintStream);
            _interpreter.interpret(new StringReader(script), "SalomonScript");
            System.setOut(oldOut);
            System.setErr(oldErr);
        } catch (InterpreterException e) {
            newPrintStream.println("ERROR: " + e.getMessage());
            LOGGER.fatal("", e);
        }
    }

    public void defineVariable(String name, Object instance)
    {
        _interpreter.defineVariable(name, instance);
    }
    
    private void defineFunctions()
    {
        String script = "void print(String text) {System.out.print(text);}";
        script += "void println(String text) {System.out.println(text);}";
        
        try {
            _interpreter.interpret(new StringReader(script), "SalomonScript");
        } catch (InterpreterException e) {
            LOGGER.fatal("", e);
        }
    }
        
    private static final Logger LOGGER = Logger.getLogger(JavaRunner.class);
}
