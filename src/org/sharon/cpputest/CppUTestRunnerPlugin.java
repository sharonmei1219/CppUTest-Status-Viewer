package org.sharon.cpputest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CppUTestRunnerPlugin implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		CppUTestRunnerPlugin.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		CppUTestRunnerPlugin.context = null;
	}

}
