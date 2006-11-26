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

package salomon.engine.controller.gui.task;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import salomon.engine.task.event.TaskEvent;
import salomon.engine.task.event.TaskListener;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Class agregates all buttons used in GUIController.
 */
public final class TaskControlPane
{
    private DefaultFormBuilder _builder;

    private TaskControlButtons _taskControlButtons;

    /**
     * 
     * @uml.property name="_taskManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private GraphTaskManagerGUI _taskManagerGUI = null;

    private TaskProcessPane _taskProcessPane;

    public TaskControlPane(GraphTaskManagerGUI taskManagerGUI)
    {
        _taskManagerGUI = taskManagerGUI;
        initComponents();
        initGUI();
    }

    public JPanel getPanel()
    {
        return _builder.getPanel();
    }

    private void initComponents()
    {
        _taskProcessPane = new TaskProcessPane();
        _taskControlButtons = new TaskControlButtons();
        _taskManagerGUI.addTaskListener(_taskProcessPane);
    }

    private void initGUI()
    {
        FormLayout layout = new FormLayout("left:pref, 3dlu, fill:100dlu:grow",
                "");
        _builder = new DefaultFormBuilder(layout);
        _builder.setDefaultDialogBorder();
        _builder.append(_taskControlButtons);
        _builder.append(_taskProcessPane.getPanel());
    }

    final class TaskControlButtons extends JPanel
    {
        private JButton _btnPause;

        private JButton _btnStart;

        private JButton _btnStop;

        private ProcessState _processState;

        /**
         * 
         */
        public TaskControlButtons()
        {
            initComponents();
            initGUI();
            setState(ProcessState.STOPPED);
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

        private void initGUI()
        {
            this.add(ButtonBarFactory.buildLeftAlignedBar(_btnStart, _btnPause,
                    _btnStop));
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

    private class TaskProcessPane implements TaskListener
    {
        private Color _defaultForegroundColor;

        private Color _errorForegroundColor = Color.RED;

        private String _failureMsg = "Failures: ";

        private int _failuresCount;

        private JLabel _lblFailures;

        private DefaultFormBuilder _processBuilder;

        private JProgressBar _progressBar;

        private int _taskCount;

        public TaskProcessPane()
        {
            initComponents();
            initGUI();
        }

        public JPanel getPanel()
        {
            return _processBuilder.getPanel();
        }

        public void taskFailed(TaskEvent event)
        {
            _progressBar.setForeground(_errorForegroundColor);
            ++_failuresCount;
            _lblFailures.setText(_failureMsg + _failuresCount);
            updateProgressBar();
        }

        public void taskPaused(TaskEvent event)
        {
            // empty body
        }

        public void taskProcessed(TaskEvent event)
        {
            updateProgressBar();
        }

        public void taskResumed(TaskEvent event)
        {
            // empty body
        }

        public void tasksInitialized(int taskCount)
        {
            _taskCount = taskCount;
            _failuresCount = 0;
            _progressBar.setForeground(_defaultForegroundColor);
            _progressBar.setMaximum(_taskCount);
            _progressBar.setValue(0);
            _progressBar.setString("0/" + _taskCount);

            _lblFailures.setText(_failureMsg + _failuresCount);
        }

        public void tasksProcessed()
        {
            _taskControlButtons.setState(ProcessState.STOPPED);
        }

        public void taskStarted(TaskEvent event)
        {
            // empty body
        }

        public void taskStopped(TaskEvent event)
        {
            // empty body
        }

        private void initComponents()
        {
            _progressBar = new JProgressBar();
            _progressBar.setMinimum(0);
            _progressBar.setStringPainted(true);
            _progressBar.setString("");
            _defaultForegroundColor = _progressBar.getForeground();
            _lblFailures = new JLabel(_failureMsg + 0);
        }

        private void initGUI()
        {
            FormLayout layout = new FormLayout(
                    "fill:100dlu:grow, 3dlu, left:pref", "");
            _processBuilder = new DefaultFormBuilder(layout);
            _processBuilder.setDefaultDialogBorder();
            _processBuilder.append(_progressBar);
            _processBuilder.append(_lblFailures);
        }

        private void updateProgressBar()
        {
            int currTask = _progressBar.getValue() + 1;
            _progressBar.setValue(currTask);
            _progressBar.setString("" + currTask + "/" + _taskCount);
        }

    }

}