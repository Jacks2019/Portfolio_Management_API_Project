package com.portfolio.mgmtsys.aspect;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portfolio.mgmtsys.domain.Assets;
import com.portfolio.mgmtsys.repository.AssetsRepo;

@Aspect
@Component
public class CheckBalanceAspect {

    @Autowired
    AssetsRepo assetsRepo;
    
    @Before(value = "execution(* *..AssetsServiceImpl.transferOut(..))")
    public void checkBalance(JoinPoint jp) throws Exception{
        System.out.println("Aspect invoked.");
        Object[] args = jp.getArgs();
        Object request = args[0];
        Class<?> mapClazz = Map.class;
        Double amount = 1.0;
        Double balance = 0.0;
        try {
            Method getMethod = mapClazz.getMethod("get", Object.class);
            Integer id = (Integer)getMethod.invoke(request, "id");
            amount = (Double)getMethod.invoke(request, "amount");
            Optional<Assets> assetsOptional = assetsRepo.findById(id);
            Assets assets = assetsOptional.get();
            balance = assets.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(balance < amount){
            throw new Exception("Not enough balance.");
        }
    }
}
