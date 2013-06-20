package unitTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.sharon.cpputest.StatusReporter;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
public class TestParseStream {
	
	Mockery context = new Mockery();

	@Test
	public void testParseStream() {
		final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);
		byte[] cppUTestOutputByte = "TEST(testSuit, \"\", ...)\nTEST(testSuit, \"\")...".getBytes();
		InputStream outputStream = new ByteArrayInputStream(cppUTestOutputByte);
		StatusReporter testStatusUpdater = new StatusReporter(outputStream, updater);
		context.checking(new Expectations(){
			{
				oneOf(updater).enterTestCase(with(any(String.class)));
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).exitTestCase();
				oneOf(updater).enterTestCase(with(any(String.class)));
				oneOf(updater).setTestStatus(ITestItem.Status.Passed);
				oneOf(updater).exitTestCase();
			}
		});
		testStatusUpdater.updateStatus();
		context.assertIsSatisfied();
	}
}
