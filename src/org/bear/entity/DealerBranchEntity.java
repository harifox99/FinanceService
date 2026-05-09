package org.bear.entity;

/**
 * Semi-Auto Gen by Geminiㄩ坝だ翴戈
 */
public class DealerBranchEntity {
    private String groupCode;
    private String code;
    private String name;

    // 礚把计篶
    public DealerBranchEntity() {
    }

    // 把计篶
    public DealerBranchEntity(String groupCode, String code, String name) {
        this.groupCode = groupCode;
        this.code = code;
        this.name = name;
    }

    // Getters and Setters
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
