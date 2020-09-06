package com.wymm.concurrent.base2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalWithUserContext implements Runnable, AutoCloseable {
    
    private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
    
    private final Integer userId;
    
    public ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public void run() {
        try {
            log.debug(userContext.get() == null ? "null" : userContext.get().toString());
            String userName = "userName" + userId;
            // userName = userRepository.getUserNameByUserId(userId);
            userContext.set(new Context(userName));
            log.debug("userId: " + userId + " is: " + userContext.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    @Override
    public void close() {
        userContext.remove();
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
