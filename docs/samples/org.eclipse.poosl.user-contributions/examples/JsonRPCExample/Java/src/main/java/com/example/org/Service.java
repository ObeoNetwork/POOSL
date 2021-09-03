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
package com.example.org;

import com.googlecode.jsonrpc4j.JsonRpcMethod;

import java.util.*;

/**
 * Planning service implementation, to be used with the JSON-RPC server.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 */
public class Service {
    /**
     * Calculates and returns the sum of two numbers that may be either integer or real in POOSL.
     *
     * @param a
     *            Number A
     * @param b
     *            Number B
     * @return Sum of numbers A and B.
     */
    @JsonRpcMethod("sum_two_numbers")
    public double sumTwoNumbers(double a, double b) {
        return a + b;
    }

    /**
     * Sorts the POOSL array provided as an argument and returns it.
     *
     * @param input
     *            List of integers
     * @return Sorted list of integers.
     */
    public List<Integer> sort(List<Integer> input) {
        input.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return input;
    }

    /**
     * Creates a dictionary (map) with contact information and returns it.
     *
     * @return Map with contact info.
     */
    @JsonRpcMethod("contact_info")
    public Map<String, Object> contactInfo() {
        Map<String, Object> result = new HashMap<>();

        result.put("name", "John Doe");
        result.put("street", "Python Road 31");
        result.put("zip", 90201);
        result.put("city", "Big City");
        result.put("married", false);
        result.put("spouse", null);

        return result;
    }
}
