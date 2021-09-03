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
package org.eclipse.poosl.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.poosl.Annotation;
import org.eclipse.poosl.PooslFactory;
import org.eclipse.poosl.PooslPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.poosl.Annotation} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class AnnotationItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AnnotationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addNamePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_Annotation_name_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Annotation_name_feature", "_UI_Annotation_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                PooslPackage.Literals.ANNOTATION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(PooslPackage.Literals.ANNOTATION__ARGUMENTS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns Annotation.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/Annotation")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((Annotation) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_Annotation_type") : //$NON-NLS-1$
                getString("_UI_Annotation_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(Annotation.class)) {
        case PooslPackage.ANNOTATION__NAME:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case PooslPackage.ANNOTATION__ARGUMENTS:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createExpressionSequence()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createExpressionSequenceRoundBracket()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createAssignmentExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createCurrentTimeExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createDataMethodCallExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createIfExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createSwitchExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createNewExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createReturnExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createSelfExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createVariableExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createWhileExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createBinaryOperatorExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createUnaryOperatorExpression()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createBooleanConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createCharacterConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createIntegerConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createNilConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createRealConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createFloatConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createStringConstant()));

        newChildDescriptors.add(createChildParameter(PooslPackage.Literals.ANNOTATION__ARGUMENTS, PooslFactory.eINSTANCE.createEnvironmentConstant()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return PooslEditPlugin.INSTANCE;
    }

}
