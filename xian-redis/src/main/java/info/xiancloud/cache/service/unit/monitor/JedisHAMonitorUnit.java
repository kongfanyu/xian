package info.xiancloud.cache.service.unit.monitor;

import info.xiancloud.cache.redis.RedisMonitor;
import info.xiancloud.plugin.Input;
import info.xiancloud.plugin.message.UnitResponse;
import info.xiancloud.plugin.UnitMeta;
import info.xiancloud.plugin.support.falcon.AbstractDiyMonitorUnit;
import info.xiancloud.plugin.util.EnvUtil;

public class JedisHAMonitorUnit extends AbstractDiyMonitorUnit {
    @Override
    public String getName() {
        return "jedisHAMonitor";
    }

    @Override
    public UnitMeta getMeta() {
        return UnitMeta.create().setDescription("Redis HA 监控")
                .setBroadcast(UnitMeta.Broadcast.create().setAsync(false).setSuccessDataOnly(true))
                .setPublic(false)
                ;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public String dashboard() {
        return EnvUtil.getShortEnvName() + "-redis";
    }

    @Override
    public String title() {
        return "Redis HA 监控";
    }

    @Override
    public Object execute0() {
        return UnitResponse.success(RedisMonitor.monitorForHA());
    }

}
