package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("猪肉涨价了", "食品"));
    rsEvents.add(new RsEvent("股市崩盘了", "经济"));
    rsEvents.add(new RsEvent("疫苗上市了", "医药"));
    return rsEvents;
  }
//          Arrays.asList("第一条事件","第二条事件","第三条事件");


  @GetMapping("/rs/{index}")
  public RsEvent getOneIndexEvent(@PathVariable int index){
    return rsList.get(index-1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    if (start == null || end == null){
      return rsList;
    }else {
      return rsList.subList(start-1,end);
    }
  }

  @PostMapping("/rs/event")
  public void addEvent(@RequestBody String jsonSting) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RsEvent rsEvent1 = objectMapper.readValue(jsonSting,RsEvent.class);
    rsList.add(rsEvent1);
  }

  @PutMapping("/rs/update")
  public void updateRsEvent(@RequestParam Integer index, @RequestBody String jsonString) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RsEvent rsEvent2 = objectMapper.readValue(jsonString,RsEvent.class);
    if (rsEvent2.getEventName() != null && rsEvent2.getKeyWord() == null){
      rsList.get(index - 1).setEventName(rsEvent2.getEventName());
    }else if (rsEvent2.getEventName() == null && rsEvent2.getKeyWord() != null){
      rsList.get(index-1).setKeyWord(rsEvent2.getKeyWord());
    }else if (rsEvent2.getEventName() != null && rsEvent2.getKeyWord() != null){
      rsList.get(index - 1).setEventName(rsEvent2.getEventName());
      rsList.get(index - 1).setKeyWord(rsEvent2.getKeyWord());
    }
  }

  @DeleteMapping("/rs/delete/{index}")
  public void deleteRsEvent(@PathVariable Integer index){
    rsList.remove(index-1);
  }
}
