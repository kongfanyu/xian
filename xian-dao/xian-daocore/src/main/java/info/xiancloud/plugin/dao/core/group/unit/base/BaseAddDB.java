package info.xiancloud.plugin.dao.core.group.unit.base;

import info.xiancloud.plugin.UnitMeta;
import info.xiancloud.plugin.dao.core.global.Table;
import info.xiancloud.plugin.dao.core.global.TableHeader;
import info.xiancloud.plugin.dao.core.group.unit.DaoUnit;
import info.xiancloud.plugin.dao.core.jdbc.sql.Action;
import info.xiancloud.plugin.dao.core.jdbc.sql.InsertAction;

import java.util.Map;

/**
 * 公共新增 dao unit
 *
 * @author hang
 */
abstract public class BaseAddDB extends DaoUnit {
    @Override
    public String getName() {
        return "BaseAddDB";
    }

    @Override
    public Action[] getActions() {
        return new Action[]{
                new InsertAction() {
                    private Table table;

                    public Table getTable(Map map) {
                        if (table != null) {
                            return table;
                        }
                        Object tableName = map.get("$tableName");
                        if (tableName instanceof String) {
                            table = TableHeader.getTable(map.get("$tableName").toString());
                            if (table.getType().equals(Table.Type.view)) {
                                throw new RuntimeException(String.format("视图:%s,不允许操作 BaseAddDB", table.getName()));
                            }
                            return table;
                        }
                        return null;
                    }

                    @Override
                    public String table() {
                        Table table = getTable(map);
                        return table == null ? "" : table.getName();
                    }

                    @Override
                    public String[] unique() {
                        Table table = getTable(map);
                        return table == null ? new String[]{} : table.getUnique();
                    }

                    ;
                }
        };
    }

    @Override
    public UnitMeta getMeta() {
        return UnitMeta.create("公共新增dao unit").setPublic(false);
    }

}