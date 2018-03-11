package com.squadfinder.brend.squadandroidcalculator.domain.calc;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

/**
 * Created by brend on 3/9/2018.
 */
public class MarkPoint implements Parcelable {
    private Integer id;
    private PointF pointCoordinates;
    private PointType pointType;

    public MarkPoint(Integer id, float x, float y, PointType pointType) {
        this.id = id;
        this.pointCoordinates = new PointF(x, y);
        this.pointType = pointType;
    }

    protected MarkPoint(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        pointCoordinates = in.readParcelable(PointF.class.getClassLoader());
        pointType = PointType.valueOf(in.readString());
    }

    public PointF getPointCoordinates() {
        return pointCoordinates;
    }

    public void setPointCoordinates(PointF pointCoordinates) {
        this.pointCoordinates = pointCoordinates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkPoint markPoint = (MarkPoint) o;

        return id != null ? id.equals(markPoint.id) : markPoint.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MarkPoint{" +
                "id=" + id +
                ", pointCoordinates=" + pointCoordinates +
                ", pointType=" + pointType +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeParcelable(pointCoordinates, flags);
        dest.writeString(pointType.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarkPoint> CREATOR = new Creator<MarkPoint>() {
        @Override
        public MarkPoint createFromParcel(Parcel in) {
            return new MarkPoint(in);
        }

        @Override
        public MarkPoint[] newArray(int size) {
            return new MarkPoint[size];
        }
    };
}
