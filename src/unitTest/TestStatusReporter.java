package unitTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.sharon.cpputest.Reporter;
import org.sharon.cpputest.TestCaseFactory;
import org.sharon.cpputest.TestCaseResult;
import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
public class TestStatusReporter {
	
	Mockery context = new Mockery();
	final ITestModelUpdater testDashBoard = context.mock(ITestModelUpdater.class);
	final TestCaseFactory factory = context.mock(TestCaseFactory.class);

	@Test
	public void testSuccessParseStream() {
		InputStream testResultStream = buildTestResultStream("TEST(testSuite, testCase1),... \nTEST(testSuite, testCase2)...");
		Reporter testStatusUpdater = new Reporter(testDashBoard, testResultStream, factory);

		final TestCaseResult testCase_1 = context.mock(TestCaseResult.class, "firstCase");
		final TestCaseResult testCase_2 = context.mock(TestCaseResult.class, "secondCase");

		context.checking(new Expectations(){
			{
				oneOf(factory).createTestCase("TEST(testSuite, testCase1),... "); 
				will(returnValue(testCase_1));
				oneOf(testCase_1).needMoreInfo();
				will(returnValue(false));
				oneOf(testCase_1).putTo(testDashBoard);
				
				oneOf(factory).createTestCase("TEST(testSuite, testCase2)..."); 
				will(returnValue(testCase_2));
				oneOf(testCase_2).needMoreInfo();
				will(returnValue(false));
				oneOf(testCase_2).putTo(testDashBoard);
			}
		});
		
		testStatusUpdater.reportTestResult();
		context.assertIsSatisfied();
	}
	
	@Test
	public void testInfoMoreThanTwoLines(){
		InputStream testResultStream = buildTestResultStream("line1 \nline2 ");
		Reporter testStatusUpdater = new Reporter(testDashBoard, testResultStream, factory);
		final TestCaseResult testCase = context.mock(TestCaseResult.class, "firstCase");
		
		context.checking(new Expectations(){
			{
				oneOf(factory).createTestCase("line1 "); 
				will(returnValue(testCase));
				oneOf(testCase).needMoreInfo();
				will(returnValue(true));
				oneOf(testCase).addMoreInfo("line2 ");
				oneOf(testCase).needMoreInfo();
				will(returnValue(false));
				oneOf(testCase).putTo(testDashBoard);
				
			}
		});
		
		testStatusUpdater.reportTestResult();
		context.assertIsSatisfied();
		
	}

	private InputStream buildTestResultStream(String testResult) {
		byte[] cppUTestOutputByte = testResult.getBytes();
		InputStream testResultStream = new ByteArrayInputStream(cppUTestOutputByte);
		return testResultStream;
	}
}
