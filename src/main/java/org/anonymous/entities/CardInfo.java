package org.anonymous.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.anonymous.global.entities.BaseMemberEntity;

import java.io.Serializable;

@Data
@Entity
@Table(indexes = {
        @Index(name="idx_gid", columnList = "gid, listOrder, createdAt"),
        @Index(name="idx_gid_location", columnList = "gid, location, listOrder, createdAt")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfo extends BaseMemberEntity implements Serializable {

    @Id @GeneratedValue
    private Long seq; // 카드 등록시 번호

    @Enumerated(EnumType.STRING)
    @Column(length=20)
    private List<Category> categories ;// 분류 - API에서 뽑아옴, 혹은 우리가 정의해서 DB에 넣을거임

    @Column(length=30, nullable = false)
    private String cardname; // 분류 - API에서 뽑아옴, 혹은 우리가 정의해서 DB에 넣을거임

    @Lob
    @Column(nullable = false)
    private String content; // 카드 설명 - API에서 뽑아옴, 혹은 우리가 정의해서 DB에 넣을거임

    // API 보고 결정
    @Column(nullable = false)
    private boolean isOpen;

    // API 보고 결정
// API 에서 판매중지된 만료상품인지 여부가 없을경우에 사용할 논리값
// 일단 API에서는 모두 가져온 후
// isUse가 true이면 사용, false면 사용 X
    @Column(nullable = false)
    private boolean isUse;

}
