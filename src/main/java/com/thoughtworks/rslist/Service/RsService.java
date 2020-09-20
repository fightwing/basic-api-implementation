package com.thoughtworks.rslist.Service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.po.RsEventPO;
import com.thoughtworks.rslist.po.UserPO;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Boyu Yuan
 * @date 2020/9/17 19:47
 */
@Service
public class RsService {

    @Autowired
    UserService userService;

    final RsRepository rsRepository;
    final UserRepository userRepository;
    public  RsService(RsRepository rsRepository, UserRepository userRepository){
        this.rsRepository = rsRepository;
        this.userRepository = userRepository;
    }

    public void addOneEvent(RsEventPO rsEventPO){
         rsRepository.save(rsEventPO);
    }

    public RsEventPO rsEventToRsEventPO(RsEvent rsEvent){
        Optional<UserPO> userPO = userRepository.findById(rsEvent.getUserId());
        RsEventPO rsEventPO = new RsEventPO();
        rsEventPO.setEventName(rsEvent.getEventName());
        rsEventPO.setKeyWord(rsEvent.getKeyWord());
        rsEventPO.setVoteNum(rsEvent.getVoteNum());
        rsEventPO.setUserPO(userPO.get());
        return rsEventPO;
    }

    public RsEvent rsEventPOToRsEvent(RsEventPO rsEventPO){
        RsEvent rsEvent = RsEvent.builder().eventName(rsEventPO.getEventName()).keyWord(rsEventPO.getKeyWord())
                .id(rsEventPO.getId()).voteNum(rsEventPO.getVoteNum()).build();
        return rsEvent;
    }

    @Transactional
    public ResponseEntity updateOneEvent(RsEventPO rsEventPO, Integer rsEventId) {
        if (rsRepository.findById(rsEventId).get().getUserPO().getId() == rsEventPO.getUserPO().getId()) {
            rsRepository.updateRsEventById(rsEventPO.getEventName(), rsEventPO.getKeyWord(), rsEventId);
            return ResponseEntity.ok(null);
        } else {
            MultiValueMap<String,String> headers = new HttpHeaders();
            headers.add("message","userId为必传字段");
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(headers,status);
        }
    }

    public RsEventPO findById(Integer rsEventId) {
        return rsRepository.findById(rsEventId).get();
    }

    public List<RsEvent> findAllRsEvent(){
        List<RsEventPO> rsEventPOS = rsRepository.findAll();
        List<RsEvent>  rsEvents = new ArrayList<>();
        for (RsEventPO rsEventPO : rsEventPOS) {
            rsEvents.add(rsEventPOToRsEvent(rsEventPO));
        }
        return rsEvents;
    }

    public void deleteById(Integer rsEventId){
        rsRepository.deleteById(rsEventId);
    }
}
