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
package org.eclipse.poosl.rotalumisclient.debug;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.poosl.rotalumisclient.sourcemapping.PooslSourceMapping;

/**
 * The PooslSourceLookupParticipant.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslSourceLookupParticipant extends AbstractSourceLookupParticipant {

    @Override
    public String getSourceName(Object object) throws CoreException {
        if (object instanceof PooslStackFrame) {
            // Return the fileName of the model
            PooslStackFrame stackFrame = (PooslStackFrame) object;
            PooslSourceMapping sourceMapping = stackFrame.getSourceMapping();

            if (sourceMapping != null) {
                return getRelativeFileString(sourceMapping);
            }
        }
        return null;
    }

    private String getRelativeFileString(PooslSourceMapping sourceMapping) {
        IPath path = new Path(sourceMapping.getFilePath());
        for (ISourceContainer iSourceContainer : getSourceContainers()) {
            if (iSourceContainer instanceof ProjectSourceContainer) {
                IPath ploc = ((ProjectSourceContainer) iSourceContainer).getProject().getLocation();
                if (path.toOSString().startsWith(ploc.toOSString())) {
                    return path.makeRelativeTo(ploc).toOSString();
                }
            }
        }
        return null;
    }
}
