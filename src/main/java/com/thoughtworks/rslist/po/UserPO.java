package com.thoughtworks.rslist.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;


/**
 * @author Boyu Yuan
 * @date 2020/9/17 17:15
 */

@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class UserPO {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    @Builder.Default
    private int voteNum = 10;

    @OneToMany(mappedBy = "userPO",cascade = CascadeType.ALL)
    private List<RsEventPO> rsEventPOS;

}
