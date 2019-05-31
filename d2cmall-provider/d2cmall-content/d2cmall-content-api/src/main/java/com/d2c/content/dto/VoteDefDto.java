package com.d2c.content.dto;

import com.d2c.content.model.VoteDef;

import java.util.List;

public class VoteDefDto extends VoteDef {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<VoteSelectionDto> voteSelectionList;

    public List<VoteSelectionDto> getVoteSelectionList() {
        return voteSelectionList;
    }

    public void setVoteSelectionList(List<VoteSelectionDto> voteSelectionList) {
        this.voteSelectionList = voteSelectionList;
    }
    // public JSONArray getOptionArray() {
    // JSONArray array = new JSONArray();
    // if (StringUtils.isNotBlank(this.getOption())) {
    // array = JSON.parseArray(this.getOption());
    // }
    // return array;
    // }
    //
    // public Map<Long, JSONObject> getOptionJsonMap() {
    // Map<Long, JSONObject> map = new HashMap<Long, JSONObject>();
    // JSONArray array = this.getOptionArray();
    // for (int i = 0; i < array.size(); i++) {
    // JSONObject json = array.getJSONObject(i);
    // map.put(json.getLong("selectId"), json);
    // }
    // return map;
    // }
}
