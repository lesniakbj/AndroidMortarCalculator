package com.squadfinder.brend.squadandroidcalculator.domain.calc;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */
public class MarkPoint implements Parcelable {
    private Integer id;
    private PointF pointCoordinates;
    private PointType pointType;
    private List<MarkPoint> mappedPoints;
    private String mapGrid;
    private Drawable markSnapshot;

    public MarkPoint(Integer id, float x, float y, PointType pointType) {
        this.id = id;
        this.pointCoordinates = new PointF(x, y);
        this.pointType = pointType;
        this.mappedPoints = new ArrayList<>();
        this.mapGrid = "";
        this.markSnapshot = null;
    }

    protected MarkPoint(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        pointCoordinates = in.readParcelable(PointF.class.getClassLoader());
        pointType = PointType.valueOf(in.readString());
        mappedPoints = in.createTypedArrayList(MarkPoint.CREATOR);
        mapGrid = in.readString();
        markSnapshot = new BitmapDrawable((Bitmap)in.readParcelable(getClass().getClassLoader()));
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
        dest.writeTypedList(mappedPoints);
        dest.writeString(mapGrid);
        dest.writeParcelable(((BitmapDrawable)markSnapshot).getBitmap(), flags);
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

    public void addMappedPoint(MarkPoint mp) {
        if(mappedPoints == null) {
            mappedPoints = new ArrayList<>();
        }
        mappedPoints.add(mp);
    }


    public void setMappedPoints(ArrayList<MarkPoint> mappedPoints) {
        this.mappedPoints = mappedPoints;
    }

    public List<MarkPoint> getMappedPoints() {
        return mappedPoints;
    }

    public String getMapGrid() {
        return mapGrid;
    }

    public void setMapGrid(String mapGrid) {
        this.mapGrid = mapGrid;
    }

    public Drawable getMarkSnapshot() {
        return markSnapshot;
    }

    public void setMarkSnapshot(Drawable markSnapshot) {
        this.markSnapshot = markSnapshot;
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
                ", mappedPoints=" + mappedPoints +
                ", mapGrid='" + mapGrid + '\'' +
                '}';
    }

}
