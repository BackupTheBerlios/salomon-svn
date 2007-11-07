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

package salomon.engine.controller.gui.common;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Version;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class AboutPanel
{
    private JPanel _aboutPanel;

    public JPanel getPanel()
    {
        if (_aboutPanel == null) {
            _aboutPanel = buildForm().getPanel();
        }
        return _aboutPanel;
    }

    private DefaultFormBuilder buildForm()
    {
        FormLayout layout = new FormLayout(
                "left:max(40dlu;pref), 3dlu, max(70dlu;pref)", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        // application name
        JLabel lblAppName = new JLabel(new ImageIcon(
                Config.RESOURCES_DIR + Config.FILE_SEPARATOR
                        + "logo.jpg")); //$NON-NLS-1$

        builder.append(lblAppName, 3);

        // authors
        builder.appendSeparator(Messages.getString("TIT_AUTHORS"));

        JLabel author = new JLabel("Nikodem Jura");
        JLabel email = new JLabel("nico@ernie.icslab.agh.edu.pl");
        email.setForeground(Color.BLUE);
        builder.append(author, email);

        author = new JLabel("Krzysztof Rajda");
        email = new JLabel("krzysztof@rajda.name");
        email.setForeground(Color.BLUE);
        builder.append(author, email);


        // versions
        builder.appendSeparator(Messages.getString("TIT_VERSION"));
        JLabel version = new JLabel(Messages.getString("VERSION"));
        version.setForeground(Color.RED);
        builder.append(new JLabel(Messages.getString("TIT_VERSION")), version);
        builder.append(new JLabel(Messages.getString("BUILD")), new JLabel(
                Version.getString("REVISION_VERSION")));

        return builder;
    }
}
