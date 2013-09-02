import com.mscconfig.commands.factories.NsnCmdFactory;
import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.exceptions.NsnCmdException;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * User: Vladimir
 * Date: 16.08.13
 * Time: 11:06
 * Basic simple tests
 */
public class SimpleTests {
	 @Test
	public void testCmdFactory() throws NsnCmdException {
		NsnCmdFactory cmdFactory = new NsnCmdFactory();
		NsnCmd nsnCmd = cmdFactory.getNsnCmdBean("vsubcmd");
		 assertTrue("NsnCmd:'vsubcmd' must contains: 'MSISDN='", nsnCmd.getCompletedCmd().contains("MSISDN="));
	}

}
