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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>If Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.poosl.IfExpression#getCondition <em>Condition</em>}</li>
 * <li>{@link org.eclipse.poosl.IfExpression#getThenClause <em>Then Clause</em>}</li>
 * <li>{@link org.eclipse.poosl.IfExpression#getElseClause <em>Else Clause</em>}</li>
 * </ul>
 *
 * @see org.eclipse.poosl.PooslPackage#getIfExpression()
 * @model
 * @generated
 */
public interface IfExpression extends Expression {
    /**
     * Returns the value of the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of the '<em>Condition</em>' containment reference.
     * @see #setCondition(Expression)
     * @see org.eclipse.poosl.PooslPackage#getIfExpression_Condition()
     * @model containment="true"
     * @generated
     */
    Expression getCondition();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.IfExpression#getCondition <em>Condition</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Condition</em>' containment reference.
     * @see #getCondition()
     * @generated
     */
    void setCondition(Expression value);

    /**
     * Returns the value of the '<em><b>Then Clause</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of the '<em>Then Clause</em>' containment reference.
     * @see #setThenClause(Expression)
     * @see org.eclipse.poosl.PooslPackage#getIfExpression_ThenClause()
     * @model containment="true"
     * @generated
     */
    Expression getThenClause();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.IfExpression#getThenClause <em>Then Clause</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Then Clause</em>' containment reference.
     * @see #getThenClause()
     * @generated
     */
    void setThenClause(Expression value);

    /**
     * Returns the value of the '<em><b>Else Clause</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of the '<em>Else Clause</em>' containment reference.
     * @see #setElseClause(Expression)
     * @see org.eclipse.poosl.PooslPackage#getIfExpression_ElseClause()
     * @model containment="true"
     * @generated
     */
    Expression getElseClause();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.IfExpression#getElseClause <em>Else Clause</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Else Clause</em>' containment reference.
     * @see #getElseClause()
     * @generated
     */
    void setElseClause(Expression value);

} // IfExpression
