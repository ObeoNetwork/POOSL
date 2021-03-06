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
package org.eclipse.poosl.rotalumisclient.views.diagram;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * The PooslTreeLabelProvider.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslTreeLabelProvider implements ILabelProvider {

    @Override
    public void addListener(ILabelProviderListener listener) {
        // This label provider does not need to support listeners
    }

    @Override
    public void dispose() {
        // Nothing to dispose
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // This label provider does not need to support listeners
    }

    @Override
    public Image getImage(Object element) {
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof Entry<?, ?>) {
            String instanceName = (String) ((Entry<?, ?>) element).getKey();
            instanceName = instanceName.substring(instanceName.lastIndexOf('/'),
                    instanceName.length());
            return instanceName;
        }
        return ""; //$NON-NLS-1$
    }
}
