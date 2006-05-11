/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.engine.controller.gui.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import salomon.engine.Config;

import com.jgoodies.forms.factories.ButtonBarFactory;

/**
 * Class agregates all buttons used in GUIController.
 */
public final class TaskControlPane extends JPanel
{
    private static final Logger LOGGER = Logger.getLogger(TaskControlPane.class);

    private String _resourcesDir = null;

    /**
     * 
     * @uml.property name="_taskManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private GraphTaskManagerGUI _taskManagerGUI = null;

    public TaskControlPane(GraphTaskManagerGUI taskManagerGUI)
    {
        _taskManagerGUI = taskManagerGUI;
        _resourcesDir = Config.getString("RESOURCES_DIR");
        this.add(new ControlPane().getPanel());
    }

    final class ControlPane
    {
        private JButton _btnPause;

        private JButton _btnStart;

        private JButton _btnStop;

        private JPanel _panel;

        private ProcessState _processState;

        /**
         * 
         */
        public ControlPane()
        {
            initComponents();
            setState(ProcessState.STOPPED);
        }

        public JPanel getPanel()
        {
            if (_panel == null) {
                _panel = ButtonBarFactory.buildCenteredBar(_btnStart,
                        _btnPause, _btnStop);
            }
            return _panel;
        }

        private void initComponents()
        {
            _btnStart = new JButton("Start");
            _btnStart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    _taskManagerGUI.startTasks();
                    setState(ProcessState.STARTED);
                }
            });
            _btnStop = new JButton("Stop");
            _btnStop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    _taskManagerGUI.stopTasks();
                    setState(ProcessState.STOPPED);
                }
            });
            _btnPause = new JButton("Pause");
            _btnPause.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e)
                {
                    if (_processState == ProcessState.PAUSED) {
                        _taskManagerGUI.resumeTasks();
                        _processState = ProcessState.RESUMED;
                        _btnPause.setText("Pause");
                    } else {
                        _taskManagerGUI.pauseTasks();
                        _processState = ProcessState.PAUSED;
                        _btnPause.setText("Resume");
                    }
                }
            });
        }

        private void setState(ProcessState processState)
        {
            _processState = processState;
            switch (_processState) {
                case STARTED :
                    _btnStart.setEnabled(false);
                    _btnPause.setEnabled(true);
                    _btnStop.setEnabled(true);
                    break;
                case PAUSED :
                case RESUMED :
                    _btnStart.setEnabled(false);
                    _btnPause.setEnabled(true);
                    _btnStop.setEnabled(true);
                    break;
                case STOPPED :
                    _btnStart.setEnabled(true);
                    _btnPause.setEnabled(false);
                    _btnStop.setEnabled(false);
                    break;
            }
        }
    }

    private enum ProcessState {
        PAUSED, RESUMED, STARTED, STOPPED;
    }
}