package com.wymm.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Views {
    public static class Public {}
    
    public static class Internal extends Public {}
    
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @JsonView(Views.Public.class)
        public int id;
        
        @JsonView(Views.Public.class)
        public String itemName;
        
        @JsonView(Views.Internal.class)
        public String ownerName;
    }
    
}
