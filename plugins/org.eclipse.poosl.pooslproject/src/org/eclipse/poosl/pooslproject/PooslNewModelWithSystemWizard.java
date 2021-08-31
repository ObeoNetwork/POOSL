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
package org.eclipse.poosl.pooslproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * The PooslNewModelWithSystemWizard.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslNewModelWithSystemWizard extends AbstractPooslModelWizard implements INewWizard {
    private static final Logger LOGGER = Logger.getLogger(PooslNewModelWithSystemWizard.class.getName());

    private static final String WIZARD_NAME = "New Poosl model with System";

    private WizardNewFileCreationPage pageOne;

    @Override
    public boolean performFinish() {
        IFile file = pageOne.createNewFile();
        addTemplate(file);
        return openFile(file);
    }

    @Override
    public void addPages() {
        super.addPages();
        pageOne = new WizardPooslNewFileWithSystemCreationPage(WIZARD_NAME, selection);
        addPage(pageOne);
    }

    private static void addTemplate(IFile file) {
        URL url;
        InputStream inputStream = null;

        try {
            url = new URL("platform:/plugin/org.eclipse.poosl.pooslproject/templates/system.poosl"); //$NON-NLS-1$
            inputStream = url.openConnection().getInputStream();
            file.setContents(inputStream, 0, null);
        } catch (IOException | CoreException e) {
            LOGGER.log(Level.FINE, "Could not add template to poosl file.");
        }
    }

}
