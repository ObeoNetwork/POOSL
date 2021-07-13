package org.eclipse.poosl.xtext.descriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.poosl.Import;
import org.eclipse.poosl.Poosl;
import org.eclipse.poosl.PooslPackage.Literals;
import org.eclipse.poosl.xtext.helpers.PooslValidationHelper;
import org.eclipse.poosl.xtext.importing.ImportingHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

public final class PooslDescription {
    private static final String IMPORT_SEPARATOR = ","; //$NON-NLS-1$

    private static final String STR_IMPORTS = "imports"; //$NON-NLS-1$

    private static final String STR_IMPORTLIBS = "importlibs"; //$NON-NLS-1$

    private static final String STR_IMPORTLIBSRAW = "importlibsraw"; //$NON-NLS-1$

    private PooslDescription() {
        throw new IllegalStateException("Utility class");
    }

    // --- Set -------

    public static Map<String, String> createUserData(Poosl poosl) {
        if (poosl.getImports().isEmpty() && poosl.getImportLibs().isEmpty()) {
            return Collections.emptyMap();
        }

        HashMap<String, String> map = new HashMap<>();
        if (!poosl.getImports().isEmpty()) {
            StringBuilder importBuilder = new StringBuilder();
            for (Import pImport : poosl.getImports()) {
                String importString = ImportingHelper.importToString(pImport);
                if (importString != null) {
                    URI resolved = ImportingHelper.resolveImportUri(poosl.eResource().getURI(), URI.createURI(importString));
                    String resolvedString = (resolved == null) ? "" : resolved.toString(); //$NON-NLS-1$
                    addImportSeperator(importBuilder);
                    importBuilder.append(resolvedString);
                }
            }
            map.put(STR_IMPORTS, importBuilder.toString());
        }

        if (!poosl.getImportLibs().isEmpty()) {
            StringBuilder importlibBuilder = new StringBuilder();
            StringBuilder importlibRawBuilder = new StringBuilder();

            for (Import pImport : poosl.getImportLibs()) {
                String importString = ImportingHelper.importToString(pImport);
                if (importString != null) {
                    URI resolved = ImportingHelper.resolveImportLibUri(poosl.eResource(), URI.createURI(importString));
                    String resolvedString = (resolved == null) ? "" : resolved.toString(); //$NON-NLS-1$
                    addImportSeperator(importlibBuilder);
                    addImportSeperator(importlibRawBuilder);
                    importlibBuilder.append(resolvedString);
                    importlibRawBuilder.append(importString);
                }
            }
            map.put(STR_IMPORTLIBS, importlibBuilder.toString());
            map.put(STR_IMPORTLIBSRAW, importlibRawBuilder.toString());
        }
        return map;
    }

    private static void addImportSeperator(StringBuilder stringBuilder) {
        if (stringBuilder.length() != 0) {
            stringBuilder.append(IMPORT_SEPARATOR);
        }
    }

    // --- Get -------

    public static List<String> getImports(IEObjectDescription descr) {
        if (!checkValidity(descr))
            return Collections.emptyList();
        String imports = descr.getUserData(STR_IMPORTS);
        if (imports == null || imports.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(imports.split(IMPORT_SEPARATOR, -1));
        }
    }

    /**
     * Returns a list of resolved/unresolved importlib pairs. Pair.getFirst() contains the resolved importlib location,
     * Pair.getSecond() contains the unresolved. If the importlib could not be resolved Pair.getFirst() is empty.
     * 
     * @param descr
     * @return a list of resolved/unresolved importlib pairs.
     */
    public static List<Pair<String, String>> getImportLibRaw(IEObjectDescription descr) {
        String importlibs = descr.getUserData(STR_IMPORTLIBS);
        String rawImportlibs = descr.getUserData(STR_IMPORTLIBSRAW);
        if (importlibs == null || rawImportlibs == null) {
            return Collections.emptyList();
        } else {
            List<Pair<String, String>> tuples = new ArrayList<>();
            String[] importlibSplit = importlibs.split(IMPORT_SEPARATOR, -1);
            String[] rawImportlibSplit = rawImportlibs.split(IMPORT_SEPARATOR, -1);
            for (int i = 0; i < Math.min(importlibSplit.length, rawImportlibSplit.length); i++) {
                tuples.add(Tuples.create(importlibSplit[i], rawImportlibSplit[i]));
            }
            return tuples;
        }
    }

    public static List<String> getImportLibs(IEObjectDescription descr) {
        if (!checkValidity(descr))
            return Collections.emptyList();

        String imports = descr.getUserData(STR_IMPORTLIBS);
        if (imports == null || imports.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(imports.split(IMPORT_SEPARATOR));
        }
    }

    private static boolean checkValidity(IEObjectDescription descr) {
        return PooslValidationHelper.checkValidity(descr, Literals.POOSL);
    }
}
