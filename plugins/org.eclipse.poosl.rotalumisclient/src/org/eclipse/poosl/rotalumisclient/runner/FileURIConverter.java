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
package org.eclipse.poosl.rotalumisclient.runner;

/**
 * The FileURIConverter.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public final class FileURIConverter {

    private FileURIConverter() {
        throw new IllegalStateException("Utility class");
    }

    // rfc1738 converter
    /**
     * Convert normal file uri to new uri that is allowed in API.
     * 
     * @param input
     * @return the converted uri
     */
    public static String toConversion(String input) {
        StringBuilder output = new StringBuilder();
        byte[] bytes = input.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            boolean escape = false;
            int c = bytes[i];

            if (c < 0x00) {
                c = c & 0xFF;
                escape = true;
            } else if (c < 0x20 || c > 0x7F) {
                escape = true;
            } else {
                switch (c) {
                case 0x3C: // <
                case 0x3E: // >
                case 0x22: // "
                case 0x23: // #
                case 0x25: // %
                case 0x7B: // {
                case 0x7D: // }
                case 0x7C: // |
                case 0x5E: // ^
                case 0x7E: // ~
                case 0x5B: // [
                case 0x5D: // ]
                case 0x60: // `
                case 0x27: // '
                case 0x20: // SPACE
                case 0x3b: // ;
                case 0x3f: // ?
                case 0x40: // @
                case 0x3d: // =
                case 0x26: // &
                case 0x7f: // DEL
                    escape = true;
                    break;
                case 0x5c: // '\'
                case 0x2f: // /
                case 0x3a: // :
                default:
                    break;
                }
            }

            if (escape) {
                output.append('%');
                output.append(Integer.toHexString(c));
            } else {
                output.append(new String(new byte[] { bytes[i] }));
            }
        }
        return output.toString();
    }

    /**
     * Convert url encoded file scheme to normal scheme.
     * 
     * @param input
     * @return the converted uri
     */
    public static String fromConversion(String input) {
        int bIndex = 0;
        byte[] bytes = new byte[input.length()];
        for (int l = 0; l < input.length(); l++) {
            char i = input.charAt(l);
            byte b;
            if (i == '%') {
                String hex = input.substring(l + 1, l + 3);
                b = (byte) Integer.parseInt(hex, 16);
                l += 2;
            } else {
                b = (byte) i;
            }
            bytes[bIndex++] = b;
        }
        return new String(bytes).trim();
    }

    public static String removeFilePrefix(String string) {
        if (string.startsWith("file://")) { //$NON-NLS-1$
            return string.substring(7);
        }
        return string;
    }
}
