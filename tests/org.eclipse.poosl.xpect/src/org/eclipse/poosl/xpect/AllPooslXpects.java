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
package org.eclipse.poosl.xpect;

import org.eclipse.poosl.xpect.validation.channel.PooslValidationChannelTest;
import org.eclipse.poosl.xpect.validation.importing.PooslValidationImportingTest;
import org.eclipse.poosl.xpect.validation.misc.PooslValidationMiscTest;
import org.eclipse.poosl.xpect.validation.suppress.PooslValidationSuppressTest;
import org.eclipse.poosl.xpect.validation.typecheck.PooslValidationTypeCheckTest;
import org.eclipse.poosl.xpect.validation.unique.PooslValidationUniqueTest;
import org.eclipse.poosl.xpect.validation.unused.PooslValidationUnusedTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Declaration of all Xpect tests.
 * <p>
 * This suite can be used in Eclipse to launch all verifications.
 * </p>
 * <p>
 * This test suite is ignored by Maven as it ends with 'Xpects' instead of 'Test' or 'Tests'.
 * </p>
 * 
 * @author <a href="nicolas.peransin@obeo.fr">Nicolas PERANSIN</a>
 */
@SuiteClasses({
    // Disabled scoping and linking test, no longer uses default xtext reference
    // PooslLinkingTest.class,
    // PooslScopingTest.class,
    PooslValidationChannelTest.class, 
    PooslValidationImportingTest.class,
    PooslValidationMiscTest.class,
    PooslValidationSuppressTest.class,
    PooslValidationTypeCheckTest.class,
    PooslValidationUniqueTest.class, 
    PooslValidationUnusedTest.class
})

@RunWith(Suite.class)
public class AllPooslXpects {
}
