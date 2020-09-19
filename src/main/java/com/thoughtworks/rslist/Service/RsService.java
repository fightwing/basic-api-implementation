package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.repository.RsRepository;
import org.springframework.stereotype.Service;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:47
 */
@Service
public class RsService {

    final RsRepository rsRepository;
    public  RsService(RsRepository rsRepository){
        this.rsRepository = rsRepository;
    }

    public void addOneEvent(RsEventPO rsEventPO){
         rsRepository.save(rsEventPO);
    }

    public RsEventPO rsEventToRsEventPO(RsEvent rsEvent){
        RsEventPO rsEventPO = new RsEventPO();
        rsEventPO.setEventName(rsEvent.getEventName());
        rsEventPO.setKeyWord(rsEvent.getKeyWord());
        return rsEventPO;
    }
}
