package org.dmonix.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dmonix.AbstractTestCase;
import org.dmonix.cipher.SerializableTestBean;
import org.junit.After;
import org.junit.Before;

/**
 * Test the class <code>ObjectInputStreamMonitor</code>
 * 
 * @author Peter Nerg
 */
public class TestObjectInputStreamMonitor extends AbstractTestCase {

    private ObjectInputStreamMonitor monitor = null;
    private InputStream istream = null;

    private File file = new File("TestObjectInputStreamMonitor.f");

    private List<SerializableTestBean> data = new ArrayList<SerializableTestBean>();

    @Before
    public void setUp() throws Exception {
        data.add(new SerializableTestBean("Peter", System.nanoTime()));
        data.add(new SerializableTestBean("69696", System.nanoTime()));
        data.add(new SerializableTestBean("Nerg", System.nanoTime()));

        ObjectOutputStream ostream = new ObjectOutputStream(new FileOutputStream(file));
        for (SerializableTestBean bean : data) {
            ostream.writeObject(bean);
        }
        ostream.flush();
        ostream.close();

        this.istream = new FileInputStream(file);
        this.monitor = new ObjectInputStreamMonitor(istream, 1000);
    }

    @After
    public void tearDown() throws Exception {
        this.istream.close();
        this.monitor.dispose();
        IOUtil.deleteFile(file);
    }

    public void test() {
        ObjectInputStreamListenerImpl listener1 = new ObjectInputStreamListenerImpl();
        ObjectInputStreamListenerImpl listener2 = new ObjectInputStreamListenerImpl();
        this.monitor.addListener(listener1);
        this.monitor.addListener(listener2);
        this.monitor.start();
        super.sleep(500);
        super.assertCollections(this.data, listener1.objects);
        super.assertCollections(this.data, listener2.objects);
    }

    public void test2() {
        ObjectInputStreamListenerImpl listener1 = new ObjectInputStreamListenerImpl();
        ObjectInputStreamListenerImpl listener2 = new ObjectInputStreamListenerImpl();
        this.monitor.addListener(listener1);
        this.monitor.addListener(listener2);
        this.monitor.removeListener(listener1);
        this.monitor.start();
        super.sleep(500);
        super.assertCollections(this.data, listener2.objects);
        super.assertCollectionSize(listener1.objects, 0);
    }

    private class ObjectInputStreamListenerImpl implements ObjectInputStreamListener {

        private List<SerializableTestBean> objects = new ArrayList<SerializableTestBean>();

        public void inputStreamAction(Object o) {
            objects.add((SerializableTestBean) o);
        }
    }
}
