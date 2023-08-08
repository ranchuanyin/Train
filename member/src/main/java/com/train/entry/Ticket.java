package com.train.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车票
 *
 * @TableName ticket
 */
@TableName(value = "ticket")
@Data
public class Ticket implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 乘客id
     */
    private Long passengerId;
    /**
     * 乘客姓名
     */
    private String passengerName;
    /**
     * 日期
     */
    private Date trainDate;
    /**
     * 车次编号
     */
    private String trainCode;
    /**
     * 箱序
     */
    private Integer carriageIndex;
    /**
     * 排号|01, 02
     */
    private String seatRow;
    /**
     * 列号|枚举[SeatColEnum]
     */
    private String seatCol;
    /**
     * 出发站
     */
    private String startStation;
    /**
     * 出发时间
     */
    private Date startTime;
    /**
     * 到达站
     */
    private String endStation;
    /**
     * 到站时间
     */
    private Date endTime;
    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    private String seatType;
    /**
     * 新增时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Ticket other = (Ticket) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
                && (this.getPassengerId() == null ? other.getPassengerId() == null : this.getPassengerId().equals(other.getPassengerId()))
                && (this.getPassengerName() == null ? other.getPassengerName() == null : this.getPassengerName().equals(other.getPassengerName()))
                && (this.getTrainDate() == null ? other.getTrainDate() == null : this.getTrainDate().equals(other.getTrainDate()))
                && (this.getTrainCode() == null ? other.getTrainCode() == null : this.getTrainCode().equals(other.getTrainCode()))
                && (this.getCarriageIndex() == null ? other.getCarriageIndex() == null : this.getCarriageIndex().equals(other.getCarriageIndex()))
                && (this.getSeatRow() == null ? other.getSeatRow() == null : this.getSeatRow().equals(other.getSeatRow()))
                && (this.getSeatCol() == null ? other.getSeatCol() == null : this.getSeatCol().equals(other.getSeatCol()))
                && (this.getStartStation() == null ? other.getStartStation() == null : this.getStartStation().equals(other.getStartStation()))
                && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
                && (this.getEndStation() == null ? other.getEndStation() == null : this.getEndStation().equals(other.getEndStation()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getSeatType() == null ? other.getSeatType() == null : this.getSeatType().equals(other.getSeatType()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getPassengerId() == null) ? 0 : getPassengerId().hashCode());
        result = prime * result + ((getPassengerName() == null) ? 0 : getPassengerName().hashCode());
        result = prime * result + ((getTrainDate() == null) ? 0 : getTrainDate().hashCode());
        result = prime * result + ((getTrainCode() == null) ? 0 : getTrainCode().hashCode());
        result = prime * result + ((getCarriageIndex() == null) ? 0 : getCarriageIndex().hashCode());
        result = prime * result + ((getSeatRow() == null) ? 0 : getSeatRow().hashCode());
        result = prime * result + ((getSeatCol() == null) ? 0 : getSeatCol().hashCode());
        result = prime * result + ((getStartStation() == null) ? 0 : getStartStation().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndStation() == null) ? 0 : getEndStation().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getSeatType() == null) ? 0 : getSeatType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", passengerId=").append(passengerId);
        sb.append(", passengerName=").append(passengerName);
        sb.append(", trainDate=").append(trainDate);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", carriageIndex=").append(carriageIndex);
        sb.append(", seatRow=").append(seatRow);
        sb.append(", seatCol=").append(seatCol);
        sb.append(", startStation=").append(startStation);
        sb.append(", startTime=").append(startTime);
        sb.append(", endStation=").append(endStation);
        sb.append(", endTime=").append(endTime);
        sb.append(", seatType=").append(seatType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}