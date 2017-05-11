package com.android.runintest2.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class TestItem implements Parcelable{
	
	public String title;
	public Intent intent;
	public Bundle extras;
    public Bundle metaDate;
    
    public TestItem(){
    	//Empty
    }
    
    @Override
    public int describeContents() {
    	return 0;
    }
    
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		if(intent != null){
			dest.writeByte((byte) 1);
			intent.writeToParcel(dest, flags);
		}else{
			dest.writeByte((byte) 0);
		}
		
		dest.writeBundle(extras);
		dest.writeBundle(metaDate);
	}
	
	public void readFromParcel(Parcel in){
		title = in.readString();
		if(in.readByte() != 0){
			intent = Intent.CREATOR.createFromParcel(in);
		}
		extras = in.readBundle();
		metaDate = in.readBundle();
	}
	
	TestItem(Parcel in){ readFromParcel(in); }
	
	public static final Creator<TestItem> CREATOR = new Creator<TestItem>() {
        public TestItem createFromParcel(Parcel source) {
            return new TestItem(source);
        }
        public TestItem[] newArray(int size) {
            return new TestItem[size];
        }
    };
}
