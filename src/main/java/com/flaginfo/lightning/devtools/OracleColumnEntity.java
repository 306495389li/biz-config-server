package com.flaginfo.lightning.devtools;

public class OracleColumnEntity {
    private String columnName;

    private String filedName;
    private String datatype;
    private String javatype;
    private int charMaxLength;
    private int numericPricision;
    private int numericScale;
    private boolean isPri;
    private String comm;
    private String nullable;
    private String Key;
    private String defaultValue;
    private String extra;
    private String columnKey;

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
        this.javatype = this.dataTypeMap(datatype);
    }

    public String getJavatype() {
        return javatype;
    }

    public void setJavatype(String javatype) {
        this.javatype = javatype;
    }

    public int getCharMaxLength() {
        return charMaxLength;
    }

    public void setCharMaxLength(int charMaxLength) {
        this.charMaxLength = charMaxLength;
    }

    public int getNumericPricision() {
        return numericPricision;
    }

    public void setNumericPricision(int numericPricision) {
        this.numericPricision = numericPricision;
    }

    public int getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(int numericScale) {
        this.numericScale = numericScale;
    }

    public boolean isPri() {
        return isPri;
    }

    public void setPri(boolean isPri) {
        this.isPri = isPri;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.filedName = this.formatFildName(columnName);
    }

    private String formatFildName(String columnName) {
        String tmp = columnName.toLowerCase();
        String[] tmpList = tmp.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tmpList.length; i++) {
            if (i == 0) {
                sb.append(tmpList[i]);
            } else {
                sb.append(tmpList[i].substring(0, 1).toUpperCase());
                sb.append(tmpList[i].substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    private String dataTypeMap(String dataMap) {
        if (dataMap.equalsIgnoreCase("CHAR")) {
            return "String";
        } else if (dataMap.equalsIgnoreCase("VARCHAR2")) {
            return "String";
        } else if (dataMap.equalsIgnoreCase("NCHAR")) {
            return "String";
        } else if (dataMap.equalsIgnoreCase("NVARCHAR2")) {
            return "String";
        } else if (dataMap.equalsIgnoreCase("DATE")) {
            return "Date";
        } else if (dataMap.equalsIgnoreCase("TIMESTAMP")) {
            return "Date";
        } else if (dataMap.equalsIgnoreCase("LONG")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("RAW")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("LONG RAW")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("BLOB")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("CLOB")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("NCLOB")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("BFILE")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("ROWID")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("NROWID")) {
            return "DataTypeWithBLOBs.byte[]";
        } else if (dataMap.equalsIgnoreCase("NUMBER")) {
            return "Long";
        } else if (dataMap.equalsIgnoreCase("DECIMAL")) {
            if(this.getNumericScale() == 0 ){
                return "Long";
            }
            return "BigDecimal";
        } else if (dataMap.equalsIgnoreCase("INTEGER")) {
            return "Long";
        } else if (dataMap.equalsIgnoreCase("FLOAT")) {
            return "Float";
        } else if (dataMap.equalsIgnoreCase("REAL")) {
            return "Double";
        } else {
            return "fobbiden type";
        }
    }
}
