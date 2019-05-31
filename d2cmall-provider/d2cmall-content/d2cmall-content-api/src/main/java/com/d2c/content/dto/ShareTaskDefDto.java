package com.d2c.content.dto;

import com.d2c.content.model.ShareTaskDef;

public class ShareTaskDefDto extends ShareTaskDef {

    private static final long serialVersionUID = 1L;
    /**
     * 用户是否已领取该任务
     */
    private boolean claimed;

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

}
