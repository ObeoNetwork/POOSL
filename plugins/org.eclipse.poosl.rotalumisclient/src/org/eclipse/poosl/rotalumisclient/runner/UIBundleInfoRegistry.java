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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

/**
 * The UIBundleInfoRegistry.
 * 
 * @author <a href="mailto:arjan.mooij@tno.nl">Arjan Mooij</a>
 *
 */
public class UIBundleInfoRegistry implements IBundleInfo.Registry {

    public static class UIBundleInfo implements IBundleInfo {
        private final Bundle bundle;

        private final URI locationURI;

        public UIBundleInfo(URI locationURI, Bundle bundle) {
            super();
            this.locationURI = locationURI;
            this.bundle = bundle;
        }

        public List<URI> find(Context context) {
            return getInfo().find(context);
        }

        public URI find(Context context, String fileName) {
            return getInfo().find(context, fileName);
        }

        public List<URI> find(
                Context context, String path, Predicate<String> matcher, String... fileExtensions) {
            return getInfo().find(context, path, matcher, fileExtensions);
        }

        public Bundle getBundle() {
            return bundle;
        }

        private ResourceAccess.ResourceInfo getInfo() {
            return ResourceAccess.create(getRootURI());
        }

        public URI getRootURI() {
            return locationURI;
        }

        public String getSymbolicName() {
            return bundle.getSymbolicName();
        }

        @Override
        public String toString() {
            String type = Platform.isFragment(bundle) ? "fragment" : "bundle"; //$NON-NLS-1$ //$NON-NLS-2$
            return type + " '" + getSymbolicName() + "' locationURI:" + getRootURI(); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    public Collection<String> getAllBundleNames() {
        Set<String> result = Sets.newLinkedHashSet();
        for (Bundle bundle : org.eclipse.poosl.rotalumisclient.Activator.getDefault().getBundle()
                .getBundleContext().getBundles())
            result.add(bundle.getSymbolicName());
        return result;
    }

    public IBundleInfo getBundle(Class<?> clazz) {
        Bundle bundle = FrameworkUtil.getBundle(clazz);
        if (bundle == null)
            return null;
        URI locationURI = getBundleLocationURI(bundle);
        Bundle[] fragments = Platform.getFragments(bundle);
        if (fragments == null || fragments.length == 0)
            return new UIBundleInfo(locationURI, bundle);
        String classURI = getClassURI(clazz).toString();
        if (classURI.startsWith(locationURI.toString()))
            return new UIBundleInfo(locationURI, bundle);
        for (Bundle fragment : fragments) {
            URI fragmentURI = getBundleLocationURI(fragment);
            if (classURI.startsWith(fragmentURI.toString()))
                return new UIBundleInfo(fragmentURI, fragment);
        }
        return null;
    }

    public IBundleInfo getBundle(String symbolicName) {
        Bundle bundle = Platform.getBundle(symbolicName);
        return bundle != null ? new UIBundleInfo(getBundleLocationURI(bundle), bundle) : null;
    }

    public IBundleInfo getBundle(URI uri) {
        return new IBundleInfo.BundleInfo(null, uri);
    }

    private URI getBundleLocationURI(Bundle bundle) {
        try {
            URI uri = URI.createURI(FileLocator.resolve(bundle.getEntry("/")).toString()); //$NON-NLS-1$
            if (uri.isArchive())
                return uri;
            File current = new File(uri.toFileString());
            if (new File(current, "META-INF").isDirectory()) { //$NON-NLS-1$
                return URI.createFileURI(current.toString()).appendSegment(""); //$NON-NLS-1$
            } else {
                return uri;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getClassURI(Class<?> clazz) {
        try {
            URL resource = clazz.getClassLoader()
                    .getResource("/" + clazz.getName().replace('.', '/') + ".class"); //$NON-NLS-1$ //$NON-NLS-2$
            URL url = FileLocator.resolve(resource);
            return URI.createURI(url.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
