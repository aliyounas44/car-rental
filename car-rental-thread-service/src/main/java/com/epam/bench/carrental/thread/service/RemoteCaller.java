package com.epam.bench.carrental.thread.service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

public class RemoteCaller {
	/*public <T> void execute2(Callable<T> task, Consumer<T> successHandler, Consumer<Throwable> failureHandler) {
		Runnable myRunnable = () -> {
			try {
				T result = task.call();
				SwingUtilities.invokeLater(() -> successHandler.accept(result));
			} catch (Exception e) {
				SwingUtilities.invokeLater(() -> failureHandler.accept(e));
			}
		};
		new Thread(myRunnable).start();
	}*/

	public <T> void execute(Callable<T> task, Consumer<T> successHandler, Consumer<Throwable> failureHandler) {	
		CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync( () -> {
			try {
				return task.call();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		showMessage(completableFuture, successHandler, failureHandler);
	}

	private <T> void showMessage(CompletableFuture<T> completableFuture, Consumer<T> successHandler, Consumer<Throwable> failureHandler) {
		SwingUtilities.invokeLater(() -> {
			completableFuture.handle( (it, error) -> {
				if( it != null ) {
					successHandler.accept(it);
					return it;
				}
				else {
					failureHandler.accept(error);
					return error;
				}
			});
		});
	}
}
