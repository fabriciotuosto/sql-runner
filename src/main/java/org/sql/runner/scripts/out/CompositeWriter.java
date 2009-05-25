package org.sql.runner.scripts.out;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompositeWriter extends Writer {

	private CopyOnWriteArrayList<OutputStreamWriter> streams;
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public static OutputStreamWriter decorateOutputSream(OutputStream stream) {
		if (!stream.getClass().isAssignableFrom(BufferedOutputStream.class)) {
			stream = new BufferedOutputStream(stream);
		}
		return new OutputStreamWriter(stream);
	}

	public CompositeWriter(OutputStreamWriter... outputs) {
		streams = new CopyOnWriteArrayList<OutputStreamWriter>();
		addWriters(outputs);
	}

	public void addWriters(OutputStreamWriter... outputs) {
		streams.addAll(Arrays.asList(outputs));
	}

	@Override
	public void close() throws IOException {
		executor.execute(new Runnable() {
			public void run() {
				for (OutputStreamWriter stream : streams) {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});
		try {
			executor.shutdown();
			executor.awaitTermination(100,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void flush() throws IOException {
		executor.execute(new Runnable() {
			public void run() {
				for (OutputStreamWriter stream : streams) {
					try {
						stream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

	}

	@Override
	public void write(final char[] cbuf, final int off, final int len)
			throws IOException {
		executor.execute(new Runnable() {
			public void run() {
				for (OutputStreamWriter stream : streams) {
					try {
						stream.write(cbuf, off, len);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

	}

}
