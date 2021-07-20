/**
 */
package org.eclipse.poosl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Switch Expression</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.poosl.SwitchExpression#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.poosl.SwitchExpression#getCases <em>Cases</em>}</li>
 * <li>{@link org.eclipse.poosl.SwitchExpression#getDefaultBody <em>Default Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.poosl.PooslPackage#getSwitchExpression()
 * @model
 * @generated
 */
public interface SwitchExpression extends Expression {
    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Expression</em>' containment reference.
     * @see #setExpression(Expression)
     * @see org.eclipse.poosl.PooslPackage#getSwitchExpression_Expression()
     * @model containment="true"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.SwitchExpression#getExpression <em>Expression</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

    /**
     * Returns the value of the '<em><b>Cases</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.poosl.SwitchExpressionCase}. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cases</em>' containment reference list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Cases</em>' containment reference list.
     * @see org.eclipse.poosl.PooslPackage#getSwitchExpression_Cases()
     * @model containment="true"
     * @generated
     */
    EList<SwitchExpressionCase> getCases();

    /**
     * Returns the value of the '<em><b>Default Body</b></em>' containment reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Body</em>' containment reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Default Body</em>' containment reference.
     * @see #setDefaultBody(Expression)
     * @see org.eclipse.poosl.PooslPackage#getSwitchExpression_DefaultBody()
     * @model containment="true"
     * @generated
     */
    Expression getDefaultBody();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.SwitchExpression#getDefaultBody <em>Default Body</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Default Body</em>' containment reference.
     * @see #getDefaultBody()
     * @generated
     */
    void setDefaultBody(Expression value);

} // SwitchExpression
