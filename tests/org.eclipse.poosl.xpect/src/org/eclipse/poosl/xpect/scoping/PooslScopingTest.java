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
package org.eclipse.poosl.xpect.scoping;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.xpect.runner.XpectRunner;
import org.xpect.xtext.lib.tests.ScopingTest;

/**
 * The PooslScopingTest.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
@RunWith(XpectRunner.class)
@Ignore 
@Deprecated // Disabled scoping and linking test, no longer uses default xtext reference
public class PooslScopingTest extends ScopingTest {

}
