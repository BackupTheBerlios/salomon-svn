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

package salomon.util.weka;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.log4j.Logger;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.exception.PlatformException;

import weka.core.Instances;

public final class AttributeConverter
{

    public static Instances toWeka(IAttributeData data, String treeName) throws IOException
    {
        boolean wasHeaderRead = false;
        StringBuilder builder = new StringBuilder();
        builder.append("@relation ").append(treeName).append("\n");
        IAttribute[] firstRow = null;
        try {
            while (data.next()) {
                IAttribute[] attributes = data.getAttributes();
                if (!wasHeaderRead) {
                    firstRow = attributes;
                    appendHeader(builder, attributes);
                    wasHeaderRead = true;
                }

                appendData(builder, attributes);
                builder.append('\n');
            }
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }

        if (firstRow == null) {
            return null;
        }

        Reader reader = new StringReader(builder.toString());
        Instances instances = new Instances(reader);

        // set last if out attribute doesn't exist
        instances.setClassIndex(instances.numAttributes() - 1);
        for (int i = 0; i < 0; ++i) {
            IAttributeDescription description = firstRow[i].getDescription();
            if (description.isOutput()) {
                instances.setClassIndex(i);
                break;
            }
        }

        return instances;
    }

    private static void appendData(StringBuilder builder,
            IAttribute[] attributes)
    {
        for (int i = 0; i < attributes.length; ++i) {
            builder.append(attributes[i].getValue());
            if (i < attributes.length - 1) {
                builder.append(',');
            }
        }
    }

    private static void appendHeader(StringBuilder builder,
            IAttribute[] attributes)
    {
        for (IAttribute attribute : attributes) {
            IAttributeDescription description = attribute.getDescription();
            AttributeType type = description.getType();
            builder.append("@attribute ").append(description.getName()).append(
                    ' ');
            if (AttributeType.INTEGER.equals(type)) {
                builder.append("integer");
            } else if (AttributeType.REAL.equals(type)) {
                builder.append("real");
            } else if (AttributeType.STRING.equals(type)) {
                builder.append("string");
            } else if (AttributeType.DATE.equals(type)) {
                builder.append("date");
            } else if (AttributeType.ENUM.equals(type)) {
                IEnumAttributeDescription enumDescription = (IEnumAttributeDescription) description;
                builder.append('{');
                String[] enumValues = enumDescription.getValues();
                for (int i = 0; i < enumValues.length; ++i) {
                    builder.append(enumValues[i]);
                    if (i < (enumValues.length - 1)) {
                        builder.append(',');
                    }
                }
                builder.append('}');
            } else {
                LOGGER.error("INVALID TYPE:" + type.toString());
            }
            builder.append('\n');
        }
        builder.append("@data\n");
    }

    private static final Logger LOGGER = Logger.getLogger(AttributeConverter.class);
}
