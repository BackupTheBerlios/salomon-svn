/*
 * Copyright (C) 2007 agent-dummy Team
 *
 * This file is part of agent-dummy.
 *
 * agent-dummy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * agent-dummy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with agent-dummy; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.agent;

import org.apache.log4j.Logger;

import salomon.communication.ICommunicationEvent;
import salomon.communication.ICommunicationListener;
import salomon.communication.IMessageEvent;
import salomon.platform.IVariable;
import salomon.util.serialization.SimpleLong;
import salomon.util.serialization.SimpleString;

/**
 * 
 */
public class DummyDecisionComponent implements IAgentDecisionComponent {
	private static final Logger LOGGER = Logger
			.getLogger(DummyDecisionComponent.class);

	private IConfigComponent _configComponent;

	private IAgentProcessingComponent _processinComponent;

	private boolean _started;

	public DummyDecisionComponent() {
		_configComponent = new DummyConfigComponent();
	}

	/**
	 * @see salomon.agent.IAgentDecisionComponent#getComponentName()
	 */
	public String getComponentName() {
		return DummyDecisionComponent.class.getName();
	}

	/**
	 * @see salomon.agent.IAgentDecisionComponent#getConfigurationComponent()
	 */
	public IConfigComponent getConfigurationComponent() {
		return _configComponent;
	}

	/**
	 * @see salomon.agent.IAgentDecisionComponent#setAgentProcessingComponent(salomon.agent.IAgentProcessingComponent)
	 */
	public void setAgentProcessingComponent(
			IAgentProcessingComponent processinComponent) {
		_processinComponent = processinComponent;
	}

	/**
	 * @see salomon.agent.IRunnable#start()
	 */
	public synchronized void start() {
		LOGGER.info("DummyDecisionComponent.start()");
		if (!_started) {
			// the agent listens to the events
			_processinComponent.getCommunicationBus().addCommunicationListener(
					new DummyEventListener());
			_started = true;
		}

	}

	private class DummyEventListener implements ICommunicationListener {

		public void onCommunicationEvent(ICommunicationEvent event) {
			LOGGER.info("DummyEventListener.onCommunicationEvent()");
			LOGGER.debug("environment: " + _processinComponent.getEnvironment());
			// if any message event was caught, start processing it
			// the logic here should be more complex,
			// in this case simply pass all messages down to processing part
			if (event instanceof IMessageEvent) {
				// pass the information about the message to the processing
				// component
				IVariable variable = _processinComponent.getEnvironment()
						.createEmpty("dummy-message-id");
				SimpleLong messageId = new SimpleLong(((IMessageEvent) event)
						.getMessageMetadata().getMessageId());
				variable.setValue(messageId);
				_processinComponent.getEnvironment().add(variable);

				// add the content of the message to the environment
				IVariable msgVar = _processinComponent.getEnvironment()
						.createEmpty("message");
				SimpleString msgStr = new SimpleString("Some text message");
				msgVar.setValue(msgStr);
				_processinComponent.getEnvironment().add(msgVar);				
				LOGGER.info("Variables added");
				LOGGER.debug("environment: " + _processinComponent.getEnvironment());

				// start processing component
				_processinComponent.start();
			}
		}
	}

	/**
	 * @see salomon.agent.IRunnable#stop()
	 */
	public void stop() {
		_started = false;
	}

	public boolean isStarted() {
		return _started;
	}

}
