package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Log.LogConfig;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import com.thoughtworks.rslist.exception.RsEventNotValidRequestParamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;



@RestController
public class RsController {
  private static Logger log = LoggerFactory.getLogger(RsController.class);

  UserController userController = new UserController();
  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    User user =new User("Bob", "male", 18,"a@b.com","12345678911");
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("猪肉涨价了", "食品", user));
    rsEvents.add(new RsEvent("股市崩盘了", "经济",user));
    rsEvents.add(new RsEvent("疫苗上市了", "医药",user));
    return rsEvents;
  }



  @GetMapping("/rs/{index}")
  public ResponseEntity getOneIndexEvent(@PathVariable int index){
    if (index <= 0 || index > rsList.size()){
      throw new RsEventNotValidException("invalid index");
    }
    return ResponseEntity.ok(rsList.get(index-1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    if (start <= 0 || end > rsList.size() || start > end){
      throw new RsEventNotValidRequestParamException("invalid request param");
    }
    if (start == null || end == null){
      return ResponseEntity.ok(rsList);
    }else {
      return ResponseEntity.ok(rsList.subList(start-1,end));
    }
  }

  @PostMapping("/rs/event")
  public ResponseEntity addEvent(@RequestBody @Valid String jsonSting) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RsEvent rsEvent1 = objectMapper.readValue(jsonSting,RsEvent.class);
    if (!userController.isUserNameExist(rsEvent1.getUser())){
      userController.register(rsEvent1.getUser());
    }
    rsList.add(rsEvent1);
    String index = String.valueOf(rsList.indexOf(rsEvent1));
    HttpStatus status = HttpStatus.CREATED;
    MultiValueMap<String,String> headers = new HttpHeaders();
    headers.add("index", index);
    return new ResponseEntity(headers,status);
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

  @ExceptionHandler({RsEventNotValidException.class, MethodArgumentNotValidException.class, RsEventNotValidRequestParamException.class})
  public ResponseEntity rsExceptionHandler(Exception e){
    LogConfig logConfig = new LogConfig();
    String errorMessage;
    if (e instanceof MethodArgumentNotValidException){
      errorMessage = "invalid param";
    }else {
      errorMessage = e.getMessage();
    }
    log.info("测试输出异常");
    log.error(errorMessage);

    Error error = new Error();
    error.setError(errorMessage);
    return ResponseEntity.badRequest().body(error);

  }


}
