package com.mscconfig.mvc.controllers;

import com.mscconfig.entities.MgwData;
import com.mscconfig.mvc.components.MessageSourceBean;
import com.mscconfig.repository.MgwDataRepository;
import controller.config.UnitTestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.annotation.Resource;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Vladimir Pronkin
 * Date: 22.08.13
 * Time: 15:27
 * Unit Tests for DataDbController
 * Notes:main/java/"package"/DataDbController.class and test/java/"package"/DataDbControllerTest.class  has same "package" path
 * 			 therefore : this  class can access to protected fields of tested class !!!
 */
@RunWith(SpringJUnit4ClassRunner.class)   // don't forget <artifactId>hibernate-validator</artifactId> maven dependency for this
@ContextConfiguration(classes = {UnitTestContext.class})
public class DataDbControllerTest {

	private DataDbController dataDbController;
	private MessageSourceBean messageSourceBeanMock;
	private MgwDataRepository mgwRepositoryMock;

	@Resource
	private Validator validator;

	@Before
	public void setUp() {
		dataDbController = new DataDbController();

		messageSourceBeanMock = mock( MessageSourceBean.class);
		ReflectionTestUtils.setField(dataDbController, "messageSourceBean",  messageSourceBeanMock);

		mgwRepositoryMock = mock(MgwDataRepository.class);
		ReflectionTestUtils.setField(dataDbController, "mgwRepository", mgwRepositoryMock);
	}
	@Test
	public void getDataPageTest() {
		BindingAwareModelMap model = new BindingAwareModelMap();
        String view = dataDbController.getDataPage(model);
		assertEquals(dataDbController.VIEW_DATA_PAGE, view);

		verifyZeroInteractions(messageSourceBeanMock);

		verify(mgwRepositoryMock).findAll();
		verifyNoMoreInteractions(mgwRepositoryMock);
		                                                                // not appear tooltip because protected filed!!!
		MgwData formObject = (MgwData) model.asMap().get(dataDbController.MODEL_ATTR_MGWDATA);
       	assertNull(formObject.getIp());
		assertNull(formObject.getMgw_id());
		assertNull(formObject.getMgw_id());
	}
}
