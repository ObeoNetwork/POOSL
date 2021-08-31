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
package org.eclipse.poosl.xtext.ui;

import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

/**
 * The PooslTerminalsTokenTypeToPartitionMapper.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class PooslTerminalsTokenTypeToPartitionMapper extends TerminalsTokenTypeToPartitionMapper {

    @Override
    protected String calculateId(String tokenName, int tokenType) {
        if ("RULE_POOSL_STRING".equals(tokenName)) { //$NON-NLS-1$
            return STRING_LITERAL_PARTITION;
        } else if ("RULE_CHARACTER".equals(tokenName)) { //$NON-NLS-1$
            return STRING_LITERAL_PARTITION;
        } else {
            return super.calculateId(tokenName, tokenType);
        }
    }
}
