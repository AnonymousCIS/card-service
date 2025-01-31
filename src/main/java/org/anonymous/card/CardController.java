package org.anonymous.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anonymous.global.rests.JSONData;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="카드 API", description = "카드 생성, 수정, 조회, 삭제, 검증 기능 제공")
@RestController
@RequiredArgsConstructor
public class CardController {

    /**
     * 카드 단일 생성 & 수정 처리
     * @param files
     * @param errors
     * @return
     */
    @Operation(summary = "카드 단일 생성 & 수정 처리")
    @ApiResponse(description = "...")
    @PostMapping("/save")
    public JSONData save(@RequestPart("card") MultipartFile[] files, @Valid Errors errors) {

        return null;
    }

    /**
     * 카드 단일 삭제
     * @param seq
     * @return
     */
    
    @Operation(summary = "카드 단일 삭제")
    @ApiResponse(description = "카드 삭제 완료 후 삭제된 카드 정보 반환")
    @Parameter(name="seq", required = true, description = "경로변수, 카드 등록번호")

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {

        return null;
    }

    /**
     * 카드 목록 삭제
     * @param gid
     * @return
     */
    
    @Operation(summary = "카드 목록 삭제")
    @ApiResponse(description = "카드 삭제 완료 후 삭제된 카드 정보 반환")
    @Parameter(name="gid", required = true, description = "경로변수, 그룹 ID")

    @DeleteMapping("/deletes/{gid}")
    public JSONData deletes(@PathVariable("gid") String gid) {

        return null;
    }

    /**
     * 카드 단일 조회
     * gid, location
     */
    @Operation(summary = "카드 단일 조회 - seq")
    @ApiResponse(description = "")
    @Parameter(name="seq", required = true, description = "경로변수, 카드 등록번호")


    @GetMapping(path={"/view/{seq}"})
    public JSONData info(@PathVariable("seq") Long seq) {


        return null;
    }

    /**
     * 카드 목록 조회
     * gid, location
     */
    @Operation(summary = "카드 목록 조회 - gid")
    @ApiResponse(description = "")
    @Parameter(name="gid", required = true, description = "경로변수, 그룹 ID")


    @GetMapping(path={"/list/{gid}"})
    public JSONData list(@PathVariable("gid") String gid) {


        return null;
    }


}
