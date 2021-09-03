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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.poosl.generatedxmlclasses.TInstanceType;

/**
 * The PooslTreeContentProvider.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslTreeContentProvider implements ITreeContentProvider {
    private Object[] instances;

    public PooslTreeContentProvider(Object[] instances) {
        this.instances = instances;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput != null) {
            instances = (Object[]) newInput;
            viewer.refresh();
        }
    }

    @Override
    public void dispose() {
        // Nothing to dispose
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof Entry<?, ?>) {
            TInstanceType type = (TInstanceType) ((Entry<?, ?>) element).getValue();
            return type == TInstanceType.CLUSTER;
        }
        return false;
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof Entry<?, ?>) {
            String name = (String) ((Entry<?, ?>) element).getKey();
            String parentName = name.substring(0, name.lastIndexOf('/'));
            for (Object instance : instances) {
                if (((Entry<?, ?>) instance).getKey().equals(parentName)) {
                    return instance;
                }
            }
        }
        return null;
    }

    @Override
    public Object[] getElements(Object inputElement) {
        Object[] inputElements = (Object[]) inputElement;
        if (inputElements != null) {
            Map<String, TInstanceType> treeElements = new HashMap<>();
            for (Object instance : inputElements) {
                Entry<?, ?> entry = (Entry<?, ?>) instance;
                String tempName = (String) entry.getKey();
                // Only add the top level elements
                String[] tempResult = tempName.split("/"); //$NON-NLS-1$
                if (tempResult.length == 2) {
                    treeElements.put(tempName, (TInstanceType) entry.getValue());
                }
            }
            return treeElements.entrySet().toArray();
        }
        return new Object[0];
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        Map<String, TInstanceType> children = new HashMap<>();
        if (parentElement instanceof Entry<?, ?>) {
            String parentName = (String) ((Entry<?, ?>) parentElement).getKey();
            for (Object instance : instances) {
                Entry<?, ?> entry = (Entry<?, ?>) instance;
                String tempName = (String) entry.getKey();
                String tempParentName = tempName.substring(0, tempName.lastIndexOf('/'));
                if (tempParentName.equals(parentName)) {
                    children.put(tempName, (TInstanceType) entry.getValue());
                }
            }
        }
        return children.entrySet().toArray();
    }
}
