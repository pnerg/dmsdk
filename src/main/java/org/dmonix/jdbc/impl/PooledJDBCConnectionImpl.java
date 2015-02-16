package org.dmonix.jdbc.impl;

import java.sql.SQLException;

public class PooledJDBCConnectionImpl extends JDBCConnectionImpl {

    private JDBCConnectionPoolImpl pool;
    private boolean inUse = false;
    private long lastUsedTime = -1;

    public PooledJDBCConnectionImpl(JDBCConnectionPoolImpl pool, JDBCConnectionImpl con) {
        super(con.getConnection());
        this.pool = pool;
    }

    public void close2() throws SQLException {
        super.close();
    }

    public void close() throws SQLException {
        this.pool.returnConnection(this);
    }

    public boolean inUse() {
        return this.inUse;
    }

    public long getLastUse() {
        return this.lastUsedTime;
    }

    public boolean validate() {
        // TODO Auto-generated method stub
        return false;
    }

    public void expireLease() {
        this.inUse = false;
        this.lastUsedTime = -1;
    }

    public void lease() {
        this.inUse = true;
        this.lastUsedTime = System.currentTimeMillis();
    }

}
