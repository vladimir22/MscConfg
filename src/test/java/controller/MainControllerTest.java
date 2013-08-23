package controller;

import com.mscconfig.mvc.controllers.MainController;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: Vladimir Pronkin
 * Date: 16.08.13
 * Time: 11:06
 * Unit Tests for MainController
 */
public class MainControllerTest {

    private MainController controller;
    @Before
    public void setUp() {
        controller = new MainController();
    }
    @Test
    public void show404Page() {
        String view = controller.show404Page();
        assertEquals(MainController.VIEW_NOT_FOUND, view);
    }
    @Test
    public void showInternalServerErrorPage() {
        String view = controller.show403Page();
        assertEquals(MainController.VIEW_ACCESS_DENIED, view);
    }
}
