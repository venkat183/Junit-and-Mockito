package com.venkat.order.bo;

import com.venkat.order.bo.exception.BOException;
import com.venkat.order.dto.Order;

public interface OrderBO {
	
	boolean placeOrder(Order order) throws BOException;
	boolean cancelOrder(int id) throws BOException;
	boolean deleteOrder(int id) throws BOException;

}
