package com.netease.vce.demo;

import android.os.Parcel;
import android.os.Parcelable;

public class SelfInfo implements Parcelable {

    /**
     * 反序列化
     */
    public static final Creator<SelfInfo> CREATOR = new Creator<SelfInfo>() {
        /**
         * 从序列化对象中创建原始对象
         */
        @Override
        public SelfInfo createFromParcel(Parcel in) {
            return new SelfInfo(in);
        }

        /**
         * 创建指定长度的原始对象数组
         */
        @Override
        public SelfInfo[] newArray(int size) {
            return new SelfInfo[size];
        }
    };

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 业务代码
     */
    private String code;

    /**
     * 业务描述
     */
    private String desc;

    /**
     * 是否是VIP
     */
    private boolean isVIP;

    public SelfInfo(String name, String code, String desc, boolean isVIP) {
        this.name = name;
        this.code = code;
        this.desc = desc;
        this.isVIP = isVIP;
    }

    protected SelfInfo(Parcel in) {
        name = in.readString();
        code = in.readString();
        desc = in.readString();
        isVIP = in.readByte() != 0;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeString(desc);
        parcel.writeByte((byte) (isVIP ? 1 : 0));
    }

    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
    public boolean isVIP() {
        return isVIP;
    }

    @Override
    public String toString() {
        return "SelfInfo{" + "name='" + name + '\'' + ", code='" + code + '\'' + ", desc='" + desc + '\'' + ", isVIP=" +
               isVIP + '}';
    }
}
