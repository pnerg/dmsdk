package org.dmonix.util;

import org.dmonix.AbstractTestCase;
import org.dmonix.cipher.SerializableTestBean;

public class TestSwingWorker extends AbstractTestCase {

    public void testSwingWorkerWithConstruct() throws Exception {

        final String name = "testName";
        final long time = System.currentTimeMillis();

        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                return new SerializableTestBean(name, time);
            }

        };
        assertNull(worker.getValue());
        worker.start();
        Thread.sleep(500);
        assertEquals(new SerializableTestBean(name, time), worker.getValue());
    }

    public void testSwingWorkerWithFinished() throws Exception {

        final String name = "testName";
        final long time = System.currentTimeMillis();

        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                assertEquals(true, true);
                return new SerializableTestBean(name, time);
            }

            public void finished() {
            }

        };
        assertNull(worker.getValue());
        worker.start();
        Thread.sleep(500);
        assertEquals(new SerializableTestBean(name, time), worker.getValue());
    }
}
