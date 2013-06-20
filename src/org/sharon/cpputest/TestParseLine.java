package org.sharon.cpputest;

import org.jmock.*;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestParseLine {
	Mockery context = new Mockery();

	@Test
	public void testUnitTestStart() {
		String line = "TEST(TestSolidBorderedRectangle, DrawSolidBorderedRectangle) - 0 ms)";
		
		final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);
		TestCaseResultParser parser = new TestCaseResultParser(line);
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("DrawSolidBorderedRectangle");
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).exitTestCase();
			}
		});
		
		parser.putTo(updater);
		context.assertIsSatisfied();
	}
}
