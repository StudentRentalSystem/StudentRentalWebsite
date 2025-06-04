package xyz.jessyu.studentrentalwebsite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "house_rental")
public class RentalInfo {

    @Id
    @JsonProperty("_id")
    private String id = "未知";

    @Field("坪數")
    @JsonProperty("坪數")
    private List<Float> area = List.of();

    @Field("是否可養寵物")
    @JsonProperty("是否可養寵物")
    private Integer allowPet = 0;

    @Field("是否可開伙")
    @JsonProperty("是否可開伙")
    private Integer allowCook = 0;

    @Field("是否可養魚")
    @JsonProperty("是否可養魚")
    private Integer allowFish = 0;

    @Field("是否有電梯")
    @JsonProperty("是否有電梯")
    private Integer hasElevator = 0;

    @Field("是否有汽車停車位")
    @JsonProperty("是否有汽車停車位")
    private Integer hasCarParking = 0;

    @Field("是否有機車停車位")
    @JsonProperty("是否有機車停車位")
    private Integer hasScooterParking = 0;

    @Field("是否可租屋補助")
    @JsonProperty("是否可租屋補助")
    private Integer rentalSubsidy = 0;

    @Field("是否有頂樓加蓋")
    @JsonProperty("是否有頂樓加蓋")
    private Integer hasRooftopAddOn = 0;

    @Field("格局")
    @JsonProperty("格局")
    private Layout layout = new Layout();

    @Field("性別限制")
    @JsonProperty("性別限制")
    private GenderLimit genderLimit = new GenderLimit();

    @Field("地址")
    @JsonProperty("地址")
    private String address = "未知";

    @Field("租金")
    @JsonProperty("租金")
    private RentalRange rentalRange = new RentalRange();

    @Field("聯絡方式")
    @JsonProperty("聯絡方式")
    private List<ContactInfo> contactInfos = List.of(new ContactInfo());

    @Field("照片")
    @JsonProperty("照片")
    private List<String> photos = List.of();

    // ================== Inner Classes ===================

    @Data
    public static class Layout {
        @Field("廳")
        @JsonProperty("廳")
        private int livingRoom = 0;

        @Field("衛")
        @JsonProperty("衛")
        private int bathroom = 0;

        @Field("房")
        @JsonProperty("房")
        private int bedroom = 0;
    }

    @Data
    public static class RentalRange {
        @Field("minRental")
        @JsonProperty("minRental")
        private int minRental = 0;

        @Field("maxRental")
        @JsonProperty("maxRental")
        private int maxRental = 0;
    }

    @Data
    public static class GenderLimit {
        @Field("男")
        @JsonProperty("男")
        private Integer male = 0;

        @Field("女")
        @JsonProperty("女")
        private Integer female = 0;
    }

    @Data
    public static class ContactInfo {
        @Field("聯絡人")
        @JsonProperty("聯絡人")
        private String contactPerson = "未知";

        @Field("手機")
        @JsonProperty("手機")
        private List<String> phones = List.of("未知");

        @Field("lineID")
        @JsonProperty("lineID")
        private List<String> lineIds = List.of("未知");

        @Field("lineLink")
        @JsonProperty("lineLink")
        private List<String> lineLinks = List.of("未知");

        @Field("others")
        @JsonProperty("others")
        private List<String> others = List.of("未知");
    }
}
