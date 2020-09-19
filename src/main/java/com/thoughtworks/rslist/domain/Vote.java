package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * @author Boyu Yuan
 * @date 2020/9/18 19:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    private int userId;
    private int rsEventId;
    private LocalDateTime voteTime;
    private int voteNum;
}
