package com.wymm.jackson.time;

import com.wymm.jackson.model.Car;
import lombok.Data;

import java.util.Date;

@Data
public class Request {
    private Car car;
    private Date datePurchased;
}
