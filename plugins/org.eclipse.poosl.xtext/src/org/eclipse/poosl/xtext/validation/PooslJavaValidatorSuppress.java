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
package org.eclipse.poosl.xtext.validation;

import java.io.UnsupportedEncodingException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.poosl.Annotable;
import org.eclipse.poosl.Annotation;
import org.eclipse.poosl.Expression;
import org.eclipse.poosl.PooslPackage;
import org.eclipse.poosl.StringConstant;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * The PooslJavaValidatorSuppress.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslJavaValidatorSuppress extends AbstractPooslValidator {
    public static final String ANNOTATION_SUPPRESSWARNINGS = "SuppressWarnings"; //$NON-NLS-1$

    public enum WarningType {
        UNUSED("\"unused\"", true), UNCONNECTED("\"unconnected\"", true), TYPECHECK("\"typecheck\"", true), RETURN("\"return\"", true), ANNOTATION("\"annotation\"", true), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

        IMPORT("\"import\"", false), ASSIGNMENT("\"assignment\"", false), UNGUARDED_LOOP("\"unguardedloop\"", false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        private final String text;

        private final boolean allowSuppressed;

        WarningType(final String text, final boolean canBeSuppressed) {
            this.text = text;
            this.allowSuppressed = canBeSuppressed;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private String normalizeMessage(String value) {
        // Based on MarkerInfo.checkValidAttribute
        if (value.length() < 21000)
            return value;
        byte[] bytes;
        try {
            bytes = value.getBytes("UTF-8"); //$NON-NLS-1$
        } catch (UnsupportedEncodingException uee) {
            // cannot validate further
            return value;
        }
        if (bytes.length > 65535) {
            return value.substring(0, 10000) + "..."; //$NON-NLS-1$
        }
        return value;
    }

    @Override
    protected void info(String message, EObject source, EStructuralFeature feature, int index, String code, String... issueData) {
        getMessageAcceptor().acceptInfo(normalizeMessage(message), source, feature, index, code, issueData);
    }

    @Override
    protected void info(String message, EObject source, EStructuralFeature feature, String code, String... issueData) {
        getMessageAcceptor().acceptInfo(normalizeMessage(message), source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData);
    }

    @Override
    protected void error(String message, EObject source, EStructuralFeature feature, int index, String code, String... issueData) {
        getMessageAcceptor().acceptError(normalizeMessage(message), source, feature, index, code, issueData);
    }

    @Override
    protected void error(String message, EObject source, EStructuralFeature feature, String code, String... issueData) {
        getMessageAcceptor().acceptError(normalizeMessage(message), source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData);
    }

    @Override
    protected void warning(String message, EObject source, EStructuralFeature feature, int index, String code, String... issueData) {
        super.warning(message, source, feature, index, code, issueData);
        throw new IllegalArgumentException("Unsupported method, call warning(String, EObject, EStructuralFeature, int, String, WarningType, String...) instead.");
    }

    @Override
    protected void warning(String message, EObject source, EStructuralFeature feature, String code, String... issueData) {
        super.warning(message, source, feature, code, issueData);
        throw new IllegalArgumentException("Unsupported method, call warning(String, EObject, EStructuralFeature, String, WarningType, String...) instead.");
    }

    protected void warning(String message, EStructuralFeature feature, String code, WarningType warningType, String... issueData) {
        warning(message, getCurrentObject(), feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, warningType, issueData);
    }

    protected void warning(String message, EStructuralFeature feature, int index, String code, WarningType warningType, String... issueData) {
        warning(message, getCurrentObject(), feature, index, code, warningType, issueData);
    }

    protected void warning(String message, EObject source, EStructuralFeature feature, int index, String code, WarningType warningType, String... issueData) {
        if (!suppressWarning(source, warningType)) {
            getMessageAcceptor().acceptWarning(normalizeMessage(message), source, feature, index, code, issueData);
        }
    }

    protected void warning(String message, EObject source, EStructuralFeature feature, String code, WarningType warningType, String... issueData) {
        if (!suppressWarning(source, warningType)) {
            getMessageAcceptor().acceptWarning(normalizeMessage(message), source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData);
        }
    }

    // -----------------SuppressWarning-----------------//

    private boolean suppressWarning(EObject element, WarningType warningType) {
        if (warningType.allowSuppressed) {
            EObject runningElement = element;
            while (runningElement != null && !(runningElement instanceof Annotable)) {
                runningElement = runningElement.eContainer();
            }
            if (runningElement instanceof Annotable) {
                for (Annotation annotation : ((Annotable) runningElement).getAnnotations()) {
                    if (interpretAnnotationAsSuppressWarning(annotation, warningType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean interpretAnnotationAsSuppressWarning(Annotation annotation, WarningType warningType) {
        String name = annotation.getName();
        if (name.equalsIgnoreCase(ANNOTATION_SUPPRESSWARNINGS)) {
            for (Expression exp : annotation.getArguments()) {
                if (exp instanceof StringConstant) {
                    String value = ((StringConstant) exp).getValue();
                    if (value.equalsIgnoreCase(warningType.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void checkSuppresWarningArguments(Annotation annotation) {
        int index = 0;
        for (Expression exp : annotation.getArguments()) {
            if (exp instanceof StringConstant) {
                String providedType = ((StringConstant) exp).getValue();
                if (!warningTypeExists(providedType)) {
                    warning("Suppressed warning type is not recognized. The type should be between quotes in lower case. " + "(Supported types are " + WarningType.UNUSED + ", "
                            + WarningType.UNCONNECTED + ", " + WarningType.TYPECHECK + " and " + WarningType.RETURN + ")", annotation, PooslPackage.Literals.ANNOTATION__ARGUMENTS, index++, null,
                            WarningType.ANNOTATION);
                }
            }
        }
    }

    private boolean warningTypeExists(String providedType) {
        for (WarningType warningType : WarningType.values()) {
            if (warningType.toString().equalsIgnoreCase(providedType)) {
                return true;
            }
        }
        return false;
    }
}
