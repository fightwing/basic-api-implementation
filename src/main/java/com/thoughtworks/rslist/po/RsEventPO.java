package com.thoughtworks.rslist.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 17:15
 */
@Entity
@Table(name = "rsevent")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsEventPO {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;
    private int userId;
}
