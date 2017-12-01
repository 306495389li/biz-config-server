package com.flaginfo.lightning.common.vo;

import java.util.List;
import java.util.ArrayList;
import com.flaginfo.lightning.common.Pagination;

public class Data<T> {
    private int total;
    private List<T> rows = new ArrayList<T>();

    public Data(Pagination<T> pagination) {
        this.rows = pagination.getDatas();
        this.total = pagination.getTotalCount();
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
