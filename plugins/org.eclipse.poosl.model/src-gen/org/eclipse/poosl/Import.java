/**
 * Copyright (c) 2021 TNO/ESI
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     TNO/ESI - initial API and implementation
 *     Obeo - refactoring
 */
package org.eclipse.poosl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Import</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.poosl.Import#getImportURI <em>Import URI</em>}</li>
 * </ul>
 *
 * @see org.eclipse.poosl.PooslPackage#getImport()
 * @model
 * @generated
 */
public interface Import extends EObject {
    /**
     * Returns the value of the '<em><b>Import URI</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Import URI</em>' attribute.
     * @see #setImportURI(String)
     * @see org.eclipse.poosl.PooslPackage#getImport_ImportURI()
     * @model
     * @generated
     */
    String getImportURI();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.Import#getImportURI <em>Import URI</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Import URI</em>' attribute.
     * @see #getImportURI()
     * @generated
     */
    void setImportURI(String value);

} // Import
