/*******************************************************************************
 * Copyright (c) 2021 TNO/ESI
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    TNO/ESI - initial API and implementation
 *    Obeo - refactoring
 *******************************************************************************/
package org.eclipse.poosl.sirius;

/**
 * The Messages.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public final class Messages {
    public static final String ERROR_MODELLING_TITLE = "Convert Project to Modeling project";

    public static final String ERROR_MODELLING_MESSAGE = "Impossible to convert the project: {0}";

    public static final String ERROR_MISSING_SYSTEM_AND_CLUSTER_TITLE = "Cannot directly open a Composite Structure Diagram.";

    public static final String ERROR_MISSING_SYSTEM_AND_CLUSTER_MESSAGE = "Cannot directly open a Composite Structure Diagram for the selected model."
            + "\n\nIf the selected model contains any cluster classes, you can open a Composite Structure Diagram via the Class Diagram or the textual editor. "
            + "For example, in the Class Diagram double-click on a cluster class, or in the textual editor select a cluster and press F2.";

    private Messages() {
        throw new IllegalStateException("Utility class");
    }
}
