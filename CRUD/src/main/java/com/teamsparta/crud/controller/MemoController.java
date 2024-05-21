package com.teamsparta.crud.controller;

import com.teamsparta.crud.dto.MemoRequestDto;
import com.teamsparta.crud.dto.MemoResponseDto;
import com.teamsparta.crud.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        // RequestDto → Entity
        // RequestDto 객체를 사용하여 새로운 Memo 객체를 만드는 코드를 완성하세요.
        Memo memo = new Memo(requestDto);

        // Memo 최대 ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB에 저장하는 코드를 완성하세요. 키는 memo의 id, 값은 memo 객체
        memoList.put(memo.getId(), memo);

        // Entity → ResponseDto
        // Memo 클래스의 객체를 사용하여 응답 데이터 전송 객체를 생성하는 코드를 완성하세요.
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);


        // 결과(memoResponseDto)를 반환하는 코드를 완성하세요.
        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        // Map To List
        // memoList라는 자료구조에서 Memo 객체들을 추출하고, 이를 MemoResponseDto 객체로 변환한 뒤 리스트로 만드는 코드를 완성하세요.
        List<MemoResponseDto> responseList = memoList.values().stream().map(MemoResponseDto::new).toList();


        // 결과(responseList)를 반환하는 코드를 완성하세요.
        return responseList;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {

            // 해당 메모 가져오기
            // memoList라는 자료구조에서 주어진 id에 해당하는 Memo 객체를 가져오는 코드를 완성하세요.
            Memo memo = memoList.get(id);

            // memo 수정
            // update 메소드를 이용하여 memo 내용을 업데이트하는 코드를 완성하세요.
            memo.update(requestDto);

            // 결과 반환
            return memo.getId();

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {

            // 해당 메모 삭제하기
            // remove 메소드를 활용하여 아이디 값을 받아와 삭제하는 코드를 완성하세요.
            memoList.remove(id);

            return id;

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}