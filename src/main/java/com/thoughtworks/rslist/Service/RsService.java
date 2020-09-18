package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.repository.RsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:47
 */
@Service
public class RsService {

    @Autowired
    RsRepository rsRepository;

    public void addOneEvent(RsEventPO rsEventPO){
         rsRepository.save(rsEventPO);
    }

    public RsEventPO rsEventToRsEventPO(RsEvent rsEvent,Integer userId){
        RsEventPO rsEventPO = new RsEventPO();
        rsEventPO.setEventName(rsEvent.getEventName());
        rsEventPO.setKeyWord(rsEvent.getKeyWord());
        rsEventPO.setUserId(userId);
        return rsEventPO;
    }
}
