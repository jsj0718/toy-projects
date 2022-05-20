package com.elevenhelevenm.practice.board;

import com.elevenhelevenm.practice.board.domain.board.service.BoardService;
import com.elevenhelevenm.practice.board.domain.grid.service.GridDataService;
import com.elevenhelevenm.practice.board.domain.user.repository.SuperAdminRepository;
import com.elevenhelevenm.practice.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
@RequiredArgsConstructor
//@Component
public class InitApplicationRunner implements ApplicationRunner {

    private final BoardService boardService;

    private final UserService userService;

    private final SuperAdminRepository superAdminRepository;

    private final GridDataService gridDataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
/*

        for (int i = 1; i <= 100; i++) {
            BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .author("author" + i)
                    .build();

            boardService.save(requestDto);
        }

        for (int i = 1; i < 10; i++) {
            GridDataDto.GridDataDtoBuilder builder = GridDataDto.builder()
                    .registrationDate(LocalDateTime.now())
                    .completeDate(LocalDateTime.now());

            if (i % 2 == 1) {
                builder
                        .workStatus(WorkStatus.COMPLETE)
                        .workType(WorkType.UPLOAD)
                        .isRequiredFile(true)
                        .isCanceled(true);
            } else {
                builder
                        .workStatus(WorkStatus.INCOMPLETE)
                        .workType(WorkType.DOWNLOAD)
                        .isResultFile(true)
                        .isCanceled(false);
            }

            gridDataService.save(builder.workName("work" + i).build());
        }

        userService.joinV2(UserV2SaveRequestDto.builder()
                .username("user")
                .password("123")
                .email("user@user.com")
                .role(Role.MEMBER)
                .build());

        userService.joinV2(UserV2SaveRequestDto.builder()
                .username("manager")
                .password("123")
                .email("manager@manager.com")
                .role(Role.MANAGER)
                .build());

        userService.joinV2(UserV2SaveRequestDto.builder()
                .username("admin")
                .password("123")
                .email("admin@admin.com")
                .role(Role.ADMIN)
                .build());

        superAdminRepository.save(SuperAdmin.builder()
                .ip("0:0:0:0:0:0:0:1")
                .role(Role.SUPER_ADMIN)
                .build());

        superAdminRepository.save(SuperAdmin.builder()
                .ip("127.0.0.1")
                .role(Role.SUPER_ADMIN)
                .build());
*/
    }
}
