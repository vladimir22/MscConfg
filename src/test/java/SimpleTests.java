import com.mscconfig.commands.CmdFactory;
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
		CmdFactory cmdFactory = new CmdFactory();
		NsnCmd nsnCmd = cmdFactory.createDispVsubCmd("111");
		 assertTrue("cmd might contains MSISDN=111; ", nsnCmd.getCompletedCmd().contains("MSISDN=111;"));



	}

}
