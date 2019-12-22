package work;

public enum EnumStudy implements OP{
    NIHAO() {
        @Override
        public String op() {
            return "你好";
        }
    },
    TAHAO() {
        @Override
        public String op() {
            return "我好";
        }
    },
    WOHAO() {
        @Override
        public String op() {
            return "他好";
        }
    };




}
