package unitTest;

import static org.junit.Assert.*;

import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.sharon.cpputest.TestCaseResult;
import org.sharon.cpputest.TestSuiteGrouper;

public class TestTestSuiteGrouper {

	Mockery context = new Mockery();
	final ITestModelUpdater dashBoard = context.mock(ITestModelUpdater.class);
	final TestCaseResult tcResult = context.mock(TestCaseResult.class);
	
	@Test
	public void testStartNewTestSuite() {
		TestSuiteGrouper testSuiteGrouper = new TestSuiteGrouper(dashBoard);
		
		context.checking(new Expectations(){
			{
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite1"));
				oneOf(dashBoard).enterTestSuite("TestSuite1");
			}
			});
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		context.assertIsSatisfied();
	}
	
	@Test
	public void test2TestCaseInSameTestSuite() {
		TestSuiteGrouper testSuiteGrouper = new TestSuiteGrouper(dashBoard);
		
		context.checking(new Expectations(){
			{
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite1"));
				oneOf(dashBoard).enterTestSuite("TestSuite1");
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite1"));
			}
			});
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		context.assertIsSatisfied();
	}
	
	@Test
	public void test2TestCaseInDifferentTestSuite() {
		TestSuiteGrouper testSuiteGrouper = new TestSuiteGrouper(dashBoard);
		
		context.checking(new Expectations(){
			{
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite1"));
				oneOf(dashBoard).enterTestSuite("TestSuite1");
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite2"));
				oneOf(dashBoard).exitTestSuite();
				oneOf(dashBoard).enterTestSuite("TestSuite2");
			}
			});
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testCloseTestSuiteGrouper() {
		TestSuiteGrouper testSuiteGrouper = new TestSuiteGrouper(dashBoard);
		
		context.checking(new Expectations(){
			{
				oneOf(tcResult).testSuite();
				will(returnValue("TestSuite1"));
				oneOf(dashBoard).enterTestSuite("TestSuite1");
				oneOf(dashBoard).exitTestSuite();
			}
			});
		testSuiteGrouper.groupTcInTestSuite(tcResult);
		testSuiteGrouper.close();
		context.assertIsSatisfied();
	}
	
	@Test
	public void testCloseWhenNoTestSuiteHadEverEntered() {
		TestSuiteGrouper testSuiteGrouper = new TestSuiteGrouper(dashBoard);

		testSuiteGrouper.close();
		context.assertIsSatisfied();
	}

}
