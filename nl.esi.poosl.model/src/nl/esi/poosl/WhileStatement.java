/**
 */
package nl.esi.poosl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>While Statement</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link nl.esi.poosl.WhileStatement#getCondition <em>Condition</em>}</li>
 * <li>{@link nl.esi.poosl.WhileStatement#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see nl.esi.poosl.PooslPackage#getWhileStatement()
 * @model
 * @generated
 */
public interface WhileStatement extends Statement {
    /**
     * Returns the value of the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Condition</em>' containment reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Condition</em>' containment reference.
     * @see #setCondition(Expression)
     * @see nl.esi.poosl.PooslPackage#getWhileStatement_Condition()
     * @model containment="true"
     * @generated
     */
    Expression getCondition();

    /**
     * Sets the value of the '{@link nl.esi.poosl.WhileStatement#getCondition <em>Condition</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Condition</em>' containment reference.
     * @see #getCondition()
     * @generated
     */
    void setCondition(Expression value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Body</em>' containment reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Body</em>' containment reference.
     * @see #setBody(Statement)
     * @see nl.esi.poosl.PooslPackage#getWhileStatement_Body()
     * @model containment="true"
     * @generated
     */
    Statement getBody();

    /**
     * Sets the value of the '{@link nl.esi.poosl.WhileStatement#getBody <em>Body</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Body</em>' containment reference.
     * @see #getBody()
     * @generated
     */
    void setBody(Statement value);

} // WhileStatement
