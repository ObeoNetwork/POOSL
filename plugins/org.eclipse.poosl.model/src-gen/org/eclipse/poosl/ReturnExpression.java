/**
 */
package org.eclipse.poosl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Return Expression</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.poosl.ReturnExpression#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.poosl.PooslPackage#getReturnExpression()
 * @model
 * @generated
 */
public interface ReturnExpression extends Expression {
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
     * @see org.eclipse.poosl.PooslPackage#getReturnExpression_Expression()
     * @model containment="true"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link org.eclipse.poosl.ReturnExpression#getExpression <em>Expression</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

} // ReturnExpression
