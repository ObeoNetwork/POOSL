/**
 */
package nl.esi.poosl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link nl.esi.poosl.Variable#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see nl.esi.poosl.PooslPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see nl.esi.poosl.PooslPackage#getVariable_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link nl.esi.poosl.Variable#getName <em>Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // Variable
