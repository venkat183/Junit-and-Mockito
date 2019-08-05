package com.venkat.order.bo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import com.venkat.order.bo.exception.BOException;
import com.venkat.order.dao.OrderDao;
import com.venkat.order.dto.Order;

public class OrderBOImplTest {
	@Mock
	OrderDao dao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void placeOrder_Should_Create_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.create(any(Order.class))).thenReturn(new Integer(1));
		boolean result = bo.placeOrder(order);
		assertTrue(result);
		verify(dao,atLeast(1)).create(order);
	}

	@Test
	public void placeOrder_Shouldnot_Create_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(0));
		boolean result = bo.placeOrder(order);
		assertFalse(result);
		verify(dao).create(order);
	}

	@Test(expected=BOException.class)
	public void placeOrder_Should_Throw_BOException() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		bo.placeOrder(order);
	}


	@Test
	public void cancelOrder_Should_Cancel_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.read(123)).thenReturn(order);
		when(dao.update(order)).thenReturn(1);
		boolean result = bo.cancelOrder(123);
		assertTrue(result);
		verify(dao).read(123);
		verify(dao).update(order);
	}


	@Test
	public void cancelOrder_Should_Not_Cancel_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);
		Order order = new Order();
		when(dao.read(123)).thenReturn(order);
		when(dao.update(order)).thenReturn(0);
		boolean result = bo.cancelOrder(123);
		assertFalse(result);
		verify(dao).read(123);
		verify(dao).update(order);
	}
	
	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_BOException() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);		
		when(dao.read(123)).thenThrow(SQLException.class);
	    bo.cancelOrder(123);
		
	}
	
	@Test
	public void deleteOrder_Should_Delete_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);	
		when(dao.delete(123)).thenReturn(1);
		assertTrue(bo.deleteOrder(123));
		verify(dao).delete(123);
	}
	
	@Test
	public void deleteOrder_Should_Not_Delete_An_Order() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);	
		when(dao.delete(123)).thenReturn(0);
		assertFalse(bo.deleteOrder(123));
		verify(dao).delete(123);
	}
	
	@Test(expected = BOException.class)
	public void deleteOrder_Should_Throw_BO_exception() throws SQLException, BOException {
		OrderBOImpl bo = new OrderBOImpl();
		bo.setDao(dao);	
		when(dao.delete(anyInt())).thenThrow(SQLException.class);
		bo.deleteOrder(123);
		verify(dao).delete(123);
	
	}

}
