package com.train.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 座位
 *
 * @TableName train_seat
 */
@TableName(value = "train_seat")
@Data
public class TrainSeat implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 车次编号
     */
    private String trainCode;
    /**
     * 厢序
     */
    private Integer carriageIndex;
    /**
     * 排号|01, 02
     */
    @TableField("`row`")
    private String row;
    /**
     * 列号|枚举[SeatColEnum]
     */
    private String col;
    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    private String seatType;
    /**
     * 同车厢座序
     */
    private Integer carriageSeatIndex;
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
        TrainSeat other = (TrainSeat) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTrainCode() == null ? other.getTrainCode() == null : this.getTrainCode().equals(other.getTrainCode()))
                && (this.getCarriageIndex() == null ? other.getCarriageIndex() == null : this.getCarriageIndex().equals(other.getCarriageIndex()))
                && (this.getRow() == null ? other.getRow() == null : this.getRow().equals(other.getRow()))
                && (this.getCol() == null ? other.getCol() == null : this.getCol().equals(other.getCol()))
                && (this.getSeatType() == null ? other.getSeatType() == null : this.getSeatType().equals(other.getSeatType()))
                && (this.getCarriageSeatIndex() == null ? other.getCarriageSeatIndex() == null : this.getCarriageSeatIndex().equals(other.getCarriageSeatIndex()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTrainCode() == null) ? 0 : getTrainCode().hashCode());
        result = prime * result + ((getCarriageIndex() == null) ? 0 : getCarriageIndex().hashCode());
        result = prime * result + ((getRow() == null) ? 0 : getRow().hashCode());
        result = prime * result + ((getCol() == null) ? 0 : getCol().hashCode());
        result = prime * result + ((getSeatType() == null) ? 0 : getSeatType().hashCode());
        result = prime * result + ((getCarriageSeatIndex() == null) ? 0 : getCarriageSeatIndex().hashCode());
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
        sb.append(", trainCode=").append(trainCode);
        sb.append(", carriageIndex=").append(carriageIndex);
        sb.append(", row=").append(row);
        sb.append(", col=").append(col);
        sb.append(", seatType=").append(seatType);
        sb.append(", carriageSeatIndex=").append(carriageSeatIndex);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}