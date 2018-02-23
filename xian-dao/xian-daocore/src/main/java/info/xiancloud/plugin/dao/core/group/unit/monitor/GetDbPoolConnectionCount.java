package info.xiancloud.plugin.dao.core.group.unit.monitor;

import com.alibaba.fastjson.JSONArray;
import info.xiancloud.plugin.dao.core.jdbc.pool.PoolFactory;
import info.xiancloud.plugin.Group;
import info.xiancloud.plugin.message.UnitResponse;
import info.xiancloud.plugin.UnitMeta;
import info.xiancloud.plugin.distribution.LocalNodeManager;
import info.xiancloud.plugin.distribution.unit.ReceiveAndBroadcast;
import info.xiancloud.plugin.message.UnitRequest;
import info.xiancloud.plugin.support.falcon.DiyMonitorGroup;

/**
 * @author happyyangyuan
 */
public class GetDbPoolConnectionCount extends ReceiveAndBroadcast {
    @Override
    public String getName() {
        return "getDbPoolConnectionCount";
    }

    @Override
    public UnitMeta getMeta() {
        return UnitMeta.create("Collect all db nodes' connection pool status.")
                .setPublic(false);
    }

    @Override
    protected UnitResponse execute0(UnitRequest msg) {
        int masterPoolActiveCount = PoolFactory.getPool().getMasterDatasource().getActiveConnectionCount(),
                masterPoolSize = PoolFactory.getPool().getMasterDatasource().getPoolSize(),
                slavePoolActiveCount = PoolFactory.getPool().getSlaveDatasource().getActiveConnectionCount(),
                slavePoolSize = PoolFactory.getPool().getSlaveDatasource().getPoolSize();
        JSONArray monitorBeans = new JSONArray() {{
            add(new DbPoolInfoMonitorBean()
                    .setTitle("DbConnectionPool")
                    .setDatasource(PoolFactory.getPool().getMasterDatasource().getDatabase())
                    .setName("masterPoolActiveCount")
                    .setValue(masterPoolActiveCount)
                    .setNodeId(LocalNodeManager.LOCAL_NODE_ID)
            );
//            add(new DbPoolInfoMonitorBean()
//                    .setTitle("DbConnectionPool")
//                    .setDatasource(PoolFactory.getPool().getMasterDatasource().getDatabase())
//                    .setName("masterPoolSize")
//                    .setValue(masterPoolSize)
//                    .setNodeId(LocalNodeManager.LOCAL_NODE_ID)
//            );
            add(new DbPoolInfoMonitorBean()
                    .setTitle("DbConnectionPool")
                    .setDatasource(PoolFactory.getPool().getMasterDatasource().getDatabase())
                    .setName("slavePoolActiveCount")
                    .setValue(slavePoolActiveCount)
                    .setNodeId(LocalNodeManager.LOCAL_NODE_ID)
            );
//            add(new DbPoolInfoMonitorBean()
//                    .setTitle("DbConnectionPool")
//                    .setDatasource(PoolFactory.getPool().getMasterDatasource().getDatabase())
//                    .setName("slavePoolSize")
//                    .setValue(slavePoolSize)
//                    .setNodeId(LocalNodeManager.LOCAL_NODE_ID)
//            );
        }};
        return UnitResponse.success(monitorBeans);
    }

    @Override
    protected boolean async() {
        return false;
    }

    @Override
    protected boolean successDataOnly() {
        return true;
    }

    @Override
    public Group getGroup() {
        return DiyMonitorGroup.singleton;
    }
}
