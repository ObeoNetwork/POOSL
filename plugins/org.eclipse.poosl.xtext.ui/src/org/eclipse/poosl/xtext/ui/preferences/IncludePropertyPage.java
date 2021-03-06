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
package org.eclipse.poosl.xtext.ui.preferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.poosl.xtext.GlobalConstants;
import org.eclipse.poosl.xtext.importing.ImportingHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * The IncludePropertyPage.
 *
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
//CHECKSTYLE.OFF: ClassDataAbstractionCoupling:Eclipse API
public class IncludePropertyPage extends PreferencePage implements IWorkbenchPropertyPage {
    //CHECKSTYLE.ON: ClassDataAbstractionCoupling
    /** The HELP_ID. */
    public static final String HELP_ID = "org.eclipse.poosl.help.help_properties_includepaths"; //$NON-NLS-1$

    private static final String PAGE_RECOMMENDATION = //
            "To include paths within the workspace, use the \"Add\" button to ensure that change propagation works.\n" //
                    + "For include paths outside the workspace, use the \"Add External\" button although change propagation will not work.\n" //
                    + "See Help (F1) for more information.";

    private static final String DIALOG_EXTERNAL_WARNING = "Warning: When using absolute paths change propogation will not happen automatically.";

    @Inject
    private IPreferenceStoreAccess preferenceStoreAccess;

    private IPreferenceStore preferenceStore;

    private Table table;

    private IProject project;

    private List<String> locations;

    private Map<String, String> currentValues = new HashMap<>();

    @Override
    public void createControl(Composite parent) {
        preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(project);
        loadLocations();
        super.createControl(parent);
    }

    @Override
    protected Control createContents(Composite composite) {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), HELP_ID);

        Composite mainComp = new Composite(composite, SWT.FILL);
        mainComp.setFont(composite.getFont());
        GridLayout layout = new GridLayout();
        layout.verticalSpacing = SWT.FILL;
        layout.numColumns = 2;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        mainComp.setLayout(layout);

        Label labelControl = new Label(mainComp, SWT.WRAP);
        labelControl.setText("Poosl include paths:");
        labelControl.setFont(JFaceResources.getDialogFont());
        GridData labelLayoutData = new GridData();
        labelLayoutData.horizontalIndent = 1;
        labelControl.setLayoutData(labelLayoutData);

        new Label(mainComp, SWT.NONE);

        table = new Table(mainComp, SWT.VIRTUAL | SWT.BORDER);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        final TableColumn locationColumn = new TableColumn(table, SWT.NONE);
        locationColumn.setWidth(table.getSize().x);
        locationColumn.setText("Location");

        table.addControlListener(ControlListener
                .controlResizedAdapter(evt -> locationColumn.setWidth(table.getSize().x)));

        createAddButton(mainComp);
        createAddExternalButton(mainComp);
        createRemoveButton(mainComp);
        createMoveButton(mainComp, "Up", SWT.CENTER, false);
        createMoveButton(mainComp, "Down", SWT.TOP, true);
        updateTable();

        Label labelRecommendation = new Label(mainComp, SWT.WRAP);
        labelRecommendation.setText(PAGE_RECOMMENDATION);
        return mainComp;
    }

    private void createMoveButton(
            Composite mainComp, String text, int verticalAlignment, boolean down) {
        Button btn = new Button(mainComp, SWT.NONE);
        btn.setLayoutData(new GridData(SWT.FILL, verticalAlignment, false, false, 1, 1));
        btn.setText(text);
        btn.addSelectionListener(
                SelectionListener.widgetSelectedAdapter(evt -> moveTableSelection(down)));
    }

    private void createRemoveButton(Composite mainComp) {
        Button btnRemove = new Button(mainComp, SWT.NONE);
        btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnRemove.setText("Remove");
        btnRemove.addSelectionListener(SelectionListener.widgetSelectedAdapter(evt -> {
            int index = table.getSelectionIndex();
            locations.remove(table.getSelectionIndex());
            updateTable();
            table.setSelection((index > locations.size() - 1) ? locations.size() - 1 : index);
        }));
    }

    private void createAddExternalButton(Composite mainComp) {
        Button btnExternal = new Button(mainComp, SWT.NONE);
        btnExternal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnExternal.setText("Add External");
        btnExternal.addSelectionListener(SelectionListener.widgetSelectedAdapter(evt -> {
            DirectoryDialog dialog = new DirectoryDialog(getShell());
            dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
            dialog.setText("Select path to include");
            dialog.setMessage(DIALOG_EXTERNAL_WARNING);
            String path = dialog.open();

            if (path != null) {
                addLocation(path);
            }
        }));
    }

    private void createAddButton(Composite mainComp) {
        Button btnAdd = new Button(mainComp, SWT.NONE);
        btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        btnAdd.setText("Add");
        btnAdd.addSelectionListener(SelectionListener.widgetSelectedAdapter(evt -> {
            ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
                    new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            dialog.setInput(root);
            dialog.setTitle("Select Folder to include");
            dialog.setAllowMultiple(false);
            dialog.addFilter(new ViewerFilter() {
                @Override
                public boolean select(Viewer viewer, Object parentElement, Object element) {
                    return element instanceof IFolder || element instanceof IProject;
                }
            });
            dialog.setBlockOnOpen(true);
            if (dialog.open() == Window.OK) {
                Object result = dialog.getFirstResult();
                if (result instanceof IResource) {
                    IPath path = ((IResource) result).getFullPath().makeRelative();
                    addLocation(path.toOSString());
                }
            }
        }));
    }

    private void moveTableSelection(boolean down) {
        int index = table.getSelectionIndex();
        int newIndex = index + (down ? 1 : -1);
        if (0 <= newIndex && newIndex < locations.size()) {
            String oldLocation = locations.get(newIndex);
            String location = locations.get(index);
            locations.set(newIndex, location);
            locations.set(index, oldLocation);
            updateTable();
            table.setSelection(newIndex);
        }
    }

    private void addLocation(String path) {
        locations.add(path);
        updateTable();
        table.setSelection(locations.size() - 1);
    }

    private void updateTable() {
        table.removeAll();
        for (String location : locations) {
            TableItem item = new TableItem(table, SWT.None);
            item.setText(location);
        }
    }

    @Override
    public boolean performOk() {
        performApply();
        return super.performOk();
    }

    @Override
    public void performApply() {
        try {
            if (preferenceStore instanceof IPersistentPreferenceStore) {
                MapDifference<String, String> differences = saveLocations();
                if (!differences.areEqual()) {
                    ((IPersistentPreferenceStore) preferenceStore).save();
                    checkRebuild();
                }
            }
        } catch (IOException e) {
            // logError("Unexpected internal error: ", e); //$NON-NLS-1$
        }
    }

    private void checkRebuild() {
        MessageDialog dialog = new MessageDialog(getShell(), "Include changed", null,
                "The Building settings have changed. A rebuild of all workspace projects is required for changes to take effect. Build now ?",
                MessageDialog.QUESTION,
                new String[] {
                        IDialogConstants.YES_LABEL,
                        IDialogConstants.NO_LABEL,
                        IDialogConstants.CANCEL_LABEL },
                2);
        int res = dialog.open();
        if (res == 0) {
            getBuildJob().schedule();
        }
    }

    private String getKey(int version) {
        return ImportingHelper.getIncludeKey(version, project);
    }

    private MapDifference<String, String> saveLocations() {
        Map<String, String> newValues = new HashMap<>();
        newValues.put(GlobalConstants.PREFERENCES_INCLUDE_VERSION,
                GlobalConstants.PREFERENCES_VERSION);

        for (ListIterator<String> it = locations.listIterator(); it.hasNext(); /**/) {
            int i = it.nextIndex();
            String location = it.next();
            newValues.put(GlobalConstants.PREFERENCES_INCLUDE_KEY + i, location);
        }

        MapDifference<String, String> diff = Maps.difference(currentValues, newValues);
        for (Entry<String, ValueDifference<String>> entry : diff.entriesDiffering().entrySet()) {
            preferenceStore.setValue(entry.getKey(), entry.getValue().rightValue());
        }
        for (Entry<String, String> entry : diff.entriesOnlyOnRight().entrySet()) {
            preferenceStore.setValue(entry.getKey(), entry.getValue());
        }
        for (String entry : diff.entriesOnlyOnLeft().keySet()) {
            preferenceStore.setValue(entry, ""); //$NON-NLS-1$
        }
        currentValues = newValues;
        return diff;
    }

    private void loadLocations() {
        int version = preferenceStore.getInt(GlobalConstants.PREFERENCES_INCLUDE_VERSION);
        currentValues.put(GlobalConstants.PREFERENCES_INCLUDE_VERSION, String.valueOf(version));
        String key = getKey(version);
        locations = new ArrayList<>();
        int i = 0;
        String lKey = key + i;
        String location = preferenceStore.getString(key + i);
        while (!location.isEmpty()) {
            currentValues.put(lKey, location);
            locations.add(location);
            lKey = key + ++i;
            location = preferenceStore.getString(lKey);
        }
    }

    @Override
    public void setElement(IAdaptable element) {
        project = (IProject) element.getAdapter(IResource.class);
        setDescription(null); // no description for property page
    }

    @Override
    public IAdaptable getElement() {
        return project;
    }

    private Job getBuildJob() {
        Job buildJob = new BuildJob("Rebuilding");
        buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
        buildJob.setUser(true);
        return buildJob;
    }

    private static final class BuildJob extends Job {

        private BuildJob(String name) {
            super(name);
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            synchronized (BuildJob.class) {
                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                Job[] buildJobs = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
                for (Job buildJob : buildJobs) {
                    if (buildJob != this && buildJob instanceof BuildJob) {
                        buildJob.cancel();
                    }
                }
            }
            try {
                monitor.beginTask("Build all...", 2);
                ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD,
                        SubMonitor.convert(monitor, 2));

            } catch (CoreException e) {
                return e.getStatus();
            } catch (OperationCanceledException e) {
                return Status.CANCEL_STATUS;
            } finally {
                monitor.done();
            }
            return Status.OK_STATUS;
        }

        @Override
        public boolean belongsTo(Object family) {
            return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
        }
    }
}
