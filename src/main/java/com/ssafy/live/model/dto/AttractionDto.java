package com.ssafy.live.model.dto;



import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class AttractionDto {
    private int no;
    private int contentId;
    private String title;
    private int contentTypeId;
    private int areaCode;
    private int siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private int mapLevel;
    private BigDecimal latitude;   // DECIMAL(20,17) -> BigDecimal 사용 권장
    private BigDecimal longitude;  // DECIMAL(20,17) -> BigDecimal 사용 권장
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;

    public AttractionDto() {
    }

    public AttractionDto(int no, int contentId, String title, int contentTypeId, int areaCode, int siGunGuCode,
                         String firstImage1, String firstImage2, int mapLevel, BigDecimal latitude, BigDecimal longitude,
                         String tel, String addr1, String addr2, String homepage, String overview) {
        this.no = no;
        this.contentId = contentId;
        this.title = title;
        this.contentTypeId = contentTypeId;
        this.areaCode = areaCode;
        this.siGunGuCode = siGunGuCode;
        this.firstImage1 = firstImage1;
        this.firstImage2 = firstImage2;
        this.mapLevel = mapLevel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tel = tel;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.homepage = homepage;
        this.overview = overview;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getSiGunGuCode() {
        return siGunGuCode;
    }

    public void setSiGunGuCode(int siGunGuCode) {
        this.siGunGuCode = siGunGuCode;
    }

    public String getFirstImage1() {
        return firstImage1;
    }

    public void setFirstImage1(String firstImage1) {
        this.firstImage1 = firstImage1;
    }

    public String getFirstImage2() {
        return firstImage2;
    }

    public void setFirstImage2(String firstImage2) {
        this.firstImage2 = firstImage2;
    }

    public int getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(int mapLevel) {
        this.mapLevel = mapLevel;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "AttractionDto{" +
                "no=" + no +
                ", contentId=" + contentId +
                ", title='" + title + '\'' +
                ", contentTypeId=" + contentTypeId +
                ", areaCode=" + areaCode +
                ", siGunGuCode=" + siGunGuCode +
                ", firstImage1='" + firstImage1 + '\'' +
                ", firstImage2='" + firstImage2 + '\'' +
                ", mapLevel=" + mapLevel +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", tel='" + tel + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", homepage='" + homepage + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }

	
}
