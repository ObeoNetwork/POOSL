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
package org.eclipse.poosl.sirius.navigator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.poosl.ClusterClass;
import org.eclipse.poosl.Poosl;
import org.eclipse.poosl.sirius.IPreferenceConstants;
import org.eclipse.poosl.sirius.navigator.PooslEditorPreferenceDialog.OpenStrategy;

/**
 * The OpenFilePreferenceManager.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class OpenFilePreferenceManager extends OpenPreferenceManager {
    private static final int MAX_CLUSTER_NAME_LENGTH = 50;

    private static final String FILE_DIALOG_LABEL = "You are opening a POOSL file. Which type of editor would you like to use?";

    private EObject mainTarget;

    private boolean hasMainClass;

    private Poosl poosl;

    public OpenFilePreferenceManager(String diagramDescription) {
        this.diagramDescription = diagramDescription;
    }

    // Used from double click in project explorer
    public OpenFilePreferenceManager(Poosl poosl) {
        this.poosl = poosl;
        mainTarget = findTarget(poosl);
        hasMainClass = !(mainTarget instanceof Poosl);
        diagramDescription = createDiagramDescription(mainTarget);
    }

    public static EObject findTarget(Poosl poosl) {
        EObject mainCluster = CompositeStructureDiagram.findTarget(poosl);
        if (mainCluster == null) {
            return poosl;
        } else {
            return mainCluster;
        }
    }

    private String createDiagramDescription(EObject target) {
        if (target instanceof Poosl) {
            return PooslEditorPreferenceDialog.CLASS_DIAGRAM;
        } else if (target instanceof ClusterClass) {
            ClusterClass cluster = (ClusterClass) target;
            if (cluster.getName() == null) {
                return PooslEditorPreferenceDialog.COMPOSITE_STRUCTURE_DIAGRAM
                        + PooslEditorPreferenceDialog.DIAGRAM_NAME_SYSTEM;
            } else {
                return PooslEditorPreferenceDialog.COMPOSITE_STRUCTURE_DIAGRAM
                        + PooslEditorPreferenceDialog.DIAGRAM_NAME_CLUSTER
                        + getClusterName(cluster);
            }
        }
        return ""; //$NON-NLS-1$
    }

    private String getClusterName(ClusterClass cluster) {
        String name = cluster.getName();
        if (name.length() > MAX_CLUSTER_NAME_LENGTH) {
            name = name.substring(0, MAX_CLUSTER_NAME_LENGTH) + "..."; //$NON-NLS-1$
        }
        return name;
    }

    public EObject getDiagramTarget() {
        return mainTarget;
    }

    public String getEditorPreference() {
        IPreferencesService preferencesService = Platform.getPreferencesService();
        Boolean dontask = preferencesService.getBoolean(IPreferenceConstants.PREFERENCE_PLUGIN_ID,
                IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_DONT_ASK, false, null);
        String currentPref = getCurrentPreference(Platform.getPreferencesService());
        if (!dontask) {
            currentPref = askPreference();
        }
        if (currentPref != null && currentPref
                .equals(IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_CLASS_DIAGRAM)) {
            mainTarget = poosl;
        }
        return currentPref;
    }

    private String getCurrentPreference(IPreferencesService preferencesService) {
        if (hasMainClass) {
            return preferencesService.getString(IPreferenceConstants.PREFERENCE_PLUGIN_ID,
                    IPreferenceConstants.PROJECT_EXPLORER,
                    IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_TEXTUAL, null);
        } else {
            return preferencesService.getString(IPreferenceConstants.PREFERENCE_PLUGIN_ID,
                    IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_NO_SYSTEM,
                    IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_TEXTUAL, null);
        }
    }

    private String askPreference() {
        OpenStrategy result = showEditorPreferenceDialog(hasMainClass);
        switch (result) {
        case TEXTUAL:
            return IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_TEXTUAL;
        case GRAPHICAL:
            return IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_GRAPHICAL;
        case CLASSDIAGRAM:
            return IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_CLASS_DIAGRAM;
        case CANCEL:
            return null;
        default:
            return IPreferenceConstants.PREFERENCE_PROJECT_EXPLORER_TEXTUAL;
        }
    }

    @Override
    protected String getDialogLabel() {
        return FILE_DIALOG_LABEL;
    }
}
