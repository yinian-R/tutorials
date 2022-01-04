package com.wymm.concurrent.base2.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalWithUserContext2 implements Runnable {
    
    public static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    
    private final Integer userId;
    
    public ThreadLocalWithUserContext2(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public void run() {
        try {
            log.debug("first:" + (userContext.get() == null ? "null" : userContext.get().toString()));
            String userName = "userName" + userId;
            // userName = userRepository.getUserNameByUserId(userId);
            userContext.set(new Context(userName));
            log.debug("userId: " + userId + " is: " + userContext.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Context {
        private String userName;
        
        public Context(String userName) {
            this.userName = userName;
        }
        
        @Override
        public String toString() {
            return "Context{" +
                    "userName='" + userName + '\'' +
                    '}';
        }
    }
}
