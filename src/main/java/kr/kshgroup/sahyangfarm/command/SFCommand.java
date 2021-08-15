package kr.kshgroup.sahyangfarm.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SFCommand {
    /**
     * 명령어를 식별할 수 있는 메인 명령어 문자열
     *
     * @return 메인 명령어 문자열
     */
    String getLabel();

    /**
     * 식별할 수 있는 메인 명령어 문자열 외에 명령어 문자열 목록을 반환하는 메소드
     *
     * @return 메인 명령어 문자열 이외의 명령어 문자열 목록
     */
    List<String> getAliasList();

    /**
     * 명령어 문자열이 이 클래스에서 담당하는지 확인하는 메소드
     *
     * @param arg 명령어 문자열
     * @return 이 클래스에서 담당하는 명령어 문자열 이라면 true 반환
     */
    boolean hasResponsibility(String arg);

    /**
     * 명령어 실행시 호출될 메소드
     *
     * @param sender 명령어를 실행한 주체
     * @param label  호출된 명령어 문자열 중 식별값 까지의 문자열
     * @param args   식별값을 제외한 명령어 문자열 (스페이스 바 단위로 구분)
     * @return 명령어 호출 반환값
     */
    boolean onCommand(CommandSender sender, String label, String[] args);
}
