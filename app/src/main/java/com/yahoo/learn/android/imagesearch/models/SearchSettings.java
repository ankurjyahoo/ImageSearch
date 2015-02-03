package com.yahoo.learn.android.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ankurj on 1/31/2015.
 */

public class SearchSettings implements Parcelable {
    private static final String PARAM_DELIM = "&";
    private static final String PARAM_IS = "imgsz=";
    private static final String PARAM_IT = "imgtype=";
    private static final String PARAM_FT = "as_filetype=";
    private static final String PARAM_SITE = "as_sitesearch=";
    private static final String NULL_STRING = "all";



    private String mImageSize = NULL_STRING;
    private String mImageType = NULL_STRING;
    private String mFileType = NULL_STRING;
    private String mSite = "";

    private int mImageSizeSpinnerPosition = 0;
    private int mImageTypeSpinnerPosition = 0;
    private int mFileTypeSpinnerPosition = 0;

    public SearchSettings(String imageSize, String imageType, String fileType, String site) {
        this.mImageSize = imageSize;
        this.mImageType = imageType;
        this.mFileType = fileType;
        this.mSite = site;
    }

    public SearchSettings() {

    }

    public String getImageSize() {
        return mImageSize;
    }

    public void setImageSize(String imageSize) {
        this.mImageSize = imageSize;
    }

    public String getImageType() {
        return mImageType;
    }

    public void setImageType(String imageType) {
        this.mImageType = imageType;
    }

    public String getFileType() {
        return mFileType;
    }

    public void setFileType(String fileType) {
        this.mFileType = fileType;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public int getImageSizeSpinnerPosition() {
        return mImageSizeSpinnerPosition;
    }

    public void setImageSizeSpinnerPosition(int imageSizeSpinnerPosition) {
        this.mImageSizeSpinnerPosition = imageSizeSpinnerPosition;
    }

    public int getImageTypeSpinnerPosition() {
        return mImageTypeSpinnerPosition;
    }

    public void setImageTypeSpinnerPosition(int imageTypeSpinnerPosition) {
        this.mImageTypeSpinnerPosition = imageTypeSpinnerPosition;
    }

    public int getFileTypeSpinnerPosition() {
        return mFileTypeSpinnerPosition;
    }

    public void setFileTypeSpinnerPosition(int fileTypeSpinnerPosition) {
        this.mFileTypeSpinnerPosition = fileTypeSpinnerPosition;
    }

    public String getParamsString()
    {
        StringBuffer sb = new StringBuffer();
        if (!NULL_STRING.equals(mImageSize))
            sb.append(PARAM_DELIM).append(PARAM_IS).append(mImageSize);

        if (!NULL_STRING.equals(mImageType))
            sb.append(PARAM_DELIM).append(PARAM_IT).append(mImageType);

        if (!NULL_STRING.equals(mFileType))
            sb.append(PARAM_DELIM).append(PARAM_FT).append(mFileType);

        if ((mSite != null) && !mSite.trim().equals(""))
            sb.append(PARAM_DELIM).append(PARAM_SITE).append(mSite);


        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // This is extremely painful to write and debug
    // Need to find out from the instructor as to the real cost compared to running http calls and
    // loading images
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageSize);
        dest.writeString(mFileType);
        dest.writeString(mImageType);
        dest.writeString(mSite);
        dest.writeInt(mImageSizeSpinnerPosition);
        dest.writeInt(mImageTypeSpinnerPosition);
        dest.writeInt(mFileTypeSpinnerPosition);
    }

    public SearchSettings(Parcel input) {
        mImageSize = input.readString();
        mFileType = input.readString();
        mImageType = input.readString();
        mSite = input.readString();
        mImageSizeSpinnerPosition = input.readInt();
        mImageTypeSpinnerPosition = input.readInt();
        mFileTypeSpinnerPosition = input.readInt();
    }

    public static final Creator<SearchSettings> CREATOR =
            new Creator<SearchSettings>() {

                @Override
                public SearchSettings createFromParcel(Parcel source) {
                    return new SearchSettings(source);
                }

                @Override
                public SearchSettings[] newArray(int size) {
                    return new SearchSettings[size];
                }
            };
}
