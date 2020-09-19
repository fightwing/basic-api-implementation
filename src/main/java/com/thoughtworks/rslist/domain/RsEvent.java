package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * @author Boyu Yuan
 * @date 2020/9/15 15:52
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsEvent {
    private String eventName;
    private String keyWord;
    @Valid
    private int userId;
    private int voteNum;

    public RsEvent(String eventName, String keyWord, @Valid int userId) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.userId = userId;
    }
}
