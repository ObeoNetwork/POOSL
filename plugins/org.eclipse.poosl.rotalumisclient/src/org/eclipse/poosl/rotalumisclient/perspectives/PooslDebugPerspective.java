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
package org.eclipse.poosl.rotalumisclient.perspectives;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.poosl.pooslproject.PooslProjectConstant;
import org.eclipse.poosl.rotalumisclient.PooslConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**
 * The PooslDebugPerspective.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslDebugPerspective implements IPerspectiveFactory {

    @Override
    public void createInitialLayout(IPageLayout pFactory) {
        addViews(pFactory);
        addActionSets(pFactory);
        addNewWizardShortcuts(pFactory);
        addPerspectiveShortcuts(pFactory);
        addViewShortcuts(pFactory);
    }

    private void addViews(IPageLayout factory) {
        String topSideId = "top"; //$NON-NLS-1$

        // Creates the overall folder layout.
        // Note that each new Folder uses a percentage of the remaining
        // EditorArea.
        factory.createFolder(topSideId, IPageLayout.TOP, 0.4f, factory.getEditorArea());
        IFolderLayout insideTop = factory.createFolder("insideTop", //$NON-NLS-1$
                IPageLayout.LEFT, 0.33f, topSideId);
        insideTop.addView(PooslConstants.ID_POOSL_DEBUGVIEW);
        IFolderLayout insideTop1 = factory.createFolder("insideTop1", //$NON-NLS-1$
                IPageLayout.LEFT, 0.5f, topSideId);

        insideTop1.addView(PooslConstants.ID_POOSL_PETVIEW);
        IFolderLayout insideTop2 = factory.createFolder("insideTop2", //$NON-NLS-1$
                IPageLayout.LEFT, 0.05f, topSideId);

        insideTop2.addView(PooslConstants.ID_POOSL_VARIABLESVIEW);
        IFolderLayout right = factory.createFolder("right", //$NON-NLS-1$
                IPageLayout.RIGHT, 0.5f, factory.getEditorArea());

        right.addView(PooslConstants.ID_POOSL_SEQUENCEDIAGRAMVIEW);
        right.addView(IPageLayout.ID_OUTLINE);
        IFolderLayout bottom = factory.createFolder("bottom", //$NON-NLS-1$
                IPageLayout.BOTTOM, 0.7f, factory.getEditorArea());
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

        bottom.addView(IDebugUIConstants.ID_BREAKPOINT_VIEW);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_TASK_LIST);
    }

    private void addActionSets(IPageLayout factory) {
        factory.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
        // NO such action set ??
        factory.addActionSet("org.eclipse.poosl.rotalumisclient.debugactionset"); //$NON-NLS-1$
        factory.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
        factory.addActionSet(IDebugUIConstants.PLUGIN_ID + ".breakpointActionSet"); //$NON-NLS-1$
        factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
    }

    private void addPerspectiveShortcuts(IPageLayout factory) {
        factory.addPerspectiveShortcut(PooslProjectConstant.ID_POOSL_EDIT_PERSPECTIVE);
    }

    private void addNewWizardShortcuts(IPageLayout factory) {
        factory.addNewWizardShortcut(PooslProjectConstant.PROJECT_WIZARD_ID);
        factory.addNewWizardShortcut(PooslProjectConstant.FILE_WIZARD_ID);
        factory.addNewWizardShortcut(PooslProjectConstant.SYSFILE_WIZARD_ID);
    }

    private void addViewShortcuts(IPageLayout factory) {
        factory.addShowViewShortcut(PooslConstants.ID_POOSL_DEBUGVIEW);
        factory.addShowViewShortcut(PooslConstants.ID_POOSL_PETVIEW);
        factory.addShowViewShortcut(PooslConstants.ID_POOSL_VARIABLESVIEW);
        factory.addShowViewShortcut(PooslConstants.ID_POOSL_SEQUENCEDIAGRAMVIEW);
        factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
    }
}
