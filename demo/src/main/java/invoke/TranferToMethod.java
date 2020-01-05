package invoke;

public enum TranferToMethod {
    name("nameMap","queryNameList"),
    appointNo("appointNoMap","queryAppointNoList");

    TranferToMethod(String param, String methodName) {
        this.param = param;
        this.methodName = methodName;
    }

    private String param;
    private String methodName;

    public static String getMethodName(String param){
        TranferToMethod[] values = TranferToMethod.values();
        for (TranferToMethod value : values) {
            if(value.param.equals(param)){
                return value.methodName;
            }
        }
        return "";

    }
}
