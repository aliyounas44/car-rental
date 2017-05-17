package com.epam.bench.carrental.car_rental_threadpool_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;

import org.junit.Before;
import org.junit.Test;

import com.epam.bench.carrental.thread.service.RemoteCaller;

public class TestApp {
	
	String onSuccess = null;
	Throwable throwable = null;
	CountDownLatch countDownLatch = new CountDownLatch(1);
	boolean isTaskOnEdt = true;
	boolean areHandlersOnEdt = false;
	
	@Before
	public void reset() {
		onSuccess = null;
		throwable = null;
		countDownLatch = new CountDownLatch(1);
		isTaskOnEdt = true;
		areHandlersOnEdt = false;
	}
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		RemoteCaller remoteCaller = new RemoteCaller();
		remoteCaller.execute(this::dummyTask, this::onSuccess, this::onFailure);
		countDownLatch.await();
		assertEquals("asdef", onSuccess, "dummy object from the server");
		assertTrue(areHandlersOnEdt);
		assertTrue(!isTaskOnEdt);
	}
	
/*	@Test
	public void test2() throws InterruptedException, ExecutionException {
		RemoteCaller remoteCaller = new RemoteCaller();
		remoteCaller.execute2(this::dummyTask, this::onSuccess, this::onFailure);
		countDownLatch.await();
		assertEquals("asdef", onSuccess, "dummy object from the server");
		assertTrue(areHandlersOnEdt);
		assertTrue(!isTaskOnEdt);
	}
	
	@Test
	public void failTest2() throws InterruptedException, ExecutionException {
		RemoteCaller remoteCaller = new RemoteCaller();
		remoteCaller.execute2(this::dummyTaskFailure, this::onSuccess, this::onFailure);
		countDownLatch.await();
		assertNotNull(throwable);
		assertTrue(areHandlersOnEdt);
		assertTrue(!isTaskOnEdt);
	}*/

	String dummyTask() {
		isTaskOnEdt = SwingUtilities.isEventDispatchThread();
		return "dummy object from the server";
	}
	
	String dummyTaskFailure() {
		isTaskOnEdt = SwingUtilities.isEventDispatchThread();
		throw new RuntimeException();
	}
	
	void onSuccess(String s) {
		areHandlersOnEdt = SwingUtilities.isEventDispatchThread();
		countDownLatch.countDown();
		onSuccess = s;
	}
	
	void onFailure(Throwable s) {
		areHandlersOnEdt = SwingUtilities.isEventDispatchThread();
		countDownLatch.countDown();
		throwable = s;
	}
}