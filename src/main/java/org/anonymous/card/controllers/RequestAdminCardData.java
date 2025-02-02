package org.anonymous.card.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.anonymous.member.contants.Authority;

@Data
public class RequestAdminCardData {

    private String mode;

    @NotBlank
    private String cid; // 카드 아이디

    @NotBlank
    private String cardname ; // 카드명

    private String content; // 카드 설명

    private boolean open; // 몇개씩 보여질지 등등..

    private String category;

    private int rowsPerPage; // 1 페이지당 카드종류 갯수

    private int pageRanges; // front 뷰일때 노출되는 페이지 링크 갯수

    private int pageRangesMobile; // mobile 뷰일때 노출되는 페이지 링크 갯수

    private boolean useEditor; // 에디터 사용 여부

    private boolean useEditorImage; // 에디터 첨부 이미지 사용 여부
    // 광고같은 이미지가 추가 될수도 있을거같구...

    private boolean listUnderView; // 보기페이지 하단 목록 노출

    private String locationAfterPick; // 카드 추가 후 이동 경로 list, mycard ...

    private boolean isUse; // 카드 사용 유무


    /**
     * ALL - 비회원 + 회원 + 관리자
     * USER - 회원 + 관리자
     * ADMIN - 관리자
     */
    private Authority listAuthority; // 목록 접근 권한

    private Authority viewAuthority; // 카드정보 보기 접근 권한
    // 이건 필요할려나..?

    private Authority writeAuthority; // 추가, 수정, 삭제 권한

}
