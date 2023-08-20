package com.portfolio.mgmtsys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.mgmtsys.domain.Assets;
import com.portfolio.mgmtsys.service.AssetsService;

@RestController
@CrossOrigin
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    AssetsService service;
    
    @GetMapping("/getassetsbyId/{id}")
    public ResponseEntity<Object> getAssetsById(@PathVariable Integer id){
        Assets assets = service.getAssetsById(id);
        if(assets==null){
            return new ResponseEntity<Object>("Account not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(assets, HttpStatus.OK);
    }

    @PostMapping("/transferin")
    public ResponseEntity<Object> transferIn(@RequestBody Map<String, Object> request){
        Assets assets = service.transferIn(request);
        if(assets==null){
            return new ResponseEntity<Object>("Account not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(assets, HttpStatus.OK);
    }

    @PostMapping("/transferout")
    public ResponseEntity<Object> transferOut(@RequestBody Map<String, Object> request){
        Assets assets = null;
        try {
            assets = service.transferOut(request);
            System.out.println(assets);
        } catch (Exception e) {
            return new ResponseEntity<Object>("Not enough balance", HttpStatus.FORBIDDEN);
        }
        if(assets==null){
            return new ResponseEntity<Object>("Account not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(assets, HttpStatus.OK);
    }
}
