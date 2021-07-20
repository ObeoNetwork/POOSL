/**
 */
package org.eclipse.poosl.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.poosl.Import;
import org.eclipse.poosl.PooslPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Import</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.poosl.impl.ImportImpl#getImportURI <em>Import URI</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportImpl extends EObjectImpl implements Import {
    /**
     * The default value of the '{@link #getImportURI() <em>Import URI</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getImportURI()
     * @generated
     * @ordered
     */
    protected static final String IMPORT_URI_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getImportURI() <em>Import URI</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getImportURI()
     * @generated
     * @ordered
     */
    protected String importURI = IMPORT_URI_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ImportImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PooslPackage.Literals.IMPORT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getImportURI() {
        return importURI;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setImportURI(String newImportURI) {
        String oldImportURI = importURI;
        importURI = newImportURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PooslPackage.IMPORT__IMPORT_URI, oldImportURI, importURI));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case PooslPackage.IMPORT__IMPORT_URI:
            return getImportURI();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case PooslPackage.IMPORT__IMPORT_URI:
            setImportURI((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case PooslPackage.IMPORT__IMPORT_URI:
            setImportURI(IMPORT_URI_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case PooslPackage.IMPORT__IMPORT_URI:
            return IMPORT_URI_EDEFAULT == null ? importURI != null : !IMPORT_URI_EDEFAULT.equals(importURI);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (importURI: "); //$NON-NLS-1$
        result.append(importURI);
        result.append(')');
        return result.toString();
    }

} // ImportImpl
