package com.flaginfo.lightning.common;

public class ResultData {
    private Header head;
    private Body body;

    public ResultData(String resultCode, String resultMsg, String status, Object data) {
        head = new Header();
        body = new Body();
        this.head.resultCode = resultCode;
        this.head.resultMsg = resultMsg;
        this.head.status = status;
        this.body.data = data;
    }

    public ResultData(Object data) {
        this("200", "success", "success", data);
    }

    public ResultData(Exception e) {
        this("201", e.toString(), "fail", null);
    }

    public ResultData() {
        this("200", "success", "success", null);
    }

    public Header getHead() {
        return head;
    }

    public void setHead(Header head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public class Header {
        private String status;
        private String resultCode;
        private String resultMsg;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }

    public class Body {
        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
