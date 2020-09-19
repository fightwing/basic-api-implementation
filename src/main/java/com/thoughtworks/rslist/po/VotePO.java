package com.thoughtworks.rslist.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Boyu Yuan
 * @date 2020/9/18 19:04
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotePO {
    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private int rsEventId;
    private LocalDateTime voteTime;
    private int voteNum;
}
