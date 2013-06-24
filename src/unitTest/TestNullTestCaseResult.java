package unitTest;

import static org.junit.Assert.*;

import org.eclipse.cdt.testsrunner.model.ITestItem;
import org.eclipse.cdt.testsrunner.model.ITestModelUpdater;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.sharon.cpputest.NullTestCaseResult;
import org.sharon.cpputest.TestCaseResult;

public class TestNullTestCaseResult {
	Mockery context = new Mockery();
	final ITestModelUpdater updater = context.mock(ITestModelUpdater.class);
	TestCaseResult nullTcResult = new NullTestCaseResult();

	@Test
	public void testNullObjectPutToDashboard() {
		nullTcResult.putTo(updater);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testNoNeedMoreInfo(){
		assertFalse(nullTcResult.needMoreInfo());
	}

}
