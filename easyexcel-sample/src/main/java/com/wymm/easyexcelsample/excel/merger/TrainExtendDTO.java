package com.wymm.easyexcelsample.excel.merger;

import lombok.Data;

import java.util.Date;

/**
 * 返程上报：航班/车次信息
 *
 * @author XieYiShan
 * 2021年12月02日 17:33:00
 */
@Data
public class TrainExtendDTO {

    private String trainCode;

    private String seatNumber;

    private Date backDepartTime;

    private Date backArrivalTime;

    private String backDepartSite;

    private String backArrivalSite;
    
}
