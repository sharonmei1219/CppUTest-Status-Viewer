package org.sharon.cpputest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.cdt.testsrunner.launcher.ITestsRunnerProvider;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.eclipse.cdt.testsrunner.model.TestingException;

public class TestProvider implements ITestsRunnerProvider {

	@Override
	public String[] getAdditionalLaunchParameters(String[][] testPaths)
			throws TestingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(ITestModelUpdater modelUpdater, InputStream inputStream)
			throws TestingException {
		// TODO Auto-generated method stub
		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(streamReader);
        String line;
        try {
			while ( ( line = reader.readLine() ) != null ) {
				if(line.startsWith("Error")){
					modelUpdater.enterTestCase("ErrorTestCase");
					modelUpdater.setTestStatus(ITestItem.Status.Failed);
					modelUpdater.exitTestCase();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
