package com.flaginfo.lightning.devtools;

import java.util.List;

public class OracleTableEntity {

    private String tableName;
    private String entityName;
    private List<OracleColumnEntity> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.entityName = this.formatClassName(tableName);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<OracleColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<OracleColumnEntity> columns) {
        this.columns = columns;
    }

    private String formatClassName(String tableName) {
        String[] tmpList = tableName.toLowerCase().split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tmpList.length; i++) {
            sb.append(tmpList[i].substring(0, 1).toUpperCase());
            sb.append(tmpList[i].substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
