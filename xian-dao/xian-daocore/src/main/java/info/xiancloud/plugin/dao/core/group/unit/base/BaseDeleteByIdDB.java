package info.xiancloud.plugin.dao.core.group.unit.base;

import info.xiancloud.plugin.UnitMeta;
import info.xiancloud.plugin.dao.core.global.Table;
import info.xiancloud.plugin.dao.core.global.TableHeader;
import info.xiancloud.plugin.dao.core.group.unit.DaoUnit;
import info.xiancloud.plugin.dao.core.jdbc.sql.Action;
import info.xiancloud.plugin.dao.core.jdbc.sql.DeleteAction;
import info.xiancloud.plugin.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共删除ById dao unit
 */
abstract public class BaseDeleteByIdDB extends DaoUnit {
    @Override
    public String getName() {
        return "BaseDeleteByIdDB";
    }

    @Override
    public Action[] getActions() {
        return new Action[]{new DeleteAction() {
            @Override
            public String table() {
                Object tableName = map.get("$tableName");
                if (tableName instanceof String) {
                    Table table = TableHeader.getTable(map.get("$tableName").toString());
                    if (table.getType().equals(Table.Type.view)) {
                        throw new RuntimeException(String.format("视图:%s,不允许操作 BaseDeleteByIdDB", table.getName()));
                    }
                    return table.getName();
                }
                return "";
            }

            @Override
            protected String[] where() {
                Object tableName = map.get("$tableName");
                if (tableName instanceof String) {
                    Table table = TableHeader.getTable(map.get("$tableName").toString());
                    if (table != null) {
                        String[] pkKeys = table.getPrimaryKey();
                        if (pkKeys != null) {
                            List<String> pkList = new ArrayList<String>();
                            for (String pk : pkKeys) {
                                pkList.add(String.format("%s = {%s}", pk, StringUtil.underlineToCamel(pk)));
                            }
                            return pkList.toArray(new String[]{});
                        }
                    }
                }
                return new String[]{"1 < 0"};
            }
        }};
    }


    @Override
    public UnitMeta getMeta() {
        return UnitMeta.create("公共删除ById unit dao");
    }

}