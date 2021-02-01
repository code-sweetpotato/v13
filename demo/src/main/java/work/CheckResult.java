package work;

public class CheckResult {
    /**校验失败理由*/
    private String reason;

    /**校验结果    T：通过   F：失败*/
    private String flag;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public CheckResult(String reason, String flag) {
        this.reason = reason;
        this.flag = flag;
    }

    public CheckResult() {
    }



}
