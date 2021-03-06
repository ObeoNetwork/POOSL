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
package org.eclipse.poosl.xtext;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.emf.mwe.utils.StandaloneSetup;

/**
 * The LocalStandaloneSetup.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class LocalStandaloneSetup extends StandaloneSetup {

    /** Path matching .classpath content */
    private static final String[] WELL_KNOWN_CLASSPATH = {
            "target", //$NON-NLS-1$
            "jdt-classes", //$NON-NLS-1$
    };

    /** Eclipse project descriptor */
    private static final String PROJECT_FILENAME = ".project"; //$NON-NLS-1$

    private final Logger log = Logger.getLogger(getClass());

    private final boolean fallback;

    public LocalStandaloneSetup() {
        this(true);
    }

    public LocalStandaloneSetup(boolean fallback) {
        this.fallback = fallback;
    }

    @Override
    protected File findProjectFileForPossibleClassesFolder(File f) {
        File result = findProjectFile(f);

        return (result != null || !fallback)
            ? result : super.findProjectFileForPossibleClassesFolder(f);
    };

    protected File findProjectFile(File f) {
        File container = withParentWhen(withParentWhen(f, WELL_KNOWN_CLASSPATH[1]),
                WELL_KNOWN_CLASSPATH[0]);
        if (container == null) {
            return null;
        }
        File projectFile = new File(container, PROJECT_FILENAME);
        if (projectFile.exists()) {
            // Default behavior logs a warning
            // This notifies of the fix
            log.warn("Local setup detected for Project path for " + container.getName()); //$NON-NLS-1$
        }
        return projectFile;
    }

    private static File withParentWhen(File file, String when) {
        return file != null && file.getName().equals(when) ? file.getParentFile() : null;
    }

}
