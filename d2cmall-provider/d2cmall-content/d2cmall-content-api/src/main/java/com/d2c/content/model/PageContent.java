package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -pc wap页面内容
 */
@Table(name = "v_page_content")
public class PageContent extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 页面定义ID
     */
    private Long pageDefId;
    /**
     * 名称
     */
    @javax.persistence.Transient
    private String moduleName;
    /**
     * 状态（1、发布，0、草稿,-1、历史版本）
     */
    private Integer status = 0;
    /**
     * 块定义
     */
    private String block01;
    private String block02;
    private String block03;
    private String block04;
    private String block05;
    private String block06;
    private String block07;
    private String block08;
    private String block09;
    private String block10;
    private String block11;
    private String block12;
    private String block13;
    private String block14;
    private String block15;
    private String block16;
    private String block17;
    private String block18;
    private String block19;
    private String block20;
    private String block21;
    private String block22;
    private String block23;
    private String block24;
    private String block25;
    private String block26;
    private String block27;
    private String block28;
    private String block29;
    private String block30;
    private String block31;
    private String block32;
    private String block33;
    private String block34;
    private String block35;
    private String block36;
    private String block37;
    private String block38;
    private String block39;
    private String block40;
    private String block41;
    private String block42;
    private String block43;
    private String block44;
    private String block45;
    private String block46;
    private String block47;
    private String block48;
    private String block49;
    private String block50;

    public String getBlock01() {
        return block01;
    }

    public void setBlock01(String block01) {
        this.block01 = block01;
    }

    public String getBlock02() {
        return block02;
    }

    public void setBlock02(String block02) {
        this.block02 = block02;
    }

    public String getBlock03() {
        return block03;
    }

    public void setBlock03(String block03) {
        this.block03 = block03;
    }

    public String getBlock04() {
        return block04;
    }

    public void setBlock04(String block04) {
        this.block04 = block04;
    }

    public String getBlock05() {
        return block05;
    }

    public void setBlock05(String block05) {
        this.block05 = block05;
    }

    public String getBlock06() {
        return block06;
    }

    public void setBlock06(String block06) {
        this.block06 = block06;
    }

    public String getBlock07() {
        return block07;
    }

    public void setBlock07(String block07) {
        this.block07 = block07;
    }

    public String getBlock08() {
        return block08;
    }

    public void setBlock08(String block08) {
        this.block08 = block08;
    }

    public String getBlock09() {
        return block09;
    }

    public void setBlock09(String block09) {
        this.block09 = block09;
    }

    public String getBlock10() {
        return block10;
    }

    public void setBlock10(String block10) {
        this.block10 = block10;
    }

    public String getBlock11() {
        return block11;
    }

    public void setBlock11(String block11) {
        this.block11 = block11;
    }

    public String getBlock12() {
        return block12;
    }

    public void setBlock12(String block12) {
        this.block12 = block12;
    }

    public String getBlock13() {
        return block13;
    }

    public void setBlock13(String block13) {
        this.block13 = block13;
    }

    public String getBlock14() {
        return block14;
    }

    public void setBlock14(String block14) {
        this.block14 = block14;
    }

    public String getBlock15() {
        return block15;
    }

    public void setBlock15(String block15) {
        this.block15 = block15;
    }

    public String getBlock16() {
        return block16;
    }

    public void setBlock16(String block16) {
        this.block16 = block16;
    }

    public String getBlock17() {
        return block17;
    }

    public void setBlock17(String block17) {
        this.block17 = block17;
    }

    public String getBlock18() {
        return block18;
    }

    public void setBlock18(String block18) {
        this.block18 = block18;
    }

    public String getBlock19() {
        return block19;
    }

    public void setBlock19(String block19) {
        this.block19 = block19;
    }

    public String getBlock20() {
        return block20;
    }

    public void setBlock20(String block20) {
        this.block20 = block20;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBlock21() {
        return block21;
    }

    public void setBlock21(String block21) {
        this.block21 = block21;
    }

    public String getBlock22() {
        return block22;
    }

    public void setBlock22(String block22) {
        this.block22 = block22;
    }

    public String getBlock23() {
        return block23;
    }

    public void setBlock23(String block23) {
        this.block23 = block23;
    }

    public String getBlock24() {
        return block24;
    }

    public void setBlock24(String block24) {
        this.block24 = block24;
    }

    public String getBlock25() {
        return block25;
    }

    public void setBlock25(String block25) {
        this.block25 = block25;
    }

    public Long getPageDefId() {
        return pageDefId;
    }

    public void setPageDefId(Long pageDefId) {
        this.pageDefId = pageDefId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBlock26() {
        return block26;
    }

    public void setBlock26(String block26) {
        this.block26 = block26;
    }

    public String getBlock27() {
        return block27;
    }

    public void setBlock27(String block27) {
        this.block27 = block27;
    }

    public String getBlock28() {
        return block28;
    }

    public void setBlock28(String block28) {
        this.block28 = block28;
    }

    public String getBlock29() {
        return block29;
    }

    public void setBlock29(String block29) {
        this.block29 = block29;
    }

    public String getBlock30() {
        return block30;
    }

    public void setBlock30(String block30) {
        this.block30 = block30;
    }

    public String getBlock31() {
        return block31;
    }

    public void setBlock31(String block31) {
        this.block31 = block31;
    }

    public String getBlock32() {
        return block32;
    }

    public void setBlock32(String block32) {
        this.block32 = block32;
    }

    public String getBlock33() {
        return block33;
    }

    public void setBlock33(String block33) {
        this.block33 = block33;
    }

    public String getBlock34() {
        return block34;
    }

    public void setBlock34(String block34) {
        this.block34 = block34;
    }

    public String getBlock35() {
        return block35;
    }

    public void setBlock35(String block35) {
        this.block35 = block35;
    }

    public String getBlock36() {
        return block36;
    }

    public void setBlock36(String block36) {
        this.block36 = block36;
    }

    public String getBlock37() {
        return block37;
    }

    public void setBlock37(String block37) {
        this.block37 = block37;
    }

    public String getBlock38() {
        return block38;
    }

    public void setBlock38(String block38) {
        this.block38 = block38;
    }

    public String getBlock39() {
        return block39;
    }

    public void setBlock39(String block39) {
        this.block39 = block39;
    }

    public String getBlock40() {
        return block40;
    }

    public void setBlock40(String block40) {
        this.block40 = block40;
    }

    public String getBlock41() {
        return block41;
    }

    public void setBlock41(String block41) {
        this.block41 = block41;
    }

    public String getBlock42() {
        return block42;
    }

    public void setBlock42(String block42) {
        this.block42 = block42;
    }

    public String getBlock43() {
        return block43;
    }

    public void setBlock43(String block43) {
        this.block43 = block43;
    }

    public String getBlock44() {
        return block44;
    }

    public void setBlock44(String block44) {
        this.block44 = block44;
    }

    public String getBlock45() {
        return block45;
    }

    public void setBlock45(String block45) {
        this.block45 = block45;
    }

    public String getBlock46() {
        return block46;
    }

    public void setBlock46(String block46) {
        this.block46 = block46;
    }

    public String getBlock47() {
        return block47;
    }

    public void setBlock47(String block47) {
        this.block47 = block47;
    }

    public String getBlock48() {
        return block48;
    }

    public void setBlock48(String block48) {
        this.block48 = block48;
    }

    public String getBlock49() {
        return block49;
    }

    public void setBlock49(String block49) {
        this.block49 = block49;
    }

    public String getBlock50() {
        return block50;
    }

    public void setBlock50(String block50) {
        this.block50 = block50;
    }

}
