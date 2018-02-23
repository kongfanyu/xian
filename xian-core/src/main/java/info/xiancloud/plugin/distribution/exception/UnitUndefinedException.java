package info.xiancloud.plugin.distribution.exception;

import info.xiancloud.plugin.Group;
import info.xiancloud.plugin.message.UnitResponse;
import info.xiancloud.plugin.util.StringUtil;

/**
 * unit is not defined.
 *
 * @author happyyangyuan
 */
public class UnitUndefinedException extends AbstractXianException {
    private String groupName;
    private String unitName;

    public UnitUndefinedException(String groupName, String unitName) {
        this.groupName = groupName;
        this.unitName = unitName;
    }

    public UnitUndefinedException(String fullName) {
        this.groupName = fullName.split(StringUtil.escapeSpecialChar("."))[0];
        this.unitName = fullName.split(StringUtil.escapeSpecialChar("."))[1];
    }

    @Override
    public String getMessage() {
        return String.format("Unit is undefined: %s.%s", groupName, unitName);
    }

    public UnitResponse toUnitResponse() {
        return UnitResponse.error(Group.CODE_UNIT_UNDEFINED, null, getLocalizedMessage());
    }

    @Override
    public String getCode() {
        return Group.CODE_UNIT_UNDEFINED;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
