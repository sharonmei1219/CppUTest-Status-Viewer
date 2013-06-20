package unitTest;

import org.jmock.*;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import static org.junit.Assert.*;

import org.junit.Test;
import org.sharon.cpputest.TestCaseResultParser;

public class TestTestCaseResult {
	Mockery context = new Mockery();

	@Test
	public void testUnitTestCasePassed() {
		String line = "TEST(TestSolidBorderedRectangle, DrawSolidBorderedRectangle) - 0 ms)";
		
		final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);
		TestCaseResultParser testResult = new TestCaseResultParser(line);
		
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase("DrawSolidBorderedRectangle");
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).exitTestCase();
			}
		});
		
		testResult.putTo(updater);
		context.assertIsSatisfied();
	}
}
