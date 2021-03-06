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

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IStepFilters;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.debug.ui.contexts.IDebugContextService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.poosl.generatedxmlclasses.Response;
import org.eclipse.poosl.generatedxmlclasses.TCommandResponse;
import org.eclipse.poosl.generatedxmlclasses.TCommandResult;
import org.eclipse.poosl.generatedxmlclasses.TCommunicationEvent;
import org.eclipse.poosl.generatedxmlclasses.TCompileResponse;
import org.eclipse.poosl.generatedxmlclasses.TConstantType;
import org.eclipse.poosl.generatedxmlclasses.TEengineEventErrorResponse;
import org.eclipse.poosl.generatedxmlclasses.TErrorInfo;
import org.eclipse.poosl.generatedxmlclasses.TErrorStackframe;
import org.eclipse.poosl.generatedxmlclasses.TErrorStacktrace;
import org.eclipse.poosl.generatedxmlclasses.TExecutionStateChangeResponse;
import org.eclipse.poosl.generatedxmlclasses.TExecutiontreeBase;
import org.eclipse.poosl.generatedxmlclasses.TGetTransitionsResponse;
import org.eclipse.poosl.generatedxmlclasses.TInspectModel;
import org.eclipse.poosl.generatedxmlclasses.TInspectResponse;
import org.eclipse.poosl.generatedxmlclasses.TInspectType;
import org.eclipse.poosl.generatedxmlclasses.TInstanceType;
import org.eclipse.poosl.generatedxmlclasses.TInstantiateResponse;
import org.eclipse.poosl.generatedxmlclasses.TInstantiateResult;
import org.eclipse.poosl.generatedxmlclasses.TObjectQueryResult;
import org.eclipse.poosl.generatedxmlclasses.TPerformTransitionResponse;
import org.eclipse.poosl.generatedxmlclasses.TPerformTransitionResponseResult;
import org.eclipse.poosl.generatedxmlclasses.TSetVariableResponse;
import org.eclipse.poosl.generatedxmlclasses.TSetVariableResult;
import org.eclipse.poosl.generatedxmlclasses.TTransition;
import org.eclipse.poosl.generatedxmlclasses.TVariable;
import org.eclipse.poosl.rotalumisclient.Activator;
import org.eclipse.poosl.rotalumisclient.Client;
import org.eclipse.poosl.rotalumisclient.Messages;
import org.eclipse.poosl.rotalumisclient.PooslConstants;
import org.eclipse.poosl.rotalumisclient.debug.credit.IPooslCreditReceiver;
import org.eclipse.poosl.rotalumisclient.debug.credit.PooslMessageCreditor;
import org.eclipse.poosl.rotalumisclient.extension.ExternLaunchStartInformer;
import org.eclipse.poosl.rotalumisclient.extension.ExternLaunchStopInformer;
import org.eclipse.poosl.rotalumisclient.extension.ExternMessageInformer;
import org.eclipse.poosl.rotalumisclient.runner.FileURIConverter;
import org.eclipse.poosl.rotalumisclient.sourcemapping.PooslSourceMap;
import org.eclipse.poosl.rotalumisclient.views.WindowCreater;
import org.eclipse.poosl.xtext.GlobalConstants;
import org.eclipse.poosl.xtext.importing.ImportingHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

import com.google.common.collect.Iterables;

/**
 * The PooslDebugTarget.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public final class PooslDebugTarget extends PooslDebugElement implements
        IDebugTarget,
        IStepFilters,
        IPooslCreditReceiver {
    private static final String COULD_NOT_LOG_RESPONSE = "Could not log response.";

    private static final Logger LOGGER = Logger.getLogger(PooslDebugTarget.class.getName());

    private static final TransitionHandlerComparator<?>[] TRANST_HANDLER_COMPARATORS = { //
            new TransitionHandlerComparator<>(it -> it.getDelay(), it -> it.getHandle()), //
            new TransitionHandlerComparator<>(it -> it.getCommunication(), it -> it.getHandle()), //
            new TransitionHandlerComparator<>(it -> it.getDataStep(), it -> it.getHandle()), //
            new TransitionHandlerComparator<>(it -> it.getProcessStep(), it -> it.getHandle()) };

    IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            // we are only interested in POST_CHANGE events
            if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
                return;
            }
            final List<IResource> changed = new ArrayList<>();
            try {
                event.getDelta().accept(delta -> {
                    // only interested in changed resources (not added or removed)
                    // only interested in content changes
                    if ((delta.getKind() == IResourceDelta.CHANGED)
                            && (delta.getFlags() & IResourceDelta.CONTENT) != 0) {
                        IResource resource = delta.getResource();
                        // only interested in files with the "poosl" extension
                        if (resource.getType() == IResource.FILE //
                                && GlobalConstants.FILE_EXTENSION
                                        .equalsIgnoreCase(resource.getFileExtension()) //
                                && resource.getLocation().toString().contains(projectName)) {
                            changed.add(resource);
                        }
                    }
                    return true;
                });
            } catch (CoreException e) {
                LOGGER.log(Level.WARNING, "Cannot accept delta from resource changelistener", e);
            }
            // nothing more to do if there were no changed files
            askRestartOnChange(changed);
        }

    };

    private IDebugEventSetListener debugEventSetListener = events -> {
        for (DebugEvent debugEvent : events) {
            dispatchEvent(debugEvent);
        }
    };

    private IDebugContextListener debugContextListener = event -> {
        // Store the last selected thread.
        ISelection selection = event.getContext();
        if (selection instanceof TreeSelection) {
            TreeSelection treeSelection = (TreeSelection) selection;
            if (treeSelection.getFirstElement() instanceof PooslThread) {
                PooslThread selectedThread = (PooslThread) treeSelection.getFirstElement();
                selectedThread.getRotalumisStackFrames();
            }
        }

    };

    private PooslSourceMap pooslSourceMap;

    // Datastore and functions for handling sequence diagram messages
    private PooslSequenceDiagramMessageProvider pooslSequenceDiagramMessageProvider;

    private final Client client;

    private final ILaunch launch;

    private final IProcess process;

    private final String name;

    private final String projectName;

    // Debug information
    private BigInteger modelHandle = BigInteger.ONE;

    private final Map<String, BigInteger> files2handle = new HashMap<>();

    private final Map<Integer, String> requests = new HashMap<>();

    private final Map<BigInteger, PooslVariable> setVariableRequests = new HashMap<>();

    // known breakpoints
    private final PooslBreakpointManager breakpointManager = new PooslBreakpointManager();

    private final Map<Integer, PooslStackFrame> stackFrames = new HashMap<>();

    private final List<String> includes;

    private final PooslMessageCreditor messageCreditor;

    private final ExternMessageInformer externMessageInformer;

    // Store the PooslThread objects created from the models processes
    private PooslThread[] threads;

    private final PooslInstanceMap pooslInstanceMap = new PooslInstanceMap();

    // contextActivationToken is used to add/remove the poosl debug context to
    // provide key bindings
    private IContextActivation contextActivationToken;

    // Store the current possible transitions. (empty when running)
    private List<TTransition> possibleTransitions = new ArrayList<>();

    // Store the simulated time. (empty when running)
    private String simulatedTime = ""; //$NON-NLS-1$

    private TEengineEventErrorResponse stacktrace;

    private String externalConfigPath;

    private boolean edited;

    private boolean relaunch;

    private boolean extensionInformedStop;

    private boolean commEventsEnabled = true;

    private final CompileJob compileJob = new CompileJob();

    private Timer timer;

    private boolean isTerminated;

    private boolean isSuspended = true;

    private boolean isDisconnected;

    public PooslDebugTarget(ILaunch launch, IProcess process, Client client, Process proc,
            List<String> includes) throws CoreException {
        super(null); // cannot use this in super.
        this.launch = launch;
        this.process = process;
        this.client = client;
        this.target = this;
        this.includes = includes;

        ILaunchConfiguration cfg = launch.getLaunchConfiguration();

        // Set the name for this debugTarget to the name of the model
        String modelPath = launch.getLaunchConfiguration()
                .getAttribute(PooslConstants.CONFIGURATION_ATTRIBUTE_RELATIVE_PATH, "");
        // //$NON-NLS-1$
        this.name = modelPath.substring(modelPath.lastIndexOf("/") + 1);
        // XXX should be
        // this.name = cfg.getName();

        // Get the projectName from the launchConfiguration
        projectName = cfg.getAttribute(PooslConstants.CONFIGURATION_ATTRIBUTE_PROJECT, ""); //$NON-NLS-1$

        // Initialize the Sequence diagram message provider
        messageCreditor = new PooslMessageCreditor(this);
        externMessageInformer = new ExternMessageInformer();

        // Add all listeners to the debug target
        Display.getDefault().asyncExec(() -> addDebugListeners());
        // Create a simulator watcher that terminates the debugTarget if the
        // process is killed or terminated.
        Thread simulatorTerminationWatcher = new Thread(
                new SimulatorTerminationWatcher(this, proc, projectName));
        simulatorTerminationWatcher.setName("Simulator termination watcher");
        simulatorTerminationWatcher.start();

        // Send compile request to rotalumis
        client.setDebugTarget(this);
    }

    public void start() throws CoreException {
        compileJob.setUser(true);
        compileJob.setPriority(Job.INTERACTIVE);
        compileJob.schedule();

        try {
            String modelPath = launch.getLaunchConfiguration()
                    .getAttribute(PooslConstants.CONFIGURATION_ATTRIBUTE_RELATIVE_PATH, ""); //$NON-NLS-1$
            String filePath = ResourcesPlugin.getWorkspace().getRoot()
                    .getFile(Path.fromPortableString(modelPath)).getLocation().toOSString();

            String rfcModelPath = FileURIConverter
                    .toConversion(FileURIConverter.removeFilePrefix(filePath));
            String rfcBasicPath = null;
            if (!ImportingHelper.useDefaultBasicclasses()) {
                String basicString = ImportingHelper.getBasicAbsoluteString();
                rfcBasicPath = FileURIConverter
                        .toConversion(FileURIConverter.removeFilePrefix(basicString));
            }
            String rawExternalConfigPath = launch.getLaunchConfiguration()
                    .getAttribute(PooslConstants.CONFIGURATION_ATTRIBUTE_EXTERNAL_CONFIG_PATH, ""); //$NON-NLS-1$
            if (rawExternalConfigPath != null) {
                externalConfigPath = FileURIConverter
                        .toConversion(FileURIConverter.removeFilePrefix(rawExternalConfigPath));
            }

            client.compile(rfcModelPath, rfcBasicPath, includes);
        } catch (Exception e) {
            PooslDebugHelper.showErrorMessage("Launch failed",
                    "Could not read the file locations.");
            terminate();
        }
    }

    private void dispatchEvent(DebugEvent debugEvent) {
        Object context = debugEvent.getSource();
        if (context instanceof ExecutionTreeContext) {
            context = ((ExecutionTreeContext) context).getContext();
        }
        if (context instanceof TreeSelection) {
            context = ((TreeSelection) context).getFirstElement();
        }
        if (context instanceof PooslDebugElement //
                && ((PooslDebugElement) context).getDebugTarget() != target) {
            return;
        }

        String threadName = ""; //$NON-NLS-1$
        if (context instanceof IThread) {
            IThread thread = (IThread) context;
            try {
                threadName = thread.getName();
            } catch (DebugException e) {
                LOGGER.log(Level.WARNING, "Could not get thread name when respond to debug event.",
                        e);
            }
        }

        if (debugEvent.getKind() != DebugEvent.MODEL_SPECIFIC) {
            return;
        }
        // Only react to model specific debug events.
        switch (debugEvent.getDetail()) {
        case PooslConstants.INSPECT_REQUEST:
            handleInspectRequest(threadName, debugEvent);
            break;
        case PooslConstants.PERFORM_TRANSITION:
            handlePerformTransition(threadName, debugEvent);
            break;
        case PooslConstants.COMM_EVENTS_CHANGE:
            handleCommEventsChange(threadName, debugEvent);
            break;
        case PooslConstants.CLEAR_COMM_EVENTS:
            handleClearCommEvents(threadName, debugEvent);
            break;
        default:
            // ignore other events
        }

    }

    private void handleClearCommEvents(String threadName, DebugEvent debugEvent) {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                if (PooslDebugHelper.isActiveDebugTarget(target)) {
                    // If this debugTarget is the current
                    // context then clear all comm messages
                    pooslSequenceDiagramMessageProvider.clearMessages();
                }
            }
        });
    }

    private void handleCommEventsChange(String threadName, DebugEvent debugEvent) {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                if (PooslDebugHelper.isActiveDebugTarget(target)) {
                    // If this debugtarget is the current
                    // context enable/disable events
                    setCommEventsEnabled((boolean) debugEvent.getData());
                }
            }
        });
    }

    private void handlePerformTransition(String threadName, DebugEvent debugEvent) {
        // The PET view requested a transition perform.
        Object source = debugEvent.getSource();

        if (source instanceof ExecutionTreeContext) {
            source = ((ExecutionTreeContext) source).getExecutiontreeBase();
        }
        if (source instanceof TExecutiontreeBase) {
            BigInteger handle = ((TExecutiontreeBase) source).getHandle();
            requests.put(handle.intValue(), threadName);
            clearStackframesOfAllThreads();
            clearPossibleTransitions();

            client.performTransition(handle);
        }
    }

    private void handleInspectRequest(String threadName, DebugEvent debugEvent) {
        // The debug view, PET view or variables view requested
        // an inspect
        Object source = debugEvent.getSource();
        if (source instanceof TreeSelection) {
            source = ((TreeSelection) source).getFirstElement();
        }
        if (source instanceof ExecutionTreeContext) {
            source = ((ExecutionTreeContext) source).getExecutiontreeBase();
        }
        if (source instanceof PooslVariable) {
            try {
                PooslValue selectedVariableValue = (PooslValue) ((PooslVariable) source).getValue();
                for (IVariable variable : selectedVariableValue.getVariables()) {
                    ((PooslVariable) variable).getSubVariables();
                }
            } catch (DebugException e) {
                LOGGER.log(Level.WARNING, "Could not handle debug event.", e);
            }
        } else if (source instanceof TExecutiontreeBase) {
            requests.put(((TExecutiontreeBase) source).getLocal().intValue(), threadName);
            client.inspectByHandle(((TExecutiontreeBase) source).getLocal(),
                    TInspectType.VARIABLE_CONTEXT);
        }
    }

    private void addDebugListeners() {
        performDebugSetting("Add listener", (workbench, window, contextService, service) -> {
            if (contextService != null) {
                contextActivationToken = contextService.activateContext(//
                        "org.eclipse.poosl.rotalumisclient.debugcontext"); //$NON-NLS-1$
            }

            // Add a debug context and debug event listener to react on debug changes
            service.addDebugContextListener(debugContextListener);
            DebugPlugin.getDefault().addDebugEventListener(debugEventSetListener);
            // Add this debugtarget as a breakpointlistener
            DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(target);
            // Add a resource change listener to listen to save actions from the
            // user during debugging
            ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
            // Switch to the debugging perspective
            try {
                workbench.showPerspective(PooslConstants.ID_POOSL_DEBUG_PERSPECTIVE, window);
            } catch (WorkbenchException e) {
                LOGGER.log(Level.WARNING, "Could not switch to debug perspective:", e);
            }
        });
    }

    private void removeDebugListeners() {
        performDebugSetting("Remove listener", (workbench, window, contextService, service) -> {
            if (contextService != null) {
                contextService.deactivateContext(contextActivationToken);
            }
            // Remove debug context and debug event listeners.
            DebugPlugin.getDefault().removeDebugEventListener(debugEventSetListener);

            service.removeDebugContextListener(debugContextListener);
            // Remove breakpoint listener.
            DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(target);
            // Remove the resourcechange listener
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
        });
    }

    private void askRestartOnChange(final List<IResource> changed) {
        if (changed.isEmpty()) {
            return;
        }
        final String fileName = changed.get(0).getName();
        Display.getDefault().asyncExec(() -> {
            String targetName = "?"; //$NON-NLS-1$
            try {
                targetName = " for " + target.getName() + "?"; //$NON-NLS-2$
            } catch (DebugException e1) {
                LOGGER.log(Level.WARNING, "Could not get debug target name.", e1.getSuppressed());
            }
            MessageDialog dialog = new MessageDialog(Display.getDefault().getActiveShell(), //
                    Messages.DIALOG_RELAUNCH_TITLE, //
                    MessageDialog.getImage(Dialog.DLG_IMG_MESSAGE_WARNING), //
                    MessageFormat.format(Messages.DIALOG_RELAUNCH_TEXT, fileName, targetName), //
                    MessageDialog.WARNING, //
                    new String[] {
                            Messages.DIALOG_RELAUNCH_BT_TERMINATE,
                            Messages.DIALOG_RELAUNCH_BT_CONTINUE },
                    0);

            if (dialog.open() == Window.OK) {
                try {
                    relaunch = true;
                    launch.terminate();
                } catch (DebugException e) {
                    LOGGER.log(Level.WARNING, "Could not terminate and relaunch after a save.", e);
                }
            } else {
                extentensionsInformStop();
                edited = true;
            }
        });
    }

    private interface DebugSettingTask {
        void apply(
                IWorkbench workbench, IWorkbenchWindow window, IContextService contextService,
                IDebugContextService service);
    }

    private void performDebugSetting(String taskname, DebugSettingTask task) {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if (window == null) {
            LOGGER.log(Level.SEVERE,
                    "Could not get active workbench window when trying to " + taskname + ".");
            try {
                target.terminate();
            } catch (DebugException e) {
                LOGGER.log(Level.SEVERE,
                        "Could not terminate target when trying to " + taskname + ".", e);
            }
        }
        IContextService contextService = workbench.getService(IContextService.class);
        IDebugContextService service = DebugUITools.getDebugContextManager()
                .getContextService(window);
        task.apply(workbench, window, contextService, service);
    }

    public void extentensionsInformStop() {
        if (!extensionInformedStop) {
            extensionInformedStop = true;
            ExternLaunchStopInformer stopInformer = new ExternLaunchStopInformer();
            stopInformer.executeInformLaunchStopped(launch);
        }
    }

    /**
     * This function is responsible to react to all possible responses from the
     * simulation. Responses are parsed by the
     * client and then send to this dispatch function.
     * 
     * @param response
     * @throws DebugException
     */
    public void dispatchResponse(Response response) throws DebugException {
        if (response.getCompile() != null) {
            dispatchCompileResponse(response);
        } else if (response.getEengineEventError() != null) {
            dispatchEngineEventError(response);
        } else if (response.getInstantiate() != null) {
            dispatchInstantiateResponse(response);
        } else if (response.getCommand() != null) {
            dispatchCommandResponse(response);
        } else if (response.getInspect() != null) {
            dispatchInspectResponse(response);
        } else if (response.getGetTransitions() != null) {
            dispatchGetTransitionResponse(response);
        } else if (response.getPerformTransition() != null) {
            dispatchPerformTransitionResponse(response);
        } else if (response.getCreateBreakpoint() != null) {
            dispathCreateBreakpointResponse(response);
        } else if (response.getDeleteBreakpoint() != null) {
            dispatchConfirmationResponse("Delete breakpoint", //
                    response.getDeleteBreakpoint().getResult());
        } else if (response.getEnableBreakpoint() != null) {
            dispatchConfirmationResponse("Enable breakpoint", //
                    response.getEnableBreakpoint().getResult());
        } else if (response.getDisableBreakpoint() != null) {
            dispatchConfirmationResponse("Disable breakpoint", //
                    response.getDisableBreakpoint().getResult());
        } else if (response.getExecutionState() != null) {
            dispatchExecutionStateResponse(response);
        } else if (response.getCommunicationEvent() != null) {
            dispatchCommunicationEventResponse(response);
        } else if (response.getEengineEventSetup() != null) {
            LOGGER.fine("Set sequence diagram setting response: "
                    + response.getEengineEventSetup().getResult());
            // Response of the enabling or disabling of the sequence diagram messages.
            // No action required
        } else if (response.getListFiles() != null) {
            dispatchGetListFilesResponse(response);
        } else if (response.getGetPosition() != null) {
            LOGGER.fine(
                    "Received position for handle:" + response.getGetPosition().getStmtHandle());
            pooslSourceMap.reponseSourceMapping(response.getGetPosition());

        } else if (response.getSetVariable() != null) {
            dispatchSetVariableResponse(response);
        } else {
            LOGGER.warning("Unrecognized response: ");
            try {
                LOGGER.warning(client.marshal(response));
            } catch (JAXBException e) {
                LOGGER.log(Level.WARNING, COULD_NOT_LOG_RESPONSE, e);
            }
        }

    }

    private void dispatchInspectResponse(Response response) throws DebugException {
        TInspectResponse inspectResponse = response.getInspect();

        if (inspectResponse.getProcess() != null) {
            inspectProcess(inspectResponse);
        } else if (inspectResponse.getData() != null) {
            inspectData(inspectResponse);
        } else if (inspectResponse.getVariableContext() != null) {
            inspectVariableContext(response, inspectResponse);
        } else if (inspectResponse.getModel() != null) {
            LOGGER.fine("Inspect response model: " + inspectResponse.getModel());
            finalizingSetup(inspectResponse.getModel());

        } else {
            LOGGER.warning("Unrecognized inspect response received ");
            try {
                LOGGER.warning(client.marshal(response));
            } catch (JAXBException e) {
                LOGGER.log(Level.WARNING, COULD_NOT_LOG_RESPONSE, e);
            }
        }

    }

    private void inspectData(TInspectResponse inspectResponse) throws DebugException {
        // Data inspect response received so update all corresponding
        // PooslValue objects.
        if (inspectResponse.getData().getVariables() != null) {
            // Needed for at least Rotalumis 23-11-2016
            BigInteger handle = inspectResponse.getData().getHandle();
            for (PooslStackFrame pooslStackFrame : Iterables
                    .concat(PooslDebugHelper.threadsToStackFrames(threads), stackFrames.values())) {
                if (pooslStackFrame.updateVariable(this, handle,
                        inspectResponse.getData().getVariables().getVariable())) {
                    fireEvent(
                            new DebugEvent(pooslStackFrame, DebugEvent.CHANGE, DebugEvent.CONTENT));
                }
            }
            LOGGER.fine("Inspect response data: " + handle + " - " //$NON-NLS-2$
                    + inspectResponse.getData().getLiteral() + ":" //$NON-NLS-1$
                    + inspectResponse.getData().getType());
        }
    }

    private void inspectProcess(TInspectResponse inspectResponse) throws DebugException {
        // Process class inspect response received so update the
        // corresponding thread with the execution tree and global
        // variables
        LOGGER.fine("Inspect response process: " + inspectResponse.getName());
        final PooslThread thread = PooslDebugHelper.getThreadByName(threads,
                inspectResponse.getName());
        if (thread != null) {
            thread.setExecutiontree(inspectResponse.getProcess().getExecutionTree());
            if (inspectResponse.getProcess().getInstanceVariables() != null) {
                thread.addStackFrame(
                        inspectResponse.getProcess().getInstanceVariables().getVariable());
                fireEvent(new DebugEvent(thread, DebugEvent.MODEL_SPECIFIC,
                        PooslConstants.INSPECT_RECEIVED));
            }
        }
    }

    private void inspectVariableContext(Response response, TInspectResponse inspectResponse)
            throws DebugException {
        // Variable context inspect response received so update
        // corresponding stackframe with local variables
        List<TVariable> tVariables = inspectResponse.getVariableContext().getVariable();
        BigInteger listHandle = inspectResponse.getHandle();

        LOGGER.fine("Inspect response variablecontext: " + tVariables);
        if (inspectResponse.getResult() == TObjectQueryResult.UNKNOWN_HANDLE) {
            LOGGER.warning("Inspect on variable context returns unknown handle: ");
            try {
                LOGGER.warning(client.marshal(response));
            } catch (JAXBException e) {
                LOGGER.log(Level.WARNING, COULD_NOT_LOG_RESPONSE, e);
            }
            return;
        }

        String threadName = requests.get(listHandle.intValue());
        if (threadName != null) {
            // localVariables
            PooslThread threadRequest = PooslDebugHelper.getThreadByName(getThreads(), threadName);
            if (threadRequest != null) {
                PooslStackFrame stackFrame = (PooslStackFrame) threadRequest.getStackFrame();
                if (stackFrame != null) {
                    stackFrame.addLocalVariables(tVariables, listHandle);
                    fireEvent(new DebugEvent(stackFrame, DebugEvent.CHANGE, DebugEvent.CONTENT));
                }
            }
        } else {
            // subVariables
            for (PooslStackFrame pooslStackFrame : Iterables
                    .concat(PooslDebugHelper.threadsToStackFrames(threads), stackFrames.values())) {
                if (pooslStackFrame.updateSubVariables(this, listHandle, tVariables)) {
                    fireEvent(
                            new DebugEvent(pooslStackFrame, DebugEvent.CHANGE, DebugEvent.CONTENT));
                }
            }
        }
    }

    private void dispatchGetTransitionResponse(Response response) {
        // Transitions inspect response received so get the selected
        // PooslThread in the debugview to get the
        // new PET and global variables
        TGetTransitionsResponse transitions = response.getGetTransitions();
        LOGGER.fine("Inspect response getTransitions");

        if (isTransitionUpdate(transitions.getTransitions().getTransition())) {
            fireEvent(new DebugEvent(target, DebugEvent.CHANGE, DebugEvent.CONTENT));
        }
    }

    private void dispatchPerformTransitionResponse(Response response) {
        // Single transition performed so get the new possible transitions.
        // Also get the execution state of the simulator to check for
        // breakpoint hits.
        TPerformTransitionResponse performedTransitionResponse = response.getPerformTransition();
        LOGGER.fine("Perform transition response: " + performedTransitionResponse.getResult());
        if (performedTransitionResponse.getResult() == TPerformTransitionResponseResult.OK) {
            clearStackframesOfAllThreads();
            clearPossibleTransitions();
            client.getTransitions();
            client.getExecutionState();
            fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.STEP_END));
        }
    }

    private void dispathCreateBreakpointResponse(Response response) {
        breakpointManager.handleBreakpointResonse(response.getCreateBreakpoint());
        dispatchConfirmationResponse("Create breakpoint", //
                response.getCreateBreakpoint().getResult());
    }

    private void dispatchConfirmationResponse(String action, String message) {
        LOGGER.fine(action + " response: " + message);
        if (!Messages.RESULT_OK.equals(message)) {
            LOGGER.warning("Failed to " + action);
        }
    }

    private void dispatchExecutionStateResponse(Response response) throws DebugException {
        // Respond to a change in the execution state of the simulator. This
        // usually only happens on a breakpoint hit or when specifically
        // requested.
        final TExecutionStateChangeResponse state = response.getExecutionState();
        simulatedTime = state.getTime().toString();
        LOGGER.fine("Execution state response: State->" + state.getState() + " simulatedTime->"
                + simulatedTime);

        if (state.getBreakpoints() != null) {
            if (!state.getBreakpoints().getBreakpoint().isEmpty()) {
                isSuspended = true;
                breakpointManager.handleBreakpointHit(this, state, pooslSourceMap);
            }

        } else if ("stopped".equals(state.getState())) { //$NON-NLS-1$
            isSuspended = true;
            fireEvent(
                    new DebugEvent(this, DebugEvent.MODEL_SPECIFIC, PooslConstants.STOPPED_STATE));

            final String message = (state.getMessage() != null && !state.getMessage().isEmpty())
                ? state.getMessage() : Messages.DEFAULT_STOPPED_TEXT;
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    MessageDialog.openInformation(Display.getDefault().getActiveShell(),
                            Messages.DEFAULT_STOPPED_TITLE, message);
                }
            });

        } else if (state.getError() != null) {
            isSuspended = true;
            final TErrorInfo errorState = state.getError();
            String processPath = errorState.getProcessPath();

            if (processPath != null && !processPath.isEmpty()) {
                final PooslThread errorThread = PooslDebugHelper.getThreadByName(threads,
                        processPath);

                // If a stacktrace is present StacktraceView will highlight
                // and navigate to the code (more precisely).
                // Active breakpoint which enables the same behavior in the
                // PETview is not needed in this case.
                if (stacktrace == null) {
                    errorThread.setActiveBreakpointNode(BigInteger.valueOf(errorState.getNode()));
                }
                // Fire a debug event to inform other views of the error in
                // the thread
                fireEvent(new DebugEvent(errorThread, DebugEvent.MODEL_SPECIFIC,
                        PooslConstants.ERROR_STATE));

            }
        } else {
            // Got execution state event without breakpoints so conform to
            // the new state (for now the simulator only sends paused)
            if ("paused".equals(state.getState()) && !isSuspended()) { //$NON-NLS-1$
                isSuspended = true;
                fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST));
                client.getTransitions();
            }
        }
    }

    private void dispatchCommunicationEventResponse(Response response) {
        // Received a sequence diagram message so add the message to the
        // provider
        TCommunicationEvent communicationEvent = response.getCommunicationEvent();
        LOGGER.fine("Sequence diagram message");
        pooslSequenceDiagramMessageProvider.addMessage(communicationEvent);
        externMessageInformer.executeInformMessage(target.getLaunch(), communicationEvent);
        messageCreditor.useCredit();
    }

    private void dispatchGetListFilesResponse(Response response) {
        List<org.eclipse.poosl.generatedxmlclasses.TListFilesResponse.File> rotalumisFiles = response
                .getListFiles().getFile();
        if (rotalumisFiles != null) {
            List<URI> uriFiles = new ArrayList<>();
            for (org.eclipse.poosl.generatedxmlclasses.TListFilesResponse.File rfcFile : rotalumisFiles) {
                String stringFile = FileURIConverter.fromConversion(rfcFile.getValue());
                uriFiles.add(URI.createFileURI(stringFile));
                files2handle.put(stringFile, rfcFile.getHandle());
            }
            (new ExternLaunchStartInformer()).executeInformDebugSelection(this, uriFiles);
        } else {
            LOGGER.severe("Invalid ListFiles Rotalumis response. Response contains no files.");
        }
    }

    private void dispatchSetVariableResponse(Response response) {
        TSetVariableResponse varRequest = response.getSetVariable();
        LOGGER.fine("Set variable response for handle: " + varRequest.getVarHandle());
        PooslVariable pVar = setVariableRequests.remove(varRequest.getVarHandle());
        if (varRequest.getResult() == TSetVariableResult.OK) {
            if (pVar != null) {
                pVar.verifiedNewValue();
            }

            // Get update from Rotalumis after set Variable succeeded
            clearPossibleTransitions();
            client.getTransitions();
            client.getExecutionState();

            PooslThread thread = PooslDebugHelper.getThread(threads, varRequest.getListHandle());
            if (thread != null) {
                thread.clearStackFrames();
                thread.getRotalumisStackFrames();
            }
        } else {
            LOGGER.warning("Variable could not be set for handle " + varRequest.getVarHandle()
                    + " with error " + varRequest.getError() + "  " + varRequest.getResult()); //$NON-NLS-2$
            PooslDebugHelper.showErrorMessage(Messages.DIALOG_SETVARIABLE_TITLE,
                    Messages.DIALOG_SETVARIABLE_TEXT);
        }
    }

    private void dispatchCommandResponse(Response response) throws DebugException {
        TCommandResponse commandResponse = response.getCommand();
        LOGGER.fine("Command response: " + commandResponse.getType() + " " //$NON-NLS-2$
                + commandResponse.getResult());
        switch (commandResponse.getType()) {
        case RUN:
            simulatedTime = ""; //$NON-NLS-1$
            for (IThread t : getThreads()) {
                t.resume();
            }
            fireEvent(new DebugEvent(this, DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST));
            if (stacktrace != null) {
                openErrorWindow();
            }
            if (TCommandResult.ERROR == commandResponse.getResult()) {
                isSuspended = true;
                client.getExecutionState();
                fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST));
            }
            stopTransitionRequester();
            break;
        case PAUSE:
            fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST));
            client.getExecutionState();
            startTransitionRequester();
            break;
        case STOP:
            simulatedTime = ""; //$NON-NLS-1$
            client.disconnect();
            fireEvent(new DebugEvent(this, DebugEvent.TERMINATE, DebugEvent.CLIENT_REQUEST));
            if (process.canTerminate() && !process.isTerminated()) {
                process.terminate();
            }
            stopTransitionRequester();
            break;
        case STEP:
            isSuspended = true;
            client.getExecutionState();
            client.getTransitions();
            fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST));
            break;
        case COMM_STEP:
            // intentional fall-through
        case PROCESS_STEP:
            // intentional fall-through
        case TIME_STEP:
            // No action required as these steps return the command
            // immediately. When the command is finished an execution state
            // event will occur instead this will not occur when there is an
            // error in the model and this step is executed
            if (TCommandResult.ERROR == commandResponse.getResult()) {
                isSuspended = true;
                client.getExecutionState();
                fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST));
            }
            break;
        default:
            LOGGER.warning("Unrecognized command response received: " + commandResponse.getType());
            break;
        }
    }

    private void dispatchInstantiateResponse(Response response) throws DebugException {
        TInstantiateResponse instantiateResponse = response.getInstantiate();
        LOGGER.fine("Instantiate response: " + instantiateResponse.getResult());
        if (instantiateResponse.getResult().equals(TInstantiateResult.OK)) {
            client.getListFiles(this.modelHandle);
            client.inspectModel();

            // Instantiate successful: send Sequence diagram setting to the simulator.
            client.setupCommunicationEvents(isCommEventsEnabled(), messageCreditor.getCurrentMax());
        } else {
            LOGGER.fine("Rotalumis is unable to instantiate the model: \n"
                    + instantiateResponse.getResult());
            this.terminate();
        }
    }

    private void dispatchEngineEventError(Response response) throws DebugException {
        stacktrace = response.getEengineEventError();
        LOGGER.fine("Engine error: " + stacktrace.getError() + " - " + stacktrace.getProcessPath() //$NON-NLS-2$
                + ":" + stacktrace.getStmtHandle()); //$NON-NLS-1$

        PooslThread errorThread = PooslDebugHelper.getThreadByName(threads,
                stacktrace.getProcessPath());
        if (errorThread == null && threads != null && threads.length > 0
                && threads[0] instanceof PooslThread) {
            errorThread = threads[0];
        }

        TErrorStacktrace errorStackTrace = stacktrace.getStacktrace();
        if (errorStackTrace != null && errorStackTrace.getStackframe() != null) {
            for (TErrorStackframe errorFrame : errorStackTrace.getStackframe()) {
                PooslStackFrame stackFrame = new PooslStackFrame(this, errorThread,
                        stacktrace.getProcessPath() + "/stackframe" + errorFrame.getId(), //$NON-NLS-1$
                        errorFrame.getVariableContextGlobal().getVariable(), null);
                stackFrame.addLocalVariables(errorFrame.getVariableContextLocal().getVariable(),
                        null);
                stackFrames.put(errorFrame.getId(), stackFrame);
            }
        } else {
            LOGGER.warning("Retrieved stacktrace with no stackframes!");
        }

        if (pooslSourceMap != null) {
            openErrorWindow();
        }
        fireEvent(new DebugEvent(this, DebugEvent.MODEL_SPECIFIC, PooslConstants.ENGINE_ERROR));
    }

    private void dispatchCompileResponse(Response response) throws DebugException {
        TCompileResponse compileResponse = response.getCompile();
        if (compileResponse.getError() != null && !compileResponse.getError().isEmpty()) {
            LOGGER.severe("Could not compile model: " + compileResponse.getError());
            PooslDebugHelper.showErrorMessage("Rotalumis Error",
                    "Rotalumis is unable to compile the model due to " + "an error: \n\n"
                            + compileResponse.getError());
            this.terminate();
        } else {
            this.modelHandle = compileResponse.getHandle();
            if (modelHandle != null) {
                client.instantiateModel(modelHandle, externalConfigPath);
            } else {
                LOGGER.severe(
                        "Invalid Rotalumis compile response. No error and no handle are returned.");
            }
        }
    }

    private void finalizingSetup(TInspectModel tInspectModel) {
        // Set instances and create the collection of threads based on the model
        pooslInstanceMap.setModel(tInspectModel);
        threads = pooslInstanceMap.createThreads(this);

        try {
            pooslSequenceDiagramMessageProvider = new PooslSequenceDiagramMessageProvider(this);
        } catch (CoreException e) {
            LOGGER.severe("SequenceDiagramMessageProvider could not be created for " + name);
        }
        // create PooslsourceMap
        pooslSourceMap = new PooslSourceMap(client, files2handle);

        // Add the debug target to the current launch
        launch.addDebugTarget(this);
        try {
            suspend();
        } catch (DebugException e) {
            LOGGER.severe("model could not be suspended, " + name);
        }
        if (PooslDebugHelper.isActiveDebugTarget(this)) {
            pooslSequenceDiagramMessageProvider.updateSequenceDiagramViewEventSetting();
        }
        breakpointManager.configureBreakpointManager(client, files2handle, modelHandle);
        compileJob.compileFinished();
    }

    private void openErrorWindow() {
        if (getStackTrace() != null) {
            Display display = PlatformUI.getWorkbench().getDisplay();
            display.asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        WindowCreater
                                .getWindowForError(
                                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                                .getActivePage().getActivePart().getSite(),
                                        PooslDebugTarget.this);
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Error window could not be created.",
                                e.getMessage());
                    }
                }
            });
        }
    }

    // =============----------- public getters -----------=============

    public Client getClient() {
        return client;
    }

    public Map<String, TInstanceType> getInstances() {
        return pooslInstanceMap.getInstances();
    }

    public Map<String, TInstanceType> getProcessInstances() {
        return pooslInstanceMap.getProcessInstances();
    }

    public String getSimulatedTime() {
        return simulatedTime;
    }

    public List<TTransition> getPossibleTransitions() {
        return possibleTransitions;
    }

    public PooslSourceMap getPooslSourceMap() {
        return pooslSourceMap;
    }

    public PooslSequenceDiagramMessageProvider getPooslSequenceDiagramMessageProvider() {
        return pooslSequenceDiagramMessageProvider;
    }

    public Map<String, String> getInstancePortMap() {
        return pooslInstanceMap.getInstancePortMap();
    }

    // =============----------- Clear functions -----------=============

    private void clearPossibleTransitions() {
        possibleTransitions.clear();
    }

    private void clearStackframesOfAllThreads() {
        for (int i = 0; i < threads.length; i++) {
            PooslThread thread = threads[i];
            thread.clearStackFrames();
        }
    }

    // =============----------- Credit System -----------=============

    @Override
    public void receiveCredits(int credits) {
        client.sendCredits(credits, commEventsEnabled);
    }

    public void setCommEventsEnabled(boolean enabled) {
        this.commEventsEnabled = enabled;
        getClient().setCommunicationMessagesEnabled(commEventsEnabled);
    }

    public boolean isCommEventsEnabled() {
        return commEventsEnabled;
    }

    // ===============----------- Step functions -----------===============

    public void step() {
        isSuspended = false;
        clearStackframesOfAllThreads();
        clearPossibleTransitions();
        target.client.stepModel();
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST));
    }

    public void timeStep() {
        isSuspended = false;
        clearStackframesOfAllThreads();
        clearPossibleTransitions();
        target.client.timeStepModel();
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST));
    }

    public void communicationStep() {
        isSuspended = false;
        clearStackframesOfAllThreads();
        clearPossibleTransitions();
        client.communicationStepModel();
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST));
    }

    public void processStep(String path) {
        isSuspended = false;
        clearStackframesOfAllThreads();
        clearPossibleTransitions();
        client.processStepModel(path);
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST));
    }

    // =============----------- DebugTarget Interfaces -----------=============

    @Override
    public ILaunch getLaunch() {
        return launch;
    }

    @Override
    public IProcess getProcess() {
        return process;
    }

    @Override
    public boolean hasThreads() throws DebugException {
        return threads.length > 0;
    }

    @Override
    public IThread[] getThreads() throws DebugException {
        return (threads == null) ? new IThread[0] : threads;
    }

    @Override
    public String getName() throws DebugException {
        return name;
    }

    public TEengineEventErrorResponse getStackTrace() {
        return stacktrace;
    }

    public IStackFrame getErrorStackFrame(int index) {
        try {
            return stackFrames.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isEdited() {
        return edited;
    }

    @Override
    public boolean canResume() {
        return isSuspended && !isTerminated;
    }

    @Override
    public void resume() throws DebugException {
        isSuspended = false;
        clearPossibleTransitions();
        clearStackframesOfAllThreads();
        client.resumeModel();
    }

    @Override
    public boolean isSuspended() {
        return isSuspended;
    }

    @Override
    public boolean canSuspend() {
        return !isSuspended && !isTerminated;
    }

    @Override
    public void suspend() throws DebugException {
        isSuspended = true;
        client.suspendModel();
    }

    @Override
    public boolean isTerminated() {
        return isTerminated;
    }

    @Override
    public boolean canTerminate() {
        return !isTerminated;
    }

    @Override
    public void terminate() throws DebugException {
        stopTransitionRequester();
        if (!isDisconnected) {
            client.stopModel();
        }

        // Remove selection listeners from the debug target
        Display.getDefault().asyncExec(() -> removeDebugListeners());
        if (!isTerminated) {
            isTerminated = true;
            disconnect();
            extentensionsInformStop();
        }
        for (IThread t : getThreads()) {
            t.terminate();
        }

        clearPossibleTransitions();
        requests.clear();
        breakpointManager.clear();
        if (pooslSequenceDiagramMessageProvider != null) {
            pooslSequenceDiagramMessageProvider.dispose();
        }

        if (pooslSourceMap != null) {
            pooslSourceMap.dispose();
        }
        compileJob.compileFailed();
        fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
        if (relaunch) {
            relaunch = false;
            DebugUITools.launch(launch.getLaunchConfiguration(), launch.getLaunchMode());
        }
    }

    @Override
    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public boolean canDisconnect() {
        return false;
    }

    @Override
    public void disconnect() throws DebugException {
        if (!isDisconnected) {
            isDisconnected = true;
        }
    }

    @Override
    public boolean supportsStorageRetrieval() {
        return false;
    }

    @Override
    public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
        return null;
    }

    @Override
    public boolean supportsStepFilters() {
        // support for step filters is disabled so always return false
        return false;
    }

    @Override
    public boolean isStepFiltersEnabled() {
        // support for step filters is disabled so always return false
        return false;
    }

    @Override
    public void setStepFiltersEnabled(boolean enabled) {
        // support for step filters is disabled so calling this interface
        // function will have not effect (and should never happen)
    }

    // ==============----------- Breakpoint Interfaces -----------==============

    @Override
    public boolean supportsBreakpoint(IBreakpoint breakpoint) {
        return breakpoint.getModelIdentifier().equals(getModelIdentifier());
    }

    @Override
    public void breakpointAdded(IBreakpoint breakpoint) {
        breakpointManager.addBreakpoint(breakpoint);
    }

    @Override
    public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
        breakpointManager.deleteBreakpoint(breakpoint);
    }

    @Override
    public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
        breakpointManager.breakpointChanged(breakpoint);
    }

    // ==============----------- Change Value -----------==============

    public void setVariable(PooslVariable pooslVariable, String newValue) throws DebugException {
        PooslValue pooslValue = (PooslValue) pooslVariable.getValue();
        BigInteger handle = pooslValue.getHandle();
        setVariableRequests.put(handle, pooslVariable);
        client.setVariable(handle, pooslVariable.getListHandle(), TConstantType.UNKNOWN, newValue);
    }

    class CompileJob extends Job {
        private boolean isSetup;

        private boolean isFailed;

        CompileJob() {
            super("Compiling model");
        }

        public void compileFinished() {
            isSetup = true;
        }

        public void compileFailed() {
            isFailed = true;
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            monitor.beginTask("Compiling " + name, 2);
            monitor.worked(1);
            while (!isSetup) {
                if (monitor.isCanceled() || isFailed) {
                    monitor.done();
                    return new Status(IStatus.CANCEL, Activator.PLUGIN_ID,
                            "Model compilation is canceled.");
                }
            }
            monitor.done();
            return new Status(IStatus.OK, Activator.PLUGIN_ID, "Model is compiled by Rotalumis.");
        }

        @Override
        protected void canceling() {
            try {
                terminate();
            } catch (DebugException e) {

            }
        }
    }

    // ==============----------- Transition Requester -----------==============

    private void startTransitionRequester() {
        stopTransitionRequester();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                client.getTransitions();
            }
        }, 0, 1000);
    }

    private void stopTransitionRequester() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private boolean isTransitionUpdate(List<TTransition> newTransitions) {
        if (possibleTransitions.size() != newTransitions.size()) {
            possibleTransitions = newTransitions;
            return true;
        }
        for (int i = 0; i < newTransitions.size(); i++) {
            if (!isTransEqual(possibleTransitions.get(i), newTransitions.get(i))) {
                possibleTransitions = newTransitions;
                return true;
            }
        }
        return false;
    }

    private static final class TransitionHandlerComparator<S> {
        private final Function<TTransition, S> getter;

        private final Function<S, BigInteger> handler;

        private TransitionHandlerComparator(Function<TTransition, S> getter,
                Function<S, BigInteger> handler) {
            this.getter = getter;
            this.handler = handler;
        }

        private Boolean isEquals(TTransition current, TTransition update) {

            S currentValue = getter.apply(current);
            S updateValue = getter.apply(update);
            if (currentValue == null && currentValue == null) {
                return null; // handle not applicable
            }
            return (currentValue != null) && (currentValue != null) //
                ? handler.apply(currentValue).equals(handler.apply(updateValue)) : false;
        }

    }

    private boolean isTransEqual(TTransition current, TTransition update) {
        if (current == update) {
            return true;
        } else if (update == null || current == null) {
            return false; // we know update != current
        }
        for (TransitionHandlerComparator<?> comparator : TRANST_HANDLER_COMPARATORS) {
            Boolean result = comparator.isEquals(current, update);
            if (result != null) {
                return result;
            }
        }

        return true;
    }

}
